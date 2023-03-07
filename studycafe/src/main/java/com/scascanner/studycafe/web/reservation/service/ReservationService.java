package com.scascanner.studycafe.web.reservation.service;

import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import com.scascanner.studycafe.domain.repository.ReservationRepository;
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
    private Map<Integer, Boolean> reservationTimes = new ConcurrentHashMap<>();

    public Map<Integer, Boolean> reservationTimeStatus(LocalDate targetDate, LocalTime openTime, LocalTime closeTime, Long studyCafeId, Long roomId) {

        List<Object[]> allPossibleReservation = reservationRepository.findAllImpossibleReservation(targetDate, studyCafeId, roomId);
        for (int i = openTime.getHour(); i < closeTime.getHour(); i++) {
            reservationTimes.put(i, true);
        }
        for (Object[] localDateTimes : allPossibleReservation) {
            LocalTime reservation_start = ((Time) localDateTimes[0]).toLocalTime();
            LocalTime reservation_end = ((Time) localDateTimes[1]).toLocalTime();
            for (int i = reservation_start.getHour(); i < reservation_end.getHour(); i++) {
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
        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        reservation.cancel();
    }
}
