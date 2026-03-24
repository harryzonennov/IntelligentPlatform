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
public class SalesContractSpecifier extends DocumentContentSpecifier<SalesContractServiceModel,
        SalesContract, SalesContractMaterialItem> {

    @Autowired
    protected SalesContractManager salesContractManager;

    @Autowired
    protected SalesContractMaterialItemManager salesContractMaterialItemManager;

    @Autowired
    protected SalesContractIdHelper salesContractIdHelper;

    private final Map<String, Map<Integer, String>> involvePartyLan = new HashMap<>();

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT;
    }

    @Override
    public Integer getDocumentStatus(SalesContract salesContract) {
        return salesContract.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return salesContractManager;
    }

    @Override
    public SalesContract setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        SalesContract salesContract = (SalesContract) serviceEntityTargetStatus.getServiceEntityNode();
        salesContract.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return salesContract;
    }

    @Override
    public Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta >> getDefAllInitConfigureMap() {
        Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta >> initDocConfigureMap = new HashMap<>();
        initDocConfigureMap.put(ServiceEntityStringHelper.getDefModelId(SalesContract.SENAME, SalesContract.NODENAME),
                ServiceCollectionsHelper.asList(
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("soldToCustomer.partyRole",null, SalesContractParty.ROLE_SOLD_TO_PARTY),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("soldToCustomer.refUUID",null,
                                StandardSystemVariableProxy.varLogonOrganizationUUID()),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("soldFromOrg.refContactUUID",null, StandardSystemVariableProxy.varLogonUserUUID()),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("soldFromOrg.partyRole",null,
                                SalesContractParty.ROLE_SOLD_FROM_PARTY)));
        return initDocConfigureMap;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return salesContractIdHelper;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() {
        // Doc root node: `SalesContract`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(SalesContract.class, SalesContractUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(SalesContractManager.METHOD_ConvSalesContractToUI).convDocUIToMethod(SalesContractManager.METHOD_ConvUIToSalesContract);
        // Root flat submodules: `SalesContractAttachment`, `SalesContractActionNode`, `SalesContractParty`
        docUIModelExtensionBuilder.docAttachmentClass(SalesContractAttachment.class).docActionClass(SalesContractActionNode.class).docInvolvePartyClass(SalesContractParty.class);
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara( SalesContractParty.ROLE_SOLD_TO_PARTY,
                                SalesContractParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(SalesContractParty.ROLE_SOLD_FROM_PARTY,
                                SalesContractParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
        docUIModelExtensionBuilder.docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(SalesContractActionNode.DOC_ACTION_APPROVE,
                                SalesContractActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(SalesContractActionNode.DOC_ACTION_INPLAN,
                                SalesContractActionNode.NODEINST_ACTION_INPLAN),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(SalesContractActionNode.DOC_ACTION_DELIVERY_DONE,
                                SalesContractActionNode.NODEINST_ACTION_DELIVERY_DONE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(SalesContractActionNode.DOC_ACTION_SUBMIT,
                                SalesContractActionNode.NODEINST_ACTION_SUBMIT),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(SalesContractActionNode.DOC_ACTION_PROCESS_DONE,
                                SalesContractActionNode.NODEINST_ACTION_PROCESS_DONE)
                )
        );
        // Doc item node: `SalesContractMaterialItem`
        docUIModelExtensionBuilder.itemModelClass(SalesContractMaterialItem.class).itemUiModelClass(SalesContractMaterialItemUIModel.class)
                .itemAttachmentClass(SalesContractMaterialItemAttachment.class)
                .convItemUIToMethod(SalesContractMaterialItemManager.METHOD_ConvUIToSalesContractMaterialItem).itemToParentDocMethod(SalesContractMaterialItemManager.METHOD_ConvParentDocToItemUI).
                convItemToUIMethod(SalesContractMaterialItemManager.METHOD_ConvSalesContractMaterialItemToUI).itemLogicManager(salesContractMaterialItemManager);
        return docUIModelExtensionBuilder;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, SalesContractServiceModel salesContractServiceModel) {
        if(partyRole == SalesContractParty.ROLE_SOLD_TO_PARTY){
            return salesContractServiceModel.getSoldToCustomer();
        }
        if(partyRole == SalesContractParty.ROLE_SOLD_FROM_PARTY){
            return salesContractServiceModel.getSoldFromOrg();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(SalesContractServiceModel salesContractServiceModel, DocInvolveParty docInvolveParty) {
        SalesContractParty salesContractParty = (SalesContractParty) docInvolveParty;
        if(salesContractParty.getPartyRole() == SalesContractParty.ROLE_SOLD_TO_PARTY){
            salesContractServiceModel.setSoldToCustomer(salesContractParty);
        }
        if(salesContractParty.getPartyRole() == SalesContractParty.ROLE_SOLD_FROM_PARTY){
            salesContractServiceModel.setSoldFromOrg(salesContractParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return salesContractManager.initStatus(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return super.getInvolvePartyMapWrapper(lanCode, SalesContractUIModel.class, "SalesContract_InvolveParty");
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(SalesContract.class, SalesContractServiceModel.class, SalesContractActionNode.class,
                SalesContractMaterialItem.class, SalesContractMaterialItemServiceModel.class, null);
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(SalesContract.SENAME, SalesContractUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(SalesContractMaterialItem.NODENAME, SalesContractMaterialItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = ISalesI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(SalesContract.SENAME, basePath + "SalesContract"));
        propertyMapList.add(new PropertyMap(SalesContractMaterialItem.NODENAME,
                basePath + "SalesContractMaterialItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        propertyMapList.add(new PropertyMap("SalesContractSoldToParty",
                basePath + "SalesContractSoldToParty"));
        propertyMapList.add(new PropertyMap("SalesContractSoldFromParty",
                basePath + "SalesContractSoldFromParty"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(SalesContractServiceModel salesContractServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<SalesContractMaterialItem> docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(salesContractServiceModel.getSalesContractMaterialItemList())) {
            for (SalesContractMaterialItemServiceModel salesContractMaterialItemServiceModel :
                    salesContractServiceModel
                            .getSalesContractMaterialItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(salesContractMaterialItemServiceModel.getSalesContractMaterialItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}