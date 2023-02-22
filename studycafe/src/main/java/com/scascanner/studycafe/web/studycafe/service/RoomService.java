package com.scascanner.studycafe.web.studycafe.service;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.domain.repository.RoomRepository;
import com.scascanner.studycafe.web.studycafe.dto.RoomEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public void updateRoom(Long roomId, RoomEditDto roomEditDto) {

        Room room = roomRepository.findOne(roomId);
        room.update(roomEditDto.getHeadCount(), roomEditDto.getPrice());
    }

    public Room findOne(Long roomId) {
        return roomRepository.findOne(roomId);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public List<Room> findAllInStudyCafe(Long cafeId) {
        return roomRepository.findAllByStudyCafeId(cafeId);
    }

    public Room findOneInStudyCafe(Long cafeId, Long roomId) {
        return roomRepository.findOneByStudyCafeId(cafeId, roomId);
    }
}
