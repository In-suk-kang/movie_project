package com.example.movie_project.controller;
import com.example.movie_project.dto.MemberDTO;
import com.example.movie_project.entity.SeatInfo;
import com.example.movie_project.entity.TheaterEntity;
import com.example.movie_project.entity.TheaterRegionEntity;
import com.example.movie_project.service.CrawlingService;
import com.example.movie_project.service.TheaterRegionService;
import com.example.movie_project.service.TheaterService;
import com.example.movie_project.service.MemberService;
import com.example.movie_project.service.poster;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.movie_project.service.TMDB;
import com.example.movie_project.service.Theater;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.movie_project.model.KakaoProfile;
import com.example.movie_project.model.OAuthToken;
import com.example.movie_project.repository.ReserveInfoRepository;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;


import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberControll {
    
    // 생성자 주입
    @Autowired
    private final MemberService memberService;
    @Autowired
    private final CrawlingService crawlingService;
    @Autowired
    private final TMDB tmdb;
    @Autowired
    private final Theater theater;
    @Autowired
    private final TheaterRegionService locationService;
    @Autowired
    private final TheaterService theaterService;
    @Autowired
    private final ReserveInfoRepository reserveInfoRepository;
    // @Autowired
    // private final Schedule schedule;

    @GetMapping("/")
    public String mainc(Model model) {
        List<poster> crawResult = crawlingService.getcrawa();
        model.addAttribute("movies", crawResult);
        return "index"; // 크롤링 결과를 표시할 뷰 이름을 반환
    }

    @GetMapping("/booking")
    public String booking(Model model) throws IOException
    {
        List <String> movies = theater.movieList();
        List<TheaterRegionEntity> region = locationService.getAllTheaterRegions();
        List<TheaterEntity> theater = theaterService.getAllTheaters();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        model.addAttribute("movies", movies);
        model.addAttribute("region", region);
        model.addAttribute("theater", theater);
        model.addAttribute("now", formattedDate);
        return "booking";
    }

    @GetMapping("/detail")
    public String movie_infa(@RequestParam("searchTerm") String search, Model model) throws IOException, ParseException {
        String crawlResult = tmdb.movie_code(search);
        poster movie_inf = tmdb.movie_information(crawlResult);
        model.addAttribute("movie_data", movie_inf);
        return "movie_detail"; // 크롤링 결과를 표시할 뷰 이름을 반환
    }

    @PostMapping("/booking")
    public String reserve(SeatInfo seatInfo, Model model) throws SQLException, ParseException, IOException{
        reserveInfoRepository.save(seatInfo);
        String name = seatInfo.getSelectedMovie();
        String crawlResult = tmdb.movie_code(name);
        poster movie_inf = tmdb.movie_information(crawlResult);
        String path = movie_inf.getPosterPath();
        model.addAttribute("movieInfo",path);
        model.addAttribute("seatInfo", seatInfo);
        return "payment";
    }
    @GetMapping("/main")
    public String movie(){
        return "movie"; // 크롤링 결과를 표시할 뷰 이름을 반환
    }
    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "login";
    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }

    @GetMapping("/member/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model사용
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }

    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session,Model model) {
        session.invalidate();
        List<poster> crawlResult = crawlingService.getcrawl(); // CrawlingService의 메서드 호출
        List<poster> soonResult = crawlingService.getcraw_soon(); // CrawlingService의 메서드 호출
        model.addAttribute("crawlResult", crawlResult); // 크롤링 결과를 모델에 추가
        model.addAttribute("soonResult", soonResult); // 크롤링 결과를 모델에 추가
        return "index";
    }

    @PostMapping("/member/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
    }
     @GetMapping("/auth/kakao/callback")
    public ModelAndView kakaoCallback(String code){ //Data를 리턴해주는 컨트롤러 함수
        //Post 방식으로 key=value 데이터를 요청 (카카오쪽으로)
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","7f5761b30ada70e56a2a12e72afb3d06" );
        params.add("redirect_uri" , "http://localhost:8081/auth/kakao/callback");
        params.add("code",code);
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest=
            new HttpEntity<>(params,headers);

        ResponseEntity<String> response = rt.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            kakaoTokenRequest,
            String.class
        );
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken =null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(),OAuthToken.class);
        } catch (JsonMappingException e) {
            
            e.printStackTrace();
        } catch (JsonProcessingException e) {
           
            e.printStackTrace();
        }
        System.out.println("카카오 엑세스 토큰 :"+ oauthToken.getAccess_token());
         

        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization","Bearer "+oauthToken.getAccess_token());
        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
       
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2=
            new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            kakaoProfileRequest2,
            String.class
        );
     
        ObjectMapper ob2 = new ObjectMapper();
		KakaoProfile kakaoProfile =null;
		
		try {
			kakaoProfile = ob2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 

        ModelAndView mav = new ModelAndView();
        mav.setViewName("main");  // main.html의 이름을 설정
        if (kakaoProfile != null && kakaoProfile.getProperties() != null) {
            mav.addObject("kakaoProfileNickname", kakaoProfile.getProperties().getNickname());
        } else {
            // 적절한 처리를 수행하거나 로그를 남깁니다.
        }
        return mav;
    }
}









