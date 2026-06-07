package com.company.IntelligentPlatform.platform.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.platform.repository.SystemAuthorizationObjectRepository;
import com.company.IntelligentPlatform.platform.service.JpaServiceEntityDAO;

import com.company.IntelligentPlatform.platform.service.ServiceEntityManager;
import com.company.IntelligentPlatform.platform.model.SystemAuthorizationObjectConfigureProxy;

/**
 * Logic Manager CLASS FOR Service Entity [SystemAuthorizationObject]
 * 
 * @author
 * @date Tue Jun 11 18:39:21 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class SystemAuthorizationObjectManager extends ServiceEntityManager {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected SystemAuthorizationObjectRepository systemAuthorizationObjectDAO;
	@Autowired
	SystemAuthorizationObjectConfigureProxy systemAuthorizationObjectConfigureProxy;

	public SystemAuthorizationObjectManager() {
		super.seConfigureProxy = new SystemAuthorizationObjectConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setSeConfigureProxy(systemAuthorizationObjectConfigureProxy);
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, systemAuthorizationObjectDAO, this.getSeConfigureProxy()));
	}
}
