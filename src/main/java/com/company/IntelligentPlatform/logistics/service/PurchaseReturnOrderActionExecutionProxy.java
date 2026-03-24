package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.PurchaseReturnOrderServiceUIModelExtension;
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
public class PurchaseReturnOrderActionExecutionProxy extends DocActionExecutionProxy<PurchaseReturnOrderServiceModel,
        PurchaseReturnOrder, PurchaseReturnMaterialItem> {

    @Autowired
    protected PurchaseReturnOrderManager purchaseReturnOrderManager;

    @Autowired
    protected PurchaseReturnOrderSpecifier purchaseReturnOrderSpecifier;

    @Autowired
    protected PurchaseReturnOrderCrossConvertRequest purchaseReturnOrderCrossConvertRequest;

    @Autowired
    protected PurchaseReturnOrderCrossConvertProfRequest purchaseReturnOrderCrossConvertProfRequest;

    @Autowired
    protected PurchaseReturnOrderServiceUIModelExtension purchaseReturnOrderServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(PurchaseReturnOrderActionExecutionProxy.class);

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(
                        PurchaseReturnOrderActionNode.DOC_ACTION_SUBMIT, PurchaseReturnOrder.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(PurchaseReturnOrder.STATUS_INITIAL,
                                PurchaseReturnOrder.STATUS_REJECT_APPROVAL),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        PurchaseReturnOrderActionNode.DOC_ACTION_REVOKE_SUBMIT, PurchaseReturnOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(PurchaseReturnOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        PurchaseReturnOrderActionNode.DOC_ACTION_APPROVE, PurchaseReturnOrder.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(PurchaseReturnOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        PurchaseReturnOrderActionNode.DOC_ACTION_REJECT_APPROVE, PurchaseReturnOrder.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(PurchaseReturnOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        PurchaseReturnOrderActionNode.DOC_ACTION_COUNTAPPROVE, PurchaseReturnOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(PurchaseReturnOrder.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        PurchaseReturnOrderActionNode.DOC_ACTION_DELIVERY_DONE, PurchaseReturnOrder.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(PurchaseReturnOrder.STATUS_APPROVED),
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
    public CrossDocConvertProfRequest<PurchaseReturnOrderServiceModel, PurchaseReturnMaterialItem, ?> getCrossDocCovertProfRequest() {
        return purchaseReturnOrderCrossConvertProfRequest;
    }

    @Override
    public List<CrossDocActConfigure> getDefCrossDocActConfigureList() {
        return null;
    }

    @Override
    public List<CrossDocActConfigure> getCustomCrossDocActConfigureList(String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return this.getCustomCrossDocActionConfigureListTool(PurchaseReturnOrder.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER, client);
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER,
                        IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                        PurchaseReturnOrderParty.ROLE_PARTYB, OutboundDeliveryParty.PARTY_ROLE_SUPPLIER,
                        StandardSwitchProxy.SWITCH_ON
                ), new CrossCopyPartyConversionConfig(
                        PurchaseReturnOrderParty.ROLE_PARTYA,
                        OutboundDeliveryParty.PARTY_ROLE_PURORG,
                        StandardSwitchProxy.SWITCH_OFF
                )), StandardSwitchProxy.SWITCH_ON,
                        PurchaseReturnOrderActionNode.DOC_ACTION_DELIVERY_DONE, StandardSwitchProxy.SWITCH_ON));
        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }


    @Override
    public DocumentContentSpecifier<PurchaseReturnOrderServiceModel,
            PurchaseReturnOrder, PurchaseReturnMaterialItem> getDocumentContentSpecifier() {
        return purchaseReturnOrderSpecifier;
    }

    @Override
    public DocSplitMergeRequest<PurchaseReturnOrder, PurchaseReturnMaterialItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<PurchaseReturnOrderServiceModel, PurchaseReturnMaterialItem, ?> getCrossDocCovertRequest() {
        return purchaseReturnOrderCrossConvertRequest;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return purchaseReturnOrderManager;
    }


    public void executeActionCore(
            PurchaseReturnOrderServiceModel purchaseReturnOrderServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(purchaseReturnOrderServiceModel, docActionCode, null, null, serialLogonInfo);

    }

}
