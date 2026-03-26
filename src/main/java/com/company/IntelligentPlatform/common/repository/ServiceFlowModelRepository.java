package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.ServiceFlowModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceFlowModelRepository extends JpaRepository<ServiceFlowModel, String> {
}
