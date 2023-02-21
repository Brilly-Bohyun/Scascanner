package com.scascanner.studycafe.domain.repository;

import com.scascanner.studycafe.domain.entity.Room;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoomRepository {

    @PersistenceContext
    private EntityManager em;

    public Room findRoomById(Long roomId) {
        return em.find(Room.class, roomId);
    }

}
