package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// TODO-DAO: import platform.foundation.DAO.ProvinceDAO;

import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.ProvinceConfigureProxy;

/**
 * Logic Manager CLASS FOR Service Entity [Province]
 *
 * @author
 * @date Sun Feb 10 13:54:24 CST 2013
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class ProvinceManager extends ServiceEntityManager {

    // TODO-DAO: @Autowired

    // TODO-DAO:     ProvinceDAO provinceDAO;

    @Autowired
    ProvinceConfigureProxy provinceConfigureProxy;

    public ProvinceManager() {
        super.seConfigureProxy = new ProvinceConfigureProxy();
        // TODO-DAO: super.serviceEntityDAO = new ProvinceDAO();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        // TODO-DAO: super.setServiceEntityDAO(provinceDAO);
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(provinceConfigureProxy);
    }
}
