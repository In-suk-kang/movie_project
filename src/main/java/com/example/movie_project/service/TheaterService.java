package com.example.movie_project.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie_project.entity.TheaterEntity;
import com.example.movie_project.repository.TheaterRepository;

import java.util.List;

@Service
public class TheaterService {

    private final TheaterRepository theaterRepository;

    @Autowired
    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    public List<TheaterEntity> getAllTheaters() {
        return theaterRepository.findAll();
    }
}
