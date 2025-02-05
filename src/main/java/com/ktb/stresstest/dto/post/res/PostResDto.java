package com.ktb.stresstest.dto.post.res;

import com.ktb.stresstest.domain.Post;
import com.ktb.stresstest.domain.User;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PostResDto(
        Long id,
        String author,
        String profile,
        String title,
        String content,
        String image,
        LocalDate date,
        Long likes,
        Long views,
        Long comments
) {

    public static PostResDto create(Post post) {
       return PostResDto.builder()
               .id(post.getId())
               .author(post.getUser().getNickname())
               .profile(post.getUser().getProfileUrl())
               .title(post.getTitle())
               .content(post.getContent())
               .image(post.getUrl())
               .date(post.getDate())
               .likes(post.getLikes())
               .views(post.getViews())
               .comments(post.getComments())
               .build();
    }
}
