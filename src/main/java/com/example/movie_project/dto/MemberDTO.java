package com.example.movie_project.dto;
import com.example.movie_project.entity.MemberEntity;
import lombok.AllArgsConstructor;

import java.util.Date;
@AllArgsConstructor
public class MemberDTO {

    private String userid;
    private String passwd;
    private String name;
    private String email;
    private Date join_data;
    public MemberEntity toEntity()
    {
        return new MemberEntity(null,userid,passwd,name,email,join_data);
    }
}

