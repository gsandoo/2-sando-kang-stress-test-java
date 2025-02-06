package com.ktb.stresstest.service;

import com.ktb.stresstest.domain.User;
import com.ktb.stresstest.domain.UserProfile;
import com.ktb.stresstest.dto.auth.res.JwtTokenDto;
import com.ktb.stresstest.dto.auth.res.UserInfoDto;
import com.ktb.stresstest.dto.auth.res.UserResDto;
import com.ktb.stresstest.exception.CommonException;
import com.ktb.stresstest.exception.ErrorCode;
import com.ktb.stresstest.repository.UserProfileRepository;
import com.ktb.stresstest.repository.UserRepository;
import com.ktb.stresstest.security.util.JwtUtil;
import com.ktb.stresstest.type.EUserRole;
import com.ktb.stresstest.usecase.S3UseCase;
import com.ktb.stresstest.usecase.UserUseCase;
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

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ValidationUtil validationUtil;
    private final JwtUtil jwtUtil;

    private final S3UseCase s3UseCase;
    private final UserUseCase userUseCase;

    @Transactional
    public UserInfoDto handleSignUp(String email, String password, String nickname, MultipartFile profile){

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
            String url = s3UseCase.uploadImage(profile, newUser.getId());

            userProfileRepository.save(
                    UserProfile.builder()
                            .user(newUser)
                            .createdAt(LocalDateTime.now())
                            .editedAt(LocalDateTime.now())
                            .imageUrl(url)
                            .build()
            );

            newUser.updateProfileImage(url);
        }

        return buildUser(newUser);
    }

    @Transactional
    public UserInfoDto handleLogin(String email, String password){
        validationUtil.validateEmail(email);
        validationUtil.validatePassword(password);

        User user = userUseCase.findByEmail(email);

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new CommonException(ErrorCode.INVALID_LOGIN);
        }

        return buildUser(user);
    }

    @Transactional
    //TODO: must delete post, comment, likes from databases.
    public String handleWithdraw(Long userId){
        User user = userUseCase.findById(userId);

        userRepository.delete(user);
        user.deleteProfileImage();


        return "회원 탈퇴가 정상적으로 되었습니다.";
    }

    @Transactional
    public UserInfoDto updateProfile(Long userId, String nickname, MultipartFile profile){
        User user = userUseCase.findById(userId);

        String updatedUrl = (user.getProfileUrl() != null)
                ? s3UseCase.replaceImage(user.getProfileUrl(), profile, userId)
                : s3UseCase.uploadImage(profile, userId);

        user.updateProfileImage(updatedUrl);

        user.updateUserNickname(nickname);

        return buildUser(user);
    }

    @Transactional
    public UserInfoDto updatePassword(Long userId, String password){
        User user = userUseCase.findById(userId);

        validationUtil.validatePassword(password);

        user.updatePassword(bCryptPasswordEncoder.encode(password));

        return buildUser(user);
    }

    @Transactional
    private UserInfoDto buildUser(User newUser){
        UserResDto userResponse = UserResDto.create(newUser);
        JwtTokenDto jwtToken = jwtUtil.generateToken(newUser.getId(), EUserRole.USER);
        newUser.updateRefreshToken(jwtToken.refreshToken());

        return UserInfoDto.create(userResponse,jwtToken);
    }
}
