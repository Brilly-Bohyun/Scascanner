package com.scascanner.studycafe.web.studycafe.controller;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.web.studycafe.service.RoomService;
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

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomController.class)
@WithMockUser(username = "테스트오너1", password = "12345678")
class RoomControllerTest {

    /**
     * 가짜 Mock 객체
     */
    @MockBean
    private RoomService roomService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[API][GET] 룸 단일 조회")
    public void 룸_단일_조회() throws Exception {

        // given

        long cafeId = 1L;
        long roomId = 1L;

        given(roomService.findOneInStudyCafe(any(), any())).willReturn(
                Room.builder()
                        .headCount(10)
                        .price(10000)
                        .build()
        );

        // when
        final ResultActions resultActions = mockMvc.perform(get("/studycafe/" + cafeId + "/room/" + roomId)
                ).andDo(print());


        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.headCount").value(10))
                .andExpect(jsonPath("$.price").value(10000));
    }

    @Test
    @DisplayName("[API][GET] 한 스터디 카페의 전체 룸 조회")
    public void 룸_전체_조회() throws Exception {

        // given
        given(roomService.findAllInStudyCafe(any())).willReturn(getRooms());

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/studycafe/1/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        ).andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].headCount").value(10))
                .andExpect(jsonPath("$.[0].price").value(10000))
                .andExpect(jsonPath("$.[1].headCount").value(20))
                .andExpect(jsonPath("$.[1].price").value(20000))
                .andExpect(jsonPath("$.[2].headCount").value(30))
                .andExpect(jsonPath("$.[2].price").value(30000));
    }

    @Test
    @DisplayName("[API][POST] 룸 정보 수정")
    public void 룸_정보_수정() throws Exception {

        // given
        String requestJson = "{\"headCount\":\"10\", \"price\": \"10000\"}";

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/studycafe/1/room/1/edit")
                        .with(csrf())   // Spring Security Test에서 403오류는 csrf때문에 발생한다고 한다.
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(requestJson)
                ).andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.headCount", is(10)).exists())
                .andExpect(jsonPath("$.price", is(10000)).exists());
    }

    private List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            rooms.add(
                    Room.builder()
                            .headCount(10 * i)
                            .price(10000 * i)
                            .build()
            );
        }
        return rooms;
    }
}