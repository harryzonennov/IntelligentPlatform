package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.ProductionOrderUIModel;
import com.company.IntelligentPlatform.production.dto.RepairProdOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.model.RepairProdTargetMatItem;
import com.company.IntelligentPlatform.production.model.RepairProdOrder;
import com.company.IntelligentPlatform.production.model.RepairProdOrderActionNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocSplitMergeRequest;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.List;
import java.util.Map;

@Service
public class RepairProdOrderActionExecutionProxy
        extends DocActionExecutionProxy<RepairProdOrderServiceModel, RepairProdOrder, RepairProdTargetMatItem> {

    @Autowired
    protected RepairProdOrderManager repairProdOrderManager;

    @Autowired
    protected RepairProdOrderSpecifier repairProdOrderSpecifier;

    @Autowired
    protected LogonInfoManager logonInfoManager;

    @Autowired
    protected RepairProdOrderServiceUIModelExtension repairProdOrderServiceUIModelExtension;

    public static final String PROPERTY_ACTIONCODE_FILE = "RepairProdOrder_actionCode";

    protected Logger logger = LoggerFactory.getLogger(RepairProdOrderActionExecutionProxy.class);

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(RepairProdOrderActionNode.DOC_ACTION_SUBMIT, RepairProdOrder.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(RepairProdOrder.STATUS_INITIAL,
                                RepairProdOrder.STATUS_REJECT_APPROVAL), ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(RepairProdOrderActionNode.DOC_ACTION_REVOKE_SUBMIT,
                        RepairProdOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(RepairProdOrder.STATUS_SUBMITTED), ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(RepairProdOrderActionNode.DOC_ACTION_APPROVE, RepairProdOrder.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(RepairProdOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(RepairProdOrderActionNode.DOC_ACTION_INPRODUCTION,
                        RepairProdOrder.STATUS_INPROCESS,
                        ServiceCollectionsHelper.asList(RepairProdOrder.STATUS_APPROVED), ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(RepairProdOrderActionNode.DOC_ACTION_REJECT_APPROVE,
                        RepairProdOrder.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(RepairProdOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(RepairProdOrderActionNode.DOC_ACTION_COUNTAPPROVE,
                        RepairProdOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(RepairProdOrder.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(RepairProdOrderActionNode.DOC_ACTION_FINISHED,
                        RepairProdOrder.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(RepairProdOrder.STATUS_INPROCESS,
                                RepairProdOrder.STATUS_INPROCESS), ISystemActionCode.ACID_EDIT));
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
        return this.getCustomCrossDocActionConfigureListTool(RepairProdOrder.SENAME,
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
    public DocumentContentSpecifier<RepairProdOrderServiceModel, RepairProdOrder, RepairProdTargetMatItem> getDocumentContentSpecifier() {
        return repairProdOrderSpecifier;
    }

    @Override
    public DocSplitMergeRequest<RepairProdOrder, RepairProdTargetMatItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<RepairProdOrderServiceModel, RepairProdTargetMatItem, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return repairProdOrderManager;
    }

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = ProductionOrderUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE);
    }

    public void executeActionCore(RepairProdOrderServiceModel repairProdOrderServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        LogonInfo logonInfo = logonInfoManager.genLogonInfo(serialLogonInfo, false);
        if (docActionCode == RepairProdOrderActionNode.DOC_ACTION_SUBMIT) {
            try {
                repairProdOrderManager.checkSubmit(repairProdOrderServiceModel);
            } catch (ProductionOrderException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }
        super.defExecuteActionCore(repairProdOrderServiceModel, docActionCode, (repairProdOrder, serialLogonInfo1) -> {
            if (docActionCode == RepairProdOrderActionNode.DOC_ACTION_APPROVE) {
                try {
                    repairProdOrderManager.approveOrder(repairProdOrderServiceModel, false, logonInfo);
                } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (BillOfMaterialException | MaterialException | ProductionOrderException |
                         SearchConfigureException | ServiceModuleProxyException | LogonInfoException |
                         AuthorizationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            if (docActionCode == RepairProdOrderActionNode.DOC_ACTION_INPRODUCTION) {
                try {
                    repairProdOrderManager.inProduction(repairProdOrderServiceModel, serialLogonInfo.getRefUserUUID(),
                            serialLogonInfo.getResOrgUUID());
                } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (SearchConfigureException | ServiceModuleProxyException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            if (docActionCode == RepairProdOrderActionNode.DOC_ACTION_FINISHED) {
                try {
                    repairProdOrderManager.setOrderComplete(repairProdOrderServiceModel,
                            logonInfoManager.genLogonInfo(serialLogonInfo, true));
                } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (SearchConfigureException | ServiceModuleProxyException | ProductionOrderException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            return repairProdOrder;
        }, null, serialLogonInfo);
    }

}
