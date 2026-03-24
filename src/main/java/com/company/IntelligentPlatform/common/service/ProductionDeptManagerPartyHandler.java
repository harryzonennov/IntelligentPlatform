package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.service.LogonUserOrgManager;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceFlowException;

@Service
public class ProductionDeptManagerPartyHandler implements IFlowTaskPartyHandler {

    @Autowired
    protected OrganizationManager organizationManager;

    @Autowired
    protected LogonUserOrgManager logonUserOrgManager;

    @Autowired
    protected FlowTaskPartyHandlerRepository flowTaskPartyHandlerRepository;

    protected Logger logger = LoggerFactory.getLogger(ProductionDeptManagerPartyHandler.class);

    public LogonUser getTargetUser(SerialLogonInfo serialLogonInfo) throws ServiceFlowException {
        return flowTaskPartyHandlerRepository.getTargetUserWrapper((orgUUID, logonInfo) -> logonUserOrgManager.getProductionDeptManager(orgUUID, logonInfo), serialLogonInfo);
    }
}
