package com.scascanner.studycafe.web.pay.request.dto;

import com.google.gson.annotations.SerializedName;
import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import com.scascanner.studycafe.domain.entity.reservation.ReservationStatus;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservationRequestPay {

    private Long id;
    @SerializedName("studycafe")
    private StudyCafeRequestPay studyCafeRequestPay;
    @SerializedName("user")
    private UserRequestPay userRequestPay;
    @SerializedName("room")
    private RoomRequestPay roomRequestPay;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private ReservationStatus reservationStatus;

    @Builder
    public ReservationRequestPay(Long id, StudyCafeRequestPay studyCafeRequestPay, UserRequestPay userRequestPay, RoomRequestPay roomRequestPay, LocalDate date, LocalTime startTime, LocalTime endTime, ReservationStatus reservationStatus) {
        this.id = id;
        this.studyCafeRequestPay = studyCafeRequestPay;
        this.userRequestPay = userRequestPay;
        this.roomRequestPay = roomRequestPay;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservationStatus = reservationStatus;
    }

    public static ReservationRequestPay of(Reservation reservation) {

        return ReservationRequestPay.builder()
                .id(reservation.getId())
                .studyCafeRequestPay(StudyCafeRequestPay.of(reservation.getStudyCafe()))
                .userRequestPay(UserRequestPay.of(reservation.getUser()))
                .roomRequestPay(RoomRequestPay.of(reservation.getRoom()))
                .date(reservation.getDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .reservationStatus(reservation.getReservationStatus())
                .build();
    }
}