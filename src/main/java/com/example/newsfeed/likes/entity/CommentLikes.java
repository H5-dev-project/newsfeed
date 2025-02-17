//package com.example.newsfeed.likes.entity;
//
//import com.example.newsfeed.users.entity.Users;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.xml.stream.events.Comment;
//
//@Entity
//@Table(name = "commentLikes")
//@Getter
//@NoArgsConstructor
//public class CommentLikes extends  BaseLike {
//
//    @ManyToOne
//    @JoinColumn(name = "comment_id", nullable = false)
//    private Comment comment;
//
//    public CommentLikes(Users users, Comment comment) {
//        super(users);
//        this.comment = comment;
//    }
//
//}
