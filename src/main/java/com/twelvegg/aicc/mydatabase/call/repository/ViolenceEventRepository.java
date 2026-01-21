package com.twelvegg.aicc.mydatabase.call.repository;

import com.twelvegg.aicc.mydatabase.call.domain.ViolenceEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViolenceEventRepository extends JpaRepository<ViolenceEvent, Long> {
}
