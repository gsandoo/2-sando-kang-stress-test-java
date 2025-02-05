package com.ktb.stresstest.dto.comment.req;

public record CommentUpdateDto(
        Long commentId,
        String content
) {
}
