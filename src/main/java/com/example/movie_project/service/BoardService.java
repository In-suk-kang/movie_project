package com.example.movie_project.service;


import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import java.util.ArrayList;
import com.example.movie_project.entity.BoardEntity;
import com.example.movie_project.dto.BoardDTO;
import com.example.movie_project.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service 
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO){
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
    }
    public List<BoardDTO> findAll(){
        List<BoardEntity>boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList =new ArrayList<>();
        for (BoardEntity boardEntity: boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }
     @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }
    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    public void delete(Long id){
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(org.springframework.data.domain.Pageable pageable){
        int page = pageable.getPageNumber() - 1;
        int pageLimit= 10; // 한 페이지에 보여줄 글 갯수
        Page<BoardEntity>boardEntities = boardRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        // entity에 작성된 id를 기준으로 내림차순으로 정렬

        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // 목록 : id , 작성자, 제목, 조회수, 작성시간
        Page<BoardDTO>boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(),board.getBoardWriter(),board.getBoardTitle(),board.getBoardHits(),board.getCreatedTime()));
        return boardDTOS;
    }
}
