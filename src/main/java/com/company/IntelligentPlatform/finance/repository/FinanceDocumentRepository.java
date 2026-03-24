package com.company.IntelligentPlatform.finance.repository;

import com.company.IntelligentPlatform.finance.model.FinanceDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceDocumentRepository extends JpaRepository<FinanceDocument, String> {
}
