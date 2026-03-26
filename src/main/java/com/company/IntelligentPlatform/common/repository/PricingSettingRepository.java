package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.PricingSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingSettingRepository extends JpaRepository<PricingSetting, String> {
}
