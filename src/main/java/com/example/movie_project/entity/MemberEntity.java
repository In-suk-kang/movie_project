package com.example.movie_project.entity;
import lombok.AllArgsConstructor;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@AllArgsConstructor
@ToString
public class MemberEntity {
    @Id //고유 식별자 / 대표 값
    @GeneratedValue //자동 생성
    private Long id;
    @Column
    private String userid;
    @Column
    private String passwd;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private Date join_data;
}
