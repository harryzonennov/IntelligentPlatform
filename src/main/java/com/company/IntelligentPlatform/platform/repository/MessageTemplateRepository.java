package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, String> {
}
