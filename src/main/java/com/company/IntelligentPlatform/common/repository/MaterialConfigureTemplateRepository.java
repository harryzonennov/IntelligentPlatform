package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.MaterialConfigureTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialConfigureTemplateRepository extends JpaRepository<MaterialConfigureTemplate, String> {
}
