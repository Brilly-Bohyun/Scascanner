package com.scascanner.studycafe.login.service;

import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.domain.repository.UserRepository;
import com.scascanner.studycafe.web.login.security.SecurityConfig;
import com.scascanner.studycafe.web.login.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class UserServiceTest{
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityConfig securityConfig;

    @Test
    public void 패스워드_암호화_테스트() throws Exception{
        //given
        String rawPW = "1234";

        //when
        String encodedPW = securityConfig.passwordEncoder().encode(rawPW);

        //then
        assertThat(rawPW).isNotEqualTo(encodedPW);
    }

    @Test
    public void 패스워드_일치_테스트() throws Exception{
        //given
        String rawPW = "1234";
        String encodedPW = securityConfig.passwordEncoder().encode(rawPW);
        String inputPW = "1234";

        //when
        Boolean check = securityConfig.passwordEncoder().matches(inputPW, encodedPW);

        //then
        assertThat(check).isEqualTo(true);
    }

    @Test
    public void 패스워드_불일치_테스트() throws Exception{
        //given
        String rawPW = "1234";
        String encodedPW = securityConfig.passwordEncoder().encode(rawPW);
        String inputPW = "123456";

        //when
        Boolean check = securityConfig.passwordEncoder().matches(inputPW, encodedPW);

        //then
        assertThat(check).isEqualTo(false);
    }
}
