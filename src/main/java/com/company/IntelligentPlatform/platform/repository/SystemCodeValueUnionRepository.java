package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.SystemCodeValueUnion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemCodeValueUnionRepository extends JpaRepository<SystemCodeValueUnion, String> {
}
