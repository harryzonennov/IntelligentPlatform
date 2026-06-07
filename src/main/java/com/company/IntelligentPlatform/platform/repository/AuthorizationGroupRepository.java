package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.AuthorizationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationGroupRepository extends JpaRepository<AuthorizationGroup, String> {
}
