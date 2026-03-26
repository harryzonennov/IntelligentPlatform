package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialStockKeepUnitRepository extends JpaRepository<MaterialStockKeepUnit, String> {
}
