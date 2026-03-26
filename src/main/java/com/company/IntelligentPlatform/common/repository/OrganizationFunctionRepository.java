package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.OrganizationFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationFunctionRepository extends JpaRepository<OrganizationFunction, String> {
}
