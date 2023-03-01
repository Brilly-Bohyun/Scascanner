package com.scascanner.studycafe.web.studycafe.dto;

import com.scascanner.studycafe.domain.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomViewDto {
    private Long id;
    private StudyCafeViewDto studyCafe;
    private Integer headCount;
    private Integer price;

    @Builder
    public RoomViewDto(Long id, StudyCafeViewDto studyCafeViewDto, Integer headCount, Integer price) {
        this.id = id;
        this.studyCafe = studyCafeViewDto;
        this.headCount = headCount;
        this.price = price;
    }

    public static RoomViewDto of(Room room) {
        return RoomViewDto.builder()
                .id(room.getId())
                .studyCafeViewDto(StudyCafeViewDto.of(room.getStudyCafe()))
                .headCount(room.getHeadCount())
                .price(room.getPrice())
                .build();
    }
}
