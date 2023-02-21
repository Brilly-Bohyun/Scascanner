package com.scascanner.studycafe.web.login.service;

import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.domain.repository.UserRepository;
import com.scascanner.studycafe.web.login.dto.UserForm;
import com.scascanner.studycafe.web.login.dto.UserLogIn;
import com.scascanner.studycafe.web.login.exception.UnMatchedPasswordException;
import com.scascanner.studycafe.web.login.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        return userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException(String.format("There is no Id : %s", userId)));
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("There is no email : %s, You need to SignUp", email)));
    }

    @Transactional
    public Long longIn(UserLogIn userLogIn){
        User user = findByEmail(userLogIn.getEmail());
        checkPassword(userLogIn.getPassword(), user.getPassword());
        return user.getId();
    }

    @Transactional
    public Long partialUpdate(Long id, UserForm userForm){
        User user = userRepository.findById(id).get();
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public User deleteById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            userRepository.deleteById(userId);
            return user.get();
        }
        return null;
    }

    private void checkPassword(String loginPassword, String savedPassword) {
        if(!passwordEncoder.matches(loginPassword, savedPassword)){
            throw new UnMatchedPasswordException(String.format("Password is not matched"));
        }
    }
}
