package com.ktb.stresstest.service;

import com.ktb.stresstest.domain.Comment;
import com.ktb.stresstest.repository.CommentRepository;
import com.ktb.stresstest.usecase.CommentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentUseCaseService implements CommentUseCase {

    private final CommentRepository commentRepository;
    @Override
    public List<Comment> findCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }
}
