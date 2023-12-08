package com.example.movie_project.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class Theater {
    public List<String> movieList() {
        List<String> listMovie = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://www.cgv.co.kr/movies/?lt=1&ft=1").get();
            Elements data = doc.select("span.thumb-image img");
            for (Element element : data) {
                String movieName = element.attr("alt");
                movieName = movieName.replace("포스터", "");
                listMovie.add(movieName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listMovie; 
    }
    
}
