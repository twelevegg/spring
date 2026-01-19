package com.twelvegg.aicc.mydatabase.call.repository;

import com.twelvegg.aicc.mydatabase.call.domain.Call;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {
}
