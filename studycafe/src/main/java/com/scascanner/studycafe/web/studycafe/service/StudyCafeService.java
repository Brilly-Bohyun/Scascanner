package com.scascanner.studycafe.web.studycafe.service;

import com.scascanner.studycafe.domain.entity.StudyCafe;
import com.scascanner.studycafe.domain.repository.StudyCafeRepository;
import com.scascanner.studycafe.web.studycafe.dto.StudyCafeEditFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@Transactional(readOnly = true) // 읽기 전용이면 영속성 컨텍스트가 스냅샷을 보관하지 않아 메모리 사용량을 최적화
@RequiredArgsConstructor
public class StudyCafeService {

    private final StudyCafeRepository studyCafeRepository;
    private Map<String, LocalTime> studyCafeOperationTime = new ConcurrentHashMap<>();

    public Map<String, LocalTime> findStudyCafeOperationTime(Long studyCafeId) {
        List<Object[]> studyCafeOperationTimeList = studyCafeRepository.findStudyCafeOperationTime(studyCafeId);
        studyCafeOperationTime.put("openTime", (LocalTime) studyCafeOperationTimeList.get(0)[0]);
        studyCafeOperationTime.put("closeTime", (LocalTime) studyCafeOperationTimeList.get(0)[1]);
        return studyCafeOperationTime;
    }

    /**
     * 스터디 카페 추가 메소드
     * @param studyCafe 추가할 스터디 카페 객체
     */
    @Transactional  // 쓰기를 해야하는 로직은 별도로 어노테이션을 달아준다 (readOnly 디폴트값이 False 이므로)
    public void addStudyCafe(StudyCafe studyCafe) {
        studyCafeRepository.save(studyCafe);
    }

    /**
     * 특정 스터디 카페의 내용들을 수정하는 메소드
     * 필터나 인터셉터 사용 가능성 있음
     * 필드 일부만 수정 가능하도록 추후 리펙토링
     * @param cafeId 특정 스터디 카페 Id
     * @param studyCafeEditFormDto 변경할 데이터
     */
    @Transactional  // 쓰기를 해야하는 로직은 별도로 어노테이션을 달아준다 (readOnly 디폴트값이 False 이므로)
    public void update(Long cafeId, StudyCafeEditFormDto studyCafeEditFormDto) {
        StudyCafe studyCafe = studyCafeRepository.findById(cafeId);
        studyCafe.update(studyCafeEditFormDto.getName(), studyCafeEditFormDto.getMinUsingTime()
        , studyCafeEditFormDto.getOpenTime(), studyCafeEditFormDto.getCloseTime(), studyCafeEditFormDto.getAddress()
        ,studyCafeEditFormDto.getComment());
    }

    /**
     * 모든 스터디 카페 목록을 조회하는 메소드
     * @return 모든 스터디 카페 목록 리스트
     */
    public List<StudyCafe> findStudyCafes() {
        return studyCafeRepository.findAll();
    }

    /**
     * Id 값을 통해 스터디 카페를 조회하는 메소드
     * @param cafeId 스터디 카페 고유 Id
     * @return 해당 Id 값의 스터디 카페
     */
    public StudyCafe findById(Long cafeId) {
        return studyCafeRepository.findById(cafeId);
    }
}
