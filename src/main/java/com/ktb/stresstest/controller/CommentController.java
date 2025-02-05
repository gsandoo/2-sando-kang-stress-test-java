package com.ktb.stresstest.controller;

import com.amazonaws.Response;
import com.ktb.stresstest.annotation.UserId;
import com.ktb.stresstest.dto.ResponseDto;
import com.ktb.stresstest.dto.comment.req.CommentCreateDto;
import com.ktb.stresstest.dto.comment.req.CommentDeleteDto;
import com.ktb.stresstest.dto.comment.req.CommentUpdateDto;
import com.ktb.stresstest.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseDto<?> getComments(@PathVariable Long postId){
        return ResponseDto.ok(commentService.getComments(postId));
    }

    @PostMapping("")
    public ResponseDto<?> createComment(@UserId Long userId, CommentCreateDto dto){
        commentService.createComment(userId, dto);
        return ResponseDto.created("댓글을 작성 하였습니다.");
    }

    @PutMapping("")
    public ResponseDto<?> updateComment(@UserId Long userId, CommentUpdateDto dto){
        return ResponseDto.ok(commentService.updateComment(userId, dto));
    }

    @DeleteMapping("")
    public ResponseDto<?> deleteComment(@UserId Long userId, CommentDeleteDto dto){
        commentService.deleteComment(userId, dto);
        return ResponseDto.ok("댓글을 삭제 하였습니다.");
    }
}
