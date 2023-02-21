package com.scascanner.studycafe.web.login.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
public class UserSavedDto {
    private Long id;
    private String name;
    private Date joinDate;

    @Builder
    public UserSavedDto(Long id, String name, Date joinDate) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
    }
}
