package com.scascanner.studycafe.web.studycafe.controller;

import com.scascanner.studycafe.domain.entity.StudyCafe;
import com.scascanner.studycafe.web.studycafe.dto.StudyCafeAddFormDto;
import com.scascanner.studycafe.web.studycafe.dto.StudyCafeViewDto;
import com.scascanner.studycafe.web.studycafe.dto.StudyCafeEditFormDto;
import com.scascanner.studycafe.web.studycafe.service.StudyCafeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController // 테스트용
@RequiredArgsConstructor
public class StudyCafeController {

    private final StudyCafeService studyCafeService;

    @GetMapping("/studycafe/new")
    public StudyCafeAddFormDto addForm(Model model) {
        StudyCafeAddFormDto studyCafeAddFormDto = new StudyCafeAddFormDto();
        model.addAttribute("form", studyCafeAddFormDto);

        return studyCafeAddFormDto;
//        return "/studycafe/create";
    }

    @PostMapping("/studycafe/new")
    public StudyCafe add(@Validated @RequestBody StudyCafeAddFormDto studyCafeAddFormDto) {
        StudyCafe studyCafe = studyCafeAddFormDto.toEntity();
        studyCafeService.addStudyCafe(studyCafe);

        return studyCafe;
//        return "/redirect:/";   // 스터디카페 등록 후 별도의 창을 띄울지는 나중에 결정
    }

    @PostMapping("/studycafe/{cafeId}/edit")
    public StudyCafeEditFormDto edit(@Validated @RequestBody StudyCafeEditFormDto studyCafeEditFormDto, @PathVariable Long cafeId) {

        studyCafeService.update(cafeId, studyCafeEditFormDto);
        return studyCafeEditFormDto;
    }

    /**
     * (의문점) DTO -> 엔티티 변환을 Service 에서 할지 Controller 에서 할지
     * (귀찮은 일은 컨트롤러가 도맡아 해야한다는 게 기억남...)
     * UserService 로부터 등록된 모든 스터디 카페를 가져와 모델을 통해 뷰로 넘겨준다.
     * @param model 등록된 모든 스터디카페 정보를 담고있는 리스트를 담을 모델
     * @return 뷰 논리 이름
     */
    @GetMapping("/studycafe")
    public List<StudyCafeViewDto> studyCafes(Model model) {
        List<StudyCafeViewDto> studyCafeViewDtos = studyCafeService.findStudyCafes().stream().map(StudyCafeViewDto::of)
                .collect(Collectors.toList());

        model.addAttribute("studyCafes", studyCafeViewDtos);
        return studyCafeViewDtos;
    }

    @GetMapping("/studycafe/{cafeId}")
    public StudyCafeViewDto studyCafe(@PathVariable Long cafeId, Model model) {

        StudyCafe studyCafe = studyCafeService.findById(cafeId);
        StudyCafeViewDto studyCafeViewDto = StudyCafeViewDto.of(studyCafe);

        model.addAttribute("studycafe", studyCafe);

        // 뷰에서 뭔가 정보를 깔끔하게 띄어주면 좋을 것 같음

        return studyCafeViewDto;
//        return "/studycafe/" + cafeId;  // 이거 더 편하게 하는 방법이 있었는데 기억 안남...
    }
}
