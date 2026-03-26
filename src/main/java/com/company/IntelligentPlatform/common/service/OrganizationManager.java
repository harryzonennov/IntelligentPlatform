package com.company.IntelligentPlatform.common.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.LogonUserOrgServiceUIModel;
import com.company.IntelligentPlatform.common.dto.LogonUserOrganizationUIModel;
import com.company.IntelligentPlatform.common.dto.OrganizationServiceUIModel;
import com.company.IntelligentPlatform.common.dto.OrganizationUIModel;
import com.company.IntelligentPlatform.common.dto.OrganizationBarcodeBasicSettingUIModel;
import com.company.IntelligentPlatform.common.repository.OrganizationRepository;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.LogonUserSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.AccountManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IWorkRoleInOrganizationConstants;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.LogonUserOrgReference;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.OrganizationBarcodeBasicSetting;
import com.company.IntelligentPlatform.common.model.OrganizationConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ISystemCodeValueCollectConstants;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [Organization]
 *
 * @author
 * @dateSun Nov 25 17:42:54 CST 2012
 * <p>
 * This class is generated automatically by platform automation
 * register tool
 */
@Service
@Transactional
public class OrganizationManager extends ServiceEntityManager {

    public static final String METHOD_ConvOrganizationToUI = "convOrganizationToUI";

    public static final String METHOD_ConvParentOrganizationToUI = "convParentOrganizationToUI";

    public static final String METHOD_ConvUIToOrganization = "convUIToOrganization";

    public static final String METHOD_ConvAccountantToUI = "convAccountantToUI";

    public static final String METHOD_ConvMainContactToUI = "convMainContactToUI";

    public static final String METHOD_ConvCashierToUI = "convCashierToUI";

    public static final String METHOD_ConvOrganizationBarcodeBasicSettingToUI =
            "convOrganizationBarcodeBasicSettingToUI";

    public static final String METHOD_ConvUIToOrganizationBarcodeBasicSetting =
            "convUIToOrganizationBarcodeBasicSetting";
    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    protected OrganizationRepository organizationDAO;

    @Autowired
    protected OrganizationConfigureProxy organizationConfigureProxy;

    @Autowired
    protected OrganizationFunctionManager organizationFunctionManager;

    @Autowired
    protected LogonUserManager logonUserManager;

    @Autowired
    protected LogonUserSpecifier logonUserSpecifier;

    @Autowired
    protected LogonUserOrgManager logonUserOrgManager;

    @Autowired
    protected AccountManager accountManager;

    @Autowired
    protected OrganizationSearchProxy organizationSearchProxy;

    @Autowired
    protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<ServiceEntityNode> organizationFunctionMap;

    private Map<String, Map<Integer, String>> organTypeMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> organLevelMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> regularTypeMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> accountTypeMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> defOrgFunctionMapLan = new HashMap<>();

