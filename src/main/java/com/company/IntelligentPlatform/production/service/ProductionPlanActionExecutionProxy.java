package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.logistics.service.QualityInspectCrossConvertRequest;
import com.company.IntelligentPlatform.production.dto.ProductionPlanServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionPlanUIModel;
import com.company.IntelligentPlatform.production.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductionPlanActionExecutionProxy
        extends DocActionExecutionProxy<ProductionPlanServiceModel, ProductionPlan, ProdPlanTargetMatItem> {

    @Autowired
    protected ProductionPlanManager productionPlanManager;

    @Autowired
    protected ProductionPlanSpecifier productionPlanSpecifier;

    @Autowired
    protected ProductionPlanServiceUIModelExtension productionPlanServiceUIModelExtension;

    @Autowired
    protected ProductionPlanCrossConvertRequest productionPlanCrossConvertRequest;

    @Autowired
    protected QualityInspectCrossConvertRequest qualityInspectCrossConvertRequest;

    public static final String PROPERTY_ACTIONCODE_FILE = "ProductionPlan_actionCode";

    protected Logger logger = LoggerFactory.getLogger(ProductionPlanActionExecutionProxy.class);
    @Autowired
    private LogonInfoManager logonInfoManager;

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        return ServiceCollectionsHelper.asList(
                new DocActionConfigure(ProductionPlanActionNode.DOC_ACTION_SUBMIT, ProductionPlan.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(ProductionPlan.STATUS_INITIAL,
                                ProductionPlan.STATUS_REJECT_APPROVAL), ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(ProductionPlanActionNode.DOC_ACTION_REVOKE_SUBMIT, ProductionPlan.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(ProductionPlan.STATUS_SUBMITTED), ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(ProductionPlanActionNode.DOC_ACTION_APPROVE, ProductionPlan.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(ProductionPlan.STATUS_SUBMITTED), ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(ProductionPlanActionNode.DOC_ACTION_REJECT_APPROVE,
                        ProductionPlan.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(ProductionPlan.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(ProductionPlanActionNode.DOC_ACTION_COUNTAPPROVE,
                        ProductionPlan.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(ProductionPlan.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(ProductionPlanActionNode.DOC_ACTION_INPRODUCTION,
                        ProductionPlan.STATUS_INPRODUCTION,
                        ServiceCollectionsHelper.asList(ProductionPlan.STATUS_APPROVED), ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(ProductionPlanActionNode.DOC_ACTION_FINISHED,
                        ProductionPlan.STATUS_FINISHED,
                        ServiceCollectionsHelper.asList(ProductionPlan.STATUS_INPRODUCTION),
                        ISystemActionCode.ACID_EDIT));
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
        return this.getCustomCrossDocActionConfigureListTool(ProductionPlan.SENAME,
                IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN, client);
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap = new HashMap<>();
        crossCopyDocConversionConfigMap.put(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER,
                new CrossCopyDocConversionConfig(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER,
                        IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER, ServiceCollectionsHelper.asList(
                        new CrossCopyPartyConversionConfig(ProductionPlanParty.PARTY_ROLE_PURORG,
                                ProductionOrderParty.PARTY_ROLE_PURORG, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(ProductionPlanParty.PARTY_ROLE_SUPPLIER,
                                ProductionOrderParty.PARTY_ROLE_SUPPLIER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(ProductionPlanParty.PARTY_ROLE_SUPPLIER,
                                ProductionOrderParty.PARTY_ROLE_SUPPLIER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(ProductionPlanParty.PARTY_ROLE_CUSTOMER,
                                ProductionOrderParty.PARTY_ROLE_CUSTOMER, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(ProductionPlanParty.PARTY_ROLE_PRODORG,
                                ProductionOrderParty.PARTY_ROLE_PRODORG, StandardSwitchProxy.SWITCH_OFF),
                        new CrossCopyPartyConversionConfig(ProductionPlanParty.PARTY_ROLE_SALESORG,
                                ProductionOrderParty.PARTY_ROLE_SALESORG, StandardSwitchProxy.SWITCH_OFF)),
                        StandardSwitchProxy.SWITCH_ON, 0, StandardSwitchProxy.SWITCH_ON));
        return crossCopyDocConversionConfigMap;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier<ProductionPlanServiceModel, ProductionPlan, ProdPlanTargetMatItem> getDocumentContentSpecifier() {
        return productionPlanSpecifier;
    }

    @Override
    public DocSplitMergeRequest<ProductionPlan, ProdPlanTargetMatItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<ProductionPlanServiceModel, ProdPlanTargetMatItem, ?> getCrossDocCovertRequest() {
        return productionPlanCrossConvertRequest;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return productionPlanManager;
    }

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = ProductionPlanUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE);
    }

    public void executeActionCore(ProductionPlanServiceModel productionPlanServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(productionPlanServiceModel, docActionCode, (productionPlan, serialLogonInfo1) -> {
            LogonInfo logonInfo = logonInfoManager.genLogonInfo(serialLogonInfo, false);
            if (docActionCode == ProductionPlanActionNode.DOC_ACTION_APPROVE) {
                try {
                    productionPlanManager.approvePlan(productionPlanServiceModel,
                            logonInfo);
                } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (ProductionPlanException | SearchConfigureException | ServiceModuleProxyException |
                         BillOfMaterialException | MaterialException | ServiceComExecuteException |
                         AuthorizationException | LogonInfoException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            if (docActionCode == ProductionPlanActionNode.DOC_ACTION_COUNTAPPROVE) {
                try {
                    productionPlanManager.countApprovePlan(productionPlanServiceModel,
                            serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
                } catch (ServiceEntityConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (ServiceModuleProxyException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            if (docActionCode == ProductionPlanActionNode.DOC_ACTION_INPRODUCTION) {
                try {
                    productionPlanManager.releasePlanService(productionPlanServiceModel, serialLogonInfo,true);
                } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (ProductionPlanException | SearchConfigureException | ServiceModuleProxyException |
                         BillOfMaterialException | MaterialException | ProductionOrderException |
                         ServiceComExecuteException | LogonInfoException | AuthorizationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            if(docActionCode == ProductionPlanActionNode.DOC_ACTION_FINISHED){
                try {
                    productionPlanManager.setPlanComplete(productionPlanServiceModel.getProductionPlan(),
                            serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
                } catch (ServiceEntityConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (ProductionPlanException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            return productionPlan;
        }, null, serialLogonInfo);
    }

}
