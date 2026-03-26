package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.SerExtendPageSettingServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.SerExtendPageSettingUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocSplitMergeRequest;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.SerExtendPageMetadata;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.model.SerExtendPageSettingActionNode;

import java.util.List;
import java.util.Map;

@Service
public class SerExtendPageSettingActionExecutionProxy
        extends DocActionExecutionProxy<SerExtendPageSettingServiceModel, SerExtendPageSetting, SerExtendPageMetadata> {

    @Autowired
    protected SerExtendPageSettingManager serExtendPageSettingManager;

    @Autowired
    protected ServiceExtensionSettingManager serviceExtensionSettingManager;

    @Autowired
    protected SerExtendPageSettingServiceUIModelExtension serExtendPageSettingServiceUIModelExtension;

    @Autowired
    protected SerExtendPageSettingSpecifier serExtendPageSettingSpecifier;

    public static final String PROPERTY_ACTIONCODE_FILE = "SerExtendPageSetting_actionCode";

    protected Logger logger = LoggerFactory.getLogger(SerExtendPageSettingActionExecutionProxy.class);

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = SerExtendPageSettingUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE, null);
    }

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(SerExtendPageSettingActionNode.DOC_ACTION_ACTIVE,
                        SerExtendPageSetting.STATUS_ACTIVE,
                        ServiceCollectionsHelper.asList(SerExtendPageSetting.STATUS_INIT),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(SerExtendPageSettingActionNode.DOC_ACTION_REINIT,
                        SerExtendPageSetting.STATUS_INIT,
                        ServiceCollectionsHelper.asList(SerExtendPageSetting.STATUS_ACTIVE),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(SerExtendPageSettingActionNode.DOC_ACTION_ARCHIVE,
                        SerExtendPageSetting.STATUS_ARCHIVED,
                        ServiceCollectionsHelper.asList(SerExtendPageSetting.STATUS_INIT, SerExtendPageSetting.STATUS_ACTIVE),
                        ISystemActionCode.ACID_EDIT));
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
    public DocumentContentSpecifier<SerExtendPageSettingServiceModel, SerExtendPageSetting, SerExtendPageMetadata> getDocumentContentSpecifier() {
        return serExtendPageSettingSpecifier;
    }

    @Override
    public DocSplitMergeRequest<SerExtendPageSetting, SerExtendPageMetadata> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<SerExtendPageSettingServiceModel, SerExtendPageMetadata, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return serviceExtensionSettingManager;
    }

    public void executeActionCore(SerExtendPageSettingServiceModel serExtendPageSettingServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        // Not implemented
    }

}
