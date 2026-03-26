package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.SystemAuthorizationObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemAuthorizationObjectRepository extends JpaRepository<SystemAuthorizationObject, String> {
}
