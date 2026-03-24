package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.logistics.model.InboundDeliveryParty;
import com.company.IntelligentPlatform.logistics.model.QualityInspectOrderParty;
import com.company.IntelligentPlatform.sales.dto.SalesReturnOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.sales.model.SalesReturnMaterialItem;
import com.company.IntelligentPlatform.sales.model.SalesReturnOrder;
import com.company.IntelligentPlatform.sales.model.SalesReturnOrderActionNode;
import com.company.IntelligentPlatform.sales.model.SalesReturnOrderParty;
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
public class SalesReturnOrderActionExecutionProxy extends DocActionExecutionProxy<SalesReturnOrderServiceModel,
        SalesReturnOrder, SalesReturnMaterialItem> {

    @Autowired
    protected SalesReturnOrderManager salesReturnOrderManager;

    @Autowired
    protected SalesReturnOrderSpecifier salesReturnOrderSpecifier;

    @Autowired
    protected SalesReturnOrderCrossConvertRequest salesReturnOrderCrossConvertRequest;

    @Autowired
    protected SalesReturnOrderCrossConvertProfRequest salesReturnOrderCrossConvertProfRequest;

    @Autowired
    protected SalesReturnOrderServiceUIModelExtension salesReturnOrderServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(SalesReturnOrderActionExecutionProxy.class);

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(
                        SalesReturnOrderActionNode.DOC_ACTION_SUBMIT, SalesReturnOrder.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(SalesReturnOrder.STATUS_INITIAL,
                                SalesReturnOrder.STATUS_REJECT_APPROVAL),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        SalesReturnOrderActionNode.DOC_ACTION_REVOKE_SUBMIT, SalesReturnOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(SalesReturnOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        SalesReturnOrderActionNode.DOC_ACTION_APPROVE, SalesReturnOrder.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(SalesReturnOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        SalesReturnOrderActionNode.DOC_ACTION_REJECT_APPROVE, SalesReturnOrder.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(SalesReturnOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        SalesReturnOrderActionNode.DOC_ACTION_COUNTAPPROVE, SalesReturnOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(SalesReturnOrder.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        SalesReturnOrderActionNode.DOC_ACTION_DELIVERY_DONE, SalesReturnOrder.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(SalesReturnOrder.STATUS_APPROVED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        SalesReturnOrderActionNode.DOC_ACTION_PROCESS_DONE, SalesReturnOrder.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(SalesReturnOrder.STATUS_APPROVED),
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
        return this.getCustomCrossDocActionConfigureListTool(SalesReturnOrder.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER, client);
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER,
                        IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                        SalesReturnOrderParty.ROLE_SOLD_TO_PARTY,
                        InboundDeliveryParty.PARTY_ROLE_CUSTOMER,
                        StandardSwitchProxy.SWITCH_ON
                ),new CrossCopyPartyConversionConfig(
                        SalesReturnOrderParty.ROLE_SOLD_FROM_PARTY,
                        InboundDeliveryParty.PARTY_ROLE_SALESORG,
                        StandardSwitchProxy.SWITCH_OFF
                )), StandardSwitchProxy.SWITCH_OFF,
                        SalesReturnOrderActionNode.DOC_ACTION_DELIVERY_DONE, StandardSwitchProxy.SWITCH_ON));
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER,
                        IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                        SalesReturnOrderParty.ROLE_SOLD_TO_PARTY,
                        QualityInspectOrderParty.PARTY_ROLE_CUSTOMER,
                        StandardSwitchProxy.SWITCH_ON
                ),new CrossCopyPartyConversionConfig(
                        SalesReturnOrderParty.ROLE_SOLD_FROM_PARTY,
                        QualityInspectOrderParty.PARTY_ROLE_SALESORG,
                        StandardSwitchProxy.SWITCH_OFF
                )), StandardSwitchProxy.SWITCH_ON,
                        SalesReturnOrderActionNode.DOC_ACTION_DELIVERY_DONE, StandardSwitchProxy.SWITCH_ON));
        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier<SalesReturnOrderServiceModel,
            SalesReturnOrder, SalesReturnMaterialItem> getDocumentContentSpecifier() {
        return salesReturnOrderSpecifier;
    }

    @Override
    public DocSplitMergeRequest<SalesReturnOrder, SalesReturnMaterialItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<SalesReturnOrderServiceModel, SalesReturnMaterialItem, ?> getCrossDocCovertRequest() {
        return salesReturnOrderCrossConvertRequest;
    }

    @Override
    public CrossDocConvertProfRequest<SalesReturnOrderServiceModel, SalesReturnMaterialItem, ?> getCrossDocCovertProfRequest() {
        return salesReturnOrderCrossConvertProfRequest;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return salesReturnOrderManager;
    }

    public void executeActionCore(
            SalesReturnOrderServiceModel salesReturnOrderServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(salesReturnOrderServiceModel, docActionCode, null, null, serialLogonInfo);
    }

}