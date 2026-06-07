package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.NavigationSystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavigationSystemSettingRepository extends JpaRepository<NavigationSystemSetting, String> {
}
