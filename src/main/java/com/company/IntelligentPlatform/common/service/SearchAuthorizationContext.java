package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

public class SearchAuthorizationContext {

    private LogonUser logonUser;

    private String aoId;

    private String acId;

    private Organization homeOrganization;

    private List<ServiceEntityNode> organizationList;

    private List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList;

    public SearchAuthorizationContext() {
    }

    public SearchAuthorizationContext(LogonUser logonUser, String aoId, String acId, Organization homeOrganization,
                                      List<ServiceEntityNode> organizationList, List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList) {
        this.logonUser = logonUser;
        this.aoId = aoId;
        this.acId = acId;
        this.homeOrganization = homeOrganization;
        this.organizationList = organizationList;
        this.authorizationACUnionList = authorizationACUnionList;
    }

    public LogonUser getLogonUser() {
        return logonUser;
    }

    public void setLogonUser(LogonUser logonUser) {
        this.logonUser = logonUser;
    }

    public String getAoId() {
        return aoId;
    }

    public void setAoId(String aoId) {
        this.aoId = aoId;
    }

    public String getAcId() {
        return acId;
    }

    public void setAcId(String acId) {
        this.acId = acId;
    }

    public Organization getHomeOrganization() {
        return homeOrganization;
    }

    public void setHomeOrganization(Organization homeOrganization) {
        this.homeOrganization = homeOrganization;
    }

    public List<ServiceEntityNode> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List<ServiceEntityNode> organizationList) {
        this.organizationList = organizationList;
    }

    public List<AuthorizationManager.AuthorizationACUnion> getAuthorizationACUnionList() {
        return authorizationACUnionList;
    }

    public void setAuthorizationACUnionList(List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList) {
        this.authorizationACUnionList = authorizationACUnionList;
    }
}
