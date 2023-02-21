package com.scascanner.studycafe.domain.repository;

import com.scascanner.studycafe.domain.entity.StudyCafe;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.scascanner.studycafe.domain.entity.User;

@Repository
public class UserRepository {

    @PersistenceContext // EntityManager를 빈으로 주입 -> 동시성 이슈 방지
    private EntityManager em;

    public User getUserById(Long userId) {
        return em.find(User.class, userId);
    }

}
