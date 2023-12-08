package com.example.movie_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.movie_project.entity.TheaterEntity;
import com.example.movie_project.entity.TheaterRegionEntity;
import com.example.movie_project.repository.TheaterRegionRepository;
import com.example.movie_project.repository.TheaterRepository;

@Component
public class Dataloader implements CommandLineRunner {

    private final TheaterRepository theaterRepository;
    private final TheaterRegionRepository theaterRegionRepository;

    @Autowired
    public Dataloader(TheaterRepository theaterRepository, TheaterRegionRepository theaterRegionRepository) {
        this.theaterRepository = theaterRepository;
        this.theaterRegionRepository = theaterRegionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 지역 생성
        TheaterRegionEntity seoulRegion = theaterRegionRepository.save(new TheaterRegionEntity("서울"));
        TheaterRegionEntity gyeonggiRegion = theaterRegionRepository.save(new TheaterRegionEntity("경기"));
        TheaterRegionEntity incheonRegion = theaterRegionRepository.save(new TheaterRegionEntity("인천"));

        // 서울 지역의 CGV 지점 추가
        theaterRepository.save(new TheaterEntity("고양스타필드", seoulRegion));
        theaterRepository.save(new TheaterEntity("광명AK플라자", seoulRegion));
        // ... (나머지 서울 지역 CGV 지점 추가)

        // 경기 지역의 CGV 지점 추가
        theaterRepository.save(new TheaterEntity("수원", gyeonggiRegion));
        theaterRepository.save(new TheaterEntity("용인테크노밸리", gyeonggiRegion));
        // ... (나머지 경기 지역 CGV 지점 추가)

        // 인천 지역의 CGV 지점 추가
        theaterRepository.save(new TheaterEntity("인천 지점1", incheonRegion));
        theaterRepository.save(new TheaterEntity("인천 지점2", incheonRegion));
        // ... (나머지 인천 지역 CGV 지점 추가)
    }
}
