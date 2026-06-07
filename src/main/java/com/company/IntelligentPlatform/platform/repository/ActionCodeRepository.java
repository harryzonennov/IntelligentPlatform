package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.ActionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionCodeRepository extends JpaRepository<ActionCode, String> {
}
