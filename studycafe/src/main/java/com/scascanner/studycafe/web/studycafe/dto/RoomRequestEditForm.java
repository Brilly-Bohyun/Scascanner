package com.scascanner.studycafe.web.studycafe.dto;

import com.scascanner.studycafe.domain.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class RoomRequestEditForm {

    @NotNull(message = "룸 이름은는 공백일 수 없습니다.")
    @Length(max = 10)
    private String name;

    @NotNull(message = "인원 수는 공백일 수 없습니다.")
    @Min(message = "인원수는 최소 1 이상이여야 합니다.", value = 1)
    private Integer headCount;

    @NotNull(message = "가격은 공백일 수 없습니다.")
    @Positive(message = "적절한 가격을 입력해주세요.")
    private Integer price;

    @Builder
    public RoomRequestEditForm(String name, Integer headCount, Integer price) {
        this.name = name;
        this.headCount = headCount;
        this.price = price;
    }

    public Room toEntity(){
        return Room.builder()
                .name(name)
                .headCount(headCount)
                .price(price)
                .build();
    }
}