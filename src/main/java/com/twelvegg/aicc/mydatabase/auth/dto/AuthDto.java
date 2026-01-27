package com.twelvegg.aicc.mydatabase.auth.dto;

import lombok.Builder;

public class AuthDto {

    public record SignUpRequest(String tenantName, String email, String password, String memberName) {
    }

    public record LoginRequest(String tenantName, String email, String password) {
    }

    public record PasswordChangeRequest(String currentPassword, String newPassword) {
    }

    @Builder
    public record TokenResponse(String accessToken, String refreshToken) {
    }

    public record RefreshRequest(String refreshToken) {
    }

    @Builder
    public record MemberInfoResponse(
            Long id,
            String email,
            String name,
            String tenantName,
            String role,
            String status,
            java.time.LocalDate hireDate) {
    }
}
