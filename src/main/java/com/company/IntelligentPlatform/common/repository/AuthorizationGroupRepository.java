package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.AuthorizationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationGroupRepository extends JpaRepository<AuthorizationGroup, String> {
}
