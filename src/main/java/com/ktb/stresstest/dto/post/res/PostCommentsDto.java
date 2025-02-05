package com.ktb.stresstest.dto.post.res;

import com.ktb.stresstest.dto.comment.res.CommentsResDto;
import lombok.Builder;

import java.util.List;

@Builder
public record PostCommentsDto(
        List<CommentsResDto> comments,
        PostResDto post
) {
    public static PostCommentsDto create(List<CommentsResDto> comments, PostResDto post){
        return PostCommentsDto.builder()
                .comments(comments)
                .post(post)
                .build();
    }
}
