package com.ktb.stresstest.service;

import com.ktb.stresstest.domain.Post;
import com.ktb.stresstest.exception.CommonException;
import com.ktb.stresstest.exception.ErrorCode;
import com.ktb.stresstest.repository.PostRepository;
import com.ktb.stresstest.usecase.PostUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostUseCaseService implements PostUseCase {

    private final PostRepository postRepository;

    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(()->new CommonException(ErrorCode.NOT_FOUND_POST));
    }

    @Override
    public Post findByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public Page findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

}
