package com.example.newsfeed.story.entity;

import com.example.newsfeed.common.BaseEntity;
import com.example.newsfeed.users.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private int visibilityType = 1;

    @CreatedDate
    private LocalDate visibilityStart;

    private LocalDate visibilityEnd;

    public Story(){

    }

    public Story (Users users, String content) {
        this.users = users;
        this.content = content;
    }

    public void update(String content, int visibilityType){
        this.content = content;
        this.visibilityType = visibilityType;
    }

    public void changeVisibilityType(int visibilityType){
        this.visibilityType = visibilityType;
    }

    @PrePersist
    public void prePersist(){
        if(visibilityStart == null){
            visibilityStart = LocalDate.now();
        }

        visibilityEnd = visibilityStart.plusDays(1);
    }

}
