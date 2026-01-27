package com.twelvegg.aicc.mydatabase.auth.repository;

import com.twelvegg.aicc.mydatabase.auth.domain.RefreshToken;
import com.twelvegg.aicc.mydatabase.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByMember(Member member);
}
