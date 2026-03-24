package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialTypeServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.MaterialTypeUIModel;
import com.company.IntelligentPlatform.common.service.IMaterialI18nPackage;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.model.MaterialTypeActionNode;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MaterialTypeSpecifier extends DocumentContentSpecifier<MaterialTypeServiceModel, MaterialType, DocMatItemNode> {

    @Autowired
    protected MaterialTypeServiceUIModelExtension materialTypeServiceUIModelExtension;

    @Autowired
    protected MaterialTypeManager materialTypeManager;

    @Autowired
    protected MaterialTypeIdHelper materialTypeIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PRODUCTPLANITEM;
    }

    @Override
    public Integer getDocumentStatus(MaterialType material) {
        return 0;
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return materialTypeManager;
    }

    @Override
    public MaterialType setDocumentStatus(DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        MaterialType materialType = (MaterialType) serviceEntityTargetStatus.getServiceEntityNode();
        materialType.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return materialType;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return materialTypeIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, MaterialTypeServiceModel serviceModule) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(MaterialTypeServiceModel serviceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(MaterialTypeServiceModel serviceModule) {
        return null;
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }


    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(MaterialType.class, MaterialTypeServiceModel.class, MaterialTypeActionNode.class,
                null, null, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return materialTypeServiceUIModelExtension;
    }

    @Override
    public void traverseMatItemNode(MaterialTypeServiceModel serviceModule,
                                    DocActionExecutionProxy.DocItemActionExecution<DocMatItemNode> docItemActionCallback,
                                    SerialLogonInfo serialLogonInfo) throws DocActionException {

    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(MaterialType.SENAME, MaterialTypeUIModel.class));
        return uiModelClassMap;
    }


    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IMaterialI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(MaterialType.SENAME, basePath + "MaterialType"));
        return propertyMapList;
    }


}
