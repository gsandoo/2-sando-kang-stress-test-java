package com.ktb.stresstest.interceptor.pre;

import com.ktb.stresstest.exception.CommonException;
import com.ktb.stresstest.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class UserIdInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication = {}", authentication);
        if (authentication == null) {
            throw new CommonException(ErrorCode.EMPTY_AUTHENTICATION);
        }
        request.setAttribute("USER_ID", authentication.getName());
        log.info("USER_ID = {}", authentication.getName());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
