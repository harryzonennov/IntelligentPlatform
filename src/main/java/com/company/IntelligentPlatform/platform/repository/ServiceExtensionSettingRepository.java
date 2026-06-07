package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.ServiceExtensionSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceExtensionSettingRepository extends JpaRepository<ServiceExtensionSetting, String> {
}
