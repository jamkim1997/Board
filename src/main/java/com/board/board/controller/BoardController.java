package com.board.board.controller;

import com.board.board.entity.Board;
import com.board.board.repository.BoardRepository;
import com.board.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String list(Model model, @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
        //Page<Board> boards = boardService.findAllBoards(pageable);
        Page<Board> boards = boardService.search(searchText, searchText, pageable);
        int startPage = Math.max(boards.getPageable().getPageNumber()-4, 1);
        int currentPage = boards.getPageable().getPageNumber();
        int endPage = Math.min(boards.getPageable().getPageNumber() + 5, boards.getTotalPages());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("boards", boards);
        return "/board/list";
    }

    @GetMapping("/board")
    public String viewBoard(Model model, @RequestParam(required = false) Long id) {
        if(id == null) {
            model.addAttribute("board", new Board());
        }
        else {
            model.addAttribute("board", boardService.findBoardById(id).orElse(null));
        }
        return "/board/board";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if(id == null) {
            model.addAttribute("board", new Board());
        }
        else {
            model.addAttribute("board", boardService.findBoardById(id).orElse(null));
        }
        return "/board/form";
    }

    @PostMapping("/form")
    public String boardSubmit(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        if(bindingResult.hasErrors()) {
            return "board/form";
        }
        String name = authentication.getName();
        boardService.saveBoard(name, board);
        return "redirect:/board/list";
    }
}
