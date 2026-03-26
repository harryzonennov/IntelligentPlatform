package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.SystemCodeValueUnion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemCodeValueUnionRepository extends JpaRepository<SystemCodeValueUnion, String> {
}
