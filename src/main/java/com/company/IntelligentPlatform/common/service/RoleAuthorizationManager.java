package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.RoleAuthorizationUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModuleConvertPara;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleAuthorizationManager {

    public static final String METHOD_ConvRoleAuthorizationToUI = "convRoleAuthorizationToUI";

    public static final String METHOD_ConvAuthorizationObjectToUI = "convAuthorizationObjectToUI";

    public static final String METHOD_ConvUIToRoleAuthorization = "convUIToRoleAuthorization";

    public static final String METHOD_ConvAuthorizationGroupToUI = "convAuthorizationGroupToUI";
    
    @Autowired
    protected RoleManager roleManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected AuthorizationObjectManager authorizationObjectManager;

    @Autowired
    protected RoleSubAuthorizationManager roleSubAuthorizationManager;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    @Autowired
    protected ActionCodeManager actionCodeManager;

    private Map<String, Map<Integer, String>> processTypeMapLan = new HashMap<>();

    protected Logger logger = LoggerFactory.getLogger(RoleAuthorizationManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), Role.NODENAME,
                        request.getUuid(), RoleAuthorization.NODENAME, roleManager);
        docPageHeaderInputPara.setGenBaseModelList(new DocPageHeaderModelProxy.GenBaseModelList<Role>() {
            @Override
            public List<PageHeaderModel> execute(Role role) throws ServiceEntityConfigureException {
                // How to get the base page header model list
                List<PageHeaderModel> pageHeaderModelList =
                        docPageHeaderModelProxy.getDocPageHeaderModelList(role, null);
                return pageHeaderModelList;
            }
        });
        docPageHeaderInputPara.setGenHomePageModel(new DocPageHeaderModelProxy.GenHomePageModel<RoleAuthorization>() {
            @Override
            public PageHeaderModel execute(RoleAuthorization roleAuthorization, PageHeaderModel pageHeaderModel) throws ServiceEntityConfigureException {
                // How to render current page header
                AuthorizationObject authorizationObject =
                        (AuthorizationObject) authorizationObjectManager.getEntityNodeByKey(roleAuthorization.getRefUUID(),
                                IServiceEntityNodeFieldConstant.UUID, AuthorizationObject.NODENAME,
                                roleAuthorization.getClient(), null);
                if (authorizationObject != null) {
                    pageHeaderModel.setHeaderName(authorizationObject.getName());
                }
                return pageHeaderModel;
            }
        });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public Map<Integer, String> initProcessTypeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.processTypeMapLan, RoleAuthorizationUIModel.class,
                "processType");
    }

    /**
     * Get all system type role authorization list under specifield role
     * @param roleUUID
     * @param client
     * @return
     */
    public List<ServiceEntityNode> getAllSystemRoleAuthorList(String roleUUID, String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> roleAuthorizationList = roleManager.getEntityNodeListByKey(roleUUID,
                IServiceEntityNodeFieldConstant.PARENTNODEUUID, RoleAuthorization.NODENAME, client, null);
        if(ServiceCollectionsHelper.checkNullList(roleAuthorizationList)){
            return null;
        }
        return roleAuthorizationList.stream().filter(serviceEntityNode -> {
            RoleAuthorization roleAuthorization = (RoleAuthorization) serviceEntityNode;
            return SystemAuthorizationObject.SENAME.equals(roleAuthorization.getRefSEName());
        }).collect(Collectors.toList());
    }

    public List<ServiceEntityNode> getAllRoleSubAuthorizationList(String roleAuthorizationUUID, String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> roleSubAuthorizationList = roleManager.getEntityNodeListByKey(roleAuthorizationUUID,
                IServiceEntityNodeFieldConstant.PARENTNODEUUID, RoleSubAuthorization.NODENAME, client, null);
        return roleSubAuthorizationList;
    }

    /**
     * Logic to batch reset role sub authorization list from role's system role authorization list
     * @param roleAuthorization
     * @param overwrite
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     */
    public void batchResetRoleSubAuthorization(RoleAuthorization roleAuthorization, boolean overwrite,
                                     String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException {
        AuthorizationObject authorizationObject =
                (AuthorizationObject) authorizationObjectManager.getEntityNodeByKey(roleAuthorization.getRefUUID(),
                        IServiceEntityNodeFieldConstant.UUID, AuthorizationObject.NODENAME, roleAuthorization.getClient(),
                        null);
        if(authorizationObject.getSubSystemAuthorNeed() != StandardSwitchProxy.SWITCH_ON){
            return;
        }
        List<ServiceEntityNode> roleSysAuthorizationList =
                getAllSystemRoleAuthorList(roleAuthorization.getParentNodeUUID(), roleAuthorization.getClient());
        if(ServiceCollectionsHelper.checkNullList(roleSysAuthorizationList)){
            return;
        }
        List<ServiceEntityNode> existRoleSubAuthorizationList =
                getAllRoleSubAuthorizationList(roleAuthorization.getUuid()
                , roleAuthorization.getClient());
        for(ServiceEntityNode serviceEntityNode: roleSysAuthorizationList){
            RoleAuthorization tempRoleAuthorization = (RoleAuthorization) serviceEntityNode;
            ServiceEntityNode existRoleSubAuthorization =
                    ServiceCollectionsHelper.filterOnline(existRoleSubAuthorizationList, tmpRoleSubAuthorSE->{
                        RoleSubAuthorization roleSubAuthorization = (RoleSubAuthorization) tmpRoleSubAuthorSE;
                        return tempRoleAuthorization.getRefUUID().equals(roleSubAuthorization.getRefUUID());
                    });
            // check if sub already exist
            if(!overwrite){
                if(existRoleSubAuthorization != null){
                    continue;
                }
            }
            if(existRoleSubAuthorization != null){
                RoleSubAuthorizationManager.copyToRoleSubAuthorization(tempRoleAuthorization, (RoleSubAuthorization) existRoleSubAuthorization);
                roleManager.updateSENode(existRoleSubAuthorization, logonUserUUID, organizationUUID);
            } else {
                RoleSubAuthorization newRoleSubAuthor =
                        roleSubAuthorizationManager.newSubAuthorizationByRefer(roleAuthorization,
                                tempRoleAuthorization);
                roleManager.updateSENode(newRoleSubAuthor, logonUserUUID, organizationUUID);
            }
        }
    }

    public List<ServiceModuleConvertPara> genActionCodeParaList()
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> actionCodeList = actionCodeManager.getAllActionCodeList();
        ServiceModuleConvertPara serviceModuleConvertPara0 = new ServiceModuleConvertPara(RoleAuthorization.NODENAME,
                RoleAuthorization.NODENAME, actionCodeList);
        ServiceModuleConvertPara serviceModuleConvertPara1 = new ServiceModuleConvertPara(RoleSubAuthorization.NODENAME,
                RoleSubAuthorization.NODENAME, actionCodeList);
        return ServiceCollectionsHelper.asList(serviceModuleConvertPara0, serviceModuleConvertPara1);
    }

    public void convRoleAuthorizationToUI(RoleAuthorization roleAuthorization,
                                          RoleAuthorizationUIModel roleAuthorizationUIModel, LogonInfo logonInfo) {
        convRoleAuthorizationToUI(roleAuthorization, roleAuthorizationUIModel, null, logonInfo);
    }

    public void convRoleAuthorizationToUI(RoleAuthorization roleAuthorization,
                                          RoleAuthorizationUIModel roleAuthorizationUIModel,
                                          List<ServiceEntityNode> actionCodeList, LogonInfo logonInfo) {
        if (roleAuthorization != null && roleAuthorizationUIModel != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(roleAuthorization, roleAuthorizationUIModel);
            roleAuthorizationUIModel.setRefUUID(roleAuthorization
                    .getRefUUID());
            roleAuthorizationUIModel
                    .setActionCodeArray(ServiceCollectionsHelper
                            .convertStringToArray(roleAuthorization
                                    .getActionCodeArray()));
            roleAuthorizationUIModel.setRefSEName(roleAuthorization
                    .getRefSEName());
            roleAuthorizationUIModel.setRefNodeName(roleAuthorization
                    .getRefNodeName());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> processTypeMap = initProcessTypeMap(logonInfo.getLanguageCode());
                    roleAuthorizationUIModel
                            .setProcessTypeValue(processTypeMap
                                    .get(roleAuthorization.getProcessType()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "processType"));
                }
            }
            List<String> actionCodeValueList = new ArrayList<>();
            List<String> actionCodeIdList = new ArrayList<>();
            if(!ServiceCollectionsHelper.checkNullList(actionCodeList)){
                ServiceCollectionsHelper.forEachArray(roleAuthorizationUIModel.getActionCodeArray(), actionCodeUUID->{
                    ActionCode actionCode =
                            (ActionCode) ServiceCollectionsHelper.filterSENodeOnline(actionCodeUUID, actionCodeList);
                    if(actionCode != null){
                        actionCodeValueList.add(actionCode.getName());
                        actionCodeIdList.add(actionCode.getId());
                    }
                    return actionCodeUUID;
                });
                roleAuthorizationUIModel
                        .setActionCodeValueArray(ServiceCollectionsHelper
                                .convListToArray(actionCodeValueList));
                roleAuthorizationUIModel.setActionCodeIdArray(ServiceCollectionsHelper
                        .convListToArray(actionCodeIdList));
            }
        }
    }

    public void convUIToRoleAuthorization(
            RoleAuthorizationUIModel roleAuthorizationUIModel,
            RoleAuthorization roleAuthorization) {
        if (roleAuthorization != null && roleAuthorizationUIModel != null) {
            DocFlowProxy.convUIToServiceEntityNode(roleAuthorizationUIModel, roleAuthorization);
            if (!ServiceEntityStringHelper
                    .checkNullString(roleAuthorizationUIModel.getUuid())) {
                roleAuthorization.setRefUUID(roleAuthorizationUIModel
                        .getRefUUID());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(roleAuthorizationUIModel.getRefSEName())) {
                roleAuthorization.setRefSEName(roleAuthorizationUIModel
                        .getRefSEName());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(roleAuthorizationUIModel.getRefNodeName())) {
                roleAuthorization.setRefNodeName(roleAuthorizationUIModel
                        .getRefNodeName());
            }
            if (roleAuthorizationUIModel.getActionCodeArray() != null) {
                roleAuthorization.setActionCodeArray(ServiceCollectionsHelper
                        .convStringArrayToString(roleAuthorizationUIModel
                                .getActionCodeArray()));
            }
        }
    }

    public void convAuthorizationObjectToUI(
            AuthorizationObject authorizationObject,
            RoleAuthorizationUIModel roleAuthorizationUIModel) {
        convAuthorizationObjectToUI(authorizationObject, roleAuthorizationUIModel, null);
    }

    public void convAuthorizationObjectToUI(
            AuthorizationObject authorizationObject,
            RoleAuthorizationUIModel roleAuthorizationUIModel, LogonInfo logonInfo) {
        if (authorizationObject != null
                && roleAuthorizationUIModel != null) {
            roleAuthorizationUIModel
                    .setAuthorizationObjectType(authorizationObject
                            .getAuthorizationObjectType());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> authorizationObjectTypeMap = authorizationObjectManager
                            .initAuthorizationObjectTypeMap(logonInfo.getLanguageCode());
                    roleAuthorizationUIModel
                            .setAuthorizationObjectTypeValue(authorizationObjectTypeMap
                                    .get(authorizationObject
                                            .getAuthorizationObjectType()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "authorizationObjectType"));
                }
            }
            roleAuthorizationUIModel.setSubSystemAuthorNeed(authorizationObject.getSubSystemAuthorNeed());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> switchFlag = standardSwitchProxy.getSwitchMap(logonInfo.getLanguageCode());
                    roleAuthorizationUIModel.setSubSystemAuthorNeedLabel(switchFlag.get(authorizationObject.getSubSystemAuthorNeed()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "subSystemAuthorNeed"));
                }
            }
            roleAuthorizationUIModel.setRefAGUUID(authorizationObject.getRefAGUUID());
            roleAuthorizationUIModel.setSimObjectArray(authorizationObject.getSimObjectArray());
            roleAuthorizationUIModel.setId(authorizationObject.getId());
            roleAuthorizationUIModel.setName(authorizationObject
                    .getName());
        }
    }

    public void convAuthorizationGroupToUI(
            AuthorizationGroup authorizationGroup,
            RoleAuthorizationUIModel roleAuthorizationUIModel) {
        if (authorizationGroup != null
                && roleAuthorizationUIModel != null) {
            roleAuthorizationUIModel.setGroupName(authorizationGroup
                    .getName());
        }
    }

}
