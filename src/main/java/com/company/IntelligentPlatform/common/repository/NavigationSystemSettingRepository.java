package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.NavigationSystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavigationSystemSettingRepository extends JpaRepository<NavigationSystemSetting, String> {
}