    public String getFunctionMapValue(int orgFunction, String languageCode, String client,
                                                   boolean refreshFlag) {
        try {
            Map<Integer, String> organizationMap = initOrgFunctionMap(languageCode, client, refreshFlag);
            if(organizationMap != null){
                return organizationMap.get(orgFunction);
            }
        } catch (ServiceEntityInstallationException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "organizationFunction"));
            return String.valueOf(orgFunction);
        }
        return String.valueOf(orgFunction);
    }

    public Map<Integer, String> initOrgFunctionMap(String languageCode, String client,
                                                   boolean refreshFlag) throws ServiceEntityInstallationException {
        List<ServiceEntityNode> systemCodeValueUnionList = this
                .initOrganizationFunctionMap(client, refreshFlag);
        if(ServiceCollectionsHelper.checkNullList(systemCodeValueUnionList)){
            // Return system default configure
            return ServiceLanHelper
                    .initDefLanguageMapUIModel(languageCode, this.defOrgFunctionMapLan, OrganizationUIModel.class,
                            "organizationFunction");
        }
        // Return custom configuration
        return systemCodeValueCollectionManager
                .convertIntCodeValueUnionMap(systemCodeValueUnionList);
    }

    public List<ServiceEntityNode> initOrganizationFunctionMap(String client,
                                                               boolean refreshFlag) throws ServiceEntityInstallationException {
        if (refreshFlag
                || ServiceCollectionsHelper
                .checkNullList(this.organizationFunctionMap)) {
            try {
                this.organizationFunctionMap = systemCodeValueCollectionManager
                        .loadRawCodeValueUnionList(
                                ISystemCodeValueCollectConstants.ID_ORGANIZATION_FUNCTION,
                                client);
            } catch (ServiceModuleProxyException
                    | ServiceEntityConfigureException e) {
                // just ignore
            }
        }
        return this.organizationFunctionMap;
    }

    public Map<Integer, String> initOrganTypeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.organTypeMapLan, OrganizationUIModel.class,
                "organType");
    }

    public Map<Integer, String> initOrganLevelMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.organLevelMapLan, OrganizationUIModel.class,
                "organLevel");
    }

    public Map<Integer, String> initRegularTypeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.regularTypeMapLan, OrganizationUIModel.class,
                "regularType");
    }

    public Map<Integer, String> initAccountTypeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.accountTypeMapLan, OrganizationUIModel.class,
                "accountType");
    }

    public void postLoadServiceUIModel(
            OrganizationServiceUIModel organizationServiceUIModel,
                                         LogonInfo logonInfo) throws ServiceEntityConfigureException, DocActionException {
        try{
            // step 1 update logonOrganization UI
            List<ServiceEntityNode> logonUserOrgList = logonUserManager.getEntityNodeListByKey(organizationServiceUIModel.getOrganizationUIModel().getUuid(),
                    IReferenceNodeFieldConstant.REFUUID, LogonUserOrgReference.NODENAME, logonInfo.getClient(), null);
            if(!ServiceCollectionsHelper.checkNullList(logonUserOrgList)){
                organizationServiceUIModel.setLogonUserOrganizationUIModelList(new ArrayList<>());
                for(ServiceEntityNode serviceEntityNode: logonUserOrgList){
                    LogonUserOrgReference logonUserOrgReference = (LogonUserOrgReference) serviceEntityNode;
                    LogonUserOrgServiceUIModel logonUserOrgServiceUIModel =
                            logonUserOrgManager.getLogonUserOrganServiceUIModel(logonUserOrgReference, logonInfo);
                    organizationServiceUIModel.getLogonUserOrganizationUIModelList().add(logonUserOrgServiceUIModel);
                }
            }
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }

    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, organizationDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(organizationConfigureProxy);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convOrganizationBarcodeBasicSettingToUI(
            OrganizationBarcodeBasicSetting organizationBarcodeBasicSetting,
            OrganizationBarcodeBasicSettingUIModel organizationBarcodeBasicSettingUIModel) {
        if (organizationBarcodeBasicSetting != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(organizationBarcodeBasicSetting, organizationBarcodeBasicSettingUIModel);
            organizationBarcodeBasicSettingUIModel
                    .setEan13CompanyCode(organizationBarcodeBasicSetting
                            .getEan13CompanyCode());
            organizationBarcodeBasicSettingUIModel
                    .setEan13CountryHead(organizationBarcodeBasicSetting
                            .getEan13CountryHead());

        }
    }

    public Organization newOrganizationFromParent(
            String baseUUID, Organization organization) throws ServiceEntityConfigureException {
        Organization parentOrganization = (Organization) this
                .getEntityNodeByKey(baseUUID,
                        IServiceEntityNodeFieldConstant.UUID,
                        Organization.NODENAME, organization.getClient(), null);
        if (parentOrganization != null) {
            organization.setParentOrganizationUUID(parentOrganization
                    .getUuid());
            organization.setRefOrganizationFunction(parentOrganization
                    .getRefOrganizationFunction());
            // Set default finance organization
            if (parentOrganization.getRefFinOrgUUID() != null
                    && !parentOrganization.getRefFinOrgUUID().equals(
                    ServiceEntityStringHelper.EMPTYSTRING)) {
                organization.setRefFinOrgUUID(parentOrganization
                        .getRefFinOrgUUID());
            } else {
                organization.setRefFinOrgUUID(parentOrganization.getUuid());
            }
        }
        return organization;
    }

    /**
     * [Internal method] Convert from UI model to se
     * model:organizationBarcodeBasicSetting
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToOrganizationBarcodeBasicSetting(
            OrganizationBarcodeBasicSettingUIModel organizationBarcodeBasicSettingUIModel,
            OrganizationBarcodeBasicSetting rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(organizationBarcodeBasicSettingUIModel, rawEntity);
        rawEntity.setEan13CompanyCode(organizationBarcodeBasicSettingUIModel
                .getEan13CompanyCode());
        rawEntity.setUuid(organizationBarcodeBasicSettingUIModel.getUuid());
        rawEntity.setRefOrganizationUUID(organizationBarcodeBasicSettingUIModel
                .getRefOrganizationUUID());
        rawEntity.setEan13CountryHead(organizationBarcodeBasicSettingUIModel
                .getEan13CountryHead());
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convAccountantToUI(Employee accountant,
                                   OrganizationUIModel organizationUIModel) {
        if (accountant != null) {
            organizationUIModel.setRefAccountantId(accountant.getId());
            organizationUIModel.setRefAccountantName(accountant.getName());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convMainContactToUI(Employee mainContact,
                                    OrganizationUIModel organizationUIModel) {
        if (mainContact != null) {
            organizationUIModel.setContactPerson(mainContact.getName());
            organizationUIModel.setTelephone(mainContact.getTelephone());
            organizationUIModel.setContactMobileNumber(mainContact.getMobile());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convCashierToUI(Employee employee,
                                OrganizationUIModel organizationUIModel) {
        if (employee != null) {
            organizationUIModel.setRefCashierId(employee.getId());
            organizationUIModel.setRefCashierName(employee.getName());
        }
    }

    /**
     * Get the assigned accountant belongs to this organization
     *
     * @return
     * @throws ServiceEntityConfigureException
     */
    public LogonUser getOrganizationAccountant(String orgUUID, String client)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> logonUserOrgList = logonUserManager
                .getEntityNodeListByKey(orgUUID,
                        IReferenceNodeFieldConstant.REFUUID,
                        LogonUserOrgReference.NODENAME, client, null);
        for (ServiceEntityNode seNode : logonUserOrgList) {
            LogonUserOrgReference logonUserOrgReference = (LogonUserOrgReference) seNode;
            if (logonUserOrgReference.getWorkRole() == IWorkRoleInOrganizationConstants.ACCOUNTANT) {
                LogonUser logonUser = (LogonUser) logonUserManager
                        .getEntityNodeByKey(
                                logonUserOrgReference.getRootNodeUUID(),
                                IServiceEntityNodeFieldConstant.UUID,
                                LogonUser.NODENAME, null);
                return logonUser;
            }
        }
        return null;
    }

    /**
     * Get all Organization list by current client
     * Organization list
     *
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getAllOrganizationList(String client)
            throws ServiceEntityConfigureException {
        return getEntityNodeListByKey(
                null, null,
                Organization.NODENAME, client, null);
    }

    /**
     * Get all Organization list including the home organization and all sub
     * Organization list
     *
     * @param homeOrganization
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getAllSubOrganizationList(
            Organization homeOrganization, boolean includeFlag)
            throws ServiceEntityConfigureException {
        if (homeOrganization == null) {
            return null;
        }
        List<ServiceEntityNode> result = new ArrayList<>();
        if(includeFlag){
            result.add(homeOrganization);
        }
        List<ServiceEntityNode> rawList = getEntityNodeListByKey(
                homeOrganization.getUuid(), Organization.PARENT_ORG_UUID,
                Organization.NODENAME, homeOrganization.getClient(), null);
        if (rawList == null || rawList.size() == 0) {
            return result;
        } else {
            result.addAll(rawList);
        }
        return result;
    }

    public Organization getOrganization(String orgUUID, String client) throws ServiceEntityConfigureException {
        Organization parentOrganization =
                (Organization) this.getEntityNodeByKey(orgUUID,
                        IServiceEntityNodeFieldConstant.UUID, Organization.NODENAME, client, null);
        return parentOrganization;
    }

    public Organization getParentOrganization(Organization sourceOrganization) throws ServiceEntityConfigureException {
        if(sourceOrganization == null){
            return null;
        }
        Organization parentOrganization =
                (Organization) this.getEntityNodeByKey(sourceOrganization.getParentOrganizationUUID(),
                IServiceEntityNodeFieldConstant.UUID, Organization.NODENAME, sourceOrganization.getClient(), null);
        return parentOrganization;
    }

    public List<ServiceEntityNode> getOrganizationSameParent(Organization sourceOrganization) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> sameOrgList =
                this.getEntityNodeListByKey(sourceOrganization.getParentOrganizationUUID(),
                        Organization.PARENT_ORG_UUID, Organization.NODENAME, sourceOrganization.getClient(), null);
        return sameOrgList;
    }

    public Organization getNearestOrganizationUntil(Organization sourceOrganization,
                                                   Function<Organization, Boolean> checkHitCallback) throws ServiceEntityConfigureException {
        if(sourceOrganization == null){
            return null;
        }
        /*
         * [Step1] Check Source Organziation itself
         */
        Boolean checkHit = checkHitCallback.apply(sourceOrganization);
        if(checkHit){
            return sourceOrganization;
        }
        /*
         * [Step2] Check Organziations belongs to same parent
         */
        List<ServiceEntityNode> sameOrgList = getOrganizationSameParent(sourceOrganization);
        if(!ServiceCollectionsHelper.checkNullList(sameOrgList)){
            // Check Same Level Organization
            for(ServiceEntityNode seNode: sameOrgList){
                Organization tempOrganization = (Organization) seNode;
                Boolean tempCheckHit = checkHitCallback.apply(tempOrganization);
                if(tempCheckHit){
                    return tempOrganization;
                }
            }
        }
        /*
         * [Step3] Check Parent Organization
         */
        if(!ServiceEntityStringHelper.checkNullString(sourceOrganization.getParentOrganizationUUID())){
            Organization parentOrganization = this.getOrganization(sourceOrganization.getParentOrganizationUUID(),
                    sourceOrganization.getClient());
            if(parentOrganization != null){
                return getNearestOrganizationUntil(parentOrganization, checkHitCallback);
            }
        }
        /*
         * [Step4] In case current is already top organization: host company
         */
        List<ServiceEntityNode> childOrganzationList = this.getAllSubOrganizationList(sourceOrganization, false);
        if(ServiceCollectionsHelper.checkNullList(childOrganzationList)){
            return null;
        }
        for(ServiceEntityNode seNode: childOrganzationList){
            Organization childOrganization = (Organization) seNode;
            Organization targetOrganization = getNearestDownwardOrganizationUntil(childOrganization, checkHitCallback);
            if(targetOrganization != null){
                return targetOrganization;
            }
        }
        return null;
    }

    private Organization getNearestDownwardOrganizationUntil(Organization sourceOrganization,
                                                    Function<Organization, Boolean> checkHitCallback) throws ServiceEntityConfigureException {
        if(sourceOrganization == null){
            return null;
        }
        /*
         * [Step1] Check Source Organziation itself
         */
        Boolean checkHit = checkHitCallback.apply(sourceOrganization);
        if(checkHit){
            return sourceOrganization;
        }
        List<ServiceEntityNode> childOrganzationList = this.getAllSubOrganizationList(sourceOrganization, false);
        if(ServiceCollectionsHelper.checkNullList(childOrganzationList)){
            return null;
        }
        for(ServiceEntityNode seNode: childOrganzationList){
            Organization childOrganization = (Organization) seNode;
            Organization targetOrganization = getNearestDownwardOrganizationUntil(childOrganization, checkHitCallback);
            if(targetOrganization != null){
                return targetOrganization;
            }
        }
        return null;
    }

    /**
     * Logic to get parent organization until
     *
     * @param sourceOrganization
     * @param checkHitCallback
     * @return
     */
    public Organization getParentOrgUntil(Organization sourceOrganization,
                                          Function<Organization, Boolean> checkHitCallback) throws ServiceEntityConfigureException {
        if(sourceOrganization == null){
            return null;
        }
        Boolean checkHit = checkHitCallback.apply(sourceOrganization);
        if(checkHit){
            return sourceOrganization;
        }
        Organization parentOrganization = getParentOrganization(sourceOrganization);
        if(parentOrganization == null){
            return sourceOrganization;
        }
        return getParentOrgUntil(parentOrganization, checkHitCallback);
    }

    public Organization getParentOrgUntilFunction(Organization sourceOrganization, int organizationFunction) throws ServiceEntityConfigureException {
        return getParentOrgUntil(sourceOrganization, organization->{
            return organizationFunction == organization.getOrganizationFunction();
        });
    }

    public Organization getNearestOrganizationUntilFunction(Organization sourceOrganization,
            int organizationFunction) throws ServiceEntityConfigureException {
        return getNearestOrganizationUntil(sourceOrganization, organization->{
            return organizationFunction == organization.getOrganizationFunction();
        });
    }

    /**
     * Get organization list including suborg list and home organization from
     * persistency
     *
     * @param logonUser
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getOrganizationListByUserBackend(
            LogonUser logonUser) throws ServiceEntityConfigureException {
        // get current registered logon organization
        LogonUserOrgReference logonOrg = (LogonUserOrgReference) logonUserManager
                .getEntityNodeByKey(logonUser.getUuid(),
                        IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                        LogonUserOrgReference.NODENAME, logonUser.getClient(),
                        null);
        if (logonOrg == null) {
            return null;
        }
        Organization homeOrganization = (Organization) getEntityNodeByKey(
                logonOrg.getRefUUID(), IServiceEntityNodeFieldConstant.UUID,
                Organization.NODENAME, logonUser.getClient(), null);
        return getAllSubOrganizationList(homeOrganization, false);
    }

    /**
     * Get the assigned cashier belongs to this organization
     *
     * @return
     * @throws ServiceEntityConfigureException
     */
    public LogonUser getOrganizationCashier(String orgUUID, String client)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> logonUserOrgList = logonUserManager
                .getEntityNodeListByKey(orgUUID,
                        IReferenceNodeFieldConstant.REFUUID,
                        LogonUserOrgReference.NODENAME, client, null);
        for (ServiceEntityNode seNode : logonUserOrgList) {
            LogonUserOrgReference logonUserOrgReference = (LogonUserOrgReference) seNode;
            if (logonUserOrgReference.getWorkRole() == IWorkRoleInOrganizationConstants.CASHIER) {
                LogonUser logonUser = (LogonUser) logonUserManager
                        .getEntityNodeByKey(
                                logonUserOrgReference.getRootNodeUUID(),
                                IServiceEntityNodeFieldConstant.UUID,
                                LogonUser.NODENAME, null);
                return logonUser;
            }
        }
        return null;
    }

    public void convUIToOrganization(OrganizationUIModel organizationUIModel,
                                     Organization rawEntity) {
        accountManager.convUIToAccount(organizationUIModel, rawEntity);
        rawEntity.setFullName(organizationUIModel.getFullName());
        rawEntity.setRefCashierUUID(organizationUIModel.getRefAccountantUUID());
        rawEntity.setMainContactUUID(organizationUIModel.getMainContactUUID());
        rawEntity.setRefAccountantUUID(organizationUIModel
                .getRefAccountantUUID());
        rawEntity.setTags(organizationUIModel.getTags());
        rawEntity.setDepositBank(organizationUIModel.getDepositBank());
        rawEntity.setTaxNumber(organizationUIModel.getTaxNumber());
        rawEntity.setBankAccount(organizationUIModel.getBankAccount());
        rawEntity.setRefCashierUUID(organizationUIModel.getRefCashierUUID());
        rawEntity.setRefFinOrgUUID(organizationUIModel.getRefFinOrgUUID());
        rawEntity.setRefOrganizationFunction(organizationUIModel
                .getRefOrganizationFunction());
        rawEntity.setOrganizationFunction(organizationUIModel
                .getOrganizationFunction());
        rawEntity.setParentOrganizationUUID(organizationUIModel
                .getParentOrganizationUUID());
    }

    public void convOrganizationToUI(Organization organization,
                                     OrganizationUIModel organizationUIModel)
            throws ServiceEntityConfigureException, ServiceEntityInstallationException {
        convOrganizationToUI(organization, organizationUIModel, null);
    }

    public void convOrganizationToUI(Organization organization,
                                     OrganizationUIModel organizationUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (organization != null) {
            accountManager.convAccountToUI(organization, organizationUIModel, logonInfo);
            organizationUIModel.setFullName(organization.getFullName());
            organizationUIModel.setRefAccountantUUID(organization
                    .getRefCashierUUID());
            organizationUIModel.setParentOrganizationUUID(organization
                    .getParentOrganizationUUID());
            organizationUIModel.setRefFinOrgUUID(organization
                    .getRefFinOrgUUID());
            organizationUIModel.setRefOrganizationFunction(organization
                    .getRefOrganizationFunction());
            organizationUIModel.setOrganizationFunction(organization
                    .getOrganizationFunction());
            if(logonInfo != null){
                try {
                    Map<Integer, String> functionMap = this.initOrgFunctionMap( logonInfo.getLanguageCode(),
                            organization.getClient(), false);
                    organizationUIModel.setOrganizationFunctionValue(functionMap
                            .get(organization.getOrganizationFunction()));
                } catch (ServiceEntityInstallationException e) {
                   logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "organizationFunction"));
                }
            }
            organizationUIModel.setMainContactUUID(organization
                    .getMainContactUUID());
            organizationUIModel.setRefAccountantUUID(organization
                    .getRefAccountantUUID());
            organizationUIModel.setRefCashierUUID(organization
                    .getRefCashierUUID());
            organizationUIModel.setTags(organization.getTags());
            organizationUIModel.setTaxNumber(organization.getTaxNumber());
            organizationUIModel.setBankAccount(organization.getBankAccount());
            organizationUIModel.setDepositBank(organization.getDepositBank());
        }
    }

    public void convParentOrganizationToUI(Organization parentOrganization,
                                           OrganizationUIModel organizationUIModel)
            throws ServiceEntityConfigureException {
        if (parentOrganization != null) {
            organizationUIModel.setParentOrganizationId(parentOrganization
                    .getId());
            organizationUIModel.setParentOrganizationName(parentOrganization
                    .getName());
            Map<String, String> functionMap = organizationFunctionManager
                    .getOrganizationFunctionMap(parentOrganization.getClient());
            organizationUIModel.setParentOrganizationFunctionValue(functionMap
                    .get(parentOrganization.getRefOrganizationFunction()));
        }
    }

    public void convCashierToUI(LogonUser cashier,
                                OrganizationUIModel organizationUIModel) {
        if (cashier != null) {
            organizationUIModel.setRefCashierName(cashier.getName());
            organizationUIModel.setRefCashierId(cashier.getId());
        }
    }

    public void convAccountantToUI(LogonUser accountant,
                                   OrganizationUIModel organizationUIModel) {
        if (accountant != null) {
            organizationUIModel.setRefAccountantName(accountant.getName());
            organizationUIModel.setRefAccountantId(accountant.getId());
        }
    }

    public void convRefFinOrgToUI(Organization organization,
                                  OrganizationUIModel organizationUIModel) {
        if (organization != null) {
            organizationUIModel.setRefFinOrgUUID(organization.getUuid());
            organizationUIModel.setRefFinOrgId(organization.getId());
            organizationUIModel.setRefFinOrgName(organization.getName());
        }
    }

    public void convContactToUI(Employee contact,
                                OrganizationUIModel organizationUIModel) {
        if (contact != null) {
            organizationUIModel.setContactPerson(contact.getName());
            organizationUIModel.setContactMobileNumber(contact.getMobile());
            organizationUIModel.setContactEmployeeTelephone(contact
                    .getTelephone());
        }
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.Organization;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return this.organizationSearchProxy;
    }

}