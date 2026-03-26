package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.logistics.dto.QualityInspectOrderUIModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrderActionNode;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
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
public class BillOfMaterialOrderExecutionProxy extends DocActionExecutionProxy<BillOfMaterialOrderServiceModel,
        BillOfMaterialOrder, BillOfMaterialItem> {

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected BillOfMaterialOrderSpecifier billOfMaterialOrderSpecifier;

    @Autowired
    protected BillOfMaterialOrderServiceUIModelExtension billOfMaterialOrderServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(BillOfMaterialOrderExecutionProxy.class);

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(
                        BillOfMaterialOrderActionNode.DOC_ACTION_SUBMIT, BillOfMaterialOrder.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(BillOfMaterialOrder.STATUS_INITIAL,
                                BillOfMaterialOrder.STATUS_REJECT_APPROVAL),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        BillOfMaterialOrderActionNode.DOC_ACTION_REVOKE_SUBMIT, BillOfMaterialOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(BillOfMaterialOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        BillOfMaterialOrderActionNode.DOC_ACTION_APPROVE, BillOfMaterialOrder.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(BillOfMaterialOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        BillOfMaterialOrderActionNode.DOC_ACTION_REJECT_APPROVE, BillOfMaterialOrder.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(BillOfMaterialOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        BillOfMaterialOrderActionNode.DOC_ACTION_REINIT, BillOfMaterialOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(BillOfMaterialOrder.STATUS_INUSE),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        BillOfMaterialOrderActionNode.DOC_ACTION_ACTIVE, BillOfMaterialOrder.STATUS_INUSE,
                        ServiceCollectionsHelper.asList(BillOfMaterialOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        BillOfMaterialOrderActionNode.DOC_ACTION_ARCHIVE, BillOfMaterialOrder.STATUS_RETIRED,
                        ServiceCollectionsHelper.asList(BillOfMaterialOrder.STATUS_INUSE),
                        ISystemActionCode.ACID_AUDITDOC
                )
        );
        return defDocActionConfigureList;
    }

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = QualityInspectOrderUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode,
                ServiceCollectionsHelper.asList(SystemDefDocActionCodeProxy.DOC_ACTION_COUNTAPPROVE));
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
        return this.getCustomCrossDocActionConfigureListTool(BillOfMaterialOrder.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST, client);
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
    public DocumentContentSpecifier<BillOfMaterialOrderServiceModel, BillOfMaterialOrder,
            BillOfMaterialItem> getDocumentContentSpecifier() {
        return billOfMaterialOrderSpecifier;
    }

    @Override
    public DocSplitMergeRequest<BillOfMaterialOrder, BillOfMaterialItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<BillOfMaterialOrderServiceModel, BillOfMaterialItem, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return billOfMaterialOrderManager;
    }

    public void executeActionCore(
            BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(billOfMaterialOrderServiceModel, docActionCode, null, null, serialLogonInfo);
    }

}
