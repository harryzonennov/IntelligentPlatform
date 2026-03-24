package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [OrganizationBarcodeBasicSetting]
 *
 * @author
 * @date Mon Mar 14 22:39:18 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class OrganizationBarcodeBasicSettingConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of OrganizationBarcodeBasicSetting [ROOT] node
		ServiceEntityConfigureMap organizationBarcodeBasicSettingConfigureMap = new ServiceEntityConfigureMap();
		organizationBarcodeBasicSettingConfigureMap.setParentNodeName(" ");
		organizationBarcodeBasicSettingConfigureMap
				.setNodeName(OrganizationBarcodeBasicSetting.NODENAME);
		organizationBarcodeBasicSettingConfigureMap
				.setNodeType(OrganizationBarcodeBasicSetting.class);
		organizationBarcodeBasicSettingConfigureMap
				.setTableName(OrganizationBarcodeBasicSetting.SENAME);
		organizationBarcodeBasicSettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		organizationBarcodeBasicSettingConfigureMap.addNodeFieldMap(
				"ean13CountryHead", int.class);
		organizationBarcodeBasicSettingConfigureMap.addNodeFieldMap(
				"ean13CompanyCode", int.class);
		organizationBarcodeBasicSettingConfigureMap.addNodeFieldMap(
				"refOrganizationUUID", java.lang.String.class);
		seConfigureMapList.add(organizationBarcodeBasicSettingConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
