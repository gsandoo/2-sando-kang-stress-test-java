package com.ktb.stresstest.controller;

import com.ktb.stresstest.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/health")
public class HealthCheckController {

    @GetMapping("")
    public ResponseDto<?> healthCheck(){
        return ResponseDto.ok("health 체크 성공");
    }

    @GetMapping("/readiness")
    public ResponseDto<?> readiness(){
        return ResponseDto.ok("readiness 체크 성공");
    }

    @GetMapping("/startup")
    public ResponseDto<?> startup(){
        return ResponseDto.ok("startup 체크 성공");
    }

    @GetMapping("/liveness")
    public ResponseDto<?> liveness(){
        return ResponseDto.ok("liveness 체크 성공");
    }
}
