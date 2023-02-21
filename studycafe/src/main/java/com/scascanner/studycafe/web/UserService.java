package com.scascanner.studycafe.web;

import com.scascanner.studycafe.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.scascanner.studycafe.domain.entity.User;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Long userId){
        return userRepository.getUserById(userId);
    }
}
