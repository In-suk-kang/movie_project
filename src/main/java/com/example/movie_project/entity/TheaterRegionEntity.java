package com.example.movie_project.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
@Table(name = "regions")
public class TheaterRegionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String regionName;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<TheaterEntity> theaters;
    public TheaterRegionEntity(){}
    // 기본 생성자와 getter, setter 등은 생략
    public TheaterRegionEntity(String regionName) {
        this.regionName = regionName;
    }
}