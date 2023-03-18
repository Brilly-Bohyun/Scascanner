package com.scascanner.studycafe.web.pay.request.dto;

import com.scascanner.studycafe.domain.entity.StudyCafe;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
public class StudyCafeRequestPay {

    private Long id;
    private String name;
    private Integer minUsingTime;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String address;
    private String comment;

    @Builder
    public StudyCafeRequestPay(Long id, String name, Integer minUsingTime, LocalTime openTime, LocalTime closeTime, String address, String comment) {
        this.id = id;
        this.name = name;
        this.minUsingTime = minUsingTime;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.address = address;
        this.comment = comment;
    }

    public static StudyCafeRequestPay of(StudyCafe studyCafe) {

        return StudyCafeRequestPay.builder()
                .id(studyCafe.getId())
                .name(studyCafe.getName())
                .minUsingTime(studyCafe.getMinUsingTime())
                .openTime(studyCafe.getOpenTime())
                .closeTime(studyCafe.getCloseTime())
                .address(studyCafe.getAddress())
                .comment(studyCafe.getComment())
                .build();
    }
}
