package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.RoleAuthorization;
import com.company.IntelligentPlatform.common.model.RoleSubAuthorization;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleSubAuthorizationServiceUIModel extends ServiceUIModule {

    @IServiceUIModuleFieldConfig(nodeName = RoleSubAuthorization.NODENAME, nodeInstId = RoleSubAuthorization.NODENAME)
    protected RoleSubAuthorizationUIModel roleSubAuthorizationUIModel;

    public RoleSubAuthorizationUIModel getRoleSubAuthorizationUIModel() {
        return roleSubAuthorizationUIModel;
    }

    public void setRoleSubAuthorizationUIModel(RoleSubAuthorizationUIModel roleSubAuthorizationUIModel) {
        this.roleSubAuthorizationUIModel = roleSubAuthorizationUIModel;
    }
}
