package com.ktb.stresstest.service;

import com.ktb.stresstest.domain.Comment;
import com.ktb.stresstest.domain.Like;
import com.ktb.stresstest.domain.Post;
import com.ktb.stresstest.domain.User;
import com.ktb.stresstest.dto.comment.res.CommentsResDto;
import com.ktb.stresstest.dto.post.res.PostCommentsDto;
import com.ktb.stresstest.dto.post.res.PostInfoDto;
import com.ktb.stresstest.dto.post.res.PostResDto;
import com.ktb.stresstest.repository.PostRepository;
import com.ktb.stresstest.usecase.*;
import com.ktb.stresstest.util.RateLimitUtil;
import com.ktb.stresstest.util.ValidationUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserUseCase userUseCase;
    private final PostUseCase postUseCase;
    private final S3UseCase s3UseCase;
    private final LikeUseCase likeUseCase;
    private final CommentUseCase commentUseCase;

    private final RateLimitUtil rateLimitUtil;
    private final ValidationUtil validationUtil;

    public PostInfoDto getPosts(int page){

        int pageSize = 5;

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Post> postPage = postUseCase.findAll(pageable);

        List<PostResDto> postData = postPage.getContent().stream()
                .map(PostResDto::create)
                .collect(Collectors.toList());

        boolean hasMore = postPage.hasNext();

        return PostInfoDto.create(postData,hasMore);
    }

    //TODO: Redis work
    @Transactional
    public PostCommentsDto getPostWithComments(Long postId, HttpServletRequest request){

        Post post = postUseCase.findById(postId);

        String key = rateLimitUtil.generateRequestKey(postId, request);
        if (rateLimitUtil.isRequestAllowed(key)) post.increaseViews();

        List<Comment> comments = commentUseCase.findCommentsByPostId(postId);
        List<CommentsResDto> commentsResDtos = comments.stream()
                .map(CommentsResDto::create)
                .collect(Collectors.toList());

        return PostCommentsDto.create(commentsResDtos, PostResDto.create(post));
    }

    @Transactional
    public PostResDto createPost(Long userId, String title, String content, MultipartFile image){

        User user = userUseCase.findById(userId);

        validationUtil.validateTitleLength(title);
        validationUtil.validateContentLength(content);

        String url = s3UseCase.uploadImage(image, userId);

        Post post = postRepository.save(
                Post.builder()
                        .user(user)
                        .title(title)
                        .content(content)
                        .url(url)
                        .build()
        );

        return PostResDto.create(post);
    }

    @Transactional
    public PostResDto updatePost(Long userId, String title, String content, MultipartFile image){

        User user = userUseCase.findById(userId);
        Post post = postUseCase.findByUserId(user.getId());

        post.updateTitle(title);
        post.updateContent(content);

        String newUrl = s3UseCase.replaceImage(post.getUrl(), image, post.getId());
        post.updateUrl(newUrl);

        return PostResDto.create(post);
    }

    @Transactional
    public void deletePost(Long userId, Long postId){

        User user = userUseCase.findById(userId);
        Post post = postUseCase.findById(postId);

        s3UseCase.deleteImage(user.getProfileUrl());
        s3UseCase.deleteImage(post.getUrl());
        likeUseCase.remove(userId, postId);

    }

    @Transactional
    public PostResDto likesPost(Long userId, Long postId){

        User user = userUseCase.findById(userId);
        Post post = postUseCase.findById(postId);

        Like like = likeUseCase.findByUserAndPostId(userId, postId).orElse(null);

        if (like == null)post.increaseLikes();
        else post.decreaseLikes();

        return PostResDto.create(post);
    }
}
