package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.WorkCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkCalendarRepository extends JpaRepository<WorkCalendar, String> {
}
