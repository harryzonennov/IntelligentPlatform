package com.company.IntelligentPlatform.platform.service;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.platform.model.ServiceEntityRegisterEntity;

/**
 * TODO-DAO: Stub replacing legacy platform.foundation.DAO.ServiceEntityRegisterEntityDAO.
 */
@Component
public class ServiceEntityRegisterEntityDAO {

    public ServiceEntityRegisterEntity getSERegEntity(String seName) {
        // TODO-DAO: implement with JPA query
        return new ServiceEntityRegisterEntity();
    }

    public String getSERegEntityDAOName(String seName) {
        // TODO-DAO: implement
        return null;
    }
}
