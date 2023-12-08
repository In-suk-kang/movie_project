package com.example.movie_project.service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.io.IOException; // IOException을 임포트합니다.
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CrawlingService {
    public List<poster> getcrawl() {
        List<poster> list_poster = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://www.cgv.co.kr/movies/?lt=1&ft=1").get();
            Elements Data = doc.select("span.thumb-image img");
            int count = 1;
            for (Element DT : Data) {
                if (count >= 5) {
                    break;  
                }
                String movie_img = DT.attr("src");
                String movie_name = DT.attr("alt");
                poster p = new poster(count,movie_img, movie_name.replace("포스터", ""));
                list_poster.add(p);
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return list_poster; // 이미지 소스 리스트를 반환합니다.
    }
    
    public List<poster> getcrawd(String search_movie) {
        List<poster> list_poster = new ArrayList<poster>();
        try {

            String encodeResult = URLEncoder.encode(search_movie, "UTF-8");
            Document doc = Jsoup.connect("https://www.cgv.co.kr/search/?query="+encodeResult).get();
            Elements Data = doc.select(".searchingMovieResult_list a img");
            int count = 1;
            for (Element DT : Data) {
                String movie_img = DT.attr("src");
                String movie_name = DT.attr("alt");
                poster p = new poster(count,movie_img,movie_name);
                list_poster.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list_poster; // 이미지 소스 리스트를 반환합니다.
    }
    public List<poster> getcrawa() {
        List<poster> list_poster = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://www.cgv.co.kr/movies/?lt=1&ft=1").get();
            Elements Data = doc.select("span.thumb-image img");
            int rank = 1;
            for (Element DT : Data) {
                String movie_img = DT.attr("src");
                String movie_name = DT.attr("alt");
                poster p = new poster(rank,movie_img, movie_name.replace("포스터", ""));
                rank++;
                if(rank > 9)
                {
                    break;
                }
                list_poster.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list_poster; // 이미지 소스 리스트를 반환합니다.
    }
    public List<poster> getcraw_soon() {
        List<poster> list_poster = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://www.cgv.co.kr/movies/pre-movies.aspx").get();
            Elements Data = doc.select("span.thumb-image img");
            int count = 1;
            for (Element DT : Data) {
                if(count >=5)
                    break;
                String movie_img = DT.attr("src");
                String movie_name = DT.attr("alt");
                poster p = new poster(count,movie_img, movie_name.replace("포스터", ""));
                list_poster.add(p);
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return list_poster; // 이미지 소스 리스트를 반환합니다.
    }
    
}

