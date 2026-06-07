package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.SystemConfigureCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigureCategoryRepository extends JpaRepository<SystemConfigureCategory, String> {
}
