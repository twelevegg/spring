package com.twelvegg.aicc.postgres.cdr.repository;

import com.twelvegg.aicc.config.annotation.db.Postgresql;
import com.twelvegg.aicc.postgres.cdr.domain.Cdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Postgresql
@Repository
public interface CdrRepository extends JpaRepository<Cdr, Long> {
}
