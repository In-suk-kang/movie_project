package com.example.movie_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movie_project.entity.TheaterRegionEntity;

public interface TheaterRegionRepository extends JpaRepository<TheaterRegionEntity, Long> {
}
