package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialStockKeepUnitServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.MaterialStockKeepUnitUIModel;
import com.company.IntelligentPlatform.common.model.MaterialSKUActionLog;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
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
public class MaterialSKUActionExecutionProxy
        extends DocActionExecutionProxy<MaterialStockKeepUnitServiceModel, MaterialStockKeepUnit, DocMatItemNode> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected MaterialStockKeepUnitServiceUIModelExtension materialStockKeepUnitServiceUIModelExtension;

    @Autowired
    protected MaterialStockKeepUnitSpecifier materialStockKeepUnitSpecifier;

    public static final String PROPERTY_ACTIONCODE_FILE = "MaterialSKU_actionCode";

    protected Logger logger = LoggerFactory.getLogger(MaterialSKUActionExecutionProxy.class);

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = MaterialStockKeepUnitUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE, null);
    }

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(MaterialSKUActionLog.DOC_ACTION_ACTIVE,
                        MaterialStockKeepUnit.STATUS_ACTIVE,
                        ServiceCollectionsHelper.asList(MaterialStockKeepUnit.STATUS_INIT),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(MaterialSKUActionLog.DOC_ACTION_REINIT,
                        MaterialStockKeepUnit.STATUS_INIT,
                        ServiceCollectionsHelper.asList(MaterialStockKeepUnit.STATUS_ACTIVE),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(MaterialSKUActionLog.DOC_ACTION_ARCHIVE,
                        MaterialStockKeepUnit.STATUS_ARCHIVED,
                        ServiceCollectionsHelper.asList(MaterialStockKeepUnit.STATUS_INIT, MaterialStockKeepUnit.STATUS_ACTIVE),
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
    public DocumentContentSpecifier<MaterialStockKeepUnitServiceModel, MaterialStockKeepUnit, DocMatItemNode> getDocumentContentSpecifier() {
        return materialStockKeepUnitSpecifier;
    }

    @Override
    public DocSplitMergeRequest<MaterialStockKeepUnit, DocMatItemNode> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<MaterialStockKeepUnitServiceModel, DocMatItemNode, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return materialStockKeepUnitManager;
    }

    public void executeActionCore(MaterialStockKeepUnitServiceModel materialStockKeepUnitServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(materialStockKeepUnitServiceModel, docActionCode,
                null, null, serialLogonInfo);
    }

}
