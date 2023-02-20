package com.scascanner.studycafe.web.reservation.controller;

import com.scascanner.studycafe.web.reservation.service.ReservationService;
import com.scascanner.studycafe.web.studycafe.service.StudyCafeService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    public List<ReservationTimeStatus> impossibleReservationTimeList(@PathVariable String date, @PathVariable Long studycafeId) {
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
        return reservationTimeStatusList;
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
