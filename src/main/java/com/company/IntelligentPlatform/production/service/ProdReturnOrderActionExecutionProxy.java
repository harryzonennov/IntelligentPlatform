package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.ProdPickingOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProdPickingOrderActionNode;
import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
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
public class ProdReturnOrderActionExecutionProxy
        extends DocActionExecutionProxy<ProdPickingOrderServiceModel, ProdPickingOrder, ProdPickingRefMaterialItem> {

    @Autowired
    protected ProdPickingOrderManager prodPickingOrderManager;

    @Autowired
    protected ProdReturnOrderSpecifier prodReturnOrderSpecifier;

    @Autowired
    protected LogonInfoManager logonInfoManager;

    @Autowired
    protected ProdPickingOrderServiceUIModelExtension prodPickingOrderServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(ProdReturnOrderActionExecutionProxy.class);

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(ProdPickingOrderActionNode.DOC_ACTION_SUBMIT, ProdPickingOrder.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(ProdPickingOrder.STATUS_INITIAL,
                                ProdPickingOrder.STATUS_REJECT_APPROVAL), ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(ProdPickingOrderActionNode.DOC_ACTION_REVOKE_SUBMIT,
                        ProdPickingOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(ProdPickingOrder.STATUS_SUBMITTED), ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(ProdPickingOrderActionNode.DOC_ACTION_APPROVE, ProdPickingOrder.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(ProdPickingOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(ProdPickingOrderActionNode.DOC_ACTION_INPROCESS,
                        ProdPickingOrder.STATUS_INPROCESS,
                        ServiceCollectionsHelper.asList(ProdPickingOrder.STATUS_APPROVED), ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(ProdPickingOrderActionNode.DOC_ACTION_REJECT_APPROVE,
                        ProdPickingOrder.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(ProdPickingOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(ProdPickingOrderActionNode.DOC_ACTION_COUNTAPPROVE,
                        ProdPickingOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(ProdPickingOrder.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(ProdPickingOrderActionNode.DOC_ACTION_DELIVERY_DONE,
                        ProdPickingOrder.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(ProdPickingOrder.STATUS_INPROCESS,
                                ProdPickingOrder.STATUS_INPROCESS), ISystemActionCode.ACID_EDIT));
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
        return this.getCustomCrossDocActionConfigureListTool(ProdPickingOrder.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER, client);
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
    public DocumentContentSpecifier<ProdPickingOrderServiceModel, ProdPickingOrder, ProdPickingRefMaterialItem> getDocumentContentSpecifier() {
        return prodReturnOrderSpecifier;
    }

    @Override
    public DocSplitMergeRequest<ProdPickingOrder, ProdPickingRefMaterialItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<ProdPickingOrderServiceModel, ProdPickingRefMaterialItem, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return prodPickingOrderManager;
    }

    public void executeActionCore(ProdPickingOrderServiceModel prodPickingOrderServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(prodPickingOrderServiceModel, docActionCode, (prodPickingOrder, serialLogonInfo1) -> prodPickingOrder, null, serialLogonInfo);
    }

}
