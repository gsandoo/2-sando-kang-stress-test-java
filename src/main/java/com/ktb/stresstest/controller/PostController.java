package com.ktb.stresstest.controller;

import com.ktb.stresstest.annotation.UserId;
import com.ktb.stresstest.dto.ResponseDto;
import com.ktb.stresstest.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public ResponseDto<?> getPosts(@RequestParam(defaultValue = "1") int page){
        return ResponseDto.ok(postService.getPosts(page));
    }

    @GetMapping("/{postId}")
    public ResponseDto<?> getPostWithComments(@PathVariable Long postId, HttpServletRequest request){
        return ResponseDto.ok(postService.getPostWithComments(postId, request));
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<?> createPost(
            @UserId Long userId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestPart(value = "image" ,required = false) MultipartFile image
            ){
        return ResponseDto.created(postService.createPost(userId, title, content, image));
    }

    @PutMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<?> updatePost(
            @UserId Long userId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestPart(value = "image" ,required = false) MultipartFile image
    ){
        return ResponseDto.ok(postService.updatePost(userId, title, content, image));
    }

    @DeleteMapping(value = "")
    public ResponseDto<?> deletePost(@UserId Long userId, Long postId){
        postService.deletePost(userId, postId);
        return ResponseDto.ok("게시물이 삭제 되었습니다.");
    }
    @PatchMapping("")
    public ResponseDto<?> likesPost(@UserId Long userId, Long postId){
        return ResponseDto.ok(postService.likesPost(userId, postId));
    }
}
