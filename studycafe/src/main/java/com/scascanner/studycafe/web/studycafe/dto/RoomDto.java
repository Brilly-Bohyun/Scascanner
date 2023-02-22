package com.scascanner.studycafe.web.studycafe.dto;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.domain.entity.StudyCafe;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class RoomDto {
    private Long id;

    private StudyCafe studyCafe;

    private Integer headCount;
    private Integer price;

    private static ModelMapper modelMapper = new ModelMapper();

    public Room createEntity() {
        return modelMapper.map(this, Room.class);
    }

    public static RoomDto mapToDto(Room room) {
        return modelMapper.map(room, RoomDto.class);
    }
}
