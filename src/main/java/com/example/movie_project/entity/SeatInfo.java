package com.example.movie_project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reserve_info")
public class SeatInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column
    private String selectedMovie;

    @Column
    private String selectRegion;
    @Column
    private String selectTheater;
    @Column
    private String selectDay;
    @Column
    private String selectTime;
    @Column
    private int selectedSeatsCount;
    @Column
    private int totalAmount;

}
