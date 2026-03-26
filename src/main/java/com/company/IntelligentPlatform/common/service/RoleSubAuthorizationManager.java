package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.RoleSubAuthorizationUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.ActionCode;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.RoleAuthorization;
import com.company.IntelligentPlatform.common.model.RoleSubAuthorization;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoleSubAuthorizationManager {

    public static final String METHOD_ConvAuthorizationObjectToSubUI = "convAuthorizationObjectToSubUI";

    public static final String METHOD_ConvUIToRoleSubAuthorization = "convUIToRoleSubAuthorization";

    public static final String METHOD_ConvRoleSubAuthorizationToUI = "convRoleSubAuthorizationToUI";

    @Autowired
    protected RoleManager roleManager;

    @Autowired
    protected RoleAuthorizationManager roleAuthorizationManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected AuthorizationObjectManager authorizationObjectManager;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    protected Logger logger = LoggerFactory.getLogger(RoleAuthorizationManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), RoleAuthorization.NODENAME,
                        request.getUuid(), RoleSubAuthorization.NODENAME, roleManager);
        docPageHeaderInputPara.setGenBaseModelList((DocPageHeaderModelProxy.GenBaseModelList<RoleAuthorization>) roleAuthorization -> {
            // How to get the base page header model list
            return roleAuthorizationManager.getPageHeaderModelList(new ServiceJSONRequest(roleAuthorization.getUuid(), roleAuthorization.getParentNodeUUID()), client);
        });
        docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<RoleSubAuthorization>) (roleSubAuthorization, pageHeaderModel) -> {
            // How to render current page header
            AuthorizationObject authorizationObject =
                    (AuthorizationObject) authorizationObjectManager.getEntityNodeByKey(roleSubAuthorization.getRefUUID(),
                            IServiceEntityNodeFieldConstant.UUID, AuthorizationObject.NODENAME,
                            roleSubAuthorization.getClient(), null);
            if (authorizationObject != null) {
                pageHeaderModel.setHeaderName(authorizationObject.getId());
            }
            return pageHeaderModel;
        });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public RoleSubAuthorization newSubAuthorizationByRefer(RoleAuthorization roleAuthorization,
                                                           RoleAuthorization referRoleAuthorization) throws ServiceEntityConfigureException {
        RoleSubAuthorization roleSubAuthorization = (RoleSubAuthorization) roleManager.newEntityNode(roleAuthorization,
                RoleSubAuthorization.NODENAME);
        copyToRoleSubAuthorization(referRoleAuthorization, roleSubAuthorization);
        return roleSubAuthorization;
    }

    public static void copyToRoleSubAuthorization(RoleAuthorization referRoleAuthorization,
                                             RoleSubAuthorization targetRoleSubAuthorization){
        if(referRoleAuthorization != null && targetRoleSubAuthorization != null){
            targetRoleSubAuthorization.setAuthorizationObjectType(referRoleAuthorization.getAuthorizationObjectType());
            targetRoleSubAuthorization.setActionCodeArray(referRoleAuthorization.getActionCodeArray());
            targetRoleSubAuthorization.setProcessType(referRoleAuthorization.getProcessType());
            targetRoleSubAuthorization.setRefNodeName(referRoleAuthorization.getRefNodeName());
            targetRoleSubAuthorization.setRefSEName(referRoleAuthorization.getRefSEName());
            targetRoleSubAuthorization.setRefUUID(referRoleAuthorization.getRefUUID());
            targetRoleSubAuthorization.setRefPackageName(referRoleAuthorization.getRefPackageName());
        }
    }

    public void convAuthorizationObjectToSubUI(
            AuthorizationObject authorizationObject,
            RoleSubAuthorizationUIModel roleSubAuthorizationUIModel) {
        convAuthorizationObjectToSubUI(authorizationObject, roleSubAuthorizationUIModel, null);
    }

    public void convAuthorizationObjectToSubUI(
            AuthorizationObject authorizationObject,
            RoleSubAuthorizationUIModel roleSubAuthorizationUIModel, LogonInfo logonInfo) {
        if (authorizationObject != null
                && roleSubAuthorizationUIModel != null) {
            roleSubAuthorizationUIModel
                    .setAuthorizationObjectType(authorizationObject
                            .getAuthorizationObjectType());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> authorizationObjectTypeMap = authorizationObjectManager
                            .initAuthorizationObjectTypeMap(logonInfo.getLanguageCode());
                    roleSubAuthorizationUIModel
                            .setAuthorizationObjectTypeValue(authorizationObjectTypeMap
                                    .get(authorizationObject
                                            .getAuthorizationObjectType()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
            roleSubAuthorizationUIModel.setSimObjectArray(authorizationObject.getSimObjectArray());
            roleSubAuthorizationUIModel.setId(authorizationObject.getId());
            roleSubAuthorizationUIModel.setName(authorizationObject
                    .getName());
        }
    }

    public void convRoleSubAuthorizationToUI(RoleSubAuthorization roleSubAuthorization,
                                             RoleSubAuthorizationUIModel roleSubAuthorizationUIModel, LogonInfo logonInfo) {
        convRoleSubAuthorizationToUI(roleSubAuthorization, roleSubAuthorizationUIModel, null, logonInfo);
    }

    public void convRoleSubAuthorizationToUI(RoleSubAuthorization roleSubAuthorization,
                                             RoleSubAuthorizationUIModel roleSubAuthorizationUIModel,
                                             List<ServiceEntityNode>  actionCodeList, LogonInfo logonInfo) {
        if (roleSubAuthorization != null && roleSubAuthorizationUIModel != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(roleSubAuthorization, roleSubAuthorizationUIModel);
            roleSubAuthorizationUIModel.setRefUUID(roleSubAuthorization
                    .getRefUUID());
            roleSubAuthorizationUIModel
                    .setActionCodeArray(ServiceCollectionsHelper
                            .convertStringToArray(roleSubAuthorization
                                    .getActionCodeArray()));
            roleSubAuthorizationUIModel.setRefSEName(roleSubAuthorization
                    .getRefSEName());
            roleSubAuthorizationUIModel.setRefNodeName(roleSubAuthorization
                    .getRefNodeName());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> processTypeMap =
                            roleAuthorizationManager.initProcessTypeMap(logonInfo.getLanguageCode());
                    roleSubAuthorizationUIModel
                            .setProcessTypeValue(processTypeMap
                                    .get(roleSubAuthorization.getProcessType()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "processType"));
                }
            }
            List<String> actionCodeValueList = new ArrayList<>();
            List<String> actionCodeIdList = new ArrayList<>();
            if(!ServiceCollectionsHelper.checkNullList(actionCodeList)){
                ServiceCollectionsHelper.forEachArray(roleSubAuthorizationUIModel.getActionCodeArray(), actionCodeUUID->{
                    ActionCode actionCode =
                            (ActionCode) ServiceCollectionsHelper.filterSENodeOnline(actionCodeUUID, actionCodeList);
                    if(actionCode != null){
                        actionCodeValueList.add(actionCode.getName());
                        actionCodeIdList.add(actionCode.getId());
                    }
                    return actionCodeUUID;
                });
                roleSubAuthorizationUIModel
                        .setActionCodeValueArray(ServiceCollectionsHelper
                                .convListToArray(actionCodeValueList));
                roleSubAuthorizationUIModel.setActionCodeIdArray(ServiceCollectionsHelper
                        .convListToArray(actionCodeIdList));
            }
        }
    }

    public void convUIToRoleSubAuthorization(
            RoleSubAuthorizationUIModel roleSubAuthorizationUIModel,
            RoleSubAuthorization roleSubAuthorization) {
        if (roleSubAuthorization != null && roleSubAuthorizationUIModel != null) {
            DocFlowProxy.convUIToServiceEntityNode(roleSubAuthorizationUIModel, roleSubAuthorization);
            if (!ServiceEntityStringHelper
                    .checkNullString(roleSubAuthorizationUIModel.getUuid())) {
                roleSubAuthorization.setRefUUID(roleSubAuthorizationUIModel
                        .getRefUUID());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(roleSubAuthorizationUIModel.getRefSEName())) {
                roleSubAuthorization.setRefSEName(roleSubAuthorizationUIModel
                        .getRefSEName());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(roleSubAuthorizationUIModel.getRefNodeName())) {
                roleSubAuthorization.setRefNodeName(roleSubAuthorizationUIModel
                        .getRefNodeName());
            }
            if (roleSubAuthorizationUIModel.getActionCodeArray() != null) {
                roleSubAuthorization.setActionCodeArray(ServiceCollectionsHelper
                        .convStringArrayToString(roleSubAuthorizationUIModel
                                .getActionCodeArray()));
            }
        }
    }

}
