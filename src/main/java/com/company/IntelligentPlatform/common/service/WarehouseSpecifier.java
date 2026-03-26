package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.WarehouseAreaUIModel;
import com.company.IntelligentPlatform.common.dto.WarehouseServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.WarehouseUIModel;
import com.company.IntelligentPlatform.common.service.IWarehouse18nPackage;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDocInitConfigureManager;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseSpecifier extends
        DocumentContentSpecifier<WarehouseServiceModel, Warehouse, WarehouseArea> {

    @Autowired
    protected WarehouseServiceUIModelExtension warehouseServiceUIModelExtension;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Autowired
    protected WarehouseIdHelper warehouseIdHelper;

    @Override
    public int getDocumentType() {
        return 0;
    }

    @Override
    public Integer getDocumentStatus(Warehouse warehouse) {
        return 0;
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return warehouseManager;
    }

    @Override
    public Warehouse setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        Warehouse warehouse = (Warehouse) serviceEntityTargetStatus.getServiceEntityNode();
        warehouse.setSwitchFlag(serviceEntityTargetStatus.getTargetStatus());
       return warehouse;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return warehouseIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, WarehouseServiceModel serviceModule) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(WarehouseServiceModel serviceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(WarehouseServiceModel warehouseServiceModel) {
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
        return new DocMetadata(Warehouse.class, WarehouseServiceModel.class, null,
                null, null, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return warehouseServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(Warehouse.SENAME, WarehouseUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(WarehouseArea.NODENAME, WarehouseAreaUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IWarehouse18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(Warehouse.SENAME, basePath + "Warehouse"));
        propertyMapList.add(new PropertyMap("WarehouseArea",
                basePath + "WarehouseArea"));
        propertyMapList.add(new PropertyMap("WarehouseStoreSetting",
                basePath + "WarehouseStoreSetting"));
        return propertyMapList;
    }

    @Override
    public List<String> getConfiguredInitIdList() {
        return null;
    }

    @Override
    public Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta>> getDefAllInitConfigureMap() {
        Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta>> serviceDocInitConfigureMap = new HashMap<>();
        serviceDocInitConfigureMap.put(ServiceEntityStringHelper.getDefModelId(Warehouse.SENAME, Warehouse.NODENAME),
                ServiceCollectionsHelper.asList(
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta(Warehouse.FIELD_refMaterialCategory,
                        null, Warehouse.REFMAT_CATEGORY_NORMAL),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta(Warehouse.FIELD_operationType,
                                null, Warehouse.OPERTYPE_SELFUSE),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta(Warehouse.FIELD_operationMode,
                                null, Warehouse.OPERMODE_HOME),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta(Warehouse.FIELD_systemDefault,
                        null, StandardSwitchProxy.SWITCH_ON)));
        return serviceDocInitConfigureMap;
    }

    @Override
    public void traverseMatItemNode(WarehouseServiceModel warehouseServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {

    }

}
