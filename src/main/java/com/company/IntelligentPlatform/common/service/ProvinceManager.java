package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.ProvinceRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;

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
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected ProvinceRepository provinceDAO;
    @Autowired
    ProvinceConfigureProxy provinceConfigureProxy;

    public ProvinceManager() {
        super.seConfigureProxy = new ProvinceConfigureProxy();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, provinceDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(provinceConfigureProxy);
    }
}
