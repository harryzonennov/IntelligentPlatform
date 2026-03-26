package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.ActionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionCodeRepository extends JpaRepository<ActionCode, String> {
}
