package com.scascanner.studycafe.web.login.service;

import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.web.login.dto.UserForm;
import com.scascanner.studycafe.domain.repository.UserRepository;
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

    @Transactional
    public Long partialUpdate(Long id, UserForm userForm){
        User user = userRepository.findOne(id);
        userRepository.save(user);
        return user.getId();
    }
}
