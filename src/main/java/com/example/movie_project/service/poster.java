package com.example.movie_project.service;

public class poster {
    private int count;
    private String image;
    private String name;

    public poster(int count,String image, String name) {
        this.count = count;
        this.image = image;
        this.name = name;
    }
    public poster(String image,String name)
    {
        this.image = image;
        this.name = name;
    }
}
