package com.scascanner.studycafe.web.studycafe.dto;

import com.scascanner.studycafe.domain.entity.StudyCafe;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class StudyCafeEditFormDto {

    @NotBlank(message = "스터디 카페 이름은 필수입니다.")
    private String name;

    @NotNull(message = "최소 사용시간은 필수입니다.")
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

    @Builder
    public StudyCafeEditFormDto(String name, Integer minUsingTime, LocalTime openTime, LocalTime closeTime, String address, String comment) {
        this.name = name;
        this.minUsingTime = minUsingTime;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.address = address;
        this.comment = comment;
    }

    public StudyCafe toEntity() {
        return StudyCafe.builder()
                .name(name)
                .minUsingTime(minUsingTime)
                .openTime(openTime)
                .closeTime(closeTime)
                .address(address)
                .comment(comment)
                .build();
    }
}
