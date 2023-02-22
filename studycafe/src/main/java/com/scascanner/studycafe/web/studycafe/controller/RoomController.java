package com.scascanner.studycafe.web.studycafe.controller;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.web.studycafe.dto.RoomDto;
import com.scascanner.studycafe.web.studycafe.dto.RoomEditDto;
import com.scascanner.studycafe.web.studycafe.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 스터디카페에서 상세 룸 조회를 할 때 사용할 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    /**
     * 특정 스터디 카페의 모든 룸을 조회하는 메소드
     * 컨트롤러에서 DTO로 변환
     * @param model
     * @param cafeId
     * @return
     */
    @GetMapping("/studycafe/{cafeId}/room")
    public List<Room> rooms(Model model, @PathVariable Long cafeId) {

        List<Room> rooms = roomService.findAllInStudyCafe(cafeId);
        model.addAttribute("rooms", rooms);

        return rooms;
    }

    @GetMapping("/studycafe/{cafeId}/rooms/{roomId}")
    public Room room(Model model, @PathVariable Long cafeId, @PathVariable Long roomId) {

        Room room = roomService.findOneInStudyCafe(cafeId, roomId);
        model.addAttribute("room", room);

        return room;
    }

    @GetMapping("/studycafe/{cafeId}/rooms/{roomId}/edit")
    public RoomEditDto editRoomForm(@PathVariable String cafeId, @PathVariable Long roomId, Model model) {

        RoomEditDto roomEditDto = new RoomEditDto();
        model.addAttribute("form", roomEditDto);
        return roomEditDto;

        // return 뷰 논리 이름
    }

    /**
     * 룸 수정
     * @param model
     * @param cafeId
     * @param roomId
     * @return
     */
    @PostMapping("/studycafe/{cafeId}/rooms/{roomId}/edit")
    public void edit(@ModelAttribute RoomEditDto roomEditDto, Model model, @PathVariable Long cafeId, @PathVariable Long roomId) {

        roomService.updateRoom(roomId, roomEditDto);

        // 리다이렉트 ("redirect:/studycafe/{cafeId}/rooms")
    }
}
