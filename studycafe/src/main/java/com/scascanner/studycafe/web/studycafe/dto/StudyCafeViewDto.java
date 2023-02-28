package com.scascanner.studycafe.web.studycafe.dto;

import com.scascanner.studycafe.domain.entity.StudyCafe;
import com.scascanner.studycafe.web.login.dto.OwnerDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Setter
@ToString
public class StudyCafeViewDto {

    private OwnerDto owner;
    private String name;
    private Integer minUsingTime;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String address;
    private String comment;

    @Builder
    public StudyCafeViewDto(OwnerDto owner, String name, Integer minUsingTime, LocalTime openTime, LocalTime closeTime, String address, String comment) {
        this.owner = owner;
        this.name = name;
        this.minUsingTime = minUsingTime;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.address = address;
        this.comment = comment;
    }

    public static StudyCafeViewDto of(StudyCafe studyCafe) {
        return StudyCafeViewDto.builder()
                .owner(OwnerDto.of(studyCafe.getOwner()))
                .name(studyCafe.getName())
                .minUsingTime(studyCafe.getMinUsingTime())
                .openTime(studyCafe.getOpenTime())
                .closeTime(studyCafe.getCloseTime())
                .address(studyCafe.getAddress())
                .comment(studyCafe.getComment())
                .build();
    }


}