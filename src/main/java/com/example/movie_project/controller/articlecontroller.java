package com.example.movie_project.controller;
import com.example.movie_project.dto.MemberDTO;
import com.example.movie_project.entity.MemberEntity;
import com.example.movie_project.repository.ArticleRepository;
import com.example.movie_project.service.CrawlingService;
import com.example.movie_project.service.poster;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List; // List를 임포트해야 합니다.

@Controller
@Slf4j // 로깅을 위한 문법
public class articlecontroller { // 클래스 이름을 변경
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired // CrawlingService 주입
    private CrawlingService crawlingService;
    @GetMapping("/")
    public String mainc(Model model) {
        List<poster> crawlResult = crawlingService.getcrawl(); // CrawlingService의 메서드 호출
        model.addAttribute("crawlResult", crawlResult); // 크롤링 결과를 모델에 추가
        return "index"; // 크롤링 결과를 표시할 뷰 이름을 반환
    }

    @GetMapping("/main")
    public String movie(Model model) {
        List<poster> crawlResult = crawlingService.getcrawa(); // CrawlingService의 메서드 호출
        model.addAttribute("crawlResult", crawlResult); // 크롤링 결과를 모델에 추가
        return "movie"; // 크롤링 결과를 표시할 뷰 이름을 반환
    }

    @GetMapping("/login")
    public String newArticleForm() {

        return "login";
    }

}
