package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.finance.service.FinanceAccountValueProxyException;
import com.company.IntelligentPlatform.finance.service.SystemResourceException;
import com.company.IntelligentPlatform.logistics.dto.InboundDeliveryServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.dto.InboundDeliveryUIModel;
import com.company.IntelligentPlatform.logistics.service.QualityInspectCrossConvertRequest;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreParty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InboundDeliveryActionExecutionProxy
        extends DocActionExecutionProxy<InboundDeliveryServiceModel, InboundDelivery, InboundItem> {

    @Autowired
    protected InboundDeliveryManager inboundDeliveryManager;

    @Autowired
    protected InboundDeliverySpecifier inboundDeliverySpecifier;

    @Autowired
    protected InboundDeliveryServiceUIModelExtension inboundDeliveryServiceUIModelExtension;

    @Autowired
    protected InboundDeliveryCrossConvertRequest inboundDeliveryCrossConvertRequest;

    @Autowired
    protected QualityInspectCrossConvertRequest qualityInspectCrossConvertRequest;

    public static final String PROPERTY_ACTIONCODE_FILE = "InboundDelivery_actionCode";

    protected Logger logger = LoggerFactory.getLogger(InboundDeliveryActionExecutionProxy.class);
    @Autowired
    private LogonInfoManager logonInfoManager;

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        return ServiceCollectionsHelper.asList(
                new DocActionConfigure(InboundDeliveryActionNode.DOC_ACTION_APPROVE, InboundDelivery.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(InboundDelivery.STATUS_INITIAL,
                                InboundDelivery.STATUS_REJECTED), ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(InboundDeliveryActionNode.DOC_ACTION_REJECT_APPROVE,
                        InboundDelivery.STATUS_REJECTED,
                        ServiceCollectionsHelper.asList(InboundDelivery.STATUS_INITIAL),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(InboundDeliveryActionNode.DOC_ACTION_COUNTAPPROVE,
                        InboundDelivery.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(InboundDelivery.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(InboundDeliveryActionNode.DOC_ACTION_RECORD_DONE,
                        InboundDelivery.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(InboundDelivery.STATUS_APPROVED), ISystemActionCode.ACID_EDIT));
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
        return this.getCustomCrossDocActionConfigureListTool(InboundDelivery.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY, client);
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY,
                        IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM, ServiceCollectionsHelper.asList(
                        new CrossCopyPartyConversionConfig(InboundDeliveryParty.PARTY_ROLE_PURORG,
                                WarehouseStoreParty.PARTY_ROLE_PURORG, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InboundDeliveryParty.PARTY_ROLE_SUPPLIER,
                                WarehouseStoreParty.PARTY_ROLE_SUPPLIER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InboundDeliveryParty.PARTY_ROLE_SUPPLIER,
                                WarehouseStoreParty.PARTY_ROLE_SUPPLIER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InboundDeliveryParty.PARTY_ROLE_CUSTOMER,
                                WarehouseStoreParty.PARTY_ROLE_CUSTOMER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InboundDeliveryParty.PARTY_ROLE_PRODORG,
                                WarehouseStoreParty.PARTY_ROLE_PRODORG, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(InboundDeliveryParty.PARTY_ROLE_SALESORG,
                                WarehouseStoreParty.PARTY_ROLE_SALESORG, StandardSwitchProxy.SWITCH_OFF)),
                        StandardSwitchProxy.SWITCH_ON, InboundDeliveryActionNode.DOC_ACTION_RECORD_DONE, StandardSwitchProxy.SWITCH_ON));
        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier<InboundDeliveryServiceModel, InboundDelivery, InboundItem> getDocumentContentSpecifier() {
        return inboundDeliverySpecifier;
    }

    @Override
    public DocSplitMergeRequest<InboundDelivery, InboundItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<InboundDeliveryServiceModel, InboundItem, ?> getCrossDocCovertRequest() {
        return inboundDeliveryCrossConvertRequest;
    }

    @Override
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> handleInboundCrossCreateDoc(ServiceModule sourceServiceModule, int sourceDocType,
                                            List<ServiceEntityNode> selectedSourceDocMatItemList,
                                            DocumentMatItemBatchGenRequest genRequest,
                                                     CrossDocConvertRequest.InputOption inputOption, LogonInfo logonInfo)
            throws DocActionException, ServiceModuleProxyException, SearchConfigureException,
            ServiceEntityConfigureException {
        if (sourceDocType == IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER) {
            // In case from quality inspect order, conversion into inbound directly
            return crossDocBatchConvertProxy.createTargetDocumentBatch(sourceServiceModule, sourceDocType,
                    selectedSourceDocMatItemList, inboundDeliveryCrossConvertRequest, genRequest, inputOption,
                    logonInfo);
        } else {
            // In case from standard business doc, dispatch & conversion
            return dispatchForQualityCheck(sourceServiceModule, sourceDocType, selectedSourceDocMatItemList, genRequest,
                    inputOption, logonInfo);
        }
    }

    private List<CrossDocBatchConvertProxy.DocContentCreateContext> dispatchForQualityCheck(ServiceModule sourceServiceModule, int sourceDocType,
                                         List<ServiceEntityNode> selectedSourceDocMatItemList,
                                         DocumentMatItemBatchGenRequest genRequest,
                                                  CrossDocConvertRequest.InputOption inputOption, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, DocActionException, ServiceModuleProxyException,
            SearchConfigureException {
        List<ServiceEntityNode> rawMaterialSKUList =
                docFlowProxy.getRefMaterialSKUList(selectedSourceDocMatItemList, null, logonInfo.getClient());
        List<ServiceEntityNode> sourceItemForInboundList =
                docFlowProxy.splitDocItemListByMaterialQualityFlag(selectedSourceDocMatItemList, rawMaterialSKUList,
                        StandardSwitchProxy.SWITCH_OFF);
        List<ServiceEntityNode> sourceItemForQualityList =
                docFlowProxy.splitDocItemListByMaterialQualityFlag(selectedSourceDocMatItemList, rawMaterialSKUList,
                        StandardSwitchProxy.SWITCH_ON);
        /*
         * trying to get the Existed & Proper in-bound delivery item
         * lists for this purchase contract.
         */
        List<CrossDocBatchConvertProxy.DocContentCreateContext> docContentCreateContextList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(sourceItemForInboundList)) {
            docContentCreateContextList = crossDocBatchConvertProxy.createTargetDocumentBatch(sourceServiceModule, sourceDocType,
                    sourceItemForInboundList, inboundDeliveryCrossConvertRequest, genRequest,inputOption,
                    logonInfo);
        }
        if (!ServiceCollectionsHelper.checkNullList(sourceItemForQualityList)) {
            genRequest.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER);
            List<CrossDocBatchConvertProxy.DocContentCreateContext> docContentContextQualityList =  crossDocBatchConvertProxy.createTargetDocumentBatch(sourceServiceModule, sourceDocType,
                    sourceItemForQualityList, qualityInspectCrossConvertRequest, genRequest, inputOption,
                    logonInfo);
            if (!ServiceCollectionsHelper.checkNullList(docContentContextQualityList)) {
                docContentCreateContextList.addAll(docContentContextQualityList);
            }
        }
        return docContentCreateContextList;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return inboundDeliveryManager;
    }

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = InboundDeliveryUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE);
    }

    public void executeActionCore(InboundDeliveryServiceModel inboundDeliveryServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(inboundDeliveryServiceModel, docActionCode, (inboundDelivery, serialLogonInfo1) -> {
            LogonInfo logonInfo = logonInfoManager.genLogonInfo(serialLogonInfo, false);
            if (docActionCode == InboundDeliveryActionNode.DOC_ACTION_RECORD_DONE) {
                try {
                    inboundDeliveryManager.recordStore(inboundDeliveryServiceModel,
                            ServiceCollectionsHelper.parseToSENodeList(inboundDeliveryServiceModel.getInboundItemList(),
                                    InboundItemServiceModel::getInboundItem), logonInfo);
                } catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (InboundDeliveryException | SystemResourceException | FinanceAccountValueProxyException | SearchConfigureException | ServiceModuleProxyException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            return inboundDelivery;
        }, null, serialLogonInfo);
    }

}
