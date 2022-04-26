package com.kkasfikis.quintessential.challenge.jwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kkasfikis.quintessential.challenge.jwt.exception.TokenRefreshException;
import com.kkasfikis.quintessential.challenge.jwt.models.ERole;
import com.kkasfikis.quintessential.challenge.jwt.models.RefreshToken;
import com.kkasfikis.quintessential.challenge.jwt.models.Role;
import com.kkasfikis.quintessential.challenge.jwt.models.User;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.LogOutRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.LoginRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.SignupRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.TokenRefreshRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.response.JwtResponse;
import com.kkasfikis.quintessential.challenge.jwt.payload.response.MessageResponse;
import com.kkasfikis.quintessential.challenge.jwt.payload.response.TokenRefreshResponse;
import com.kkasfikis.quintessential.challenge.jwt.repository.RoleRepository;
import com.kkasfikis.quintessential.challenge.jwt.repository.UserRepository;
import com.kkasfikis.quintessential.challenge.jwt.security.jwt.JwtUtils;
import com.kkasfikis.quintessential.challenge.jwt.security.services.RefreshTokenService;
import com.kkasfikis.quintessential.challenge.jwt.security.services.UserDetailsImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  RefreshTokenService refreshTokenService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(userDetails);

    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
        .collect(Collectors.toList());

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

    return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
        userDetails.getUsername(), userDetails.getEmail(), roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()),signUpRequest.getFirstName(), 
        signUpRequest.getLastName());

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);
          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }
    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
          String token = jwtUtils.generateTokenFromEmail(user.getEmail());
          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        })
        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,"Refresh token is not in database!"));
  }
  
  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser() {
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  Optional<User> user = userRepository.findByUsername(auth.getName());
	  if(!user.isPresent()) { return ResponseEntity.ok(new MessageResponse("Could not match user"));}
      refreshTokenService.deleteByUserId(user.get().getId());
      return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }

}
