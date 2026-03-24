package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.model.RegisteredProductExtendProperty;
import com.company.IntelligentPlatform.common.model.RegisteredProductInvolveParty;

/**
 * Configure Proxy CLASS FOR Service Entity [RegisteredProduct]
 *
 * @author
 * @date Wed Jul 17 18:08:01 CST 2019
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class RegisteredProductConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.coreFunction");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of RegisteredProduct [ROOT] node
		ServiceEntityConfigureMap registeredProductConfigureMap = new ServiceEntityConfigureMap();
		registeredProductConfigureMap.setParentNodeName(" ");
		registeredProductConfigureMap.setNodeName(RegisteredProduct.NODENAME);
		registeredProductConfigureMap.setNodeType(RegisteredProduct.class);
		registeredProductConfigureMap.setTableName(RegisteredProduct.SENAME);
		registeredProductConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		registeredProductConfigureMap.addNodeFieldMap("barcode",
				java.lang.String.class);
		registeredProductConfigureMap.addNodeFieldMap("switchFlag", int.class);
		registeredProductConfigureMap.addNodeFieldMap("packageStandard",
				java.lang.String.class);
		registeredProductConfigureMap.addNodeFieldMap("productionDate",
				java.util.Date.class);
		registeredProductConfigureMap.addNodeFieldMap("refMaterialUUID",
				java.lang.String.class);
		registeredProductConfigureMap.addNodeFieldMap("refSupplierUUID",
				java.lang.String.class);
		registeredProductConfigureMap.addNodeFieldMap("productionPlace",
				java.lang.String.class);
		registeredProductConfigureMap.addNodeFieldMap("productionBatchNumber",
				java.lang.String.class);
		registeredProductConfigureMap.addNodeFieldMap("inboundDeliveryPrice",
				double.class);
		registeredProductConfigureMap.addNodeFieldMap("outboundDeliveryPrice",
				double.class);
		registeredProductConfigureMap.addNodeFieldMap("length", double.class);
		registeredProductConfigureMap.addNodeFieldMap("width", double.class);
		registeredProductConfigureMap.addNodeFieldMap("height", double.class);
		registeredProductConfigureMap
				.addNodeFieldMap("netWeight", double.class);
		registeredProductConfigureMap.addNodeFieldMap("grossWeight",
				double.class);
		registeredProductConfigureMap.addNodeFieldMap("volume", double.class);
		registeredProductConfigureMap.addNodeFieldMap("mainMaterialUnit",
				java.lang.String.class);
		registeredProductConfigureMap.addNodeFieldMap("packageMaterialType",
				int.class);
		registeredProductConfigureMap.addNodeFieldMap("retailPrice",
				double.class);
		registeredProductConfigureMap.addNodeFieldMap("memberSalePrice",
				double.class);
		registeredProductConfigureMap.addNodeFieldMap("wholeSalePrice",
				double.class);
		registeredProductConfigureMap.addNodeFieldMap("purchasePrice",
				double.class);
		registeredProductConfigureMap.addNodeFieldMap("minStoreNumber",
				int.class);
		registeredProductConfigureMap.addNodeFieldMap("cargoType", int.class);
		registeredProductConfigureMap.addNodeFieldMap("fixLeadTime",
				double.class);
		registeredProductConfigureMap.addNodeFieldMap("variableLeadTime",
				double.class);
		registeredProductConfigureMap.addNodeFieldMap("amountForVarLeadTime",
				double.class);
		registeredProductConfigureMap.addNodeFieldMap("supplyType", int.class);
		registeredProductConfigureMap.addNodeFieldMap("unitCost", double.class);
		registeredProductConfigureMap.addNodeFieldMap("refLengthUnit",
				java.lang.String.class);
		registeredProductConfigureMap.addNodeFieldMap("refVolumeUnit",
				java.lang.String.class);
		registeredProductConfigureMap.addNodeFieldMap("refWeightUnit",
				java.lang.String.class);
		registeredProductConfigureMap.addNodeFieldMap("operationMode",
				int.class);
		registeredProductConfigureMap.addNodeFieldMap("traceMode", int.class);
		registeredProductConfigureMap.addNodeFieldMap("traceLevel", int.class);
		registeredProductConfigureMap.addNodeFieldMap("traceStatus", int.class);
		registeredProductConfigureMap.addNodeFieldMap("qualityInspectFlag",
				int.class);
		registeredProductConfigureMap.addNodeFieldMap("refTemplateUUID",
				java.lang.String.class);
		registeredProductConfigureMap.addNodeFieldMap("refMaterialSKUUUID",
				java.lang.String.class);
		registeredProductConfigureMap.addNodeFieldMap("serialId",
				java.lang.String.class);
		registeredProductConfigureMap.addNodeFieldMap("referenceDate",
				java.util.Date.class);
		registeredProductConfigureMap.addNodeFieldMap("validFromDate",
				java.util.Date.class);
		registeredProductConfigureMap.addNodeFieldMap("validToDate",
				java.util.Date.class);
		seConfigureMapList.add(registeredProductConfigureMap);
		// Init configuration of RegisteredProduct
		// [RegisteredProductExtendProperty] node
		ServiceEntityConfigureMap registeredProductExtendPropertyConfigureMap = new ServiceEntityConfigureMap();
		registeredProductExtendPropertyConfigureMap
				.setParentNodeName(RegisteredProduct.NODENAME);
		registeredProductExtendPropertyConfigureMap
				.setNodeName(RegisteredProductExtendProperty.NODENAME);
		registeredProductExtendPropertyConfigureMap
				.setNodeType(RegisteredProductExtendProperty.class);
		registeredProductExtendPropertyConfigureMap
				.setTableName(RegisteredProductExtendProperty.NODENAME);
		registeredProductExtendPropertyConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		registeredProductExtendPropertyConfigureMap.addNodeFieldMap(
				"refValueSettingUUID", java.lang.String.class);
		registeredProductExtendPropertyConfigureMap.addNodeFieldMap(
				"doubleValue", double.class);
		registeredProductExtendPropertyConfigureMap.addNodeFieldMap(
				"stringValue", java.lang.String.class);
		registeredProductExtendPropertyConfigureMap.addNodeFieldMap("intValue",
				int.class);
		registeredProductExtendPropertyConfigureMap.addNodeFieldMap(
				"qualityInspectFlag", int.class);
		registeredProductExtendPropertyConfigureMap.addNodeFieldMap(
				"measureFlag", int.class);
		registeredProductExtendPropertyConfigureMap.addNodeFieldMap(
				"refUnitUUID", String.class);
		seConfigureMapList.add(registeredProductExtendPropertyConfigureMap);

		// [RegisteredProductExtendProperty] node
		ServiceEntityConfigureMap registeredProductActionLogPropertyConfigureMap = new ServiceEntityConfigureMap();
		registeredProductActionLogPropertyConfigureMap
				.setParentNodeName(RegisteredProduct.NODENAME);
		registeredProductActionLogPropertyConfigureMap
				.setNodeName(RegisteredProductActionLog.NODENAME);
		registeredProductActionLogPropertyConfigureMap
				.setNodeType(RegisteredProductActionLog.class);
		registeredProductActionLogPropertyConfigureMap
				.setTableName(RegisteredProductActionLog.NODENAME);
		registeredProductActionLogPropertyConfigureMap.setFieldList(super
				.getBasicActionCodeNodeMap());
		seConfigureMapList.add(registeredProductActionLogPropertyConfigureMap);
		// Init configuration of RegisteredProduct
		// [RegisteredProductInvolveParty] node
		ServiceEntityConfigureMap registeredProductInvolvePartyConfigureMap = new ServiceEntityConfigureMap();
		registeredProductInvolvePartyConfigureMap
				.setParentNodeName(RegisteredProduct.NODENAME);
		registeredProductInvolvePartyConfigureMap
				.setNodeName(RegisteredProductInvolveParty.NODENAME);
		registeredProductInvolvePartyConfigureMap
				.setNodeType(RegisteredProductInvolveParty.class);
		registeredProductInvolvePartyConfigureMap
				.setTableName(RegisteredProductInvolveParty.NODENAME);
		registeredProductInvolvePartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap("refPartyId",
				java.lang.String.class);
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap(
				"refPartyName", java.lang.String.class);
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap(
				"refPartyTelephone", java.lang.String.class);
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap(
				"refPartyAddress", java.lang.String.class);
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap(
				"refPartyTaxNumber", java.lang.String.class);
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap(
				"refPartyBankAccount", java.lang.String.class);
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap(
				"refPartyEmail", java.lang.String.class);
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap(
				"refPartyFax", java.lang.String.class);
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap(
				"refPartyContactName", java.lang.String.class);
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap(
				"refPartyContactId", java.lang.String.class);
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap(
				"refPartyContactMobile", java.lang.String.class);
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap(
				"refPartyContactWeixin", java.lang.String.class);
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap(
				"refPartyContactEmail", java.lang.String.class);
		registeredProductInvolvePartyConfigureMap.addNodeFieldMap(
				"refPartyContactUUID", java.lang.String.class);
		seConfigureMapList.add(registeredProductInvolvePartyConfigureMap);

		// Init configuration of Registered [RegisteredAttachment] node
		ServiceEntityConfigureMap registeredAttachmentConfigureMap = new ServiceEntityConfigureMap();
		registeredAttachmentConfigureMap
				.setParentNodeName(RegisteredProduct.NODENAME);
		registeredAttachmentConfigureMap
				.setNodeName(RegisteredProductAttachment.NODENAME);
		registeredAttachmentConfigureMap
				.setNodeType(RegisteredProductAttachment.class);
		registeredAttachmentConfigureMap
				.setTableName(RegisteredProductAttachment.NODENAME);
		registeredAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		registeredAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		registeredAttachmentConfigureMap.addNodeFieldMap("fileType",
				String.class);
		seConfigureMapList.add(registeredAttachmentConfigureMap);

		// Init configuration of Registered [RegisteredAttachment] node
		ServiceEntityConfigureMap registeredExtendPropertyAttachmentConfigureMap = new ServiceEntityConfigureMap();
		registeredExtendPropertyAttachmentConfigureMap
				.setParentNodeName(RegisteredProductExtendProperty.NODENAME);
		registeredExtendPropertyAttachmentConfigureMap
				.setNodeName(RegisteredProductExtendPropertyAttachment.NODENAME);
		registeredExtendPropertyAttachmentConfigureMap
				.setNodeType(RegisteredProductExtendPropertyAttachment.class);
		registeredExtendPropertyAttachmentConfigureMap
				.setTableName(RegisteredProductExtendPropertyAttachment.NODENAME);
		registeredExtendPropertyAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		registeredExtendPropertyAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		registeredExtendPropertyAttachmentConfigureMap.addNodeFieldMap("fileType",
				String.class);
		seConfigureMapList.add(registeredExtendPropertyAttachmentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
