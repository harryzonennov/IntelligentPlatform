package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.GenericServiceEntityNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericServiceEntityNodeRepository extends JpaRepository<GenericServiceEntityNode, String> {
}
