package com.company.IntelligentPlatform.finance.repository;

import com.company.IntelligentPlatform.common.model.SystemResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemResourceRepository extends JpaRepository<SystemResource, String> {
}
