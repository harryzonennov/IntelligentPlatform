package com.company.IntelligentPlatform.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.OrganizationFactoryService;
// TODO-DAO: import platform.foundation.DAO.LogonUserDAO;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * This is System AO implementation class for the AO Need to access the data for
 * the own user.
 *
 * @author Zhang, Hang
 */
@Service
public class SelfOrgData extends SysAODeterminer {

    // TODO-DAO: @Autowired

    // TODO-DAO:     LogonUserDAO logonUserDAO;

    @Autowired
    LogonUserManager logonUserManager;

    @Autowired
    OrganizationFactoryService organizationFactoryService;

    @Override
    public boolean hitTarget(String logonUserUUID, ServiceEntityNode target, Organization homeOrganization,
                             List<ServiceEntityNode> allOrganizationList)
            throws ServiceEntityConfigureException {
        if (logonUserUUID == null || logonUserUUID.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
            return false;
        }
        if (homeOrganization.getUuid().equals(target.getResOrgUUID())) {
            return true;
        }
        return false;
    }

    @Override
    public ServiceBasicKeyStructure genKeyValueStructure(String logonUserUUID, Organization homeOrganization,
                                                         List<ServiceEntityNode> allOrganizationList) {
        return new ServiceBasicKeyStructure(ServiceCollectionsHelper.asList(homeOrganization.getUuid()),
                IServiceEntityNodeFieldConstant.RESORG_UUID, ServiceBasicKeyStructure.OPERATOR_OR);
    }


}
