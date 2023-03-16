package com.scascanner.studycafe.domain.repository;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.domain.entity.StudyCafe;
import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import com.scascanner.studycafe.domain.entity.reservation.ReservationStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.scascanner.studycafe.domain.entity.reservation.ReservationStatus.CANCELED;
import static com.scascanner.studycafe.domain.entity.reservation.ReservationStatus.RESERVED;
import static com.scascanner.studycafe.fixture.ReservationFixture.RESERVATION_AEGIS_SIX;
import static com.scascanner.studycafe.fixture.ReservationFixture.RESERVATION_TIMEU_FOUR;
import static com.scascanner.studycafe.fixture.RoomFixture.FOUR_TIMEU;
import static com.scascanner.studycafe.fixture.StudyCafeFixture.TIMEU;
import static com.scascanner.studycafe.fixture.UserFixture.GAEUN;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@SpringBootTest
class ReservationRepositoryTest {

    @Autowired ReservationRepository reservationRepository;

    @Test
    public void id로_user가_예약한_목록_조회() {
        //given
        User user = GAEUN.생성();
        StudyCafe studyCafe = TIMEU.생성();
        Room room = FOUR_TIMEU.생성();

        List<Reservation> reservations = List.of(
                RESERVATION_TIMEU_FOUR.사용자_예약상태_시간_생성(RESERVED, user, LocalTime.of(12, 0), LocalTime.of(14, 0)),
                RESERVATION_AEGIS_SIX.사용자_예약상태_시간_생성(RESERVED, user, LocalTime.of(14,0), LocalTime.of(17,0)),
                RESERVATION_TIMEU_FOUR.사용자_예약상태_시간_생성(CANCELED, user, LocalTime.of(12,0), LocalTime.of(14,0))
        );
        //when
        List<Reservation> findReservations = reservationRepository.findByUserId(user.getId());
        //then
        for (Reservation reservation : findReservations) {
            Assertions.assertThat(reservation).isIn(reservations);
        }
    }

    @Test
    public void 이미_예약이_되어있는_시간을_반환() {
        //given

        //when

        //then
    }


}