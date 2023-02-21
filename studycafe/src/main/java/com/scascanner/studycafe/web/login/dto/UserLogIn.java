package com.scascanner.studycafe.web.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
public class UserLogIn {
    @NotEmpty(message = "로그인을 위해 이메일은 필수입니다.")
    private String email;
    @NotEmpty(message = "로그인을 위해 비밀번호는 필수입니다.")
    private String password;
}
