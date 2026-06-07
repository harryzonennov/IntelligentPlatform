package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.FlowRouter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowRouterRepository extends JpaRepository<FlowRouter, String> {
}
