package com.scascanner.studycafe.web.login.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
public class UserLogIn {
    @NotEmpty(message = "로그인을 위해 이메일은 필수입니다.")
    private String email;
    @NotEmpty(message = "로그인을 위해 비밀번호는 필수입니다.")
    private String password;

    @Builder
    public UserLogIn(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
