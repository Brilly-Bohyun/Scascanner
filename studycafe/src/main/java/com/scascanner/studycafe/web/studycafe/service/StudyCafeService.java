package com.scascanner.studycafe.web.studycafe.service;

import com.scascanner.studycafe.domain.repository.StudyCafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyCafeService {

    private final StudyCafeRepository studyCafeRepository;
    private Map<String, LocalTime> studyCafeOperationTime = new ConcurrentHashMap<>();
    
    public Map<String, LocalTime> findStudyCafeOperationTime(Long studycafeId) {
        List<LocalTime> studyCafeOperationTimeList = studyCafeRepository.findStudyCafeOperationTime(studycafeId);
        studyCafeOperationTime.put("openTime", studyCafeOperationTime.get(0));
        studyCafeOperationTime.put("closeTime", studyCafeOperationTime.get(1));
        return studyCafeOperationTime;
    }
}
