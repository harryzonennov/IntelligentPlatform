package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.EmployeeManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeConfigPreCondition;
import com.company.IntelligentPlatform.common.controller.DocInvolvePartyUIModel;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.controller.MaterialInvolvePartyUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.HostCompanyManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.HostCompany;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Common proxy class for involve party
 *
 * @author Zhang, Hang
 */
@Service
public class DocInvolvePartyProxy {

    public static final String METHOD_ConvRefDocumentToInvolvePartyUI = "convRefDocumentToInvolvePartyUI";

    public static final String METHOD_ConvDocInvolvePartyToUI = "convDocInvolvePartyToUI";

    public static final String METHOD_ConvUIToDocInvolveParty = "convUIToDocInvolveParty";

    public static final String METHOD_ConvCustomerToPartyUI = "convCustomerToPartyUI";

    public static final String METHOD_ConvContactToPartyUI = "convContactToPartyUI";

    public static final String METHOD_ConvOrganizationToPartyUI = "convOrganizationToPartyUI";

    public static final String METHOD_ConvEmployeeToPartyUI = "convEmployeeToPartyUI";

    @Autowired
    protected EmployeeManager employeeManager;

    @Autowired
    protected CorporateCustomerManager corporateCustomerManager;

    @Autowired
    protected IndividualCustomerManager individualCustomerManager;

    @Autowired
    protected OrganizationManager organizationManager;

    @Autowired
    protected HostCompanyManager hostCompanyManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected ServiceModuleProxy serviceModuleProxy;

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    protected Logger logger = LoggerFactory.getLogger(DocInvolvePartyProxy.class);

    public static class DocInvolvePartyInputPara extends DocConfigureUIModelInputPara {

        protected int partyRole;

        protected Class<?> targetPartyType;

        protected Class<?> targetContactType;

        public DocInvolvePartyInputPara() {

        }

        public DocInvolvePartyInputPara(String seName, String nodeName, String nodeInstId,
                                        ServiceEntityManager serviceEntityManager, int partyRole,
                                        Class<?> targetPartyType, Class<?> targetContactType) {
            super(seName, nodeName, nodeInstId);
            this.serviceEntityManager = serviceEntityManager;
            this.partyRole = partyRole;
            this.targetPartyType = targetPartyType;
            this.targetContactType = targetContactType;
        }

        public DocInvolvePartyInputPara(String seName, String nodeName, String nodeInstId,
                                        Class<?>[] convToUIMethodParas, String convToUIMethod,
                                        Class<?>[] convUIToMethodParas, String convUIToMethod,
                                        ServiceEntityManager serviceEntityManager, Object logicManager,
                                        Class<?> targetPartyType, Class<?> targetContactType, int partyRole) {
            super(seName, nodeName, nodeInstId, convToUIMethodParas, convToUIMethod, convUIToMethodParas,
                    convUIToMethod, serviceEntityManager, logicManager);
            this.partyRole = partyRole;
            this.targetPartyType = targetPartyType;
            this.targetContactType = targetContactType;
        }

        public int getPartyRole() {
            return partyRole;
        }

        public void setPartyRole(int partyRole) {
            this.partyRole = partyRole;
        }

        public Class<?> getTargetPartyType() {
            return targetPartyType;
        }

        public void setTargetPartyType(Class<?> targetPartyType) {
            this.targetPartyType = targetPartyType;
        }

        public Class<?> getTargetContactType() {
            return targetContactType;
        }

        public void setTargetContactType(Class<?> targetContactType) {
            this.targetContactType = targetContactType;
        }
    }

    public ServiceUIModelExtension genDefServiceUIModelExtension(DocInvolvePartyInputPara docInvolvePartyInputPara)
            throws ServiceEntityConfigureException {
        ServiceUIModelExtension serviceUIModelExtension = new ServiceUIModelExtension();
        serviceUIModelExtension.setChildUIModelExtensions(null);
        serviceUIModelExtension.setUIModelExtensionUnion(genDefUIModelExtensionUnion(docInvolvePartyInputPara));
        return serviceUIModelExtension;
    }

    public List<ServiceUIModelExtensionUnion> genDefUIModelExtensionUnion(
            DocInvolvePartyInputPara docInvolvePartyInputPara) throws ServiceEntityConfigureException {
        List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
        ServiceUIModelExtensionUnion docInvolvePartyExtensionUnion = new ServiceUIModelExtensionUnion();
        docInvolvePartyExtensionUnion.setNodeInstId(docInvolvePartyInputPara.getNodeInstId());
        docInvolvePartyExtensionUnion.setNodeName(docInvolvePartyInputPara.getNodeName());
        UIModelNodeMapConfigure toParentModelNodeMapConfigure = new UIModelNodeMapConfigure();
        toParentModelNodeMapConfigure.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
        toParentModelNodeMapConfigure.setNodeInstID(docInvolvePartyInputPara.getNodeInstId());
        toParentModelNodeMapConfigure.setNodeName(docInvolvePartyInputPara.getNodeName());
        toParentModelNodeMapConfigure.setSeName(docInvolvePartyInputPara.getSeName());
        // Set pre condition
        List<UIModelNodeConfigPreCondition> partyPreConditions = new ArrayList<>();
        UIModelNodeConfigPreCondition partyPreCondtion0 = new UIModelNodeConfigPreCondition();
        partyPreCondtion0.setFieldName(DocInvolveParty.FIELD_PARTYROLE);
        partyPreCondtion0.setFieldValue(docInvolvePartyInputPara.getPartyRole());
        partyPreConditions.add(partyPreCondtion0);
        toParentModelNodeMapConfigure.setPreConditions(partyPreConditions);
        docInvolvePartyExtensionUnion.setToParentModelNodeMapConfigure(toParentModelNodeMapConfigure);

        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        // UI Model Configure of node:[partyMap]
        UIModelNodeMapConfigure partyMap = new UIModelNodeMapConfigure();
        partyMap.setSeName(docInvolvePartyInputPara.getSeName());
        partyMap.setNodeName(docInvolvePartyInputPara.getNodeName());
        partyMap.setNodeInstID(docInvolvePartyInputPara.getNodeInstId());
        partyMap.setHostNodeFlag(true);
        partyMap.setServiceEntityManager(docInvolvePartyInputPara.getServiceEntityManager());
        if(docInvolvePartyInputPara.getLogicManager() != null){
            partyMap.setLogicManager(docInvolvePartyInputPara.getLogicManager());
        } else {
            partyMap.setLogicManager(this);
        }
        if (docInvolvePartyInputPara.getConvUIToMethod() != null) {
            partyMap.setConvToUIMethod(docInvolvePartyInputPara.getConvUIToMethod());
        } else {
            partyMap.setConvToUIMethod(METHOD_ConvDocInvolvePartyToUI);
        }
        if (docInvolvePartyInputPara.getConvToUIMethodParas() != null) {
            partyMap.setConvToUIMethodParas(docInvolvePartyInputPara.getConvToUIMethodParas());
        } else {
            Class<?>[] localConvToUIParas = {DocInvolveParty.class,
                    DocInvolvePartyUIModel.class};
            partyMap.setConvToUIMethodParas(localConvToUIParas);
        }
        if (docInvolvePartyInputPara.getConvUIToMethod() != null) {
            partyMap.setConvUIToMethod(docInvolvePartyInputPara.getConvUIToMethod());
        } else {
            partyMap.setConvUIToMethod(METHOD_ConvUIToDocInvolveParty);
        }
        if (docInvolvePartyInputPara.getConvUIToMethodParas() != null) {
            partyMap.setConvUIToMethodParas(docInvolvePartyInputPara.getConvUIToMethodParas());
        } else {
            Class<?>[] localConvUIToParas = {DocInvolvePartyUIModel.class, DocInvolveParty.class};
            partyMap.setConvUIToMethodParas(localConvUIToParas);
        }
        uiModelNodeMapList.add(partyMap);
        // UI Model Configure of node:[party Contact Map]
        Class<?>[] targetPartyConvToUIParas =
                {docInvolvePartyInputPara.getTargetPartyType(), DocInvolvePartyUIModel.class};
        uiModelNodeMapList.addAll(getDefTargetPartyMapConfigureList(docInvolvePartyInputPara.getNodeInstId(), null,
                targetPartyConvToUIParas));
        Class<?>[] targetContactConvToUIParas =
                {docInvolvePartyInputPara.getTargetContactType(), DocInvolvePartyUIModel.class};
        uiModelNodeMapList.addAll(getDefTargetContactMapConfigureList(docInvolvePartyInputPara.getNodeInstId(), null,
                targetContactConvToUIParas));
        uiModelNodeMapList.addAll(getDefInvolveDocMapConfigureList(docInvolvePartyInputPara));

        docInvolvePartyExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
        resultList.add(docInvolvePartyExtensionUnion);
        return resultList;
    }

