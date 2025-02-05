package com.ktb.stresstest.util;

import com.ktb.stresstest.exception.CommonException;
import com.ktb.stresstest.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.ktb.stresstest.constant.Constants.EMAIL_REGEX;
import static com.ktb.stresstest.constant.Constants.PASSWORD_REGEX;
@Component
public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    public void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new CommonException(ErrorCode.EMAIL_ERROR);
        }
    }

    public void validatePassword(String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new CommonException(ErrorCode.PASSWORD_ERROR);
        }
    }

    public void validateTitleLength(String title) {
        if (title.length() > 26) {
            throw new CommonException(ErrorCode.TITLE_ERROR);
        }
    }

    public void validateContentLength(String content) {
        if (content.length() > 100) {
            throw new CommonException(ErrorCode.CONTENT_ERROR);
        }
    }
}
