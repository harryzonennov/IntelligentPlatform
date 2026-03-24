package com.company.IntelligentPlatform.production.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [BillOfMaterialTemplate]
 *
 * @author
 * @date Fri May 28 20:38:46 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Repository
public class BillOfMaterialTemplateConfigureProxy extends ServiceEntityConfigureProxy {

    @Override
    public void initConfig() {
        super.initConfig();
        super.setPackageName("net.thorstein.production");
        List<ServiceEntityConfigureMap> seConfigureMapList =
                Collections.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
//Init configuration of BillOfMaterialTemplate [ROOT] node
        ServiceEntityConfigureMap billOfMaterialTemplateConfigureMap = new ServiceEntityConfigureMap();
        billOfMaterialTemplateConfigureMap.setParentNodeName(" ");
        billOfMaterialTemplateConfigureMap.setNodeName(BillOfMaterialTemplate.NODENAME);
        billOfMaterialTemplateConfigureMap.setNodeType(BillOfMaterialTemplate.class);
        billOfMaterialTemplateConfigureMap.setTableName(BillOfMaterialTemplate.SENAME);
        billOfMaterialTemplateConfigureMap.setFieldList(super.getBasicDocumentFieldMap());
        billOfMaterialTemplateConfigureMap.addNodeFieldMap("refMaterialSKUUUID", java.lang.String.class);
        billOfMaterialTemplateConfigureMap.addNodeFieldMap("amount", double.class);
        billOfMaterialTemplateConfigureMap.addNodeFieldMap("refUnitUUID", java.lang.String.class);
        billOfMaterialTemplateConfigureMap.addNodeFieldMap("itemCategory", int.class);
        billOfMaterialTemplateConfigureMap.addNodeFieldMap("refRouteOrderUUID", java.lang.String.class);
        billOfMaterialTemplateConfigureMap.addNodeFieldMap("leadTimeCalMode", int.class);
        billOfMaterialTemplateConfigureMap.addNodeFieldMap("refWocUUID", java.lang.String.class);
        billOfMaterialTemplateConfigureMap.addNodeFieldMap("versionNumber", int.class);
        billOfMaterialTemplateConfigureMap.addNodeFieldMap("patchNumber", int.class);
        billOfMaterialTemplateConfigureMap.addNodeFieldMap("refTemplateUUID", java.lang.String.class);
        seConfigureMapList.add(billOfMaterialTemplateConfigureMap);
//Init configuration of BillOfMaterialTemplate [BillOfMaterialTemplateItem] node
        ServiceEntityConfigureMap billOfMaterialTemplateItemConfigureMap = new ServiceEntityConfigureMap();
        billOfMaterialTemplateItemConfigureMap.setParentNodeName(BillOfMaterialTemplate.NODENAME);
        billOfMaterialTemplateItemConfigureMap.setNodeName(BillOfMaterialTemplateItem.NODENAME);
        billOfMaterialTemplateItemConfigureMap.setNodeType(BillOfMaterialTemplateItem.class);
        billOfMaterialTemplateItemConfigureMap.setTableName(BillOfMaterialTemplateItem.NODENAME);
        billOfMaterialTemplateItemConfigureMap.setFieldList(super.getBasicDocMatItemMap());
        billOfMaterialTemplateItemConfigureMap.addNodeFieldMap("currencyCode", java.lang.String.class);
        billOfMaterialTemplateItemConfigureMap.addNodeFieldMap("refFinMatItemUUID", java.lang.String.class);
        billOfMaterialTemplateItemConfigureMap.addNodeFieldMap("layer", int.class);
        billOfMaterialTemplateItemConfigureMap.addNodeFieldMap("refParentItemUUID", java.lang.String.class);
        billOfMaterialTemplateItemConfigureMap.addNodeFieldMap("itemCategory", int.class);
        billOfMaterialTemplateItemConfigureMap.addNodeFieldMap("leadTimeOffset", double.class);
        billOfMaterialTemplateItemConfigureMap.addNodeFieldMap("theoLossRate", double.class);
        billOfMaterialTemplateItemConfigureMap.addNodeFieldMap("refSubBOMUUID", java.lang.String.class);
        billOfMaterialTemplateItemConfigureMap.addNodeFieldMap("refRouteProcessItemUUID", java.lang.String.class);
        billOfMaterialTemplateItemConfigureMap.addNodeFieldMap("refWocUUID", java.lang.String.class);
        seConfigureMapList.add(billOfMaterialTemplateItemConfigureMap);
//Init configuration of BillOfMaterialTemplate [BillOfMaterialTemplateItemUpdateLog] node
        ServiceEntityConfigureMap billOfMaterialTemplateItemUpdateLogConfigureMap = new ServiceEntityConfigureMap();
        billOfMaterialTemplateItemUpdateLogConfigureMap.setParentNodeName(BillOfMaterialTemplateItem.NODENAME);
        billOfMaterialTemplateItemUpdateLogConfigureMap.setNodeName(BillOfMaterialTemplateItemUpdateLog.NODENAME);
        billOfMaterialTemplateItemUpdateLogConfigureMap.setNodeType(BillOfMaterialTemplateItemUpdateLog.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.setTableName(BillOfMaterialTemplateItemUpdateLog.NODENAME);
        billOfMaterialTemplateItemUpdateLogConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("refMaterialSKUUUID", java.lang.String.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("updateAmount", double.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("updateRefUnitUUID", java.lang.String.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("updateAmountMode", int.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("layer", int.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("refParentItemUUID", java.lang.String.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("refBeforeItemUUID", java.lang.String.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("refAftertemUUID", java.lang.String.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("itemCategory", int.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("leadTimeOffset", double.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("updateTheoLossRate", double.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("updateTheoLossRateMode", int.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("refSubBOMUUID", java.lang.String.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("refRouteProcessItemUUID",
                java.lang.String.class);
        billOfMaterialTemplateItemUpdateLogConfigureMap.addNodeFieldMap("refWocUUID", java.lang.String.class);
        seConfigureMapList.add(billOfMaterialTemplateItemUpdateLogConfigureMap);
//Init configuration of BillOfMaterialTemplate [BillOfMaterialTemplateAttachment] node
        ServiceEntityConfigureMap billOfMaterialTemplateAttachmentConfigureMap = new ServiceEntityConfigureMap();
        billOfMaterialTemplateAttachmentConfigureMap.setParentNodeName(BillOfMaterialTemplate.NODENAME);
        billOfMaterialTemplateAttachmentConfigureMap.setNodeName(BillOfMaterialTemplateAttachment.NODENAME);
        billOfMaterialTemplateAttachmentConfigureMap.setNodeType(BillOfMaterialTemplateAttachment.class);
        billOfMaterialTemplateAttachmentConfigureMap.setTableName(BillOfMaterialTemplateAttachment.NODENAME);
        billOfMaterialTemplateAttachmentConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        billOfMaterialTemplateAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
        billOfMaterialTemplateAttachmentConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(billOfMaterialTemplateAttachmentConfigureMap);
//Init configuration of BillOfMaterialOrder [BillOfMaterialTemplateActionNode] node
        ServiceEntityConfigureMap billOfMaterialTemplateActionNodeConfigureMap = new ServiceEntityConfigureMap();
        billOfMaterialTemplateActionNodeConfigureMap.setParentNodeName(BillOfMaterialTemplate.NODENAME);
        billOfMaterialTemplateActionNodeConfigureMap.setNodeName(BillOfMaterialTemplateActionNode.NODENAME);
        billOfMaterialTemplateActionNodeConfigureMap.setNodeType(BillOfMaterialTemplateActionNode.class);
        billOfMaterialTemplateActionNodeConfigureMap.setTableName(BillOfMaterialTemplateActionNode.NODENAME);
        billOfMaterialTemplateActionNodeConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        billOfMaterialTemplateActionNodeConfigureMap.addNodeFieldMap("processIndex", int.class);
        billOfMaterialTemplateActionNodeConfigureMap.addNodeFieldMap("flatNodeSwitch", int.class);
        billOfMaterialTemplateActionNodeConfigureMap.addNodeFieldMap("docActionCode", int.class);
        billOfMaterialTemplateActionNodeConfigureMap.addNodeFieldMap("executionTime", java.util.Date.class);
        billOfMaterialTemplateActionNodeConfigureMap.addNodeFieldMap("executedByUUID", java.lang.String.class);
        seConfigureMapList.add(billOfMaterialTemplateActionNodeConfigureMap);
// End
        super.setSeConfigMapList(seConfigureMapList);
    }


}
