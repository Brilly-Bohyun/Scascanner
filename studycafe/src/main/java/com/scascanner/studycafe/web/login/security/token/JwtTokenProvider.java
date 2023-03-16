package com.scascanner.studycafe.web.login.security.token;

import com.scascanner.studycafe.domain.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey;
    private String refreshSecretKey;

    // 토큰 유효 시간 1시간
    private Long accessTokenValidTime = 60 * 60 * 1000L; // 1시간
    private Long refreshTokenValidTime = 60 * 60 * 1000L * 24 * 15; // 15일

    private UserDetailsService userDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //JWT 토큰 생성
    public Token createToken(String user, Role roles){
        // JWT payload에 저장되는 정보 단위
        Claims claims = Jwts.claims().setSubject(user);
        // 정보는 key / value 쌍으로 저장됨
        claims.put("roles", roles);
        Date now = new Date();

        //Access Token
        String accessToken = Jwts.builder()
                   .setClaims(claims) // 정보 저장
                   .setIssuedAt(now) // 토큰 발행 시간 정보
                   .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // 토큰 유효 기간 설정
                    .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature에 들어갈 secret값 세팅
                    .compact();

        // Refresh Token
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)
                .compact();

        return Token.builder().accessToken(accessToken).refreshToken(refreshToken).key(user).build();
    }

    // JWT 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserEmail(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져온다 "X-AUTH-TOKEN" : "TOKEN값"
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e){
            return false;
        }
    }

    public String validateRefreshToken(RefreshToken refreshToken){
        // refresh 객체에서 refreshToken 추출
        String refreshedToken = refreshToken.getRefreshToken();

        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshedToken);

            // refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰 생성
            if(!claims.getBody().getExpiration().before(new Date())){
                return recreationAccessToken(claims.getBody().get("sub").toString(), claims.getBody().get("roles"));
            }
        } catch (Exception e){
            // refresh 토큰 만료시, 로그인 필요
            return null;
        }
        return null;
    }

    private String recreationAccessToken(String user, Object roles) {
        Claims claims = Jwts.claims().setSubject(user);
        claims.put("roles", roles);
        Date now = new Date();

        // Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return accessToken;
    }
}
