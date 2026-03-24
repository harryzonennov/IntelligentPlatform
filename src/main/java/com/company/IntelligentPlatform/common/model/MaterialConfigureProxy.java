package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialUnitReference;
import com.company.IntelligentPlatform.common.model.MaterialAttachment;

/**
 * Configure Proxy CLASS FOR Service Entity [Material]
 *
 * @author
 * @date Sat Apr 07 22:08:32 CST 2018
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class MaterialConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.coreFunction");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of Material [ROOT] node
		ServiceEntityConfigureMap materialConfigureMap = new ServiceEntityConfigureMap();
		materialConfigureMap.setParentNodeName(" ");
		materialConfigureMap.setNodeName(Material.NODENAME);
		materialConfigureMap.setNodeType(Material.class);
		materialConfigureMap.setTableName(Material.SENAME);
		materialConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		materialConfigureMap.addNodeFieldMap("refMainSupplierUUID",
				java.lang.String.class);
		materialConfigureMap.addNodeFieldMap("mainProductionPlace",
				java.lang.String.class);
		materialConfigureMap.addNodeFieldMap("length", double.class);
		materialConfigureMap.addNodeFieldMap("width", double.class);
		materialConfigureMap.addNodeFieldMap("height", double.class);
		materialConfigureMap.addNodeFieldMap("netWeight", double.class);
		materialConfigureMap.addNodeFieldMap("grossWeight", double.class);
		materialConfigureMap.addNodeFieldMap("volume", double.class);
		materialConfigureMap.addNodeFieldMap("inboundDeliveryPrice",
				double.class);
		materialConfigureMap.addNodeFieldMap("outboundDeliveryPrice",
				double.class);
		materialConfigureMap.addNodeFieldMap("retailPrice", double.class);
		materialConfigureMap.addNodeFieldMap("purchasePrice", double.class);
		materialConfigureMap.addNodeFieldMap("wholeSalePrice", double.class);
		materialConfigureMap.addNodeFieldMap("memberSalePrice", double.class);
		materialConfigureMap.addNodeFieldMap("materialCategory", int.class);
		materialConfigureMap.addNodeFieldMap("refMaterialType",
				java.lang.String.class);
		materialConfigureMap.addNodeFieldMap("switchFlag", int.class);
		materialConfigureMap.addNodeFieldMap("mainMaterialUnit",
				java.lang.String.class);
		materialConfigureMap.addNodeFieldMap("barcode", java.lang.String.class);
		materialConfigureMap.addNodeFieldMap("packageStandard",
				java.lang.String.class);
		materialConfigureMap.addNodeFieldMap("status",
				int.class);
		materialConfigureMap.addNodeFieldMap("packageMaterialType", int.class);
		materialConfigureMap.addNodeFieldMap("minStoreNumber", int.class);
		materialConfigureMap.addNodeFieldMap("cargoType", int.class);
		materialConfigureMap.addNodeFieldMap("fixLeadTime", double.class);
		materialConfigureMap.addNodeFieldMap("variableLeadTime", double.class);
		materialConfigureMap.addNodeFieldMap("amountForVarLeadTime",
				double.class);
		materialConfigureMap.addNodeFieldMap("supplyType", int.class);
		materialConfigureMap.addNodeFieldMap("unitCost", double.class);
		materialConfigureMap.addNodeFieldMap("retailPriceDisplay",
				double.class);
		materialConfigureMap.addNodeFieldMap("purchasePriceDisplay",
				double.class);
		materialConfigureMap.addNodeFieldMap("unitCostDisplay",
				double.class);
		materialConfigureMap.addNodeFieldMap("refLengthUnit",
				java.lang.String.class);
		materialConfigureMap.addNodeFieldMap("refVolumeUnit",
				java.lang.String.class);
		materialConfigureMap.addNodeFieldMap("refWeightUnit",
				java.lang.String.class);
		materialConfigureMap.addNodeFieldMap("operationMode",
				int.class);
		materialConfigureMap.addNodeFieldMap("qualityInspectFlag",
				int.class);
		seConfigureMapList.add(materialConfigureMap);
		// Init configuration of Material [MaterialUnitReference] node
		ServiceEntityConfigureMap materialUnitReferenceConfigureMap = new ServiceEntityConfigureMap();
		materialUnitReferenceConfigureMap.setParentNodeName(Material.NODENAME);
		materialUnitReferenceConfigureMap
				.setNodeName(MaterialUnitReference.NODENAME);
		materialUnitReferenceConfigureMap
				.setNodeType(MaterialUnitReference.class);
		materialUnitReferenceConfigureMap
				.setTableName(MaterialUnitReference.NODENAME);
		materialUnitReferenceConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		materialUnitReferenceConfigureMap.addNodeFieldMap("ratioToStandard",
				double.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("barcode",
				java.lang.String.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("length",
				double.class);
		materialUnitReferenceConfigureMap
				.addNodeFieldMap("width", double.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("height",
				double.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("volume",
				double.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("grossWeight",
				double.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("netWeight",
				double.class);
		materialUnitReferenceConfigureMap
				.addNodeFieldMap("unitType", int.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap(
				"outPackageMaterialType", int.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap(
				"inboundDeliveryPrice", double.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap(
				"outboundDeliveryPrice", double.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("retailPrice",
				double.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("purchasePrice",
				double.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("wholeSalePrice",
				double.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("memberSalePrice",
				double.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("unitCost",
				double.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("refLengthUnit",
				java.lang.String.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("refVolumeUnit",
				java.lang.String.class);
		materialUnitReferenceConfigureMap.addNodeFieldMap("refWeightUnit",
				java.lang.String.class);

		seConfigureMapList.add(materialUnitReferenceConfigureMap);
		// Init configuration of Material [MaterialAttachment] node
		ServiceEntityConfigureMap materialAttachmentConfigureMap = new ServiceEntityConfigureMap();
		materialAttachmentConfigureMap.setParentNodeName(Material.NODENAME);
		materialAttachmentConfigureMap.setNodeName(MaterialAttachment.NODENAME);
		materialAttachmentConfigureMap.setNodeType(MaterialAttachment.class);
		materialAttachmentConfigureMap
				.setTableName(MaterialAttachment.NODENAME);
		materialAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		materialAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
		materialAttachmentConfigureMap
				.addNodeFieldMap("fileType", String.class);
		seConfigureMapList.add(materialAttachmentConfigureMap);

		// Init configuration of Material [MaterialAttachment] node
		ServiceEntityConfigureMap materialUnitAttachmentConfigureMap = new ServiceEntityConfigureMap();
		materialUnitAttachmentConfigureMap.setParentNodeName(MaterialUnitReference.NODENAME);
		materialUnitAttachmentConfigureMap.setNodeName(MaterialUnitAttachment.NODENAME);
		materialUnitAttachmentConfigureMap.setNodeType(MaterialUnitAttachment.class);
		materialUnitAttachmentConfigureMap
				.setTableName(MaterialUnitAttachment.NODENAME);
		materialUnitAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		materialUnitAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
		materialUnitAttachmentConfigureMap
				.addNodeFieldMap("fileType", String.class);
		seConfigureMapList.add(materialUnitAttachmentConfigureMap);

		// Init configuration of Material [MaterialActionLog] node
		ServiceEntityConfigureMap materialActionLogConfigureMap = new ServiceEntityConfigureMap();
		materialActionLogConfigureMap.setParentNodeName(Material.NODENAME);
		materialActionLogConfigureMap.setNodeName(MaterialActionLog.NODENAME);
		materialActionLogConfigureMap.setNodeType(MaterialActionLog.class);
		materialActionLogConfigureMap
				.setTableName(MaterialActionLog.NODENAME);
		materialActionLogConfigureMap.setFieldList(super
				.getBasicActionCodeNodeMap());
		seConfigureMapList.add(materialActionLogConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
