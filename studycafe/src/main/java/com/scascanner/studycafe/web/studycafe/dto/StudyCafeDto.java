package com.scascanner.studycafe.web.studycafe.dto;

import com.scascanner.studycafe.domain.entity.Owner;
import com.scascanner.studycafe.domain.entity.StudyCafe;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Getter
@Setter
public class StudyCafeDto {

    private Long id;

    private Owner owner;

    @NotBlank(message = "스터디 카페 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "최소 사용시간은 필수입니다.")
    @Min(value = 1, message = "최소 사용시간은 1시간 이상이여야 합니다.")
    private Integer minUsingTime;

    // LocalTime 바인딩 오류시 메시지 : 정확한 시간을 입력해주세요.
    private LocalTime openTime;
    private LocalTime closeTime;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    @NotBlank(message = "설명은 필수입니다.")
    @Size(max = 1000, message = "소개글은 최대 1000자까지 작성할 수 있습니다.")
    private String comment;

    private static ModelMapper modelMapper = new ModelMapper();

    public StudyCafe createEntity() {
        return modelMapper.map(this, StudyCafe.class);
    }

    public static StudyCafeDto mapToDto(StudyCafe studyCafe) {
        return modelMapper.map(studyCafe, StudyCafeDto.class);
    }
}
