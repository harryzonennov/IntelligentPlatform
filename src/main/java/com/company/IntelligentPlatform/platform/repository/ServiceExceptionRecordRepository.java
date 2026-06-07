package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.ServiceExceptionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceExceptionRecordRepository extends JpaRepository<ServiceExceptionRecord, String> {
}
