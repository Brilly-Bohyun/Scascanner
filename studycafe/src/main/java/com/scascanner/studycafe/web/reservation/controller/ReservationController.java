package com.scascanner.studycafe.web.reservation.controller;

import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import com.scascanner.studycafe.web.reservation.service.ReservationService;
import com.scascanner.studycafe.web.studycafe.service.StudyCafeService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final StudyCafeService studyCafeService;

    @GetMapping("/reservation/{date}/{studycafeId}")//date가 20230211의 형태로 올 경우
    public GetReservationResponse impossibleReservationTimeList(@PathVariable String date, @PathVariable Long studycafeId) {
        LocalDate findTargetDate = LocalDate.of(
                Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(6, 8)));

        Map<String, LocalTime> studyCafeOperationTime = studyCafeService.findStudyCafeOperationTime(studycafeId);
        Map<Integer, Boolean> reservationTimeStatus = reservationService.reservationTimeStatus(findTargetDate, studyCafeOperationTime.get("openTime"), studyCafeOperationTime.get("closeTime"));

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
                .year(findTargetDate.getYear())
                .month(findTargetDate.getMonthValue())
                .day(findTargetDate.getDayOfMonth())
                .build();

        return GetReservationResponse.builder()
                .reservationTimeStatus(reservationTimeStatusList)
                .date(targetDate)
                .build();
    }

    @PostMapping("/reservation")
    public ResponseEntity<?> reserve(Reservation reservation) {
        reservationService.reserve(reservation);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/"));

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY); // "/"으로 redirect , 후에 예약 상세페이지를 만들면 예약 상세 페이지로 redirect하는 것으로 변경 !
    }


    /**
     * 예약 조회 응답 DTO
     */
    @Builder
    static class GetReservationResponse{
        private Date date;
        private List<ReservationTimeStatus> reservationTimeStatus;
    }

    /**
     * 넘겨줄 날짜 형식
     */
    @Builder
    static class Date{
        private int year;
        private int month;
        private int day;
    }

    /**
     * 시간별 예약 가능 여부
     */
    @Builder
    static class ReservationTimeStatus {
        private Integer start;
        private Integer end;
        private boolean reservationStatus;
    }

}
