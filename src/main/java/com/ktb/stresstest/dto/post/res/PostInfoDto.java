package com.ktb.stresstest.dto.post.res;

import lombok.Builder;

import java.util.List;

@Builder
public record PostInfoDto(
        List<PostResDto> posts,
        Boolean hasMore
) {
    public static PostInfoDto create(List<PostResDto> posts, Boolean hasMore){
        return PostInfoDto.builder()
                .posts(posts)
                .hasMore(hasMore)
                .build();
    }
}
