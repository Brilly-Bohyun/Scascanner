package com.scascanner.studycafe.web.login.security.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void login(Token tokenDto){
        RefreshToken refreshToken = RefreshToken.builder().keyEmail(tokenDto.getKey()).refreshToken(tokenDto.getRefreshToken()).build();
        String loginUserEmail = refreshToken.getKeyEmail();
        if(refreshTokenRepository.exsistsByKeyEmail(loginUserEmail)){
            log.info("기존의 존재하는 refresh 토큰 삭제");
            refreshTokenRepository.deleteByKeyEmail(loginUserEmail);
        }
        refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> getRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public Map<String, String> validateRefreshToken(String refreshToken){
        RefreshToken refreshedToken = getRefreshToken(refreshToken).get();
        String createdAccessToken = jwtTokenProvider.validateRefreshToken(refreshedToken);

        return createdRefreshJson(createdAccessToken);
    }

    private Map<String, String> createdRefreshJson(String createdAccessToken) {
        Map<String, String> map = new HashMap<>();
        if(createdAccessToken == null){
            map.put("errortype", "Forbidden");
            map.put("status", "402");
            map.put("message", "Refresh Token이 만료되어 로그인이 필요합니다.");

            return map;
        }

        //기존에 존재하는 AccessToken 제거
        map.put("status", "200");
        map.put("message", "Refresh Token을 활용한 Access Token 생성이 완료되었습니다.");
        map.put("accessToken", createdAccessToken);

        return map;
    }
}
