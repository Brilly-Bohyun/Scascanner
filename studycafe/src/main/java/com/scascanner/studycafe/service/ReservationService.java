package com.scascanner.studycafe.service;

import com.scascanner.studycafe.domain.Reservation;
import com.scascanner.studycafe.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public void reserve(Reservation reservation) {
        reservationRepository.reserve(reservation);
    }
}
