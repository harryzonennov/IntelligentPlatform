package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigureCategoryRepository extends JpaRepository<SystemConfigureCategory, String> {
}
