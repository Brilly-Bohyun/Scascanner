package com.scascanner.studycafe.web.service;

import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import com.scascanner.studycafe.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private Map<Integer, Boolean> reservationTimes = new ConcurrentHashMap<>();

    public Map<Integer, Boolean> reservationTimeStatus(LocalDate targetDate, LocalTime openTime, LocalTime closeTime) {
        List<LocalDateTime[]> allPossibleReservation = reservationRepository.findAllImpossibleReservation(targetDate);

        for (int i = openTime.getHour(); i < closeTime.getHour(); i++) {
            reservationTimes.put(i, true);
        }

        for (LocalDateTime[] localDateTimes : allPossibleReservation) {
            for (int i = localDateTimes[0].getHour(); i < localDateTimes[1].getHour(); i++) {
                reservationTimes.put(i, false);
            }
        }

        return reservationTimes;
    }
    public void reserve(Reservation reservation) {
        reservationRepository.reserve(reservation);
    }

}
