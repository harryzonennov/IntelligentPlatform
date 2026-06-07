package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.CalendarSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarSettingRepository extends JpaRepository<CalendarSetting, String> {
}
