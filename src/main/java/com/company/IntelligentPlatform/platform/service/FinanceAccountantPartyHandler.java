package com.company.IntelligentPlatform.platform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.service.OrganizationManager;
import com.company.IntelligentPlatform.platform.service.LogonUserOrgManager;
import com.company.IntelligentPlatform.platform.model.LogonInfo;
import com.company.IntelligentPlatform.platform.model.LogonUser;
import com.company.IntelligentPlatform.platform.model.SerialLogonInfo;
import com.company.IntelligentPlatform.platform.model.ServiceFlowException;

@Service
public class FinanceAccountantPartyHandler implements IFlowTaskPartyHandler {

    @Autowired
    protected OrganizationManager organizationManager;

    @Autowired
    protected LogonUserOrgManager logonUserOrgManager;

    @Autowired
    protected FlowTaskPartyHandlerRepository flowTaskPartyHandlerRepository;

    protected Logger logger = LoggerFactory.getLogger(FinanceAccountantPartyHandler.class);

    public LogonUser getTargetUser(SerialLogonInfo serialLogonInfo) throws ServiceFlowException {
        return flowTaskPartyHandlerRepository.getTargetUserWrapper((orgUUID, logonInfo) -> logonUserOrgManager.getFinanceAccountant(orgUUID, logonInfo),
                serialLogonInfo);
    }
}
