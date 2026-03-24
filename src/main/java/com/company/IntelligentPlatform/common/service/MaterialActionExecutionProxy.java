package com.company.IntelligentPlatform.common.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.MaterialUIModel;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialActionLog;
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
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.List;
import java.util.Map;

@Service
public class MaterialActionExecutionProxy
        extends DocActionExecutionProxy<MaterialServiceModel, Material, DocMatItemNode> {

    @Autowired
    protected MaterialManager materialManager;

    @Autowired
    protected MaterialServiceUIModelExtension materialServiceUIModelExtension;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected MaterialSpecifier materialSpecifier;

    public static final String PROPERTY_ACTIONCODE_FILE = "Material_actionCode";

    protected Logger logger = LoggerFactory.getLogger(MaterialActionExecutionProxy.class);

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = MaterialUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE, null);
    }

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(MaterialActionLog.DOC_ACTION_ACTIVE,
                        Material.STATUS_ACTIVE,
                        ServiceCollectionsHelper.asList(Material.STATUS_INIT),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(MaterialActionLog.DOC_ACTION_REINIT,
                        Material.STATUS_INIT,
                        ServiceCollectionsHelper.asList(Material.STATUS_ACTIVE),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(MaterialActionLog.DOC_ACTION_ARCHIVE,
                        Material.STATUS_ARCHIVED,
                        ServiceCollectionsHelper.asList(Material.STATUS_INIT, Material.STATUS_ACTIVE),
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
    public DocumentContentSpecifier<MaterialServiceModel, Material, DocMatItemNode> getDocumentContentSpecifier() {
        return materialSpecifier;
    }

    @Override
    public DocSplitMergeRequest<Material, DocMatItemNode> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<MaterialServiceModel, DocMatItemNode, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return materialManager;
    }

    public void executeActionCore(MaterialServiceModel materialServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        if(docActionCode == MaterialActionLog.DOC_ACTION_ACTIVE){
            try {
                materialManager.activeMaterial(materialServiceModel.getMaterial(), serialLogonInfo);
            } catch (ServiceEntityConfigureException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
            } catch (MaterialException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }
        super.defExecuteActionCore(materialServiceModel, docActionCode,
                null, null, serialLogonInfo);
    }

}
