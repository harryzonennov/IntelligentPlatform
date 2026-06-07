package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.SystemAuthorizationObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemAuthorizationObjectRepository extends JpaRepository<SystemAuthorizationObject, String> {
}
