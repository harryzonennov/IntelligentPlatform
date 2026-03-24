package com.company.IntelligentPlatform.production.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [BillOfMaterialOrder]
 *
 * @author
 * @date Sat Jun 05 21:46:15 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Repository
public class BillOfMaterialOrderConfigureProxy extends ServiceEntityConfigureProxy {

    @Override
    public void initConfig() {
        super.initConfig();
        super.setPackageName("net.thorstein.production");
        List<ServiceEntityConfigureMap> seConfigureMapList =
                Collections.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
//Init configuration of BillOfMaterialOrder [ROOT] node
        ServiceEntityConfigureMap billOfMaterialOrderConfigureMap = new ServiceEntityConfigureMap();
        billOfMaterialOrderConfigureMap.setParentNodeName(" ");
        billOfMaterialOrderConfigureMap.setNodeName(BillOfMaterialOrder.NODENAME);
        billOfMaterialOrderConfigureMap.setNodeType(BillOfMaterialOrder.class);
        billOfMaterialOrderConfigureMap.setTableName(BillOfMaterialOrder.SENAME);
        billOfMaterialOrderConfigureMap.setFieldList(super.getBasicDocumentFieldMap());
        billOfMaterialOrderConfigureMap.addNodeFieldMap("refMaterialSKUUUID", java.lang.String.class);
        billOfMaterialOrderConfigureMap.addNodeFieldMap("amount", double.class);
        billOfMaterialOrderConfigureMap.addNodeFieldMap("refUnitUUID", java.lang.String.class);
        billOfMaterialOrderConfigureMap.addNodeFieldMap("status", int.class);
        billOfMaterialOrderConfigureMap.addNodeFieldMap("itemCategory", int.class);
        billOfMaterialOrderConfigureMap.addNodeFieldMap("refRouteOrderUUID", java.lang.String.class);
        billOfMaterialOrderConfigureMap.addNodeFieldMap("leadTimeCalMode", int.class);
        billOfMaterialOrderConfigureMap.addNodeFieldMap("refWocUUID", java.lang.String.class);
        billOfMaterialOrderConfigureMap.addNodeFieldMap("versionNumber", int.class);
        billOfMaterialOrderConfigureMap.addNodeFieldMap("patchNumber", int.class);
        billOfMaterialOrderConfigureMap.addNodeFieldMap("refTemplateUUID", java.lang.String.class);
        seConfigureMapList.add(billOfMaterialOrderConfigureMap);
//Init configuration of BillOfMaterialOrder [BillOfMaterialItem] node
        ServiceEntityConfigureMap billOfMaterialItemConfigureMap = new ServiceEntityConfigureMap();
        billOfMaterialItemConfigureMap.setParentNodeName(BillOfMaterialOrder.NODENAME);
        billOfMaterialItemConfigureMap.setNodeName(BillOfMaterialItem.NODENAME);
        billOfMaterialItemConfigureMap.setNodeType(BillOfMaterialItem.class);
        billOfMaterialItemConfigureMap.setTableName(BillOfMaterialItem.NODENAME);
        billOfMaterialItemConfigureMap.setFieldList(super.getBasicDocMatItemMap());

        billOfMaterialItemConfigureMap.addNodeFieldMap("layer", int.class);
        billOfMaterialItemConfigureMap.addNodeFieldMap("refParentItemUUID", java.lang.String.class);
        billOfMaterialItemConfigureMap.addNodeFieldMap("itemCategory", int.class);
        billOfMaterialItemConfigureMap.addNodeFieldMap("leadTimeOffset", double.class);
        billOfMaterialItemConfigureMap.addNodeFieldMap("theoLossRate", double.class);
        billOfMaterialItemConfigureMap.addNodeFieldMap("refSubBOMUUID", java.lang.String.class);
        billOfMaterialItemConfigureMap.addNodeFieldMap("refRouteProcessItemUUID", java.lang.String.class);
        billOfMaterialItemConfigureMap.addNodeFieldMap("refWocUUID", java.lang.String.class);
        seConfigureMapList.add(billOfMaterialItemConfigureMap);
//Init configuration of BillOfMaterialOrder [BillOfMaterialItemUpdateLog] node
        ServiceEntityConfigureMap billOfMaterialItemUpdateLogConfigureMap = new ServiceEntityConfigureMap();
        billOfMaterialItemUpdateLogConfigureMap.setParentNodeName(BillOfMaterialItem.NODENAME);
        billOfMaterialItemUpdateLogConfigureMap.setNodeName(BillOfMaterialItemUpdateLog.NODENAME);
        billOfMaterialItemUpdateLogConfigureMap.setNodeType(BillOfMaterialItemUpdateLog.class);
        billOfMaterialItemUpdateLogConfigureMap.setTableName(BillOfMaterialItemUpdateLog.NODENAME);
        billOfMaterialItemUpdateLogConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("refMaterialSKUUUID", java.lang.String.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("updateAmount", double.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("updateRefUnitUUID", java.lang.String.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("updateAmountMode", int.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("layer", int.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("refParentItemUUID", java.lang.String.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("refBeforeItemUUID", java.lang.String.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("refAftertemUUID", java.lang.String.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("itemCategory", int.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("leadTimeOffset", double.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("updateTheoLossRate", double.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("updateTheoLossRateMode", int.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("refSubBOMUUID", java.lang.String.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("refRouteProcessItemUUID", java.lang.String.class);
        billOfMaterialItemUpdateLogConfigureMap.addNodeFieldMap("refWocUUID", java.lang.String.class);
        seConfigureMapList.add(billOfMaterialItemUpdateLogConfigureMap);
//Init configuration of BillOfMaterialOrder [BillOfMaterialAttachment] node
        ServiceEntityConfigureMap billOfMaterialAttachmentConfigureMap = new ServiceEntityConfigureMap();
        billOfMaterialAttachmentConfigureMap.setParentNodeName(BillOfMaterialOrder.NODENAME);
        billOfMaterialAttachmentConfigureMap.setNodeName(BillOfMaterialAttachment.NODENAME);
        billOfMaterialAttachmentConfigureMap.setNodeType(BillOfMaterialAttachment.class);
        billOfMaterialAttachmentConfigureMap.setTableName(BillOfMaterialAttachment.NODENAME);
        billOfMaterialAttachmentConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        billOfMaterialAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
        billOfMaterialAttachmentConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(billOfMaterialAttachmentConfigureMap);
//Init configuration of BillOfMaterialOrder [BillOfMaterialOrderActionNode] node
        ServiceEntityConfigureMap billOfMaterialOrderActionNodeConfigureMap = new ServiceEntityConfigureMap();
        billOfMaterialOrderActionNodeConfigureMap.setParentNodeName(BillOfMaterialOrder.NODENAME);
        billOfMaterialOrderActionNodeConfigureMap.setNodeName(BillOfMaterialOrderActionNode.NODENAME);
        billOfMaterialOrderActionNodeConfigureMap.setNodeType(BillOfMaterialOrderActionNode.class);
        billOfMaterialOrderActionNodeConfigureMap.setTableName(BillOfMaterialOrderActionNode.NODENAME);
        billOfMaterialOrderActionNodeConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        billOfMaterialOrderActionNodeConfigureMap.addNodeFieldMap("processIndex", int.class);
        billOfMaterialOrderActionNodeConfigureMap.addNodeFieldMap("flatNodeSwitch", int.class);
        billOfMaterialOrderActionNodeConfigureMap.addNodeFieldMap("docActionCode", int.class);
        billOfMaterialOrderActionNodeConfigureMap.addNodeFieldMap("executionTime", java.util.Date.class);
        billOfMaterialOrderActionNodeConfigureMap.addNodeFieldMap("executedByUUID", java.lang.String.class);
        seConfigureMapList.add(billOfMaterialOrderActionNodeConfigureMap);
        seConfigureMapList.add(billOfMaterialItemUpdateLogConfigureMap);
// End
        super.setSeConfigMapList(seConfigureMapList);
    }


}
