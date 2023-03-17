package com.scascanner.studycafe.web.login.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
public class UserLogIn {
    @ApiModelProperty(example = "abcdef@naver.com", required = true)
    @ApiParam(value = "사용자 아이디")
    @NotEmpty(message = "로그인을 위해 이메일은 필수입니다.")
    private String email;

    @ApiModelProperty(example = "absdf123456", required = true)
    @ApiParam(value = "사용자 비밀번호")
    @NotEmpty(message = "로그인을 위해 비밀번호는 필수입니다.")
    private String password;

    @Builder
    public UserLogIn(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
