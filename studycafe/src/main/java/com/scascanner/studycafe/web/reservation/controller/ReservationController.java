package com.scascanner.studycafe.web.reservation.controller;

import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import com.scascanner.studycafe.domain.entity.reservation.ReservationStatus;
import com.scascanner.studycafe.web.login.service.UserService;
import com.scascanner.studycafe.web.reservation.service.ReservationService;
import com.scascanner.studycafe.web.studycafe.service.RoomService;
import com.scascanner.studycafe.web.studycafe.service.StudyCafeService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final StudyCafeService studyCafeService;
    private final UserService userService;
    private final RoomService roomService;

    @GetMapping//date가 2023-02-11의 형태로 올 경우
    public GetReservationResponse impossibleReservationTimeList(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                                @RequestParam Long studyCafeId,
                                                                @RequestParam Long roomId) {

        Map<String, LocalTime> studyCafeOperationTime = studyCafeService.findStudyCafeOperationTime(studyCafeId);
        Map<Integer, Boolean> reservationTimeStatus = reservationService.reservationTimeStatus(date, studyCafeOperationTime.get("openTime"),
                studyCafeOperationTime.get("closeTime"),
                studyCafeId, roomId);

        List<ReservationTimeStatus> reservationTimeStatusList = new ArrayList<>();

        for (Integer key : reservationTimeStatus.keySet()) {
            reservationTimeStatusList.add(
                    ReservationTimeStatus.builder()
                            .start(key)
                            .end(key+1)
                            .reservationStatus(reservationTimeStatus.get(key))
                            .build());

        }

        Date targetDate = Date.builder()
                .year(date.getYear())
                .month(date.getMonthValue())
                .day(date.getDayOfMonth())
                .build();

        return GetReservationResponse.builder()
                .reservationTimeStatus(reservationTimeStatusList)
                .date(targetDate)
                .build();
    }

    @PostMapping
    public ResponseEntity<?> reserve(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                     @RequestParam Long studyCafeId,
                                     @RequestParam Long roomId,
                                     @RequestParam @DateTimeFormat(pattern = "kk:mm:ss") LocalTime startTime,
                                     @RequestParam @DateTimeFormat(pattern = "kk:mm:ss") LocalTime endTime,
                                     @CookieValue(name = "memberId", required = true) Long userId) {

        Reservation reservation = new Reservation(
                studyCafeService.findById(studyCafeId),
                userService.findById(userId),
                roomService.findById(roomId),
                date, startTime, endTime, ReservationStatus.RESERVED);
        reservationService.reserve(reservation);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/"));

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY); // "/"으로 redirect , 후에 예약 상세페이지를 만들면 예약 상세 페이지로 redirect하는 것으로 변경 !
    }

    /**
     * 예약 조회 응답 DTO
     */
    @Builder
    @Getter
    static class GetReservationResponse{
        private Date date;
        private List<ReservationTimeStatus> reservationTimeStatus;
    }

    /**
     * 넘겨줄 날짜 형식
     */
    @Builder
    @Getter
    static class Date{
        private int year;
        private int month;
        private int day;
    }

    /**
     * 시간별 예약 가능 여부
     */
    @Builder
    @Getter
    static class ReservationTimeStatus {
        private Integer start;
        private Integer end;
        private boolean reservationStatus;
    }

}