    public List<ServiceEntityNode> getDocInvolvePartyListWrapper(int partyRole, String nodeName,
                                                                 ServiceEntityManager serviceEntityManager,
                                                                 String parentNodeUUID, String client)
            throws ServiceEntityConfigureException {
        ServiceBasicKeyStructure key1 =
                new ServiceBasicKeyStructure(parentNodeUUID, IServiceEntityNodeFieldConstant.PARENTNODEUUID);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(partyRole, DocInvolveParty.FIELD_PARTYROLE);
        List<ServiceBasicKeyStructure> keyList = ServiceCollectionsHelper.asList(key1, key2);
        return serviceEntityManager.getEntityNodeListByKeyList(keyList, nodeName, client, null);
    }

    public DocInvolveParty getDocInvolvePartyWrapper(int partyRole, String nodeName,
                                                     ServiceEntityManager serviceEntityManager, String parentNodeUUID,
                                                     String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> allDocInvolvePartyList =
                this.getDocInvolvePartyListWrapper(partyRole, nodeName, serviceEntityManager, parentNodeUUID, client);
        if (ServiceCollectionsHelper.checkNullList(allDocInvolvePartyList)) {
            return null;
        }
        return (DocInvolveParty) allDocInvolvePartyList.get(0);
    }

    public static void copyAccountToInvolveParty(Account corporateAccount, DocInvolveParty docInvolveParty) {
        docInvolveParty.setRefUUID(corporateAccount.getUuid());
        docInvolveParty.setName(corporateAccount.getName());
        docInvolveParty.setId(corporateAccount.getId());
        docInvolveParty.setEmail(corporateAccount.getEmail());
        docInvolveParty.setAddress(corporateAccount.getAddress());
        docInvolveParty.setBankAccount(corporateAccount.getBankAccount());
        docInvolveParty.setCityName(corporateAccount.getCityName());
        docInvolveParty.setDepositBank(corporateAccount.getDepositBank());
        docInvolveParty.setFax(corporateAccount.getFax());
        docInvolveParty.setHouseNumber(corporateAccount.getHouseNumber());
        docInvolveParty.setPostcode(corporateAccount.getPostcode());
        docInvolveParty.setStreetName(corporateAccount.getStreetName());
        docInvolveParty.setSubArea(corporateAccount.getSubArea());
        docInvolveParty.setTaxNumber(corporateAccount.getTaxNumber());
    }

    public DocInvolveParty initGenInvolveParty(ServiceEntityNode parentNode, int partyRole, String nodeName,
                                               ServiceEntityManager serviceEntityManager)
            throws ServiceEntityConfigureException {
        DocInvolveParty docInvolveParty =
                getDocInvolvePartyWrapper(partyRole, nodeName, serviceEntityManager, parentNode.getUuid(),
                        parentNode.getClient());
        if (docInvolveParty == null) {
            docInvolveParty = (DocInvolveParty) serviceEntityManager.newEntityNode(parentNode, nodeName);
            docInvolveParty.setPartyRole(partyRole);
        }
        return docInvolveParty;
    }

    /**
     * Generate or update involve party by Customer & Contact information
     *
     * @param partyRole
     * @param customerAccount
     * @param contact
     * @return
     * @throws ServiceEntityConfigureException
     */
    public DocInvolveParty genInvolveParty(ServiceEntityNode parentNode, int partyRole, String nodeName,
                                           ServiceEntityManager serviceEntityManager, DocMatItemNode docMatItemNode,
                                           Account customerAccount, Account contact)
            throws ServiceEntityConfigureException {
        DocInvolveParty docInvolveParty = initGenInvolveParty(parentNode, partyRole, nodeName, serviceEntityManager);
        if (customerAccount != null) {
            DocInvolvePartyProxy.copyAccountToInvolveParty(customerAccount, docInvolveParty);
        }
        if (docMatItemNode != null) {
            docInvolveParty.setRefDocumentDate(new Date());
            docInvolveParty.setRefDocumentType(docMatItemNode.getHomeDocumentType());
            docInvolveParty.setRefDocMatItemUUID(docMatItemNode.getUuid());
        }
        if (contact != null) {
            docInvolveParty.setContactId(contact.getId());
            docInvolveParty.setContactName(contact.getName());
            docInvolveParty.setContactMobile(contact.getMobile());
            docInvolveParty.setRefContactUUID(contact.getUuid());
        }
        return docInvolveParty;
    }

