package com.company.IntelligentPlatform.platform.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.platform.repository.CountryRepository;
import com.company.IntelligentPlatform.platform.service.JpaServiceEntityDAO;

import com.company.IntelligentPlatform.platform.service.ServiceEntityManager;
import com.company.IntelligentPlatform.platform.model.CountryConfigureProxy;

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
        super.setSeConfigureProxy(countryConfigureProxy);
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, countryDAO, this.getSeConfigureProxy()));
    }
}
