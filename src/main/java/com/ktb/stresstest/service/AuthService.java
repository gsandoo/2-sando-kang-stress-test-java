package com.ktb.stresstest.service;

import com.ktb.stresstest.domain.User;
import com.ktb.stresstest.domain.UserProfile;
import com.ktb.stresstest.dto.auth.res.JwtTokenDto;
import com.ktb.stresstest.dto.auth.res.SigninDto;
import com.ktb.stresstest.dto.auth.res.UserResDto;
import com.ktb.stresstest.exception.CommonException;
import com.ktb.stresstest.exception.ErrorCode;
import com.ktb.stresstest.repository.UserProfileRepository;
import com.ktb.stresstest.repository.UserRepository;
import com.ktb.stresstest.security.util.JwtUtil;
import com.ktb.stresstest.type.EUserRole;
import com.ktb.stresstest.util.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ValidationUtil validationUtil;
    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final S3Service s3Service;

    @Transactional
    public SigninDto handleSignUp(String email, String password, String nickname, MultipartFile profile){

        validationUtil.validateEmail(email);
        validationUtil.validatePassword(password);

        if (userRepository.existsByEmail(email)) throw new CommonException(ErrorCode.DUPLICATED_EMAIL);
        if (userRepository.existsByNickname(nickname)) throw new CommonException(ErrorCode.DUPLICATED_NICKNAME);

        User newUser = userRepository.save(User.builder()
                        .email(email)
                        .password(bCryptPasswordEncoder.encode(password))
                        .nickname(nickname)
                        .role(EUserRole.USER)// 현재는 모든 유저가 USER
                        .build()
        );

        if (profile != null){
            String url = s3Service.uploadUserProfile(profile, newUser.getId());
            UserProfile userProfile = UserProfile.builder()
                    .user(newUser)
                    .createdAt(LocalDateTime.now())
                    .editedAt(LocalDateTime.now())
                    .imageUrl(url)
                    .build();
            userProfileRepository.save(userProfile);
            newUser.updateProfileImage(url);
        }

        return buildUser(newUser);
    }







    private SigninDto buildUser(User newUser){
        UserResDto userResponse = UserResDto.create(newUser);
        JwtTokenDto jwtToken = jwtUtil.generateToken(newUser.getId(), EUserRole.USER);
        newUser.updateRefreshToken(jwtToken.refreshToken());

        return SigninDto.create(userResponse,jwtToken);
    }
}
