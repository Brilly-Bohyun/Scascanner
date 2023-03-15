package com.scascanner.studycafe.web.reservation.dto;

import lombok.Builder;

@Builder
public class DayReservationStatus {

    private int day;
    private boolean reservationStatus;

    public static DayReservationStatus of(int day, boolean reservationStatus) {
        return DayReservationStatus.builder()
                .day(day)
                .reservationStatus(reservationStatus)
                .build();
    }
}
