package com.scascanner.studycafe.web.login.controller;

import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.web.login.dto.UserForm;
import com.scascanner.studycafe.web.login.dto.UserInfoDto;
import com.scascanner.studycafe.web.login.dto.UserLogIn;
import com.scascanner.studycafe.web.login.exception.UserNotFoundException;
import com.scascanner.studycafe.web.login.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Api(tags = {"1. User"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserApiController {

    private final UserService userService;

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

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody UserLogIn userLogIn){
        String userToken = userService.longIn(userLogIn);

        return ResponseEntity.ok().body("UserLogin Succeeded, userToken is" + userToken);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserInfoDto> partialUpdate(@PathVariable Long id, @RequestBody @Valid UserForm userForm){
        Long updatedId = userService.partialUpdate(id, userForm);
        UserInfoDto updatedUserInfoDto = getUserInfoDto(id, userForm);

        return new ResponseEntity<>(updatedUserInfoDto, HttpStatus.OK);
    }

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
