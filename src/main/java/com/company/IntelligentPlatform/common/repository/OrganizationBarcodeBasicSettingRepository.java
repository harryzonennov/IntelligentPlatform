package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.OrganizationBarcodeBasicSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationBarcodeBasicSettingRepository extends JpaRepository<OrganizationBarcodeBasicSetting, String> {
}
