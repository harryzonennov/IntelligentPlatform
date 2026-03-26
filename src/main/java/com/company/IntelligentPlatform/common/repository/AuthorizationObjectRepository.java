package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationObjectRepository extends JpaRepository<AuthorizationObject, String> {
}
