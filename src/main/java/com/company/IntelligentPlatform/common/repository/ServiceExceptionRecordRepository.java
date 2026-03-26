package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.ServiceExceptionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceExceptionRecordRepository extends JpaRepository<ServiceExceptionRecord, String> {
}
