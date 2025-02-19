package com.example.newsfeed.board.entity;

import com.example.newsfeed.common.BaseEntity;
import com.example.newsfeed.users.entity.Users;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "boards")
public class Board extends BaseEntity {

    @Id
    @Column(updatable = false, nullable = false, length = 26)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "longtext", nullable = false)
    private String content;

    @Column(nullable = false)
    private String images;

    @Column(nullable = false)
    private Byte visibilityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    public Board(Users user, String title, String content, Byte visibilityType) {
        this.id = generateUlid();
        this.user = user;
        this.title = title;
        this.content = content;
        this.visibilityType = visibilityType;
    }

    private String generateUlid() {
        return new ULID().nextULID();
    }

    public void update(String title, String content, String images, Byte visibilityType) {
        this.title = title;
        this.content = content;
        this.images = images;
        this.visibilityType = visibilityType;
    }

    public void updateImage(String images) {
        this.images = images;
    }
}
