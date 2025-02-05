package com.ktb.stresstest.dto.comment.req;

public record CommentDeleteDto(
        Long commentId,
        Long postId
) {
}
