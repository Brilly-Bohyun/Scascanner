package com.scascanner.studycafe.domain.repository;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.domain.entity.StudyCafe;
import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import com.scascanner.studycafe.fixture.ReservationFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.scascanner.studycafe.fixture.ReservationFixture.RESERVATION_CANCELED_19;
import static com.scascanner.studycafe.fixture.ReservationFixture.RESERVATION_RESERVED_13;
import static com.scascanner.studycafe.fixture.ReservationFixture.RESERVATION_RESERVED_15;
import static com.scascanner.studycafe.fixture.RoomFixture.ROOM_FOUR;
import static com.scascanner.studycafe.fixture.StudyCafeFixture.AEGIS;
import static com.scascanner.studycafe.fixture.UserFixture.GAEUN;

@SpringBootTest
@Transactional
class ReservationRepositoryTest {

    @Autowired ReservationRepository reservationRepository;

    @Test
    public void id로_user가_예약한_목록_조회() {
        //given
        User user = GAEUN.생성();

        List<Reservation> reservations = List.of(
                예약_저장(RESERVATION_RESERVED_13.사용자_예약_생성(user)),
                예약_저장(RESERVATION_RESERVED_15.사용자_예약_생성(user)),
                예약_저장(RESERVATION_CANCELED_19.사용자_예약_생성(user))
       );
        //when
        List<Reservation> findReservations = reservationRepository.findByUserId(user.getId());
        //then
        Assertions.assertThat(findReservations).hasSize(3);
        for (Reservation reservation : findReservations) {
            Assertions.assertThat(reservation).isIn(reservations);
        }
    }

    private Reservation 예약_저장(Reservation reservation) {
        reservationRepository.reserve(reservation);
        return reservation;
    }

    @Test
    public void 이미_예약이_되어있는_시간을_반환() {
        //given
        StudyCafe aegis = AEGIS.생성();
        Room room = ROOM_FOUR.스터디카페_생성(aegis);

        for (ReservationFixture fixture : ReservationFixture.values()) {
            예약_저장(fixture.스터디카페_룸_생성(aegis, room));

        }

        //when
        List<Object[]> allReservedReservation = reservationRepository.findAllReservedTime(LocalDate.now(), aegis.getId(), room.getId());
        //then
        Assertions.assertThat(allReservedReservation).hasSize(2);

        Assertions.assertThat(시간으로_변경(allReservedReservation.get(0)[0])).isEqualTo(13);
        Assertions.assertThat(시간으로_변경(allReservedReservation.get(0)[1])).isEqualTo(15);
        Assertions.assertThat(시간으로_변경(allReservedReservation.get(1)[0])).isEqualTo(15);
        Assertions.assertThat(시간으로_변경(allReservedReservation.get(1)[1])).isEqualTo(18);
    }

    public int 시간으로_변경(Object time) {
        return ((LocalTime) time).getHour();
    }


}