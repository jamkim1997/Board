package com.board.board.service;

import com.board.board.entity.Board;
import com.board.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Page<Board> findAllBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
    public Board saveBoard(Board board) {return boardRepository.save(board);}
    public Optional<Board> findBoardById(Long id) {return boardRepository.findById(id);}

    public Page<Board> search(String title, String content, Pageable pageable) {
        return boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);}
}
