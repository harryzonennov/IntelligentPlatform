package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardMaterialUnitRepository extends JpaRepository<StandardMaterialUnit, String> {
}
