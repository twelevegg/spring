package com.twelvegg.aicc.mydatabase.call.repository;

import com.twelvegg.aicc.mydatabase.call.domain.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Long> {
}
