package com.board.board.service;

import com.board.board.entity.Board;
import com.board.board.entity.Role;
import com.board.board.entity.User;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public User save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role role = new Role();
        role.setId(1l);
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);

        Board board = new Board();
        board.setTitle("new! " +savedUser.getUsername());
        board.setContent("Hello. I just joined");
        board.setUser(savedUser);
        boardRepository.save(board);

        return savedUser;
    }
}
