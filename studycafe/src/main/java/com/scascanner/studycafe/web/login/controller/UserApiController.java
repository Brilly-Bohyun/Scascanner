package com.scascanner.studycafe.web.login.controller;

import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.web.login.dto.UserForm;
import com.scascanner.studycafe.web.login.dto.UserInfoDto;
import com.scascanner.studycafe.web.login.dto.UserLogIn;
import com.scascanner.studycafe.web.login.dto.UserSavedDto;
import com.scascanner.studycafe.web.login.exception.UnMatchedPasswordException;
import com.scascanner.studycafe.web.login.exception.UserNotFoundException;
import com.scascanner.studycafe.web.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<UserSavedDto> saveUser(@RequestBody @Valid UserForm userForm){
        Long id = userService.join(userForm);
        UserSavedDto userSavedDto = new UserSavedDto(id, userForm.getName(),new Date());

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application","json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(userSavedDto, HttpStatus.OK);
    }

    @PostMapping("/api/users/login")
    public ResponseEntity<?> login(@RequestBody UserLogIn userLogIn){
            Long userId = userService.longIn(userLogIn);
            return ResponseEntity.ok().body("Login Succeeded");
    }

    @PatchMapping("/api/users/{id}")
    public ResponseEntity<UserInfoDto> partialUpdate(@PathVariable Long id, @RequestBody @Valid UserForm userForm){
        Long updatedId = userService.partialUpdate(id, userForm);
        UserInfoDto updatedUserInfoDto = getUserInfoDto(id, userForm);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application","json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(updatedUserInfoDto, HttpStatus.OK);
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        User user = userService.deleteById(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] is Not Found", id));
        }

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application","json", StandardCharsets.UTF_8));

        return ResponseEntity.ok().body("Delete Success");
    }

    private UserInfoDto getUserInfoDto(Long id, UserForm userForm) {
        UserInfoDto updatedUserInfoDto = new UserInfoDto(id, userForm.getEmail(), userForm.getPassword(), userForm.getNickname(),
                userForm.getName(), userForm.getBirthday());
        return updatedUserInfoDto;
    }
}
