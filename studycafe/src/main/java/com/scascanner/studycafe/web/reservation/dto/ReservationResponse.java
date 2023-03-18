package com.scascanner.studycafe.web.reservation.dto;

import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import com.scascanner.studycafe.domain.entity.reservation.ReservationStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationResponse {
    private String studyCafeName;
    private Integer roomHeadCount;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private ReservationStatus reservationStatus;

    public ReservationResponse(String studyCafeName, Integer roomHeadCount, LocalDate date, LocalTime startTime, LocalTime endTime, ReservationStatus reservationStatus) {
        this.studyCafeName = studyCafeName;
        this.roomHeadCount = roomHeadCount;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservationStatus = reservationStatus;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getStudyCafe().getName(),
                reservation.getRoom().getHeadCount(),
                reservation.getDate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getReservationStatus());
    }
}
