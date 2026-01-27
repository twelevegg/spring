package com.twelvegg.aicc.mydatabase.auth.service;

import com.twelvegg.aicc.mydatabase.auth.dto.AuthDto;

public interface AuthService {
    AuthDto.TokenResponse signUp(AuthDto.SignUpRequest request);

    AuthDto.TokenResponse login(AuthDto.LoginRequest request);

    AuthDto.TokenResponse refreshToken(String refreshTokenInput);

    AuthDto.MemberInfoResponse getMe(Long memberId);

    void changePassword(Long memberId, AuthDto.PasswordChangeRequest request);
}
