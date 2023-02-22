package com.scascanner.studycafe.login.service;

import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.domain.repository.UserRepository;
import com.scascanner.studycafe.web.login.dto.UserForm;
import com.scascanner.studycafe.web.login.dto.UserLogIn;
import com.scascanner.studycafe.web.login.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest{
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원가입() throws Exception{
        //Given
        String email = "qhgus564@naver.com";
        String password = "choi1234";
        String nickname = "뽀";
        String name = "최보현";
        LocalDate birthday = LocalDate.of(2001, 10, 16);

        //when
        Long savedId = getId(email, password, nickname, name, birthday);

        //then
        assertEquals(userRepository.findByName(name).get(0), userRepository.findById(savedId).get());
    }

    @Test
    public void 이메일로찾기() throws Exception{
        //Given
        String email = "qhgus564@naver.com";
        String password = "choi1234";
        String nickname = "뽀";
        String name = "최보현";
        LocalDate birthday = LocalDate.of(2001, 10, 16);

        //when
        Long savedId = getId(email, password, nickname, name, birthday);
        User userByEmail = userService.findByEmail(email);

        //then
        assertEquals(userRepository.findById(savedId).get(), userByEmail);
    }

    @Test
    public void 로그인하기() throws Exception{
        //Given
        String email = "qhgus564@naver.com";
        String password = "choi1234";
        String nickname = "뽀";
        String name = "최보현";
        LocalDate birthday = LocalDate.of(2001, 10, 16);

        //when
        Long savedId = getId(email, password, nickname, name, birthday);
        Long logInedId = login(email, password);

        //then
        assertEquals(logInedId, savedId);
    }

    private Long login(String email, String password) {
        UserLogIn userLogIn = UserLogIn.builder()
                .email(email)
                .password(password)
                .build();
        Long logInedId = userService.logIn(userLogIn);
        return logInedId;
    }

    private Long getId(String email, String password, String nickname, String name, LocalDate birthday) {
        UserForm userForm1 = UserForm.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .name(name)
                .birthday(birthday)
                .build();

        Long savedId = userService.join(userForm1);
        return savedId;
    }
}
