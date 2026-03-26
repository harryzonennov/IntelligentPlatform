package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.*;
import com.company.IntelligentPlatform.common.controller.ActionCodeJSONModel;
import com.company.IntelligentPlatform.common.controller.ActionCodeUUIDUnion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.RoleRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.SystemAuthorizationObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.LogonUserMessageException;
import com.company.IntelligentPlatform.common.service.LogonUserMessageManager;
import com.company.IntelligentPlatform.common.service.MessageTemplateManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.RoleConfigureProxy;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Logic Manager CLASS FOR Service Entity [Role]
 *
 * @author Zhang,Hang
 * @date Sun Jun 09 14:48:59 CST 2013
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class RoleManager extends ServiceEntityManager {

    public static final String METHOD_ConvRoleAuthorizationToComUI = "convRoleAuthorizationToComUI";

    public static final String METHOD_ConvRoleToUI = "convRoleToUI";

    public static final String METHOD_ConvUIToRole = "convUIToRole";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected RoleRepository roleDAO;
    @Autowired
    protected RoleConfigureProxy roleConfigureProxy;

    @Autowired
    protected AuthorizationGroupManager authorizationGroupManager;

    @Autowired
    protected AuthorizationObjectManager authorizationObjectManager;

    @Autowired
    protected SystemAuthorizationObjectManager systemAuthorizationObjectManager;

    @Autowired
    protected ActionCodeManager actionCodeManager;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected LogonUserMessageManager logonUserMessageManager;

    @Autowired
    protected MessageTemplateManager messageTemplateManager;

    @Autowired
    protected RoleMessageCategoryManager roleMessageCategoryManager;

    @Autowired
    protected LogonInfoManager logonInfoManager;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    @Autowired
    protected RoleSearchProxy roleSearchProxy;

    protected Logger logger = LoggerFactory.getLogger(RoleManager.class);

    Map<String, Map<String, String>> roleNameMapLan = new HashMap<>();

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, roleDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(roleConfigureProxy);
    }

    public List<PageHeaderModel> getPageHeaderModelList(Role role, String client)
            throws ServiceEntityConfigureException {
        List<PageHeaderModel> resultList = new ArrayList<>();
        if (role != null) {
            PageHeaderModel itemHeaderModel = getPageHeaderModel(role, 0);
            if (itemHeaderModel != null) {
                resultList.add(itemHeaderModel);
            }
        }
        return resultList;
    }

    public PageHeaderModel getPageHeaderModel(Role role, int index) throws ServiceEntityConfigureException {
        PageHeaderModel pageHeaderModel = new PageHeaderModel();
        pageHeaderModel.setPageTitle("roleTitle");
        pageHeaderModel.setHeaderName(role.getId() + "-" + role.getName());
        pageHeaderModel.setNodeInstId(Role.SENAME);
        pageHeaderModel.setUuid(role.getUuid());
        pageHeaderModel.setIndex(index);
        return pageHeaderModel;
    }

    /**
     * Get system all role authorization list
     *
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getAllRoleAuthorizationList(String client)
            throws ServiceEntityConfigureException {
        return getEntityNodeListByKey(null, null, RoleAuthorization.NODENAME,
                client, null);
    }

    /**
     * Get the overall Authorization Group contains Authorization object
     * Authorization Object Map under this role UUID
     *
     * @param roleUUID
     * @return
     * @throws ServiceEntityConfigureException
     */
    public HashMap<String, HashMap<String, AuthorizationObject>> getOverallAuthorGroupObjectMap(
            String roleUUID, List<ServiceEntityNode> rawRoleAOList,
            String client) throws ServiceEntityConfigureException {
        HashMap<String, HashMap<String, AuthorizationObject>> result = new HashMap<>();
        for (ServiceEntityNode roleAOEntity : rawRoleAOList) {
            AuthorizationObject ao = getReferenceAO((RoleAuthorization) roleAOEntity);
            if (ao == null) {
                continue;
            }
            if (result.containsKey(ao.getRefAGUUID())) {
                HashMap<String, AuthorizationObject> tmpAOMap = result.get(ao
                        .getRefAGUUID());
                tmpAOMap.put(roleAOEntity.getUuid(), ao);
                result.put(ao.getRefAGUUID(), tmpAOMap);
            } else {
                HashMap<String, AuthorizationObject> tmpAOMap = new HashMap<String, AuthorizationObject>();
                tmpAOMap.put(roleAOEntity.getUuid(), ao);
                result.put(ao.getRefAGUUID(), tmpAOMap);
            }
        }
        return result;
    }

    /**
     * Get the overall Authorization Group contains Authorization object
     * Authorization Object Map under this role UUID
     *
     * @param roleUUID
     * @return
     * @throws ServiceEntityConfigureException
     */
    public HashMap<String, HashMap<String, AuthorizationObject>> filterOverallAuthorGroupObjectMapOnline(
            String roleUUID, String client,
            List<ServiceEntityNode> rawAuthorizationObjectList,
            List<ServiceEntityNode> rawRoleAOList)
            throws ServiceEntityConfigureException {
        HashMap<String, HashMap<String, AuthorizationObject>> result = new HashMap<String, HashMap<String,
                AuthorizationObject>>();
        for (ServiceEntityNode roleAOEntity : rawRoleAOList) {
            RoleAuthorization roleAuthorization = (RoleAuthorization) roleAOEntity;
            AuthorizationObject ao = (AuthorizationObject) ServiceCollectionsHelper
                    .filterSENodeOnline(roleAuthorization.getRefUUID(),
                            rawAuthorizationObjectList);
            if (result.containsKey(ao.getRefAGUUID())) {
                HashMap<String, AuthorizationObject> tmpAOMap = result.get(ao
                        .getRefAGUUID());
                tmpAOMap.put(roleAOEntity.getUuid(), ao);
                result.put(ao.getRefAGUUID(), tmpAOMap);
            } else {
                HashMap<String, AuthorizationObject> tmpAOMap = new HashMap<String, AuthorizationObject>();
                tmpAOMap.put(roleAOEntity.getUuid(), ao);
                result.put(ao.getRefAGUUID(), tmpAOMap);
            }
        }
        return result;
    }

    /**
     * Get all Non duplicate role authorization list by role list
     *
     * @param roleList
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<RoleAuthorization> getRoleAuthorizationList(List<Role> roleList)
            throws ServiceEntityConfigureException {
        if(ServiceCollectionsHelper.checkNullList(roleList)){
            return null;
        }
        List<String> roleUUIDList = roleList.stream().map(ServiceEntityNode::getUuid).collect(Collectors.toList());
        List<ServiceEntityNode> rawRoleAuthorizationList = this.getEntityNodeListByMultipleKey(roleUUIDList,
                IServiceEntityNodeFieldConstant.PARENTNODEUUID, RoleAuthorization.NODENAME,
                roleList.get(0).getClient(), null);
        if (ServiceCollectionsHelper.checkNullList(rawRoleAuthorizationList)) {
            return null;
        }
        List<RoleAuthorization> resultList = new ArrayList<>();
        // remove duplicate refUUID
        for(ServiceEntityNode seNode: rawRoleAuthorizationList){
            ServiceCollectionsHelper.mergeToListUnit(resultList, seNode, tempObj->{
                RoleAuthorization roleAuthorization = (RoleAuthorization) tempObj;
                return roleAuthorization.getRefUUID();
            });
        }
        return resultList;
    }

    public List<SystemAuthorizationObject> getSystemAOListByRoleList(
            List<Role> roleList) throws ServiceEntityConfigureException {
        if (roleList == null || roleList.size() == 0) {
            return null;
        }
        List<RoleAuthorization> roleAuthorList = getRoleAuthorizationList(roleList);
        List<SystemAuthorizationObject> resultList = new ArrayList<SystemAuthorizationObject>();
        if (roleAuthorList != null && roleAuthorList.size() > 0) {
            for (RoleAuthorization roleAuthorization : roleAuthorList) {
                AuthorizationObject ao = getReferenceAO(roleAuthorization);
                if (ao == null) {
                    continue;
                }
                if (ao.getAuthorizationObjectType() == AuthorizationObject.AUTH_TYPE_SYS) {
                    resultList.add((SystemAuthorizationObject) ao);
                }
            }
        }
        return resultList;
    }

    public void preClearRoleAO(String roleUUID, String client) throws ServiceEntityConfigureException {
        admDeleteEntityByKey(roleUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID, RoleAuthorization.NODENAME);
        admDeleteEntityByKey(roleUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                RoleAuthorizationActionCode.NODENAME);

    }

    /**
     * [Internal method] get the reference Authorization object (all possible
     * AO) by role authorization
     *
     * @param roleAuthorization
     * @return
     * @throws ServiceEntityConfigureException
     */
    public AuthorizationObject getReferenceAO(
            RoleAuthorization roleAuthorization)
            throws ServiceEntityConfigureException {
        // In case a System AO
        if (SystemAuthorizationObject.SENAME.equals(roleAuthorization
                .getRefSEName())) {
            SystemAuthorizationObject ao = (SystemAuthorizationObject) systemAuthorizationObjectManager
                    .getEntityNodeByKey(roleAuthorization.getRefUUID(),
                            IServiceEntityNodeFieldConstant.UUID,
                            SystemAuthorizationObject.NODENAME, null);
            if (ao != null) {
                return ao;
            }
        }
        // By default using standard AO
        AuthorizationObject ao = (AuthorizationObject) authorizationObjectManager
                .getEntityNodeByKey(roleAuthorization.getRefUUID(),
                        IServiceEntityNodeFieldConstant.UUID,
                        AuthorizationObject.NODENAME, null);
        if (ao != null) {
            return ao;
        }
        return null;
    }

    /**
     * [Internal method] get the reference Authorization object (all possible
     * AO) by role authorization
     *
     * @param uuidList
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getReferenceAOBatch(
            List<String> uuidList, String client)
            throws ServiceEntityConfigureException {
        // By default using standard AO
        List<ServiceEntityNode> aoList = authorizationObjectManager
                .getEntityNodeListByMultipleKey(uuidList,
                        IServiceEntityNodeFieldConstant.UUID,
                        AuthorizationObject.NODENAME, client, null);
        return aoList;
    }

    /**
     * [Internal method] get the reference Authorization object (all possible
     * AO) by role authorization
     *
     * @param roleAuthorizationActionCode
     * @return
     * @throws ServiceEntityConfigureException
     */
    @Deprecated
    public ActionCode getRefActionCode(
            RoleAuthorizationActionCode roleAuthorizationActionCode)
            throws ServiceEntityConfigureException {
        if (ActionCode.SENAME
                .equals(roleAuthorizationActionCode.getRefSEName())) {
            ActionCode actionCode = (ActionCode) actionCodeManager
                    .getEntityNodeByKey(
                            roleAuthorizationActionCode.getRefUUID(),
                            IServiceEntityNodeFieldConstant.UUID,
                            ActionCode.NODENAME, null);
            return actionCode;
        }
        return null;
    }

    /**
     * Filter the Authorization Group - Object Map with Cross Group process type
     *
     * @param rawMap
     * @param crossType
     * @return
     * @throws ServiceEntityConfigureException
     */
    public HashMap<String, HashMap<String, AuthorizationObject>> getAuthorGroupObjectMapByCrossType(
            HashMap<String, HashMap<String, AuthorizationObject>> rawMap,
            List<ServiceEntityNode> rawAuthorizationGroupList, int crossType)
            throws ServiceEntityConfigureException {
        HashMap<String, HashMap<String, AuthorizationObject>> result = new HashMap<String, HashMap<String,
                AuthorizationObject>>();
        if (rawMap.size() == 0) {
            return result;
        }
        @SuppressWarnings("rawtypes")
        Iterator iterator = rawMap.keySet().iterator();
        while (iterator.hasNext()) {
            // Traverse each element in roleAGMap
            String agUUID = (String) iterator.next();
            if (agUUID == null
                    || agUUID.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
                continue;
            }
            AuthorizationGroup authorizationGroup = (AuthorizationGroup) ServiceCollectionsHelper
                    .filterSENodeOnline(agUUID, rawAuthorizationGroupList);
            if (authorizationGroup.getCrossGroupProcessType() == crossType) {
                result.put(agUUID, rawMap.get(agUUID));
            }
        }
        return result;
    }

    /**
     * This method will be invoked by system installation program, by generating
     * the system role message category instance
     *
     * @param role
     * @param messageCategory
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     */
    public void generateRoleMessageCategory(Role role, Integer messageCategory,
                                            String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException {
        // if such role already exist, just return;
        List<ServiceEntityNode> roleMessageListBack = getEntityNodeListByKey(
                role.getUuid(), IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                RoleMessageCategory.NODENAME, role.getClient(), null);
        int processFlag = ServiceEntityBindModel.PROCESSMODE_CREATE;
        if (roleMessageListBack != null && roleMessageListBack.size() > 0) {
            for (ServiceEntityNode seNode : roleMessageListBack) {
                RoleMessageCategory roleMessageCategory = (RoleMessageCategory) seNode;
                if (roleMessageCategory.getMessageCategory() == messageCategory) {
                    processFlag = ServiceEntityBindModel.PROCESSMODE_UPDATE;
                    break;
                }
            }
        }
        // register into persistence
        if (processFlag == ServiceEntityBindModel.PROCESSMODE_CREATE) {
            RoleMessageCategory roleMessageCategory = (RoleMessageCategory) newEntityNode(
                    role, RoleMessageCategory.NODENAME);
            roleMessageCategory.setMessageCategory(messageCategory);
            insertSENode(roleMessageCategory, logonUserUUID, organizationUUID);
        } else {
            // updateSENode(role, logonUserUUID, organizationUUID);
        }
    }

    //TODO to be replaced by the
    public void deleteAuthorizationObject(String baseUUID, String uuid,
                                          String logonUserUUID, String organizationUUID, String client)
            throws ServiceEntityConfigureException, AuthorizationException {
        // Pre-check
        Role role = (Role) this.getEntityNodeByKey(baseUUID,
                IServiceEntityNodeFieldConstant.UUID, Role.NODENAME, client,
                null);
        if (role == null) {
            throw new AuthorizationException(
                    AuthorizationException.TYPE_SYSTEM_WRONG);
        }
        RoleAuthorization roleAuthorization = (RoleAuthorization) getEntityNodeByKey(
                uuid, IServiceEntityNodeFieldConstant.UUID,
                RoleAuthorization.NODENAME, client, null);
        if (roleAuthorization == null) {
            throw new AuthorizationException(
                    AuthorizationException.TYPE_SYSTEM_WRONG);
        }
        // Delete the relative action code list
        List<ServiceEntityNode> roleActionCodeList = this
                .getEntityNodeListByKey(roleAuthorization.getUuid(),
                        IServiceEntityNodeFieldConstant.PARENTNODEUUID,
                        RoleAuthorizationActionCode.NODENAME, client, null);
        if (roleActionCodeList != null) {
            for (ServiceEntityNode tmpNode : roleActionCodeList) {
                this.deleteSENode(tmpNode, logonUserUUID, organizationUUID);
            }
        }
        // Delete the reference AO instance
        this.deleteSENode(roleAuthorization, logonUserUUID, organizationUUID);
    }

    public boolean containsActionCode(RoleAuthorization roleAuthorization, String actionCodeUUID) {
        if (ServiceEntityStringHelper.checkNullString(roleAuthorization.getActionCodeArray())) {
            return false;
        }
        return roleAuthorization.getActionCodeArray().contains(actionCodeUUID);
    }

    /**
     * Insert or update action code key to RoleAuthorization code array with duplicate key check.
     *
     * @param roleAuthorization
     * @param actionCodeUUID
     */
    public void updateActionCodeToRole(RoleAuthorization roleAuthorization, String actionCodeUUID) {
        if (containsActionCode(roleAuthorization, actionCodeUUID)) {
            return;
        }
        String actionCodeArray = roleAuthorization.getActionCodeArray();
        if (ServiceEntityStringHelper.checkNullString(actionCodeArray)) {
            actionCodeArray = actionCodeUUID;
        } else {
            actionCodeArray = actionCodeArray + ',' + actionCodeUUID;
        }
        roleAuthorization.setActionCodeArray(actionCodeArray);

    }

    public void convRoleToUI(Role role, RoleUIModel roleUIModel) {
        convRoleToUI(role, roleUIModel, null);
    }

    public void convRoleToUI(Role role, RoleUIModel roleUIModel, LogonInfo logonInfo) {
        if (role != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(role, roleUIModel);
            roleUIModel.setDefaultPageUrl(role.getDefaultPageUrl());
        }
    }

    public void convUIToRole(RoleUIModel roleUIModel, Role rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(roleUIModel, rawEntity);
        rawEntity.setDefaultPageUrl(roleUIModel.getDefaultPageUrl());
    }

    private Map<String, String> initRoleNameMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefaultLanguageStrMap(languageCode, this.roleNameMapLan, lanCode -> {
            try {
                String path = RoleUIModel.class.getResource("").getPath();
                Map<String, String> tempMessageMap = serviceDropdownListHelper
                        .getStrStaticDropDownMap(path + "RoleNote", lanCode);
                return tempMessageMap;
            } catch (IOException e) {
                return null;
            }
        });
    }

    public String getDefaultRoleName(String roleId, String languageCode) throws IOException, ServiceEntityInstallationException {
        Map<String, String> nameMap = this.initRoleNameMap(languageCode);
        if (nameMap != null) {
            return nameMap.get(roleId);
        }
        return null;
    }

    //TODO to check if it is still neccessary?
    public RoleServiceUIModel postRefreshServiceUIModel(
                                                        RoleServiceUIModel roleServiceUIModel, LogonInfo logonInfo) {
        /*
         * [Step1] process role message category list
         */
        List<RoleMessageCategoryServiceUIModel> roleMessageCategoryUIModelList =
                roleServiceUIModel.getRoleMessageCategoryUIModelList();
        try {
            ServiceCollectionsHelper.traverseListInterrupt(roleMessageCategoryUIModelList,
                    roleMessageCategoryServiceUIModel -> {
                        RoleMessageCategoryUIModel roleMessageCategoryUIModel =
                                roleMessageCategoryServiceUIModel.getRoleMessageCategoryUIModel();
                        roleMessageCategoryManager.postConvMessageTemplateToUI(roleMessageCategoryUIModel.getRefUUID(),
                                roleMessageCategoryUIModel, logonInfo);
                        return true;
            });
        } catch (DocActionException e) {
            logger.warn("DocActionException during role processing, skipping", e);
        }
        return roleServiceUIModel;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return this.roleSearchProxy;
    }

}
