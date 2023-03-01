package com.scascanner.studycafe.web.studycafe.dto;

import com.scascanner.studycafe.domain.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomViewDto {

    private StudyCafeViewDto studyCafe;
    private Integer headCount;
    private Integer price;

    @Builder
    public RoomViewDto(StudyCafeViewDto studyCafeViewDto, Integer headCount, Integer price) {
        this.studyCafe = studyCafeViewDto;
        this.headCount = headCount;
        this.price = price;
    }

    public static RoomViewDto of(Room room) {
        return RoomViewDto.builder()
                .studyCafeViewDto(StudyCafeViewDto.of(room.getStudyCafe()))
                .headCount(room.getHeadCount())
                .price(room.getPrice())
                .build();
    }
}
