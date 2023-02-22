package com.scascanner.studycafe.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_cafe_id")
    private StudyCafe studyCafe;

    private Integer headCount;
    private Integer price;

    public Long getId() {
        return id;
    }

    public void update(Integer headCount, Integer price) {
        this.headCount = headCount;
        this.price = price;
    }

    @Builder
    public Room(Long id, StudyCafe studyCafe, Integer headCount, Integer price) {
        this.id = id;
        this.studyCafe = studyCafe;
        this.headCount = headCount;
        this.price = price;
    }
}
