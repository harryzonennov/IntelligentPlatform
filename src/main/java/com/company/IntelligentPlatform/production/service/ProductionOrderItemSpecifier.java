package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.service.i18n.IProductionI18nPackage;
import com.company.IntelligentPlatform.production.dto.ProdOrderItemReqProposalUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionOrderItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionOrderItemUIModel;
import com.company.IntelligentPlatform.production.model.ProdOrderItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;
import com.company.IntelligentPlatform.production.model.ProductionOrderItemParty;
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
public class ProductionOrderItemSpecifier extends DocumentContentSpecifier<ProductionOrderItemServiceModel, ProductionOrderItem, ProdOrderItemReqProposal> {

    @Autowired
    protected ProductionOrderItemServiceUIModelExtension productionOrderItemServiceUIModelExtension;

    @Autowired
    protected ProductionOrderItemManager productionOrderItemManager;

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PRODUCTORDERITEM;
    }

    @Override
    public Integer getDocumentStatus(ProductionOrderItem productionOrderItem) {
        return productionOrderItem.getItemStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return productionOrderManager;
    }

    @Override
    public ProductionOrderItem setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        ProductionOrderItem productionOrderItem = (ProductionOrderItem) serviceEntityTargetStatus.getServiceEntityNode();
        productionOrderItem.setItemStatus(serviceEntityTargetStatus.getTargetStatus());
        return productionOrderItem;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return null;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, ProductionOrderItemServiceModel productionOrderItemServiceModel) {
        if(partyRole == ProductionOrderItemParty.PARTY_ROLE_CUSTOMER){
            return productionOrderItemServiceModel.getCorporateCustomerParty();
        }
        if(partyRole == ProductionOrderItemParty.PARTY_ROLE_PRODORG){
            return productionOrderItemServiceModel.getProductionOrgParty();
        }
        if(partyRole == ProductionOrderItemParty.PARTY_ROLE_SALESORG){
            return productionOrderItemServiceModel.getSalesOrganizationParty();
        }
        if(partyRole == ProductionOrderItemParty.PARTY_ROLE_PURORG){
            return productionOrderItemServiceModel.getPurchaseOrgParty();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(ProductionOrderItemServiceModel productionOrderItemServiceModel,
                                   DocInvolveParty docInvolveParty) {
        ProductionOrderItemParty productionOrderItemParty = (ProductionOrderItemParty) docInvolveParty;
        if(productionOrderItemParty.getPartyRole() == ProductionOrderItemParty.PARTY_ROLE_CUSTOMER){
            productionOrderItemServiceModel.setCorporateCustomerParty(productionOrderItemParty);
        }
        if(productionOrderItemParty.getPartyRole() == ProductionOrderItemParty.PARTY_ROLE_PRODORG){
            productionOrderItemServiceModel.setProductionOrgParty(productionOrderItemParty);
        }
        if(productionOrderItemParty.getPartyRole() == ProductionOrderItemParty.PARTY_ROLE_SALESORG){
            productionOrderItemServiceModel.setSalesOrganizationParty(productionOrderItemParty);
        }
        if(productionOrderItemParty.getPartyRole() == ProductionOrderItemParty.PARTY_ROLE_PURORG){
            productionOrderItemServiceModel.setPurchaseOrgParty(productionOrderItemParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(ProductionOrderItemServiceModel productionOrderItemServiceModel) {
        return productionOrderItemServiceModel.getProdOrderItemReqProposalList();
    }


    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return productionOrderManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(ProductionOrderItem.class, ProductionOrderItemServiceModel.class, null,
                ProdOrderItemReqProposal.class, ProdOrderItemReqProposalServiceModel.class, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return productionOrderItemServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(ProductionOrderItem.SENAME, ProductionOrderItemUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(ProdOrderItemReqProposal.NODENAME,
                ProdOrderItemReqProposalUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {

        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IProductionI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(ProductionOrderItem.SENAME, basePath + "ProductionOrderItem"));
        propertyMapList.add(new PropertyMap(ProdOrderItemReqProposal.NODENAME,
                basePath + "ProdOrderItemReqProposal"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(ProductionOrderItemServiceModel productionOrderItemServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<ProdOrderItemReqProposal> docItemActionCallback,
                                    SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(productionOrderItemServiceModel.getProdOrderItemReqProposalList())) {
            for (ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel :
                    productionOrderItemServiceModel
                            .getProdOrderItemReqProposalList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(prodOrderItemReqProposalServiceModel.getProdOrderItemReqProposal(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
