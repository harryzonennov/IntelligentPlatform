package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.PurchaseRequestServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseRequestActionExecutionProxy extends DocActionExecutionProxy<PurchaseRequestServiceModel,
        PurchaseRequest, PurchaseRequestMaterialItem> {

    @Autowired
    protected PurchaseRequestManager purchaseRequestManager;

    @Autowired
    protected PurchaseRequestSpecifier purchaseRequestSpecifier;

    @Autowired
    protected PurchaseRequestCrossConvertRequest purchaseRequestCrossConvertRequest;

    @Autowired
    protected PurchaseRequestServiceUIModelExtension purchaseRequestServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(PurchaseRequestActionExecutionProxy.class);

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(
                        PurchaseRequestActionNode.DOC_ACTION_SUBMIT, PurchaseRequest.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(PurchaseRequest.STATUS_INITIAL,
                                PurchaseRequest.STATUS_REJECT_APPROVAL),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        PurchaseRequestActionNode.DOC_ACTION_REVOKE_SUBMIT, PurchaseRequest.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(PurchaseRequest.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        PurchaseRequestActionNode.DOC_ACTION_APPROVE, PurchaseRequest.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(PurchaseRequest.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        PurchaseRequestActionNode.DOC_ACTION_INPROCESS, PurchaseRequest.STATUS_INPROCESS,
                        ServiceCollectionsHelper.asList(PurchaseRequest.STATUS_APPROVED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        PurchaseRequestActionNode.DOC_ACTION_REJECT_APPROVE, PurchaseRequest.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(PurchaseRequest.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        PurchaseRequestActionNode.DOC_ACTION_COUNTAPPROVE, PurchaseRequest.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(PurchaseRequest.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        PurchaseRequestActionNode.DOC_ACTION_PROCESS_DONE, PurchaseRequest.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(PurchaseRequest.STATUS_INPROCESS, PurchaseRequest.STATUS_INPROCESS),
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
        return this.getCustomCrossDocActionConfigureListTool(PurchaseRequest.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST, client);
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST,
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                        PurchaseRequestParty.ROLE_PARTYA,
                        PurchaseContractParty.ROLE_PARTYA,
                        StandardSwitchProxy.SWITCH_ON
                ),new CrossCopyPartyConversionConfig(
                        PurchaseRequestParty.ROLE_PARTYB,
                        PurchaseContractParty.ROLE_PARTYB
                )), StandardSwitchProxy.SWITCH_OFF, PurchaseRequestActionNode.DOC_ACTION_INPROCESS, StandardSwitchProxy.SWITCH_OFF));
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_INQUIRY,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST,
                        IDefDocumentResource.DOCUMENT_TYPE_INQUIRY, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                        PurchaseRequestParty.ROLE_PARTYA,
                        InquiryParty.ROLE_PARTYA,
                        StandardSwitchProxy.SWITCH_ON
                ),new CrossCopyPartyConversionConfig(
                        PurchaseRequestParty.ROLE_PARTYB,
                        InquiryParty.ROLE_PARTYB
                )), StandardSwitchProxy.SWITCH_OFF, PurchaseRequestActionNode.DOC_ACTION_INPROCESS, StandardSwitchProxy.SWITCH_OFF));
        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier<PurchaseRequestServiceModel, PurchaseRequest, PurchaseRequestMaterialItem> getDocumentContentSpecifier() {
        return purchaseRequestSpecifier;
    }

    @Override
    public DocSplitMergeRequest<PurchaseRequest, PurchaseRequestMaterialItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<PurchaseRequestServiceModel, PurchaseRequestMaterialItem, ?> getCrossDocCovertRequest() {
        return purchaseRequestCrossConvertRequest ;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return purchaseRequestManager;
    }

    public void executeActionCore(
            PurchaseRequestServiceModel purchaseRequestServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(purchaseRequestServiceModel, docActionCode, null, null, serialLogonInfo);
    }

}
