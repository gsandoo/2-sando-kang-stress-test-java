package com.ktb.stresstest.usecase;

import com.ktb.stresstest.annotation.UseCase;
import com.ktb.stresstest.domain.Like;

import java.util.Optional;

@UseCase
public interface LikeUseCase {

    void remove(Long userId, Long popupId);
    Optional<Like> findByUserAndPostId(Long userId, Long postId);
}
