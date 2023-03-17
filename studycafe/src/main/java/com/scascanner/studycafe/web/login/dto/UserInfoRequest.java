package com.scascanner.studycafe.web.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scascanner.studycafe.domain.entity.Role;
import com.scascanner.studycafe.domain.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class UserInfoRequest {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(hidden = true)
    private Long id;

    @NotEmpty(message = "사용자 이메일은 필수 입니다.")
    @ApiModelProperty(example = "abcdef@naver.com", required = true)
    @ApiParam(value = "사용자 아이디")
    @Email
    private String email;

    @NotEmpty(message = "사용자 비밀번호는 필수 입니다.")
    @ApiModelProperty(example = "absdf123456", required = true)
    @ApiParam(value = "사용자 비밀번호")
    @Size(min = 8, max = 20, message = "8자 이상 20자 이하로 작성해야 합니다.")
    private String password;

    @ApiModelProperty(example = "말하는감자", required = true)
    @ApiParam(value = "사용자 닉네임")
    @Size(min = 1, max = 10, message = "1자 이상 10자 이하로 작성해야 합니다.")
    private String nickname;

    @ApiModelProperty(example = "홍길동", required = true)
    @ApiParam(value = "사용자 이름")
    @NotEmpty(message = "사용자 이름은 필수 입니다.")
    private String name;

    @ApiModelProperty(example = "1965-02-02", required = true)
    @ApiParam(value = "사용자 생년월일")
    @Past
    private LocalDate birthday;

    @Builder
    public UserInfoRequest(Long id, String email, String password, String nickname, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.birthday = birthday;
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password))
                .nickname(nickname)
                .name(name)
                .birthday(birthday)
                .roles(Role.USER)
                .build();
    }
}
