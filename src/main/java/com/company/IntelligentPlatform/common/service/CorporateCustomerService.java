package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.repository.CorporateCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Replaces: ThorsteinPlatform CorporateCustomerManager (ServiceEntityManager subclass)
 */
@Service
@Transactional
public class CorporateCustomerService extends ServiceEntityService {

	@Autowired
	protected CorporateCustomerRepository corporateCustomerRepository;

	public CorporateCustomer create(CorporateCustomer customer, String userUUID, String orgUUID) {
		customer.setStatus(CorporateCustomer.STATUS_INITIAL);
		customer.setAccountType(CorporateCustomer.ACCOUNTTYPE_COR_CUSTOMER);
		return insertSENode(corporateCustomerRepository, customer, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public CorporateCustomer getByUuid(String uuid) {
		return getEntityNodeByUUID(corporateCustomerRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<CorporateCustomer> getByClient(String client) {
		return corporateCustomerRepository.findByClient(client);
	}

	public CorporateCustomer update(CorporateCustomer customer, String userUUID, String orgUUID) {
		return updateSENode(corporateCustomerRepository, customer, userUUID, orgUUID);
	}

	public void setStatus(String uuid, int status, String userUUID, String orgUUID) {
		CorporateCustomer customer = corporateCustomerRepository.findById(uuid).orElseThrow();
		customer.setStatus(status);
		updateSENode(corporateCustomerRepository, customer, userUUID, orgUUID);
	}

	public void delete(String uuid) {
		deleteSENode(corporateCustomerRepository, uuid);
	}

}
