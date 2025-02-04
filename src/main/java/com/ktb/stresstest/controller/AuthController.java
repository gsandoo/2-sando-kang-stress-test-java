package com.ktb.stresstest.controller;

import com.ktb.stresstest.dto.ResponseDto;
import com.ktb.stresstest.dto.auth.res.SigninDto;
import com.ktb.stresstest.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/signin",  consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<SigninDto> signin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("nickname") String nickname,
            @RequestPart(value = "profile" ,required = false) MultipartFile profile
    ){
        return ResponseDto.created(authService.handleSignUp(email,password,nickname,profile));
    }
}
