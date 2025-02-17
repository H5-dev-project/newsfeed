package com.example.newsfeed.users.entity;

import com.example.newsfeed.common.BaseEntity;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class Users extends BaseEntity {
    @Id
    @Column(updatable = false, nullable = false, length = 26)
    private String id;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private Byte gender;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Boolean isDeleted = false;


    public Users(String id, String email, String password, String username, LocalDateTime birth, String phone, Byte gender, String image) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.birth = birth;
        this.phone = phone;
        this.gender = gender;
        this.image = image;
    }

    private String generateUlid() {
        return new ULID().nextULID();
    }

    public void markDeleted() {
        this.isDeleted = true;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
