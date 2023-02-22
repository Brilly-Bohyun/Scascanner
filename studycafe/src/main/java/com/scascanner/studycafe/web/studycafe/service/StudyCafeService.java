package com.scascanner.studycafe.web.studycafe.service;

import com.scascanner.studycafe.domain.entity.StudyCafe;
import com.scascanner.studycafe.domain.repository.StudyCafeRepository;
import com.scascanner.studycafe.web.studycafe.dto.StudyCafeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Transactional(readOnly = true) // 읽기 전용이면 영속성 컨텍스트가 스냅샷을 보관하지 않아 메모리 사용량을 최적화
@RequiredArgsConstructor
public class StudyCafeService {

    private final StudyCafeRepository studyCafeRepository;
    private Map<String, LocalTime> studyCafeOperationTime = new ConcurrentHashMap<>();

    public Map<String, LocalTime> findStudyCafeOperationTime(Long studycafeId) {
        List<LocalTime> studyCafeOperationTimeList = studyCafeRepository.findStudyCafeOperationTime(studycafeId);
        studyCafeOperationTime.put("openTime", studyCafeOperationTimeList.get(0));
        studyCafeOperationTime.put("closeTime", studyCafeOperationTimeList.get(1));
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
     * 특정 스터디 카페의 최소 사용시간을 수정하는 메소드
     * 필터나 인터셉터 사용 가능성 있음
     * @param cafeId 특정 스터디 카페 Id
     * @param minUsingTime 변경할 최소 사용시간
     */
    @Transactional  // 쓰기를 해야하는 로직은 별도로 어노테이션을 달아준다 (readOnly 디폴트값이 False 이므로)
    public void updateMinUsingTime(Long cafeId, Integer minUsingTime) {
        StudyCafe studyCafe = studyCafeRepository.findById(cafeId);
        StudyCafeDto studyCafeDto = StudyCafeDto.mapToDto(studyCafe);

        studyCafeDto.setMinUsingTime(minUsingTime);
        // 업데이트 쿼리는 별도로 날려야 하는가?
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
