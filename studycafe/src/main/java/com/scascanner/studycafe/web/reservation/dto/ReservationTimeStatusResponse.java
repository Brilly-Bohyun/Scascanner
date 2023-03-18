package com.scascanner.studycafe.web.reservation.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ReservationTimeStatusResponse {

    private LocalDate date;
    private List<ReservationTimeStatus> reservationTimeStatus;

    public static ReservationTimeStatusResponse of(List<ReservationTimeStatus> reservationTimeStatus, LocalDate date) {
        return ReservationTimeStatusResponse.builder()
                .date(date)
                .reservationTimeStatus(reservationTimeStatus)
                .build();
    }

}
