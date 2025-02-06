package com.ktb.stresstest.controller;

import com.ktb.stresstest.annotation.UserId;
import com.ktb.stresstest.dto.ResponseDto;
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
    public ResponseDto<?> signin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("nickname") String nickname,
            @RequestPart(value = "profile" ,required = false) MultipartFile profile
    ){
        return ResponseDto.created(authService.handleSignUp(email,password,nickname,profile));
    }

    @PostMapping("/login")
    public ResponseDto<?> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        return ResponseDto.ok(authService.handleLogin(email, password));
    }

    @PostMapping("/withdraw")
    public ResponseDto<?> withdraw(
            @UserId Long userId
    ){
        return ResponseDto.ok(authService.handleWithdraw(userId));
    }

    //TODO: match with front code.
    //FIX: end point fixed. /nickname > /profile
    @PostMapping(value = "/profile",  consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<?> updateProfile(
            @UserId Long userId,
            @RequestParam("nickname") String nickname,
            @RequestPart(value = "profile" ,required = false) MultipartFile profile
    ){
        return ResponseDto.ok(authService.updateProfile(userId, nickname, profile));
    }

    @PostMapping("/password")
    public ResponseDto<?> updatePassword(
            @UserId Long userId,
            @RequestParam("password") String password
    ){
        return ResponseDto.ok(authService.updatePassword(userId, password));
    }

}
