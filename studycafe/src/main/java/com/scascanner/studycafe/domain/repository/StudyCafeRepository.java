package com.scascanner.studycafe.domain.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalTime;
import java.util.List;

@Repository
public class StudyCafeRepository {

    EntityManager em;

    public List<LocalTime> findStudyCafeOperationTime(Long studycafeId) {

        return em.createQuery("select s.openTime, s.closeTime from StudyCafe s where s.id = :id")
                .setParameter("id", studycafeId)
                .getResultList();

    }
}
