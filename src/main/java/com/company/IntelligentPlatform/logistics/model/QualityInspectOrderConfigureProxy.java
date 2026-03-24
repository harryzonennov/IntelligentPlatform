package com.company.IntelligentPlatform.logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [QualityInspectOrder]
 *
 * @author
 * @date Thu Jul 11 21:17:21 CST 2019
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class QualityInspectOrderConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.logistics");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of QualityInspectOrder [ROOT] node
		ServiceEntityConfigureMap qualityInspectOrderConfigureMap = new ServiceEntityConfigureMap();
		qualityInspectOrderConfigureMap.setParentNodeName(" ");
		qualityInspectOrderConfigureMap
				.setNodeName(QualityInspectOrder.NODENAME);
		qualityInspectOrderConfigureMap.setNodeType(QualityInspectOrder.class);
		qualityInspectOrderConfigureMap
				.setTableName(QualityInspectOrder.SENAME);
		qualityInspectOrderConfigureMap.setFieldList(super
				.getBasicDocumentFieldMap());
		qualityInspectOrderConfigureMap.addNodeFieldMap("inspectType",
				int.class);
		qualityInspectOrderConfigureMap.addNodeFieldMap("reservedDocType",
				int.class);
		qualityInspectOrderConfigureMap.addNodeFieldMap("reservedDocUUID",
				java.lang.String.class);
		qualityInspectOrderConfigureMap.addNodeFieldMap("checkStatus",
				int.class);
		qualityInspectOrderConfigureMap.addNodeFieldMap("grossPrice",
				double.class);
		qualityInspectOrderConfigureMap.addNodeFieldMap("checkDate",
				java.util.Date.class);
		qualityInspectOrderConfigureMap.addNodeFieldMap("category", int.class);
		qualityInspectOrderConfigureMap.addNodeFieldMap("checkResult",
				java.lang.String.class);
		qualityInspectOrderConfigureMap.addNodeFieldMap("refWarehouseUUID",
				java.lang.String.class);
		qualityInspectOrderConfigureMap.addNodeFieldMap("refWarehouseAreaUUID",
				java.lang.String.class);
		qualityInspectOrderConfigureMap.addNodeFieldMap("purchaseBatchNumber",
				java.lang.String.class);
		qualityInspectOrderConfigureMap.addNodeFieldMap("productionBatchNumber",
				java.lang.String.class);
		seConfigureMapList.add(qualityInspectOrderConfigureMap);

		ServiceEntityConfigureMap qualityInspectOrderPartyConfigureMap = new ServiceEntityConfigureMap();
		qualityInspectOrderPartyConfigureMap.setParentNodeName(QualityInspectOrder.NODENAME);
		qualityInspectOrderPartyConfigureMap.setNodeName(QualityInspectOrderParty.NODENAME);
		qualityInspectOrderPartyConfigureMap.setNodeType(QualityInspectOrderParty.class);
		qualityInspectOrderPartyConfigureMap.setTableName(QualityInspectOrderParty.NODENAME);
		qualityInspectOrderPartyConfigureMap.setFieldList(super.getBasicInvolvePartyMap());
		seConfigureMapList.add(qualityInspectOrderPartyConfigureMap);
		// Init configuration of QualityInspectOrder [QualityInsOrderAttachment]
		// node
		ServiceEntityConfigureMap qualityInsOrderAttachmentConfigureMap = new ServiceEntityConfigureMap();
		qualityInsOrderAttachmentConfigureMap
				.setParentNodeName(QualityInspectOrder.NODENAME);
		qualityInsOrderAttachmentConfigureMap
				.setNodeName(QualityInsOrderAttachment.NODENAME);
		qualityInsOrderAttachmentConfigureMap
				.setNodeType(QualityInsOrderAttachment.class);
		qualityInsOrderAttachmentConfigureMap
				.setTableName(QualityInsOrderAttachment.NODENAME);
		qualityInsOrderAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		qualityInsOrderAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		qualityInsOrderAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(qualityInsOrderAttachmentConfigureMap);
		// Init configuration of QualityInspectOrder [QualityInspectMatItem]
		// node
		ServiceEntityConfigureMap qualityInspectMatItemConfigureMap = new ServiceEntityConfigureMap();
		qualityInspectMatItemConfigureMap
				.setParentNodeName(QualityInspectOrder.NODENAME);
		qualityInspectMatItemConfigureMap
				.setNodeName(QualityInspectMatItem.NODENAME);
		qualityInspectMatItemConfigureMap
				.setNodeType(QualityInspectMatItem.class);
		qualityInspectMatItemConfigureMap
				.setTableName(QualityInspectMatItem.NODENAME);
		qualityInspectMatItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		qualityInspectMatItemConfigureMap.addNodeFieldMap("itemInspectType",
				int.class);
		qualityInspectMatItemConfigureMap.addNodeFieldMap("itemCheckStatus",
				int.class);
		qualityInspectMatItemConfigureMap.addNodeFieldMap("checkDate",
				java.util.Date.class);
		qualityInspectMatItemConfigureMap.addNodeFieldMap("checkTimes",
				int.class);
		qualityInspectMatItemConfigureMap.addNodeFieldMap("itemCheckResult",
				java.lang.String.class);
		qualityInspectMatItemConfigureMap.addNodeFieldMap("sampleRate",
				double.class);
		qualityInspectMatItemConfigureMap.addNodeFieldMap("sampleAmount",
				double.class);
		qualityInspectMatItemConfigureMap.addNodeFieldMap("sampleUnitUUID",
				java.lang.String.class);
		qualityInspectMatItemConfigureMap.addNodeFieldMap(
				"refWarehouseAreaUUID", java.lang.String.class);
		qualityInspectMatItemConfigureMap.addNodeFieldMap(
				"failAmount", double.class);
		qualityInspectMatItemConfigureMap.addNodeFieldMap(
				"failRefUnitUUID", java.lang.String.class);
		qualityInspectMatItemConfigureMap.addNodeFieldMap(
				"refWasteWarehouseUUID", java.lang.String.class);
		qualityInspectMatItemConfigureMap.addNodeFieldMap(
				"refWasteWareAreaUUID", java.lang.String.class);
		seConfigureMapList.add(qualityInspectMatItemConfigureMap);


		ServiceEntityConfigureMap qualityInspectMatItemPartyConfigureMap = new ServiceEntityConfigureMap();
		qualityInspectMatItemPartyConfigureMap.setParentNodeName(QualityInspectMatItem.NODENAME);
		qualityInspectMatItemPartyConfigureMap.setNodeName(QualityInspectMatItemParty.NODENAME);
		qualityInspectMatItemPartyConfigureMap.setNodeType(QualityInspectMatItemParty.class);
		qualityInspectMatItemPartyConfigureMap.setTableName(QualityInspectMatItemParty.NODENAME);
		qualityInspectMatItemPartyConfigureMap.setFieldList(super.getBasicInvolvePartyMap());
		seConfigureMapList.add(qualityInspectMatItemPartyConfigureMap);

		ServiceEntityConfigureMap qualityInsOrderActionNodeConfigureMap = new ServiceEntityConfigureMap();
		qualityInsOrderActionNodeConfigureMap.setParentNodeName(QualityInspectOrder.NODENAME);
		qualityInsOrderActionNodeConfigureMap.setNodeName(QualityInsOrderActionNode.NODENAME);
		qualityInsOrderActionNodeConfigureMap.setNodeType(QualityInsOrderActionNode.class);
		qualityInsOrderActionNodeConfigureMap.setTableName(QualityInsOrderActionNode.NODENAME);
		qualityInsOrderActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
		seConfigureMapList.add(qualityInsOrderActionNodeConfigureMap);
		// Init configuration of QualityInspectOrder
		// [QualityInsMatItemAttachment] node
		ServiceEntityConfigureMap qualityInsMatItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
		qualityInsMatItemAttachmentConfigureMap
				.setParentNodeName(QualityInspectMatItem.NODENAME);
		qualityInsMatItemAttachmentConfigureMap
				.setNodeName(QualityInsMatItemAttachment.NODENAME);
		qualityInsMatItemAttachmentConfigureMap
				.setNodeType(QualityInsMatItemAttachment.class);
		qualityInsMatItemAttachmentConfigureMap
				.setTableName(QualityInsMatItemAttachment.NODENAME);
		qualityInsMatItemAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		qualityInsMatItemAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		qualityInsMatItemAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(qualityInsMatItemAttachmentConfigureMap);
		// Init configuration of QualityInspectOrder
		// [QualityInspectPropertyItem] node
		ServiceEntityConfigureMap qualityInspectPropertyItemConfigureMap = new ServiceEntityConfigureMap();
		qualityInspectPropertyItemConfigureMap
				.setParentNodeName(QualityInspectMatItem.NODENAME);
		qualityInspectPropertyItemConfigureMap
				.setNodeName(QualityInspectPropertyItem.NODENAME);
		qualityInspectPropertyItemConfigureMap
				.setNodeType(QualityInspectPropertyItem.class);
		qualityInspectPropertyItemConfigureMap
				.setTableName(QualityInspectPropertyItem.NODENAME);
		qualityInspectPropertyItemConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap(
				"propCheckStatus", int.class);
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap("fieldName",
				java.lang.String.class);
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap(
				"actualValueDouble", double.class);
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap(
				"criCenterValueDouble", double.class);
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap(
				"criLowValueDouble", double.class);
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap(
				"criHighValueDouble", double.class);
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap(
				"criOffSetValueDouble", double.class);
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap("actualValue",
				java.lang.String.class);
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap(
				"criCenterValue", java.lang.String.class);
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap("criLowValue",
				java.lang.String.class);
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap("criHighValue",
				java.lang.String.class);
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap(
				"refPropertyUUID", java.lang.String.class);
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap("refUnitUUID",
				java.lang.String.class);
		qualityInspectPropertyItemConfigureMap.addNodeFieldMap(
				"criOffSetValue", java.lang.String.class);
		seConfigureMapList.add(qualityInspectPropertyItemConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
