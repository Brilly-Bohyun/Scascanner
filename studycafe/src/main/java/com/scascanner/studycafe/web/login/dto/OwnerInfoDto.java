package com.scascanner.studycafe.web.login.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class OwnerInfoDto {
    private Long id;

    @NotEmpty(message = "사용자 이메일은 필수 입니다.")
    @Email
    private String email;

    @NotEmpty(message = "사용자 비밀번호는 필수 입니다.")
    @Size(min = 8, max = 20, message = "8자 이상 20자 이하로 작성해야 합니다.")
    private String password;

    @Size(min = 1, max = 10, message = "1자 이상 10자 이하로 작성해야 합니다.")
    private String nickname;

    @NotEmpty(message = "사용자 이름은 필수 입니다.")
    private String name;

    @Past
    private LocalDate birthday;

    @Builder
    public OwnerInfoDto(Long id, String email, String password, String nickname, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.birthday = birthday;
    }
}
