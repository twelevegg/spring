package com.twelvegg.aicc.cdr.repository;

import com.twelvegg.aicc.cdr.domain.Cdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdrRepository extends JpaRepository<Cdr, Long> {
}
