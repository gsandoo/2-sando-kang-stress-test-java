package com.ktb.stresstest.service;

import com.ktb.stresstest.domain.Comment;
import com.ktb.stresstest.domain.Post;
import com.ktb.stresstest.domain.User;
import com.ktb.stresstest.dto.comment.req.CommentCreateDto;
import com.ktb.stresstest.dto.comment.req.CommentDeleteDto;
import com.ktb.stresstest.dto.comment.req.CommentUpdateDto;
import com.ktb.stresstest.dto.comment.res.CommentsResDto;
import com.ktb.stresstest.exception.CommonException;
import com.ktb.stresstest.exception.ErrorCode;
import com.ktb.stresstest.repository.CommentRepository;
import com.ktb.stresstest.usecase.CommentUseCase;
import com.ktb.stresstest.usecase.PostUseCase;
import com.ktb.stresstest.usecase.UserUseCase;
import com.ktb.stresstest.util.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final ValidationUtil validationUtil;
    private final CommentUseCase commentUseCase;
    private final PostUseCase postUseCase;
    private final UserUseCase userUseCase;

    public List<CommentsResDto> getComments(Long postId) {

        return commentUseCase.findCommentsByPostId(postId).stream()
                .map(comment -> CommentsResDto.create(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createComment(Long userId, CommentCreateDto dto){

        User user = userUseCase.findById(userId);
        Post post = postUseCase.findById(dto.postId());

        validationUtil.validateContentLength(dto.comment());
        post.increaseComments();

        commentRepository.save(new Comment(user, post, dto.comment()));
    }

    @Transactional
    public CommentsResDto updateComment(Long userId, CommentUpdateDto dto){

        User user = userUseCase.findById(userId);
        Comment comment = commentRepository.findById(dto.commentId())
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_COMMENT));

        comment.updateComment(dto.content());

        return CommentsResDto.create(comment);
    }

    @Transactional
    public void deleteComment(Long userId, CommentDeleteDto dto){

        User user = userUseCase.findById(userId);
        Post post = postUseCase.findById(dto.postId());
        Comment comment = commentRepository.findById(dto.commentId())
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_COMMENT));

        commentRepository.delete(comment);
    }
}
