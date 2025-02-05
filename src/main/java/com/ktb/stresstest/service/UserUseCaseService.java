package com.ktb.stresstest.service;

import com.ktb.stresstest.domain.User;
import com.ktb.stresstest.exception.CommonException;
import com.ktb.stresstest.exception.ErrorCode;
import com.ktb.stresstest.repository.UserRepository;
import com.ktb.stresstest.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUseCaseService implements UserUseCase {
    private final UserRepository userRepository;


    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_USER));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new CommonException(ErrorCode.FAILURE_LOGIN));
    }
}
