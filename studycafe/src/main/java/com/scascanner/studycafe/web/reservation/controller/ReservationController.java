package com.scascanner.studycafe.web.reservation.controller;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ReservationController {

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
