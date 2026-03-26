package com.company.IntelligentPlatform.sales.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [SalesReturnOrder]
 *
 * @author
 * @date Tue Sep 08 14:52:43 CST 2020
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class SalesReturnOrderConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.salesDistribution");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of SalesReturnOrder [ROOT] node
		ServiceEntityConfigureMap salesReturnOrderConfigureMap = new ServiceEntityConfigureMap();
		salesReturnOrderConfigureMap.setParentNodeName(" ");
		salesReturnOrderConfigureMap.setNodeName(SalesReturnOrder.NODENAME);
		salesReturnOrderConfigureMap.setNodeType(SalesReturnOrder.class);
		salesReturnOrderConfigureMap.setTableName(SalesReturnOrder.SENAME);
		salesReturnOrderConfigureMap.setFieldList(super
				.getBasicDocumentFieldMap());
		salesReturnOrderConfigureMap.addNodeFieldMap("grossPrice",
				double.class);
		salesReturnOrderConfigureMap.addNodeFieldMap("grossPriceDisplay",
				double.class);
		salesReturnOrderConfigureMap.addNodeFieldMap("refInWarehouseUUID",
				java.lang.String.class);
		salesReturnOrderConfigureMap.addNodeFieldMap("refInboundDeliveryUUID",
				java.lang.String.class);
		salesReturnOrderConfigureMap.addNodeFieldMap("barcode",
				java.lang.String.class);
		salesReturnOrderConfigureMap.addNodeFieldMap("taxRate", double.class);
		salesReturnOrderConfigureMap.addNodeFieldMap("productionBatchNumber",
				java.lang.String.class);
		seConfigureMapList.add(salesReturnOrderConfigureMap);
		// Init configuration of SalesReturnOrder [SalesReturnMaterialItem] node
		ServiceEntityConfigureMap salesReturnMaterialItemConfigureMap = new ServiceEntityConfigureMap();
		salesReturnMaterialItemConfigureMap
				.setParentNodeName(SalesReturnOrder.NODENAME);
		salesReturnMaterialItemConfigureMap
				.setNodeName(SalesReturnMaterialItem.NODENAME);
		salesReturnMaterialItemConfigureMap
				.setNodeType(SalesReturnMaterialItem.class);
		salesReturnMaterialItemConfigureMap
				.setTableName(SalesReturnMaterialItem.NODENAME);
		salesReturnMaterialItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		salesReturnMaterialItemConfigureMap.addNodeFieldMap("itemStatus",
				int.class);
		salesReturnMaterialItemConfigureMap.addNodeFieldMap("refDocItemUUID",
				java.lang.String.class);
		salesReturnMaterialItemConfigureMap.addNodeFieldMap("refDocItemType",
				int.class);
		seConfigureMapList.add(salesReturnMaterialItemConfigureMap);
		//Init configuration of SalesReturnOrder [SalesReturnOrderActionNode] node
		ServiceEntityConfigureMap salesReturnOrderActionNodeConfigureMap = new ServiceEntityConfigureMap();
		salesReturnOrderActionNodeConfigureMap.setParentNodeName(SalesReturnOrder.NODENAME);
		salesReturnOrderActionNodeConfigureMap.setNodeName(SalesReturnOrderActionNode.NODENAME);
		salesReturnOrderActionNodeConfigureMap.setNodeType(SalesReturnOrderActionNode.class);
		salesReturnOrderActionNodeConfigureMap.setTableName(SalesReturnOrderActionNode.NODENAME);
		salesReturnOrderActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
		seConfigureMapList.add(salesReturnOrderActionNodeConfigureMap);
		// Init configuration of SalesReturnOrder [SalesReturnOrderAttachment]
		// node
		ServiceEntityConfigureMap salesReturnOrderAttachmentConfigureMap = new ServiceEntityConfigureMap();
		salesReturnOrderAttachmentConfigureMap
				.setParentNodeName(SalesReturnOrder.NODENAME);
		salesReturnOrderAttachmentConfigureMap
				.setNodeName(SalesReturnOrderAttachment.NODENAME);
		salesReturnOrderAttachmentConfigureMap
				.setNodeType(SalesReturnOrderAttachment.class);
		salesReturnOrderAttachmentConfigureMap
				.setTableName(SalesReturnOrderAttachment.NODENAME);
		salesReturnOrderAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		salesReturnOrderAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		salesReturnOrderAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(salesReturnOrderAttachmentConfigureMap);
		// Init configuration of SalesReturnOrder [SalesReturnOrderParty] node
		ServiceEntityConfigureMap salesReturnOrderPartyConfigureMap = new ServiceEntityConfigureMap();
		salesReturnOrderPartyConfigureMap
				.setParentNodeName(SalesReturnOrder.NODENAME);
		salesReturnOrderPartyConfigureMap
				.setNodeName(SalesReturnOrderParty.NODENAME);
		salesReturnOrderPartyConfigureMap
				.setNodeType(SalesReturnOrderParty.class);
		salesReturnOrderPartyConfigureMap
				.setTableName(SalesReturnOrderParty.NODENAME);
		salesReturnOrderPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(salesReturnOrderPartyConfigureMap);

		// Init configuration of SalesReturnOrder [SalesReturnMatItemAttachment]
		// node
		ServiceEntityConfigureMap salesReturnMatItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
		salesReturnMatItemAttachmentConfigureMap
				.setParentNodeName(SalesReturnMaterialItem.NODENAME);
		salesReturnMatItemAttachmentConfigureMap
				.setNodeName(SalesReturnMatItemAttachment.NODENAME);
		salesReturnMatItemAttachmentConfigureMap
				.setNodeType(SalesReturnMatItemAttachment.class);
		salesReturnMatItemAttachmentConfigureMap
				.setTableName(SalesReturnMatItemAttachment.NODENAME);
		salesReturnMatItemAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		salesReturnMatItemAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		salesReturnMatItemAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(salesReturnMatItemAttachmentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
