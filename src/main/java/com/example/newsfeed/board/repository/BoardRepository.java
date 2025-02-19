package com.example.newsfeed.board.repository;

import com.example.newsfeed.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,String> {
    List<Board> findByUser_Id(String userId);
}
