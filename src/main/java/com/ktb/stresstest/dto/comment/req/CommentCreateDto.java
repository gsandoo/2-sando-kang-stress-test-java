package com.ktb.stresstest.dto.comment.req;

public record CommentCreateDto(
        Long postId,
        String comment
) {
}
