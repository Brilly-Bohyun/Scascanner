package com.scascanner.studycafe.web.studycafe.controller;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.web.studycafe.dto.RoomViewDto;
import com.scascanner.studycafe.web.studycafe.dto.RoomRequestEditForm;
import com.scascanner.studycafe.web.studycafe.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 스터디카페에서 상세 룸 조회를 할 때 사용할 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/studycafe/{cafeId}/room")
public class RoomController {

    private final RoomService roomService;

    /**
     * 특정 스터디 카페의 모든 룸을 조회하는 메소드
     * 컨트롤러에서 DTO로 변환
     * @param model
     * @param cafeId
     * @return
     */
    @GetMapping
    public List<RoomViewDto> rooms(Model model, @PathVariable Long cafeId) {

        List<RoomViewDto> rooms = roomService.findAllInStudyCafe(cafeId).stream()
                .map(RoomViewDto::of).collect(Collectors.toList());

        model.addAttribute("rooms", rooms);

        return rooms;
    }

    @GetMapping("/{roomId}")
    public RoomViewDto room(Model model, @PathVariable Long cafeId, @PathVariable Long roomId) {

        Room room = roomService.findOneInStudyCafe(cafeId, roomId);
        RoomViewDto roomViewDto = RoomViewDto.of(room);

        model.addAttribute("room", roomViewDto);

        return roomViewDto;
    }

    /*
    @GetMapping("/studycafe/{cafeId}/room/{roomId}/")
    public RoomRequestEditForm editRoomForm(@PathVariable String cafeId, @PathVariable Long roomId, Model model) {

        RoomRequestEditForm roomRequestEditForm = new RoomRequestEditForm();
        model.addAttribute("form", roomRequestEditForm);

        return roomRequestEditForm;
        // return 뷰 논리 이름
    }
     */

    /**
     * 룸 수정
     * @param cafeId
     * @param roomId
     * @return
     */
    @PatchMapping("/{roomId}")
    public ResponseEntity<?> edit(@Validated @RequestBody RoomRequestEditForm roomRequestEditForm, @PathVariable Long cafeId, @PathVariable Long roomId) {

        roomService.updateRoom(roomId, roomRequestEditForm);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/studycafe/" + cafeId + "/room/" + roomId));

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }
}