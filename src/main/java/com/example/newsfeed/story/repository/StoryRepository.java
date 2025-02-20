package com.example.newsfeed.story.repository;

import com.example.newsfeed.story.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    List<Story> findAllByVisibilityType(int visibilityType);
    List<Story> findByVisibilityEndBeforeAndVisibilityType(LocalDateTime now, int visibilityType);
}
