package com.twelvegg.aicc.mydatabase.auth.service;

import com.twelvegg.aicc.common.util.JwtTokenProvider;
import com.twelvegg.aicc.common.util.PasswordEncoder;
import com.twelvegg.aicc.exception.CustomException;
import com.twelvegg.aicc.exception.ErrorCode;
import com.twelvegg.aicc.mydatabase.auth.domain.RefreshToken;
import com.twelvegg.aicc.mydatabase.auth.dto.AuthDto;
import com.twelvegg.aicc.mydatabase.auth.repository.RefreshTokenRepository;
import com.twelvegg.aicc.mydatabase.member.domain.Member;
import com.twelvegg.aicc.mydatabase.member.repository.MemberRepository;
import com.twelvegg.aicc.mydatabase.tenant.domain.Tenant;
import com.twelvegg.aicc.mydatabase.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final TenantRepository tenantRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public AuthDto.TokenResponse signUp(AuthDto.SignUpRequest request) {
        if (memberRepository.findByEmail(request.email()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXISTING_EMAIL);
        }

        Tenant tenant = tenantRepository.findByName(request.tenantName())
                .orElseGet(() -> tenantRepository.save(
                        Tenant.builder()
                                .name(request.tenantName())
                                .status("ACTIVE")
                                .createdAt(LocalDateTime.now())
                                .build()));

        Member member = Member.builder()
                .tenant(tenant)
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .name(request.memberName() != null ? request.memberName() : "이름없음")
                .status("ACTIVE")
                .role("USER")
                .hireDate(java.time.LocalDate.now())
                .build();

        memberRepository.save(member);

        return generateTokens(member, tenant.getName());
    }

    @Override
    @Transactional
    public AuthDto.TokenResponse login(AuthDto.LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        if (!member.getTenant().getName().equals(request.tenantName())) {
            throw new CustomException(ErrorCode.TENANT_NOT_FOUND);
        }

        return generateTokens(member, member.getTenant().getName());
    }

    @Override
    @Transactional
    public AuthDto.TokenResponse refreshToken(String refreshTokenInput) {
        if (!jwtTokenProvider.validateToken(refreshTokenInput)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshTokenInput)
                .orElseThrow(() -> new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if (storedToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(storedToken);
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Member member = storedToken.getMember();

        String newAccessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getId(),
                member.getTenant().getName());

        return AuthDto.TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshTokenInput)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthDto.MemberInfoResponse getMe(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return AuthDto.MemberInfoResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .tenantName(member.getTenant().getName())
                .role(member.getRole())
                .status(member.getStatus())
                .hireDate(member.getHireDate())
                .build();
    }

    @Override
    @Transactional
    public void changePassword(Long memberId, AuthDto.PasswordChangeRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.currentPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        member.updatePassword(passwordEncoder.encode(request.newPassword()));
        memberRepository.save(member);
    }

    private AuthDto.TokenResponse generateTokens(Member member, String tenantName) {
        String accessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getId(), tenantName);
        String refreshTokenStr = jwtTokenProvider.createRefreshToken(member.getEmail(), member.getId(), tenantName);

        RefreshToken refreshToken = refreshTokenRepository.findByMember(member)
                .orElse(RefreshToken.builder().member(member).build());

        refreshToken.updateToken(refreshTokenStr, LocalDateTime.now().plusHours(3));
        refreshTokenRepository.save(refreshToken);

        return AuthDto.TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenStr)
                .build();
    }
}
