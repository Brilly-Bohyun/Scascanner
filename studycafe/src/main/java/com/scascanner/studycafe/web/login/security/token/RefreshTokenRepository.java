package com.scascanner.studycafe.web.login.security.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    boolean existsByUser(String userId);
    void deleteByUser(String userId);
    boolean existsByRefreshToken(String refreshToken);
}
