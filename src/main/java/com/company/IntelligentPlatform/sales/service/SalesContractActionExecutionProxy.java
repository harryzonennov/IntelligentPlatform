package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreParty;
import com.company.IntelligentPlatform.sales.dto.SalesContractServiceUIModelExtension;
import com.company.IntelligentPlatform.sales.model.*;
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
public class SalesContractActionExecutionProxy extends DocActionExecutionProxy<SalesContractServiceModel,
        SalesContract, SalesContractMaterialItem> {

    @Autowired
    protected SalesContractManager salesContractManager;

    @Autowired
    protected SalesContractSpecifier salesContractSpecifier;

    @Autowired
    protected SalesContractCrossConvertRequest salesContractCrossConvertRequest;

    @Autowired
    protected SalesContractCrossConvertProfRequest salesContractCrossConvertProfRequest;

    @Autowired
    protected SalesContractServiceUIModelExtension salesContractServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(SalesContractActionExecutionProxy.class);

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(
                        SalesContractActionNode.DOC_ACTION_SUBMIT, SalesContract.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(SalesContract.STATUS_INITIAL,
                                SalesContract.STATUS_REJECT_APPROVAL),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        SalesContractActionNode.DOC_ACTION_REVOKE_SUBMIT, SalesContract.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(SalesContract.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        SalesContractActionNode.DOC_ACTION_APPROVE, SalesContract.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(SalesContract.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        SalesContractActionNode.DOC_ACTION_REJECT_APPROVE, SalesContract.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(SalesContract.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        SalesContractActionNode.DOC_ACTION_COUNTAPPROVE, SalesContract.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(SalesContract.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        SalesContractActionNode.DOC_ACTION_DELIVERY_DONE, SalesContract.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(SalesContract.STATUS_APPROVED),
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
        return this.getCustomCrossDocActionConfigureListTool(SalesContract.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT, client);
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT,
                        IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                        SalesContractParty.ROLE_SOLD_TO_PARTY,
                        SalesReturnOrderParty.ROLE_SOLD_TO_PARTY,
                        StandardSwitchProxy.SWITCH_ON
                ),new CrossCopyPartyConversionConfig(
                        SalesContractParty.ROLE_SOLD_FROM_PARTY,
                        SalesReturnOrderParty.ROLE_SOLD_FROM_PARTY
                )), StandardSwitchProxy.SWITCH_ON, 0,
                        StandardSwitchProxy.SWITCH_OFF));
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT,
                        IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                        SalesContractParty.ROLE_SOLD_TO_PARTY, OutboundDeliveryParty.PARTY_ROLE_CUSTOMER,
                        StandardSwitchProxy.SWITCH_ON
                ),new CrossCopyPartyConversionConfig(
                        SalesContractParty.ROLE_SOLD_FROM_PARTY,
                        OutboundDeliveryParty.PARTY_ROLE_SALESORG,
                        StandardSwitchProxy.SWITCH_OFF
                )), StandardSwitchProxy.SWITCH_ON,
                        SalesContractActionNode.DOC_ACTION_DELIVERY_DONE, StandardSwitchProxy.SWITCH_ON));
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT,
                        IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                        SalesContractParty.ROLE_SOLD_TO_PARTY, WarehouseStoreParty.PARTY_ROLE_CUSTOMER,
                        StandardSwitchProxy.SWITCH_ON
                ),new CrossCopyPartyConversionConfig(
                        SalesContractParty.ROLE_SOLD_FROM_PARTY,
                        WarehouseStoreParty.PARTY_ROLE_SALESORG,
                        StandardSwitchProxy.SWITCH_OFF
                )), StandardSwitchProxy.SWITCH_ON,
                        SalesContractActionNode.DOC_ACTION_DELIVERY_DONE, StandardSwitchProxy.SWITCH_ON));
        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier<SalesContractServiceModel,
            SalesContract, SalesContractMaterialItem> getDocumentContentSpecifier() {
        return salesContractSpecifier;
    }

    @Override
    public DocSplitMergeRequest<SalesContract, SalesContractMaterialItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<SalesContractServiceModel, SalesContractMaterialItem, ?> getCrossDocCovertRequest() {
        return salesContractCrossConvertRequest;
    }

    @Override
    public CrossDocConvertProfRequest<SalesContractServiceModel, SalesContractMaterialItem, ?> getCrossDocCovertProfRequest() {
        return salesContractCrossConvertProfRequest;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return salesContractManager;
    }

    public void executeActionCore(
            SalesContractServiceModel salesContractServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(salesContractServiceModel, docActionCode, null, null, serialLogonInfo);

    }

}