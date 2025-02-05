package com.ktb.stresstest.usecase;

import com.ktb.stresstest.annotation.UseCase;
import com.ktb.stresstest.domain.User;

@UseCase
public interface UserUseCase {

    User findById(Long userId);

    User findByEmail(String email);
}
