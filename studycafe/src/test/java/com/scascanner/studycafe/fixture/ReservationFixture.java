package com.scascanner.studycafe.fixture;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.domain.entity.StudyCafe;
import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import com.scascanner.studycafe.domain.entity.reservation.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.scascanner.studycafe.domain.entity.reservation.Reservation.*;
import static com.scascanner.studycafe.fixture.RoomFixture.FOUR_TIMEU;
import static com.scascanner.studycafe.fixture.RoomFixture.SIX_AEGIS;
import static com.scascanner.studycafe.fixture.StudyCafeFixture.AEGIS;
import static com.scascanner.studycafe.fixture.StudyCafeFixture.TIMEU;

public enum ReservationFixture {

    RESERVATION_TIMEU_FOUR(TIMEU.생성(), FOUR_TIMEU.생성(), LocalDate.now()),
    RESERVATION_AEGIS_SIX(AEGIS.생성(), SIX_AEGIS.생성(), LocalDate.now());

    private final StudyCafe studyCafe;
    private final Room room;
    private final LocalDate reservationDate;

    ReservationFixture(StudyCafe studyCafe, Room room, LocalDate reservationDate) {
        this.studyCafe = studyCafe;
        this.room = room;
        this.reservationDate = reservationDate;
    }

    public Reservation 생성() {
        return 기본_정보_생성().build();
    }

    private ReservationBuilder 기본_정보_생성() {
        return Reservation.builder()
                .studyCafe(this.studyCafe)
                .room(this.room)
                .date(reservationDate);
    }

    public Reservation 사용자_예약상태_시간_생성(ReservationStatus reservationStatus, User user, LocalTime startTime, LocalTime endTime) {
        return 기본_정보_생성()
                .reservationStatus(reservationStatus)
                .user(user)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    
}
