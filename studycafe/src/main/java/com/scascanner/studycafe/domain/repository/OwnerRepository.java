package com.scascanner.studycafe.domain.repository;

import com.scascanner.studycafe.domain.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    List<Owner> findByName(String name);
    Optional<Owner> findByEmail(String email);
}