    /**
     * Generate or update involve party by Customer & Contact information
     *
     * @param partyRole
     * @param organization
     * @param contactEmployee
     * @return
     * @throws ServiceEntityConfigureException
     */
    public DocInvolveParty genInvolveOrgParty(ServiceEntityNode parentNode, int partyRole, String nodeName,
                                              DocMatItemNode docMatItemNode, ServiceEntityManager serviceEntityManager,
                                              Organization organization, ServiceEntityNode contactEmployee)
            throws ServiceEntityConfigureException {
        DocInvolveParty docInvolveParty =
                getDocInvolvePartyWrapper(partyRole, nodeName, serviceEntityManager, parentNode.getUuid(),
                        parentNode.getClient());
        if (docInvolveParty == null) {
            docInvolveParty = (DocInvolveParty) serviceEntityManager.newEntityNode(parentNode, nodeName);
            docInvolveParty.setPartyRole(partyRole);
        }
        if (organization != null) {
            DocInvolvePartyProxy.copyAccountToInvolveParty(organization, docInvolveParty);
        }
        if (docMatItemNode != null) {
            docInvolveParty.setRefDocMatItemUUID(docMatItemNode.getUuid());
            docInvolveParty.setRefDocumentType(docMatItemNode.getHomeDocumentType());
            docInvolveParty.setRefDocumentDate(new Date());
        }
        if (contactEmployee != null) {
            docInvolveParty.setContactId(contactEmployee.getId());
            docInvolveParty.setContactName(contactEmployee.getName());
            if (contactEmployee instanceof Employee) {
                Employee employee = (Employee) contactEmployee;
                docInvolveParty.setContactMobile(employee.getMobile());
            }
            docInvolveParty.setRefContactUUID(contactEmployee.getUuid());
        }
        return docInvolveParty;
    }

    /**
     * Logic to generate Configure map for target Party
     *
     * @param baseNodeId
     * @param convToUIMethod
     * @param convToUIMethodParas
     * @return
     */
    public List<UIModelNodeMapConfigure> getDefTargetPartyMapConfigureList(String baseNodeId, String convToUIMethod,
                                                                           Class<?>[] convToUIMethodParas) {
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        // UI Model Configure of node:[TargetParty]
        UIModelNodeMapConfigure targetPartyMap = new UIModelNodeMapConfigure();
        targetPartyMap.setSeName(calculateTargetPartySEName(convToUIMethodParas));
        targetPartyMap.setNodeName(calculateTargetPartyNodeName(convToUIMethodParas));
        targetPartyMap.setNodeInstID("TargetParty" + baseNodeId);
        targetPartyMap.setBaseNodeInstID(baseNodeId);
        targetPartyMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
        targetPartyMap.setServiceEntityManager(calculateTargetPartyManager(convToUIMethodParas));
        if (convToUIMethodParas == null) {
            Class<?>[] localConvToUIParas = {CorporateCustomer.class, DocInvolvePartyUIModel.class};
            targetPartyMap.setConvToUIMethodParas(localConvToUIParas);
        } else {
            targetPartyMap.setConvToUIMethodParas(convToUIMethodParas);
        }
        if (convToUIMethod == null) {
            targetPartyMap.setLogicManager(this);
            targetPartyMap.setConvToUIMethod(calculateTargetPartyConvToUI(convToUIMethodParas));
        } else {
            targetPartyMap.setConvToUIMethod(convToUIMethod);
        }
        uiModelNodeMapList.add(targetPartyMap);
        return uiModelNodeMapList;
    }

    /**
     * Logic to generate Configure map for target Contact
     *
     * @param baseNodeId
     * @param convToUIMethod
     * @param convToUIMethodParas
     * @return
     */
    public List<UIModelNodeMapConfigure> getDefTargetContactMapConfigureList(String baseNodeId, String convToUIMethod,
                                                                             Class<?>[] convToUIMethodParas) {
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        // UI Model Configure of node:[TargetContact]
        UIModelNodeMapConfigure targetContactMap = new UIModelNodeMapConfigure();
        targetContactMap.setSeName(calculateTargetContactSEName(convToUIMethodParas));
        targetContactMap.setNodeName(calculateTargetContactNodeName(convToUIMethodParas));
        targetContactMap.setNodeInstID("TargetContact" + baseNodeId);
        targetContactMap.setBaseNodeInstID(baseNodeId);
        targetContactMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
        List<SearchConfigConnectCondition> targetContactConditionList = new ArrayList<>();
        SearchConfigConnectCondition contactCondition0 = new SearchConfigConnectCondition();
        contactCondition0.setSourceFieldName("refContactUUID");
        contactCondition0.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
        targetContactConditionList.add(contactCondition0);
        targetContactMap.setConnectionConditions(targetContactConditionList);
        targetContactMap.setServiceEntityManager(calculateTargetContactManager(convToUIMethodParas));
        if (convToUIMethodParas == null) {
            Class<?>[] localConvToUIParas = {IndividualCustomer.class, DocInvolvePartyUIModel.class};
            targetContactMap.setConvToUIMethodParas(localConvToUIParas);
        } else {
            targetContactMap.setConvToUIMethodParas(convToUIMethodParas);
        }
        if (convToUIMethod == null) {
            targetContactMap.setLogicManager(this);
            targetContactMap.setConvToUIMethod(calculateTargetContactConvToUI(convToUIMethodParas));
        } else {
            targetContactMap.setConvToUIMethod(convToUIMethod);
        }
        uiModelNodeMapList.add(targetContactMap);
        return uiModelNodeMapList;
    }

    /**
     * Utility Method to Copy Involve Party from source to target
     */
    public void copyDocInvolveParty(DocInvolveParty source, DocInvolveParty target) {
        if (source == null || target == null) {
            return;
        }
        target.setRefContactUUID(source.getRefContactUUID());
        target.setRefUUID(source.getRefUUID());
        target.setEmail(source.getEmail());
        target.setFax(source.getFax());
        target.setTelephone(source.getTelephone());
        target.setTaxNumber(source.getTaxNumber());
        target.setBankAccount(source.getBankAccount());
        target.setDepositBank(source.getDepositBank());
        target.setCityName(source.getCityName());
        target.setTownZone(source.getTownZone());
        target.setSubArea(source.getSubArea());
        target.setStreetName(source.getStreetName());
        target.setHouseNumber(source.getHouseNumber());
        target.setContactName(source.getContactName());
        target.setContactId(source.getContactId());
        target.setContactMobile(source.getContactMobile());
        target.setAddress(source.getAddress());
        target.setRefDocumentDate(source.getRefDocumentDate());
        target.setRefDocumentType(source.getRefDocumentType());
        target.setRefDocMatItemUUID(source.getRefDocMatItemUUID());
    }

