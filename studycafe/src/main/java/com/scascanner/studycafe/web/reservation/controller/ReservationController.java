package com.scascanner.studycafe.web.reservation.controller;

import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import com.scascanner.studycafe.domain.entity.reservation.ReservationStatus;
import com.scascanner.studycafe.domain.entity.User;
import com.scascanner.studycafe.web.login.service.UserService;
import com.scascanner.studycafe.web.reservation.dto.DayReservationStatus;
import com.scascanner.studycafe.web.reservation.dto.MonthReservationResponse;
import com.scascanner.studycafe.web.reservation.dto.ReservationResponse;
import com.scascanner.studycafe.web.reservation.dto.ReservationTimeStatus;
import com.scascanner.studycafe.web.reservation.dto.ReservationTimeStatusResponse;
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
    public ReservationTimeStatusResponse impossibleReservationTimeList(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                                       @PathVariable Long studyCafeId,
                                                                       @PathVariable Long roomId) {

        Map<Integer, Boolean> reservationTimeStatus = reservationService.reservationTimeStatus(date, studyCafeId, roomId);

        List<ReservationTimeStatus> reservationTimeStatusList = new ArrayList<>();
        reservationTimeStatus.entrySet().stream().
                forEach(entry -> reservationTimeStatusList.add(ReservationTimeStatus.of(entry.getKey(), reservationTimeStatus.get(entry.getKey()))));

        return ReservationTimeStatusResponse.of(reservationTimeStatusList, date);
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
    public List<ReservationResponse> userReservation(@SessionAttribute(name = "loginUser") User user) {
        List<Reservation> reservations = reservationService.findByUserId(user.getId());
        List<ReservationResponse> responses = new ArrayList<>();
        for (Reservation reservation : reservations) {
            responses.add(ReservationResponse.from(reservation));
        }
        return responses;
    }

    @PostMapping("/details/{reservationId}/cancel") //userId가 포함되어 있어야 하는가 .. ?
    public ResponseEntity<?> cancelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/reservation/details"));
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY); // "/"으로 redirect , 후에 예약 상세페이지를 만들면 예약 상세 페이지로 redirect하는 것으로 변경 !
    }

    @GetMapping("/{studyCafeId}/{roomId}")
    public MonthReservationResponse reservationStatusPerMonth(@PathVariable Long studyCafeId, @PathVariable Long roomId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM") LocalDate date) {
        Map<Integer, Boolean> reservationTimeStatusPerMonth = reservationService.reservationTimeStatusPerMonth(date.getYear(), date.getMonthValue(), studyCafeId, roomId);
        List<DayReservationStatus> dayReservationStatuses = new ArrayList<>();
        reservationTimeStatusPerMonth.entrySet().stream()
                .forEach(entry -> dayReservationStatuses.add(DayReservationStatus.of(entry.getKey(), reservationTimeStatusPerMonth.get(entry.getKey()))));
        return MonthReservationResponse.of(date.getMonthValue(), dayReservationStatuses);
    }


}
