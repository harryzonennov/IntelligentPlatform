package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String> {
}