    /**
     * Utility Method to Convert
     */
    public void convDocInvolvePartyToUI(DocInvolveParty docInvolveParty,
                                        DocInvolvePartyUIModel docInvolvePartyUIModel) {
        DocFlowProxy.convServiceEntityNodeToUIModel(docInvolveParty, docInvolvePartyUIModel);
        if (!ServiceEntityStringHelper.checkNullString(docInvolveParty.getName())) {
            docInvolvePartyUIModel.setPartyName(docInvolveParty.getName());
        }
        docInvolvePartyUIModel.setPartyRole(docInvolveParty.getPartyRole());
        docInvolvePartyUIModel.setRefContactUUID(docInvolveParty.getRefContactUUID());
        docInvolvePartyUIModel.setRefUUID(docInvolveParty.getRefUUID());
        docInvolvePartyUIModel.setEmail(docInvolveParty.getEmail());
        docInvolvePartyUIModel.setFax(docInvolveParty.getFax());
        docInvolvePartyUIModel.setTelephone(docInvolveParty.getTelephone());
        docInvolvePartyUIModel.setTaxNumber(docInvolveParty.getTaxNumber());
        docInvolvePartyUIModel.setBankAccount(docInvolveParty.getBankAccount());
        docInvolvePartyUIModel.setDepositBank(docInvolveParty.getDepositBank());
        docInvolvePartyUIModel.setCityName(docInvolveParty.getCityName());
        docInvolvePartyUIModel.setTownZone(docInvolveParty.getTownZone());
        docInvolvePartyUIModel.setSubArea(docInvolveParty.getSubArea());
        docInvolvePartyUIModel.setStreetName(docInvolveParty.getStreetName());
        docInvolvePartyUIModel.setHouseNumber(docInvolveParty.getHouseNumber());
        docInvolvePartyUIModel.setContactName(docInvolveParty.getContactName());
        docInvolvePartyUIModel.setContactId(docInvolveParty.getContactId());
        docInvolvePartyUIModel.setContactMobile(docInvolveParty.getContactMobile());
        docInvolvePartyUIModel.setAddress(docInvolveParty.getAddress());
        if (docInvolveParty.getRefDocumentDate() != null) {
            docInvolvePartyUIModel.setCreatedDate(
                    DefaultDateFormatConstant.DATE_FORMAT.format(docInvolveParty.getRefDocumentDate()));
        }
        docInvolvePartyUIModel.setRefDocMatItemUUID(docInvolveParty.getRefDocMatItemUUID());
        docInvolvePartyUIModel.setRefDocumentType(docInvolveParty.getRefDocumentType());
    }

