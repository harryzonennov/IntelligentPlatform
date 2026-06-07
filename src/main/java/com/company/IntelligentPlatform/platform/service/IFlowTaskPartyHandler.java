package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.LogonInfo;
import com.company.IntelligentPlatform.platform.model.LogonUser;
import com.company.IntelligentPlatform.platform.model.SerialLogonInfo;
import com.company.IntelligentPlatform.platform.model.ServiceFlowException;

public interface IFlowTaskPartyHandler {

    public default LogonUser getTargetUser(SerialLogonInfo serialLogonInfo) throws ServiceFlowException {
        return null;
    }
}
