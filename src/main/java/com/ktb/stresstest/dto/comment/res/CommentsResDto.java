package com.ktb.stresstest.dto.comment.res;

import com.ktb.stresstest.domain.Comment;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CommentsResDto(
        Long id,
        Long userId,
        String content,
        String author,
        String profile,
        LocalDate date
) {
    public static CommentsResDto create(Comment comment){
        return CommentsResDto.builder()
                .id(comment.getId())
                .userId(comment.getUser().getId())
                .content(comment.getComment())
                .author(comment.getUser().getNickname())
                .profile(comment.getUser().getProfileUrl())
                .date(comment.getDate())
                .build();
    }
}
