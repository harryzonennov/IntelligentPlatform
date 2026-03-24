package com.company.IntelligentPlatform.common.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.*;
import com.company.IntelligentPlatform.common.service.IMaterialI18nPackage;
import com.company.IntelligentPlatform.common.model.*;
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
public class MaterialStockKeepUnitSpecifier extends
        DocumentContentSpecifier<MaterialStockKeepUnitServiceModel, MaterialStockKeepUnit, DocMatItemNode> {

    @Autowired
    protected MaterialStockKeepUnitServiceUIModelExtension materialStockKeepUnitServiceUIModelExtension;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected MaterialStockKeepUnitIdHelper materialStockKeepUnitIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
    }

    @Override
    public Integer getDocumentStatus(MaterialStockKeepUnit materialStockKeepUnit) {
        return materialStockKeepUnit.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return materialStockKeepUnitManager;
    }

    @Override
    public MaterialStockKeepUnit setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) serviceEntityTargetStatus.getServiceEntityNode();
        materialStockKeepUnit.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return materialStockKeepUnit;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return materialStockKeepUnitIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, MaterialStockKeepUnitServiceModel serviceModule) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(MaterialStockKeepUnitServiceModel serviceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(MaterialStockKeepUnitServiceModel materialServiceModel) {
        return null;
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return materialStockKeepUnitManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(MaterialStockKeepUnit.class, MaterialStockKeepUnitServiceModel.class, MaterialSKUActionLog.class,
                MaterialSKUUnitReference.class, MaterialSKUUnitServiceModel.class, null);
    }

    //TODO remove in the future
    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return materialStockKeepUnitServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(MaterialStockKeepUnit.SENAME, MaterialStockKeepUnitUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(MaterialSKUUnitReference.NODENAME, MaterialSKUUnitUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IMaterialI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(MaterialStockKeepUnit.SENAME, basePath + "MaterialStockKeepUnit"));
        propertyMapList.add(new PropertyMap("MaterialSKUUnit",
                basePath + "MaterialSKUUnit"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(MaterialStockKeepUnitServiceModel materialServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {

    }

}
