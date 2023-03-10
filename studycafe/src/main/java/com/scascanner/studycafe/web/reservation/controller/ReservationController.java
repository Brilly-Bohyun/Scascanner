package com.scascanner.studycafe.web.reservation.controller;

import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import com.scascanner.studycafe.domain.entity.reservation.ReservationStatus;
import com.scascanner.studycafe.domain.entity.User;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final StudyCafeService studyCafeService;
    private final UserService userService;
    private final RoomService roomService;

    @GetMapping("/{studyCafeId}/{roomId}")//date가 2023-02-11의 형태로 올 경우
    public ReservationResponse impossibleReservationTimeList(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                             @PathVariable Long studyCafeId,
                                                             @PathVariable Long roomId) {

        Map<Integer, Boolean> reservationTimeStatus = reservationService.reservationTimeStatus(date, studyCafeId, roomId);

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

        return ReservationResponse.builder()
                .reservationTimeStatus(reservationTimeStatusList)
                .date(targetDate)
                .build();
    }

    @PostMapping("/{studyCafeId}/{roomId}")
    public ResponseEntity<?> reserve(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                     @PathVariable Long studyCafeId,
                                     @PathVariable Long roomId,
                                     @RequestParam @DateTimeFormat(pattern = "kk:mm:ss") LocalTime startTime,
                                     @RequestParam @DateTimeFormat(pattern = "kk:mm:ss") LocalTime endTime,
                                     @SessionAttribute(name = "loginUser") User user) {

        Reservation reservation = new Reservation(
                studyCafeService.findById(studyCafeId),
                userService.findById(user.getId()),
                roomService.findById(roomId),
                date, startTime, endTime, ReservationStatus.RESERVED);
        reservationService.reserve(reservation);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/reservation/details"));

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY); // "/"으로 redirect , 후에 예약 상세페이지를 만들면 예약 상세 페이지로 redirect하는 것으로 변경 !
    }

    @GetMapping("/details")
    public List<ReservationDto> userReservation( @SessionAttribute(name = "loginUser") User user) {
        List<Reservation> reservations = reservationService.findByUserId(user.getId());
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDtos.add(
                    ReservationDto.builder()
                            .studyCafeName(reservation.getStudyCafe().getName())
                            .roomHeadCount(reservation.getRoom().getHeadCount())
                            .date(reservation.getDate())
                            .startTime(reservation.getStartTime())
                            .endTime(reservation.getEndTime())
                            .reservationStatus(reservation.getReservationStatus())
                            .build());
        }
        return reservationDtos;
    }

    @PostMapping("/details/{reservationId}/cancel") //userId가 포함되어 있어야 하는가 .. ?
    public ResponseEntity<?> cancelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/reservation/details"));
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY); // "/"으로 redirect , 후에 예약 상세페이지를 만들면 예약 상세 페이지로 redirect하는 것으로 변경 !
    }

    @Getter
    @Builder
    static class MonthReservationResponse {
        private String month;
        private List<DayReservationStatus> dayReservationStatus;
    }

    static class DayReservationStatus { //날짜별 예약 가능 여부
        private String day;
        private boolean reservationStatus;
    }

    @Getter
    @Builder
    static class ReservationDto {
        private String studyCafeName;
        private Integer roomHeadCount;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private ReservationStatus reservationStatus;
    }

    /**
     * 예약 조회 응답 DTO
     */
    @Builder
    @Getter
    static class ReservationResponse {
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
