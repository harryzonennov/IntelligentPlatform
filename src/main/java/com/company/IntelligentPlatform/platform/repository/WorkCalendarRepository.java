package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.WorkCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkCalendarRepository extends JpaRepository<WorkCalendar, String> {
}
