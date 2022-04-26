package com.kkasfikis.quintessential.challenge.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kkasfikis.quintessential.challenge.jwt.models.ERole;
import com.kkasfikis.quintessential.challenge.jwt.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
