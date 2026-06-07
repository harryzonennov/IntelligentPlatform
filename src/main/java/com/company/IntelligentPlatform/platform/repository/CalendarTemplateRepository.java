package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.CalendarTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarTemplateRepository extends JpaRepository<CalendarTemplate, String> {
}
