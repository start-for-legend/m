package com.minsta.m.domain.main.service.impl;

import com.minsta.m.domain.feed.controller.data.response.FeedResponse;
import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.main.controller.data.response.MainResponse;
import com.minsta.m.domain.main.controller.data.response.MainStoryResponse;
import com.minsta.m.domain.main.service.MainResponseService;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.util.UserUtil;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.minsta.m.domain.feed.entity.feed.QFeedLike.feedLike;
import static com.minsta.m.domain.follow.entity.QFollow.follow;
import static com.minsta.m.domain.story.entity.QStory.story;
import static com.minsta.m.domain.user.entity.QUser.user;
import static com.minsta.m.domain.feed.entity.feed.QFeed.feed;

@ReadOnlyService
@RequiredArgsConstructor
public class MainResponseServiceImpl implements MainResponseService {

    private final UserUtil userUtil;
    private final JPAQueryFactory em;

    @Override
    public MainResponse execute() {
        return MainResponse.of(getStory(), getFeed());
    }

    private List<FeedResponse> getFeed() {
        List<Feed> resultFeeds = em
                .selectFrom(feed)
                .leftJoin(feed.user, user)
                .leftJoin(follow)
                .on(follow.followedUser.userId.eq(feed.user.userId))
                .leftJoin(feedLike)
                .on(feedLike.feed.feedId.eq(feed.feedId).and(feedLike.user.userId.eq(userUtil.getUser().getUserId())))
                .where(follow.user.userId.eq(userUtil.getUser().getUserId())
                        .and(feed.createdAt.after(LocalDateTime.now().minusWeeks(1)))  // Checking only for feeds younger than 1 week
                        .and(feed.createdAt.before(LocalDateTime.now())))
                .groupBy(feed.feedId)
                .fetch();

        return resultFeeds.stream()
                .map(feed1 -> FeedResponse.builder()
                        .feedId(feed1.getFeedId())
                        .userResponse(convertUserToUserResponse(feed1.getUser()))
                        .content(feed1.getContent())
                        .hashtags(feed1.getHashtags())  // Assuming you have a method to extract hashtags
                        .fileUrls(feed1.getFileUrls())  // Assuming you have a method to extract file URLs
                        .build())
                .toList();
    }

    private UserResponse convertUserToUserResponse(User user) {
        return UserResponse.of(user.getUserId(), user.getNickName(), user.getProfileUrl(), user.getName());
    }

    private List<MainStoryResponse> getStory() {
        List<Tuple> stories = em
                .select(story.storyId, story.user)
                .leftJoin(story.user, user)
                .leftJoin(follow)
                .on(follow.followedUser.userId.eq(story.user.userId))
                .where(follow.user.userId.eq(userUtil.getUser().getUserId())
                        .and(story.createdAt.after(LocalDateTime.now().minusDays(1))))
                .where(story.isValid.notIn(true))
                .groupBy(story.user)
                .fetch();

        Map<Long, MainStoryResponse> groupedStories = new HashMap<>();

        for (Tuple row : stories) {
            Long userId = row.get(user.userId);
            String profileUrl = row.get(user.profileUrl);
            String nickName = row.get(user.nickName);
            Long storyId = row.get(story.storyId);
            Boolean isRead = row.get(story).getWatchers().contains(userUtil.getUser().getUserId());

            Map<Long, Boolean> storyMap = new HashMap<>();
            storyMap.put(storyId, isRead);

            if (!groupedStories.containsKey(userId)) {
                groupedStories.put(userId, MainStoryResponse.of(userId, profileUrl, nickName, new ArrayList<>(List.of(storyMap))));
            } else {
                groupedStories.get(userId).storyValue().add(storyMap);
            }
        }

        return new ArrayList<>(groupedStories.values());
    }
}
