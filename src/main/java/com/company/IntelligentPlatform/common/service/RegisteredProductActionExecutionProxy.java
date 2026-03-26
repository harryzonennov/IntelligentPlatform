package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.RegisteredProductServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.RegisteredProductUIModel;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.model.RegisteredProductActionLog;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocSplitMergeRequest;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.*;

@Service
public class RegisteredProductActionExecutionProxy
        extends DocActionExecutionProxy<RegisteredProductServiceModel, RegisteredProduct, DocMatItemNode> {

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected RegisteredProductServiceUIModelExtension registeredProductServiceUIModelExtension;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    public static final String PROPERTY_ACTIONCODE_FILE = "RegisteredProduct_actionCode";

    protected Logger logger = LoggerFactory.getLogger(RegisteredProductActionExecutionProxy.class);

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = RegisteredProductUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE);
    }

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(RegisteredProductActionLog.DOC_ACTION_ACTIVE,
                        RegisteredProduct.TRACESTATUS_ACTIVE,
                        ServiceCollectionsHelper.asList(RegisteredProduct.TRACESTATUS_INIT),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(RegisteredProductActionLog.DOC_ACTION_INPROCESS,
                        RegisteredProduct.TRACESTATUS_INSERVICE,
                        ServiceCollectionsHelper.asList(RegisteredProduct.TRACESTATUS_ACTIVE),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(RegisteredProductActionLog.DOC_ACTION_WASTE,
                        RegisteredProduct.TRACESTATUS_WASTE,
                        ServiceCollectionsHelper.asList(RegisteredProduct.TRACESTATUS_INIT,
                                RegisteredProduct.TRACESTATUS_INIT, RegisteredProduct.TRACESTATUS_INSERVICE),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(RegisteredProductActionLog.DOC_ACTION_ARCHIVE,
                        RegisteredProduct.TRACESTATUS_ARCHIVE,
                        ServiceCollectionsHelper.asList(RegisteredProduct.TRACESTATUS_INIT,
                                RegisteredProduct.TRACESTATUS_INIT, RegisteredProduct.TRACESTATUS_INSERVICE),
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
    public DocumentContentSpecifier<RegisteredProductServiceModel, RegisteredProduct, DocMatItemNode> getDocumentContentSpecifier() {
        return null;
    }

    @Override
    public DocSplitMergeRequest<RegisteredProduct, DocMatItemNode> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<RegisteredProductServiceModel, DocMatItemNode, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return registeredProductManager;
    }

    public void executeActionCore(RegisteredProductServiceModel registeredProductServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(registeredProductServiceModel, docActionCode,
                 null, null, serialLogonInfo);
    }

}
