package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.FlowRouter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowRouterRepository extends JpaRepository<FlowRouter, String> {
}
