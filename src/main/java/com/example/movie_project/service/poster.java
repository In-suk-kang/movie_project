package com.example.movie_project.service;

import java.util.List;

public class poster {
    private int count;
    private String image;
    private String name;
    private String title;
    private String overview;
    private String posterPath;
    private String language;
    private String releasedDay;
    private List<String> originalNames;
    public int getCount()
    {
        return count;
    }
    public String getName()
    {
        return name;
    }
    public String getTitle()
    {
        return title;
    }
    public String getOverview()
    {
        return overview;
    }
    public String getPosterPath()
    {
        return posterPath;
    }
    public String getLanguage()
    {
        return language;
    }
    public String getReleasedDay()
    {
        return releasedDay;
    }
    public String getImage()
    {
        return image;
    }
    public List<String> getOriginalNames()
    {
        return originalNames;
    }
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
    public poster(String title, String overview, String posterPath, String language, String releasedDay, List<String> originalNames) {
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.language = language;
        this.releasedDay = releasedDay;
        this.originalNames = originalNames;
    }
}
