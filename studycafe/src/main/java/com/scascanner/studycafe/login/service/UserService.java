package com.scascanner.studycafe.login.service;

import com.scascanner.studycafe.domain.User;
import com.scascanner.studycafe.login.dto.UserForm;
import com.scascanner.studycafe.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(UserForm userForm){
        User user = userForm.toEntity();
        userRepository.save(user);
        return user.getId();
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findOneUser(Long userId){
        return userRepository.findOne(userId);
    }
}
