package com.company.IntelligentPlatform.logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [Inquiry]
 *
 * @author
 * @date Wed Jun 06 20:40:41 CST 2018
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class InquiryConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.coreFunction");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of Inquiry [ROOT] node
		ServiceEntityConfigureMap inquiryConfigureMap = new ServiceEntityConfigureMap();
		inquiryConfigureMap.setParentNodeName(" ");
		inquiryConfigureMap.setNodeName(Inquiry.NODENAME);
		inquiryConfigureMap.setNodeType(Inquiry.class);
		inquiryConfigureMap.setTableName(Inquiry.SENAME);
		inquiryConfigureMap.setFieldList(super.getBasicDocumentFieldMap());
		inquiryConfigureMap.addNodeFieldMap("grossPrice", double.class);
		inquiryConfigureMap.addNodeFieldMap("grossPriceDisplay", double.class);
		inquiryConfigureMap.addNodeFieldMap("contractDetails",
				java.lang.String.class);
		inquiryConfigureMap.addNodeFieldMap("signDate", java.util.Date.class);
		inquiryConfigureMap.addNodeFieldMap("requireExecutionDate",
				java.util.Date.class);
		inquiryConfigureMap.addNodeFieldMap("currencyCode",
						java.lang.String.class);
		seConfigureMapList.add(inquiryConfigureMap);

		//Init configuration of OutboundDelivery [OutboundDeliveryActionNode] node
		ServiceEntityConfigureMap inquiryActionNodeConfigureMap = new ServiceEntityConfigureMap();
		inquiryActionNodeConfigureMap.setParentNodeName(Inquiry.NODENAME);
		inquiryActionNodeConfigureMap.setNodeName(InquiryActionNode.NODENAME);
		inquiryActionNodeConfigureMap.setNodeType(InquiryActionNode.class);
		inquiryActionNodeConfigureMap.setTableName(InquiryActionNode.NODENAME);
		inquiryActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
		seConfigureMapList.add(inquiryActionNodeConfigureMap);

		// Init configuration of Inquiry [InquiryMaterialItem] node
		ServiceEntityConfigureMap inquiryMaterialItemConfigureMap = new ServiceEntityConfigureMap();
		inquiryMaterialItemConfigureMap.setParentNodeName(Inquiry.NODENAME);
		inquiryMaterialItemConfigureMap
				.setNodeName(InquiryMaterialItem.NODENAME);
		inquiryMaterialItemConfigureMap.setNodeType(InquiryMaterialItem.class);
		inquiryMaterialItemConfigureMap
				.setTableName(InquiryMaterialItem.NODENAME);
		inquiryMaterialItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		inquiryMaterialItemConfigureMap.addNodeFieldMap("shippingPoint",
				java.lang.String.class);
		inquiryMaterialItemConfigureMap.addNodeFieldMap("requireShippingTime",
				java.util.Date.class);
		inquiryMaterialItemConfigureMap
				.addNodeFieldMap("itemStatus", int.class);
		inquiryMaterialItemConfigureMap.addNodeFieldMap("refUnitUUID",
				java.lang.String.class);
		inquiryMaterialItemConfigureMap.addNodeFieldMap("refUnitName",
				java.lang.String.class);
		inquiryMaterialItemConfigureMap.addNodeFieldMap("firstItemPrice",
				double.class);
		inquiryMaterialItemConfigureMap.addNodeFieldMap("firstUnitPrice",
				double.class);
		seConfigureMapList.add(inquiryMaterialItemConfigureMap);

		// Init configuration of Inquiry [InquiryParty] node
		ServiceEntityConfigureMap inquiryPartyConfigureMap = new ServiceEntityConfigureMap();
		inquiryPartyConfigureMap
				.setParentNodeName(Inquiry.NODENAME);
		inquiryPartyConfigureMap
				.setNodeName(InquiryParty.NODENAME);
		inquiryPartyConfigureMap
				.setNodeType(InquiryParty.class);
		inquiryPartyConfigureMap
				.setTableName(InquiryParty.NODENAME);
		inquiryPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(inquiryPartyConfigureMap);
		// Init configuration of Inquiry [InquiryAttachment]
		// node
		ServiceEntityConfigureMap inquiryAttachmentConfigureMap = new ServiceEntityConfigureMap();
		inquiryAttachmentConfigureMap
				.setParentNodeName(Inquiry.NODENAME);
		inquiryAttachmentConfigureMap
				.setNodeName(InquiryAttachment.NODENAME);
		inquiryAttachmentConfigureMap
				.setNodeType(InquiryAttachment.class);
		inquiryAttachmentConfigureMap
				.setTableName(InquiryAttachment.NODENAME);
		inquiryAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		inquiryAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		inquiryAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(inquiryAttachmentConfigureMap);
		// Init configuration of Inquiry [InquiryMaterialItemAttachment] node
		ServiceEntityConfigureMap inquiryMaterialItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
		inquiryMaterialItemAttachmentConfigureMap
				.setParentNodeName(InquiryMaterialItem.NODENAME);
		inquiryMaterialItemAttachmentConfigureMap
				.setNodeName(InquiryMaterialItemAttachment.NODENAME);
		inquiryMaterialItemAttachmentConfigureMap
				.setNodeType(InquiryMaterialItemAttachment.class);
		inquiryMaterialItemAttachmentConfigureMap
				.setTableName(InquiryMaterialItemAttachment.NODENAME);
		inquiryMaterialItemAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		inquiryMaterialItemAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		inquiryMaterialItemAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(inquiryMaterialItemAttachmentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
