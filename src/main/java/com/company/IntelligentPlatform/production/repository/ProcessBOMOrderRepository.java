package com.company.IntelligentPlatform.production.repository;

import com.company.IntelligentPlatform.production.model.ProcessBOMOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessBOMOrderRepository extends JpaRepository<ProcessBOMOrder, String> {
}
