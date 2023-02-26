package com.scascanner.studycafe.web.studycafe.dto;

import com.scascanner.studycafe.domain.entity.Owner;
import com.scascanner.studycafe.domain.entity.StudyCafe;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Setter
@ToString
public class StudyCafeDto {

    private Long id;
    private Owner owner;
    private String name;
    private Integer minUsingTime;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String address;
    private String comment;

    @Builder
    public StudyCafeDto(Long id, Owner owner, String name, Integer minUsingTime, LocalTime openTime, LocalTime closeTime, String address, String comment) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.minUsingTime = minUsingTime;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.address = address;
        this.comment = comment;
    }

    public StudyCafe toEntity() {
        return StudyCafe.builder()
                .id(id)
                .owner(owner)
                .name(name)
                .minUsingTime(minUsingTime)
                .openTime(openTime)
                .closeTime(closeTime)
                .address(address)
                .comment(comment)
                .build();
    }
}
