package com.example.newsfeed.users.repository;

import com.example.newsfeed.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByPhone(String phone);


}
