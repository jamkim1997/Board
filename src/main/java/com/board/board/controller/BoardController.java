package com.board.board.controller;

import com.board.board.entity.Board;
import com.board.board.repository.BoardRepository;
import com.board.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Board> allBoards = boardService.findAllBoards();
        model.addAttribute("boards", allBoards);
        return "/board/list";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("board", new Board());
        return "/board/form";
    }

    @PostMapping("/form")
    public String boardSubmit(@ModelAttribute Board board) {
        boardService.saveBoard(board);
        return "redirect::/board/list";
    }
}
