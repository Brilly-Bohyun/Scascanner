package com.scascanner.studycafe.web.reservation.service;

import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import com.scascanner.studycafe.domain.repository.ReservationRepository;
import com.scascanner.studycafe.domain.repository.StudyCafeRepository;
import com.scascanner.studycafe.web.studycafe.service.StudyCafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StudyCafeService studyCafeService;

    private Map<Integer, Boolean> reservationTimes = new ConcurrentHashMap<>();

    public Map<Integer, Boolean> reservationTimeStatus(LocalDate targetDate, Long studyCafeId, Long roomId) {
        Map<String, LocalTime> operationTime = studyCafeService.findStudyCafeOperationTime(studyCafeId);
        for (int i = operationTime.get("openTime").getHour(); i < operationTime.get("closeTime").getHour(); i++) {
            reservationTimes.put(i, true);
        }

        List<Object[]> allPossibleReservation = reservationRepository.findAllImpossibleReservation(targetDate, studyCafeId, roomId);
        for (Object[] localDateTimes : allPossibleReservation) {
            for (int i = ((Time) localDateTimes[0]).toLocalTime().getHour(); i < ((Time) localDateTimes[1]).toLocalTime().getHour(); i++) {
                reservationTimes.put(i, false);
            }
        }

        return reservationTimes;
    }
    public void reserve(Reservation reservation) {
        reservationRepository.reserve(reservation);
    }

    public List<Reservation> findByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public void cancelReservation(Long reservationId) {
        reservationRepository.findByReservationId(reservationId).cancel();
    }

}
