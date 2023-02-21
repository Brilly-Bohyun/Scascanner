package com.scascanner.studycafe.web.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserSavedDto {
    private Long id;
    private String name;
    private Date joinDate;
}
