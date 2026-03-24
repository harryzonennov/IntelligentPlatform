package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.WasteProcessOrderServiceUIModelExtension;
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
public class WasteProcessOrderActionExecutionProxy extends DocActionExecutionProxy<WasteProcessOrderServiceModel,
        WasteProcessOrder, WasteProcessMaterialItem> {

    @Autowired
    protected WasteProcessOrderManager wasteProcessOrderManager;

    @Autowired
    protected WasteProcessOrderSpecifier wasteProcessOrderSpecifier;

    @Autowired
    protected WasteProcessOrderCrossConvertRequest wasteProcessOrderCrossConvertRequest;

    @Autowired
    protected WasteProcessOrderServiceUIModelExtension wasteProcessOrderServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(WasteProcessOrderActionExecutionProxy.class);

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(
                        WasteProcessOrderActionNode.DOC_ACTION_SUBMIT, WasteProcessOrder.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(WasteProcessOrder.STATUS_INITIAL,
                                WasteProcessOrder.STATUS_REJECT_APPROVAL),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        WasteProcessOrderActionNode.DOC_ACTION_REVOKE_SUBMIT, WasteProcessOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(WasteProcessOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        WasteProcessOrderActionNode.DOC_ACTION_APPROVE, WasteProcessOrder.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(WasteProcessOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        WasteProcessOrderActionNode.DOC_ACTION_REJECT_APPROVE, WasteProcessOrder.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(WasteProcessOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        WasteProcessOrderActionNode.DOC_ACTION_COUNTAPPROVE, WasteProcessOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(WasteProcessOrder.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        WasteProcessOrderActionNode.DOC_ACTION_DELIVERY_DONE, WasteProcessOrder.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(WasteProcessOrder.STATUS_APPROVED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        WasteProcessOrderActionNode.DOC_ACTION_PROCESS_DONE, WasteProcessOrder.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(WasteProcessOrder.STATUS_APPROVED),
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
        return this.getCustomCrossDocActionConfigureListTool(WasteProcessOrder.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER, client);
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER,
                        IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                        WasteProcessOrderParty.ROLE_SOLD_TO_PARTY, OutboundDeliveryParty.PARTY_ROLE_CUSTOMER,
                        StandardSwitchProxy.SWITCH_ON
                ),new CrossCopyPartyConversionConfig(
                        WasteProcessOrderParty.ROLE_SOLD_FROM_PARTY,
                        OutboundDeliveryParty.PARTY_ROLE_SALESORG,
                        StandardSwitchProxy.SWITCH_OFF
                )), StandardSwitchProxy.SWITCH_ON,
                        WasteProcessOrderActionNode.DOC_ACTION_DELIVERY_DONE, StandardSwitchProxy.SWITCH_ON,
                        StandardSwitchProxy.SWITCH_ON, WasteProcessOrderActionNode.DOC_ACTION_DELIVERY_DONE, StandardSwitchProxy.SWITCH_ON));
        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier getDocumentContentSpecifier() {
        return wasteProcessOrderSpecifier;
    }

    @Override
    public DocSplitMergeRequest<WasteProcessOrder, WasteProcessMaterialItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<WasteProcessOrderServiceModel, WasteProcessMaterialItem, ?> getCrossDocCovertRequest() {
        return wasteProcessOrderCrossConvertRequest;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return wasteProcessOrderManager;
    }

    public void executeActionCore(
            WasteProcessOrderServiceModel wasteProcessOrderServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(wasteProcessOrderServiceModel, docActionCode, null, null, serialLogonInfo);
    }

}
