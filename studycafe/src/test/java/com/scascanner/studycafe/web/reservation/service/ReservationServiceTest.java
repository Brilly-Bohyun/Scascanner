package com.scascanner.studycafe.web.reservation.service;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.domain.entity.StudyCafe;
import com.scascanner.studycafe.domain.repository.ReservationRepository;
import com.scascanner.studycafe.web.studycafe.service.StudyCafeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static com.scascanner.studycafe.fixture.RoomFixture.ROOM_FOUR;
import static com.scascanner.studycafe.fixture.StudyCafeFixture.TIMEU;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    StudyCafeService studyCafeService;

    @InjectMocks
    ReservationService reservationService;

    @Test
    public void 시간별로_예약이_가능한지_확인(){
        //given
        StudyCafe studyCafe = TIMEU.생성(1L);
        Room room = ROOM_FOUR.스터디카페_생성(studyCafe);
        Object[] reservedTime1 = {LocalTime.of(13,0), LocalTime.of(15,0)};
        Object[] reservedTime2 = {LocalTime.of(16,0), LocalTime.of(19,0)};
        doReturn((List<Object[]>) List.of(reservedTime1, reservedTime2))
                .when(reservationRepository).findAllReservedTime(any(LocalDate.class), anyLong(), anyLong());

        doReturn(Map.of("openTime", LocalTime.of(10, 0), "closeTime", LocalTime.of(23, 0)))
                .when(studyCafeService).findOperationTime(anyLong());
        Map<Integer, Boolean> reservationTimeStatus = reservationService.reservationTimeStatus(LocalDate.now(), studyCafe.getId(), 1L);
        //when

        //then
        Assertions.assertThat(reservationTimeStatus.size()).isEqualTo(13);
        Assertions.assertThat(reservationTimeStatus.get(13)).isFalse();
        Assertions.assertThat(reservationTimeStatus.get(11)).isTrue();
    }


}