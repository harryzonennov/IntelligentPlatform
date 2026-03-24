package com.company.IntelligentPlatform.logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [PurchaseContract]
 *
 * @author
 * @date Mon May 21 16:30:45 CST 2018
 *       <p>
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class PurchaseContractConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.coreFunction");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of PurchaseContract [ROOT] node
		ServiceEntityConfigureMap purchaseContractConfigureMap = new ServiceEntityConfigureMap();
		purchaseContractConfigureMap.setParentNodeName(" ");
		purchaseContractConfigureMap.setNodeName(PurchaseContract.NODENAME);
		purchaseContractConfigureMap.setNodeType(PurchaseContract.class);
		purchaseContractConfigureMap.setTableName(PurchaseContract.SENAME);
		purchaseContractConfigureMap.setFieldList(super
				.getBasicDocumentFieldMap());
		purchaseContractConfigureMap
				.addNodeFieldMap("grossPrice", double.class);
		purchaseContractConfigureMap
				.addNodeFieldMap("grossPriceDisplay", double.class);
		purchaseContractConfigureMap.addNodeFieldMap("contractDetails",
				java.lang.String.class);
		purchaseContractConfigureMap.addNodeFieldMap("signDate",
				java.util.Date.class);
		purchaseContractConfigureMap.addNodeFieldMap("requireExecutionDate",
				java.util.Date.class);
		purchaseContractConfigureMap.addNodeFieldMap("currencyCode",
				java.lang.String.class);
		purchaseContractConfigureMap.addNodeFieldMap("refFinAccountUUID",
				java.lang.String.class);
		purchaseContractConfigureMap.addNodeFieldMap("purchaseBatchNumber",
				java.lang.String.class);
		purchaseContractConfigureMap.addNodeFieldMap("productionBatchNumber",
				java.lang.String.class);
		seConfigureMapList.add(purchaseContractConfigureMap);
		// Init configuration of PurchaseContract [PurchaseContractMaterialItem]
		// node
		ServiceEntityConfigureMap purchaseContractMaterialItemConfigureMap = new ServiceEntityConfigureMap();
		purchaseContractMaterialItemConfigureMap
				.setParentNodeName(PurchaseContract.NODENAME);
		purchaseContractMaterialItemConfigureMap
				.setNodeName(PurchaseContractMaterialItem.NODENAME);
		purchaseContractMaterialItemConfigureMap
				.setNodeType(PurchaseContractMaterialItem.class);
		purchaseContractMaterialItemConfigureMap
				.setTableName(PurchaseContractMaterialItem.NODENAME);
		purchaseContractMaterialItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		purchaseContractMaterialItemConfigureMap.addNodeFieldMap(
				"shippingPoint", java.lang.String.class);
		purchaseContractMaterialItemConfigureMap.addNodeFieldMap(
				"requireShippingTime", java.util.Date.class);
		purchaseContractMaterialItemConfigureMap.addNodeFieldMap("itemStatus",
				int.class);
		purchaseContractMaterialItemConfigureMap.addNodeFieldMap("refUnitName",
				java.lang.String.class);
		purchaseContractMaterialItemConfigureMap.addNodeFieldMap(
				"currencyCode", String.class);
		purchaseContractMaterialItemConfigureMap.addNodeFieldMap(
				"refFinAccountUUID", java.lang.String.class);
		seConfigureMapList.add(purchaseContractMaterialItemConfigureMap);

		//Init configuration of PurchaseContract [PurchaseContractActionNode] node
		ServiceEntityConfigureMap purchaseContractActionNodeConfigureMap = new ServiceEntityConfigureMap();
		purchaseContractActionNodeConfigureMap.setParentNodeName(PurchaseContract.NODENAME);
		purchaseContractActionNodeConfigureMap.setNodeName(PurchaseContractActionNode.NODENAME);
		purchaseContractActionNodeConfigureMap.setNodeType(PurchaseContractActionNode.class);
		purchaseContractActionNodeConfigureMap.setTableName(PurchaseContractActionNode.NODENAME);
		purchaseContractActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
		seConfigureMapList.add(purchaseContractActionNodeConfigureMap);
		// Init configuration of PurchaseContract [PurchaseContractAttachment]

		// Init configuration of PurchaseContract [purchaseContractParty] node
		ServiceEntityConfigureMap purchaseContractPartyConfigureMap = new ServiceEntityConfigureMap();
		purchaseContractPartyConfigureMap
				.setParentNodeName(PurchaseContract.NODENAME);
		purchaseContractPartyConfigureMap
				.setNodeName(PurchaseContractParty.NODENAME);
		purchaseContractPartyConfigureMap
				.setNodeType(PurchaseContractParty.class);
		purchaseContractPartyConfigureMap
				.setTableName(PurchaseContractParty.NODENAME);
		purchaseContractPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(purchaseContractPartyConfigureMap);
		// Init configuration of PurchaseContract [PurchaseContractAttachment]
		// node
		ServiceEntityConfigureMap purchaseContractAttachmentConfigureMap = new ServiceEntityConfigureMap();
		purchaseContractAttachmentConfigureMap
				.setParentNodeName(PurchaseContract.NODENAME);
		purchaseContractAttachmentConfigureMap
				.setNodeName(PurchaseContractAttachment.NODENAME);
		purchaseContractAttachmentConfigureMap
				.setNodeType(PurchaseContractAttachment.class);
		purchaseContractAttachmentConfigureMap
				.setTableName(PurchaseContractAttachment.NODENAME);
		purchaseContractAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		purchaseContractAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		purchaseContractAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(purchaseContractAttachmentConfigureMap);
		// Init configuration of PurchaseContract
		// [PurchaseContractMaterialItemAttachment] node
		ServiceEntityConfigureMap purchaseContractMaterialItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
		purchaseContractMaterialItemAttachmentConfigureMap
				.setParentNodeName(PurchaseContractMaterialItem.NODENAME);
		purchaseContractMaterialItemAttachmentConfigureMap
				.setNodeName(PurchaseContractMaterialItemAttachment.NODENAME);
		purchaseContractMaterialItemAttachmentConfigureMap
				.setNodeType(PurchaseContractMaterialItemAttachment.class);
		purchaseContractMaterialItemAttachmentConfigureMap
				.setTableName(PurchaseContractMaterialItemAttachment.NODENAME);
		purchaseContractMaterialItemAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		purchaseContractMaterialItemAttachmentConfigureMap.addNodeFieldMap(
				"content", byte[].class);
		purchaseContractMaterialItemAttachmentConfigureMap.addNodeFieldMap(
				"fileType", java.lang.String.class);
		seConfigureMapList
				.add(purchaseContractMaterialItemAttachmentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
