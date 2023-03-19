package com.scascanner.studycafe.fixture;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.domain.entity.StudyCafe;
import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import com.scascanner.studycafe.domain.entity.reservation.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.scascanner.studycafe.domain.entity.reservation.Reservation.*;
import static com.scascanner.studycafe.domain.entity.reservation.ReservationStatus.CANCELED;
import static com.scascanner.studycafe.domain.entity.reservation.ReservationStatus.RESERVED;


public enum ReservationFixture {

    RESERVATION_RESERVED_13(LocalDate.now(), LocalTime.of(13, 0), LocalTime.of(15, 0), RESERVED),
    RESERVATION_RESERVED_15(LocalDate.now(), LocalTime.of(15, 0), LocalTime.of(18, 0), RESERVED),
    RESERVATION_CANCELED_19(LocalDate.now(), LocalTime.of(19, 0), LocalTime.of(23, 0), CANCELED),
    RESERVATION_RESERVED_FULL(LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(23, 0), RESERVED),
    ;

    private final LocalDate reservationDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final ReservationStatus reservationStatus;

    ReservationFixture(LocalDate reservationDate, LocalTime startTime, LocalTime endTime, ReservationStatus reservationStatus) {
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservationStatus = reservationStatus;
    }

    public Reservation 생성() {
        return 기본_정보_생성().build();
    }

    public Reservation 생성(Long id) {
        return 기본_정보_생성()
                .id(id)
                .build();
    }

    private ReservationBuilder 기본_정보_생성() {
        return Reservation.builder()
                .startTime(this.startTime)
                .endTime(this.endTime)
                .reservationStatus(this.reservationStatus)
                .date(reservationDate);
    }

    public Reservation 사용자_예약_생성(User user) {
        return 기본_정보_생성()
                .user(user)
                .build();
    }

    public Reservation 스터디카페_룸_생성(StudyCafe studyCafe, Room room) {
        return 기본_정보_생성()
                .studyCafe(studyCafe)
                .room(room)
                .build();
    }

    public Reservation 스터디카페_룸_날짜_생성(StudyCafe studyCafe, Room room, LocalDate date) {
        return Reservation.builder()
                .startTime(this.startTime)
                .endTime(this.endTime)
                .reservationStatus(this.reservationStatus)
                .date(date)
                .studyCafe(studyCafe)
                .room(room)
                .build();
    }
}
