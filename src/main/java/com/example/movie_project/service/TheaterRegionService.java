package com.example.movie_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie_project.entity.TheaterRegionEntity;
import com.example.movie_project.repository.TheaterRegionRepository;

import java.util.List;

@Service
public class TheaterRegionService {

    private final TheaterRegionRepository theaterRegionRepository;

    @Autowired
    public TheaterRegionService(TheaterRegionRepository theaterRegionRepository) {
        this.theaterRegionRepository = theaterRegionRepository;
    }

    public List<TheaterRegionEntity> getAllTheaterRegions() {
        return theaterRegionRepository.findAll();
    }
}
