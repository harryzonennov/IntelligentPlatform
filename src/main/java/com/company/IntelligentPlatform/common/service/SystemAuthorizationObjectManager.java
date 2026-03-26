package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.SystemAuthorizationObjectRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;

import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.SystemAuthorizationObjectConfigureProxy;

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
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, systemAuthorizationObjectDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(systemAuthorizationObjectConfigureProxy);
	}
}
