package com.example.newsfeed.story.entity;

import com.example.newsfeed.common.BaseEntity;
import com.example.newsfeed.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "story")
public class Story extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int visibilityType;

    @Column(nullable = false)
    private LocalDate visibilityStart;

    @Column(nullable = false)
    private LocalDate visibilityEnd;

    public Story(){

    }

    public Story(Users users, String content, int visibilityType, LocalDate visibilityStart, LocalDate visibilityEnd) {
        this.users = users;
        this.content = content;
        this.visibilityType = visibilityType;
        this.visibilityStart = visibilityStart;
        this.visibilityEnd = visibilityEnd;
    }

    public void update(String content, int visibilityType, LocalDate visibilityStart, LocalDate visibilityEnd){
        this.content = content;
        this.visibilityType = visibilityType;
        this.visibilityStart = visibilityStart;
        this.visibilityEnd = visibilityEnd;
    }

    public void changeVisibilityType(int visibilityType){
        this.visibilityType = visibilityType;
    }
}
