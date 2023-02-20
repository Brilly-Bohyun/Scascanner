package com.scascanner.studycafe.login.controller;

import com.scascanner.studycafe.login.dto.UserForm;
import com.scascanner.studycafe.login.dto.UserInfoDto;
import com.scascanner.studycafe.login.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/new-user")
    public ResponseEntity<UserInfoDto> saveUser(@RequestBody @Valid UserForm userForm){
        Long id = userService.join(userForm);
        UserInfoDto userInfoDto = new UserInfoDto(id, userForm.getEmail(), userForm.getPassword(), userForm.getNickname(),
                userForm.getName(), userForm.getBirthday());

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application","json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
    }

    @PatchMapping("/api/users/{id}")
    public ResponseEntity<UserInfoDto> partialUpdate(@PathVariable Long id, @RequestBody @Valid UserForm userForm){
        Long updatedId = userService.partialUpdate(id, userForm);
        UserInfoDto updatedUserInfoDto = new UserInfoDto(id, userForm.getEmail(), userForm.getPassword(), userForm.getNickname(),
                userForm.getName(), userForm.getBirthday());

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application","json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(updatedUserInfoDto, HttpStatus.OK);
    }
}
