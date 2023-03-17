package com.scascanner.studycafe.web.login.controller;

import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.web.login.dto.UserForm;
import com.scascanner.studycafe.web.login.dto.UserInfoDto;
import com.scascanner.studycafe.web.login.dto.UserLogIn;
import com.scascanner.studycafe.web.login.exception.UserNotFoundException;
import com.scascanner.studycafe.web.login.security.token.JwtTokenProvider;
import com.scascanner.studycafe.web.login.security.token.RefreshToken;
import com.scascanner.studycafe.web.login.security.token.RefreshTokenRepository;
import com.scascanner.studycafe.web.login.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Api(tags = {"1. User"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserApiController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회한다")
    @GetMapping
    public List<User> findAllUser(){
        return userService.findAllUsers();
    }

    @ApiOperation(value = "회원 저장", notes = "회원 가입을 한다")
    @PostMapping
    public ResponseEntity<UserSavedDto> saveUser(@RequestBody @Valid UserForm userForm){
        Long id = userService.join(userForm);
        UserSavedDto userSavedDto = UserSavedDto.builder()
                .id(id)
                .name(userForm.getName())
                .joinDate(new Date()).build();

        return new ResponseEntity<>(userSavedDto, HttpStatus.OK);
    }

    @ApiOperation(value = "로그인", notes = "이메일과 비밀번호를 입력하여 로그인을 한다")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogIn userLogIn, HttpServletResponse response){
        User user = userService.longIn(userLogIn);

        // 토큰 발급 및 헤더 설정
        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRoles());
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

        //리프레시 토큰 저장소에 저장
        refreshTokenRepository.save(new RefreshToken(refreshToken));

        return ResponseEntity.ok().body("로그인 성공!");
    }

    @ApiOperation(value = "회원 정보 수정", notes = "바뀐 정보를 입력하여 정보를 수정한다")
    @PatchMapping("/{id}")
    public ResponseEntity<UserInfoDto> partialUpdate(@PathVariable Long id, @RequestBody @Valid UserForm userForm){
        Long updatedId = userService.partialUpdate(id, userForm);
        UserInfoDto updatedUserInfoDto = getUserInfoDto(id, userForm);

        return new ResponseEntity<>(updatedUserInfoDto, HttpStatus.OK);
    }

    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴를 합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        User user = userService.deleteById(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] is Not Found", id));
        }

        return ResponseEntity.ok().body("Delete Success");
    }

    private UserInfoDto getUserInfoDto(Long id, UserForm userForm) {
        UserInfoDto updatedUserInfoDto = UserInfoDto.builder()
                .id(id)
                .email(userForm.getEmail())
                .password(userForm.getPassword())
                .nickname(userForm.getNickname())
                .name(userForm.getName())
                .birthday(userForm.getBirthday())
                .build();
        return updatedUserInfoDto;
    }

    /**
     * 회원 가입 성공 시 반환 DTO
     */
    @Builder
    @Getter
    static class UserSavedDto{
        private Long id;
        private String name;
        private Date joinDate;
    }
}
