package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.CalendarSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarSettingRepository extends JpaRepository<CalendarSetting, String> {
}
