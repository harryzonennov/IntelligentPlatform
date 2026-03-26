package com.company.IntelligentPlatform.common.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.OrganizationFactoryService;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.LoginComModel;
import com.company.IntelligentPlatform.common.dto.LogonInfoUIModel;
import com.company.IntelligentPlatform.common.dto.LogonUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.LogonInfoRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.IServiceEncodeProxy;
import com.company.IntelligentPlatform.common.service.ServiceEncodeException;
import com.company.IntelligentPlatform.common.service.ServiceEncodeProxyFactory;
import com.company.IntelligentPlatform.common.service.SystemConfigureResourceException;
import com.company.IntelligentPlatform.common.service.SystemExecutorSettingManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.LogonInfoConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Logic Manager CLASS FOR Service Entity [LogonInfo]
 *
 * @author
 * @date Wed Feb 20 18:51:40 CST 2013
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class LogonInfoManager extends ServiceEntityManager {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected LogonInfoRepository logonInfoDAO;
    @Autowired
    LogonInfoConfigureProxy logonInfoConfigureProxy;

    @Autowired
    protected LogonUserManager logonUserManager;

    @Autowired
    protected LockObjectManager lockObjectManager;

    @Autowired
    protected OrganizationFactoryService organizationFactoryService;

    @Autowired
    protected OrganizationManager organizationManager;

    @Autowired
    protected ServiceEncodeProxyFactory serviceEncodeProxyFactory;

    /**
     * Currently the whole system encode type
     */
    public static final String SYS_ENCODE_TYPE = ServiceEncodeProxyFactory.ENCODE_MD5;

    @Autowired
    protected AuthorizationManager authorizationManager;

    @Autowired
    protected SystemExecutorSettingManager systemExecutorSettingManager;

    protected Logger logger = LoggerFactory.getLogger(LogonInfoManager.class);

    protected Locale locale;

    protected Role role;

    protected Map<String, Map<Integer, String>> logonStatusMapLan = new HashMap<String, Map<Integer, String>>();

    public LogonInfoManager() {
        super.seConfigureProxy = new LogonInfoConfigureProxy();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, logonInfoDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(logonInfoConfigureProxy);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Map<Integer, String> initLogonStatus(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.logonStatusMapLan, LogonInfoUIModel.class,
                "status");
    }

    /**
     * Core Logic to execute initial login process, while change the initial
     * password
     *
     * @param logonUIModel : the previous tried times
     * @throws LogonInfoException
     * @throws ServiceEncodeException
     */
    public LoginComModel initiallogon(LogonUIModel logonUIModel)
            throws LogonInfoException, ServiceEncodeException, ServiceComExecuteException {
        /*
         * [Step1] execute standard login process
         */
        LoginComModel loginComModel = logon(logonUIModel, false);
        LogonUser logonUser = loginComModel.getLogonUser();
        LogonUser logonUserBack = (LogonUser) logonUser.clone();
        /*
         * [Step2] Check password and reset User's initial status
         */
        String encodePassword = getEncodedPassword(logonUIModel
                .getNewPassword());
        // set the new password
        logonUser.setPassword(encodePassword);
        // reset the initial flag
        logonUser.setPasswordInitFlag(StandardSwitchProxy.SWITCH_OFF);
        this.updateSENode(logonUser, logonUserBack, logonUser.getUuid(),
                ServiceEntityStringHelper.EMPTYSTRING);
        return loginComModel;
    }

    public String getEncodedPassword(String rawValue)
            throws ServiceEncodeException {
        IServiceEncodeProxy serviceEncodeProxy = serviceEncodeProxyFactory
                .getEncodeProxy(SYS_ENCODE_TYPE);
        return serviceEncodeProxy.getEncodeValue(rawValue);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getSystemLocale() {
        if (locale == null) {
            return ServiceLanHelper.getDefault();
        } else {
            return locale;
        }
    }

    public Organization getOrganizationByUserBackend(String logonUserUUID)
            throws ServiceEntityConfigureException {
        // get current registered logon organization
        LogonUserOrgReference logonOrg = (LogonUserOrgReference) logonUserManager
                .getEntityNodeByKey(logonUserUUID,
                        IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                        LogonUserOrgReference.NODENAME, null);
        if (logonOrg == null) {
            logger.warn("No LogonUserOrgReference found for user UUID: {}", logonUserUUID);
            return null;
        }
        Organization organization = organizationFactoryService
                .getRefOrganization(logonOrg);
        return organization;
    }

    public boolean checkClientExist(String client)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> userList = logonUserManager
                .getEntityNodeListByKeyList(null, LogonUser.NODENAME, client,
                        null);
        return userList != null && userList.size() != 0;
    }

    /**
     * Core method to logon into system admin platform
     *
     * @param userID
     * @param password
     * @param triedTimes
     * @return
     * @throws LogonInfoException
     * @throws ServiceEncodeException
     */
    public LogonUser adminLogon(String userID, String password, int triedTimes)
            throws LogonInfoException, ServiceEncodeException {
        try {
            if (userID == null
                    || userID.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
                throw new LogonInfoException(
                        LogonInfoException.TYPE_EMP_USERNAME);
            }
            // Should get cross-client user exist or not
            LogonUser logonUser = (LogonUser) logonUserManager
                    .getEntityNodeByKey(userID,
                            IServiceEntityNodeFieldConstant.ID,
                            ServiceEntityNode.NODENAME_ROOT, null, true);
            if (logonUser == null) {
                // In case user can not be found by user ID
                throw new LogonInfoException(
                        LogonInfoException.PARA_NO_USER_FOUND, userID);
            }
            // Encode password for checking
            String encodePassword = getEncodedPassword(password);
            // Compare password
            if (logonUser.getPassword().equals(encodePassword)) {
                // Just continue, do nothing
            } else {
                // Should raise exception if password doesn't match
                throw new LogonInfoException(LogonInfoException.TYPE_WRONG_PASS);
            }
            /**
             * Check if the logon User has admin authorization
             */
            authorizationManager.checkResourceAuthorization(logonUser,
                    IDefResourceAuthorizationObject.AOID_BOOKINGNOTE,
                    ISystemActionCode.ACID_LIST, null);
            // generate new logonInfo
            LogonInfo logonInfo = (LogonInfo) newRootEntityNode();
            logonInfo.setRefUserUUID(logonUser.getUuid());
            logonInfo.setStartLogonTime(new Date());
            logonInfo.setLogonTryTimes(triedTimes);
            // Unlock the previous lock object by this user
            lockObjectManager.unLockSEListByLogonUser(logonUser);
            return logonUser;
        } catch (ServiceEntityConfigureException e) {
            throw new LogonInfoException(LogonInfoException.TYPE_SYSTEM_WRONG,
                    e.getMessage());
        }
    }

    public LogonInfo setInfoSwitch(LogonInfo logonInfo, boolean infoSwitch) {
        if (logonInfo == null) {
            return null;
        }
        ExtendedSettings extendedSettings = logonInfo.getExtendedSettings();
        if (extendedSettings == null) {
            extendedSettings = new ExtendedSettings();
            logonInfo.setExtendedSettings(extendedSettings);
        }
        extendedSettings.setInfoSwitch(infoSwitch);
        return logonInfo;
    }

    public boolean getInfoSwitch(LogonInfo logonInfo) {
        if (logonInfo == null) {
            return false;
        }
        ExtendedSettings extendedSettings = logonInfo.getExtendedSettings();
        if (extendedSettings == null) {
            return false;
        } else {
            return extendedSettings.getInfoSwitch();
        }
    }

    /**
     * Center logic to login into application system with different client
     *
     * @param logonUIModel
     * @param encodedFlag  : the previous tried times
     * @throws LogonInfoException
     * @throws ServiceEncodeException
     */
    public LoginComModel logon(LogonUIModel logonUIModel, boolean encodedFlag)
            throws LogonInfoException, ServiceEncodeException, ServiceComExecuteException {
        LogonUser logonUser;
        String userId = logonUIModel.getUserId();
        String client = logonUIModel.getClient();
        String password = logonUIModel.getPassword();
        try {
            /*
             * [Step1] Check if login user id is empty
             */
            if (ServiceEntityStringHelper.checkNullString(userId)) {
                throw new LogonInfoException(
                        LogonInfoException.TYPE_EMP_USERNAME);
            }
            /*
             * [Step2] Check if the client exist on system
             */
            boolean clientFlag = checkClientExist(client);
            if (!clientFlag) {
                throw new LogonInfoException(LogonInfoException.PARA_NO_CLIENT,
                        client);
            }
            /*
             * [Step3] Check if logonUser entity existed by user id
             */
            logonUser = (LogonUser) logonUserManager.getEntityNodeByKey(userId,
                    IServiceEntityNodeFieldConstant.ID,
                    ServiceEntityNode.NODENAME_ROOT, client, null, true);
            if (logonUser == null) {
                // In case user can not be found by user id
                throw new LogonInfoException(
                        LogonInfoException.PARA_NO_USER_FOUND, userId);
            }
            /*
             * [Step4] Encode password for checking identification of password
             */
            String encodePassword = password;
            if (!encodedFlag) {
                // In case the password not encoded
                encodePassword = getEncodedPassword(password);
            }
            // Compare password
            if (logonUser.getPassword().equals(encodePassword)) {
                // Just continue, do nothing
            } else {
                // Should raise exception if password doesn't match
                throw new LogonInfoException(LogonInfoException.TYPE_WRONG_PASS);
            }
            /*
             * [Step5] generate new logonInfo
             */
            LogonInfo logonInfo = (LogonInfo) newRootEntityNode(client);
            logonInfo.setRefUserUUID(logonUser.getUuid());
            logonInfo.setLogonUser(logonUser);
            logonInfo.setStartLogonTime(new Date());
            logonInfo.setLogonTryTimes(logonUIModel.getTriedTimes());
            if (!ServiceEntityStringHelper.checkNullString(logonUIModel
                    .getLanguageCode())) {
                logonInfo.setLanguageCode(logonUIModel.getLanguageCode());
            }

            /*
             * [Step6] Unlock the previous lock object by this user
             */
            lockObjectManager.unLockSEListByLogonUser(logonUser);
            LoginComModel loginComModel = new LoginComModel(logonUser,
                    logonInfo);
            /*
             * [Step7] execute normal tasks in async way
             */
            postExecuteTaskAsyncWrapper(logonInfo);
            return loginComModel;
        } catch (ServiceEntityConfigureException e) {
            throw new LogonInfoException(LogonInfoException.TYPE_SYSTEM_WRONG,
                    e.getMessage());
        }
    }

    public String postExecuteTaskAsyncWrapper(
            LogonInfo logonInfo)
            throws ServiceComExecuteException {
        String resultStr = ServiceEntityStringHelper.EMPTYSTRING;
        try {
            ServiceAsyncExecuteProxy.executeAsyncWrapper(logonInfo, new IComSimExecutor<LogonInfo,
                    String>() {
                @Override
                public String execute(LogonInfo t, LogonInfo logonInfo) throws ServiceEntityExceptionContainer {
                    try {
                        return systemExecutorSettingManager.executeDailyUpdate(logonInfo);
                    } catch (ServiceComExecuteException | SystemConfigureResourceException e) {
                        throw new ServiceEntityExceptionContainer(e);
                    }
                }
            }, logonInfo);
        } catch (ServiceEntityExceptionContainer serviceEntityExceptionContainer) {
            ServiceEntityException coreException = serviceEntityExceptionContainer.getCoreException();
            if (coreException != null) {
                if (coreException instanceof ServiceComExecuteException) {
                    throw (ServiceComExecuteException) coreException;
                }
            }
            throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR,
                    coreException.getErrorMessage());
        }
        return resultStr;
    }

    public static SerialLogonInfo cloneToSerialLogonInfo(LogonInfo logonInfo){
        SerialLogonInfo serialLogonInfo = new SerialLogonInfo();
        serialLogonInfo.setLogonType(logonInfo.getLogonType());
        serialLogonInfo.setHomeOrganizationUUID(logonInfo.getHomeOrganization().getUuid());
        serialLogonInfo.setExtendedSettings(logonInfo.getExtendedSettings());
        serialLogonInfo.setLogonTryTimes(logonInfo.getLogonTryTimes());
        serialLogonInfo.setClient(logonInfo.getClient());
        serialLogonInfo.setStartLogonTime(logonInfo.getStartLogonTime());
        serialLogonInfo.setLanguageCode(logonInfo.getLanguageCode());
        serialLogonInfo.setLogOffTime(logonInfo.getLogOffTime());
        serialLogonInfo.setStatus(logonInfo.getStatus());
        serialLogonInfo.setRefUserUUID(logonInfo.getRefUserUUID());
        serialLogonInfo.setResOrgUUID(logonInfo.getResOrgUUID());
        return serialLogonInfo;
    }

    /**
     * Generate LogonInfo from Serial Info
     * @return
     */
    public LogonInfo genLogonInfo(SerialLogonInfo serialLogonInfo, boolean simpleFlag){
        LogonInfo logonInfo = new LogonInfo();
        logonInfo.setLogonType(serialLogonInfo.getLogonType());
        String homeOrganizationUUID = serialLogonInfo.getHomeOrganizationUUID();
        if(!simpleFlag && !ServiceEntityStringHelper.checkNullString(homeOrganizationUUID)){
            try {
                Organization homeOrganization = organizationManager.getOrganization(homeOrganizationUUID,
                        serialLogonInfo.getClient());
                logonInfo.setHomeOrganization(homeOrganization);
            } catch (ServiceEntityConfigureException e) {
                // ignore
            }
        }
        logonInfo.setExtendedSettings(serialLogonInfo.getExtendedSettings());
        logonInfo.setLogonTryTimes(serialLogonInfo.getLogonTryTimes());
        logonInfo.setClient(serialLogonInfo.getClient());
        logonInfo.setStartLogonTime(serialLogonInfo.getStartLogonTime());
        logonInfo.setLanguageCode(serialLogonInfo.getLanguageCode());
        logonInfo.setLogOffTime(serialLogonInfo.getLogOffTime());
        logonInfo.setStatus(serialLogonInfo.getStatus());
        logonInfo.setRefUserUUID(serialLogonInfo.getRefUserUUID());
        logonInfo.setResOrgUUID(serialLogonInfo.getResOrgUUID());
        return logonInfo;
    }

    public void convLogonInfoToUI(LogonInfo logonInfo,
                                  LogonInfoUIModel logonInfoUIModel) {
        if (logonInfo != null && logonInfoUIModel != null) {
            logonInfoUIModel.setUuid(logonInfo.getUuid());
            logonInfoUIModel.setParentNodeUUID(logonInfo.getParentNodeUUID());
            logonInfoUIModel.setRootNodeUUID(logonInfo.getRootNodeUUID());
            logonInfoUIModel.setClient(logonInfo.getClient());
            logonInfoUIModel.setStatus(logonInfo.getStatus());
            if (logonInfo.getLogonUser() != null) {
                logonInfoUIModel.setLogonUserUUID(logonInfo.getLogonUser().getUuid());
                logonInfoUIModel.setLogonUserId(logonInfo.getLogonUser()
                        .getId());
                logonInfoUIModel.setLogonUserName(logonInfo.getLogonUser()
                        .getName());
            }
            if (logonInfo.getHomeOrganization() != null) {
                logonInfoUIModel.setHomeOrgId(logonInfo.getHomeOrganization()
                        .getId());
                logonInfoUIModel.setHomeOrgName(logonInfo.getHomeOrganization()
                        .getName());
            }
            if (logonInfo.getStartLogonTime() != null) {
                logonInfoUIModel
                        .setStartLogonTime(DefaultDateFormatConstant.DATE_MIN_FORMAT
                                .format(logonInfo.getStartLogonTime()));
            }
            Map<Integer, String> logonStatusMap;
            try {
                logonStatusMap = this.initLogonStatus(logonInfo.getLanguageCode());
                logonInfoUIModel.setStatusValue(logonStatusMap.get(logonInfo.getStatus()));
            } catch (ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "status"));
            }
        }
    }

    public ServiceEntityNode logonWithEquipment(String client, String id,
                                                String password, int triedTimes) throws LogonInfoException {
        return null;
    }

}
