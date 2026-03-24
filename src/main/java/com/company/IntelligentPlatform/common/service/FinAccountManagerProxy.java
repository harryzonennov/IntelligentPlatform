package com.company.IntelligentPlatform.common.service;

import java.util.List;

import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

/**
 * Proxy Class for FinAccount Manager need to access from platform package.
 *
 * @author Zhang, Hang
 */
public class FinAccountManagerProxy extends ServiceEntityManager {

    public void admDeleteFinAccountUnion(String uuid)
            throws ServiceEntityConfigureException {
        // Have to implement in sub-class
    }

    public List<ServiceEntityNode> getFinAccountListFromAccount(String accountUUID,
                                                                String client) throws ServiceEntityConfigureException {
        // Have to implement in Sub-class
        return null;
    }


}
