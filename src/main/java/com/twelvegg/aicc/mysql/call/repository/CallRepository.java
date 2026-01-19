package com.twelvegg.aicc.mysql.call.repository;

import com.twelvegg.aicc.mysql.call.domain.Call;
import com.twelvegg.aicc.config.annotation.db.Mysql;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Mysql
@Repository
public interface CallRepository extends JpaRepository<Call, Long> {
}
