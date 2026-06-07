package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.LogonInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogonInfoRepository extends JpaRepository<LogonInfo, String> {
}
