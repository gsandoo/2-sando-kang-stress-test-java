package com.ktb.stresstest.usecase;

import com.ktb.stresstest.annotation.UseCase;
import com.ktb.stresstest.domain.Comment;
import org.hibernate.annotations.Comments;

import java.util.List;

@UseCase
public interface CommentUseCase {
    List<Comment> findCommentsByPostId(Long postId);
}
