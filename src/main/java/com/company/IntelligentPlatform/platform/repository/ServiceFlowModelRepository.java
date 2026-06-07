package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.ServiceFlowModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceFlowModelRepository extends JpaRepository<ServiceFlowModel, String> {
}
