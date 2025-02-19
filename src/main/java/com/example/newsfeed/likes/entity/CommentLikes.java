package com.example.newsfeed.likes.entity;


import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.users.entity.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment_likes")
@Getter
@NoArgsConstructor
public class CommentLikes extends  BaseLike {

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    public CommentLikes(Users users, Comment comment) {
        super(users);
        this.comment = comment;
    }

}
