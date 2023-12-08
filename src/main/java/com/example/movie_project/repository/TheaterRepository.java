package com.example.movie_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movie_project.entity.TheaterEntity;

public interface TheaterRepository extends JpaRepository<TheaterEntity, Long> {
}
