package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.sales.dto.*;
import com.company.IntelligentPlatform.sales.service.i18n.ISalesI18nPackage;
import com.company.IntelligentPlatform.sales.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.StandardSystemVariableProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDocInitConfigureManager;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesForcastSpecifier extends DocumentContentSpecifier<SalesForcastServiceModel, SalesForcast, SalesForcastMaterialItem> {

    @Autowired
    protected SalesForcastManager salesForcastManager;

    @Autowired
    protected SalesForcastMaterialItemManager salesForcastMaterialItemManager;

    @Autowired
    protected SalesForcastIdHelper salesForcastIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST;
    }

    @Override
    public Integer getDocumentStatus(SalesForcast salesForcast) {
        return salesForcast.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return salesForcastManager;
    }

    @Override
    public SalesForcast setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        SalesForcast salesForcast = (SalesForcast) serviceEntityTargetStatus.getServiceEntityNode();
        salesForcast.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return salesForcast;
    }

    @Override
    public Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta >> getDefAllInitConfigureMap() {
        Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta >> initDocConfigureMap = new HashMap<>();
        initDocConfigureMap.put(ServiceEntityStringHelper.getDefModelId(SalesForcast.SENAME, SalesForcast.NODENAME),
                ServiceCollectionsHelper.asList(
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("soldToCustomer.partyRole",null, SalesForcastParty.ROLE_SOLD_TO_PARTY),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("soldFromOrg.refUUID",null,
                                StandardSystemVariableProxy.varLogonOrganizationUUID()),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("soldFromOrg.refContactUUID",null, StandardSystemVariableProxy.varLogonUserUUID()),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("soldFromOrg.partyRole",null,
                                SalesForcastParty.ROLE_SOLD_FROM_PARTY)));
        return initDocConfigureMap;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return salesForcastIdHelper;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() {
        // Doc root node: `SalesForcast`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(SalesForcast.class, SalesForcastUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(SalesForcastManager.METHOD_ConvSalesForcastToUI).convDocUIToMethod(SalesForcastManager.METHOD_ConvUIToSalesForcast);
        // Root flat submodules: `SalesForcastAttachment`, `SalesForcastActionNode`, `SalesForcastParty`
        docUIModelExtensionBuilder.docAttachmentClass(SalesForcastAttachment.class).docActionClass(SalesForcastActionNode.class).docInvolvePartyClass(SalesForcastParty.class);
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara( SalesForcastParty.ROLE_SOLD_TO_PARTY,
                                SalesForcastParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(SalesForcastParty.ROLE_SOLD_FROM_PARTY,
                                SalesForcastParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
        docUIModelExtensionBuilder.docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(SalesForcastActionNode.DOC_ACTION_APPROVE,
                                SalesForcastActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(SalesForcastActionNode.DOC_ACTION_SUBMIT,
                                SalesForcastActionNode.NODEINST_ACTION_SUBMIT)
                )
        );
        // Doc item node: `SalesForcastMaterialItem`
        docUIModelExtensionBuilder.itemModelClass(SalesForcastMaterialItem.class).itemUiModelClass(SalesForcastMaterialItemUIModel.class)
                .itemAttachmentClass(SalesForcastMaterialItemAttachment.class)
                .convItemUIToMethod(SalesForcastMaterialItemManager.METHOD_ConvUIToSalesForcastMaterialItem).itemToParentDocMethod(SalesForcastMaterialItemManager.METHOD_ConvParentDocToItemUI).
                convItemToUIMethod(SalesForcastMaterialItemManager.METHOD_ConvSalesForcastMaterialItemToUI).itemLogicManager(salesForcastMaterialItemManager);
        return docUIModelExtensionBuilder;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, SalesForcastServiceModel salesForcastServiceModel) {
        if(partyRole == SalesForcastParty.ROLE_SOLD_TO_PARTY){
            return salesForcastServiceModel.getSoldToCustomer();
        }
        if(partyRole == SalesForcastParty.ROLE_SOLD_FROM_PARTY){
            return salesForcastServiceModel.getSoldFromOrg();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(SalesForcastServiceModel salesForcastServiceModel, DocInvolveParty docInvolveParty) {
        SalesForcastParty salesForcastParty = (SalesForcastParty) docInvolveParty;
        if(salesForcastParty.getPartyRole() == SalesForcastParty.ROLE_SOLD_TO_PARTY){
            salesForcastServiceModel.setSoldToCustomer(salesForcastParty);
        }
        if(salesForcastParty.getPartyRole() == SalesForcastParty.ROLE_SOLD_FROM_PARTY){
            salesForcastServiceModel.setSoldFromOrg(salesForcastParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return salesForcastManager.initStatus(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return super.getInvolvePartyMapWrapper(lanCode, SalesForcastUIModel.class, "SalesForcast_InvolveParty");
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(SalesForcast.class, SalesForcastServiceModel.class, SalesForcastActionNode.class,
                SalesForcastMaterialItem.class, SalesForcastMaterialItemServiceModel.class, null);
    }

    @Override
    public void traverseMatItemNode(SalesForcastServiceModel salesForcastServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<SalesForcastMaterialItem> docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(salesForcastServiceModel.getSalesForcastMaterialItemList())) {
            for (SalesForcastMaterialItemServiceModel salesForcastMaterialItemServiceModel :
                    salesForcastServiceModel
                            .getSalesForcastMaterialItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(salesForcastMaterialItemServiceModel.getSalesForcastMaterialItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(SalesForcast.SENAME, SalesForcastUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(SalesForcastMaterialItem.NODENAME, SalesForcastMaterialItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = ISalesI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(SalesForcast.SENAME, basePath + "SalesForcast"));
        propertyMapList.add(new PropertyMap(SalesForcastMaterialItem.NODENAME,
                basePath + "SalesForcastMaterialItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        propertyMapList.add(new PropertyMap("SalesForcastSoldToParty",
                basePath + "SalesForcastSoldToParty"));
        propertyMapList.add(new PropertyMap("SalesForcastSoldFromParty",
                basePath + "SalesForcastSoldFromParty"));
        return propertyMapList;
    }

}