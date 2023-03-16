package com.scascanner.studycafe.web.login.security.token;

import com.scascanner.studycafe.web.login.exception.CustomizedResponseEntityExceptionHandler;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RefreshController {

    private final JwtService jwtService;

    @PostMapping("/refresh")
    public ResponseEntity<?> validateRefreshToken(@RequestBody HashMap<String, String> bodyJson){
        log.info("refresh controller 실행");
        Map<String, String> map = jwtService.validateRefreshToken(bodyJson.get("refreshToken"));

        if (map.get("status").equals("402")){
            log.info("RefreshController - Refresh Token이 만료됨");
            ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .message(map.get("message"))
                    .build();

            return new ResponseEntity<>(exceptionResponse,HttpStatus.FORBIDDEN);
        }

        log.info("RefreshController - Refresh Token이 유효");

        return ResponseEntity.ok().body("Refresh Token이 유효");
    }

    @Builder
    @Getter
    static class ExceptionResponse{
        private LocalDateTime timestamp;
        private String message;
    }
}
