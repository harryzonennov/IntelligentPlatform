package com.company.IntelligentPlatform.platform.service;

import java.util.List;

import com.company.IntelligentPlatform.platform.service.ServiceEntityManager;
import com.company.IntelligentPlatform.platform.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

/**
 * Proxy Class for SalesArea Manager need to access from platform package.
 *
 * @author Zhang, Hang
 */
public class SalesAreaManagerProxy extends ServiceEntityManager {

    public List<ServiceEntityNode> getAllSubAreaByKeyList(
            List<ServiceBasicKeyStructure> keyList, String client)
            throws ServiceEntityConfigureException {
        // Have to implemented in sub class
        return null;
    }

    public ServiceEntityNode filterSalesAreaByName(List<ServiceEntityNode> rawAreaList,
                                                   String areaName) {
        return null;
    }

}
