package com.twelvegg.aicc.mydatabase.caselibrary.repository;

import com.twelvegg.aicc.mydatabase.caselibrary.domain.CaseLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseLibraryRepository extends JpaRepository<CaseLibrary, String> {
    List<CaseLibrary> findAllByOrderByDateDescCaseLibraryIdDesc();

    Optional<CaseLibrary> findTopByOrderByCaseLibraryIdDesc();
}
