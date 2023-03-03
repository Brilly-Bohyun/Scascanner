package com.scascanner.studycafe.web.login.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
public class OwnerLogIn {
    @NotEmpty(message = "로그인을 위해 이메일은 필수입니다.")
    private String email;
    @NotEmpty(message = "로그인을 위해 비밀번호는 필수입니다.")
    private String password;

    @Builder
    public OwnerLogIn(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
