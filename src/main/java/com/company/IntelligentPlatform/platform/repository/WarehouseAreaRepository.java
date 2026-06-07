package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.WarehouseArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseAreaRepository extends JpaRepository<WarehouseArea, String> {
}
