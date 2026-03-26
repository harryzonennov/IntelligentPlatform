package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.LogonUserServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.LogonUserUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.LogonUserActionNode;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.List;
import java.util.Map;

@Service
public class LogonUserActionExecutionProxy
        extends DocActionExecutionProxy<LogonUserServiceModel, LogonUser, DocMatItemNode> {

    @Autowired
    protected LogonUserManager logonUserManager;

    @Autowired
    protected LogonUserServiceUIModelExtension logonUserServiceUIModelExtension;

    @Autowired
    protected LogonUserSpecifier logonUserSpecifier;

    public static final String PROPERTY_ACTIONCODE_FILE = "LogonUser_actionCode";

    protected Logger logger = LoggerFactory.getLogger(LogonUserActionExecutionProxy.class);

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = LogonUserUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE, null);
    }

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        return ServiceCollectionsHelper.asList(
                new DocActionConfigure(LogonUserActionNode.DOC_ACTION_ACTIVE,
                        LogonUser.STATUS_ACTIVE,
                        ServiceCollectionsHelper.asList(LogonUser.STATUS_INIT),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(LogonUserActionNode.DOC_ACTION_REINIT,
                        LogonUser.STATUS_INIT,
                        ServiceCollectionsHelper.asList(LogonUser.STATUS_ACTIVE),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(LogonUserActionNode.DOC_ACTION_ARCHIVE,
                        LogonUser.STATUS_ARCHIVED,
                        ServiceCollectionsHelper.asList(LogonUser.STATUS_INIT, LogonUser.STATUS_ACTIVE),
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
    public DocumentContentSpecifier<LogonUserServiceModel, LogonUser, DocMatItemNode> getDocumentContentSpecifier() {
        return logonUserSpecifier;
    }

    @Override
    public DocSplitMergeRequest<LogonUser, DocMatItemNode> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<LogonUserServiceModel, DocMatItemNode, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return logonUserManager;
    }

    public void executeActionCore(LogonUserServiceModel logonUserServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(logonUserServiceModel, docActionCode,
                null, null, serialLogonInfo);
    }

}
