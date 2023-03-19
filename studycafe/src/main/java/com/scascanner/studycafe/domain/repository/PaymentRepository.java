package com.scascanner.studycafe.domain.repository;

import com.scascanner.studycafe.domain.entity.payment.Payment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional(readOnly = true)
@Repository
public class PaymentRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Payment payment) {
        if (payment.getId() == null) {
            em.persist(payment);
        } else {
            em.merge(payment);
        }
    }

    /**
     * 유저의 총 결제 정보 조회
     * @param userId
     * @return
     */
    public List<Payment> findAllByUserId(String userId) {
        return em.createQuery("select p from Payment p where p.reservation.user.id =: userId", Payment.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    /**
     * 예약 ID를 통한 결제 정보 조회
     * @param reservationId 예약 id
     * @return
     */
    public Payment findOneByReservationId(String reservationId) {
        return em.createQuery("select p from Payment p where p.reservation =: reservationId", Payment.class)
                .setParameter("reservationId", reservationId)
                .getSingleResult();
    }

    /**
     * 룸의 전체 결제 정보 조회
     * @param roomId
     * @return
     */
    public List<Payment> findAllByRoomId(String roomId) {
        return em.createQuery("select p from Payment p where p.reservation.room.id =: roomId", Payment.class)
                .setParameter("roomId", roomId)
                .getResultList();
    }
}
