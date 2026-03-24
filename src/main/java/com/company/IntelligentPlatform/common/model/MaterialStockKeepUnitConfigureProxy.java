package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.MaterialSKUUnitReference;
import com.company.IntelligentPlatform.common.model.MaterialSKUAttachment;
import com.company.IntelligentPlatform.common.model.MaterialSKUExtendProperty;

/**
 * Configure Proxy CLASS FOR Service Entity [MaterialStockKeepUnit]
 *
 * @author
 * @date Tue Jul 09 11:15:19 CST 2019
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class MaterialStockKeepUnitConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.coreFunction");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of MaterialStockKeepUnit [ROOT] node
		ServiceEntityConfigureMap materialStockKeepUnitConfigureMap = new ServiceEntityConfigureMap();
		materialStockKeepUnitConfigureMap.setParentNodeName(" ");
		materialStockKeepUnitConfigureMap
				.setNodeName(MaterialStockKeepUnit.NODENAME);
		materialStockKeepUnitConfigureMap
				.setNodeType(MaterialStockKeepUnit.class);
		materialStockKeepUnitConfigureMap
				.setTableName(MaterialStockKeepUnit.SENAME);
		materialStockKeepUnitConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		materialStockKeepUnitConfigureMap.addNodeFieldMap("barcode",
				java.lang.String.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("switchFlag",
				int.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("status",
				int.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("packageStandard",
				java.lang.String.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("productionDate",
				java.util.Date.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("refMaterialUUID",
				java.lang.String.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("refSupplierUUID",
				java.lang.String.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("productionPlace",
				java.lang.String.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap(
				"productionBatchNumber", java.lang.String.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap(
				"inboundDeliveryPrice", double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap(
				"outboundDeliveryPrice", double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("length",
				double.class);
		materialStockKeepUnitConfigureMap
				.addNodeFieldMap("width", double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("height",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("netWeight",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("grossWeight",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("volume",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("mainMaterialUnit",
				java.lang.String.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap(
				"packageMaterialType", int.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("retailPrice",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("memberSalePrice",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("wholeSalePrice",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("purchasePrice",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("minStoreNumber",
				int.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("cargoType",
				int.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("fixLeadTime",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("variableLeadTime",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap(
				"amountForVarLeadTime", double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("supplyType",
				int.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("unitCost",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("retailPriceDisplay",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("purchasePriceDisplay",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("unitCostDisplay",
				double.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("refLengthUnit",
				java.lang.String.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("refVolumeUnit",
				java.lang.String.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("refWeightUnit",
				java.lang.String.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("operationMode",
				int.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("traceMode",
				int.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("qualityInspectFlag",
				int.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("traceLevel",
				int.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("traceStatus",
				int.class);
		materialStockKeepUnitConfigureMap.addNodeFieldMap("refTemplateUUID",
				java.lang.String.class);
		seConfigureMapList.add(materialStockKeepUnitConfigureMap);
		// Init configuration of MaterialStockKeepUnit
		// [MaterialSKUUnitReference] node
		ServiceEntityConfigureMap materialSKUUnitReferenceConfigureMap = new ServiceEntityConfigureMap();
		materialSKUUnitReferenceConfigureMap
				.setParentNodeName(MaterialStockKeepUnit.NODENAME);
		materialSKUUnitReferenceConfigureMap
				.setNodeName(MaterialSKUUnitReference.NODENAME);
		materialSKUUnitReferenceConfigureMap
				.setNodeType(MaterialSKUUnitReference.class);
		materialSKUUnitReferenceConfigureMap
				.setTableName(MaterialSKUUnitReference.NODENAME);
		materialSKUUnitReferenceConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("ratioToStandard",
				double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("barcode",
				java.lang.String.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("unitType",
				int.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("length",
				double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("width",
				double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("height",
				double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("volume",
				double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("grossWeight",
				double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("netWeight",
				double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap(
				"outPackageMaterialType", int.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap(
				"inboundDeliveryPrice", double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap(
				"outboundDeliveryPrice", double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("retailPrice",
				double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("purchasePrice",
				double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("wholeSalePrice",
				double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("memberSalePrice",
				double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("unitName",
				java.lang.String.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("unitCost",
				double.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("refLengthUnit",
				java.lang.String.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("refVolumeUnit",
				java.lang.String.class);
		materialSKUUnitReferenceConfigureMap.addNodeFieldMap("refWeightUnit",
				java.lang.String.class);
		seConfigureMapList.add(materialSKUUnitReferenceConfigureMap);
		// Init configuration of MaterialStockKeepUnit [MaterialSKUAttachment]
		// node
		ServiceEntityConfigureMap materialSKUAttachmentConfigureMap = new ServiceEntityConfigureMap();
		materialSKUAttachmentConfigureMap
				.setParentNodeName(MaterialStockKeepUnit.NODENAME);
		materialSKUAttachmentConfigureMap
				.setNodeName(MaterialSKUAttachment.NODENAME);
		materialSKUAttachmentConfigureMap
				.setNodeType(MaterialSKUAttachment.class);
		materialSKUAttachmentConfigureMap
				.setTableName(MaterialSKUAttachment.NODENAME);
		materialSKUAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		materialSKUAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		materialSKUAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(materialSKUAttachmentConfigureMap);

		// Init configuration of MaterialStockKeepUnit [MaterialSKUAttachment]
		// node
		ServiceEntityConfigureMap materialSKUUnitAttachmentConfigureMap = new ServiceEntityConfigureMap();
		materialSKUUnitAttachmentConfigureMap
				.setParentNodeName(MaterialStockKeepUnit.NODENAME);
		materialSKUUnitAttachmentConfigureMap
				.setNodeName(MaterialSKUUnitAttachment.NODENAME);
		materialSKUUnitAttachmentConfigureMap
				.setNodeType(MaterialSKUUnitAttachment.class);
		materialSKUUnitAttachmentConfigureMap
				.setTableName(MaterialSKUUnitAttachment.NODENAME);
		materialSKUUnitAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		materialSKUUnitAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		materialSKUUnitAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(materialSKUUnitAttachmentConfigureMap);

		// Init configuration of MaterialStockKeepUnit
		// [MaterialSKUExtendProperty] node
		ServiceEntityConfigureMap materialSKUExtendPropertyConfigureMap = new ServiceEntityConfigureMap();
		materialSKUExtendPropertyConfigureMap
				.setParentNodeName(MaterialStockKeepUnit.NODENAME);
		materialSKUExtendPropertyConfigureMap
				.setNodeName(MaterialSKUExtendProperty.NODENAME);
		materialSKUExtendPropertyConfigureMap
				.setNodeType(MaterialSKUExtendProperty.class);
		materialSKUExtendPropertyConfigureMap
				.setTableName(MaterialSKUExtendProperty.NODENAME);
		materialSKUExtendPropertyConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		materialSKUExtendPropertyConfigureMap.addNodeFieldMap(
				"refValueSettingUUID", java.lang.String.class);
		materialSKUExtendPropertyConfigureMap.addNodeFieldMap("doubleValue",
				double.class);
		materialSKUExtendPropertyConfigureMap.addNodeFieldMap("stringValue",
				java.lang.String.class);
		materialSKUExtendPropertyConfigureMap.addNodeFieldMap("intValue",
				int.class);
		materialSKUExtendPropertyConfigureMap.addNodeFieldMap(
				"qualityInspectFlag", int.class);
		materialSKUExtendPropertyConfigureMap.addNodeFieldMap(
				"measureFlag", int.class);
		materialSKUExtendPropertyConfigureMap.addNodeFieldMap(
				"refUnitUUID", String.class);
		seConfigureMapList.add(materialSKUExtendPropertyConfigureMap);

		// Init configuration of Material [MaterialSKUActionLog] node
		ServiceEntityConfigureMap materialSKUActionLogConfigureMap = new ServiceEntityConfigureMap();
		materialSKUActionLogConfigureMap.setParentNodeName(MaterialStockKeepUnit.NODENAME);
		materialSKUActionLogConfigureMap.setNodeName(MaterialSKUActionLog.NODENAME);
		materialSKUActionLogConfigureMap.setNodeType(MaterialSKUActionLog.class);
		materialSKUActionLogConfigureMap
				.setTableName(MaterialSKUActionLog.NODENAME);
		materialSKUActionLogConfigureMap.setFieldList(super
				.getBasicActionCodeNodeMap());
		seConfigureMapList.add(materialSKUActionLogConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
