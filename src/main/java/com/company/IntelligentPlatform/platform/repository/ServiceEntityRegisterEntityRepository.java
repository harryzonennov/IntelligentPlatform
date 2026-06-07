package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.ServiceEntityRegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceEntityRegisterEntityRepository extends JpaRepository<ServiceEntityRegisterEntity, String> {
}
