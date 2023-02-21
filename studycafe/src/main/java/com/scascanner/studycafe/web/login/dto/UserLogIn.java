package com.scascanner.studycafe.web.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogIn {
    private String email;
    private String password;
}
