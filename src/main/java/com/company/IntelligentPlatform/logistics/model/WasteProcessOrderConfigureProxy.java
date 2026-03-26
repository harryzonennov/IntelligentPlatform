package com.company.IntelligentPlatform.logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [WasteProcessOrder]
 *
 * @author
 * @date Sun Jun 13 21:15:58 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Repository
public class WasteProcessOrderConfigureProxy extends ServiceEntityConfigureProxy {

    @Override
    public void initConfig() {
        super.initConfig();
        super.setPackageName("net.thorstein.logistics");
        List<ServiceEntityConfigureMap> seConfigureMapList =
                Collections.synchronizedList(new ArrayList<>());
//Init configuration of WasteProcessOrder [ROOT] node
        ServiceEntityConfigureMap wasteProcessOrderConfigureMap = new ServiceEntityConfigureMap();
        wasteProcessOrderConfigureMap.setParentNodeName(" ");
        wasteProcessOrderConfigureMap.setNodeName(WasteProcessOrder.NODENAME);
        wasteProcessOrderConfigureMap.setNodeType(WasteProcessOrder.class);
        wasteProcessOrderConfigureMap.setTableName(WasteProcessOrder.SENAME);
        wasteProcessOrderConfigureMap.setFieldList(super.getBasicDocumentFieldMap());
        wasteProcessOrderConfigureMap.addNodeFieldMap("grossPrice", double.class);
        wasteProcessOrderConfigureMap.addNodeFieldMap("grossPriceDisplay", double.class);
        wasteProcessOrderConfigureMap.addNodeFieldMap("currencyCode", java.lang.String.class);
        wasteProcessOrderConfigureMap.addNodeFieldMap("processType", int.class);
        seConfigureMapList.add(wasteProcessOrderConfigureMap);
//Init configuration of WasteProcessOrder [WasteProcessMaterialItem] node
        ServiceEntityConfigureMap wasteProcessMaterialItemConfigureMap = new ServiceEntityConfigureMap();
        wasteProcessMaterialItemConfigureMap.setParentNodeName(WasteProcessOrder.NODENAME);
        wasteProcessMaterialItemConfigureMap.setNodeName(WasteProcessMaterialItem.NODENAME);
        wasteProcessMaterialItemConfigureMap.setNodeType(WasteProcessMaterialItem.class);
        wasteProcessMaterialItemConfigureMap.setTableName(WasteProcessMaterialItem.NODENAME);
        wasteProcessMaterialItemConfigureMap.setFieldList(super.getBasicDocMatItemMap());
        wasteProcessMaterialItemConfigureMap.addNodeFieldMap("itemStatus", int.class);
        wasteProcessMaterialItemConfigureMap.addNodeFieldMap("storeCheckStatus", int.class);
        wasteProcessMaterialItemConfigureMap.addNodeFieldMap("refStoreItemUUID", java.lang.String.class);
        seConfigureMapList.add(wasteProcessMaterialItemConfigureMap);
//Init configuration of WasteProcessOrder [WasteProcessOrderAttachment] node
        ServiceEntityConfigureMap wasteProcessOrderAttachmentConfigureMap = new ServiceEntityConfigureMap();
        wasteProcessOrderAttachmentConfigureMap.setParentNodeName(WasteProcessOrder.NODENAME);
        wasteProcessOrderAttachmentConfigureMap.setNodeName(WasteProcessOrderAttachment.NODENAME);
        wasteProcessOrderAttachmentConfigureMap.setNodeType(WasteProcessOrderAttachment.class);
        wasteProcessOrderAttachmentConfigureMap.setTableName(WasteProcessOrderAttachment.NODENAME);
        wasteProcessOrderAttachmentConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        wasteProcessOrderAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
        wasteProcessOrderAttachmentConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(wasteProcessOrderAttachmentConfigureMap);
//Init configuration of WasteProcessOrder [WasteProcessOrderParty] node
        ServiceEntityConfigureMap wasteProcessOrderPartyConfigureMap = new ServiceEntityConfigureMap();
        wasteProcessOrderPartyConfigureMap.setParentNodeName(WasteProcessOrder.NODENAME);
        wasteProcessOrderPartyConfigureMap.setNodeName(WasteProcessOrderParty.NODENAME);
        wasteProcessOrderPartyConfigureMap.setNodeType(WasteProcessOrderParty.class);
        wasteProcessOrderPartyConfigureMap.setTableName(WasteProcessOrderParty.NODENAME);
        wasteProcessOrderPartyConfigureMap.setFieldList(super.getBasicInvolvePartyMap());
        seConfigureMapList.add(wasteProcessOrderPartyConfigureMap);
//Init configuration of WasteProcessOrder [WasteProcessOrderActionNode] node
        ServiceEntityConfigureMap wasteProcessOrderActionNodeConfigureMap = new ServiceEntityConfigureMap();
        wasteProcessOrderActionNodeConfigureMap.setParentNodeName(WasteProcessOrder.NODENAME);
        wasteProcessOrderActionNodeConfigureMap.setNodeName(WasteProcessOrderActionNode.NODENAME);
        wasteProcessOrderActionNodeConfigureMap.setNodeType(WasteProcessOrderActionNode.class);
        wasteProcessOrderActionNodeConfigureMap.setTableName(WasteProcessOrderActionNode.NODENAME);
        wasteProcessOrderActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
        seConfigureMapList.add(wasteProcessOrderActionNodeConfigureMap);
//Init configuration of WasteProcessOrder [WasteProcessMaterialItemAttachment] node
        ServiceEntityConfigureMap wasteProcessMaterialItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
        wasteProcessMaterialItemAttachmentConfigureMap.setParentNodeName(WasteProcessMaterialItem.NODENAME);
        wasteProcessMaterialItemAttachmentConfigureMap.setNodeName(WasteProcessMaterialItemAttachment.NODENAME);
        wasteProcessMaterialItemAttachmentConfigureMap.setNodeType(WasteProcessMaterialItemAttachment.class);
        wasteProcessMaterialItemAttachmentConfigureMap.setTableName(WasteProcessMaterialItemAttachment.NODENAME);
        wasteProcessMaterialItemAttachmentConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        wasteProcessMaterialItemAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
        wasteProcessMaterialItemAttachmentConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(wasteProcessMaterialItemAttachmentConfigureMap);
// End
        super.setSeConfigMapList(seConfigureMapList);
    }

}
