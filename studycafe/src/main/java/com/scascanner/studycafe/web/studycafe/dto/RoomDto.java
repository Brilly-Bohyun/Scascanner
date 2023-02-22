package com.scascanner.studycafe.web.studycafe.dto;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.domain.entity.StudyCafe;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
    private Long id;

    private StudyCafe studyCafe;

    private Integer headCount;
    private Integer price;

    @Builder
    public RoomDto(Long id, StudyCafe studyCafe, Integer headCount, Integer price) {
        this.id = id;
        this.studyCafe = studyCafe;
        this.headCount = headCount;
        this.price = price;
    }

    public Room toEntity(){
        return Room.builder()
                .id(id)
                .studyCafe(studyCafe)
                .headCount(headCount)
                .price(price)
                .build();
    }
}
