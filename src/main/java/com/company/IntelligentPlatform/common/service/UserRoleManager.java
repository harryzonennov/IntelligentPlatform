package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.RoleMessageHelper;
import com.company.IntelligentPlatform.common.dto.LogonUserUIModel;
import com.company.IntelligentPlatform.common.dto.UserRoleUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.UserRole;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.io.IOException;

@Service
public class UserRoleManager {

    public static final String METHOD_convRoleToUserRoleUI = "convRoleToUserRoleUI";

    public static final String METHOD_convUIToUserRole = "convUIToUserRole";

    public static final String METHOD_convUserRoleUIToUserRole = "convUserRoleUIToUserRole";

    public static final String METHOD_convUserRoleToUserRoleUI = "convUserRoleToUserRoleUI";

    @Autowired
    protected RoleMessageHelper roleMessageHelper;

    public void convUIToUserRole(LogonUserUIModel logonUserUIModel,
                                 UserRole userRole) {
        if (userRole != null) {
            userRole.setRefUUID(logonUserUIModel.getRoleUUID());
            userRole.setRefNodeName(Role.NODENAME);
            userRole.setRefSEName(Role.SENAME);
            userRole.setRefPackageName(ServiceEntityFieldsHelper
                    .getCommonPackage(Role.class));
        }
    }


    public void convUserRoleUIToUserRole(UserRoleUIModel userRoleUIModel,
                                         UserRole rawEntity) throws ServiceEntityInstallationException,
            IOException {
        if (rawEntity != null) {
            DocFlowProxy.convUIToServiceEntityNode(userRoleUIModel, rawEntity);
            if (!ServiceEntityStringHelper.checkNullString(userRoleUIModel
                    .getRefUUID())) {
                rawEntity.setRefUUID(userRoleUIModel.getRefUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(userRoleUIModel
                    .getRefSEName())) {
                rawEntity.setRefSEName(userRoleUIModel.getRefSEName());
            } else {
                rawEntity.setRefSEName(Role.SENAME);
            }
            if (!ServiceEntityStringHelper.checkNullString(userRoleUIModel
                    .getRefNodeName())) {
                rawEntity.setRefNodeName(userRoleUIModel.getRefNodeName());
            } else {
                rawEntity.setRefNodeName(Role.NODENAME);
            }
        }
    }

    public void convUserRoleToUserRoleUI(UserRole userRole,
                                         UserRoleUIModel userRoleUIModel) {
        if (userRole != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(userRole, userRoleUIModel);
            userRoleUIModel.setRefSEName(userRole.getRefSEName());
            userRoleUIModel.setRefNodeName(userRole.getRefNodeName());
            userRoleUIModel.setRefUUID(userRole.getRefUUID());
        }
    }

    public void convRoleToUserRoleUI(Role role, UserRoleUIModel userRoleUIModel)
            throws ServiceEntityInstallationException, IOException {
        if (role != null) {
            userRoleUIModel.setRefUUID(role.getUuid());
            userRoleUIModel.setRoleId(role.getId());
            userRoleUIModel.setRoleName(role.getName());
            userRoleUIModel
                    .setRoleNote(roleMessageHelper.getNote(role.getId()));
        }
    }


}
