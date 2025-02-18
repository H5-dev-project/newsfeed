package com.example.newsfeed.board.repository;

import com.example.newsfeed.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
