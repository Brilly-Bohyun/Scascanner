package com.scascanner.studycafe.fixture;

import com.scascanner.studycafe.domain.entity.Room;
import com.scascanner.studycafe.domain.entity.Room.RoomBuilder;
import com.scascanner.studycafe.domain.entity.StudyCafe;

public enum RoomFixture {

    ROOM_FOUR(4),
    ROOM_SIX(6);



    private final int headCount;

    RoomFixture(int headCount) {
        this.headCount = headCount;
    }

    public Room 생성(){
        return 기본_정보_생성().build();
    }

    public RoomBuilder 기본_정보_생성() {
        return Room.builder()
                .headCount(this.headCount);
    }

    public Room 스터디카페_생성(StudyCafe studyCafe) {
        return 기본_정보_생성()
                .studyCafe(studyCafe)
                .build();
    }

    public Room 스터디카페_생성(StudyCafe studyCafe, Long id){
        return 기본_정보_생성()
                .id(id)
                .studyCafe(studyCafe)
                .build();
    }

}
