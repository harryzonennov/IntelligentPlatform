package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.CalendarTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarTemplateRepository extends JpaRepository<CalendarTemplate, String> {
}
