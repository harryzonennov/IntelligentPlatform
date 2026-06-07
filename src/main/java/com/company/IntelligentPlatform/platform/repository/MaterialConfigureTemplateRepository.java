package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.MaterialConfigureTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialConfigureTemplateRepository extends JpaRepository<MaterialConfigureTemplate, String> {
}
