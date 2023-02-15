package com.scascanner.studycafe.login.controller;

import com.scascanner.studycafe.login.dto.UserForm;
import com.scascanner.studycafe.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/new")
    public String createForm(Model model){
        model.addAttribute("userForm", new UserForm());
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(@Valid UserForm userForm, BindingResult result){
        if(result.hasErrors()){
            return "users/createUserForm";
        }
        userService.join(userForm);
        return "redirect:/";
    }
}
