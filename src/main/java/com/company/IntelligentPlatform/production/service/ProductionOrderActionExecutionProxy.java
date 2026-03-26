package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.ProductionOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionOrderUIModel;
import com.company.IntelligentPlatform.production.model.ProdOrderTargetMatItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.production.model.ProductionOrderActionNode;
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
public class ProductionOrderActionExecutionProxy
        extends DocActionExecutionProxy<ProductionOrderServiceModel, ProductionOrder, ProdOrderTargetMatItem> {

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected ProductionOrderSpecifier productionOrderSpecifier;

    @Autowired
    protected LogonInfoManager logonInfoManager;

    @Autowired
    protected ProductionOrderServiceUIModelExtension productionOrderServiceUIModelExtension;

    public static final String PROPERTY_ACTIONCODE_FILE = "ProductionOrder_actionCode";

    protected Logger logger = LoggerFactory.getLogger(ProductionOrderActionExecutionProxy.class);

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(ProductionOrderActionNode.DOC_ACTION_SUBMIT, ProductionOrder.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(ProductionOrder.STATUS_INITIAL,
                                ProductionOrder.STATUS_REJECT_APPROVAL), ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(ProductionOrderActionNode.DOC_ACTION_REVOKE_SUBMIT,
                        ProductionOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(ProductionOrder.STATUS_SUBMITTED), ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(ProductionOrderActionNode.DOC_ACTION_APPROVE, ProductionOrder.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(ProductionOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(ProductionOrderActionNode.DOC_ACTION_INPRODUCTION,
                        ProductionOrder.STATUS_INPROCESS,
                        ServiceCollectionsHelper.asList(ProductionOrder.STATUS_APPROVED), ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(ProductionOrderActionNode.DOC_ACTION_REJECT_APPROVE,
                        ProductionOrder.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(ProductionOrder.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(ProductionOrderActionNode.DOC_ACTION_COUNTAPPROVE,
                        ProductionOrder.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(ProductionOrder.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(ProductionOrderActionNode.DOC_ACTION_FINISHED,
                        ProductionOrder.STATUS_DELIVERYDONE,
                        ServiceCollectionsHelper.asList(ProductionOrder.STATUS_INPROCESS,
                                ProductionOrder.STATUS_INPROCESS), ISystemActionCode.ACID_EDIT));
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
        return this.getCustomCrossDocActionConfigureListTool(ProductionOrder.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER, client);
    }

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = ProductionOrderUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE);
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
    public DocumentContentSpecifier<ProductionOrderServiceModel, ProductionOrder, ProdOrderTargetMatItem> getDocumentContentSpecifier() {
        return productionOrderSpecifier;
    }

    @Override
    public DocSplitMergeRequest<ProductionOrder, ProdOrderTargetMatItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<ProductionOrderServiceModel, ProdOrderTargetMatItem, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return productionOrderManager;
    }

    public void executeActionCore(ProductionOrderServiceModel productionOrderServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        LogonInfo logonInfo = logonInfoManager.genLogonInfo(serialLogonInfo, false);
        if (docActionCode == ProductionOrderActionNode.DOC_ACTION_SUBMIT) {
            try {
                productionOrderManager.checkSubmit(productionOrderServiceModel);
            } catch (ProductionOrderException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }
        super.defExecuteActionCore(productionOrderServiceModel, docActionCode, (productionOrder, serialLogonInfo1) -> {
            if (docActionCode == ProductionOrderActionNode.DOC_ACTION_APPROVE) {
                try {
                    productionOrderManager.approveOrder(productionOrderServiceModel, false, logonInfo);
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
            if (docActionCode == ProductionOrderActionNode.DOC_ACTION_INPRODUCTION) {
                try {
                    productionOrderManager.inProduction(productionOrderServiceModel, serialLogonInfo);
                } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (SearchConfigureException | ServiceModuleProxyException | LogonInfoException |
                         AuthorizationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            if (docActionCode == ProductionOrderActionNode.DOC_ACTION_FINISHED) {
                try {
                    productionOrderManager.setOrderComplete(productionOrderServiceModel,
                            logonInfoManager.genLogonInfo(serialLogonInfo, true));
                } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (SearchConfigureException | ServiceModuleProxyException | ProductionOrderException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            return productionOrder;
        }, null, serialLogonInfo);
    }

}
