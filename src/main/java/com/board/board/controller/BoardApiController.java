package com.board.board.controller;

import com.board.board.entity.Board;
import com.board.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardApiController {
    private BoardRepository boardRepository;

    @Autowired
    public BoardApiController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false) String title) {
        if(StringUtils.isEmpty(title)) {
            return boardRepository.findAll();
        }
        else {
            return boardRepository.findByTitle(title);
        }
    }

    @PostMapping("/boards")
    Board newBoard(@RequestBody Board board) {
        return boardRepository.save(board);
    }

    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {
        return boardRepository.findById(id).map(board -> {
            board.setTitle(newBoard.getTitle());
            board.setContent(newBoard.getContent());
            return boardRepository.save(board);
        }).orElseGet(() -> {
            newBoard.setId(id);
            return boardRepository.save(newBoard);
        });
    }


    @Secured("ROLE_ADMIN")
    @DeleteMapping ("/boards/{id}")
        void deleteBoard(@PathVariable Long id) {
            boardRepository.deleteById(id);
    }
}
