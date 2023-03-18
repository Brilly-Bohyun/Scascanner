package com.scascanner.studycafe.web.login.security.token;

import com.scascanner.studycafe.domain.entity.Role;
import com.scascanner.studycafe.domain.repository.UserRepository;
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
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "secret";
    private String refreshSecretKey;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

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
    public String createToken(String user, Role roles, long tokenValid){
        // JWT payload에 저장되는 정보 단위
        Claims claims = Jwts.claims().setSubject(user);
        // 정보는 key / value 쌍으로 저장됨
        claims.put("roles", roles);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(now) // 발행 시간 저장
                .setExpiration(new Date(now.getTime() + tokenValid)) // 토큰 유효 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) //해싱 알고리즘 및 키 설정
                .compact(); //생성
    }

    // Access Token 생성
    public String createAccessToken(String email, Role roles){
        return this.createToken(email, roles, accessTokenValidTime);
    }

    // Refresh Token 생성
    public String createRefreshToken(String email, Role roles){
        return this.createToken(email, roles, refreshTokenValidTime);
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

    // Email로 권한 정보 추출
    public Role getRoles(String email){
        return userRepository.findByEmail(email).get().getRoles();
    }

    // RefreshToken 존재유무 확인
    public boolean existsRefreshToken(String refreshToken){
        return refreshTokenRepository.existsByRefreshToken(refreshToken);
    }

    // Request의 Header에서 token 값을 가져온다 "authorization" : "TOKEN값"
    public String resolveAccessToken(HttpServletRequest request){
        if(request.getHeader("authorization") != null){
            return request.getHeader("authorization").substring(7);
        }
        return null;
    }

    // Request의 Header에서 RefreshToken 값을 가져온다. "refreshToken" : "TOKEN값"
    public String resolveRefreshToken(HttpServletRequest request){
        if(request.getHeader("refreshToken") != null){
            return request.getHeader("refreshToken").substring(7);
        }
        return null;
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

    // 에세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken){
        response.setHeader("authorization", "bearer " + accessToken);
    }

    // 리프레시 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response ,String refreshToken){
        response.setHeader("refreshToken", "bearer " + refreshToken);
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
