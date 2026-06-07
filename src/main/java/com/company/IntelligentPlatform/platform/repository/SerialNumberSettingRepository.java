package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.SerialNumberSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerialNumberSettingRepository extends JpaRepository<SerialNumberSetting, String> {
}
