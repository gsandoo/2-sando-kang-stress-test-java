package com.ktb.stresstest.security.manager;

import com.ktb.stresstest.dto.auth.res.JwtTokenDto;
import com.ktb.stresstest.repository.UserRepository;
import com.ktb.stresstest.security.info.CustomUserDetails;
import com.ktb.stresstest.security.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class DefaultSignInSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        JwtTokenDto jwtTokenDto = jwtUtil.generateToken(userDetails.getId(), userDetails.getRole());

        userRepository.updateRefreshToken(userDetails.getId(), jwtTokenDto.refreshToken());
        setSuccessAppResponse(response, jwtTokenDto);
    }

    private void setSuccessAppResponse(HttpServletResponse response, JwtTokenDto jwtTokenDto) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data",
                Map.of("accessToken", jwtTokenDto.accessToken(), "refreshToken", jwtTokenDto.refreshToken()));
        result.put("error", null);

        response.getWriter().write(JSONValue.toJSONString(result));
    }
}
