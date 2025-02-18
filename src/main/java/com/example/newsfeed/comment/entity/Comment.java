package com.example.newsfeed.comment.entity;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.common.BaseEntity;
import com.example.newsfeed.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(columnDefinition = "TEXT")
    private String content;

    public Comment(Board board, Users user, String content) {
        this.board = board;
        this.user = user;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}