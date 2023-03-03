package com.scascanner.studycafe.web.login.service;

import com.scascanner.studycafe.domain.entity.Owner;
import com.scascanner.studycafe.domain.repository.OwnerRepository;
import com.scascanner.studycafe.web.login.dto.OwnerDto;
import com.scascanner.studycafe.web.login.dto.OwnerLogIn;
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
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(OwnerDto ownerDto){
        Owner owner = ownerDto.toEntity();
        ownerRepository.save(owner);
        return owner.getId();
    }

    public List<Owner> findAllUsers(){
        return ownerRepository.findAll();
    }

    public Owner findById(Long ownerId){
        return ownerRepository.findById(ownerId)
                .orElseThrow(()-> new UserNotFoundException(String.format("There is no Id : %s", ownerId)));
    }

    public Owner findByEmail(String email){
        return ownerRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("There is no email : %s, You need to SignUp", email)));
    }

    @Transactional
    public Owner longIn(OwnerLogIn ownerLogIn){
        Owner owner = findByEmail(ownerLogIn.getEmail());
        checkPassword(ownerLogIn.getPassword(), owner.getPassword());
        return owner;
    }

    @Transactional
    public Long partialUpdate(Long id, OwnerDto ownerDto){
        Owner owner = ownerRepository.findById(id).get();
        ownerRepository.save(owner);
        return owner.getId();
    }

    @Transactional
    public Owner deleteById(Long ownerId) {
        Optional<Owner> owner = ownerRepository.findById(ownerId);
        if(owner.isPresent()){
            ownerRepository.deleteById(ownerId);
            return owner.get();
        }
        return null;
    }

    private void checkPassword(String loginPassword, String savedPassword) {
        if(!passwordEncoder.matches(loginPassword, savedPassword)){
            throw new UnMatchedPasswordException("Password is not matched");
        }
    }

}
