package com.scascanner.studycafe.web.login.service;

import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.domain.repository.UserRepository;
import com.scascanner.studycafe.web.login.dto.UserForm;
import com.scascanner.studycafe.web.login.dto.UserLogIn;
import com.scascanner.studycafe.web.login.exception.UnMatchedPasswordException;
import com.scascanner.studycafe.web.login.exception.UserNotFoundException;
import com.scascanner.studycafe.web.login.security.token.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long join(UserForm userForm){
        User user = userForm.toEntity();
        userRepository.save(user);
        return user.getId();
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException(String.format("There is no Id : %s", userId)));
    }

    public User findByUserId(String email){
        return userRepository.findByUserId(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("There is no email : %s, You need to SignUp", email)));
    }

    @Transactional
    public String longIn(UserLogIn userLogIn){
        User user = findByUserId(userLogIn.getEmail());
        checkPassword(userLogIn.getPassword(), user.getPassword());
        return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
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
            throw new UnMatchedPasswordException("Password is not matched");
        }
    }
}
