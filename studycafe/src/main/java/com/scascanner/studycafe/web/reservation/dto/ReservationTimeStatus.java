package com.scascanner.studycafe.web.reservation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationTimeStatus {

    private int start;
    private int end;
    private boolean reservationStatus;

    public static ReservationTimeStatus of(int start, boolean reservationStatus) {
        return ReservationTimeStatus.builder()
                .start(start)
                .end(start + 1)
                .reservationStatus(reservationStatus)
                .build();
    }

}
