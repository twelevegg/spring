package com.twelvegg.aicc.mydatabase.member.service;

import com.twelvegg.aicc.mydatabase.member.domain.Member;
import com.twelvegg.aicc.mydatabase.member.dto.MemberResponseDto;
import com.twelvegg.aicc.mydatabase.member.dto.MemberCallStatsDto;
import com.twelvegg.aicc.mydatabase.call.dto.CallDetailResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;

public interface MemberService {
    MemberResponseDto findById(Long id);

    Member save(Member member);

    MemberCallStatsDto getMemberCallStats(Long memberId);

    Page<CallDetailResponseDto> getMemberCallHistory(Long memberId, LocalDate date, Pageable pageable);

    Long getMemberCallCount(Long memberId, LocalDate date);
}
