package com.scascanner.studycafe.web.studycafe.controller;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.domain.entity.StudyCafe;
import com.scascanner.studycafe.web.studycafe.dto.RoomDto;
import com.scascanner.studycafe.web.studycafe.service.RoomService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 스터디카페에서 상세 룸 조회를 할 때 사용할 컨트롤러
 */
@Controller
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
    public List<RoomDto> rooms(Model model, @PathVariable Long cafeId) {

        List<RoomDto> rooms = roomService.findAllInStudyCafe(cafeId).stream().map(RoomDto::mapToDto).collect(Collectors.toList());
        model.addAttribute("rooms", rooms);

        return rooms;
    }

    @GetMapping("/studycafe/{cafeId}/room/{roomId}")
    public RoomDto room(Model model, @PathVariable Long cafeId, @PathVariable Long roomId) {

        RoomDto room = RoomDto.mapToDto(roomService.findOneInStudyCafe(cafeId, roomId));
        model.addAttribute("room", room);

        return room;
    }

    /**
     * 룸 수정
     * @param model
     * @param cafeId
     * @param roomId
     * @return
     */
    @PostMapping("/studycafe/{cafeId}/room/{roomId}/edit")
    public RoomEditDto edit(Model model, @PathVariable Long cafeId, @PathVariable Long roomId) {


    }

    @Getter
    @Setter
    static class RoomEditDto {
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "study_cafe_id")
        private StudyCafe studyCafe;

        @NotBlank(message = "인원 수는 공백일 수 없습니다.")
        @Min(message = "인원수는 최소 1 이상이여야 합니다.", value = 1)
        private Integer headCount;

        @NotBlank(message = "가격은 공백일 수 없습니다.")
        @Positive(message = "적절한 가격을 입력해주세요.")
        private Integer price;

        private static ModelMapper modelMapper = new ModelMapper();

        public Room createEntity() {
            return modelMapper.map(this, Room.class);
        }

        public static RoomDto mapToDto(Room room) {
            return modelMapper.map(room, RoomDto.class);
        }
    }
}
