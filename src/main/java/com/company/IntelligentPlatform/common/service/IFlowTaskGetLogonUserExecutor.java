package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.LogonUserOrgException;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceFlowException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

public interface IFlowTaskGetLogonUserExecutor {

    public LogonUser getTargetLogonUser(String orgUUID, SerialLogonInfo serialLogonInfo) throws LogonUserOrgException,
            ServiceEntityConfigureException, ServiceFlowException;
}
