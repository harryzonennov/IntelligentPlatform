package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.logistics.service.IDeliveryI18nPackage;
import com.company.IntelligentPlatform.production.dto.ProdPickingOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProdPickingOrderUIModel;
import com.company.IntelligentPlatform.production.dto.ProdPickingRefMaterialItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProdPickingRefMaterialItemUIModel;
import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProdPickingOrderActionNode;
import com.company.IntelligentPlatform.production.model.ProdPickingOrderParty;
import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import com.company.IntelligentPlatform.sales.service.SalesContractMaterialItemServiceModel;
import com.company.IntelligentPlatform.sales.service.SalesContractServiceModel;
import com.company.IntelligentPlatform.sales.model.SalesContract;
import com.company.IntelligentPlatform.sales.model.SalesContractMaterialItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProdPickingOrderSpecifier extends DocumentContentSpecifier<ProdPickingOrderServiceModel, ProdPickingOrder, ProdPickingRefMaterialItem> {

    @Autowired
    protected ProdPickingOrderServiceUIModelExtension prodPickingOrderServiceUIModelExtension;

    @Autowired
    protected ProdPickingRefMaterialItemServiceUIModelExtension prodPickingRefMaterialItemServiceUIModelExtension;

    @Autowired
    protected ProdPickingOrderManager prodPickingOrderManager;

    @Autowired
    protected ProdPickingOrderIdHelper prodPickingOrderIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER;
    }

    @Override
    public Integer getDocumentStatus(ProdPickingOrder prodPickingOrder) {
        return prodPickingOrder.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return prodPickingOrderManager;
    }

    @Override
    public ProdPickingOrder setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        ProdPickingOrder prodPickingOrder = (ProdPickingOrder) serviceEntityTargetStatus.getServiceEntityNode();
        prodPickingOrder.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return prodPickingOrder;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return prodPickingOrderIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, ProdPickingOrderServiceModel prodPickingOrderServiceModel) {
        if(partyRole == ProdPickingOrderParty.PARTY_ROLE_CUSTOMER){
            return prodPickingOrderServiceModel.getCorporateCustomerParty();
        }
        if(partyRole == ProdPickingOrderParty.PARTY_ROLE_PRODORG){
            return prodPickingOrderServiceModel.getProductionOrgParty();
        }
        if(partyRole == ProdPickingOrderParty.PARTY_ROLE_SALESORG){
            return prodPickingOrderServiceModel.getSalesOrganizationParty();
        }
        if(partyRole == ProdPickingOrderParty.PARTY_ROLE_PURORG){
            return prodPickingOrderServiceModel.getPurchaseOrgParty();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(ProdPickingOrderServiceModel prodPickingOrderServiceModel,
                                   DocInvolveParty docInvolveParty) {
        ProdPickingOrderParty prodPickingOrderParty = (ProdPickingOrderParty) docInvolveParty;
        if(prodPickingOrderParty.getPartyRole() == ProdPickingOrderParty.PARTY_ROLE_CUSTOMER){
            prodPickingOrderServiceModel.setCorporateCustomerParty(prodPickingOrderParty);
        }
        if(prodPickingOrderParty.getPartyRole() == ProdPickingOrderParty.PARTY_ROLE_PRODORG){
            prodPickingOrderServiceModel.setProductionOrgParty(prodPickingOrderParty);
        }
        if(prodPickingOrderParty.getPartyRole() == ProdPickingOrderParty.PARTY_ROLE_SALESORG){
            prodPickingOrderServiceModel.setSalesOrganizationParty(prodPickingOrderParty);
        }
        if(prodPickingOrderParty.getPartyRole() == ProdPickingOrderParty.PARTY_ROLE_PURORG){
            prodPickingOrderServiceModel.setPurchaseOrgParty(prodPickingOrderParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(ProdPickingOrderServiceModel prodPickingOrderServiceModel) {
        List<ProdPickingRefMaterialItemServiceModel> prodPickingRefMaterialtemList = new ArrayList<>();
        ServiceCollectionsHelper.forEach(prodPickingOrderServiceModel.getProdPickingRefOrderItemList(),
                prodPickingRefOrderItemServiceModel -> {
            ServiceCollectionsHelper.directAddToList(prodPickingRefMaterialtemList,
                    prodPickingRefOrderItemServiceModel.getProdPickingRefMaterialItemList());
                    return prodPickingRefOrderItemServiceModel;
                });
        return prodPickingRefMaterialtemList;
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return prodPickingOrderManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(ProdPickingOrder.class, ProdPickingOrderServiceModel.class, ProdPickingOrderActionNode.class,
                ProdPickingRefMaterialItem.class, ProdPickingRefMaterialItemServiceModel.class, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return prodPickingOrderServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(ProdPickingOrder.SENAME, ProdPickingOrderUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(ProdPickingRefMaterialItem.NODENAME,
                ProdPickingRefMaterialItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IDeliveryI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(ProdPickingOrder.SENAME, basePath + "ProdPickingOrder"));
        propertyMapList.add(new PropertyMap(ProdPickingRefMaterialItem.NODENAME,
                basePath + "ProdPickingRefMaterialItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(ProdPickingOrderServiceModel prodPickingOrderServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<ProdPickingRefMaterialItem> docItemActionCallback,
                                    SerialLogonInfo serialLogonInfo) throws DocActionException {
        List<ProdPickingRefMaterialItemServiceModel> prodPickingRefMaterialtemList =
                (List<ProdPickingRefMaterialItemServiceModel>) this.getDocMatItemServiceModuleList(prodPickingOrderServiceModel);
       if (!ServiceCollectionsHelper
                .checkNullList(prodPickingRefMaterialtemList)) {
            for (ProdPickingRefMaterialItemServiceModel prodPickingRefMaterialItemServiceModel :
                    prodPickingRefMaterialtemList) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(prodPickingRefMaterialItemServiceModel.getProdPickingRefMaterialItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
