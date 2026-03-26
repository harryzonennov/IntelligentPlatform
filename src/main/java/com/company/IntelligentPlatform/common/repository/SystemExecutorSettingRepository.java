package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.SystemExecutorSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemExecutorSettingRepository extends JpaRepository<SystemExecutorSetting, String> {
}
