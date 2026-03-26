package com.company.IntelligentPlatform.finance.service;

import jakarta.annotation.PostConstruct;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;

import com.company.IntelligentPlatform.finance.repository.FinanceDocumentRepository;
import com.company.IntelligentPlatform.finance.model.FinanceDocumentConfigureProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.ServiceEntityManager;

/**
 * Logic Manager CLASS FOR Service Entity [FinanceDocument]
 * 
 * @author
 * @date Tue May 07 16:09:14 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class FinanceDocumentManager extends ServiceEntityManager {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	FinanceDocumentRepository financeDocumentDAO;

	@Autowired
	FinanceDocumentConfigureProxy financeDocumentConfigureProxy;

	@Autowired
	FinAccountTitleManager accountTitleManager;

	public FinanceDocumentManager() {
		super.seConfigureProxy = new FinanceDocumentConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, financeDocumentDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(financeDocumentConfigureProxy);
	}

	/**
	 * Get All AccountTitle By Debit
	 * 
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	// public List<ServiceEntityNode> getAllAccountTitleByDebit()
	// throws ServiceEntityConfigureException {
	// return accountTitleManager.getAllSubsidiaryNode();
	// }
}
