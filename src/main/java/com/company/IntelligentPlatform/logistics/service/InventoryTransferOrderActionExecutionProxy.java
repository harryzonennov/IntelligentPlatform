package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.InventoryTransferOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.dto.InventoryTransferOrderUIModel;
import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryTransferOrderActionExecutionProxy extends DocActionExecutionProxy
        <InventoryTransferOrderServiceModel, InventoryTransferOrder, InventoryTransferItem> {

    @Autowired
    protected InventoryTransferOrderManager inventoryTransferOrderManager;

    @Autowired
    protected InventoryTransferOrderSpecifier inventoryTransferOrderSpecifier;

    @Autowired
    protected InventoryTransferOrderCrossConvertRequest inventoryTransferOrderCrossConvertRequest;

    @Autowired
    protected InventoryTransferOrderServiceUIModelExtension inventoryTransferOrderServiceUIModelExtension;

    public static final String PROPERTY_ACTIONCODE_FILE = "InventoryTransferOrder_actionCode";

    protected Logger logger = LoggerFactory.getLogger(InventoryTransferOrderActionExecutionProxy.class);
    @Autowired
    private LogonInfoManager logonInfoManager;

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(
                        InventoryTransferOrderActionNode.DOC_ACTION_SUBMIT, InventoryTransferOrder.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(InventoryTransferOrder.STATUS_INITIAL,
                                InventoryTransferOrder.STATUS_REJECTED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        InventoryTransferOrderActionNode.DOC_ACTION_REVOKE_SUBMIT, InventoryTransferOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(InventoryTransferOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        InventoryTransferOrderActionNode.DOC_ACTION_APPROVE, InventoryTransferOrder.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(InventoryTransferOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        InventoryTransferOrderActionNode.DOC_ACTION_REJECT_APPROVE, InventoryTransferOrder.STATUS_REJECTED,
                        ServiceCollectionsHelper.asList(InventoryTransferOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        InventoryTransferOrderActionNode.DOC_ACTION_COUNTAPPROVE, InventoryTransferOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(InventoryTransferOrder.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        InventoryTransferOrderActionNode.DOC_ACTION_TRANSFER_DONE, InventoryTransferOrder.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(InventoryTransferOrder.STATUS_APPROVED),
                        ISystemActionCode.ACID_EDIT
                )
        );
        return defDocActionConfigureList;
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
        return this.getCustomCrossDocActionConfigureListTool(InventoryTransferOrder.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER, client);
    }

    @Override
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> crossCreateDocumentCore(ServiceModule sourceServiceModule,
                                                                                           List<ServiceEntityNode> selectedSourceDocMatItemList,
                                                                                           DocumentMatItemBatchGenRequest genRequest,
                                                                                           CrossDocConvertRequest.InputOption inputOption, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException,
            SearchConfigureException, ServiceEntityInstallationException {
        int targetDocType = genRequest.getTargetDocType();
        if (targetDocType == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
            // Special logic for outbound to create inbound delivery
            return inventoryTransferOrderManager.generateDelivery(sourceServiceModule, selectedSourceDocMatItemList, genRequest, logonInfo);
        } else {
            // In case outbound for other document
            return super.crossCreateDocumentCore(sourceServiceModule, selectedSourceDocMatItemList, genRequest, inputOption,
                    logonInfo);
        }
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER,
                        IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY, ServiceCollectionsHelper.asList(
                        new CrossCopyPartyConversionConfig(InventoryTransferOrderParty.PARTY_ROLE_PURORG,
                                InboundDeliveryParty.PARTY_ROLE_PURORG, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InventoryTransferOrderParty.PARTY_ROLE_SUPPLIER,
                                InboundDeliveryParty.PARTY_ROLE_SUPPLIER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InventoryTransferOrderParty.PARTY_ROLE_SUPPLIER,
                                InboundDeliveryParty.PARTY_ROLE_SUPPLIER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InventoryTransferOrderParty.PARTY_ROLE_CUSTOMER,
                                InboundDeliveryParty.PARTY_ROLE_CUSTOMER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InventoryTransferOrderParty.PARTY_ROLE_PRODORG,
                                InboundDeliveryParty.PARTY_ROLE_PRODORG, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InventoryTransferOrderParty.PARTY_ROLE_SALESORG,
                                InboundDeliveryParty.PARTY_ROLE_SALESORG, StandardSwitchProxy.SWITCH_OFF)),
                        StandardSwitchProxy.SWITCH_ON, InventoryTransferOrderActionNode.DOC_ACTION_TRANSFER_DONE, StandardSwitchProxy.SWITCH_OFF));
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER,
                        IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER, ServiceCollectionsHelper.asList(
                        new CrossCopyPartyConversionConfig(InventoryTransferOrderParty.PARTY_ROLE_PURORG,
                                QualityInspectOrderParty.PARTY_ROLE_PURORG, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InventoryTransferOrderParty.PARTY_ROLE_SUPPLIER,
                                QualityInspectOrderParty.PARTY_ROLE_SUPPLIER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InventoryTransferOrderParty.PARTY_ROLE_SUPPLIER,
                                QualityInspectOrderParty.PARTY_ROLE_SUPPLIER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InventoryTransferOrderParty.PARTY_ROLE_CUSTOMER,
                                QualityInspectOrderParty.PARTY_ROLE_CUSTOMER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InventoryTransferOrderParty.PARTY_ROLE_PRODORG,
                                QualityInspectOrderParty.PARTY_ROLE_PRODORG, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InventoryTransferOrderParty.PARTY_ROLE_SALESORG,
                                QualityInspectOrderParty.PARTY_ROLE_SALESORG, StandardSwitchProxy.SWITCH_OFF)),
                        StandardSwitchProxy.SWITCH_ON, InventoryTransferOrderActionNode.DOC_ACTION_TRANSFER_DONE, StandardSwitchProxy.SWITCH_OFF));
        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier getDocumentContentSpecifier() {
        return inventoryTransferOrderSpecifier;
    }

    @Override
    public DocSplitMergeRequest<InventoryTransferOrder, InventoryTransferItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<InventoryTransferOrderServiceModel, InventoryTransferItem, ?> getCrossDocCovertRequest() {
        return inventoryTransferOrderCrossConvertRequest;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return inventoryTransferOrderManager;
    }

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = InventoryTransferOrderUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE);
    }

    public void executeActionCore(
            InventoryTransferOrderServiceModel inventoryTransferOrderServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(inventoryTransferOrderServiceModel, docActionCode, null, null, serialLogonInfo);
    }

}
