package com.company.IntelligentPlatform.sales.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;
/**
 * Configure Proxy CLASS FOR Service Entity [SalesForcast]
 *
 * @author
 * @date Thu Jan 28 21:38:52 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Repository
public class SalesForcastConfigureProxy extends ServiceEntityConfigureProxy {

    @Override
    public void initConfig() {
        super.initConfig();
        super.setPackageName("net.thorstein.salesDistribution");
        List<ServiceEntityConfigureMap> seConfigureMapList =
                Collections.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
//Init configuration of SalesForcast [ROOT] node
        ServiceEntityConfigureMap salesForcastConfigureMap = new ServiceEntityConfigureMap();
        salesForcastConfigureMap.setParentNodeName(" ");
        salesForcastConfigureMap.setNodeName(SalesForcast.NODENAME);
        salesForcastConfigureMap.setNodeType(SalesForcast.class);
        salesForcastConfigureMap.setTableName(SalesForcast.SENAME);
        salesForcastConfigureMap.setFieldList(super.getBasicDocumentFieldMap());
        salesForcastConfigureMap.addNodeFieldMap("grossPrice", double.class);
        salesForcastConfigureMap.addNodeFieldMap("grossPriceDisplay", double.class);
        salesForcastConfigureMap.addNodeFieldMap("currencyCode", java.lang.String.class);
        salesForcastConfigureMap.addNodeFieldMap("planExecutionDate", java.util.Date.class);
        seConfigureMapList.add(salesForcastConfigureMap);
//Init configuration of SalesForcast [SalesForcastMaterialItem] node
        ServiceEntityConfigureMap salesForcastMaterialItemConfigureMap = new ServiceEntityConfigureMap();
        salesForcastMaterialItemConfigureMap.setParentNodeName(SalesForcast.NODENAME);
        salesForcastMaterialItemConfigureMap.setNodeName(SalesForcastMaterialItem.NODENAME);
        salesForcastMaterialItemConfigureMap.setNodeType(SalesForcastMaterialItem.class);
        salesForcastMaterialItemConfigureMap.setTableName(SalesForcastMaterialItem.NODENAME);
        salesForcastMaterialItemConfigureMap.setFieldList(super.getBasicDocMatItemMap());
        salesForcastMaterialItemConfigureMap.addNodeFieldMap("itemStatus", int.class);
        seConfigureMapList.add(salesForcastMaterialItemConfigureMap);
//Init configuration of SalesForcast [SalesForcastAttachment] node
        ServiceEntityConfigureMap salesForcastAttachmentConfigureMap = new ServiceEntityConfigureMap();
        salesForcastAttachmentConfigureMap.setParentNodeName(SalesForcast.NODENAME);
        salesForcastAttachmentConfigureMap.setNodeName(SalesForcastAttachment.NODENAME);
        salesForcastAttachmentConfigureMap.setNodeType(SalesForcastAttachment.class);
        salesForcastAttachmentConfigureMap.setTableName(SalesForcastAttachment.NODENAME);
        salesForcastAttachmentConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        salesForcastAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
        salesForcastAttachmentConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(salesForcastAttachmentConfigureMap);
//Init configuration of SalesContract [SalesContractParty] node
        ServiceEntityConfigureMap salesContractPartyConfigureMap = new ServiceEntityConfigureMap();
        salesContractPartyConfigureMap.setParentNodeName(SalesForcast.NODENAME);
        salesContractPartyConfigureMap.setNodeName(SalesForcastParty.NODENAME);
        salesContractPartyConfigureMap.setNodeType(SalesForcastParty.class);
        salesContractPartyConfigureMap.setTableName(SalesForcastParty.NODENAME);
        salesContractPartyConfigureMap.setFieldList(super.getBasicInvolvePartyMap());
        seConfigureMapList.add(salesContractPartyConfigureMap);
//Init configuration of SalesForcast [SalesForcastActionNode] node
        ServiceEntityConfigureMap salesForcastActionNodeConfigureMap = new ServiceEntityConfigureMap();
        salesForcastActionNodeConfigureMap.setParentNodeName(SalesForcast.NODENAME);
        salesForcastActionNodeConfigureMap.setNodeName(SalesForcastActionNode.NODENAME);
        salesForcastActionNodeConfigureMap.setNodeType(SalesForcastActionNode.class);
        salesForcastActionNodeConfigureMap.setTableName(SalesForcastActionNode.NODENAME);
        salesForcastActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
        seConfigureMapList.add(salesForcastActionNodeConfigureMap);
//Init configuration of SalesContract [SalesContractMaterialItemAttachment] node
        ServiceEntityConfigureMap salesContractMaterialItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
        salesContractMaterialItemAttachmentConfigureMap.setParentNodeName("SalesForcastMaterialItem");
        salesContractMaterialItemAttachmentConfigureMap.setNodeName(SalesForcastMaterialItemAttachment.NODENAME);
        salesContractMaterialItemAttachmentConfigureMap.setNodeType(SalesForcastMaterialItemAttachment.class);
        salesContractMaterialItemAttachmentConfigureMap.setTableName(SalesForcastMaterialItemAttachment.NODENAME);
        salesContractMaterialItemAttachmentConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        salesContractMaterialItemAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
        salesContractMaterialItemAttachmentConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(salesContractMaterialItemAttachmentConfigureMap);
// End
        super.setSeConfigMapList(seConfigureMapList);
    }


}

