package com.scascanner.studycafe.domain.repository;

import com.scascanner.studycafe.domain.entity.reservation.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

import static com.scascanner.studycafe.domain.entity.reservation.ReservationStatus.RESERVED;

@Slf4j
@Repository
public class ReservationRepository {

    @PersistenceContext
    public EntityManager em;

    //날짜가 주어졌을 때 예약이 안되는 시간대를 반환
    public List<Object[]> findAllReservedTime(LocalDate targetDate, Long studyCafeId, Long roomId) {

        return em.createQuery("select r.startTime, r.endTime from Reservation r where r.date = :date and r.studyCafe.id = :studyCafeId and r.room.id = :roomId and r.reservationStatus = :status")
                .setParameter("date", targetDate)
                .setParameter("studyCafeId", studyCafeId)
                .setParameter("roomId", roomId)
                .setParameter("status", RESERVED)
                .getResultList();

    }
    
    public void reserve(Reservation reservation) {
        em.persist(reservation);
    }

    public List<Reservation> findByUserId(Long userId) {
        return em.createQuery("select r from Reservation r where r.user.id = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }

    public Reservation findByReservationId(Long reservationId) {
        return em.find(Reservation.class, reservationId);
    }

}
