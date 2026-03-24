package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.PurchaseContractUIModel;
import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.model.RegisteredProductInvolveParty;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocActionExecutorCase;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
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
import com.company.IntelligentPlatform.common.model.ServiceCrossDocEventMonitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseContractActionExecutionProxy extends DocActionExecutionProxy<PurchaseContractServiceModel,
        PurchaseContract, PurchaseContractMaterialItem> {

    @Autowired
    protected PurchaseContractManager purchaseContractManager;

    @Autowired
    protected PurchaseContractSpecifier purchaseContractSpecifier;

    @Autowired
    protected PurchaseContractCrossConvertRequest purchaseContractCrossConvertRequest;

    @Autowired
    protected PurchaseContractMergeRequest purchaseContractMergeRequest;

    @Autowired
    protected PurchaseContractCrossConvertProfRequest purchaseContractCrossConvertProfRequest;

    protected Logger logger = LoggerFactory.getLogger(PurchaseContractActionExecutionProxy.class);

    public static final String PROPERTY_ACTIONCODE_FILE = "PurchaseContract_actionCode";

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = PurchaseContractUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE);
    }

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(
                        PurchaseContractActionNode.DOC_ACTION_SUBMIT, PurchaseContract.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(PurchaseContract.STATUS_INITIAL,
                                PurchaseContract.STATUS_REJECT_APPROVAL),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        PurchaseContractActionNode.DOC_ACTION_REVOKE_SUBMIT, PurchaseContract.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(PurchaseContract.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        PurchaseContractActionNode.DOC_ACTION_APPROVE, PurchaseContract.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(PurchaseContract.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        PurchaseContractActionNode.DOC_ACTION_REJECT_APPROVE, PurchaseContract.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(PurchaseContract.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        PurchaseContractActionNode.DOC_ACTION_COUNTAPPROVE, PurchaseContract.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(PurchaseContract.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        PurchaseContractActionNode.DOC_ACTION_DELIVERY_DONE, PurchaseContract.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(PurchaseContract.STATUS_APPROVED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        PurchaseContractActionNode.DOC_ACTION_PROCESS_DONE, PurchaseContract.STATUS_PROCESSDONE,
                        ServiceCollectionsHelper.asList(PurchaseContract.STATUS_APPROVED, PurchaseContract.STATUS_DELIVERYDONE),
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
    public CrossDocConvertProfRequest<PurchaseContractServiceModel, PurchaseContractMaterialItem, ?> getCrossDocCovertProfRequest() {
        return purchaseContractCrossConvertProfRequest;
    }

    @Override
    public List<CrossDocActConfigure> getDefCrossDocActConfigureList() {
        List<CrossDocActConfigure> crossDocActConfigureList = ServiceCollectionsHelper.asList(
                new CrossDocActConfigure(
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                        IDefDocumentResource.DOCUMENT_TYPE_INQUIRY, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV,
                        PurchaseContractActionNode.DOC_ACTION_DELIVERY_DONE,
                        InquiryActionNode.DOC_ACTION_PROCESS_DONE,
                        ServiceCrossDocEventMonitor.TRIG_DOCACT_SCEN_SELECTITEM,
                        ServiceCrossDocEventMonitor.TRIG_PARENTMODE_ALL
               ),new CrossDocActConfigure(
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                        IDefDocumentResource.DOCUMENT_TYPE_INQUIRY, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV,
                        PurchaseContractActionNode.DOC_ACTION_PROCESS_DONE,
                        InquiryActionNode.DOC_ACTION_PROCESS_DONE,
                        ServiceCrossDocEventMonitor.TRIG_DOCACT_SCEN_SELECTITEM,
                        ServiceCrossDocEventMonitor.TRIG_PARENTMODE_ALL
                ),new CrossDocActConfigure(
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV,
                        PurchaseContractActionNode.DOC_ACTION_PROCESS_DONE,
                        PurchaseRequestActionNode.DOC_ACTION_PROCESS_DONE,
                        ServiceCrossDocEventMonitor.TRIG_DOCACT_SCEN_SELECTITEM,
                        ServiceCrossDocEventMonitor.TRIG_PARENTMODE_ALL
                ),new CrossDocActConfigure(
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV,
                        PurchaseContractActionNode.DOC_ACTION_DELIVERY_DONE,
                        PurchaseRequestActionNode.DOC_ACTION_PROCESS_DONE,
                        ServiceCrossDocEventMonitor.TRIG_DOCACT_SCEN_SELECTITEM,
                        ServiceCrossDocEventMonitor.TRIG_PARENTMODE_ALL
                ),new CrossDocActConfigure(
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV,
                        PurchaseContractActionNode.DOC_ACTION_APPROVE,
                        PurchaseRequestActionNode.DOC_ACTION_INPROCESS,
                        ServiceCrossDocEventMonitor.TRIG_DOCACT_SCEN_SELECTITEM,
                        ServiceCrossDocEventMonitor.TRIG_PARENTMODE_ALL
                )
        );
        return crossDocActConfigureList;
    }

    @Override
    public List<CrossDocActConfigure> getCustomCrossDocActConfigureList(String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return this.getCustomCrossDocActionConfigureListTool(PurchaseContract.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT, client);
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                        IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                                PurchaseContractParty.ROLE_PARTYA,
                                InboundDeliveryParty.PARTY_ROLE_PURORG,
                                StandardSwitchProxy.SWITCH_ON
                        ),new CrossCopyPartyConversionConfig(
                                PurchaseContractParty.ROLE_PARTYB,
                                InboundDeliveryParty.PARTY_ROLE_SUPPLIER,
                                StandardSwitchProxy.SWITCH_OFF
                        )), StandardSwitchProxy.SWITCH_OFF,
                        PurchaseContractActionNode.DOC_ACTION_DELIVERY_DONE, StandardSwitchProxy.SWITCH_ON));
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                        IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                        PurchaseContractParty.ROLE_PARTYA,
                        QualityInspectOrderParty.PARTY_ROLE_PURORG,
                        StandardSwitchProxy.SWITCH_ON
                ),new CrossCopyPartyConversionConfig(
                        PurchaseContractParty.ROLE_PARTYB,
                        QualityInspectOrderParty.PARTY_ROLE_SUPPLIER,
                        StandardSwitchProxy.SWITCH_OFF
                )), StandardSwitchProxy.SWITCH_ON,
                        PurchaseContractActionNode.DOC_ACTION_DELIVERY_DONE, StandardSwitchProxy.SWITCH_ON));
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                        PurchaseContractParty.ROLE_PARTYA,
                        PurchaseReturnOrderParty.ROLE_PARTYA,
                        StandardSwitchProxy.SWITCH_ON
                ),new CrossCopyPartyConversionConfig(
                        PurchaseContractParty.ROLE_PARTYB,
                        PurchaseReturnOrderParty.ROLE_PARTYB,
                        StandardSwitchProxy.SWITCH_OFF
                )), StandardSwitchProxy.SWITCH_ON,
                        PurchaseContractActionNode.DOC_ACTION_PROCESS_DONE, StandardSwitchProxy.SWITCH_ON));
        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier<PurchaseContractServiceModel, PurchaseContract, PurchaseContractMaterialItem> getDocumentContentSpecifier() {
        return purchaseContractSpecifier;
    }

    @Override
    public DocSplitMergeRequest<PurchaseContract, PurchaseContractMaterialItem> getDocMergeRequest() {
        return purchaseContractMergeRequest;
    }

    @Override
    public CrossDocConvertRequest<PurchaseContractServiceModel, PurchaseContractMaterialItem, ?> getCrossDocCovertRequest() {
        return purchaseContractCrossConvertRequest;
    }

    @Override
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> crossCreateDocumentBatch(ServiceModule sourceServiceModule,
                                         List<ServiceEntityNode> selectedSourceDocMatItemList,
                                         DocumentMatItemBatchGenRequest genRequest, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException,
            SearchConfigureException, ServiceEntityInstallationException {
        PurchaseContractServiceModel purchaseContractServiceModel = (PurchaseContractServiceModel) sourceServiceModule;
        if(purchaseContractServiceModel == null){
            purchaseContractServiceModel =
                    (PurchaseContractServiceModel) this.processEmptyServiceModel(selectedSourceDocMatItemList);
        }
        try {
            // Preparing the involvePartyMap for create new registered product instances
            Map<Integer, Account> involvePartyMap = new HashMap<>();
            CorporateCustomer supplier = purchaseContractManager.getSupplier(purchaseContractServiceModel.getPurchaseContract().getUuid(), logonInfo.getClient());
            involvePartyMap.put(RegisteredProductInvolveParty.PARTY_ROLE_SUPPLIER, supplier);
            List<ServiceEntityNode> renewPurchaseContractMatItemList =
                    this.getDocumentContentSpecifier().checkAndSplitDocMatItemListForRegProduct(
                            selectedSourceDocMatItemList, involvePartyMap,
                            LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
            return super.crossCreateDocumentBatch(sourceServiceModule, renewPurchaseContractMatItemList, genRequest,
                    logonInfo);
        } catch (MaterialException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage() );
        }
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return purchaseContractManager;
    }


    public void executeActionCore(
            PurchaseContractServiceModel purchaseContractServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(purchaseContractServiceModel, docActionCode, (purchaseContract, serialLogonInfo1) -> {
    if(docActionCode == PurchaseContractActionNode.DOC_ACTION_DELIVERY_DONE){
        try {
            setCompleteService(
                    purchaseContractServiceModel,
                    serialLogonInfo);
        } catch (ServiceEntityConfigureException  e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        } catch (PurchaseContractException  e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        } catch (ServiceModuleProxyException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }
   return purchaseContract; }, null, serialLogonInfo);

    }

    /**
     * TODO: check this logic, it might be unnessary
     * Core Logic for handling standard doc action "Delivery Complete".
     *
     * @param purchaseContractServiceModel
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    private void setCompleteService(PurchaseContractServiceModel purchaseContractServiceModel,
                                   SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, PurchaseContractException, ServiceEntityConfigureException,
            DocActionException {
        /*
         * [Step1] Try to batch set purchase contract
         */
        try {
            batchExecItemTryExecuteParent(
                    new DocActionExecutorCase.DocItemBatchExecutionRequest<>(purchaseContractServiceModel, null,
                            PurchaseContractActionNode.DOC_ACTION_DELIVERY_DONE,
                            null, null),
                    serialLogonInfo);
        } catch (ServiceEntityInstallationException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }

        /*
         * [Step2] Try to inform the relative cross doc
         */
        List<ServiceEntityNode> docMatItemList =
                this.getDocumentContentSpecifier().getDocMatItemList(purchaseContractServiceModel);
        ServiceCrossDocActionEvent serviceCrossDocActionEvent =
                new ServiceCrossDocActionEvent(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT, docMatItemList,  PurchaseContractActionNode.DOC_ACTION_DELIVERY_DONE);
    }

}
