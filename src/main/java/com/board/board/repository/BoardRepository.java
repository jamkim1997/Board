package com.board.board.repository;

import com.board.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitle(String title);
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
