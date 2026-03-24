package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.InventoryCheckItemServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.dto.InventoryCheckItemUIModel;
import com.company.IntelligentPlatform.logistics.dto.InventoryCheckOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.dto.InventoryCheckOrderUIModel;
import com.company.IntelligentPlatform.logistics.service.IDeliveryI18nPackage;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InventoryCheckOrderSpecifier extends DocumentContentSpecifier<InventoryCheckOrderServiceModel,
        InventoryCheckOrder,
        InventoryCheckItem> {

    @Autowired
    protected InventoryCheckOrderServiceUIModelExtension inventoryCheckOrderServiceUIModelExtension;

    @Autowired
    protected InventoryCheckItemServiceUIModelExtension inventoryCheckItemServiceUIModelExtension;

    @Autowired
    protected InventoryCheckOrderManager inventoryCheckOrderManager;

    @Autowired
    protected InventoryCheckOrderIdHelper inventoryCheckOrderIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_CHECKORDER;
    }

    @Override
    public Integer getDocumentStatus(InventoryCheckOrder inventoryCheckOrder) {
        return inventoryCheckOrder.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return inventoryCheckOrderManager;
    }

    @Override
    public InventoryCheckOrder setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        InventoryCheckOrder inventoryCheckOrder = (InventoryCheckOrder) serviceEntityTargetStatus.getServiceEntityNode();
        inventoryCheckOrder.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return inventoryCheckOrder;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return inventoryCheckOrderIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, InventoryCheckOrderServiceModel inventoryCheckOrderServiceModel) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {

        return null;
    }

    @Override
    public void setDocInvolveParty(InventoryCheckOrderServiceModel inventoryCheckOrderServiceModel,
                                   DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(InventoryCheckOrderServiceModel inventoryCheckOrderServiceModel) {
        return inventoryCheckOrderServiceModel.getInventoryCheckItemList();
    }


    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return inventoryCheckOrderManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(InventoryCheckOrder.class, InventoryCheckOrderServiceModel.class, InventoryCheckOrderActionNode.class,
                InventoryCheckItem.class, InventoryCheckItemServiceModel.class, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return inventoryCheckOrderServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(InventoryCheckOrder.SENAME, InventoryCheckOrderUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(InventoryCheckItem.NODENAME,
                InventoryCheckItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IDeliveryI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(InventoryCheckOrder.SENAME, basePath + "InventoryCheckOrder"));
        propertyMapList.add(new PropertyMap(InventoryCheckItem.NODENAME,
                basePath + "InventoryCheckItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(InventoryCheckOrderServiceModel inventoryCheckOrderServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<InventoryCheckItem> docItemActionCallback,
                                    SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(inventoryCheckOrderServiceModel.getInventoryCheckItemList())) {
            for (InventoryCheckItemServiceModel inventoryCheckItemServiceModel :
                    inventoryCheckOrderServiceModel
                            .getInventoryCheckItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(inventoryCheckItemServiceModel.getInventoryCheckItem(),
                            new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
