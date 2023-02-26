package com.scascanner.studycafe.web.studycafe.service;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.domain.repository.RoomRepository;
import com.scascanner.studycafe.web.studycafe.dto.RoomEditFormDto;
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
    public void updateRoom(Long roomId, RoomEditFormDto roomEditFormDto) {

        Room room = roomRepository.findById(roomId);
        room.update(roomEditFormDto.getHeadCount(), roomEditFormDto.getPrice());
    }

    public Room findById(Long roomId) {
        return roomRepository.findById(roomId);
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
