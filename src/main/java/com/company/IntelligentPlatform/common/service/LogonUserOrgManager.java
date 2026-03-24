package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.EmployeeManager;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.dto.LogonUserOrgServiceUIModel;
import com.company.IntelligentPlatform.common.dto.LogonUserOrgServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.LogonUserOrganizationUIModel;
import com.company.IntelligentPlatform.common.dto.LogonUserUIModel;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceFlowException;
import com.company.IntelligentPlatform.common.model.IReferenceNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LogonUserOrgManager {

    public static final String METHOD_convOrgToUserOrgUIModel = "convOrgToUserOrgUIModel";

    public static final String METHOD_convLogonUserOrgRefToUserOrgUIModel = "convLogonUserOrgRefToUserOrgUIModel";

    public static final String METHOD_convLogonUserToUserOrgUIModel = "convLogonUserToUserOrgUIModel";

    public static final String METHOD_convUserOrgUIModelToLogonUserOrgRef = "convUserOrgUIModelToLogonUserOrgRef";

    @Autowired
    protected LogonUserManager logonUserManager;

    @Autowired
    protected EmployeeManager employeeManager;

    @Autowired
    protected OrganizationManager organizationManager;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected LogonUserOrgServiceUIModelExtension logonUserOrgReferenceServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public Map<Integer, String> initWorkRoleMap(String languageCode, String client, boolean refreshFlag)
            throws ServiceEntityInstallationException {
       return employeeManager.initWorkRoleMap(languageCode, client, refreshFlag);
    }

    public String getWorkRoleValue(int workRole, String languageCode, String client, boolean refreshFlag){
        return employeeManager.getWorkRoleValue(workRole, languageCode, client, refreshFlag);
    }

    public LogonUserOrgServiceUIModel getLogonUserOrganServiceUIModel(LogonUserOrgReference logonUserOrgReference,
                                                                 LogonInfo logonInfo) throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException {
        LogonUserOrgServiceModel logonUserOrgServiceModel = (LogonUserOrgServiceModel) logonUserManager.loadServiceModule(LogonUserOrgServiceModel.class,
                logonUserOrgReference, logonUserOrgReferenceServiceUIModelExtension);
        return (LogonUserOrgServiceUIModel) logonUserManager
                .genServiceUIModuleFromServiceModel(
                        LogonUserOrgServiceUIModel.class,
                        LogonUserOrgServiceModel.class,
                        logonUserOrgServiceModel,
                        logonUserOrgReferenceServiceUIModelExtension,
                        null, logonInfo);
    }

    /**
     * Get Current Organization's department Manager
     * @param orgUUID
     * @param serialLogonInfo
     * @return
     * @throws ServiceEntityConfigureException
     */
    public LogonUser getDeptManager(String orgUUID, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException, ServiceFlowException {
        return getLogonUserByWorkRole(orgUUID, LogonUserOrgReference.ORGROLE_ORG_MAN, serialLogonInfo);
    }

    public LogonUser getCompanyManager(String orgUUID, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException,
            LogonUserOrgException, ServiceFlowException {
        return getDeptManagerByOrgFunc(orgUUID, serialLogonInfo, Organization.ORGFUNCTION_HOSTCOMPANY);
    }

    public LogonUser getCompanyViceManager(String orgUUID, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException,
            LogonUserOrgException, ServiceFlowException {
        Organization organization = organizationManager.getOrganization(orgUUID, serialLogonInfo.getClient());
        Organization targetOrganization = organizationManager.getNearestOrganizationUntilFunction(organization,
                Organization.ORGFUNCTION_HOSTCOMPANY);
        if(targetOrganization == null){
            String organizationFuncValue = organizationManager.getFunctionMapValue(Organization.ORGFUNCTION_HOSTCOMPANY,
                    serialLogonInfo.getLanguageCode(), serialLogonInfo.getClient(), false);
            throw new LogonUserOrgException(LogonUserOrgException.PARA_NO_ORG_BYFUNC,organizationFuncValue);
        }
        return getLogonUserByWorkRole(orgUUID, LogonUserOrgReference.ORGROLE_ORG_MAN, serialLogonInfo);
    }

    public LogonUser getFinanceDeptManager(String orgUUID, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException,
            LogonUserOrgException, ServiceFlowException {
        return getDeptManagerByOrgFunc(orgUUID, serialLogonInfo, Organization.ORGFUNCTION_FINANCE);
    }

    public LogonUser getPurchaseDeptManager(String orgUUID, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException,
            LogonUserOrgException, ServiceFlowException {
        return getDeptManagerByOrgFunc(orgUUID, serialLogonInfo, Organization.ORGFUNCTION_PURCHASE);
    }

    public LogonUser getProductionDeptManager(String orgUUID, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException,
            LogonUserOrgException, ServiceFlowException {
        return getDeptManagerByOrgFunc(orgUUID, serialLogonInfo, Organization.ORGFUNCTION_PROD_DEPT);
    }

    public LogonUser getCompanyBoardChairMan(String orgUUID, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException,
            LogonUserOrgException, ServiceFlowException {
        Organization organization = organizationManager.getOrganization(orgUUID, serialLogonInfo.getClient());
        Organization hostCompany = organizationManager.getParentOrgUntilFunction(organization,Organization.ORGFUNCTION_HOSTCOMPANY);
        if(hostCompany == null){
            throw new LogonUserOrgException(LogonUserOrgException.PARA_NO_ORG_BYFUNC,
                    Organization.ORGFUNCTION_HOSTCOMPANY);
        }
        return getLogonUserByWorkRole(hostCompany.getUuid(), LogonUserOrgReference.ORGROLE_BOARD_CHAIRMAN,
                serialLogonInfo);
    }

    public LogonUser getFinanceAccountant(String orgUUID, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException, ServiceFlowException {
        LogonUser finAccUser = getLogonUserByWorkRole(orgUUID, LogonUserOrgReference.ORGROLE_FIN_ACC, serialLogonInfo);
        if(finAccUser != null){
            return finAccUser;
        }
        Organization organization = organizationManager.getOrganization(orgUUID, serialLogonInfo.getClient());
        if(ServiceEntityStringHelper.checkNullString(organization.getParentOrganizationUUID())){
            return null;
        }
        return getFinanceAccountant(organization.getParentOrganizationUUID(), serialLogonInfo);
    }

    private LogonUser getDeptManagerByOrgFunc(String orgUUID, SerialLogonInfo serialLogonInfo, int organFunction) throws ServiceEntityConfigureException, LogonUserOrgException, ServiceFlowException {
        Organization organization = organizationManager.getOrganization(orgUUID, serialLogonInfo.getClient());
        Organization targetOrganization = organizationManager.getNearestOrganizationUntilFunction(organization,
                organFunction);
        if(targetOrganization == null){
            String organizationFuncValue = organizationManager.getFunctionMapValue(organFunction,
                    serialLogonInfo.getLanguageCode(), serialLogonInfo.getClient(), false);
            throw new LogonUserOrgException(LogonUserOrgException.PARA_NO_ORG_BYFUNC,organizationFuncValue);
        }
        return getDeptManager(targetOrganization.getUuid(), serialLogonInfo);
    }

    /**
     * Core Logic to get Logon User List with specific role under specific organization
     */
    public LogonUser getLogonUserByWorkRole(String orgUUID, int workRole, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException, ServiceFlowException {
        List<ServiceEntityNode> logonUserList = this.getLogonUserListByWorkRole(orgUUID, workRole, serialLogonInfo.getClient());
        if(ServiceCollectionsHelper.checkNullList(logonUserList)){
            String workRoleValue = this.getWorkRoleValue(workRole, serialLogonInfo.getLanguageCode(),
                    serialLogonInfo.getClient(), false);
            throw new ServiceFlowException(ServiceFlowException.PARA_NO_TARGET_USER, workRoleValue);
        }
        return (LogonUser) logonUserList.get(0);
    }

    /**
     * Core Logic to get Logon User List with specific role under specific organization
     */
    public List<ServiceEntityNode> getLogonUserListByWorkRole(String orgUUID, int workRole, String client) throws ServiceEntityConfigureException {
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(orgUUID, IReferenceNodeFieldConstant.REFUUID);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(workRole, LogonUserOrgReference.FEILD_WORKROLE);
        List<ServiceBasicKeyStructure> keyList = ServiceCollectionsHelper.asList(key1, key2);
        List<ServiceEntityNode> logonUserOrgList = logonUserManager.getEntityNodeListByKeyList(keyList,
                LogonUserOrgReference.NODENAME, client,null);
        List<String> rootNodeUUIDList = new ArrayList<>();
        if(ServiceCollectionsHelper.checkNullList(logonUserOrgList)){
            return null;
        }
        for(ServiceEntityNode seNode:logonUserOrgList){
            ServiceCollectionsHelper.mergeToList(rootNodeUUIDList, seNode.getRootNodeUUID());
        }
        if(ServiceCollectionsHelper.checkNullList(rootNodeUUIDList)){
            return null;
        }
        return logonUserManager.getEntityNodeListByMultipleKey(rootNodeUUIDList, IServiceEntityNodeFieldConstant.UUID
                , LogonUser.NODENAME, client, null);
    }

    public void convOrgToUserOrgUIModel(Organization organization,
                                        LogonUserOrganizationUIModel logonUserOrganizationUIModel) {
        convOrgToUserOrgUIModel(organization, logonUserOrganizationUIModel, null);
    }

    public void convOrgToUserOrgUIModel(Organization organization,
                                        LogonUserOrganizationUIModel logonUserOrganizationUIModel, LogonInfo logonInfo) {
        if (organization != null) {
            logonUserOrganizationUIModel.setRefUUID(organization.getUuid());
            logonUserOrganizationUIModel
                    .setOrganizationId(organization.getId());
            logonUserOrganizationUIModel.setOrganizationName(organization
                    .getName());
            logonUserOrganizationUIModel.setOrganizationFunction(organization
                    .getOrganizationFunction());
            if(logonInfo != null){
                try {
                    Map<Integer, String> functionMap = organizationManager
                            .initOrgFunctionMap(logonInfo.getLanguageCode(), organization.getClient(), true);
                    logonUserOrganizationUIModel.setOrganizationFunctionValue(functionMap
                            .get(organization.getOrganizationFunction()));
                } catch (ServiceEntityInstallationException e) {
                    // Just continue;
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "organizationFunction"));
                }
            }
            logonUserOrganizationUIModel.setContactMobileNumber(organization
                    .getTelephone());
            logonUserOrganizationUIModel.setAddress(organization
                    .getAddress());
            logonUserOrganizationUIModel.setRefCityUUID(organization
                    .getRefCityUUID());
            logonUserOrganizationUIModel
                    .setTownZone(organization.getTownZone());
            logonUserOrganizationUIModel
                    .setCityName(organization.getCityName());
            logonUserOrganizationUIModel.setSubArea(organization.getSubArea());
            logonUserOrganizationUIModel.setStreetName(organization
                    .getStreetName());
            logonUserOrganizationUIModel.setHouseNumber(organization
                    .getHouseNumber());
            logonUserOrganizationUIModel
                    .setPostcode(organization.getPostcode());
            logonUserOrganizationUIModel.setOrganType(organization
                    .getRefOrganizationFunction());
        }
    }

    public void convUIToLogonUserOrgReference(
            LogonUserUIModel logonUserUIModel,
            LogonUserOrgReference logonUserOrgReference) {
        if (logonUserOrgReference != null) {
            DocFlowProxy.convUIToServiceEntityNode(logonUserUIModel, logonUserOrgReference);
            logonUserOrgReference.setRefUUID(logonUserUIModel
                    .getOrganizationUUID());
        }
    }

    public void convLogonUserToUserOrgUIModel(LogonUser logonUser,
                                          LogonUserOrganizationUIModel logonUserOrganizationUIModel){
        if(logonUser != null){
            logonUserOrganizationUIModel.setId(logonUser.getId());
            logonUserOrganizationUIModel.setName(logonUser.getName());
        }
    }

    public void convUserOrgUIModelToLogonUserOrgRef(
            LogonUserOrganizationUIModel logonUserOrganizationUIModel,
            LogonUserOrgReference rawEntity) {
        if (rawEntity != null) {
            DocFlowProxy.convUIToServiceEntityNode(logonUserOrganizationUIModel, rawEntity);
            if (!ServiceEntityStringHelper
                    .checkNullString(logonUserOrganizationUIModel.getRefUUID())) {
                rawEntity.setRefUUID(logonUserOrganizationUIModel.getRefUUID());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(logonUserOrganizationUIModel
                            .getRefSEName())) {
                rawEntity.setRefSEName(logonUserOrganizationUIModel
                        .getRefSEName());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(logonUserOrganizationUIModel
                            .getRefNodeName())) {
                rawEntity.setRefNodeName(logonUserOrganizationUIModel
                        .getRefNodeName());
            }
        }
    }

    public void convLogonUserOrgRefToUserOrgUIModel(
            LogonUserOrgReference logonUserOrgReference,
            LogonUserOrganizationUIModel logonUserOrganizationUIModel) {
        convLogonUserOrgRefToUserOrgUIModel(logonUserOrgReference, logonUserOrganizationUIModel, null);
    }

    public void convLogonUserOrgRefToUserOrgUIModel(
            LogonUserOrgReference logonUserOrgReference,
            LogonUserOrganizationUIModel logonUserOrganizationUIModel, LogonInfo logonInfo) {
        if (logonUserOrgReference != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(logonUserOrgReference,
                    logonUserOrganizationUIModel);
            logonUserOrganizationUIModel.setWorkRole(logonUserOrgReference.getWorkRole());
            if(logonInfo != null){
                try {
                    Map<Integer, String> workRoleMap = this.initWorkRoleMap(logonInfo.getLanguageCode(),
                            logonInfo.getClient(), false);
                    logonUserOrganizationUIModel.setWorkRoleValue(workRoleMap.get(logonUserOrgReference.getWorkRole()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "workRole"));
                }
            }
            logonUserOrganizationUIModel.setRefUUID(logonUserOrgReference
                    .getRefUUID());
            logonUserOrganizationUIModel.setRefSEName(logonUserOrgReference
                    .getRefSEName());
            logonUserOrganizationUIModel.setRefNodeName(logonUserOrgReference
                    .getRefNodeName());
        }
    }



}
