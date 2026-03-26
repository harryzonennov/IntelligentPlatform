package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateUIModel;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplate;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateActionNode;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocSplitMergeRequest;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.List;
import java.util.Map;

@Service
public class BillOfMaterialTemplateExecutionProxy extends DocActionExecutionProxy<BillOfMaterialTemplateServiceModel,
        BillOfMaterialTemplate, BillOfMaterialTemplateItem> {

    @Autowired
    protected BillOfMaterialTemplateManager billOfMaterialTemplateManager;

    @Autowired
    protected BillOfMaterialTemplateSpecifier billOfMaterialTemplateSpecifier;

    @Autowired
    protected BillOfMaterialTemplateServiceUIModelExtension billOfMaterialTemplateServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(BillOfMaterialTemplateExecutionProxy.class);

    public static final String PROPERTY_ACTIONCODE_FILE = "BillOfMaterialTemplate_actionCode";

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(
                        BillOfMaterialTemplateActionNode.DOC_ACTION_SUBMIT, BillOfMaterialTemplate.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(BillOfMaterialTemplate.STATUS_INITIAL,
                                BillOfMaterialTemplate.STATUS_REJECT_APPROVAL),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        BillOfMaterialTemplateActionNode.DOC_ACTION_REVOKE_SUBMIT, BillOfMaterialTemplate.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(BillOfMaterialTemplate.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_EDIT
                ),
                new DocActionConfigure(
                        BillOfMaterialTemplateActionNode.DOC_ACTION_APPROVE, BillOfMaterialTemplate.STATUS_APPROVED,
                        ServiceCollectionsHelper.asList(BillOfMaterialTemplate.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        BillOfMaterialTemplateActionNode.DOC_ACTION_REJECT_APPROVE, BillOfMaterialTemplate.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(BillOfMaterialTemplate.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        BillOfMaterialTemplateActionNode.DOC_ACTION_REINIT, BillOfMaterialTemplate.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(BillOfMaterialTemplate.STATUS_INUSE),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        BillOfMaterialTemplateActionNode.DOC_ACTION_ACTIVE, BillOfMaterialTemplate.STATUS_INUSE,
                        ServiceCollectionsHelper.asList(BillOfMaterialTemplate.STATUS_APPROVED),
                        ISystemActionCode.ACID_AUDITDOC
                ),
                new DocActionConfigure(
                        BillOfMaterialTemplateActionNode.DOC_ACTION_ARCHIVE, BillOfMaterialTemplate.STATUS_RETIRED,
                        ServiceCollectionsHelper.asList(BillOfMaterialTemplate.STATUS_INUSE),
                        ISystemActionCode.ACID_AUDITDOC
                )
        );
        return defDocActionConfigureList;
    }

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = BillOfMaterialTemplateUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE, null);
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
        return this.getCustomCrossDocActionConfigureListTool(BillOfMaterialTemplate.SENAME,
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
    public DocumentContentSpecifier<BillOfMaterialTemplateServiceModel, BillOfMaterialTemplate,
            BillOfMaterialTemplateItem> getDocumentContentSpecifier() {
        return billOfMaterialTemplateSpecifier;
    }

    @Override
    public DocSplitMergeRequest<BillOfMaterialTemplate, BillOfMaterialTemplateItem> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<BillOfMaterialTemplateServiceModel, BillOfMaterialTemplateItem, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return billOfMaterialTemplateManager;
    }

    public void executeActionCore(
            BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel,
            int docActionCode,
            SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(billOfMaterialTemplateServiceModel, docActionCode, (billOfMaterialTemplate, serialLogonInfo1) -> {
            if(docActionCode == BillOfMaterialTemplateActionNode.DOC_ACTION_ACTIVE){
                try {
                    billOfMaterialTemplateManager.activeService(billOfMaterialTemplateServiceModel,
                            serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
                } catch (ServiceModuleProxyException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                } catch (ServiceEntityConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                }
            }
            if(docActionCode == BillOfMaterialTemplateActionNode.DOC_ACTION_REINIT){
                try {
                    billOfMaterialTemplateManager.reInitBOMTemplate(billOfMaterialTemplateServiceModel,
                            serialLogonInfo);
                } catch (ServiceEntityConfigureException  e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (ServiceModuleProxyException e) {
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            return billOfMaterialTemplate;
        }, (billOfMaterialTemplateMaterialItem, itemSelectionExecutionContext) -> {
                    super.checkUpdateItemStatus(billOfMaterialTemplateMaterialItem,docActionCode, serialLogonInfo, false, null);
                    return true;
                }, serialLogonInfo);
    }

}
