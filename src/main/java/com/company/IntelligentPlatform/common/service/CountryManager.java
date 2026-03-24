package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// TODO-DAO: import platform.foundation.DAO.CountryDAO;

import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.CountryConfigureProxy;

/**
 * Logic Manager CLASS FOR Service Entity [Country]
 *
 * @author
 * @date Sun Feb 10 12:34:18 CST 2013
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class CountryManager extends ServiceEntityManager {

    // TODO-DAO: @Autowired

    // TODO-DAO:     CountryDAO countryDAO;

    @Autowired
    CountryConfigureProxy countryConfigureProxy;

    public CountryManager() {
        super.seConfigureProxy = new CountryConfigureProxy();
        // TODO-DAO: super.serviceEntityDAO = new CountryDAO();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        // TODO-DAO: super.setServiceEntityDAO(countryDAO);
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(countryConfigureProxy);
    }
}
