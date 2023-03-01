package com.scascanner.studycafe;

import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.domain.repository.UserRepository;
import com.scascanner.studycafe.web.login.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;

    @GetMapping("/")
    public String home(@Login User loginUser, Model model){

        if(loginUser == null){
            return "home";
        }

        model.addAttribute("user", loginUser);
        return "loginHome";
    }
}
