package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.LogonInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogonInfoRepository extends JpaRepository<LogonInfo, String> {
}
