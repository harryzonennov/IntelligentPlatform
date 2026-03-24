package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.OutboundDeliveryServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.dto.OutboundDeliveryUIModel;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OutboundDeliveryActionExecutionProxy extends DocActionExecutionProxy<OutboundDeliveryServiceModel,
        OutboundDelivery, OutboundItem> {

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    @Autowired
    protected OutboundDeliverySpecifier outboundDeliverySpecifier;

    @Autowired
    protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

    @Autowired
    protected LogonInfoManager logonInfoManager;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected OutboundDeliveryServiceUIModelExtension outboundDeliveryServiceUIModelExtension;

    @Autowired
    protected OutboundDeliveryCrossConvertRequest outboundDeliveryCrossConvertRequest;

    @Autowired
    protected OutboundDeliveryCrossConvertReservedRequest outboundDeliveryCrossConvertReservedRequest;

    public static final String PROPERTY_ACTIONCODE_FILE = "OutboundDelivery_actionCode";

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        return ServiceCollectionsHelper.asList(
                new DocActionConfigure(
                        OutboundDeliveryActionNode.DOC_ACTION_APPROVE, OutboundDelivery.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(OutboundDelivery.STATUS_INITIAL,
                                OutboundDelivery.STATUS_REJECTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        OutboundDeliveryActionNode.DOC_ACTION_REJECT_APPROVE, OutboundDelivery.STATUS_REJECTED,
                        ServiceCollectionsHelper.asList(OutboundDelivery.STATUS_INITIAL),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        OutboundDeliveryActionNode.DOC_ACTION_COUNTAPPROVE, OutboundDelivery.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(OutboundDelivery.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        OutboundDeliveryActionNode.DOC_ACTION_RECORD_DONE, OutboundDelivery.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(OutboundDelivery.STATUS_APPROVED),
                        ISystemActionCode.ACID_EDIT
                )
        );
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
        return this.getCustomCrossDocActionConfigureListTool(OutboundDelivery.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY, client);
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
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
        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier<OutboundDeliveryServiceModel,
            OutboundDelivery, OutboundItem> getDocumentContentSpecifier() {
        return outboundDeliverySpecifier;
    }

    @Override
    public DocSplitMergeRequest<OutboundDelivery, OutboundItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<OutboundDeliveryServiceModel, OutboundItem, ?> getCrossDocCovertRequest() {
        return outboundDeliveryCrossConvertRequest;
    }
    @Override
    public CrossDocConvertReservedRequest<OutboundDeliveryServiceModel, OutboundItem,
            ?> getCrossDocCovertReservedRequest() {
        return outboundDeliveryCrossConvertReservedRequest;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return outboundDeliveryManager;
    }

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = OutboundDeliveryUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE);
    }

    public void executeActionCore(
            OutboundDeliveryServiceModel outboundDeliveryServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(outboundDeliveryServiceModel, docActionCode, null,  null,  serialLogonInfo);
    }

    public void batchExecItemHomeAction(
            ServiceModule outboundDeliveryServiceModel, List<ServiceEntityNode> selectedSourceDocMatItemList,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException, ServiceEntityInstallationException, ServiceEntityConfigureException {
        super.defBatchExecItemHomeAction(outboundDeliveryServiceModel, selectedSourceDocMatItemList, docActionCode, null,
                (outboundItem, itemSelectionExecutionContext) -> checkUpdateOutboundItem(docActionCode, outboundItem, itemSelectionExecutionContext.getAllItemList(), serialLogonInfo), serialLogonInfo);
    }

    public boolean checkUpdateOutboundItem(int docActionCode, OutboundItem outboundItem, List<ServiceEntityNode> allItemList,
                                           SerialLogonInfo serialLogonInfo) throws DocActionException {
        try {
            if (docActionCode == OutboundDeliveryActionNode.DOC_ACTION_RECORD_DONE) {
                Map<String, List<String>> outboundItemUUIDMap = outboundDeliveryManager.groupOutboundByMaterialSKU(allItemList);
                WarehouseStoreItem warehouseStoreItem = outboundDeliveryManager.getRefWarehouseStoreItem(outboundItem);
                List<String> outboundItemUUIDList =
                        outboundItemUUIDMap.get(outboundItem.getRefMaterialSKUUUID());
                String outboundItemUUIDArray = ServiceEntityStringHelper.convListToString(outboundItemUUIDList);
                // Generate warehouse store item.
                WarehouseStoreItem warehouseStoreItemBack = (WarehouseStoreItem) warehouseStoreManager.getDBEntityNodeByUUID(warehouseStoreItem.getUuid(),
                        WarehouseStoreItem.NODENAME, warehouseStoreItem.getClient());
                StorageCoreUnit requestItemUpdateCoreUnit = outboundDeliveryWarehouseItemManager.checkWarehouseStoreItemAvailableWrapper(outboundItemUUIDArray,
                        warehouseStoreItem, outboundItem.getAmount(),
                        outboundItem.getRefUnitUUID());
                // Update the result amount to store item.
                warehouseStoreItem.setAmount(requestItemUpdateCoreUnit.getAmount());
                warehouseStoreItem.setRefUnitUUID(requestItemUpdateCoreUnit.getRefUnitUUID());
                // Generate the store item log.
                WarehouseStoreItemLog warehouseStoreItemLog = warehouseStoreManager.updateStoreItemLogFromDocItem(
                        outboundItem, warehouseStoreItem,
                        warehouseStoreItemBack);
                warehouseStoreManager.updateSENode(warehouseStoreItemLog, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
                warehouseStoreManager.updateSENode(warehouseStoreItem, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
                if (requestItemUpdateCoreUnit.getAmount() == 0) {
                    warehouseStoreItem.setItemStatus(WarehouseStoreItem.STATUS_ARCHIVE);
                    warehouseStoreManager.updateSENode(warehouseStoreItem, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
                    // Should update warehouse store item here.
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ServiceEntityConfigureException | MaterialException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
        return true;
    }

}
