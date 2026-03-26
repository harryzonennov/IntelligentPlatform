package com.company.IntelligentPlatform.production.repository;

import com.company.IntelligentPlatform.production.model.ProductionResourceUnion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionResourceUnionRepository extends JpaRepository<ProductionResourceUnion, String> {
}
