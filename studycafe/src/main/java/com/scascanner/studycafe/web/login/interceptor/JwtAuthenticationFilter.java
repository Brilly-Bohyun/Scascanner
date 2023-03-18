package com.scascanner.studycafe.web.login.interceptor;

import com.scascanner.studycafe.domain.entity.Role;
import com.scascanner.studycafe.web.login.security.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 JWT를 받아옴
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        // 유효한 토큰인지 확인
        if(accessToken != null){
            // 에세스 토큰이 유효한 경우
            if(jwtTokenProvider.validateToken(accessToken)){
                this.setAuthentication(accessToken);
            }
            // 에세스 토큰이 만료된 상황 | 리프레시 토큰 또한 존재하는 상황
            else if(!jwtTokenProvider.validateToken(accessToken) && refreshToken != null){
                // 재발급 후, 컨텍스트에 다시 넣기
                // 리프레시 토큰 검증
                boolean validateRefreshToken = jwtTokenProvider.validateToken(refreshToken);
                // 리프레시 토큰 저장소 존재유무 확인
                boolean isRefreshToken = jwtTokenProvider.existsRefreshToken(refreshToken);
                if (validateRefreshToken && isRefreshToken){
                    // 리프레시 토큰으로 이메일 정보 가져오기
                    String userEmail = jwtTokenProvider.getUserEmail(refreshToken);
                    // 이메일로 권한 정보 받아오기
                    Role roles = jwtTokenProvider.getRoles(userEmail);
                    // 토큰 발급
                    String newAccessToken = jwtTokenProvider.createAccessToken(userEmail, roles);
                    // 헤더에 에세스 토큰 추가
                    jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                    // 컨텍스트에 넣기
                    this.setAuthentication(newAccessToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token) {
        // 토큰으로부터 유저 정보를 받아옴
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        // SecurityContext에 Authentication 객체 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
