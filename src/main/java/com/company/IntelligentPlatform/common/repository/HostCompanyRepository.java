package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.HostCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostCompanyRepository extends JpaRepository<HostCompany, String> {
}
