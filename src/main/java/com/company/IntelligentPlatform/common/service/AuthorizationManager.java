package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.OrganizationFactoryService;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
// TODO-LEGACY: import platform.foundation.Administration.InstallService.AuthorizationRegisterService;
// TODO-LEGACY: import platform.foundation.Administration.InstallService.InstallServiceCommonTool;
// TODO-DAO: import platform.foundation.DAO.RoleDAO;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.SearchAuthorizationContext;
import com.company.IntelligentPlatform.common.service.SpringContextBeanService;
import com.company.IntelligentPlatform.common.service.SysAODetermineProxy;
import com.company.IntelligentPlatform.common.service.SysAODeterminer;
import com.company.IntelligentPlatform.common.service.SystemAuthorizationObjectManager;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Main entry class to check Authorization
 *
 * @author Zhang, Hang
 */
@Service
public class AuthorizationManager {

    @Autowired
    protected LogonUserManager logonUserManager;

    @Autowired
    protected RoleManager roleManager;

    @Autowired
    protected SystemAuthorizationObjectManager systemAuthorizationObjectManager;

    @Autowired
    protected SpringContextBeanService springContextBeanService;

    @Autowired
    protected ActionCodeManager actionCodeManager;

    @Autowired
    protected AuthorizationGroupManager authorizationGroupManager;

    @Autowired
    protected AuthorizationObjectManager authorizationObjectManager;

    // TODO-LEGACY: @Autowired
    @Autowired
    protected AuthorizationRegisterService authorizationRegisterService; // TODO-LEGACY: stub

    // TODO-DAO: @Autowired

    // TODO-DAO:     protected RoleDAO roleDAO;

    @Autowired
    protected SysAODetermineProxy sysAODetermineProxy;

    @Autowired
    protected OrganizationFactoryService organizationFactoryService;

    @Autowired
    protected OrganizationManager organizationManager;

    /**
     * Constant for compound authorization type to assign [counter] for resource
     * type AO with default action code list
     * <p>
     * by default will assign [edit][view][list] Action code to specified
     * resource type AO
     * <p>
     * for data access, for self user data, with all permission, for data in
     * same organization, with [list][view] authorization, for data in cross
     * organization, with [list] authorization
     */
    public static final int DEFCOM_AUTHTYPE_COUNTER = 1;

    /**
     * Constant for compound authorization type to assign [guest] for resource
     * type AO with default action code list
     * <p>
     * by default will assign [view][list] Action code to specified resource
     * type AO
     * <p>
     * for data access, for data only with [list] authorization
     */
    public static final int DEFCOM_AUTHTYPE_GUEST = 2;

    /**
     * Constant for compound authorization type to assign [manager] for resource
     * type AO with default action code list
     * <p>
     * by default will assign [edit][view][list][delete] Action code to
     * specified resource type AO
     * <p>
     * for data access, for self user data, with all permission, for data in
     * same organization, with [list][view][edit] authorization, for data in
     * cross organization, with [list][view] authorization
     */
    public static final int DEFCOM_AUTHTYPE_MANAGER = 3;

    /**
     * Constant for compound authorization type to assign [board manager] for
     * resource type AO with default action code list
     * <p>
     * by default will assign [edit][view][list][delete] Action code to
     * specified resource type AO
     * <p>
     * for data access, for self user data, with all permission, for data in
     * same organization, with [list][view][edit] authorization, for data in
     * cross organization, with [list][view][edit] authorization, for data in
     * lower organization, with [list][view][edit] authorization, for data in
     */
    public static final int DEFCOM_AUTHTYPE_BOARD_MANAGER = 4;

    public boolean checkResourceAuthorization(LogonUser logonUser,
                                              String targetAOID, String actionCode, ServiceEntityNode target)
            throws ServiceEntityConfigureException, LogonInfoException {
        return checkAuthorization(logonUser,
                AuthorizationObject.AUTH_TYPE_RESOURCE, targetAOID, actionCode,
                target, null, null);
    }

    public boolean checkSuperAuthorization(LogonUser logonUser)
            throws LogonInfoException, AuthorizationException {
        if (logonUser == null) {
            throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
        }
        if (logonUser.getId() != null
                && logonUser.getId().equals(
                InstallServiceCommonTool.ID_SUPERUSER)) {
            return true;
        }
        throw new AuthorizationException(
                AuthorizationException.TYPE_NO_AUTHORIZATION);
    }

    public void checkAuthorizationWrapper(LogonInfo logonInfo, String resourceId, String acId)
            throws AuthorizationException, ServiceEntityConfigureException, LogonInfoException {
        LogonUser logonUser = logonInfo.getLogonUser();
        if (logonUser == null) {
            throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
        }
        Organization homeOrganization = logonInfo.getHomeOrganization();
        List<ServiceEntityNode> organizationList = logonInfo.getOrganizationList();
        boolean accessFlag = checkAuthorization(logonUser,
                AuthorizationObject.AUTH_TYPE_RESOURCE, resourceId, acId, null,
                homeOrganization, organizationList);
        if (!accessFlag) {
            // Raise Authorization exception
            throw new AuthorizationException(
                    AuthorizationException.TYPE_NO_AUTHORIZATION);
        }
    }

