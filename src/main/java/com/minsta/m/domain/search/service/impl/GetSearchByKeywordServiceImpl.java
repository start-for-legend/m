package com.minsta.m.domain.search.service.impl;

import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.repository.FeedRepository;
import com.minsta.m.domain.leels.entity.Leels;
import com.minsta.m.domain.leels.repository.LeelsRepository;
import com.minsta.m.domain.search.controller.data.response.SearchResponse;
import com.minsta.m.domain.search.service.GetSearchByKeywordService;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.minsta.m.domain.follow.entity.QFollow.follow;
import static com.minsta.m.domain.user.entity.QUser.user;

@ReadOnlyService
@RequiredArgsConstructor
public class GetSearchByKeywordServiceImpl implements GetSearchByKeywordService {

    private final JPAQueryFactory em;
    private final LeelsRepository leelsRepository;
    private final FeedRepository feedRepository;

    @Override
    public SearchResponse execute(String keyword) {
        if (keyword.startsWith("#")) {
            return SearchResponse.forHashTag(getHashTags(keyword, 20));
        } else {
            return SearchResponse.forKeyWard(getHashTags("#" + keyword, 5), getUsers());
        }
    }

    private Map<String, Long> getHashTags(String keyword, int size) {
        List<String> hashtags = new ArrayList<>();

        List<Leels> leelsList = leelsRepository.findAllByHashtagsContainsAndQuery(keyword);
        List<Feed> feedList = feedRepository.findAllByHashtagsContains(keyword);

        hashtags.addAll(leelsList.stream()
                .flatMap(leels -> leels.getHashtags().stream())
                .toList());
        hashtags.addAll(feedList.stream()
                .flatMap(feed -> feed.getHashtags().stream())
                .toList());

        return hashtags.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getKey().contains(keyword.substring(1)))
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(size)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private Map<UserResponse, Long> getUsers() {
        Map<UserResponse, Long> users = new LinkedHashMap<>();

        List<Tuple> results = em
                .select(user.userId, user.name, user.profileUrl, follow.followedUser.userId.count())
                .from(user)
                .join(follow)
                .on(user.userId.eq(follow.followedUser.userId))
                .groupBy(user.userId)
                .orderBy(follow.followedUser.userId.count().desc())
                .limit(15)
                .fetch();

        for (Tuple t : results) {
            UserResponse userResponse = UserResponse.of(t.get(user.userId), t.get(user.name), t.get(user.profileUrl));
            users.put(userResponse, t.get(follow.followedUser.userId.count()));
        }

        return users;
    }
}
