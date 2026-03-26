package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.CountryRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;

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
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected CountryRepository countryDAO;
    @Autowired
    CountryConfigureProxy countryConfigureProxy;

    public CountryManager() {
        super.seConfigureProxy = new CountryConfigureProxy();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, countryDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(countryConfigureProxy);
    }
}
