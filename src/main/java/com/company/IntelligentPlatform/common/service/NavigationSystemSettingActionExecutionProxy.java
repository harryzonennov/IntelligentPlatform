package com.company.IntelligentPlatform.common.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.NavigationSystemSettingServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.NavigationSystemSettingUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocSplitMergeRequest;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.NavigationSystemSetting;
import com.company.IntelligentPlatform.common.model.NavigationSystemSettingActionNode;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.List;
import java.util.Map;


@Service
public class NavigationSystemSettingActionExecutionProxy
        extends DocActionExecutionProxy<NavigationSystemSettingServiceModel, NavigationSystemSetting, DocMatItemNode> {

    @Autowired
    protected NavigationSystemSettingManager navigationSystemSettingManager;

    @Autowired
    protected NavigationSystemSettingServiceUIModelExtension navigationSystemSettingServiceUIModelExtension;

    @Autowired
    protected NavigationSystemSettingSpecifier navigationSystemSettingSpecifier;

    public static final String PROPERTY_ACTIONCODE_FILE = "NavigationSystemSetting_actionCode";

    protected Logger logger = LoggerFactory.getLogger(NavigationSystemSettingActionExecutionProxy.class);

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = NavigationSystemSettingUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE, null);
    }

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        return ServiceCollectionsHelper.asList(
                new DocActionConfigure(NavigationSystemSettingActionNode.DOC_ACTION_SUBMIT,
                        NavigationSystemSetting.STATUS_SUBMITTED,
                        ServiceCollectionsHelper.asList(NavigationSystemSetting.STATUS_INIT),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(NavigationSystemSettingActionNode.DOC_ACTION_REVOKE_SUBMIT,
                        NavigationSystemSetting.STATUS_INIT,
                        ServiceCollectionsHelper.asList(NavigationSystemSetting.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(NavigationSystemSettingActionNode.DOC_ACTION_ACTIVE,
                        NavigationSystemSetting.STATUS_ACTIVE,
                        ServiceCollectionsHelper.asList(NavigationSystemSetting.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(NavigationSystemSettingActionNode.DOC_ACTION_REJECT_APPROVE,
                        NavigationSystemSetting.STATUS_REJECT_APPROVAL,
                        ServiceCollectionsHelper.asList(NavigationSystemSetting.STATUS_SUBMITTED),
                        ISystemActionCode.ACID_AUDITDOC),
                new DocActionConfigure(NavigationSystemSettingActionNode.DOC_ACTION_REINIT,
                        NavigationSystemSetting.STATUS_INIT,
                        ServiceCollectionsHelper.asList(NavigationSystemSetting.STATUS_ACTIVE),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(NavigationSystemSettingActionNode.DOC_ACTION_ARCHIVE,
                        NavigationSystemSetting.STATUS_ARCHIVED,
                        ServiceCollectionsHelper.asList(NavigationSystemSetting.STATUS_INIT, NavigationSystemSetting.STATUS_ACTIVE),
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
        return null;
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
    public DocumentContentSpecifier<NavigationSystemSettingServiceModel, NavigationSystemSetting, DocMatItemNode> getDocumentContentSpecifier() {
        return navigationSystemSettingSpecifier;
    }

    @Override
    public DocSplitMergeRequest<NavigationSystemSetting, DocMatItemNode> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<NavigationSystemSettingServiceModel, DocMatItemNode, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return navigationSystemSettingManager;
    }

    public void executeActionCore(NavigationSystemSettingServiceModel navigationSystemSettingServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        if(docActionCode == NavigationSystemSettingActionNode.DOC_ACTION_ACTIVE){
            try {
                navigationSystemSettingManager.activeSystemSetting(navigationSystemSettingServiceModel.getNavigationSystemSetting(), serialLogonInfo);
            } catch (ServiceEntityConfigureException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
            } catch (NavigationSystemSettingException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }
        super.defExecuteActionCore(navigationSystemSettingServiceModel, docActionCode,
                (navigationSystemSetting, serialLogonInfo1) -> {
                    return navigationSystemSetting;
                }, null, serialLogonInfo);
    }

}
