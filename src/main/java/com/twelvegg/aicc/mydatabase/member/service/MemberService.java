package com.twelvegg.aicc.mydatabase.member.service;

import com.twelvegg.aicc.mydatabase.member.domain.Member;
import com.twelvegg.aicc.mydatabase.member.dto.MemberResponseDto;

public interface MemberService {
    MemberResponseDto findById(Long id);

    Member save(Member member);
}
