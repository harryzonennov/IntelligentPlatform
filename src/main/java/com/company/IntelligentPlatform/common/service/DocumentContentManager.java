package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// TODO-DAO: import platform.foundation.DAO.DocumentContentDAO;

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

	// TODO-DAO: @Autowired

	// TODO-DAO: 	DocumentContentDAO documentContentDAO;

	@Autowired
	DocumentContentConfigureProxy documentContentConfigureProxy;

	public DocumentContentManager() {
		super.seConfigureProxy = new DocumentContentConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new DocumentContentDAO();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(documentContentDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(documentContentConfigureProxy);
	}
}