    /**
     * Utility Method to Convert
     */
    public void convUIToDocInvolveParty(DocInvolvePartyUIModel docInvolvePartyUIModel, DocInvolveParty rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(docInvolvePartyUIModel, rawEntity);
        if (docInvolvePartyUIModel.getPartyRole() > 0) {
            rawEntity.setPartyRole(docInvolvePartyUIModel.getPartyRole());
        }
        if (!ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getPartyName())) {
            rawEntity.setName(docInvolvePartyUIModel.getPartyName());
        }
        rawEntity.setRefContactUUID(docInvolvePartyUIModel.getRefContactUUID());
        rawEntity.setRefUUID(docInvolvePartyUIModel.getRefUUID());
        rawEntity.setEmail(docInvolvePartyUIModel.getEmail());
        rawEntity.setTelephone(docInvolvePartyUIModel.getTelephone());
        rawEntity.setFax(docInvolvePartyUIModel.getFax());
        rawEntity.setTaxNumber(docInvolvePartyUIModel.getTaxNumber());
        rawEntity.setBankAccount(docInvolvePartyUIModel.getBankAccount());
        rawEntity.setDepositBank(docInvolvePartyUIModel.getDepositBank());
        rawEntity.setCityName(docInvolvePartyUIModel.getCityName());
        rawEntity.setTownZone(docInvolvePartyUIModel.getTownZone());
        rawEntity.setSubArea(docInvolvePartyUIModel.getSubArea());
        rawEntity.setStreetName(docInvolvePartyUIModel.getStreetName());
        rawEntity.setHouseNumber(docInvolvePartyUIModel.getHouseNumber());
        rawEntity.setContactName(docInvolvePartyUIModel.getContactName());
        rawEntity.setContactId(docInvolvePartyUIModel.getContactId());
        rawEntity.setContactMobile(docInvolvePartyUIModel.getContactMobile());
        rawEntity.setAddress(docInvolvePartyUIModel.getAddress());
    }

    /**
     * Utility Method to Convert Corporate Customer to Party UI
     */
    public void convCustomerToPartyUI(CorporateCustomer corporateCustomer,
                                      DocInvolvePartyUIModel docInvolvePartyUIModel) {
        if (corporateCustomer != null) {
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getPartyName())) {
                docInvolvePartyUIModel.setPartyName(corporateCustomer.getName());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getPartyId())) {
                docInvolvePartyUIModel.setPartyId(corporateCustomer.getId());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getEmail())) {
                docInvolvePartyUIModel.setEmail(corporateCustomer.getEmail());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getEmail())) {
                docInvolvePartyUIModel.setFax(corporateCustomer.getFax());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getTaxNumber())) {
                docInvolvePartyUIModel.setTaxNumber(corporateCustomer.getTaxNumber());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getBankAccount())) {
                docInvolvePartyUIModel.setBankAccount(corporateCustomer.getBankAccount());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getCityName())) {
                docInvolvePartyUIModel.setCityName(corporateCustomer.getCityName());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getTelephone())) {
                docInvolvePartyUIModel.setTelephone(corporateCustomer.getTelephone());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getDepositBank())) {
                docInvolvePartyUIModel.setDepositBank(corporateCustomer.getDepositBank());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getTownZone())) {
                docInvolvePartyUIModel.setTownZone(corporateCustomer.getTownZone());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getSubArea())) {
                docInvolvePartyUIModel.setSubArea(corporateCustomer.getSubArea());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getStreetName())) {
                docInvolvePartyUIModel.setStreetName(corporateCustomer.getStreetName());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getHouseNumber())) {
                docInvolvePartyUIModel.setHouseNumber(corporateCustomer.getHouseNumber());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getAddress())) {
                docInvolvePartyUIModel.setAddress(corporateCustomer.getAddress());
            }
        }
    }

    /**
     * Utility Method to Convert Corporate Customer to Party UI
     */
    public void convOrganizationToPartyUI(Organization organization, DocInvolvePartyUIModel docInvolvePartyUIModel) {
        if (organization != null) {
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getPartyName())) {
                docInvolvePartyUIModel.setPartyName(organization.getName());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getPartyId())) {
                docInvolvePartyUIModel.setPartyId(organization.getId());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getEmail())) {
                docInvolvePartyUIModel.setEmail(organization.getEmail());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getEmail())) {
                docInvolvePartyUIModel.setFax(organization.getFax());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getTaxNumber())) {
                docInvolvePartyUIModel.setTaxNumber(organization.getTaxNumber());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getBankAccount())) {
                docInvolvePartyUIModel.setBankAccount(organization.getBankAccount());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getCityName())) {
                docInvolvePartyUIModel.setCityName(organization.getCityName());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getTownZone())) {
                docInvolvePartyUIModel.setTownZone(organization.getTownZone());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getSubArea())) {
                docInvolvePartyUIModel.setSubArea(organization.getSubArea());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getStreetName())) {
                docInvolvePartyUIModel.setStreetName(organization.getStreetName());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getHouseNumber())) {
                docInvolvePartyUIModel.setHouseNumber(organization.getHouseNumber());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getAddress())) {
                docInvolvePartyUIModel.setAddress(organization.getAddress());
            }
        }
    }

    /**
     * Utility Method to Convert Corporate Customer to Party UI
     */
    public void convContactToPartyUI(IndividualCustomer individualCustomer,
                                     DocInvolvePartyUIModel docInvolvePartyUIModel) {
        if (individualCustomer != null) {
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getContactName())) {
                docInvolvePartyUIModel.setContactName(individualCustomer.getName());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getContactId())) {
                docInvolvePartyUIModel.setContactId(individualCustomer.getId());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getContactMobile())) {
                docInvolvePartyUIModel.setContactMobile(individualCustomer.getMobile());
            }
        }
    }

    /**
     * Utility Method to Convert Corporate Customer to Party UI
     */
    public void convEmployeeToPartyUI(Employee employee, DocInvolvePartyUIModel docInvolvePartyUIModel) {
        if (employee != null) {
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getPartyName())) {
                docInvolvePartyUIModel.setContactName(employee.getName());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getContactId())) {
                docInvolvePartyUIModel.setContactId(employee.getId());
            }
            // Avoid over-written the manual value
            if (ServiceEntityStringHelper.checkNullString(docInvolvePartyUIModel.getContactMobile())) {
                docInvolvePartyUIModel.setContactMobile(employee.getMobile());
            }
        }
    }

    /**
     * Core Logic to calculate target party
     *
     * @param convToUIMethodParas
     * @return
     */
    private Class<?> calculateTargetContactClass(Class<?>[] convToUIMethodParas) {
        if (convToUIMethodParas == null || convToUIMethodParas.length == 0) {
            return IndividualCustomer.class;
        }
        if (convToUIMethodParas[0] != null) {
            if (Employee.class.getSimpleName().equals(convToUIMethodParas[0].getSimpleName())) {
                return Employee.class;
            }
        }
        return IndividualCustomer.class;
    }

    private String calculateTargetContactSEName(Class<?>[] convToUIMethodParas) {
        Class<?> targetContactClass = calculateTargetContactClass(convToUIMethodParas);
        if (targetContactClass == null) {
            return IndividualCustomer.SENAME;
        }
        if (IndividualCustomer.class.getSimpleName().equals(targetContactClass.getSimpleName())) {
            return IndividualCustomer.SENAME;
        }
        if (Employee.class.getSimpleName().equals(targetContactClass.getSimpleName())) {
            return Employee.SENAME;
        }
        return IndividualCustomer.SENAME;
    }

    private String calculateTargetContactNodeName(Class<?>[] convToUIMethodParas) {
        Class<?> targetContactClass = calculateTargetContactClass(convToUIMethodParas);
        if (targetContactClass == null) {
            return IndividualCustomer.NODENAME;
        }
        if (IndividualCustomer.class.getSimpleName().equals(targetContactClass.getSimpleName())) {
            return IndividualCustomer.NODENAME;
        }
        if (Employee.class.getSimpleName().equals(targetContactClass.getSimpleName())) {
            return Employee.NODENAME;
        }
        return IndividualCustomer.NODENAME;
    }

    private ServiceEntityManager calculateTargetContactManager(Class<?>[] convToUIMethodParas) {
        Class<?> targetContactClass = calculateTargetContactClass(convToUIMethodParas);
        if (targetContactClass == null) {
            return individualCustomerManager;
        }
        if (IndividualCustomer.class.getSimpleName().equals(targetContactClass.getSimpleName())) {
            return individualCustomerManager;
        }
        if (Employee.class.getSimpleName().equals(targetContactClass.getSimpleName())) {
            return employeeManager;
        }

        return individualCustomerManager;
    }

    private String calculateTargetContactConvToUI(Class<?>[] convToUIMethodParas) {
        Class<?> targetContactClass = calculateTargetContactClass(convToUIMethodParas);
        if (targetContactClass == null) {
            return DocInvolvePartyProxy.METHOD_ConvContactToPartyUI;
        }
        if (IndividualCustomer.class.getSimpleName().equals(targetContactClass.getSimpleName())) {
            return DocInvolvePartyProxy.METHOD_ConvContactToPartyUI;
        }
        if (Employee.class.getSimpleName().equals(targetContactClass.getSimpleName())) {
            return DocInvolvePartyProxy.METHOD_ConvEmployeeToPartyUI;
        }
        return DocInvolvePartyProxy.METHOD_ConvContactToPartyUI;
    }

    /**
     * Core Logic to calculate target party Class Definitaion by reflective method paras
     *
     * @param convToUIMethodParas
     * @return
     */
    private Class<?> calculateTargetPartyClass(Class<?>[] convToUIMethodParas) {
        // By default: CorporateCustomer
        if (convToUIMethodParas == null || convToUIMethodParas.length == 0) {
            return CorporateCustomer.class;
        }
        if (convToUIMethodParas[0] != null) {
            if (Organization.class.getSimpleName().equals(convToUIMethodParas[0].getSimpleName())) {
                return Organization.class;
            }
            if (HostCompany.class.getSimpleName().equals(convToUIMethodParas[0].getSimpleName())) {
                return HostCompany.class;
            }
        }
        return CorporateCustomer.class;
    }

    private String calculateTargetPartySEName(Class<?>[] convToUIMethodParas) {
        Class<?> targetPartyClass = calculateTargetPartyClass(convToUIMethodParas);
        if (targetPartyClass == null) {
            return CorporateCustomer.SENAME;
        }
        if (CorporateCustomer.class.getSimpleName().equals(targetPartyClass.getSimpleName())) {
            return CorporateCustomer.SENAME;
        }
        if (Organization.class.getSimpleName().equals(targetPartyClass.getSimpleName())) {
            return Organization.SENAME;
        }
        if (HostCompany.class.getSimpleName().equals(targetPartyClass.getSimpleName())) {
            return HostCompany.SENAME;
        }
        return CorporateCustomer.SENAME;
    }

    private String calculateTargetPartyNodeName(Class<?>[] convToUIMethodParas) {
        Class<?> targetPartyClass = calculateTargetPartyClass(convToUIMethodParas);
        if (targetPartyClass == null) {
            return CorporateCustomer.NODENAME;
        }
        if (CorporateCustomer.class.getSimpleName().equals(targetPartyClass.getSimpleName())) {
            return CorporateCustomer.NODENAME;
        }
        if (Organization.class.getSimpleName().equals(targetPartyClass.getSimpleName())) {
            return Organization.NODENAME;
        }
        if (HostCompany.class.getSimpleName().equals(targetPartyClass.getSimpleName())) {
            return HostCompany.NODENAME;
        }
        return CorporateCustomer.NODENAME;
    }

    private ServiceEntityManager calculateTargetPartyManager(Class<?>[] convToUIMethodParas) {
        Class<?> targetPartyClass = calculateTargetPartyClass(convToUIMethodParas);
        if (targetPartyClass == null) {
            return corporateCustomerManager;
        }
        if (CorporateCustomer.class.getSimpleName().equals(targetPartyClass.getSimpleName())) {
            return corporateCustomerManager;
        }
        if (Organization.class.getSimpleName().equals(targetPartyClass.getSimpleName())) {
            return organizationManager;
        }
        if (HostCompany.class.getSimpleName().equals(targetPartyClass.getSimpleName())) {
            return hostCompanyManager;
        }
        return corporateCustomerManager;
    }

    private String calculateTargetPartyConvToUI(Class<?>[] convToUIMethodParas) {
        Class<?> targetPartyClass = calculateTargetPartyClass(convToUIMethodParas);
        if (targetPartyClass == null) {
            return DocInvolvePartyProxy.METHOD_ConvCustomerToPartyUI;
        }
        if (CorporateCustomer.class.getSimpleName().equals(targetPartyClass.getSimpleName())) {
            return DocInvolvePartyProxy.METHOD_ConvCustomerToPartyUI;
        }
        if (Organization.class.getSimpleName().equals(targetPartyClass.getSimpleName())) {
            return DocInvolvePartyProxy.METHOD_ConvOrganizationToPartyUI;
        }
        if (HostCompany.class.getSimpleName().equals(targetPartyClass.getSimpleName())) {
            return DocInvolvePartyProxy.METHOD_ConvOrganizationToPartyUI;
        }
        return DocInvolvePartyProxy.METHOD_ConvCustomerToPartyUI;
    }

    /**
     * Logic to generate Configure map for target Party
     *
     * @param docInvolvePartyInputPara
     * @return
     */
    public List<UIModelNodeMapConfigure> getDefInvolveDocMapConfigureList(
            DocInvolvePartyProxy.DocInvolvePartyInputPara docInvolvePartyInputPara)
            throws ServiceEntityConfigureException {
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        Class<?>[] involveDocItemToUIParas = {DocumentContent.class, MaterialInvolvePartyUIModel.class};
        String convDocumentToUIMethod = METHOD_ConvRefDocumentToInvolvePartyUI;
        DocFlowProxy.SimpleDocConfigurePara simpleDocConfigurePara =
                new DocFlowProxy.SimpleDocConfigurePara(docInvolvePartyInputPara.getNodeName(), convDocumentToUIMethod,
                        involveDocItemToUIParas, docInvolvePartyInputPara.getServiceEntityManager());
        simpleDocConfigurePara.setDocMatItemGetCallback(rawSENode -> {
            InvolvePartyTemplate involvePartyTemplate = (InvolvePartyTemplate) rawSENode;
            int refDocumentType = involvePartyTemplate.getRefDocumentType();
            ServiceEntityNode specDocMatItemNode =
                    docFlowProxy.getDefDocItemNode(refDocumentType, involvePartyTemplate.getRefDocMatItemUUID(),
                            involvePartyTemplate.getClient());
            return specDocMatItemNode;
        });
        uiModelNodeMapList.addAll(docFlowProxy.getSpecNodeMapConfigureList(simpleDocConfigurePara));
        return uiModelNodeMapList;
    }

    public void convRefDocumentToInvolvePartyUI(DocumentContent documentContent,
                                                MaterialInvolvePartyUIModel materialInvolvePartyUIModel,
                                                LogonInfo logonInfo) {
        if (materialInvolvePartyUIModel != null) {
            materialInvolvePartyUIModel.setRefDocumentId(documentContent.getId());
            materialInvolvePartyUIModel.setRefDocumentName(documentContent.getName());
            materialInvolvePartyUIModel.setRefDocumentStatus(documentContent.getStatus());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = null;
                try {
                    statusMap = docFlowProxy.getStatusMap(materialInvolvePartyUIModel.getRefDocumentType(),
                            logonInfo.getLanguageCode());
                    if (statusMap != null) {
                        materialInvolvePartyUIModel.setRefDocumentStatusValue(
                                statusMap.get(documentContent.getStatus()));
                    }
                } catch (ServiceEntityInstallationException | DocActionException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "status"));
                }
            }
        }
    }

    public void genConvertCrossDocParty(DocumentContentSpecifier sourceContentSpecifier,
                                        ServiceModule sourceServiceModule,
                                        DocumentContentSpecifier targetContentSpecifier,
                                        ServiceModule targetServiceModule, boolean updateFlag,
                                        List<CrossCopyPartyConversionConfig> crossDocPartyConversionMapList)
            throws ServiceEntityConfigureException {
        ServiceEntityNode targetDocument = targetContentSpecifier.getCoreEntity(targetServiceModule);
        ServiceEntityManager targetDocumentManager = targetContentSpecifier.getDocumentManager();
        if (ServiceCollectionsHelper.checkNullList(crossDocPartyConversionMapList)) {
            return;
        }
        String targetNodeName = ServiceModuleProxy.getNodeNameByFieldDocNodeCategory(targetServiceModule.getClass(),
                IServiceModuleFieldConfig.DOCNODE_CATE_PARTY);
        if(!ServiceEntityStringHelper.checkNullString(targetNodeName)){
            for (CrossCopyPartyConversionConfig crossDocPartyConversionMap : crossDocPartyConversionMapList) {
                DocInvolveParty sourceInvolveParty =
                        sourceContentSpecifier.getDocInvolveParty(crossDocPartyConversionMap.getSourcePartyRole(),
                                sourceServiceModule);
                if (sourceInvolveParty != null) {
                    DocInvolveParty targetInvolveParty =
                            getDocInvolvePartyWrapper(crossDocPartyConversionMap.getTargetPartyRole(), targetNodeName
                                    , targetContentSpecifier.getDocumentManager(),
                                    targetDocument.getUuid(), targetDocument.getClient());
                    if(targetInvolveParty == null){
                        targetInvolveParty =
                                (DocInvolveParty) targetDocumentManager.newEntityNode(targetDocument, targetNodeName);
                    } else {
                        if(!updateFlag){
                            return;
                        }
                    }
                    targetInvolveParty.setPartyRole(crossDocPartyConversionMap.getTargetPartyRole());
                    copyDocInvolveParty(sourceInvolveParty, targetInvolveParty);
                    targetContentSpecifier.setDocInvolveParty(targetServiceModule, targetInvolveParty);
                }
            }
        }
    }

    public DocInvolveParty updateDocInvolvePartyWrapper(DocInvolveParty docInvolveParty, String nodeName,
                                                        ServiceEntityManager serviceEntityManager,
                                                        String parentNodeUUID, LogonInfo logonInfo)
            throws ServiceEntityConfigureException {
        DocInvolveParty existDocInvolveParty =
                getDocInvolvePartyWrapper(docInvolveParty.getPartyRole(), nodeName, serviceEntityManager,
                        parentNodeUUID, logonInfo.getClient());
        if (existDocInvolveParty == null) {
            serviceEntityManager.insertSENode(existDocInvolveParty, logonInfo.getRefUserUUID(),
                    logonInfo.getResOrgUUID());
        } else {
            copyDocInvolveParty(docInvolveParty, existDocInvolveParty);
            serviceEntityManager.updateSENode(docInvolveParty, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
        }
        return docInvolveParty;
    }

    public boolean checkTargetDocPartyNull(DocumentContentSpecifier sourceContentSpecifier,
                                           ServiceModule sourceServiceModule, int partyRole){
        DocInvolveParty sourceInvolveParty =
                sourceContentSpecifier.getDocInvolveParty(partyRole,
                        sourceServiceModule);
        return sourceInvolveParty == null;
    }

    /**
     * Generate target doc party information from source mat item
     * @param sourceContentSpecifier
     * @param targetContentSpecifier
     * @param targetServiceModule
     * @param crossDocPartyConversionMapList
     * @throws ServiceEntityConfigureException
     */
    public void genConvertCrossPartyFromItem(DocumentContentSpecifier sourceContentSpecifier,
                                         DocumentContentSpecifier targetContentSpecifier,
                                         ServiceModule sourceItemServiceModule,
                                         ServiceModule targetServiceModule,
                                         List<CrossCopyPartyConversionConfig> crossDocPartyConversionMapList)
            throws ServiceEntityConfigureException {
        ServiceEntityNode targetDocument = targetContentSpecifier.getCoreEntity(targetServiceModule);
        ServiceEntityManager targetDocumentManager = targetContentSpecifier.getDocumentManager();
        if (ServiceCollectionsHelper.checkNullList(crossDocPartyConversionMapList)) {
            return;
        }
        String targetNodeName = ServiceModuleProxy.getNodeNameByFieldDocNodeCategory(targetServiceModule.getClass(),
                IServiceModuleFieldConfig.DOCNODE_CATE_PARTY);
        if(!ServiceEntityStringHelper.checkNullString(targetNodeName)){
            for (CrossCopyPartyConversionConfig crossDocPartyConversionMap : crossDocPartyConversionMapList) {
                DocInvolveParty sourceInvolveParty =
                        sourceContentSpecifier.getDocItemInvolveParty(crossDocPartyConversionMap.getSourcePartyRole(),
                                sourceItemServiceModule);
                if (sourceInvolveParty != null) {
                    DocInvolveParty targetInvolveParty =
                            (DocInvolveParty) targetDocumentManager.newEntityNode(targetDocument, targetNodeName);
                    targetInvolveParty.setPartyRole(crossDocPartyConversionMap.getTargetPartyRole());
                    copyDocInvolveParty(sourceInvolveParty, targetInvolveParty);
                    targetContentSpecifier.setDocInvolveParty(targetServiceModule, targetInvolveParty);
                }
            }
        }
    }

    public void genConvertCrossItemParty(DocumentContentSpecifier sourceContentSpecifier,
                                        ServiceModule sourceItemServiceModule,
                                        DocumentContentSpecifier targetContentSpecifier,
                                        ServiceModule targetServiceModule,
                                        DocMatItemNode targetDocMatItem,
                                        ServiceModule targetItemServiceModule, boolean updateFlag,
                                        List<CrossCopyPartyConversionConfig> crossDocPartyConversionMapList)
            throws ServiceEntityConfigureException {
        ServiceEntityManager targetDocumentManager = targetContentSpecifier.getDocumentManager();
        if (ServiceCollectionsHelper.checkNullList(crossDocPartyConversionMapList)) {
            return;
        }
        String targetNodeName = ServiceModuleProxy.getNodeNameByFieldDocNodeCategory(targetItemServiceModule.getClass(),
                IServiceModuleFieldConfig.DOCNODE_CATE_PARTY);
        if(!ServiceEntityStringHelper.checkNullString(targetNodeName)){
            for (CrossCopyPartyConversionConfig crossDocPartyConversionMap : crossDocPartyConversionMapList) {
                DocInvolveParty sourceInvolveParty =
                        sourceContentSpecifier.getDocItemInvolveParty(crossDocPartyConversionMap.getSourcePartyRole(),
                                sourceItemServiceModule);
                if (sourceInvolveParty != null) {
                    DocInvolveParty targetInvolveParty =
                            getDocInvolvePartyWrapper(crossDocPartyConversionMap.getTargetPartyRole(), targetNodeName
                                    , targetContentSpecifier.getDocumentManager(),
                                    targetDocMatItem.getUuid(), targetDocMatItem.getClient());
                    if(targetInvolveParty == null){
                        targetInvolveParty =
                                (DocInvolveParty) targetDocumentManager.newEntityNode(targetDocMatItem, targetNodeName);
                    } else {
                        if(!updateFlag){
                            return;
                        }
                    }
                    targetInvolveParty.setPartyRole(crossDocPartyConversionMap.getTargetPartyRole());
                    copyDocInvolveParty(sourceInvolveParty, targetInvolveParty);
                    targetContentSpecifier.setDocItemInvolveParty(targetItemServiceModule, targetInvolveParty);

                    // [Additional Logic] In case party on target root doc service module is null, then provide
                    // additional party
                    if(targetServiceModule != null){
                        ServiceEntityNode targetDocument = targetContentSpecifier.getCoreEntity(targetServiceModule);
                        DocInvolveParty targetRootInvolveParty =
                                sourceContentSpecifier.getDocInvolveParty(crossDocPartyConversionMap.getSourcePartyRole(),
                                        targetServiceModule);
                        if(targetRootInvolveParty == null){
                            targetRootInvolveParty =
                                    (DocInvolveParty) targetDocumentManager.newEntityNode(targetDocument, targetNodeName);
                            targetRootInvolveParty.setPartyRole(crossDocPartyConversionMap.getTargetPartyRole());
                            copyDocInvolveParty(sourceInvolveParty, targetRootInvolveParty);
                            targetContentSpecifier.setDocInvolveParty(targetServiceModule, targetInvolveParty);
                        }
                    }
                }
            }
        }
    }

    /**
     * Utility method to get involve party mapping relationship from sourceDocType and targetDocType
     * @param sourceDocType
     * @param targetDocType
     * @return
     */
    public List<CrossCopyPartyConversionConfig> getInvolvePartyMap(int sourceDocType, int targetDocType)
            throws DocActionException {
        DocActionExecutionProxy<?, ?, ?> sourceDocActionExecutionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(sourceDocType);
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap =
                sourceDocActionExecutionProxy.getCrossCopyDocConversionConfigMap();
        if(crossCopyDocConversionConfigMap == null){
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG_SPECIFIER, sourceDocType);
        }
        CrossCopyDocConversionConfig crossCopyDocConversionConfig = crossCopyDocConversionConfigMap.get(targetDocType);
        if(crossCopyDocConversionConfig == null){
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG_SPECIFIER, targetDocType);
        }
        return crossCopyDocConversionConfig.getCrossCopyPartyConversionConfigList();
    }

    public DocInvolveParty getSourceInvolveParty(int sourceDocType, int targetDocType, int targetPartyRole,
                                                 String sourceDocUUID, String client)
            throws DocActionException, ServiceEntityInstallationException, ServiceEntityConfigureException {
        List<CrossCopyPartyConversionConfig> crossCopyPartyConversionConfigList = getInvolvePartyMap(sourceDocType,
                targetDocType);
        CrossCopyPartyConversionConfig crossCopyPartyConversionConfig =
                ServiceCollectionsHelper.filterOnline(crossCopyPartyConversionConfigList, config-> config.getTargetPartyRole() == targetPartyRole);
        if(crossCopyPartyConversionConfig == null){
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG_SPECIFIER, targetDocType);
        }
        int sourcePartyRole = crossCopyPartyConversionConfig.getSourcePartyRole();
        DocumentContentSpecifier<?, ?, ?> sourceDocSpecifier =
                docActionExecutionProxyFactory.getSpecifierByDocType(sourceDocType);
        String sourcePartyNodeName = sourceDocSpecifier.getInvolvePartyNodeName();
        return getDocInvolvePartyWrapper(sourcePartyRole, sourcePartyNodeName,
                sourceDocSpecifier.getDocumentManager(), sourceDocUUID,
                client);
    }

    public void genConvertCrossDocItemParty(DocumentContentSpecifier sourceContentSpecifier,
                                         ServiceModule sourceDocServiceModule,
                                         DocumentContentSpecifier targetContentSpecifier,
                                         DocMatItemNode targetDocMatItem,
                                         ServiceModule targetItemServiceModule,
                                         List<CrossCopyPartyConversionConfig> crossDocPartyConversionMapList)
            throws ServiceEntityConfigureException {
        ServiceEntityManager targetDocumentManager = targetContentSpecifier.getDocumentManager();
        if (ServiceCollectionsHelper.checkNullList(crossDocPartyConversionMapList)) {
            return;
        }
        String targetNodeName = ServiceModuleProxy.getNodeNameByFieldDocNodeCategory(targetItemServiceModule.getClass(),
                IServiceModuleFieldConfig.DOCNODE_CATE_PARTY);
        if(!ServiceEntityStringHelper.checkNullString(targetNodeName) && sourceDocServiceModule != null){
            for (CrossCopyPartyConversionConfig crossDocPartyConversionMap : crossDocPartyConversionMapList) {
                DocInvolveParty sourceInvolveParty =
                        sourceContentSpecifier.getDocInvolveParty(crossDocPartyConversionMap.getSourcePartyRole(),
                                sourceDocServiceModule);
                if (sourceInvolveParty != null) {
                    DocInvolveParty targetInvolveParty =
                            (DocInvolveParty) targetDocumentManager.newEntityNode(targetDocMatItem, targetNodeName);
                    targetInvolveParty.setPartyRole(crossDocPartyConversionMap.getTargetPartyRole());
                    copyDocInvolveParty(sourceInvolveParty, targetInvolveParty);
                    targetContentSpecifier.setDocItemInvolveParty(targetItemServiceModule, targetInvolveParty);
                }
            }
        }
    }

    public Map<Integer, String> getDocInvolvePartyMapByDocType(int documentType, String lanCode)
            throws ServiceEntityInstallationException, DocActionException {
        DocumentContentSpecifier documentContentSpecifier =
                docActionExecutionProxyFactory.getSpecifierByDocType(documentType);
        if (documentContentSpecifier != null) {
            return documentContentSpecifier.getInvolvePartyMap(lanCode);
        }
        return null;
    }

    /**
     * Provide a default logic for filtering target document by checking if involve party matches.
     * The involve parties could be external customer or suppliler
     *
     * @param documentMatItemBatchGenRequest
     * @param partyRole
     * @return
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public boolean defCheckPartyForFilter(DocumentMatItemBatchGenRequest documentMatItemBatchGenRequest,
                                          int targetDocType, ServiceEntityNode serviceEntityNode, int partyRole)
            throws DocActionException {
        DocumentMatItemBatchGenRequest.InvolvePartyGenRequest requestTargetParty =
                ServiceBasicRequestHelper.parseInvolvePartyRequest(documentMatItemBatchGenRequest, partyRole);
        DocumentContentSpecifier targetDocumentSpecifer = null;
        targetDocumentSpecifer = docActionExecutionProxyFactory.getSpecifierByDocType(targetDocType);
        if (targetDocumentSpecifer == null) {
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG, targetDocType);
        }
        ServiceModule targetServiceModel = null;
        try {
            targetServiceModel = targetDocumentSpecifer.loadServiceModule(serviceEntityNode.getUuid(),
                    serviceEntityNode.getClient());
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        } catch (ServiceModuleProxyException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
        /*
         * [Case 1] If current doc has not assigned special party, return true
         */
        DocInvolveParty curDocInvolveParty = targetDocumentSpecifer.getDocInvolveParty(partyRole, targetServiceModel);
        if (curDocInvolveParty == null || ServiceEntityStringHelper.checkNullString(curDocInvolveParty.getRefUUID())) {
            // in case current target doc has assigned no party
            return true;
        }
        /*
         * [Case 2] If no request party, return true
         */
        if (requestTargetParty == null) {
            return true;
        }
        /*
         * [Case 3] Check if request party and cur doc party matches
         */
        return curDocInvolveParty.getRefUUID().equals(requestTargetParty.getRefPartyUUID());
    }

}
