package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.InventoryCheckOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.dto.InventoryCheckOrderUIModel;
import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocSplitMergeRequest;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.List;
import java.util.Map;

@Service
public class InventoryCheckOrderActionExecutionProxy extends
        DocActionExecutionProxy<InventoryCheckOrderServiceModel, InventoryCheckOrder, InventoryCheckItem> {

    @Autowired
    protected InventoryCheckOrderManager inventoryCheckOrderManager;

    @Autowired
    protected InventoryCheckOrderSpecifier inventoryCheckOrderSpecifier;

    @Autowired
    protected InventoryCheckOrderServiceUIModelExtension inventoryCheckOrderServiceUIModelExtension;

    public static final String PROPERTY_ACTIONCODE_FILE = "InventoryCheckOrder_actionCode";

    protected Logger logger = LoggerFactory.getLogger(InventoryCheckOrderActionExecutionProxy.class);

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(InventoryCheckOrderActionNode.DOC_ACTION_START_CHECK,
                        InventoryCheckOrder.STATUS_INPROCESS,
                        ServiceCollectionsHelper.asList(InventoryCheckOrder.STATUS_INITIAL,
                                InventoryCheckOrder.STATUS_REJECTED), ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(InventoryCheckOrderActionNode.DOC_ACTION_SUBMIT,
                        InventoryCheckOrder.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(InventoryCheckOrder.STATUS_INPROCESS),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(InventoryCheckOrderActionNode.DOC_ACTION_REVOKE_SUBMIT,
                        InventoryCheckOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(InventoryCheckOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(InventoryCheckOrderActionNode.DOC_ACTION_APPROVE,
                        InventoryCheckOrder.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(InventoryCheckOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(InventoryCheckOrderActionNode.DOC_ACTION_REJECT_APPROVE,
                        InventoryCheckOrder.STATUS_REJECTED,
                        ServiceCollectionsHelper.asList(InventoryCheckOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(InventoryCheckOrderActionNode.DOC_ACTION_COUNTAPPROVE,
                        InventoryCheckOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(InventoryCheckOrder.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(InventoryCheckOrderActionNode.DOC_ACTION_RECORD_DONE,
                        InventoryCheckOrder.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(InventoryCheckOrder.STATUS_INPROCESS),
                        ISystemActionCode.ACID_EDIT));
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
        return this.getCustomCrossDocActionConfigureListTool(InventoryCheckOrder.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER, client);
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier getDocumentContentSpecifier() {
        return inventoryCheckOrderSpecifier;
    }

    @Override
    public DocSplitMergeRequest<InventoryCheckOrder, InventoryCheckItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<InventoryCheckOrderServiceModel, InventoryCheckItem, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return inventoryCheckOrderManager;
    }

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = InventoryCheckOrderUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE);
    }

    public void executeActionCore(InventoryCheckOrderServiceModel inventoryCheckOrderServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(inventoryCheckOrderServiceModel, docActionCode,
                (inventoryCheckOrder, serialLogonInfo1) -> inventoryCheckOrder, (inventoryCheckItem, itemSelectionExecutionContext) -> {
                    if (docActionCode == InventoryCheckOrderActionNode.DOC_ACTION_RECORD_DONE) {
                        super.checkUpdateItemStatus(inventoryCheckItem, docActionCode, serialLogonInfo, false,
                                (checkItem, itemSelectionExecutionContext2) -> {
                                    try {
                                        inventoryCheckOrderManager.recordCheckItem(checkItem,
                                                serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
                                    } catch (ServiceEntityConfigureException | MaterialException e) {
                                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR,
                                                e.getErrorMessage());
                                    }
                                    return true;
                                });
                    }
                    return true;
                }, serialLogonInfo);
    }

}
