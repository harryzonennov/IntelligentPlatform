package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.sales.dto.SalesForcastServiceUIModelExtension;
import com.company.IntelligentPlatform.sales.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocSplitMergeRequest;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesForcastActionExecutionProxy extends DocActionExecutionProxy<SalesForcastServiceModel, SalesForcast, SalesForcastMaterialItem> {

    @Autowired
    protected SalesForcastManager salesForcastManager;

    @Autowired
    protected SalesForcastSpecifier salesForcastSpecifier;

    @Autowired
    protected SalesForcastCrossConvertRequest salesForcastCrossConvertRequest;

    @Autowired
    protected SalesForcastServiceUIModelExtension salesForcastServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(SalesForcastActionExecutionProxy.class);

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(
                        SalesForcastActionNode.DOC_ACTION_SUBMIT, SalesForcast.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(SalesForcast.STATUS_INITIAL,
                                SalesForcast.STATUS_REJECT_APPROVAL),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        SalesForcastActionNode.DOC_ACTION_REVOKE_SUBMIT, SalesForcast.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(SalesForcast.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        SalesForcastActionNode.DOC_ACTION_APPROVE, SalesForcast.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(SalesForcast.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        SalesForcastActionNode.DOC_ACTION_REJECT_APPROVE, SalesForcast.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(SalesForcast.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        SalesForcastActionNode.DOC_ACTION_COUNTAPPROVE, SalesForcast.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(SalesForcast.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        SalesForcastActionNode.DOC_ACTION_INPLAN, SalesForcast.STATUS_INPLAN,
                        ServiceCollectionsHelper.asList(SalesForcast.STATUS_APPROVED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        SalesForcastActionNode.DOC_ACTION_DONE, SalesForcast.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(SalesForcast.STATUS_APPROVED),
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
        return this.getCustomCrossDocActionConfigureListTool(SalesForcast.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST, client);
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST,
                        IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT, ServiceCollectionsHelper.asList(new CrossCopyPartyConversionConfig(
                        SalesForcastParty.ROLE_SOLD_TO_PARTY,
                        SalesContractParty.ROLE_SOLD_TO_PARTY,
                        StandardSwitchProxy.SWITCH_ON
                ),new CrossCopyPartyConversionConfig(
                        SalesForcastParty.ROLE_SOLD_FROM_PARTY,
                        SalesContractParty.ROLE_SOLD_FROM_PARTY
                )), StandardSwitchProxy.SWITCH_OFF, SalesForcastActionNode.DOC_ACTION_INPLAN,
                        StandardSwitchProxy.SWITCH_OFF));

        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }


    @Override
    public DocumentContentSpecifier<SalesForcastServiceModel, SalesForcast, SalesForcastMaterialItem> getDocumentContentSpecifier() {
        return salesForcastSpecifier;
    }

    @Override
    public DocSplitMergeRequest<SalesForcast, SalesForcastMaterialItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<SalesForcastServiceModel, SalesForcastMaterialItem, ?> getCrossDocCovertRequest() {
        return salesForcastCrossConvertRequest;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return salesForcastManager;
    }


    public void executeActionCore(
            SalesForcastServiceModel salesForcastServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(salesForcastServiceModel, docActionCode, null, null, serialLogonInfo);
    }

}