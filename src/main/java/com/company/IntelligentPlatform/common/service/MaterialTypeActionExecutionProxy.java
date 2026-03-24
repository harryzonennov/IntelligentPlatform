package com.company.IntelligentPlatform.common.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialTypeServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.MaterialTypeUIModel;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.model.MaterialTypeActionNode;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocSplitMergeRequest;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.List;
import java.util.Map;

@Service
public class MaterialTypeActionExecutionProxy
        extends DocActionExecutionProxy<MaterialTypeServiceModel, MaterialType, DocMatItemNode> {

    @Autowired
    protected MaterialTypeManager materialTypeManager;

    @Autowired
    protected MaterialTypeServiceUIModelExtension materialTypeServiceUIModelExtension;

    @Autowired
    protected MaterialTypeSpecifier materialTypeSpecifier;

    public static final String PROPERTY_ACTIONCODE_FILE = "MaterialType_actionCode";

    protected Logger logger = LoggerFactory.getLogger(MaterialTypeActionExecutionProxy.class);

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = MaterialTypeUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE, null);
    }

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        return ServiceCollectionsHelper.asList(
                new DocActionConfigure(MaterialTypeActionNode.DOC_ACTION_ACTIVE,
                        MaterialType.STATUS_ACTIVE,
                        ServiceCollectionsHelper.asList(MaterialType.STATUS_INIT),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(MaterialTypeActionNode.DOC_ACTION_REINIT,
                        MaterialType.STATUS_INIT,
                        ServiceCollectionsHelper.asList(MaterialType.STATUS_ACTIVE),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(MaterialTypeActionNode.DOC_ACTION_ARCHIVE,
                        MaterialType.STATUS_ARCHIVED,
                        ServiceCollectionsHelper.asList(MaterialType.STATUS_INIT, MaterialType.STATUS_ACTIVE),
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
    public DocumentContentSpecifier<MaterialTypeServiceModel, MaterialType, DocMatItemNode> getDocumentContentSpecifier() {
        return materialTypeSpecifier;
    }

    @Override
    public DocSplitMergeRequest<MaterialType, DocMatItemNode> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<MaterialTypeServiceModel, DocMatItemNode, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return materialTypeManager;
    }

    public void executeActionCore(MaterialTypeServiceModel materialTypeServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(materialTypeServiceModel, docActionCode,
                null, null, serialLogonInfo);
    }

}
