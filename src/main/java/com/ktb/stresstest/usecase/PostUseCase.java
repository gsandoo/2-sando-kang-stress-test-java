package com.ktb.stresstest.usecase;

import com.ktb.stresstest.annotation.UseCase;
import com.ktb.stresstest.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@UseCase
public interface PostUseCase {
    Post findById(Long postId);
    Post findByUserId(Long userId);
    Page findAll(Pageable pageable);

}
