package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.GenericServiceEntityNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericServiceEntityNodeRepository extends JpaRepository<GenericServiceEntityNode, String> {
}
