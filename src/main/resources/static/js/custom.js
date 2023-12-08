(function($){
  var mySwiper = new Swiper('.swiper-container', {
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
    },
    pagination: {
        el: '.swiper-pagination',
        type: 'bullets',
    },
    autoplay: {
        delay: 6000,
    },
});

//영화차트 이미지 슬라이드
var mySwiper = new Swiper('.swiper-container2', {
    slidesPerView: 4,
    spaceBetween: 24,
    
    /*mousewheel: { 마우스와 키보드로 슬라이드 조작
      invert: true,
    },
    keybord: {
      enable: true,
      onlyInViewport: false,
    },*/
    breakpoints: {
      600: {
         slidesPerView: 1.4,
         spaceBetween: 24
      },
      768: {
         slidesPerView: 2,
         spaceBetween: 24
      },
      960: {
         slidesPerView: 3,
         spaceBetween: 24
      }
    }
});

//영화차트 탭 메뉴
var movBtn = $(".movie_title > ul > li");
var movCont = $(".movie_chart > div");

movCont.hide().eq(0).show();

movBtn.click(function(e){
   e.preventDefault();
   var target = $(this);
   var index = target.index();
   movBtn.removeClass("active");
   target.addClass("active");
   movCont.css("display","none");
   movCont.eq(index).css("display","block");
});

// 공지사항 탭 메뉴
var tapMenu = $(".notice");

//공지사항 컨텐츠 내용 숨기기 기능
tapMenu.find("ul > li > ul").hide();
tapMenu.find("li.active > ul").show();

function tapList(e){
   e.preventDefault(); //#의 기능 차단
   var target = $(this);
   target.next().show().parent("li").addClass("active").siblings("li").removeClass("active").find("ul").hide();
}

tapMenu.find("ul > li > a").click(tapList).focus(tapList);

// 관람객 게시판 탭 메뉴
var tapMenu2 = $(".audience");

//관람객 게시판 컨텐츠 내용 숨기기 기능
tapMenu2.find("ul > li > ul").hide();
tapMenu2.find("li.active > ul").show();

function tapList(e){
   e.preventDefault(); //#의 기능 차단
   var target = $(this);
   target.next().show().parent("li").addClass("active").siblings("li").removeClass("active").find("ul").hide();
}

tapMenu2.find("ul > li > a").click(tapList).focus(tapList);

})