package com.ktb.stresstest.service;

import com.ktb.stresstest.domain.Like;
import com.ktb.stresstest.repository.LikeRepository;
import com.ktb.stresstest.usecase.LikeUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeUseCaseService implements LikeUseCase {

    private final LikeRepository likeRepository;

    @Override
    public void remove(Long userId, Long popupId) {
        likeRepository.deleteByUserAndPost(userId, popupId);
    }

    @Override
    public Optional<Like> findByUserAndPostId(Long userId, Long postId) {
        return Optional.ofNullable(likeRepository.findByUserAndPostId(userId, postId));
    }
}
