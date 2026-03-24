package com.company.IntelligentPlatform.sales.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [SalesContract]
 *
 * @author
 * @date Wed Apr 18 20:24:56 CST 2018
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class SalesContractConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.coreFunction");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of SalesContract [ROOT] node
		ServiceEntityConfigureMap salesContractConfigureMap = new ServiceEntityConfigureMap();
		salesContractConfigureMap.setParentNodeName(" ");
		salesContractConfigureMap.setNodeName(SalesContract.NODENAME);
		salesContractConfigureMap.setNodeType(SalesContract.class);
		salesContractConfigureMap.setTableName(SalesContract.SENAME);
		salesContractConfigureMap
				.setFieldList(super.getBasicDocumentFieldMap());

		salesContractConfigureMap.addNodeFieldMap("grossPrice",
				double.class);
		salesContractConfigureMap.addNodeFieldMap("grossPriceDisplay",
				double.class);
		salesContractConfigureMap.addNodeFieldMap("contractDetails",
				java.lang.String.class);
		salesContractConfigureMap.addNodeFieldMap("signDate",
				java.util.Date.class);
		salesContractConfigureMap.addNodeFieldMap("requireExecutionDate",
				java.util.Date.class);
		salesContractConfigureMap.addNodeFieldMap("planExecutionDate",
				java.util.Date.class);
		salesContractConfigureMap.addNodeFieldMap(
				"contractType", int.class);
		salesContractConfigureMap.addNodeFieldMap("tags",
				java.lang.String.class);
		salesContractConfigureMap.addNodeFieldMap("currencyCode",
				java.lang.String.class);
		salesContractConfigureMap.addNodeFieldMap(
				"approveBy", String.class);
		salesContractConfigureMap.addNodeFieldMap("refFinAccountUUID",
				String.class);
		salesContractConfigureMap.addNodeFieldMap("productionBatchNumber",
				java.lang.String.class);
		salesContractConfigureMap.addNodeFieldMap("deliveryDoneBy",
				java.lang.String.class);
		salesContractConfigureMap.addNodeFieldMap("deliveryDoneTime",
				java.util.Date.class);
		salesContractConfigureMap.addNodeFieldMap("processDoneBy",
				java.lang.String.class);
		salesContractConfigureMap.addNodeFieldMap("processDoneTime",
				java.util.Date.class);
		seConfigureMapList.add(salesContractConfigureMap);


		// Init configuration of SalesContract [SalesContractMaterialItem] node
		ServiceEntityConfigureMap salesContractMaterialItemConfigureMap = new ServiceEntityConfigureMap();
		salesContractMaterialItemConfigureMap
				.setParentNodeName(SalesContract.NODENAME);
		salesContractMaterialItemConfigureMap
				.setNodeName(SalesContractMaterialItem.NODENAME);
		salesContractMaterialItemConfigureMap
				.setNodeType(SalesContractMaterialItem.class);
		salesContractMaterialItemConfigureMap
				.setTableName(SalesContractMaterialItem.NODENAME);
		salesContractMaterialItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		salesContractMaterialItemConfigureMap.addNodeFieldMap("shippingPoint",
				java.lang.String.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap(
				"requireShippingTime", java.util.Date.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap(
				"requirementNote", java.lang.String.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap("itemStatus",
				int.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap("refTechStandard",
				java.lang.String.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap("refUnitName",
				java.lang.String.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap(
				"refFinAccountUUID", java.lang.String.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap(
				"refOutboundItemUUID", java.lang.String.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap(
				"refStoreItemUUID", java.lang.String.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap(
				"productionBatchNumber", java.lang.String.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap("deliveryDoneBy",
				java.lang.String.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap("deliveryDoneTime",
				java.util.Date.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap("processDoneBy",
				java.lang.String.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap("processDoneTime",
				java.util.Date.class);
		salesContractMaterialItemConfigureMap.addNodeFieldMap("storeCheckStatus",
				int.class);
		seConfigureMapList.add(salesContractMaterialItemConfigureMap);
		// Init configuration of SalesReturnOrder [salesContractParty] node
		ServiceEntityConfigureMap salesContractPartyConfigureMap = new ServiceEntityConfigureMap();
		salesContractPartyConfigureMap
				.setParentNodeName(SalesContract.NODENAME);
		salesContractPartyConfigureMap
				.setNodeName(SalesContractParty.NODENAME);
		salesContractPartyConfigureMap
				.setNodeType(SalesContractParty.class);
		salesContractPartyConfigureMap
				.setTableName(SalesContractParty.NODENAME);
		salesContractPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(salesContractPartyConfigureMap);
		// Init configuration of SalesContract [SalesContractAttachment] node
		ServiceEntityConfigureMap salesContractAttachmentConfigureMap = new ServiceEntityConfigureMap();
		salesContractAttachmentConfigureMap
				.setParentNodeName(SalesContract.NODENAME);
		salesContractAttachmentConfigureMap
				.setNodeName(SalesContractAttachment.NODENAME);
		salesContractAttachmentConfigureMap
				.setNodeType(SalesContractAttachment.class);
		salesContractAttachmentConfigureMap
				.setTableName(SalesContractAttachment.NODENAME);
		salesContractAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		salesContractAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		salesContractAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(salesContractAttachmentConfigureMap);

		//Init configuration of SalesContract [SalesContractActionNode] node
		ServiceEntityConfigureMap salesContractActionNodeConfigureMap = new ServiceEntityConfigureMap();
		salesContractActionNodeConfigureMap.setParentNodeName(SalesContract.NODENAME);
		salesContractActionNodeConfigureMap.setNodeName(SalesContractActionNode.NODENAME);
		salesContractActionNodeConfigureMap.setNodeType(SalesContractActionNode.class);
		salesContractActionNodeConfigureMap.setTableName(SalesContractActionNode.NODENAME);
		salesContractActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
		seConfigureMapList.add(salesContractActionNodeConfigureMap);
		// Init configuration of SalesContract
		// [SalesContractMaterialItemAttachment] node
		ServiceEntityConfigureMap salesContractMaterialItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
		salesContractMaterialItemAttachmentConfigureMap
				.setParentNodeName(SalesContractMaterialItem.NODENAME);
		salesContractMaterialItemAttachmentConfigureMap
				.setNodeName(SalesContractMaterialItemAttachment.NODENAME);
		salesContractMaterialItemAttachmentConfigureMap
				.setNodeType(SalesContractMaterialItemAttachment.class);
		salesContractMaterialItemAttachmentConfigureMap
				.setTableName(SalesContractMaterialItemAttachment.NODENAME);
		salesContractMaterialItemAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		salesContractMaterialItemAttachmentConfigureMap.addNodeFieldMap(
				"content", byte[].class);
		salesContractMaterialItemAttachmentConfigureMap.addNodeFieldMap(
				"fileType", java.lang.String.class);
		seConfigureMapList.add(salesContractMaterialItemAttachmentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
