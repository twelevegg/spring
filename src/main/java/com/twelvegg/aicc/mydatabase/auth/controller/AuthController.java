package com.twelvegg.aicc.mydatabase.auth.controller;

import com.twelvegg.aicc.mydatabase.auth.dto.AuthDto;
import com.twelvegg.aicc.mydatabase.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증", description = "인증 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/signup")
    public ResponseEntity<AuthDto.TokenResponse> signUp(@RequestBody AuthDto.SignUpRequest request) {
        return ResponseEntity.ok(authService.signUp(request));
    }

    @Operation(summary = "로그인", description = "로그인 API")
    @PostMapping("/login")
    public ResponseEntity<AuthDto.TokenResponse> login(@RequestBody AuthDto.LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "리프레시 토큰", description = "리프레시 토큰 재발급 API")
    @PostMapping("/refresh")
    public ResponseEntity<AuthDto.TokenResponse> refresh(@RequestBody AuthDto.RefreshRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request.refreshToken()));
    }

    @Operation(summary = "내 정보 조회", description = "로그인한 사용자의 정보를 조회하는 API")
    @GetMapping("/me")
    public ResponseEntity<AuthDto.MemberInfoResponse> getMe(@RequestAttribute("memberId") Long memberId) {
        return ResponseEntity.ok(authService.getMe(memberId));
    }

    @Operation(summary = "비밀번호 변경", description = "로그인한 사용자의 비밀번호를 변경하는 API")
    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestAttribute("memberId") Long memberId,
            @RequestBody AuthDto.PasswordChangeRequest request) {
        authService.changePassword(memberId, request);
        return ResponseEntity.ok().build();
    }
}
