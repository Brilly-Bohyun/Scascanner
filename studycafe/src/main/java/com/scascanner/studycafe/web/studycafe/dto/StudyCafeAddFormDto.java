package com.scascanner.studycafe.web.studycafe.dto;

import com.scascanner.studycafe.domain.entity.Owner;
import com.scascanner.studycafe.domain.entity.StudyCafe;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@ToString
public class StudyCafeDto {

    private OwnerDto owner;

    @NotBlank(message = "스터디 카페 이름은 필수입니다.")
    private String name;
    private Integer minUsingTime;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String address;
    private String comment;
}
