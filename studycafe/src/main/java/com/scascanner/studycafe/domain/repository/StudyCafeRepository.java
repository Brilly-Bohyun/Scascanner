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

    public List<Object[]> findStudyCafeOperationTime(Long studyCafeId) {

        return em.createQuery("select s.openTime, s.closeTime from StudyCafe s where s.id = :id")
                .setParameter("id", studyCafeId)
                .getResultList();

    }

    public void save(StudyCafe studyCafe) {

        if (studyCafe.getId() == null) {
            em.persist(studyCafe);  // DB에 없는 경우엔 id 값이 null
        } else {
            em.merge(studyCafe);    // 이미 DB에 등록되있는 경우
        }
    }

    public StudyCafe findById(Long cafeId) {
        return em.find(StudyCafe.class, cafeId);
    }

    public List<StudyCafe> findAll() {
        return em.createQuery("select sc from StudyCafe sc", StudyCafe.class).getResultList();
    }
}
