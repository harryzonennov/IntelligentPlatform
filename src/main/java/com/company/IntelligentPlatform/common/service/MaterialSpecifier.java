package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.MaterialUIModel;
import com.company.IntelligentPlatform.common.dto.MaterialUnitUIModel;
import com.company.IntelligentPlatform.common.service.IMaterialI18nPackage;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialActionLog;
import com.company.IntelligentPlatform.common.model.MaterialUnitReference;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDocInitConfigureManager;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaterialSpecifier extends
        DocumentContentSpecifier<MaterialServiceModel, Material, DocMatItemNode> {

    public static final String INIT_CONFIGID_newModuleFromType = "newModuleFromType";

    @Autowired
    protected MaterialServiceUIModelExtension materialServiceUIModelExtension;

    @Autowired
    protected MaterialManager materialManager;

    @Autowired
    protected MaterialIdHelper materialIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
    }

    @Override
    public Integer getDocumentStatus(Material material) {
        return material.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return materialManager;
    }

    @Override
    public Material setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        Material material = (Material) serviceEntityTargetStatus.getServiceEntityNode();
        material.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return material;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return materialIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, MaterialServiceModel serviceModule) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(MaterialServiceModel serviceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(MaterialServiceModel materialServiceModel) {
        return null;
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return materialManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(Material.class, MaterialServiceModel.class, MaterialActionLog.class,
                MaterialUnitReference.class, MaterialUnitServiceModel.class, null);
    }

    //TODO to remove in the future
    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return materialServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(Material.SENAME, MaterialUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(MaterialUnitReference.NODENAME, MaterialUnitUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IMaterialI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(Material.SENAME, basePath + "Material"));
        propertyMapList.add(new PropertyMap("MaterialUnit",
                basePath + "MaterialUnit"));
        return propertyMapList;
    }

    @Override
    public List<String> getConfiguredInitIdList() {
        return ServiceCollectionsHelper.asList(INIT_CONFIGID_newModuleFromType);
    }

    @Override
    public Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta>> getDefAllInitConfigureMap() {
        Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta>> serviceDocInitConfigureMap = new HashMap<>();
        serviceDocInitConfigureMap.put(ServiceEntityStringHelper.getDefModelId(Material.SENAME, Material.NODENAME), null);
        serviceDocInitConfigureMap.put(INIT_CONFIGID_newModuleFromType,
                ServiceCollectionsHelper.asList(new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta("refMaterialType",
                        "baseUUID", null)));
        return serviceDocInitConfigureMap;
    }

    @Override
    public void traverseMatItemNode(MaterialServiceModel materialServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {

    }

}
