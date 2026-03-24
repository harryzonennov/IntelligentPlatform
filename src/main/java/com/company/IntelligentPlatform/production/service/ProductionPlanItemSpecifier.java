package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.service.i18n.IProductionI18nPackage;
import com.company.IntelligentPlatform.production.dto.ProdPlanItemReqProposalUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionPlanItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionPlanItemUIModel;
import com.company.IntelligentPlatform.production.model.ProdPlanItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionPlanItem;
import com.company.IntelligentPlatform.production.model.ProductionPlanItemParty;
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
public class ProductionPlanItemSpecifier extends DocumentContentSpecifier<ProductionPlanItemServiceModel, ProductionPlanItem, ProdPlanItemReqProposal> {

    @Autowired
    protected ProductionPlanItemServiceUIModelExtension productionPlanItemServiceUIModelExtension;

    @Autowired
    protected ProductionPlanItemManager productionPlanItemManager;

    @Autowired
    protected ProductionPlanManager productionPlanManager;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PRODUCTPLANITEM;
    }

    @Override
    public Integer getDocumentStatus(ProductionPlanItem productionPlanItem) {
        return productionPlanItem.getItemStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return productionPlanManager;
    }

    @Override
    public ProductionPlanItem setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        ProductionPlanItem productionPlanItem = (ProductionPlanItem) serviceEntityTargetStatus.getServiceEntityNode();
        productionPlanItem.setItemStatus(serviceEntityTargetStatus.getTargetStatus());
        return productionPlanItem;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return null;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, ProductionPlanItemServiceModel productionPlanItemServiceModel) {
        if(partyRole == ProductionPlanItemParty.PARTY_ROLE_CUSTOMER){
            return productionPlanItemServiceModel.getCorporateCustomerParty();
        }
        if(partyRole == ProductionPlanItemParty.PARTY_ROLE_PRODORG){
            return productionPlanItemServiceModel.getProductionOrgParty();
        }
        if(partyRole == ProductionPlanItemParty.PARTY_ROLE_SALESORG){
            return productionPlanItemServiceModel.getSalesOrganizationParty();
        }
        if(partyRole == ProductionPlanItemParty.PARTY_ROLE_PURORG){
            return productionPlanItemServiceModel.getPurchaseOrgParty();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(ProductionPlanItemServiceModel productionPlanItemServiceModel,
                                   DocInvolveParty docInvolveParty) {
        ProductionPlanItemParty productionPlanItemParty = (ProductionPlanItemParty) docInvolveParty;
        if(productionPlanItemParty.getPartyRole() == ProductionPlanItemParty.PARTY_ROLE_CUSTOMER){
            productionPlanItemServiceModel.setCorporateCustomerParty(productionPlanItemParty);
        }
        if(productionPlanItemParty.getPartyRole() == ProductionPlanItemParty.PARTY_ROLE_PRODORG){
            productionPlanItemServiceModel.setProductionOrgParty(productionPlanItemParty);
        }
        if(productionPlanItemParty.getPartyRole() == ProductionPlanItemParty.PARTY_ROLE_SALESORG){
            productionPlanItemServiceModel.setSalesOrganizationParty(productionPlanItemParty);
        }
        if(productionPlanItemParty.getPartyRole() == ProductionPlanItemParty.PARTY_ROLE_PURORG){
            productionPlanItemServiceModel.setPurchaseOrgParty(productionPlanItemParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(ProductionPlanItemServiceModel productionPlanItemServiceModel) {
        return productionPlanItemServiceModel.getProdPlanItemReqProposalList();
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
        return new DocMetadata(ProductionPlanItem.class, ProductionPlanItemServiceModel.class, null,
                ProdPlanItemReqProposal.class, ProdPlanItemReqProposalServiceModel.class, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return productionPlanItemServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(ProductionPlanItem.SENAME, ProductionPlanItemUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(ProdPlanItemReqProposal.NODENAME,
                ProdPlanItemReqProposalUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IProductionI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(ProductionPlanItem.SENAME, basePath + "ProductionPlanItem"));
        propertyMapList.add(new PropertyMap(ProdPlanItemReqProposal.NODENAME,
                basePath + "ProdPlanItemReqProposal"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(ProductionPlanItemServiceModel productionPlanItemServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<ProdPlanItemReqProposal> docItemActionCallback,
                                    SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(productionPlanItemServiceModel.getProdPlanItemReqProposalList())) {
            for (ProdPlanItemReqProposalServiceModel prodOrderItemReqProposalServiceModel :
                    productionPlanItemServiceModel
                            .getProdPlanItemReqProposalList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(prodOrderItemReqProposalServiceModel.getProdPlanItemReqProposal(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
