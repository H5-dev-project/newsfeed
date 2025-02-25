package com.example.newsfeed.likes.entity;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.users.entity.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board_likes")
@Getter
@NoArgsConstructor
public class BoardLikes extends  BaseLike {

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public BoardLikes(Users users, Board board) {
        super(users);
        this.board = board;
    }

}
