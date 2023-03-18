package com.scascanner.studycafe.web.pay.request.dto;

import com.scascanner.studycafe.domain.entity.Room;
import lombok.Builder;
import lombok.Data;

@Data
public class RoomRequestPay {

    private Long id;
    private String name;
    private Integer headCount;
    private Integer price;

    @Builder
    public RoomRequestPay(Long id, String name, Integer headCount, Integer price) {
        this.id = id;
        this.name = name;
        this.headCount = headCount;
        this.price = price;
    }

    public static RoomRequestPay of(Room room) {

        return RoomRequestPay.builder()
                .id(room.getId())
                .name(room.getName())
                .headCount(room.getHeadCount())
                .price(room.getPrice())
                .build();
    }
}
