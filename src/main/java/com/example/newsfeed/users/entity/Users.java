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
import org.hibernate.annotations.Where;


import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class Users extends BaseEntity {
    //id가 없음
    @Id
    @Column(updatable = false, nullable = true, length = 26, unique = true) //nullable = true, unique = true [추가 및 변경]
    private String id;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column( nullable = false)//닉네임
    private String username;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private Byte gender;

    @Column(nullable = false)
    private String image = "basicImage";

    @Column(nullable = false)
    private Boolean isDeleted = false;


    public Users(String email, String password, String username, LocalDate birth, String phone, Byte gender) {
        this.id = generateUlid();
        this.email = email;
        this.password = password;
        this.username = username;
        this.birth = birth;
        this.phone = phone;
        this.gender = gender;
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
