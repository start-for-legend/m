//package com.minsta.m.domain.story.scheduler;
//
//import com.minsta.m.domain.story.entity.Story;
//import com.minsta.m.domain.story.repository.StoryRepository;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.stream.Collectors;
//
//import static com.minsta.m.domain.story.entity.QStory.story;
//
//@Component
//@RequiredArgsConstructor
//public class StoryValidScheduler {
//
//    private final StoryRepository storyRepository;
//    private final JPAQueryFactory em;
//
//    @Scheduled(cron = "*/10 * * * * *")
//    public void setStoryValid() {
//
//        storyRepository.saveAll(
//                em.selectFrom(story)
//                        .where(story.isValid.eq(true))
//                        .where(story.createdAt.after(LocalDateTime.now().minus(24, ChronoUnit.HOURS)))
//                        .fetch()
//                        .stream().map(this::setValid).collect(Collectors.toList()));
//    }
//
//    private Story setValid(Story story) {
//        story.setValid(false);
//        return story;
//    }
//}