    /**
     * Main entry point of checking authorization
     *
     * @param logonUser
     * @param aoType
     * @param targetAOID
     * @param actionCode
     * @param target
     * @return
     * @throws ServiceEntityConfigureException
     * @throws LogonInfoException
     */
    public boolean checkAuthorization(LogonUser logonUser, int aoType,
                                      String targetAOID, String actionCode, ServiceEntityNode target,
                                      Organization homeOrganization,
                                      List<ServiceEntityNode> organizationList)
            throws ServiceEntityConfigureException, LogonInfoException {
        if (logonUser == null) {
            throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
        }
        List<Role> roleList = logonUserManager.getRoleList(logonUser.getUuid(),
                logonUser.getClient());
        List<ServiceEntityNode> rawActionCodeList = actionCodeManager
                .getAllActionCodeList();
        List<ServiceEntityNode> rawAuthorizationGroupList = authorizationGroupManager
                .getAllAuthorizationGroupList();
        for (Role role : roleList) {
            List<ServiceEntityNode> roleAOList = roleManager
                    .getEntityNodeListByKey(role.getUuid(),
                            IServiceEntityNodeFieldConstant.PARENTNODEUUID,
                            RoleAuthorization.NODENAME, logonUser.getClient(),
                            null);
            if (getRoleAccess(logonUser, role.getUuid(), roleAOList,
                    targetAOID, actionCode, target,
                    rawAuthorizationGroupList, rawActionCodeList,
                    homeOrganization, organizationList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Main entry point of checking authorization online mode for authorization
     * access check in batch No DB access logic inside
     *
     * @param logonUser
     * @param aoType
     * @param rawRoleAuthorizationList
     * @param actionCode
     * @param target
     * @return
     * @throws ServiceEntityConfigureException
     * @throws LogonInfoException
     */
    public boolean checkAuthorizationOnline(LogonUser logonUser, int aoType,
                                            List<ServiceEntityNode> rawRoleAuthorizationList, String targetAOID,
                                            String actionCode, ServiceEntityNode target,
                                            List<ServiceEntityNode> rawAuthorizationGroupList,
                                            List<ServiceEntityNode> rawAuthorizationObjectList,
                                            List<ServiceEntityNode> rawActionCodeList,
                                            Organization homeOrganization,
                                            List<ServiceEntityNode> organizationList)
            throws ServiceEntityConfigureException, LogonInfoException {
        if (logonUser == null) {
            throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
        }
        List<Role> roleList = logonUserManager.getRoleList(logonUser.getUuid(),
                logonUser.getClient());
        for (Role role : roleList) {
            List<ServiceEntityNode> roleAOList = ServiceCollectionsHelper
                    .filterSENodeByParentUUID(role.getUuid(),
                            rawRoleAuthorizationList);
            if (ServiceCollectionsHelper.checkNullList(roleAOList)) {
                continue;
            }
            if (getRoleAccessOnline(logonUser, role.getUuid(), roleAOList,
                    targetAOID, actionCode, target,
                    rawAuthorizationGroupList, rawAuthorizationObjectList,
                    rawActionCodeList, homeOrganization, organizationList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Filter raw data list by current user's system authorization
     *
     * @param logonUser
     * @param rawDataList
     * @return
     * @throws ServiceEntityConfigureException
     * @throws LogonInfoException
     */
    public List<ServiceEntityNode> filterDataBySystemAuthorization(
            LogonUser logonUser, List<ServiceEntityNode> rawDataList,
            Organization organization, List<ServiceEntityNode> organizationList)
            throws ServiceEntityConfigureException, LogonInfoException {
        if (logonUser == null) {
            throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
        }
        if (rawDataList == null || rawDataList.size() == 0) {
            return null;
        }
        List<Role> roleList = logonUserManager.getRoleList(logonUser.getUuid(),
                logonUser.getClient());
        List<ServiceEntityNode> resultList = new ArrayList<>();
        List<SystemAuthorizationObject> sysAOList = roleManager
                .getSystemAOListByRoleList(roleList);
        if (sysAOList == null || sysAOList.size() == 0) {
            return null;
        }
        for (ServiceEntityNode rawSENode : rawDataList) {
            for (AuthorizationObject ao : sysAOList) {
                SystemAuthorizationObject systemAuthorizationObject = (SystemAuthorizationObject) ao;
                SysAODeterminer sysAODeterminer = sysAODetermineProxy
                        .getSystemAODeterminer(systemAuthorizationObject
                                .getDeterMineName());
                boolean hitFlag = sysAODeterminer.hitTarget(
                        logonUser.getUuid(), rawSENode, organization,
                        organizationList);
                if (hitFlag) {
                    resultList.add(rawSENode);
                    break;
                }
            }
        }
        return resultList;
    }

    public List<ServiceBasicKeyStructure> generateSearchCondition(SearchAuthorizationContext searchAuthorizationContext)
            throws LogonInfoException, ServiceEntityConfigureException {
        LogonUser logonUser = searchAuthorizationContext.getLogonUser();
        if (searchAuthorizationContext.getLogonUser() == null) {
            throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
        }
        List<Role> roleList = logonUserManager.getRoleList(logonUser.getUuid(),
                logonUser.getClient());
        List<SystemAuthorizationObject> sysAOList = roleManager
                .getSystemAOListByRoleList(roleList);
        List<ServiceBasicKeyStructure> keyStructureList = new ArrayList<>();
        for (AuthorizationObject ao : sysAOList) {
            SystemAuthorizationObject systemAuthorizationObject = (SystemAuthorizationObject) ao;
            SysAODeterminer sysAODeterminer = sysAODetermineProxy
                    .getSystemAODeterminer(systemAuthorizationObject
                            .getDeterMineName());
            ServiceBasicKeyStructure serviceBasicKeyStructure = sysAODeterminer.genKeyValueStructure(logonUser.getUuid(),
                    searchAuthorizationContext.getHomeOrganization(), searchAuthorizationContext.getOrganizationList());
            if (serviceBasicKeyStructure == null) {
                continue;
            }
            if (ServiceCollectionsHelper.checkNullList(serviceBasicKeyStructure.getMultipleValueList())) {
                // In case full system access
                return null;
            }
            ServiceCollectionsHelper.mergeToListUnit(keyStructureList, serviceBasicKeyStructure, tempObj->{
                ServiceBasicKeyStructure tmpBasicKeyStructure = (ServiceBasicKeyStructure) tempObj;
                return tmpBasicKeyStructure.getKeyName();
            });
        }
        return keyStructureList;
    }

    /**
     * Get all System Authorization object belongs to this logonUser
     *
     * @param logonUser
     * @return
     * @throws LogonInfoException
     * @throws ServiceEntityConfigureException
     */
    public List<SystemAuthorizationObject> getUserSystemAuthorizationObjectList(
            LogonUser logonUser) throws LogonInfoException,
            ServiceEntityConfigureException {
        if (logonUser == null) {
            throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
        }
        List<Role> roleList = logonUserManager.getRoleList(logonUser.getUuid(),
                logonUser.getClient());
        List<RoleAuthorization> roleAuthorList = roleManager
                .getRoleAuthorizationList(roleList);
        if (roleAuthorList == null || roleAuthorList.size() == 0) {
            return null;
        }
        List<ServiceEntityNode> allAuthorObjectList =
                authorizationObjectManager.getAllAuthorizationObjectList(logonUser.getClient());
        List<SystemAuthorizationObject> systemAuthorizationObjectList = new ArrayList<SystemAuthorizationObject>();
        if (roleAuthorList != null && roleAuthorList.size() > 0) {
            for (RoleAuthorization roleAuthorization : roleAuthorList) {
                AuthorizationObject ao = (AuthorizationObject) ServiceCollectionsHelper.filterOnline(allAuthorObjectList, seNode -> seNode.getUuid().equals(roleAuthorization.getRefUUID()));
                if (ao == null) {
                    continue;
                }
                if (ao.getAuthorizationObjectType() == AuthorizationObject.AUTH_TYPE_SYS) {
                    SystemAuthorizationObject systemAuthorizationObject = (SystemAuthorizationObject) ao;
                    systemAuthorizationObjectList
                            .add(systemAuthorizationObject);
                }
            }
        }
        return systemAuthorizationObjectList;
    }

    public List<ServiceEntityNode> filterDataAccessBySystemAuthor(
            List<ServiceEntityNode> rawList, String acid, LogonUser logonUser,
            Organization homeOrganization,
            List<ServiceEntityNode> organizationList)
            throws LogonInfoException, ServiceEntityConfigureException {
        if (rawList == null || rawList.size() == 0) {
            return new ArrayList<>();
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap = getAuthorizationACListMap(logonUser);
        for (ServiceEntityNode seNode : rawList) {
            boolean accessFlag = checkDataAccessBySystemAuthorization(logonUser, seNode,
                    acid, homeOrganization, organizationList, authorizationActionCodeMap);
            if (accessFlag) {
                resultList.add(seNode);
            }
        }
        return resultList;
    }

    public List<ServiceEntityNode> filterDataAccessByAuthorization(
            List<ServiceEntityNode> rawList, String aoId, String acid, LogonUser logonUser,
            Organization homeOrganization,
            List<ServiceEntityNode> organizationList, List<AuthorizationACUnion> authorizationACUnionList)
            throws LogonInfoException, ServiceEntityConfigureException {
        if (rawList == null || rawList.size() == 0) {
            return new ArrayList<>();
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : rawList) {
            boolean accessFlag = checkDataAccessByComAuthorization(logonUser, seNode,aoId,
                    acid, homeOrganization, organizationList, authorizationACUnionList);
            if (accessFlag) {
                resultList.add(seNode);
            }
        }
        return resultList;
    }

    public boolean checkDataAccessByComAuthorization(
            LogonUser logonUser,
            ServiceEntityNode targetNode,
            String targetAOId,
            String acId,
            Organization organization,
            List<ServiceEntityNode> organizationList,
            List<AuthorizationACUnion> authorizationACUnionList)
            throws ServiceEntityConfigureException, LogonInfoException {
        if (ServiceCollectionsHelper.checkNullList(authorizationACUnionList)) {
            return false;
        }
        List<AuthorizationObject> allAuthorizationObjectList =
                authorizationACUnionList.stream().map(AuthorizationACUnion::getAuthorizationObject).collect(Collectors.toList());
        /*
         * [Step1] Filter the relative resource authorization object
         */
        AuthorizationObject resourceAO = ServiceCollectionsHelper.filterOnline(allAuthorizationObjectList,
                authorizationObject -> {
                    return checkResourceAOAccess(authorizationObject, targetAOId);
                });
        if (resourceAO == null) {
            // In case can't find resource
            return false;
        }
        /*
         * [Step2] Check basic action code list
         */
        AuthorizationACUnion authorizationACUnion = filterAuthorizationACUnion(resourceAO,
                authorizationACUnionList);
        if (authorizationACUnion == null) {
            // this should not happen
            return false;
        }
        if (!getRoleAccessForResource(resourceAO, targetAOId, acId, authorizationACUnion.getActionCodeList())) {
            return false;
        }
        if (authorizationACUnion.getAuthorizationObject().getSystemAuthorCheck() == StandardSwitchProxy.SWITCH_OFF){
            // In case don't need to check system authorization
            return true;
        }
        /*
         * [Step3] Check system authorization
         */
        if (resourceAO.getSubSystemAuthorNeed() == StandardSwitchProxy.SWITCH_ON) {
            Map<AuthorizationObject, List<ActionCode>> subSystemAuthorMap =
                    authorizationACUnion.getSubSystemAuthorMap();
            return checkDataAccessBySystemAuthorization(logonUser, targetNode, acId, organization, organizationList,
                    subSystemAuthorMap);
        } else {
            // Check with default system authorization
            Map<AuthorizationObject, List<ActionCode>> defSystemAuthorMap =
                    filterToDefSystemAuthorMap(authorizationACUnionList);
            return checkDataAccessBySystemAuthorization(logonUser, targetNode, acId, organization, organizationList,
                    defSystemAuthorMap);
        }
    }

    private Map<AuthorizationObject, List<ActionCode>> filterToDefSystemAuthorMap(List<AuthorizationACUnion> authorizationACUnionList) {
        if (ServiceCollectionsHelper.checkNullList(authorizationACUnionList)) {
            return null;
        }
        Map<AuthorizationObject, List<ActionCode>> systemAuthorACMap = new HashMap<>();
        for (AuthorizationACUnion authorizationACUnion : authorizationACUnionList) {
            if (authorizationACUnion.getAuthorizationObject().getAuthorizationObjectType() != AuthorizationObject.AUTH_TYPE_SYS) {
                continue;
            }
            systemAuthorACMap.computeIfAbsent(authorizationACUnion.getAuthorizationObject(),
                    k -> authorizationACUnion.getActionCodeList());
        }
        return systemAuthorACMap;
    }

    public boolean checkDataAccessBySystemAuthorization(
            LogonUser logonUser,
            ServiceEntityNode targetNode,
            String acID,
            Organization organization,
            List<ServiceEntityNode> organizationList,
            Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap)
            throws ServiceEntityConfigureException, LogonInfoException {
        if (authorizationActionCodeMap == null
                || authorizationActionCodeMap.isEmpty()) {
            return false;
        }
        for (AuthorizationObject authorizationObject : authorizationActionCodeMap
                .keySet()) {
            // Traverse to process all the [Mandatory] cross-group type
            // Authorization Group
            if (authorizationObject.getAuthorizationObjectType() != AuthorizationObject.AUTH_TYPE_SYS) {
                continue;
            }
            SystemAuthorizationObject systemAuthorizationObject = (SystemAuthorizationObject) authorizationObject;
            List<ActionCode> actionCodeList = authorizationActionCodeMap
                    .get(authorizationObject);
            if (actionCodeList == null || actionCodeList.size() == 0) {
                continue;
            }
            boolean systemAOCheckFlag = getRoleAccessForSySAO(logonUser,
                    systemAuthorizationObject, acID, targetNode, organization,
                    organizationList, actionCodeList);
            if (systemAOCheckFlag) {
                return true;
            }
        }
        return false;
    }

    /**
     * Core Logic to load user's all authorization object mapping to AC list
     * Hashmap.
     *
     * @param logonUser
     * @return
     * @throws ServiceEntityConfigureException
     */
    public Map<AuthorizationObject, List<ActionCode>> getAuthorizationACListMap(
            LogonUser logonUser) throws ServiceEntityConfigureException {
        Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap = new HashMap<>();
        List<Role> roleList = logonUserManager.getRoleList(logonUser.getUuid(),
                logonUser.getClient());
        List<RoleAuthorization> roleAuthorList = roleManager
                .getRoleAuthorizationList(roleList);
        if (ServiceCollectionsHelper.checkNullList(roleAuthorList)) {
            return null;
        }
        for (RoleAuthorization roleAuthorization : roleAuthorList) {
            // Default system authorization should be series hit
            AuthorizationObject ao = roleManager
                    .getReferenceAO(roleAuthorization);
            if (ao == null) {
                continue;
            }
            List<ActionCode> acList = getActionCodeListByRoleAuthorization(
                    roleAuthorization);
            if (ao.getAuthorizationObjectType() == AuthorizationObject.AUTH_TYPE_SYS) {
                if (authorizationActionCodeMap.containsKey(ao)) {
                    List<ActionCode> backACList = authorizationActionCodeMap
                            .get(ao);
                    if (backACList == null) {
                        backACList = new ArrayList<>();
                    }
                    if (acList != null && acList.size() > 0) {
                        backACList.addAll(acList);
                    }
                } else {
                    authorizationActionCodeMap.put(ao, acList);
                }
            }
        }
        return authorizationActionCodeMap;
    }

    /**
     * Core Logic to load user's all authorization object mapping to AC list
     * Hashmap.
     *
     * @param logonUser
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<AuthorizationACUnion> getAuthorizationACList(
            LogonUser logonUser) throws ServiceEntityConfigureException {
        List<AuthorizationACUnion> resultList = new ArrayList<>();
        List<Role> roleList = logonUserManager.getRoleList(logonUser.getUuid(),
                logonUser.getClient());
        List<RoleAuthorization> roleAuthorList = roleManager
                .getRoleAuthorizationList(roleList);
        if (ServiceCollectionsHelper.checkNullList(roleAuthorList)) {
            return null;
        }
        List<ServiceEntityNode> allAuthorObjectList =
                authorizationObjectManager.getAllAuthorizationObjectList(logonUser.getClient());
        for (RoleAuthorization roleAuthorization : roleAuthorList) {
            // Default system authorization should be series hit
            AuthorizationObject ao = (AuthorizationObject) ServiceCollectionsHelper.filterOnline(
                    allAuthorObjectList, seNode -> seNode.getUuid().equals(roleAuthorization.getRefUUID()));
            if (ao == null) {
                continue;
            }
            List<ActionCode> acList = getActionCodeListByRoleAuthorization(
                    roleAuthorization);

            if (ao.getAuthorizationObjectType() == AuthorizationObject.AUTH_TYPE_RESOURCE) {
                if (ao.getSubSystemAuthorNeed() == StandardSwitchProxy.SWITCH_OFF) {
                    AuthorizationACUnion authorizationACUnion = new AuthorizationACUnion(ao, acList, null);
                    ServiceCollectionsHelper.mergeToListUnit(resultList,
                            authorizationACUnion, rawAuthorACUnion -> {
                                AuthorizationACUnion tmpAuthorizationACUnion = (AuthorizationACUnion) rawAuthorACUnion;
                                return tmpAuthorizationACUnion.getAuthorizationObject().getUuid();
                            });
                    continue; //?? continue
                }
                if (ao.getSubSystemAuthorNeed() == StandardSwitchProxy.SWITCH_ON) {
                    Map<AuthorizationObject, List<ActionCode>> subSystemAuthorMap =
                            calculateSubSystemAuthorMap(roleAuthorization, null);
                    AuthorizationACUnion authorizationACUnion = new AuthorizationACUnion(ao, acList, subSystemAuthorMap);
                    ServiceCollectionsHelper.mergeToListUnit(resultList,
                            authorizationACUnion, rawAuthorACUnion -> {
                                AuthorizationACUnion tmpAuthorizationACUnion = (AuthorizationACUnion) rawAuthorACUnion;
                                return tmpAuthorizationACUnion.getAuthorizationObject().getUuid();
                            });
                }
            }
            if (ao.getAuthorizationObjectType() == AuthorizationObject.AUTH_TYPE_SYS) {
                AuthorizationACUnion authorizationACUnion = new AuthorizationACUnion(ao, acList, null);
                ServiceCollectionsHelper.mergeToListUnit(resultList,
                        authorizationACUnion, rawAuthorACUnion -> {
                            AuthorizationACUnion tmpAuthorizationACUnion = (AuthorizationACUnion) rawAuthorACUnion;
                            return tmpAuthorizationACUnion.getAuthorizationObject().getUuid();
                        });
            }
        }
        return resultList;
    }

    private void processMergeACListToMap(Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap,
                                         AuthorizationObject ao, List<ActionCode> acList) {
        if (authorizationActionCodeMap.containsKey(ao)) {
            List<ActionCode> backACList = authorizationActionCodeMap
                    .get(ao);
            if (backACList == null) {
                backACList = new ArrayList<>();
            }
            if (!ServiceCollectionsHelper.checkNullList(acList)) {
                ServiceCollectionsHelper.mergeToList(backACList, acList, rawActionCode -> {
                    ActionCode actionCode = (ActionCode) rawActionCode;
                    return actionCode.getUuid();
                });
            }
        } else {
            authorizationActionCodeMap.put(ao, acList);
        }
    }

    public Map<AuthorizationObject, List<ActionCode>> calculateSubSystemAuthorMap(RoleAuthorization roleAuthorization
            , List<ServiceEntityNode> allAOList) throws ServiceEntityConfigureException {
        Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap = new HashMap<>();
        List<ServiceEntityNode> roleSubAuthorizationList =
                roleManager.getEntityNodeListByKey(roleAuthorization.getUuid(),
                        IServiceEntityNodeFieldConstant.PARENTNODEUUID, RoleSubAuthorization.NODENAME,
                        roleAuthorization.getClient(), null);
        if (ServiceCollectionsHelper.checkNullList(roleSubAuthorizationList)) {
            return null;
        }
        for (ServiceEntityNode serviceEntityNode : roleSubAuthorizationList) {
            RoleSubAuthorization roleSubAuthorization = (RoleSubAuthorization) serviceEntityNode;
            AuthorizationObject authorizationObject =
                    authorizationObjectManager.getAuthorizationObject(roleSubAuthorization.getRefUUID(),
                            roleSubAuthorization.getClient(), allAOList);
            List<ActionCode> acList = getActionCodeListByRoleSubAuthorization(
                    roleSubAuthorization);
            processMergeACListToMap(authorizationActionCodeMap, authorizationObject, acList);
        }
        return authorizationActionCodeMap;
    }

    public static AuthorizationACUnion filterAuthorizationACUnion(AuthorizationObject authorizationObject,
                                                                  List<AuthorizationACUnion> allAuthorizationACUnionList) {
        if (ServiceCollectionsHelper.checkNullList(allAuthorizationACUnionList)) {
            return null;
        }
        AuthorizationACUnion authorizationACUnion = ServiceCollectionsHelper.filterOnline(allAuthorizationACUnionList
                , tempAuthorACUnion -> tempAuthorACUnion.getAuthorizationObject().getUuid().equals(authorizationObject.getUuid()));
        return authorizationACUnion;
    }

    public static class AuthorizationACUnion {

        private AuthorizationObject authorizationObject;

        private List<ActionCode> actionCodeList;

        private Map<AuthorizationObject, List<ActionCode>> subSystemAuthorMap;

        public AuthorizationACUnion() {

        }

        public AuthorizationACUnion(AuthorizationObject authorizationObject, List<ActionCode> actionCodeList, Map<AuthorizationObject, List<ActionCode>> subSystemAuthorMap) {
            this.authorizationObject = authorizationObject;
            this.actionCodeList = actionCodeList;
            this.subSystemAuthorMap = subSystemAuthorMap;
        }

        public AuthorizationObject getAuthorizationObject() {
            return authorizationObject;
        }

        public void setAuthorizationObject(AuthorizationObject authorizationObject) {
            this.authorizationObject = authorizationObject;
        }

        public List<ActionCode> getActionCodeList() {
            return actionCodeList;
        }

        public void setActionCodeList(List<ActionCode> actionCodeList) {
            this.actionCodeList = actionCodeList;
        }

        public Map<AuthorizationObject, List<ActionCode>> getSubSystemAuthorMap() {
            return subSystemAuthorMap;
        }

        public void setSubSystemAuthorMap(Map<AuthorizationObject, List<ActionCode>> subSystemAuthorMap) {
            this.subSystemAuthorMap = subSystemAuthorMap;
        }
    }

    /**
     * Check Authorization access for each role
     *
     * @param logonUser
     * @param roleUUID
     * @param targetAOID
     * @param actionCode
     * @param target
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean getRoleAccess(LogonUser logonUser, String roleUUID,
                                 List<ServiceEntityNode> roleAOList,
                                 String targetAOID,
                                 String actionCode, ServiceEntityNode target,
                                 List<ServiceEntityNode> rawAuthorizationGroupList,
                                 List<ServiceEntityNode> rawActionCodeList,
                                 Organization homeOrganization,
                                 List<ServiceEntityNode> organizationList)
            throws ServiceEntityConfigureException {
        /*
         * [Step1] Get all AO list under this role, grouped by AO Group UUID
         */
        HashMap<String, HashMap<String, AuthorizationObject>> roleAGAOMap = roleManager
                .getOverallAuthorGroupObjectMap(roleUUID, roleAOList,
                        logonUser.getClient());
        if (roleAGAOMap.size() <= 0) {
            return false;
        }
        /*
         * [Step2] Get the Mandatory Authorization group object map by filter
         * overall group data
         */
        HashMap<String, HashMap<String, AuthorizationObject>> manAGAOMap = roleManager
                .getAuthorGroupObjectMapByCrossType(roleAGAOMap,
                        rawAuthorizationGroupList,
                        AuthorizationGroup.CROS_PROCESS_MANDATORY);
        boolean result = true;
        /**
         * [Step2.5] Process the [Mandatory] type Authorization groups
         */
        @SuppressWarnings("rawtypes")
        Iterator iterator = manAGAOMap.keySet().iterator();
        while (iterator.hasNext()) {
            // Traverse to process all the [Mandatory] cross-group type
            // Authorization Group
            String agUUID = (String) iterator.next();
            if (agUUID == null
                    || agUUID.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
                continue;
            }
            HashMap<String, AuthorizationObject> aoMap = manAGAOMap.get(agUUID);
            AuthorizationGroup authorizationGroup = (AuthorizationGroup) ServiceCollectionsHelper
                    .filterSENodeOnline(agUUID, rawAuthorizationGroupList);
            if (this.processAuthorizationGroup(logonUser, authorizationGroup,
                    roleAOList, aoMap, targetAOID, actionCode, target,
                    rawActionCodeList, homeOrganization,
                    organizationList)) {
                result = true;
                continue;
            } else {
                result = false;
                break;
            }
        }
        if (!result) {
            // if final result from [Mandatory] type Authorization group is
            // [false], just return
            return false;
        }
        /**
         * [Step3] Get the [Selective] Authorization group object map
         */
        // Before re-check the Selective AG group, reset the result = false
        result = false;
        HashMap<String, HashMap<String, AuthorizationObject>> selectAGAOMap = roleManager
                .getAuthorGroupObjectMapByCrossType(roleAGAOMap,
                        rawAuthorizationGroupList,
                        AuthorizationGroup.CROS_PROCESS_SELECTIVE);
        /**
         * [Step3.5]Process the [Mandatory] type Authorization groups
         */
        @SuppressWarnings("rawtypes")
        Iterator iterator2 = selectAGAOMap.keySet().iterator();
        while (iterator2.hasNext()) {
            // Traverse to process all the [Mandatory] cross-group type
            // Authorization Group
            String agUUID = (String) iterator2.next();
            if (agUUID == null
                    || agUUID.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
                continue;
            }
            HashMap<String, AuthorizationObject> aoMap = selectAGAOMap
                    .get(agUUID);
            AuthorizationGroup authorizationGroup = (AuthorizationGroup) ServiceCollectionsHelper
                    .filterSENodeOnline(agUUID, rawAuthorizationGroupList);
            if (this.processAuthorizationGroup(logonUser, authorizationGroup,
                    roleAOList, aoMap, targetAOID, actionCode, target,
                    rawActionCodeList, homeOrganization,
                    organizationList)) {
                result = true;
                break;
            } else {
                continue;
            }
        }
        return result;

    }

    /**
     * Check Authorization access for each role online mode for navigation
     * access check in batch No DB access logic inside
     *
     * @param logonUser
     * @param roleUUID
     * @param targetAOID
     * @param actionCode
     * @param target
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean getRoleAccessOnline(LogonUser logonUser, String roleUUID,
                                       List<ServiceEntityNode> roleAOList,
                                       String targetAOID,
                                       String actionCode, ServiceEntityNode target,
                                       List<ServiceEntityNode> rawAuthorizationGroupList,
                                       List<ServiceEntityNode> rawAuthorizationObjectList,
                                       List<ServiceEntityNode> rawActionCodeList,
                                       Organization homeOrganization,
                                       List<ServiceEntityNode> organizationList)
            throws ServiceEntityConfigureException {
        /**
         * [Step1] Get all AO list under this role, grouped by AO Group UUID
         */
        HashMap<String, HashMap<String, AuthorizationObject>> roleAGAOMap = roleManager
                .filterOverallAuthorGroupObjectMapOnline(roleUUID,
                        logonUser.getClient(), rawAuthorizationObjectList,
                        roleAOList);
        if (roleAGAOMap.size() <= 0) {
            return false;
        }
        /*
         * [Step2] Get the Mandatory Authorization group object map by filter
         * overall group data
         */
        HashMap<String, HashMap<String, AuthorizationObject>> manAGAOMap = roleManager
                .getAuthorGroupObjectMapByCrossType(roleAGAOMap,
                        rawAuthorizationGroupList,
                        AuthorizationGroup.CROS_PROCESS_MANDATORY);
        boolean result = true;
        /*
         * [Step2.5] Process the [Mandatory] type Authorization groups
         */
        @SuppressWarnings("rawtypes")
        Iterator iterator = manAGAOMap.keySet().iterator();
        while (iterator.hasNext()) {
            // Traverse to process all the [Mandatory] cross-group type
            // Authorization Group
            String agUUID = (String) iterator.next();
            if (agUUID == null
                    || agUUID.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
                continue;
            }
            HashMap<String, AuthorizationObject> aoMap = manAGAOMap.get(agUUID);
            AuthorizationGroup authorizationGroup = (AuthorizationGroup) ServiceCollectionsHelper
                    .filterSENodeOnline(agUUID, rawAuthorizationGroupList);
            if (this.processAuthorizationGroup(logonUser, authorizationGroup,
                    roleAOList, aoMap, targetAOID, actionCode, target,
                    rawActionCodeList, homeOrganization,
                    organizationList)) {
                result = true;
                continue;
            } else {
                result = false;
                break;
            }
        }
        if (!result) {
            // if final result from [Mandatory] type Authorization group is
            // [false], just return
            return false;
        }
        /**
         * [Step3] Get the [Selective] Authorization group object map
         */
        // Before re-check the Selective AG group, reset the result = false
        result = false;
        HashMap<String, HashMap<String, AuthorizationObject>> selectAGAOMap = roleManager
                .getAuthorGroupObjectMapByCrossType(roleAGAOMap,
                        rawAuthorizationGroupList,
                        AuthorizationGroup.CROS_PROCESS_SELECTIVE);
        /**
         * [Step3.5]Process the [Mandatory] type Authorization groups
         */
        @SuppressWarnings("rawtypes")
        Iterator iterator2 = selectAGAOMap.keySet().iterator();
        while (iterator2.hasNext()) {
            // Traverse to process all the [Mandatory] cross-group type
            // Authorization Group
            String agUUID = (String) iterator2.next();
            if (agUUID == null
                    || agUUID.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
                continue;
            }
            HashMap<String, AuthorizationObject> aoMap = selectAGAOMap
                    .get(agUUID);
            AuthorizationGroup authorizationGroup = (AuthorizationGroup) ServiceCollectionsHelper
                    .filterSENodeOnline(agUUID, rawAuthorizationGroupList);
            if (this.processAuthorizationGroup(logonUser, authorizationGroup,
                    roleAOList, aoMap, targetAOID, actionCode, target,
                    rawActionCodeList, homeOrganization,
                    organizationList)) {
                result = true;
                break;
            } else {
                continue;
            }
        }
        return result;

    }

    protected boolean processAuthorizationGroup(LogonUser logonUser,
                                                AuthorizationGroup authorizationGroup,
                                                List<ServiceEntityNode> roleAOList,
                                                HashMap<String, AuthorizationObject> aoMap, String targetAOID,
                                                String actionCode, ServiceEntityNode target,
                                                List<ServiceEntityNode> rawActionCodeList,
                                                Organization homeOrganization,
                                                List<ServiceEntityNode> organizationList)
            throws ServiceEntityConfigureException {
        boolean groupResult = false;
        if (authorizationGroup.getInnerProcessType() == AuthorizationGroup.INPROCESS_PARALLEL) {
            // Process each Authorization object in [Parallel] type
            @SuppressWarnings("rawtypes")
            Iterator iterator = aoMap.keySet().iterator();
            while (iterator.hasNext()) {
                String roleAOUUID = (String) iterator.next();
                AuthorizationObject ao = aoMap.get(roleAOUUID);
                RoleAuthorization roleAuthorization = (RoleAuthorization) ServiceCollectionsHelper
                        .filterSENodeOnline(roleAOUUID, roleAOList);
                List<ActionCode> actionCodeList = actionCodeManager
                        .filterActionCodeByActionCodeArray(roleAuthorization,
                                rawActionCodeList);
                if (actionCodeList == null || actionCodeList.size() == 0) {
                    continue;
                }
                if (!getAOAccessDispatcher(logonUser, ao, targetAOID,
                        actionCode, target, homeOrganization, organizationList,
                        actionCodeList)) {
                    continue;
                } else {
                    groupResult = true;
                    break;
                }
            }
        }
        if (authorizationGroup.getInnerProcessType() == AuthorizationGroup.INPROCESS_SERIAL) {
            // Process each Authorization object in [Serial] type
            @SuppressWarnings("rawtypes")
            Iterator iterator = aoMap.keySet().iterator();
            while (iterator.hasNext()) {
                String roleAOUUID = (String) iterator.next();
                AuthorizationObject ao = aoMap.get(roleAOUUID);
                RoleAuthorization roleAuthorization = (RoleAuthorization) ServiceCollectionsHelper
                        .filterSENodeOnline(roleAOUUID, roleAOList);
                List<ActionCode> actionCodeList = actionCodeManager
                        .filterActionCodeByActionCodeArray(roleAuthorization,
                                rawActionCodeList);
                if (actionCodeList == null || actionCodeList.size() == 0) {
                    groupResult = false;
                    break;
                }
                if (getAOAccessDispatcher(logonUser, ao, targetAOID,
                        actionCode, target, homeOrganization, organizationList,
                        actionCodeList)) {
                    continue;
                } else {
                    groupResult = false;
                    break;
                }
            }
        }
        return groupResult;
    }

    /**
     * [Internal method] Check Each AO Authorization object access
     *
     * @param logonUser
     * @param authorizationObject : Authorization object need to be check
     * @param targetAOID          : possible specified target Authorization object ID
     * @param actionCode          : target Authorization Action code
     * @param target              :possible target data
     * @return
     * @throws ServiceEntityConfigureException
     */
    protected boolean getAOAccessDispatcher(LogonUser logonUser,
                                            AuthorizationObject authorizationObject, String targetAOID,
                                            String actionCode, ServiceEntityNode target,
                                            Organization homeOrganization,
                                            List<ServiceEntityNode> organizationList,
                                            List<ActionCode> actionCodeList)
            throws ServiceEntityConfigureException {
        // Dispatch to detailed authorization type
        // In case the AO type is [Resource]
        if (authorizationObject.getAuthorizationObjectType() == AuthorizationObject.AUTH_TYPE_RESOURCE) {
            return getRoleAccessForResource(authorizationObject, targetAOID,
                    actionCode, actionCodeList);
        }
        // In case the AO type is [System AO]
        if (authorizationObject.getAuthorizationObjectType() == AuthorizationObject.AUTH_TYPE_SYS) {
            return getRoleAccessForSySAO(logonUser, authorizationObject,
                    actionCode, target, homeOrganization, organizationList,
                    actionCodeList);
        }
        return false;

    }

    /**
     * [Internal method] checking if the Target AO ID matches Authorization
     * object in sub Sim Object Array
     *
     * @param authorizationObject
     * @param targetAOID
     * @return
     */
    private static boolean checkAOMatchInSimObjectArray(
            AuthorizationObject authorizationObject, String targetAOID) {
        if (ServiceEntityStringHelper.checkNullString(authorizationObject
                .getSimObjectArray())) {
            return false;
        }
        String[] simAOArray = authorizationObject.getSimObjectArray()
                .split(",");
        if (simAOArray == null || simAOArray.length == 0) {
            return false;
        }
        boolean hitFlag = false;
        for (String rawAO : simAOArray) {
            if (targetAOID.equals(rawAO)) {
                hitFlag = true;
                break;
            }
        }
        return hitFlag;
    }

    /**
     * Core Logic to decide weather this resource Authorization Object match target AO Id
     *
     * @param authorizationObject
     * @param targetAOID
     * @return
     */
    public static boolean checkResourceAOAccess(AuthorizationObject authorizationObject, String targetAOID) {
        if (!targetAOID.equals(authorizationObject.getId())) {
            /*
             * [Step2] In case doesn't match directly by id.
             */
            return checkAOMatchInSimObjectArray(authorizationObject,
                    targetAOID);
        }
        return true;
    }

    /**
     * [Internal method] check role access for the [Resource type] AO
     *
     * @param authorizationObject
     * @param targetAOId
     * @param actionCode          *
     * @return
     * @throws ServiceEntityConfigureException
     */
    protected boolean getRoleAccessForResource(
            AuthorizationObject authorizationObject, String targetAOId,
            String actionCode, List<ActionCode> actionCodeList)
            throws ServiceEntityConfigureException {
        /*
         * [Step1] Check if match target ID for Authorization Object
         */
        if (!checkResourceAOAccess(authorizationObject, targetAOId)) {
            return false;
        }
        /*
         * [Step3] Check action code list matches or not
         */
        for (ActionCode actionCodeEntity : actionCodeList) {
            if (actionCodeEntity.getId().equals(actionCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * [Internal method] check role access for the [System AO type] AO
     *
     * @param logonUser
     * @param authorizationObject
     * @param target
     * @param actionCode          *
     * @return
     * @throws ServiceEntityConfigureException
     */
    protected boolean getRoleAccessForSySAO(LogonUser logonUser,
                                            AuthorizationObject authorizationObject, String actionCode,
                                            ServiceEntityNode target, Organization homeOrganization,
                                            List<ServiceEntityNode> organizationList,
                                            List<ActionCode> actionCodeList)
            throws ServiceEntityConfigureException {
        SystemAuthorizationObject systemAuthorizationObject = (SystemAuthorizationObject) authorizationObject;
        if (target == null) {
            // In case, don't need to check the system AO by target, return
            // truth
            return true;
        }
        if (actionCodeList == null || actionCodeList.size() == 0) {
            return false;
        }
        SysAODeterminer sysAODeterminer = sysAODetermineProxy
                .getSystemAODeterminer(systemAuthorizationObject
                        .getDeterMineName());
        if (sysAODeterminer.hitTarget(logonUser.getUuid(), target,
                homeOrganization, organizationList)) {
            // in case this Sys AO meets the requirement.check the action code
            for (ActionCode actionCodeEntity : actionCodeList) {
                if (actionCodeEntity.getId().equals(actionCode)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * [Internal method] get all action code list belongs to this role
     * authorization
     *
     * @param roleAuthorization
     * @return
     * @throws ServiceEntityConfigureException
     */
    protected List<ActionCode> getActionCodeListByRoleAuthorization(
            RoleAuthorization roleAuthorization)
            throws ServiceEntityConfigureException {
        List<ActionCode> resultList = new ArrayList<>();
        /*
         * [Step1] Get action code list from sub RoleAuthorizationActionCode node
         * This logic is deprecated
         */
//		List<ServiceEntityNode> roleACList = roleManager
//				.getEntityNodeListByKey(roleAuthorization.getUuid(),
//						IServiceEntityNodeFieldConstant.PARENTNODEUUID,
//						RoleAuthorizationActionCode.NODENAME, client, null);
//		if(!ServiceCollectionsHelper.checkNullList(roleACList)){
//			for (ServiceEntityNode seNode : roleACList) {
//				RoleAuthorizationActionCode roleAC = (RoleAuthorizationActionCode) seNode;
//				ActionCode actionCodeEntity = roleManager
//						.getRefActionCode((RoleAuthorizationActionCode) roleAC);
//				resultList.add(actionCodeEntity);
//			}
//		}
        /*
         * [Step2] Get action code list from field: [actionCodeArray]
         */
        if (!ServiceEntityStringHelper.checkNullString(roleAuthorization.getActionCodeArray())) {
            processMergeToActionCodeList(resultList, roleAuthorization.getActionCodeArray(),
                    roleAuthorization.getClient());
        }
        return resultList;
    }

    private void processMergeToActionCodeList(List<ActionCode> resultList, String rawActionCodeArray, String client) throws ServiceEntityConfigureException {
        String[] codeKeyArray = ServiceCollectionsHelper.convertStringToArray(rawActionCodeArray);
        List<String> actionCodeKeyList = ServiceCollectionsHelper.mergeStringArrayToList(codeKeyArray);
        List<ServiceEntityNode> actionCodeList = actionCodeManager
                .getEntityNodeListByMultipleKey(actionCodeKeyList, IServiceEntityNodeFieldConstant.UUID, ActionCode.NODENAME, client, null);
        if (!ServiceCollectionsHelper.checkNullList(actionCodeList)) {
            ServiceCollectionsHelper.mergeToList(resultList, actionCodeList, rawActionCode -> {
                ActionCode actionCode = (ActionCode) rawActionCode;
                return actionCode.getUuid();
            });
        }
    }

    /**
     * [Internal method] get all action code list belongs to this role
     * authorization
     *
     * @param roleSubAuthorization
     * @return
     * @throws ServiceEntityConfigureException
     */
    protected List<ActionCode> getActionCodeListByRoleSubAuthorization(
            RoleSubAuthorization roleSubAuthorization)
            throws ServiceEntityConfigureException {
        List<ActionCode> resultList = new ArrayList<>();
        if (!ServiceEntityStringHelper.checkNullString(roleSubAuthorization.getActionCodeArray())) {
            processMergeToActionCodeList(resultList, roleSubAuthorization.getActionCodeArray(),
                    roleSubAuthorization.getClient());
        }
        return resultList;
    }

//	/**
//	 * [Internal method] get all action code list belongs to this role
//	 * authorization
//	 *
//	 * @param roleAuthorization
//	 * @return
//	 * @throws ServiceEntityConfigureException
//	 */
//	protected List<ActionCode> getActionCodeListByRoleAuthorizationOnline(
//			RoleAuthorization roleAuthorization,
//			List<ServiceEntityNode> rawRoleACList,
//			List<ServiceEntityNode> rawActionCodeList)
//			throws ServiceEntityConfigureException {
//		List<ServiceEntityNode> roleACList = roleManager
//				.filterRoleACListOnline(roleAuthorization.getUuid(),
//						rawRoleACList);
//		if (roleACList == null || roleACList.size() == 0) {
//			return null;
//		}
//		List<ActionCode> resultList = new ArrayList<ActionCode>();
//		for (ServiceEntityNode seNode : roleACList) {
//			RoleAuthorizationActionCode roleAC = (RoleAuthorizationActionCode) seNode;
//			ActionCode actionCodeEntity = roleManager
//					.getRefActionCode((RoleAuthorizationActionCode) roleAC);
//			resultList.add(actionCodeEntity);
//		}
//		return resultList;
//	}

    /**
     * Dispatcher method to Assign the Authorization Objects to Role in
     * different default AO type and assign the different Action code list
     *
     * @param role
     * @param authorizationObject
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     */
    public void assignRoleResourceAODispatcher(Role role,
                                               AuthorizationObject authorizationObject, int defAuthType,
                                               String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException {
        if (defAuthType == DEFCOM_AUTHTYPE_COUNTER
                || defAuthType == DEFCOM_AUTHTYPE_MANAGER) {
            if (authorizationObject.getAuthorizationObjectType() == AuthorizationObject.AUTH_TYPE_RESOURCE) {
                RoleAuthorization roleAO = authorizationRegisterService
                        .generateRoleAuthorization(role, authorizationObject,
                                logonUserUUID, organizationUUID);
                generateActionCodeForResource(roleAO,
                        ISystemActionCode.ACID_LIST, logonUserUUID,
                        organizationUUID);
                generateActionCodeForResource(roleAO,
                        ISystemActionCode.ACID_VIEW, logonUserUUID,
                        organizationUUID);
                generateActionCodeForResource(roleAO,
                        ISystemActionCode.ACID_EDIT, logonUserUUID,
                        organizationUUID);
                if (defAuthType == DEFCOM_AUTHTYPE_MANAGER) {
                    generateActionCodeForResource(roleAO,
                            ISystemActionCode.ACID_DELETE, logonUserUUID,
                            organizationUUID);
                }
            }
        }
        if (defAuthType == DEFCOM_AUTHTYPE_GUEST) {
            if (authorizationObject.getAuthorizationObjectType() == AuthorizationObject.AUTH_TYPE_RESOURCE) {
                RoleAuthorization roleAO = authorizationRegisterService
                        .generateRoleAuthorization(role, authorizationObject,
                                logonUserUUID, organizationUUID);
                generateActionCodeForResource(roleAO,
                        ISystemActionCode.ACID_LIST, logonUserUUID,
                        organizationUUID);
                generateActionCodeForResource(roleAO,
                        ISystemActionCode.ACID_VIEW, logonUserUUID,
                        organizationUUID);
            }
        }
    }

    /**
     * The logic of assign standard system authorization to role by different
     * Authorization type [manager][counter][guest]
     *
     * @param role
     * @param defAuthType
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     */
    public void assignRoleSysAODispatcher(Role role, int defAuthType,
                                          String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException {
        SystemAuthorizationObject selfUserDataAO = (SystemAuthorizationObject) systemAuthorizationObjectManager
                .getEntityNodeByKey(
                        ISystemAuthorizationObject.AOID_SELFUSER_DATA,
                        IServiceEntityNodeFieldConstant.ID,
                        SystemAuthorizationObject.NODENAME, null, true);
        SystemAuthorizationObject selfOrgDataAO = (SystemAuthorizationObject) systemAuthorizationObjectManager
                .getEntityNodeByKey(
                        ISystemAuthorizationObject.AOID_SELFORG_DATA,
                        IServiceEntityNodeFieldConstant.ID,
                        SystemAuthorizationObject.NODENAME, null, true);
        SystemAuthorizationObject crossOrgDataAO = (SystemAuthorizationObject) systemAuthorizationObjectManager
                .getEntityNodeByKey(
                        ISystemAuthorizationObject.AOID_CROSSORG_DATA,
                        IServiceEntityNodeFieldConstant.ID,
                        SystemAuthorizationObject.NODENAME, null, true);
        SystemAuthorizationObject lowerOrgDataAO = (SystemAuthorizationObject) systemAuthorizationObjectManager
                .getEntityNodeByKey(
                        ISystemAuthorizationObject.AOID_LOWERORG_DATA,
                        IServiceEntityNodeFieldConstant.ID,
                        SystemAuthorizationObject.NODENAME, null, true);
        SystemAuthorizationObject exterOrgDataAO = (SystemAuthorizationObject) systemAuthorizationObjectManager
                .getEntityNodeByKey(
                        ISystemAuthorizationObject.AOID_EXTERORG_DATA,
                        IServiceEntityNodeFieldConstant.ID,
                        SystemAuthorizationObject.NODENAME, null, true);
        /*
         * In case [Manager] type, assign all default AC list to self user
         */
        if (defAuthType == DEFCOM_AUTHTYPE_MANAGER) {
            // In case [Manager] type, assign all default AC list to self user
            // data,
            // assign all default AC list to same org data, assign view, list to
            // cross org data
            // For self user data
            RoleAuthorization roleAOSelfUser = authorizationRegisterService
                    .generateRoleAuthorization(role, selfUserDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_VIEW, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_EDIT, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_DELETE, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_AUDITDOC, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_PRICEINFO, logonUserUUID,
                    organizationUUID);
            // For self org data
            RoleAuthorization roleAOSelfOrg = authorizationRegisterService
                    .generateRoleAuthorization(role, selfOrgDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_VIEW, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_EDIT, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_DELETE, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_AUDITDOC, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_PRICEINFO, logonUserUUID,
                    organizationUUID);
            // For lower org data
            RoleAuthorization roleAOLowerOrg = authorizationRegisterService
                    .generateRoleAuthorization(role, lowerOrgDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOLowerOrg,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOLowerOrg,
                    ISystemActionCode.ACID_VIEW, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOLowerOrg,
                    ISystemActionCode.ACID_EDIT, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOLowerOrg,
                    ISystemActionCode.ACID_DELETE, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOLowerOrg,
                    ISystemActionCode.ACID_AUDITDOC, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOLowerOrg,
                    ISystemActionCode.ACID_PRICEINFO, logonUserUUID,
                    organizationUUID);
            // For lower-external org data
            RoleAuthorization roleAOExternalOrg = authorizationRegisterService
                    .generateRoleAuthorization(role, exterOrgDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOExternalOrg,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOExternalOrg,
                    ISystemActionCode.ACID_VIEW, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOExternalOrg,
                    ISystemActionCode.ACID_EDIT, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOExternalOrg,
                    ISystemActionCode.ACID_DELETE, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOExternalOrg,
                    ISystemActionCode.ACID_AUDITDOC, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOExternalOrg,
                    ISystemActionCode.ACID_PRICEINFO, logonUserUUID,
                    organizationUUID);
            // For cross org data
            RoleAuthorization roleAOCrossOrg = authorizationRegisterService
                    .generateRoleAuthorization(role, crossOrgDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOCrossOrg,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOCrossOrg,
                    ISystemActionCode.ACID_VIEW, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOCrossOrg,
                    ISystemActionCode.ACID_EDIT, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOCrossOrg,
                    ISystemActionCode.ACID_DELETE, logonUserUUID,
                    organizationUUID);
        }
        /*
         * In case [Board Manager] type, assign all default AC list to self user
         */
        if (defAuthType == DEFCOM_AUTHTYPE_BOARD_MANAGER) {
            // In case [Manager] type, assign all default AC list to self user
            // data,
            // assign all default AC list to same org data, assign view, list to
            // cross org data
            // For self user data
            RoleAuthorization roleAOSelfUser = authorizationRegisterService
                    .generateRoleAuthorization(role, selfUserDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_VIEW, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_EDIT, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_DELETE, logonUserUUID,
                    organizationUUID);
            // For self org data
            RoleAuthorization roleAOSelfOrg = authorizationRegisterService
                    .generateRoleAuthorization(role, selfOrgDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_VIEW, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_EDIT, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_DELETE, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_AUDITDOC, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_PRICEINFO, logonUserUUID,
                    organizationUUID);
            // For lower org data
            RoleAuthorization roleAOLowerOrg = authorizationRegisterService
                    .generateRoleAuthorization(role, lowerOrgDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOLowerOrg,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOLowerOrg,
                    ISystemActionCode.ACID_VIEW, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOLowerOrg,
                    ISystemActionCode.ACID_EDIT, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOLowerOrg,
                    ISystemActionCode.ACID_DELETE, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOLowerOrg,
                    ISystemActionCode.ACID_AUDITDOC, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOLowerOrg,
                    ISystemActionCode.ACID_PRICEINFO, logonUserUUID,
                    organizationUUID);
            // For lower-external org data
            RoleAuthorization roleAOExternalOrg = authorizationRegisterService
                    .generateRoleAuthorization(role, exterOrgDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOExternalOrg,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOExternalOrg,
                    ISystemActionCode.ACID_VIEW, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOExternalOrg,
                    ISystemActionCode.ACID_EDIT, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOExternalOrg,
                    ISystemActionCode.ACID_DELETE, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOExternalOrg,
                    ISystemActionCode.ACID_AUDITDOC, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOExternalOrg,
                    ISystemActionCode.ACID_PRICEINFO, logonUserUUID,
                    organizationUUID);
            // For cross org data
            RoleAuthorization roleAOCrossOrg = authorizationRegisterService
                    .generateRoleAuthorization(role, crossOrgDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOCrossOrg,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOCrossOrg,
                    ISystemActionCode.ACID_VIEW, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOCrossOrg,
                    ISystemActionCode.ACID_EDIT, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOCrossOrg,
                    ISystemActionCode.ACID_DELETE, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOCrossOrg,
                    ISystemActionCode.ACID_AUDITDOC, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOCrossOrg,
                    ISystemActionCode.ACID_PRICEINFO, logonUserUUID,
                    organizationUUID);
        }
        /*
         * In case [Manager] type, assign all default AC list to self user
         */
        if (defAuthType == DEFCOM_AUTHTYPE_COUNTER) {
            // In case [Counter] type, assign all default AC list to self user
            // data,
            // assign [list][view] ACs to same org data, assign view, list to
            // cross org data
            // For self user data
            RoleAuthorization roleAOSelfUser = authorizationRegisterService
                    .generateRoleAuthorization(role, selfUserDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_VIEW, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_EDIT, logonUserUUID,
                    organizationUUID);
            // For cross org data
            RoleAuthorization roleAOSelfOrg = authorizationRegisterService
                    .generateRoleAuthorization(role, selfOrgDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_VIEW, logonUserUUID,
                    organizationUUID);

        }
        if (defAuthType == DEFCOM_AUTHTYPE_GUEST) {
            RoleAuthorization roleAOSelfUser = authorizationRegisterService
                    .generateRoleAuthorization(role, selfUserDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOSelfUser,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);
            // For cross org data
            RoleAuthorization roleAOSelfOrg = authorizationRegisterService
                    .generateRoleAuthorization(role, selfOrgDataAO,
                            logonUserUUID, organizationUUID);
            generateActionCodeForResource(roleAOSelfOrg,
                    ISystemActionCode.ACID_LIST, logonUserUUID,
                    organizationUUID);

        }
    }

    /*
     * [Internal method] generate the RoleAuthorization instance and link to the
     * Authorization object .
     * <p>
     * If such RoleAuthorization existed under this Role and point to the Same
     * AO, then delete it
     *
     * @param role
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     */
    // protected RoleAuthorization genearteRoleAuthorization(Role role,
    // AuthorizationObject authorizationObject, String logonUserUUID,
    // String organizationUUID) throws ServiceEntityConfigureException {
    // // clear the previous role authorization object under this role
    // List<ServiceEntityNode> roleAOListBack = roleManager
    // .getEntityNodeListByKey(role.getUuid(),
    // IServiceEntityNodeFieldConstant.PARENTNODEUUID,
    // RoleAuthorization.NODENAME, null);
    // if (roleAOListBack != null && roleAOListBack.size() > 0) {
    // for (ServiceEntityNode seNode : roleAOListBack) {
    // RoleAuthorization roleAuthorization = (RoleAuthorization) seNode;
    // if (authorizationObject.getUuid().equals(
    // roleAuthorization.getRefUUID())) {
    // // clear the roleAuthorizaton code object firstly
    // roleDAO.deleteEntityNodeByKey(seNode.getUuid(),
    // IServiceEntityNodeFieldConstant.PARENTNODEUUID,
    // RoleAuthorizationActionCode.NODENAME);
    // roleManager.deleteSENode(seNode, logonUserUUID,
    // organizationUUID);
    // }
    // }
    // }
    // RoleAuthorization roleAO = (RoleAuthorization) roleManager
    // .newEntityNode(role, RoleAuthorization.NODENAME);
    // roleManager.buildReferenceNode(authorizationObject, roleAO,
    // ServiceEntityFieldsHelper
    // .getCommonPackage(AuthorizationObject.class));
    // roleManager.insertSENode(roleAO, logonUserUUID, organizationUUID);
    // return roleAO;
    // }

    /**
     * [Internal method] generate the RoleAuthorizationActionCode instance, and
     * assign to specified RoleAuthorization.
     * <p>
     * If already RoleAuthorizationActionCode existed under this
     * RoleAuthorization AND point to same ActionCode, just ignore and return
     *
     * @param roleAuthorization
     * @param actionCodeID
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     */
    protected void generateActionCodeForResource(
            RoleAuthorization roleAuthorization, String actionCodeID,
            String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException {
        // Get AC for [VIEW]
        ActionCode actionCode = (ActionCode) actionCodeManager
                .getEntityNodeByKey(actionCodeID,
                        IServiceEntityNodeFieldConstant.ID,
                        ActionCode.NODENAME, null, true);
        if (actionCode == null) {
            return;
        }
        // The role authorization here is generated new
        List<ServiceEntityNode> roleACListBack = roleManager
                .getEntityNodeListByKey(roleAuthorization.getUuid(),
                        IServiceEntityNodeFieldConstant.PARENTNODEUUID,
                        RoleAuthorizationActionCode.NODENAME,
                        roleAuthorization.getClient(), null);
        if (roleACListBack != null && roleACListBack.size() > 0) {
            for (ServiceEntityNode seNode : roleACListBack) {
                RoleAuthorizationActionCode roleACBack = (RoleAuthorizationActionCode) seNode;
                if (roleACBack.getRefUUID().equals(actionCode.getUuid())) {
                    // In case there is role AC already linked to the actionCode
                    return;
                }
            }
        }
        RoleAuthorizationActionCode roleActionCode = (RoleAuthorizationActionCode) roleManager
                .newEntityNode(roleAuthorization,
                        RoleAuthorizationActionCode.NODENAME);
        roleManager.buildReferenceNode(actionCode, roleActionCode,
                ServiceEntityFieldsHelper.getCommonPackage(ActionCode.class));
        roleManager.updateActionCodeToRole(roleAuthorization,
                actionCode.getUuid());
        roleManager.insertSENode(roleActionCode, logonUserUUID,
                organizationUUID);
    }
}
