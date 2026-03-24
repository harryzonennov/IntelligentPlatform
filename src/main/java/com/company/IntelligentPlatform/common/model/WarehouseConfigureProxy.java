package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [Warehouse]
 *
 * @author
 * @date Sat Nov 14 15:20:29 CST 2015
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class WarehouseConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of Warehouse [ROOT] node
		ServiceEntityConfigureMap warehouseConfigureMap = new ServiceEntityConfigureMap();
		warehouseConfigureMap.setParentNodeName(" ");
		warehouseConfigureMap.setNodeName(Warehouse.NODENAME);
		warehouseConfigureMap.setNodeType(Warehouse.class);
		warehouseConfigureMap.setTableName(Warehouse.SENAME);
		warehouseConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		warehouseConfigureMap.addNodeFieldMap("warehouseType", int.class);
		warehouseConfigureMap.addNodeFieldMap("operationType", int.class);
		warehouseConfigureMap.addNodeFieldMap("mainContactUUID",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("telephone",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("address",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("switchFlag", int.class);
		warehouseConfigureMap.addNodeFieldMap("postcode",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("cityName",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("refCityUUID",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("townZone",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("parentOrganizationUUID",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("organizationFunction", int.class);
		warehouseConfigureMap.addNodeFieldMap("organType", int.class);
		warehouseConfigureMap.addNodeFieldMap("organLevel", int.class);
		warehouseConfigureMap.addNodeFieldMap("regularType", int.class);
		warehouseConfigureMap.addNodeFieldMap("accountType", int.class);
		warehouseConfigureMap.addNodeFieldMap("contactMobileNumber", String.class);
		warehouseConfigureMap.addNodeFieldMap("refOrganizationFunction", String.class);
		warehouseConfigureMap.addNodeFieldMap("refCashierUUID",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("refAccountantUUID",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("refFinOrgUUID",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("streetName",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("houseNumber",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("fax", java.lang.String.class);
		warehouseConfigureMap
				.addNodeFieldMap("email", java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("webPage",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("taxNumber",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("bankAccount",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("depositBank",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("length", double.class);
		warehouseConfigureMap.addNodeFieldMap("width", double.class);
		warehouseConfigureMap.addNodeFieldMap("height", double.class);
		warehouseConfigureMap.addNodeFieldMap("area", double.class);
		warehouseConfigureMap.addNodeFieldMap("volume", double.class);
		warehouseConfigureMap.addNodeFieldMap("addressOnMap",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("fullName",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("longitude",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("latitude",
				java.lang.String.class);
		warehouseConfigureMap.addNodeFieldMap("hasFootStep", boolean.class);
		warehouseConfigureMap.addNodeFieldMap("restrictedGoodsFlag",
				boolean.class);
		warehouseConfigureMap.addNodeFieldMap("forbiddenGoodsFlag",
				boolean.class);
		warehouseConfigureMap.addNodeFieldMap("operationMode", int.class);
		warehouseConfigureMap.addNodeFieldMap("systemDefault", int.class);
		warehouseConfigureMap.addNodeFieldMap("refMaterialCategory", int.class);
		seConfigureMapList.add(warehouseConfigureMap);
		// Init configuration of Warehouse [WarehouseArea] node
		ServiceEntityConfigureMap warehouseAreaConfigureMap = new ServiceEntityConfigureMap();
		warehouseAreaConfigureMap.setParentNodeName(Warehouse.NODENAME);
		warehouseAreaConfigureMap.setNodeName(WarehouseArea.NODENAME);
		warehouseAreaConfigureMap.setNodeType(WarehouseArea.class);
		warehouseAreaConfigureMap.setTableName(WarehouseArea.NODENAME);
		warehouseAreaConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		warehouseAreaConfigureMap.addNodeFieldMap("storeType", int.class);
		warehouseAreaConfigureMap.addNodeFieldMap("operationType", int.class);
		warehouseAreaConfigureMap.addNodeFieldMap("space", double.class);
		warehouseAreaConfigureMap.addNodeFieldMap("grossWeight", double.class);
		warehouseAreaConfigureMap.addNodeFieldMap("switchFlag", int.class);
		warehouseAreaConfigureMap.addNodeFieldMap("length", double.class);
		warehouseAreaConfigureMap.addNodeFieldMap("width", double.class);
		warehouseAreaConfigureMap.addNodeFieldMap("height", double.class);
		warehouseAreaConfigureMap.addNodeFieldMap("area", double.class);
		warehouseAreaConfigureMap.addNodeFieldMap("volume", double.class);
		warehouseAreaConfigureMap.addNodeFieldMap("hasFootStep", boolean.class);
		warehouseAreaConfigureMap.addNodeFieldMap("restrictedGoodsFlag",
				boolean.class);
		warehouseAreaConfigureMap.addNodeFieldMap("forbiddenGoodsFlag",
				boolean.class);
		warehouseAreaConfigureMap.addNodeFieldMap("operationMode", int.class);
		seConfigureMapList.add(warehouseAreaConfigureMap);

		// Init configuration of Warehouse [WarehouseStoreSetting] node
		ServiceEntityConfigureMap warehouseStoreSettingConfigureMap = new ServiceEntityConfigureMap();
		warehouseStoreSettingConfigureMap.setParentNodeName(Warehouse.NODENAME);
		warehouseStoreSettingConfigureMap
				.setNodeName(WarehouseStoreSetting.NODENAME);
		warehouseStoreSettingConfigureMap
				.setNodeType(WarehouseStoreSetting.class);
		warehouseStoreSettingConfigureMap
				.setTableName(WarehouseStoreSetting.NODENAME);
		warehouseStoreSettingConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		warehouseStoreSettingConfigureMap.addNodeFieldMap("refMaterialSKUUUID",
				java.lang.String.class);
		warehouseStoreSettingConfigureMap.addNodeFieldMap("maxSafeStoreAmount",
				double.class);
		warehouseStoreSettingConfigureMap.addNodeFieldMap(
				"maxSafeStoreUnitUUID", java.lang.String.class);
		warehouseStoreSettingConfigureMap.addNodeFieldMap("minSafeStoreAmount",
				double.class);
		warehouseStoreSettingConfigureMap.addNodeFieldMap(
				"minSafeStoreUnitUUID", java.lang.String.class);
		warehouseStoreSettingConfigureMap.addNodeFieldMap(
				"safeStoreCalculateCategory", int.class);
		warehouseStoreSettingConfigureMap.addNodeFieldMap("maxStoreRatio",
				double.class);
		warehouseStoreSettingConfigureMap.addNodeFieldMap("minStoreRatio",
				double.class);
		warehouseStoreSettingConfigureMap.addNodeFieldMap(
				"targetAverageStoreAmount", double.class);
		warehouseStoreSettingConfigureMap.addNodeFieldMap(
				"targetAverageStoreUnitUUID", java.lang.String.class);
		warehouseStoreSettingConfigureMap.addNodeFieldMap(
				"refActiveSysWarnMsgUUID", java.lang.String.class);
		warehouseStoreSettingConfigureMap.addNodeFieldMap("errorType",
				int.class);
		warehouseStoreSettingConfigureMap.addNodeFieldMap("dataSourceType",
				int.class);
		seConfigureMapList.add(warehouseStoreSettingConfigureMap);

		// Init configuration of Warehouse [warehouseAttachment] node
		ServiceEntityConfigureMap warehouseAttachmentConfigureMap = new ServiceEntityConfigureMap();
		warehouseAttachmentConfigureMap
				.setParentNodeName(Warehouse.NODENAME);
		warehouseAttachmentConfigureMap
				.setNodeName(WarehouseAttachment.NODENAME);
		warehouseAttachmentConfigureMap
				.setNodeType(WarehouseAttachment.class);
		warehouseAttachmentConfigureMap
				.setTableName(WarehouseAttachment.NODENAME);
		warehouseAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		warehouseAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		warehouseAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(warehouseAttachmentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
