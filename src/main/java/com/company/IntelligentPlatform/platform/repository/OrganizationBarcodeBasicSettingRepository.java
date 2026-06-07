package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.OrganizationBarcodeBasicSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationBarcodeBasicSettingRepository extends JpaRepository<OrganizationBarcodeBasicSetting, String> {
}
