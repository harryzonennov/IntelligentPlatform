package com.company.IntelligentPlatform.logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [PurchaseReturnOrder]
 *
 * @author
 * @date Wed Apr 07 15:23:35 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Repository
public class PurchaseReturnOrderConfigureProxy extends ServiceEntityConfigureProxy {

    @Override
    public void initConfig() {
        super.initConfig();
        super.setPackageName("net.thorstein.logistics");
        List<ServiceEntityConfigureMap> seConfigureMapList =
                Collections.synchronizedList(new ArrayList<>());
//Init configuration of PurchaseReturnOrder [ROOT] node
        ServiceEntityConfigureMap purchaseReturnOrderConfigureMap = new ServiceEntityConfigureMap();
        purchaseReturnOrderConfigureMap.setParentNodeName(" ");
        purchaseReturnOrderConfigureMap.setNodeName(PurchaseReturnOrder.NODENAME);
        purchaseReturnOrderConfigureMap.setNodeType(PurchaseReturnOrder.class);
        purchaseReturnOrderConfigureMap.setTableName(PurchaseReturnOrder.SENAME);
        purchaseReturnOrderConfigureMap.setFieldList(super.getBasicDocumentFieldMap());
        purchaseReturnOrderConfigureMap.addNodeFieldMap("grossPrice", double.class);
        purchaseReturnOrderConfigureMap.addNodeFieldMap("grossPriceDisplay", double.class);
        purchaseReturnOrderConfigureMap.addNodeFieldMap("taxRate", double.class);
        purchaseReturnOrderConfigureMap.addNodeFieldMap("productionBatchNumber", java.lang.String.class);
        seConfigureMapList.add(purchaseReturnOrderConfigureMap);
//Init configuration of PurchaseReturnOrder [PurchaseReturnOrderAttachment] node
        ServiceEntityConfigureMap purchaseReturnOrderAttachmentConfigureMap = new ServiceEntityConfigureMap();
        purchaseReturnOrderAttachmentConfigureMap.setParentNodeName(PurchaseReturnOrder.NODENAME);
        purchaseReturnOrderAttachmentConfigureMap.setNodeName(PurchaseReturnOrderAttachment.NODENAME);
        purchaseReturnOrderAttachmentConfigureMap.setNodeType(PurchaseReturnOrderAttachment.class);
        purchaseReturnOrderAttachmentConfigureMap.setTableName(PurchaseReturnOrderAttachment.NODENAME);
        purchaseReturnOrderAttachmentConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        purchaseReturnOrderAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
        purchaseReturnOrderAttachmentConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(purchaseReturnOrderAttachmentConfigureMap);
//Init configuration of PurchaseReturnOrder [PurchaseReturnOrderActionNode] node
        ServiceEntityConfigureMap purchaseReturnOrderActionNodeConfigureMap = new ServiceEntityConfigureMap();
        purchaseReturnOrderActionNodeConfigureMap.setParentNodeName(PurchaseReturnOrder.NODENAME);
        purchaseReturnOrderActionNodeConfigureMap.setNodeName(PurchaseReturnOrderActionNode.NODENAME);
        purchaseReturnOrderActionNodeConfigureMap.setNodeType(PurchaseReturnOrderActionNode.class);
        purchaseReturnOrderActionNodeConfigureMap.setTableName(PurchaseReturnOrderActionNode.NODENAME);
        purchaseReturnOrderActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
        seConfigureMapList.add(purchaseReturnOrderActionNodeConfigureMap);
//Init configuration of PurchaseReturnOrder [PurchaseReturnOrderParty] node
        ServiceEntityConfigureMap purchaseReturnOrderPartyConfigureMap = new ServiceEntityConfigureMap();
        purchaseReturnOrderPartyConfigureMap.setParentNodeName(PurchaseReturnOrder.NODENAME);
        purchaseReturnOrderPartyConfigureMap.setNodeName(PurchaseReturnOrderParty.NODENAME);
        purchaseReturnOrderPartyConfigureMap.setNodeType(PurchaseReturnOrderParty.class);
        purchaseReturnOrderPartyConfigureMap.setTableName(PurchaseReturnOrderParty.NODENAME);
        purchaseReturnOrderPartyConfigureMap.setFieldList(super.getBasicInvolvePartyMap());
        seConfigureMapList.add(purchaseReturnOrderPartyConfigureMap);
//Init configuration of PurchaseReturnOrder [PurchaseReturnMaterialItem] node
        ServiceEntityConfigureMap purchaseReturnMaterialItemConfigureMap = new ServiceEntityConfigureMap();
        purchaseReturnMaterialItemConfigureMap.setParentNodeName(PurchaseReturnOrder.NODENAME);
        purchaseReturnMaterialItemConfigureMap.setNodeName(PurchaseReturnMaterialItem.NODENAME);
        purchaseReturnMaterialItemConfigureMap.setNodeType(PurchaseReturnMaterialItem.class);
        purchaseReturnMaterialItemConfigureMap.setTableName(PurchaseReturnMaterialItem.NODENAME);
        purchaseReturnMaterialItemConfigureMap.setFieldList(super.getBasicDocMatItemMap());
        purchaseReturnMaterialItemConfigureMap.addNodeFieldMap("refFinAccountUUID", java.lang.String.class);
        purchaseReturnMaterialItemConfigureMap.addNodeFieldMap("refDocItemUUID", java.lang.String.class);
        purchaseReturnMaterialItemConfigureMap.addNodeFieldMap("refDocItemType", int.class);
        purchaseReturnMaterialItemConfigureMap.addNodeFieldMap("refStoreItemUUID", String.class);
        seConfigureMapList.add(purchaseReturnMaterialItemConfigureMap);
//Init configuration of PurchaseReturnOrder [PurchaseReturnMaterialItemAttachment] node
        ServiceEntityConfigureMap purchaseReturnMaterialItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
        purchaseReturnMaterialItemAttachmentConfigureMap.setParentNodeName(PurchaseReturnMaterialItem.NODENAME);
        purchaseReturnMaterialItemAttachmentConfigureMap.setNodeName(PurchaseReturnMaterialItemAttachment.NODENAME);
        purchaseReturnMaterialItemAttachmentConfigureMap.setNodeType(PurchaseReturnMaterialItemAttachment.class);
        purchaseReturnMaterialItemAttachmentConfigureMap.setTableName(PurchaseReturnMaterialItemAttachment.NODENAME);
        purchaseReturnMaterialItemAttachmentConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        purchaseReturnMaterialItemAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
        purchaseReturnMaterialItemAttachmentConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(purchaseReturnMaterialItemAttachmentConfigureMap);
// End
        super.setSeConfigMapList(seConfigureMapList);
    }

}
