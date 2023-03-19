package com.scascanner.studycafe.web.studycafe.controller;

import com.scascanner.studycafe.domain.entity.StudyCafe;
import com.scascanner.studycafe.web.studycafe.service.StudyCafeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudyCafeController.class)
@WithMockUser(username = "테스트오너1", password = "12345678")
class StudyCafeControllerTest {

    @MockBean
    private StudyCafeService studyCafeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[URL][GET] 스터디 카페 전체 조회")
    public void 스터디_카페_전체_조회() throws Exception {

        // given
        given(studyCafeService.findStudyCafes()).willReturn(getStudyCafes());

        // when
        ResultActions resultActions = mockMvc.perform(get("/studycafe/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());

        // then
        resultActions
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.[0].name").value("테스터1"))
                .andExpect(jsonPath("$.[0].minUsingTime").value("1"))
                .andExpect(jsonPath("$.[0].name").value("테스터2"))
                .andExpect(jsonPath("$.[0].minUsingTime").value("2"))
                .andExpect(jsonPath("$.[0].name").value("테스터3"))
                .andExpect(jsonPath("$.[0].minUsingTime").value("3"));
    }

    private List<StudyCafe> getStudyCafes() {
        List<StudyCafe> studyCafes = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            studyCafes.add(
                    StudyCafe.builder()
                            .name("테스터" + i)
                            .minUsingTime(i)
                            .build()
            );
        }
        return studyCafes;
    }
}