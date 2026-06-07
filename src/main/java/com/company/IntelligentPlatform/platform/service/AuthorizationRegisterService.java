package com.company.IntelligentPlatform.platform.service;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.platform.model.AuthorizationObject;
import com.company.IntelligentPlatform.platform.model.Role;
import com.company.IntelligentPlatform.platform.model.RoleAuthorization;

/**
 * TODO-LEGACY: Stub replacing legacy platform.foundation.Administration.InstallService.AuthorizationRegisterService.
 */
@Component
public class AuthorizationRegisterService {

    public RoleAuthorization generateRoleAuthorization(Role role, AuthorizationObject authorizationObject,
            String logonUserUUID, String organizationUUID) {
        // TODO-LEGACY: implement actual authorization registration
        return null;
    }
}
