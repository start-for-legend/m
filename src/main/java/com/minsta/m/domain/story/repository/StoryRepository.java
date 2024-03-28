package com.minsta.m.domain.story.repository;

import com.minsta.m.domain.story.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {
}
