package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.common.repository.GenericServiceEntityNodeRepository;

import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.DocumentContentConfigureProxy;

/**
 * Logic Manager CLASS FOR Service Entity [DocumentContent]
 * 
 * @author
 * @date Sat Dec 12 20:58:29 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class DocumentContentManager extends ServiceEntityManager {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	GenericServiceEntityNodeRepository documentContentDAO;

	@Autowired
	DocumentContentConfigureProxy documentContentConfigureProxy;

	public DocumentContentManager() {
		super.seConfigureProxy = new DocumentContentConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, documentContentDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(documentContentConfigureProxy);
	}
}
