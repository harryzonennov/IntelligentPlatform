package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.ServiceVersionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceVersionRecordRepository extends JpaRepository<ServiceVersionRecord, String> {
}
