package com.scascanner.studycafe.web.login.controller;

import com.scascanner.studycafe.web.login.dto.UserForm;
import com.scascanner.studycafe.web.login.dto.UserLogIn;
import com.scascanner.studycafe.web.login.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("userForm", new UserForm());
        return "users/createUserForm";
    }

    @PostMapping("/new")
    public String create(@Valid UserForm userForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("문제 발생");
            log.info("errors = {}", bindingResult);
            return "users/createUserForm";
        }
        userService.join(userForm);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("userLogIn", new UserLogIn());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid UserLogIn userLogIn, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            log.info("문제 발생");
            log.info("errors = {}", bindingResult);
            return "login/loginForm";
        }

        String loginUser = userService.longIn(userLogIn);

        if(loginUser == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login/loginForm";
        }

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

        return "redirect:/";
    }
}
