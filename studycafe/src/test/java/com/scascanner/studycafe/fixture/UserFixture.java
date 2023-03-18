package com.scascanner.studycafe.fixture;

import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.domain.entity.User.UserBuilder;

import java.time.LocalDate;

public enum UserFixture {

    MINWOO("김민우", LocalDate.of(1999,12,18),"alsdn@naver.com","kmw2378","12345678"),
    GAEUN("길가은", LocalDate.of(2000,10,4), "rkdms@naver.com", "ddukddaki", "12345678"),
    BOHYUN("최보현",LocalDate.of(2001,10,16),"qhgus@naver.com","brilly","12345678");

    private final String name;
    private final LocalDate birth;
    private final String email;
    private final String nickname;
    private final String password;

    UserFixture(String name, LocalDate birth, String email, String nickname, String password) {
        this.name = name;
        this.birth = birth;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public UserBuilder 기본_정보_생성(){
        return User.builder()
                .name(this.name)
                .birthday(this.birth)
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password);
    }

    public User 생성() {
        return 기본_정보_생성().build();
    }

    public User 생성(Long id) {
        return 기본_정보_생성()
                .id(id)
                .build();
    }

}
