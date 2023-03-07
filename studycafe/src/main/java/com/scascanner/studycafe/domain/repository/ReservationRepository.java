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
    public List<Object[]> findAllImpossibleReservation(LocalDate targetDate, Long studyCafeId, Long roomId) {
        return em.createNativeQuery("select r.start_time, r.end_time from reservation r where r.date = ? and r.study_cafe_id = ? and r.room_id = ? and r.reservation_status = ?")
                .setParameter(1, targetDate)
                .setParameter(2, studyCafeId)
                .setParameter(3, roomId)
                .setParameter(4, RESERVED)
                .getResultList();
    }
    
    public void reserve(Reservation reservation) {
        em.persist(reservation);
    }

    public List<Reservation> findByUserId(Long userId) {
        return em.createQuery("select r from Reservation r join r.user u on u.id = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }

    public Reservation findByReservationId(Long reservationId) {
        return em.find(Reservation.class, reservationId);
    }

}
