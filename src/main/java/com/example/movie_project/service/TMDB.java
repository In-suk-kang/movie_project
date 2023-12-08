package com.example.movie_project.service;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class TMDB {
    private final OkHttpClient client;
    private final JSONParser jsonParser;

    public TMDB() {
        this.client = new OkHttpClient();
        this.jsonParser = new JSONParser();
    }

    public String movie_code(String movie_name) throws IOException, ParseException {
        String searchTerm = URLEncoder.encode(movie_name, "UTF-8");
        String apiUrl = String.format("https://api.themoviedb.org/3/search/movie?query=%s&include_adult=false&page=1", searchTerm);
        Request request = new Request.Builder()
                .url(apiUrl)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNjg3OGRjZjg0M2VlNGFkODE1Y2JiMGI0MGMxYWE2NyIsInN1YiI6IjY1NWEyOTQzZDRmZTA0MDBlMWI1M2U0ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.yOmNXNGsqV-s0oIhjArZRPsTwOVGCCWgM6R8nuouYUs")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(responseBody);
            JSONArray results = (JSONArray) jsonObj.get("results");
            if (results.isEmpty()) {
                return null;
            }
            JSONObject result = (JSONObject) results.get(0);
            System.out.println(result);
            return String.valueOf(result.get("id"));
        }
    }

    public poster movie_information(String movie_id) throws IOException, ParseException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + movie_id + "?language=ko-KR")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNjg3OGRjZjg0M2VlNGFkODE1Y2JiMGI0MGMxYWE2NyIsInN1YiI6IjY1NWEyOTQzZDRmZTA0MDBlMWI1M2U0ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.yOmNXNGsqV-s0oIhjArZRPsTwOVGCCWgM6R8nuouYUs")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(responseBody);
            String title = (String) jsonObj.get("title");
            String overview = (String) jsonObj.get("overview");
            String poster_path = (String) jsonObj.get("poster_path");
            poster_path = "https://image.tmdb.org/t/p/original" + poster_path;
            String language = (String)jsonObj.get("original_language");
            String released_day = (String)jsonObj.get("release_date");

        Request request2 = new Request.Builder()
            .url("https://api.themoviedb.org/3/movie/"+ movie_id+"/credits?language=ko-KR")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNjg3OGRjZjg0M2VlNGFkODE1Y2JiMGI0MGMxYWE2NyIsInN1YiI6IjY1NWEyOTQzZDRmZTA0MDBlMWI1M2U0ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.yOmNXNGsqV-s0oIhjArZRPsTwOVGCCWgM6R8nuouYUs")
            .build();
        try (Response response2 = client.newCall(request2).execute()) {
            if (!response2.isSuccessful()) {
                throw new IOException("Unexpected code " + response2);
            }
            String responseBody2 = response2.body().string();
            JSONObject jsonResponse2 = (JSONObject) jsonParser.parse(responseBody2);
            JSONArray castArray = (JSONArray) jsonResponse2.get("cast");
            int i = 1;
            List<String> originalNames = new ArrayList<>();
            for (Object castObject : castArray) {
                JSONObject cast = (JSONObject) castObject;
                String originalName = (String) cast.get("original_name");
                originalNames.add(originalName);
                i++;
                if(i >3){
                    break;
                }
            }
            poster p = new poster(title, overview, poster_path, language, released_day, originalNames);
            System.out.println(title+overview+poster_path+language+released_day+originalNames);
            // JSONObject jsonObj2 = (JSONObject) jsonParser.parse(responseBody);
            return p;
        }
    }
}
    
}
