package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.LogonUserOrgException;
import com.company.IntelligentPlatform.platform.model.LogonUser;
import com.company.IntelligentPlatform.platform.model.SerialLogonInfo;
import com.company.IntelligentPlatform.platform.model.ServiceFlowException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

public interface IFlowTaskGetLogonUserExecutor {

    public LogonUser getTargetLogonUser(String orgUUID, SerialLogonInfo serialLogonInfo) throws LogonUserOrgException,
            ServiceEntityConfigureException, ServiceFlowException;
}
