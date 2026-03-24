package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceFlowException;

public interface IFlowTaskPartyHandler {

    public default LogonUser getTargetUser(SerialLogonInfo serialLogonInfo) throws ServiceFlowException {
        return null;
    }
}
