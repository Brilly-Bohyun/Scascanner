package com.scascanner.studycafe.web;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.domain.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room findRoomById(Long roomId) {
        return roomRepository.findRoomById(roomId);
    }

}
