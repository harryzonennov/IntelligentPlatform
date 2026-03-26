package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.ProdPlanTargetMatItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionPlanServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionPlanUIModel;
import com.company.IntelligentPlatform.production.service.i18n.IProductionI18nPackage;
import com.company.IntelligentPlatform.production.model.*;
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
public class ProductionPlanSpecifier extends DocumentContentSpecifier<ProductionPlanServiceModel, ProductionPlan, ProdPlanTargetMatItem> {

    @Autowired
    protected ProductionPlanServiceUIModelExtension productionPlanServiceUIModelExtension;

    @Autowired
    protected ProductionPlanManager productionPlanManager;

    @Autowired
    protected ProductionPlanIdHelper productionPlanIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN;
    }

    @Override
    public Integer getDocumentStatus(ProductionPlan productionPlan) {
        return productionPlan.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return productionPlanManager;
    }

    @Override
    public ProductionPlan setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        ProductionPlan productionPlan = (ProductionPlan) serviceEntityTargetStatus.getServiceEntityNode();
        productionPlan.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return productionPlan;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return productionPlanIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, ProductionPlanServiceModel productionPlanServiceModel) {
        if(partyRole == ProductionPlanParty.PARTY_ROLE_CUSTOMER){
            return productionPlanServiceModel.getCorporateCustomerParty();
        }
        if(partyRole == ProductionPlanParty.PARTY_ROLE_PRODORG){
            return productionPlanServiceModel.getProductionOrgParty();
        }
        if(partyRole == ProductionPlanParty.PARTY_ROLE_SALESORG){
            return productionPlanServiceModel.getSalesOrganizationParty();
        }
        if(partyRole == ProductionPlanParty.PARTY_ROLE_PURORG){
            return productionPlanServiceModel.getPurchaseOrgParty();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(ProductionPlanServiceModel productionPlanServiceModel,
                                   DocInvolveParty docInvolveParty) {
        ProductionPlanParty productionPlanParty = (ProductionPlanParty) docInvolveParty;
        if(productionPlanParty.getPartyRole() == ProductionPlanParty.PARTY_ROLE_CUSTOMER){
            productionPlanServiceModel.setCorporateCustomerParty(productionPlanParty);
        }
        if(productionPlanParty.getPartyRole() == ProductionPlanParty.PARTY_ROLE_PRODORG){
            productionPlanServiceModel.setProductionOrgParty(productionPlanParty);
        }
        if(productionPlanParty.getPartyRole() == ProductionPlanParty.PARTY_ROLE_SALESORG){
            productionPlanServiceModel.setSalesOrganizationParty(productionPlanParty);
        }
        if(productionPlanParty.getPartyRole() == ProductionPlanParty.PARTY_ROLE_PURORG){
            productionPlanServiceModel.setPurchaseOrgParty(productionPlanParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(ProductionPlanServiceModel productionPlanServiceModel) {
        return productionPlanServiceModel.getProdPlanTargetMatItemList();
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return productionPlanManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(ProductionPlan.class, ProductionPlanServiceModel.class, ProductionPlanActionNode.class,
                ProdPlanTargetMatItem.class, ProdPlanTargetMatItemServiceModel.class, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return productionPlanServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(ProductionPlan.SENAME, ProductionPlanUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(ProdPlanTargetMatItem.NODENAME,
                ProdPlanTargetMatItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IProductionI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(ProductionPlan.SENAME, basePath + "ProductionPlan"));
        propertyMapList.add(new PropertyMap(ProdPlanTargetMatItem.NODENAME,
                basePath + "ProdPlanTargetMatItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(ProductionPlanServiceModel productionPlanServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<ProdPlanTargetMatItem> docItemActionCallback,
                                    SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(productionPlanServiceModel.getProdPlanTargetMatItemList())) {
            for (ProdPlanTargetMatItemServiceModel prodPlanTargetMatItemServiceModel :
                    productionPlanServiceModel
                            .getProdPlanTargetMatItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(prodPlanTargetMatItemServiceModel.getProdPlanTargetMatItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
