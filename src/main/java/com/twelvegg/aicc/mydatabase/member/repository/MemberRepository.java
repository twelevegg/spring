package com.twelvegg.aicc.mydatabase.member.repository;

import com.twelvegg.aicc.mydatabase.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    long countByStatus(String status);

    List<Member> findByHireDateGreaterThanEqualOrderByHireDateDesc(LocalDate hireDate);
}
