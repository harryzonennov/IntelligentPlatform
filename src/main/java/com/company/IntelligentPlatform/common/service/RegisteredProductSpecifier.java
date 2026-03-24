package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialSKUUnitUIModel;
import com.company.IntelligentPlatform.common.dto.RegisteredProductServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.RegisteredProductUIModel;
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
public class RegisteredProductSpecifier extends
        DocumentContentSpecifier<RegisteredProductServiceModel, RegisteredProduct, DocMatItemNode> {

    @Autowired
    protected RegisteredProductServiceUIModelExtension registeredProductServiceUIModelExtension;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected RegisteredProductIdHelper registeredProductIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
    }

    @Override
    public Integer getDocumentStatus(RegisteredProduct registeredProduct) {
        return registeredProduct.getTraceStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return registeredProductManager;
    }

    @Override
    public RegisteredProduct setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        RegisteredProduct registeredProduct = (RegisteredProduct) serviceEntityTargetStatus.getServiceEntityNode();
        registeredProduct.setTraceStatus(serviceEntityTargetStatus.getTargetStatus());
        return registeredProduct;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return registeredProductIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, RegisteredProductServiceModel serviceModule) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(RegisteredProductServiceModel serviceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(RegisteredProductServiceModel registeredProductServiceModel) {
        return null;
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return registeredProductManager.initTraceStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(RegisteredProduct.class, RegisteredProductServiceModel.class, RegisteredProductActionLog.class,
                null, null, null);
    }

    //TODO remove in the future
    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return registeredProductServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(RegisteredProduct.SENAME, RegisteredProductUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(MaterialSKUUnitReference.NODENAME, MaterialSKUUnitUIModel.class));
        return uiModelClassMap;
    }



    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IMaterialI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(RegisteredProduct.SENAME, basePath + "RegisteredProduct"));
        propertyMapList.add(new PropertyMap(RegisteredProductExtendProperty.NODENAME,
                basePath + "RegisteredProductExtendProperty"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(RegisteredProductServiceModel registeredProductServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {

    }


}
