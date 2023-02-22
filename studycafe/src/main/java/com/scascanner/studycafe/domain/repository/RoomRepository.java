package com.scascanner.studycafe.domain.repository;

import com.scascanner.studycafe.domain.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class RoomRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Room room) {

        if (room.getId() == null) {
            em.persist(room);
        } else {
            em.merge(room);
        }
    }

    /**
     * 룸 정보 수정 시 해당 룸 엔티티를 찾기 위한 메소드
     * 꼭 스터디카페 내에서 조회하는 경우에만 룸 엔티티를 DB에서 조회하지 않는다는 것을 주의
     * @param roomId
     * @return
     */
    public Room findOne(Long roomId) {
        return em.find(Room.class, roomId);
    }

    public Room findOneByStudyCafeId(Long cafeId, Long roomId) {
        return em.createQuery("select r from Room r where r.studyCafe.id =: cafeId and r.id =: roomId", Room.class)
                .setParameter("cafeId", cafeId)
                .setParameter("roomId", roomId)
                .getSingleResult();
    }

    public List<Room> findAllByStudyCafeId(Long cafeId) {
        return em.createQuery("select r from Room r where r.studyCafe.id = :cafeId", Room.class)
                .setParameter("cafeId", cafeId)
                .getResultList();
    }

    public List<Room> findAll() {
        return em.createQuery("select r from Room r", Room.class).getResultList();
    }
}
