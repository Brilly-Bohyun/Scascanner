package com.scascanner.studycafe.domain.repository;

import com.scascanner.studycafe.domain.entity.StudyCafe;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalTime;
import java.util.List;

@Repository
public class StudyCafeRepository {

    @PersistenceContext
    private EntityManager em;

    public List<LocalTime> findStudyCafeOperationTime(Long studycafeId) {

        return em.createQuery("select s.openTime, s.closeTime from StudyCafe s where s.id = :id")
                .setParameter("id", studycafeId)
                .getResultList();

    }

    public StudyCafe findStudyCafeById(Long studyCafeId) {
        return em.find(StudyCafe.class, studyCafeId);
    }
}
