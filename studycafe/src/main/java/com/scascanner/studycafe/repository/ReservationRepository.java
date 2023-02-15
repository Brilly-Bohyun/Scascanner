package com.scascanner.studycafe.repository;

import com.scascanner.studycafe.domain.Reservation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ReservationRepository {

    public EntityManager em;

    public void reserve(Reservation reservation) {
        em.persist(reservation);
    }
}
