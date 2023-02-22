package com.scascanner.studycafe.web.studycafe.dto;

import com.scascanner.studycafe.domain.entity.Room;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class RoomEditDto {
    private Long id;

    @NotBlank(message = "인원 수는 공백일 수 없습니다.")
    @Min(message = "인원수는 최소 1 이상이여야 합니다.", value = 1)
    private Integer headCount;

    @NotBlank(message = "가격은 공백일 수 없습니다.")
    @Positive(message = "적절한 가격을 입력해주세요.")
    private Integer price;

    public Room toEntity(){
        return Room.builder()
                .id(id)
                .headCount(headCount)
                .price(price)
                .build();
    }
}