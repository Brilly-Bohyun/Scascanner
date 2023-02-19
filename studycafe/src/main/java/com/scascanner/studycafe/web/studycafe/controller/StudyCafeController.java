package com.scascanner.studycafe.web.studycafe.controller;

import com.scascanner.studycafe.domain.entity.StudyCafe;
import com.scascanner.studycafe.web.studycafe.dto.StudyCafeDto;
import com.scascanner.studycafe.web.studycafe.service.StudyCafeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController // 테스트용
@RequiredArgsConstructor
public class StudyCafeController {

    private final StudyCafeService studyCafeService;

    @GetMapping("/studycafe/new")
    public String addForm(Model model) {
        model.addAttribute("form", new StudyCafeDto());
        return "ok";
//        return "/studycafe/create";
    }

    @PostMapping("/studycafe/new")
    public String add(StudyCafeDto studyCafeDto) {
        StudyCafe studyCafe = studyCafeDto.createDao();
        studyCafeService.addStudyCafe(studyCafe);

        return "ok";
//        return "/redirect:/";   // 스터디카페 등록 후 별도의 창을 띄울지는 나중에 결정
    }

    /**
     * (의문점) DTO -> 엔티티 변환을 Service 에서 할지 Controller 에서 할지
     * (귀찮은 일은 컨트롤러가 도맡아 해야한다는 게 기억남...)
     * UserService 로부터 등록된 모든 스터디 카페를 가져와 모델을 통해 뷰로 넘겨준다.
     * @param model 등록된 모든 스터디카페 정보를 담고있는 리스트를 담을 모델
     * @return 뷰 논리 이름
     */
    @GetMapping("/studycafe")
    public String studyCafes(Model model) {
        List<StudyCafeDto> studyCafes = studyCafeService.findStudyCafes();
        model.addAttribute("studyCafes", studyCafes);

        return "ok";
//        return "/studycafe";
    }

    @GetMapping("/studycafe/{cafeId}")
    public String studyCafe(@PathVariable Long cafeId, Model model) {

        StudyCafeDto studyCafe = studyCafeService.findOne(cafeId);
        model.addAttribute("studycafe", studyCafe);

        // 뷰에서 뭔가 정보를 깔끔하게 띄어주면 좋을 것 같음

        return "ok";
//        return "/studycafe/" + cafeId;  // 이거 더 편하게 하는 방법이 있었는데 기억 안남...
    }
}
