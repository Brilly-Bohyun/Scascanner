package com.scascanner.studycafe.fixture;

import com.scascanner.studycafe.domain.entity.StudyCafe;
import com.scascanner.studycafe.domain.entity.StudyCafe.StudyCafeBuilder;

import java.time.LocalTime;

public enum StudyCafeFixture {

    AEGIS("이지스스터디카페", LocalTime.of(0, 0), LocalTime.of(23, 0)),
    TIMEU("타임유스터디카페", LocalTime.of(10, 0), LocalTime.of(23, 0));

    private String name;
    private LocalTime openTime;
    private LocalTime closeTime;

    StudyCafeFixture(String name, LocalTime openTime, LocalTime closeTime) {
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public StudyCafeBuilder 기본_정보_생성() {
        return StudyCafe.builder()
                .name(this.name)
                .openTime(this.openTime)
                .closeTime(this.closeTime);
    }

    public StudyCafe 생성() {
        return 기본_정보_생성().build();
    }
}
