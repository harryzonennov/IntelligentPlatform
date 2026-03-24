package com.company.IntelligentPlatform.logistics.service;


import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.model.InventoryTransferOrderParty;
import com.company.IntelligentPlatform.logistics.model.OutboundDeliveryParty;
import com.company.IntelligentPlatform.logistics.model.WarehouseStore;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreActionNode;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreParty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocSplitMergeRequest;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseStoreActionExecutionProxy extends DocActionExecutionProxy<WarehouseStoreServiceModel, WarehouseStore, WarehouseStoreItem> {

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected WarehouseStoreSpecifier warehouseStoreSpecifier;

    @Autowired
    protected WarehouseStoreServiceUIModelExtension warehouseStoreServiceUIModelExtension;

    @Autowired
    protected WarehouseStoreCrossConvertRequest warehouseStoreCrossConvertRequest;

    protected Logger logger = LoggerFactory.getLogger(WarehouseStoreActionExecutionProxy.class);

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
//        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
//                new DocActionConfigure(
//                        WarehouseStoreActionNode.DOC_ACTION_INSTOCK, WarehouseStoreItem.STATUS_INSTOCK,
//                        null,
//                        ISystemActionCode.ACID_EDIT
//                ),
//                new DocActionConfigure(
//                        WarehouseStoreActionNode.DOC_ACTION_ARCHIVE, WarehouseStoreItem.STATUS_ARCHIVE,
//                        ServiceCollectionsHelper.asList(WarehouseStoreItem.STATUS_INSTOCK),
//                        ISystemActionCode.ACID_EDIT
//                )
//        );
//        return defDocActionConfigureList;
        // DO not handle any action in warehouse store directly
        return null;
    }

    @Override
    public List<DocActionConfigure> getCustomDocActionConfigureList(String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return null;
    }

    @Override
    public List<CrossDocActConfigure> getDefCrossDocActConfigureList() {
        return null;
    }

    @Override
    public List<CrossDocActConfigure> getCustomCrossDocActConfigureList(String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return this.getCustomCrossDocActionConfigureListTool(WarehouseStore.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM, client);
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
                        IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY, ServiceCollectionsHelper.asList(
                        new CrossCopyPartyConversionConfig(WarehouseStoreParty.PARTY_ROLE_PURORG,
                                OutboundDeliveryParty.PARTY_ROLE_PURORG, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(WarehouseStoreParty.PARTY_ROLE_SUPPLIER,
                                OutboundDeliveryParty.PARTY_ROLE_SUPPLIER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(WarehouseStoreParty.PARTY_ROLE_SUPPLIER,
                                OutboundDeliveryParty.PARTY_ROLE_SUPPLIER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(WarehouseStoreParty.PARTY_ROLE_CUSTOMER,
                                OutboundDeliveryParty.PARTY_ROLE_CUSTOMER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(WarehouseStoreParty.PARTY_ROLE_PRODORG,
                                OutboundDeliveryParty.PARTY_ROLE_PRODORG, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(WarehouseStoreParty.PARTY_ROLE_SALESORG,
                                OutboundDeliveryParty.PARTY_ROLE_SALESORG, StandardSwitchProxy.SWITCH_OFF)),
                        StandardSwitchProxy.SWITCH_ON, 0, StandardSwitchProxy.SWITCH_OFF,
                        StandardSwitchProxy.SWITCH_OFF, StandardSwitchProxy.SWITCH_ON, StandardSwitchProxy.SWITCH_ON));
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
                        IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER, ServiceCollectionsHelper.asList(
                        new CrossCopyPartyConversionConfig(WarehouseStoreParty.PARTY_ROLE_PURORG,
                                InventoryTransferOrderParty.PARTY_ROLE_PURORG, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(WarehouseStoreParty.PARTY_ROLE_SUPPLIER,
                                InventoryTransferOrderParty.PARTY_ROLE_SUPPLIER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(WarehouseStoreParty.PARTY_ROLE_SUPPLIER,
                                InventoryTransferOrderParty.PARTY_ROLE_SUPPLIER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(WarehouseStoreParty.PARTY_ROLE_CUSTOMER,
                                InventoryTransferOrderParty.PARTY_ROLE_CUSTOMER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(WarehouseStoreParty.PARTY_ROLE_PRODORG,
                                InventoryTransferOrderParty.PARTY_ROLE_PRODORG, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(WarehouseStoreParty.PARTY_ROLE_SALESORG,
                                InventoryTransferOrderParty.PARTY_ROLE_SALESORG, StandardSwitchProxy.SWITCH_OFF)),
                        StandardSwitchProxy.SWITCH_ON, 0, StandardSwitchProxy.SWITCH_OFF,
                        StandardSwitchProxy.SWITCH_OFF, StandardSwitchProxy.SWITCH_ON, StandardSwitchProxy.SWITCH_ON));
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER, null,
                        StandardSwitchProxy.SWITCH_ON, 0, StandardSwitchProxy.SWITCH_OFF,
                        StandardSwitchProxy.SWITCH_ON, StandardSwitchProxy.SWITCH_ON, StandardSwitchProxy.SWITCH_ON));
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
                        IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT, null,
                        StandardSwitchProxy.SWITCH_ON, 0, StandardSwitchProxy.SWITCH_OFF,
                        StandardSwitchProxy.SWITCH_ON, StandardSwitchProxy.SWITCH_ON, StandardSwitchProxy.SWITCH_ON));
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
                        IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER, null,
                        StandardSwitchProxy.SWITCH_ON, 0, StandardSwitchProxy.SWITCH_OFF,
                        StandardSwitchProxy.SWITCH_ON, StandardSwitchProxy.SWITCH_ON, StandardSwitchProxy.SWITCH_ON));
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_CHECKORDER ,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
                        IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_CHECKORDER,null,
                        StandardSwitchProxy.SWITCH_OFF, 0, StandardSwitchProxy.SWITCH_OFF));
        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier<WarehouseStoreServiceModel, WarehouseStore, WarehouseStoreItem> getDocumentContentSpecifier() {
        return warehouseStoreSpecifier;
    }

    @Override
    public DocSplitMergeRequest<WarehouseStore, WarehouseStoreItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<WarehouseStoreServiceModel, WarehouseStoreItem, ?> getCrossDocCovertRequest() {
        return warehouseStoreCrossConvertRequest ;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return warehouseStoreManager;
    }

    public void executeActionCore(
            WarehouseStoreServiceModel warehouseStoreServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(warehouseStoreServiceModel, docActionCode, null,
                null, serialLogonInfo);
    }

}
