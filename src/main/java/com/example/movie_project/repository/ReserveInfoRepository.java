package com.example.movie_project.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movie_project.entity.SeatInfo;

public interface ReserveInfoRepository extends JpaRepository<SeatInfo, Long> {
    
}
