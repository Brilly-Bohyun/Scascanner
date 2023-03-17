package com.scascanner.studycafe.web.login.controller;

import com.scascanner.studycafe.domain.entity.Owner;
import com.scascanner.studycafe.web.login.dto.*;
import com.scascanner.studycafe.web.login.exception.UserNotFoundException;
import com.scascanner.studycafe.web.login.service.OwnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Api(tags = {"2. Owner"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/owners")
public class OwnerApiController {
    private final OwnerService ownerService;

    @ApiOperation(value = "오너 저장", notes = "회원 가입을 한다")
    @PostMapping
    public ResponseEntity<OwnerSavedDto> saveOwner(@RequestBody @Valid OwnerDto ownerDto){
        Long ownerId = ownerService.join(ownerDto);
        OwnerSavedDto ownerSavedDto = OwnerSavedDto.builder()
                .id(ownerId)
                .name(ownerDto.getName())
                .joinDate(new Date()).build();

        return new ResponseEntity<>(ownerSavedDto, HttpStatus.OK);
    }

    @ApiOperation(value = "오너 로그인", notes = "이메일과 비밀번호를 입력하여 로그인을 한다")
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody OwnerLogIn ownerLogIn){
        Owner owner = ownerService.longIn(ownerLogIn);

        return ResponseEntity.ok().body("Login Succeeded");
    }

    @ApiOperation(value = "오너 정보 수정", notes = "바뀐 정보를 입력하여 정보를 수정한다")
    @PatchMapping("/{id}")
    public ResponseEntity<OwnerInfoDto> partialUpdate(@PathVariable Long id, @RequestBody @Valid OwnerDto ownerDto){
        Long updatedId = ownerService.partialUpdate(id, ownerDto);
        OwnerInfoDto updatedOwnerInfoDto = getOwnerInfoDto(updatedId, ownerDto);

        return new ResponseEntity<>(updatedOwnerInfoDto, HttpStatus.OK);
    }

    @ApiOperation(value = "회원 탈퇴", notes = "오너 탈퇴를 합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOwner(@PathVariable Long id){
        Owner owner = ownerService.deleteById(id);

        if(owner == null){
            throw new UserNotFoundException(String.format("ID[%s] is Not Found", id));
        }

        return ResponseEntity.ok().body("Delete Success");
    }

    private OwnerInfoDto getOwnerInfoDto(Long id, OwnerDto ownerDto) {
        OwnerInfoDto updatedOwnerInfoDto = OwnerInfoDto.builder()
                .id(id)
                .email(ownerDto.getEmail())
                .password(ownerDto.getPassword())
                .name(ownerDto.getName())
                .nickname(ownerDto.getNickname())
                .birthday(ownerDto.getBirthday())
                .build();
        return updatedOwnerInfoDto;
    }

    /**
     * 회원 가입 성공 시 반환 DTO
     */
    @Builder
    @Getter
    static class OwnerSavedDto{
        private Long id;
        private String name;
        private Date joinDate;
    }

}
