package com.scascanner.studycafe.login.service;

import com.scascanner.studycafe.domain.User;
import com.scascanner.studycafe.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long join(User user){
        userRepository.save(user);
        return user.getId();
    }
}
