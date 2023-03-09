package com.scascanner.studycafe.domain.repository;

import com.scascanner.studycafe.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
  List<User> findByName(String name);
  Optional<User> findByUserId(String email);
}
