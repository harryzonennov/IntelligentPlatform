package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.RepairProdTargetMatItemUIModel;
import com.company.IntelligentPlatform.production.dto.RepairProdOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.RepairProdOrderUIModel;
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
public class RepairProdOrderSpecifier extends DocumentContentSpecifier<RepairProdOrderServiceModel, RepairProdOrder,
        RepairProdTargetMatItem> {

    @Autowired
    protected RepairProdOrderServiceUIModelExtension repairProdOrderServiceUIModelExtension;

    @Autowired
    protected RepairProdOrderManager repairProdOrderManager;

    @Autowired
    protected RepairProdOrderIdHelper repairProdOrderIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER;
    }

    @Override
    public Integer getDocumentStatus(RepairProdOrder repairProdOrder) {
        return repairProdOrder.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return repairProdOrderManager;
    }

    @Override
    public RepairProdOrder setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        RepairProdOrder repairProdOrder = (RepairProdOrder) serviceEntityTargetStatus.getServiceEntityNode();
        repairProdOrder.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return repairProdOrder;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return repairProdOrderIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, RepairProdOrderServiceModel repairProdOrderServiceModel) {
        if(partyRole == RepairProdOrderParty.PARTY_ROLE_CUSTOMER){
            return repairProdOrderServiceModel.getCorporateCustomerParty();
        }
        if(partyRole == RepairProdOrderParty.PARTY_ROLE_PRODORG){
            return repairProdOrderServiceModel.getProductionOrgParty();
        }
        if(partyRole == RepairProdOrderParty.PARTY_ROLE_SALESORG){
            return repairProdOrderServiceModel.getSalesOrganizationParty();
        }
        if(partyRole == RepairProdOrderParty.PARTY_ROLE_PURORG){
            return repairProdOrderServiceModel.getPurchaseOrgParty();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }


    @Override
    public void setDocInvolveParty(RepairProdOrderServiceModel repairProdOrderServiceModel,
                                   DocInvolveParty docInvolveParty) {
        RepairProdOrderParty repairProdOrderParty = (RepairProdOrderParty) docInvolveParty;
        if(repairProdOrderParty.getPartyRole() == RepairProdOrderParty.PARTY_ROLE_CUSTOMER){
            repairProdOrderServiceModel.setCorporateCustomerParty(repairProdOrderParty);
        }
        if(repairProdOrderParty.getPartyRole() == RepairProdOrderParty.PARTY_ROLE_PRODORG){
            repairProdOrderServiceModel.setProductionOrgParty(repairProdOrderParty);
        }
        if(repairProdOrderParty.getPartyRole() == RepairProdOrderParty.PARTY_ROLE_SALESORG){
            repairProdOrderServiceModel.setSalesOrganizationParty(repairProdOrderParty);
        }
        if(repairProdOrderParty.getPartyRole() == RepairProdOrderParty.PARTY_ROLE_PURORG){
            repairProdOrderServiceModel.setPurchaseOrgParty(repairProdOrderParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(RepairProdOrderServiceModel repairProdOrderServiceModel) {
        return repairProdOrderServiceModel.getRepairProdTargetMatItemList();
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return repairProdOrderManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(RepairProdOrder.class, RepairProdOrderServiceModel.class, RepairProdOrderActionNode.class,
                RepairProdTargetMatItem.class, RepairProdTargetMatItemServiceModel.class, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return repairProdOrderServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(RepairProdOrder.SENAME, RepairProdOrderUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(RepairProdTargetMatItem.NODENAME,
                RepairProdTargetMatItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IProductionI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(RepairProdOrder.SENAME, basePath + "RepairProdOrder"));
        propertyMapList.add(new PropertyMap(RepairProdTargetMatItem.NODENAME,
                basePath + "RepairProdTargetMatItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(RepairProdOrderServiceModel repairProdOrderServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<RepairProdTargetMatItem> docItemActionCallback,
                                    SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(repairProdOrderServiceModel.getRepairProdTargetMatItemList())) {
            for (RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel :
                    repairProdOrderServiceModel
                            .getRepairProdTargetMatItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(repairProdTargetMatItemServiceModel.getRepairProdTargetMatItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
