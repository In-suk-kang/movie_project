package com.example.movie_project.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "theaters")
public class TheaterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String theaterName;

    @ManyToOne
    private TheaterRegionEntity region;

    // 기본 생성자와 getter, setter 등은 생략
    public TheaterEntity(){}
    public TheaterEntity(String theaterName, TheaterRegionEntity region) {
        this.theaterName = theaterName;
        this.region = region;
    }
}
