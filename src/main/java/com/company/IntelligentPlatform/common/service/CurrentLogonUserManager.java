package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.OrganizationFactoryService;
import com.company.IntelligentPlatform.common.dto.CurrentLogonUser;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.LogonUserOrgReference;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class CurrentLogonUserManager {

    @Autowired
    protected LogonUserManager logonUserManager;

    @Autowired
    protected OrganizationFactoryService organizationFactoryService;

    public CurrentLogonUser convToUICom(LogonUser logonUser) throws ServiceEntityConfigureException {
        LogonUserOrgReference logonUserOrgReference = (LogonUserOrgReference) logonUserManager
                .getEntityNodeByKey(logonUser.getUuid(),
                        IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                        "LogonUserOrgReference", null);
        Role role = logonUserManager.getMainRole(logonUser.getUuid(), logonUser.getClient());
        Organization organization = organizationFactoryService.getRefOrganization(logonUserOrgReference);
        // Convert SE node list to UIModel
        CurrentLogonUser currentLogonUser = new CurrentLogonUser();
        convLogonUserToUI(logonUser, currentLogonUser);
        convOrganizationToUI(organization, currentLogonUser);
        convRoleToUI(role, currentLogonUser);
        return currentLogonUser;
    }

    protected void convRoleToUI(Role role,
                                CurrentLogonUser currentLogonUser) {
        if (role != null) {
            currentLogonUser.setRoleName(role.getName());
        }
    }

    protected void convLogonUserToUI(LogonUser logonUser,
                                     CurrentLogonUser currentLogonUser) {
        if (logonUser != null) {
            currentLogonUser.setId(logonUser.getId());
            currentLogonUser.setName(logonUser.getName());
        }
    }

    protected void convOrganizationToUI(Organization organization,
                                        CurrentLogonUser currentLogonUser) {
        if (organization != null) {
            currentLogonUser.setOrgName(organization.getName());
        }
    }


}
