package com.scascanner.studycafe.domain.repository;

import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ReservationRepository {

    public EntityManager em;

    //날짜가 주어졌을 때 예약이 안되는 시간대를 반환
    public List<LocalDateTime[]> findAllImpossibleReservation(LocalDate targetDate) {
        return em.createQuery("select r.startTime, r.endTime from Reservation r where r.date = :targetDate")
                .setParameter("targetDate", targetDate)
                .getResultList();
    }
    
    public void reserve(Reservation reservation) {
        em.persist(reservation);
    }

}
