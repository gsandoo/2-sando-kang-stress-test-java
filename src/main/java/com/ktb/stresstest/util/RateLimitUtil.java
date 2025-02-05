package com.ktb.stresstest.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import static com.ktb.stresstest.constant.Constants.LIMIT_TIME_MS;

@Component
@Slf4j
public class RateLimitUtil {
    private static final ConcurrentHashMap<String, Long> memoryStore = new ConcurrentHashMap<>();

    public static String getClientIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    public String generateRequestKey(Long postId, HttpServletRequest request) {
        String clientIp = getClientIp(request);
        return "post:" + postId + ":ip:" + clientIp;
    }

    public boolean isRequestAllowed(String key) {
        long currentTime = System.currentTimeMillis();

        if (memoryStore.containsKey(key)) {
            long lastRequestTime = memoryStore.get(key);
            if (currentTime - lastRequestTime < LIMIT_TIME_MS) {
                log.info("IP 요청 제한: {}", key);
                return false; // 중복 요청 방지
            }
        }

        // 새로운 요청 저장
        memoryStore.put(key, currentTime);
        return true;
    }
}
