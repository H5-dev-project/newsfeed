package com.example.newsfeed.board.entity;

import com.example.newsfeed.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "boards")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String images;
    private int visibilityType;

    public Board(String title, String content, String images, int visibilityType) {
        this.title = title;
        this.content = content;
        this.images = images;
        this.visibilityType = visibilityType;
    }
}
