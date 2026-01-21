package com.twelvegg.aicc.mydatabase.member.repository;

import com.twelvegg.aicc.mydatabase.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
