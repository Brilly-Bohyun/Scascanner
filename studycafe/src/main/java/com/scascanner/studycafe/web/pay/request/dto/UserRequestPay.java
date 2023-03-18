package com.scascanner.studycafe.web.pay.request.dto;

import com.scascanner.studycafe.domain.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserRequestPay {

    private Long id;
    private String nickname;
    private String email;
    private String name;

    @Builder
    public UserRequestPay(Long id, String nickname, String email, String name) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.name = name;
    }

    public static UserRequestPay of(User user) {
        return UserRequestPay.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .name(user.getName())
                .build();
    }
}
