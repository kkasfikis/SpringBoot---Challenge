package com.kkasfikis.quintessential.challenge.jwt.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kkasfikis.quintessential.challenge.jwt.models.User;
import com.kkasfikis.quintessential.challenge.jwt.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      Optional<User> user = userRepository.findByEmail(email);

      if (user == null) {
          throw new UsernameNotFoundException("Not found!");
      } 

      return UserDetailsImpl.build(user.get());
  }
  
}
