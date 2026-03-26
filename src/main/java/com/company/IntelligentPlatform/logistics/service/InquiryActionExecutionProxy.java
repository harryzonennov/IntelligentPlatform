package com.company.IntelligentPlatform.logistics.service;

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
public class InquiryActionExecutionProxy extends DocActionExecutionProxy<InquiryServiceModel, Inquiry, InquiryMaterialItem> {

    @Autowired
    protected InquiryManager inquiryManager;

    @Autowired
    protected InquirySpecifier inquirySpecifier;

    @Autowired
    protected InquiryCrossConvertRequest inquiryCrossConvertRequest;

    protected Logger logger = LoggerFactory.getLogger(InquiryActionExecutionProxy.class);

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(
                        InquiryActionNode.DOC_ACTION_SUBMIT, Inquiry.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(Inquiry.STATUS_INITIAL,
                                Inquiry.STATUS_REJECT_APPROVAL),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        InquiryActionNode.DOC_ACTION_REVOKE_SUBMIT, Inquiry.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(Inquiry.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        InquiryActionNode.DOC_ACTION_APPROVE, Inquiry.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(Inquiry.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        InquiryActionNode.DOC_ACTION_REJECT_APPROVE, Inquiry.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(Inquiry.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        InquiryActionNode.DOC_ACTION_COUNTAPPROVE, Inquiry.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(Inquiry.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        InquiryActionNode.DOC_ACTION_PROCESS_DONE, Inquiry.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(Inquiry.STATUS_APPROVED),
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
        return this.getCustomCrossDocActionConfigureListTool(Inquiry.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_INQUIRY, client);
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_INQUIRY,
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                        InquiryParty.ROLE_PARTYA,
                        PurchaseContractParty.ROLE_PARTYA
                ),new CrossCopyPartyConversionConfig(
                        InquiryParty.ROLE_PARTYB,
                        PurchaseContractParty.ROLE_PARTYB
                )), StandardSwitchProxy.SWITCH_ON, InquiryActionNode.DOC_ACTION_PROCESS_DONE, StandardSwitchProxy.SWITCH_OFF));
        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier<InquiryServiceModel, Inquiry, InquiryMaterialItem> getDocumentContentSpecifier() {
        return inquirySpecifier;
    }

    @Override
    public DocSplitMergeRequest<Inquiry, InquiryMaterialItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<InquiryServiceModel, InquiryMaterialItem, ?> getCrossDocCovertRequest() {
        return inquiryCrossConvertRequest;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return inquiryManager;
    }

    public void executeActionCore(
            InquiryServiceModel inquiryServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(inquiryServiceModel, docActionCode, null,null, serialLogonInfo);
    }

}
