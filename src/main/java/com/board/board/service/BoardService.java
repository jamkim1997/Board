package com.board.board.service;

import com.board.board.entity.Board;
import com.board.board.entity.User;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public Board saveBoard(String userName, Board board) {
        User user = userRepository.findByUsername(userName);
        board.setUser(user);
        return boardRepository.save(board);
    }
    public Optional<Board> findBoardById(Long id) {return boardRepository.findById(id);}

    public Page<Board> search(String title, String content, Pageable pageable) {
        return boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);}
}
