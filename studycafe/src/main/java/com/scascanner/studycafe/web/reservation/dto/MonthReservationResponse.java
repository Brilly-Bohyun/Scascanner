package com.scascanner.studycafe.web.reservation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MonthReservationResponse {

    private int month;
    private List<DayReservationStatus> dayReservationStatus;

    public static MonthReservationResponse of(int month, List<DayReservationStatus> dayReservationStatus) {
        return MonthReservationResponse.builder()
                .month(month)
                .dayReservationStatus(dayReservationStatus)
                .build();
    }

}
