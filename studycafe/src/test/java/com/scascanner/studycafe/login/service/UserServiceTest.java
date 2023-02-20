package com.scascanner.studycafe.login.service;

import com.scascanner.studycafe.web.login.dto.UserForm;
import com.scascanner.studycafe.domain.repository.UserRepository;
import com.scascanner.studycafe.web.login.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
public class UserServiceTest{
    @Autowired
    UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void 회원가입() throws Exception{
        //Given

        String email = "qhgus564@naver.com";
        String password = "choi1234";
        String nickname = "뽀";
        String name = "최보현";
        LocalDate birthday = LocalDate.of(2001, 10, 16);

        //when
        UserForm userForm1 = UserForm.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .name(name)
                .birthday(birthday)
                .build();

        Long savedId = userService.join(userForm1);

        //then
        Assertions.assertEquals(userRepository.findOne(savedId), userRepository.findByName(name));
    }
}
