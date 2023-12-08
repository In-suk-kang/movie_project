package com.example.movie_project.controller;

import com.example.movie_project.dto.BoardDTO;
import com.example.movie_project.service.BoardService;

import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping ("/board")
public class BoardController {
    private final BoardService boardService;
    @GetMapping("/save")
    public String saveForm() {
        return "boardsave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO,Model model) {
        boardService.save(boardDTO);
        model.addAttribute("board", boardDTO);
        return "redirect:/";
    }

    @GetMapping("/")
    public String findAll(Model model){
        List<BoardDTO>boardDTOList = boardService.findAll();
        model.addAttribute("boardList",boardDTOList);
        return "boardlist";
    }

    @GetMapping("/{id}")
    public String findByID(@PathVariable Long id, Model model,
                           @PageableDefault(page=1)Pageable pageable){
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page",pageable.getPageNumber());
        return "boarddetail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate",boardDTO);
        return "boardupdate";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        
       return "redirect:/board/" + boardDTO.getId();
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/";
    }

    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit =3 ;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";
    }

    
}
