package com.company.IntelligentPlatform.logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [PurchaseRequest]
 *
 * @author
 * @date Mon Apr 05 00:45:51 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Repository
public class PurchaseRequestConfigureProxy extends ServiceEntityConfigureProxy {

    @Override
    public void initConfig() {
        super.initConfig();
        super.setPackageName("net.thorstein.logistics");
        List<ServiceEntityConfigureMap> seConfigureMapList =
                Collections.synchronizedList(new ArrayList<>());
//Init configuration of PurchaseRequest [ROOT] node
        ServiceEntityConfigureMap purchaseRequestConfigureMap = new ServiceEntityConfigureMap();
        purchaseRequestConfigureMap.setParentNodeName(" ");
        purchaseRequestConfigureMap.setNodeName(PurchaseRequest.NODENAME);
        purchaseRequestConfigureMap.setNodeType(PurchaseRequest.class);
        purchaseRequestConfigureMap.setTableName(PurchaseRequest.SENAME);
        purchaseRequestConfigureMap.setFieldList(super.getBasicDocumentFieldMap());
        purchaseRequestConfigureMap.addNodeFieldMap("grossPrice", double.class);
        purchaseRequestConfigureMap.addNodeFieldMap("grossPriceDisplay", double.class);
        purchaseRequestConfigureMap.addNodeFieldMap("currencyCode", java.lang.String.class);
        purchaseRequestConfigureMap.addNodeFieldMap("planExecutionDate", java.util.Date.class);
        purchaseRequestConfigureMap.addNodeFieldMap("productionBatchNumber", java.lang.String.class);
        seConfigureMapList.add(purchaseRequestConfigureMap);
//Init configuration of PurchaseRequest [PurchaseRequestAttachment] node
        ServiceEntityConfigureMap purchaseRequestAttachmentConfigureMap = new ServiceEntityConfigureMap();
        purchaseRequestAttachmentConfigureMap.setParentNodeName(PurchaseRequest.NODENAME);
        purchaseRequestAttachmentConfigureMap.setNodeName(PurchaseRequestAttachment.NODENAME);
        purchaseRequestAttachmentConfigureMap.setNodeType(PurchaseRequestAttachment.class);
        purchaseRequestAttachmentConfigureMap.setTableName(PurchaseRequestAttachment.NODENAME);
        purchaseRequestAttachmentConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        purchaseRequestAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
        purchaseRequestAttachmentConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(purchaseRequestAttachmentConfigureMap);
//Init configuration of PurchaseRequest [PurchaseRequestActionNode] node
        ServiceEntityConfigureMap purchaseRequestActionNodeConfigureMap = new ServiceEntityConfigureMap();
        purchaseRequestActionNodeConfigureMap.setParentNodeName(PurchaseRequest.NODENAME);
        purchaseRequestActionNodeConfigureMap.setNodeName(PurchaseRequestActionNode.NODENAME);
        purchaseRequestActionNodeConfigureMap.setNodeType(PurchaseRequestActionNode.class);
        purchaseRequestActionNodeConfigureMap.setTableName(PurchaseRequestActionNode.NODENAME);
        purchaseRequestActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
        seConfigureMapList.add(purchaseRequestActionNodeConfigureMap);
//Init configuration of PurchaseRequest [PurchaseRequestParty] node
        ServiceEntityConfigureMap purchaseRequestPartyConfigureMap = new ServiceEntityConfigureMap();
        purchaseRequestPartyConfigureMap.setParentNodeName(PurchaseRequest.NODENAME);
        purchaseRequestPartyConfigureMap.setNodeName(PurchaseRequestParty.NODENAME);
        purchaseRequestPartyConfigureMap.setNodeType(PurchaseRequestParty.class);
        purchaseRequestPartyConfigureMap.setTableName(PurchaseRequestParty.NODENAME);
        purchaseRequestPartyConfigureMap.setFieldList(super.getBasicInvolvePartyMap());
        seConfigureMapList.add(purchaseRequestPartyConfigureMap);
//Init configuration of PurchaseRequest [PurchaseRequestMaterialItem] node
        ServiceEntityConfigureMap purchaseRequestMaterialItemConfigureMap = new ServiceEntityConfigureMap();
        purchaseRequestMaterialItemConfigureMap.setParentNodeName(PurchaseRequest.NODENAME);
        purchaseRequestMaterialItemConfigureMap.setNodeName(PurchaseRequestMaterialItem.NODENAME);
        purchaseRequestMaterialItemConfigureMap.setNodeType(PurchaseRequestMaterialItem.class);
        purchaseRequestMaterialItemConfigureMap.setTableName(PurchaseRequestMaterialItem.NODENAME);
        purchaseRequestMaterialItemConfigureMap.setFieldList(super.getBasicDocMatItemMap());
        seConfigureMapList.add(purchaseRequestMaterialItemConfigureMap);
//Init configuration of PurchaseRequest [PurchaseRequestMaterialItemAttachment] node
        ServiceEntityConfigureMap purchaseRequestMaterialItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
        purchaseRequestMaterialItemAttachmentConfigureMap.setParentNodeName(PurchaseRequestMaterialItem.NODENAME);
        purchaseRequestMaterialItemAttachmentConfigureMap.setNodeName(PurchaseRequestMaterialItemAttachment.NODENAME);
        purchaseRequestMaterialItemAttachmentConfigureMap.setNodeType(PurchaseRequestMaterialItemAttachment.class);
        purchaseRequestMaterialItemAttachmentConfigureMap.setTableName(PurchaseRequestMaterialItemAttachment.NODENAME);
        purchaseRequestMaterialItemAttachmentConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        purchaseRequestMaterialItemAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
        purchaseRequestMaterialItemAttachmentConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(purchaseRequestMaterialItemAttachmentConfigureMap);
// End
        super.setSeConfigMapList(seConfigureMapList);
    }

}
