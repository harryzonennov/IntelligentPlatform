package com.company.IntelligentPlatform.production.repository;

import com.company.IntelligentPlatform.production.model.ProdProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdProcessRepository extends JpaRepository<ProdProcess, String> {
}
