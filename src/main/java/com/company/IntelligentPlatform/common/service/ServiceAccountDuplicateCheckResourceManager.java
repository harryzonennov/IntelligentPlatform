package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.ServiceAccountDuplicateCheckResourceRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.model.ServiceAccountDuplicateCheckResource;
import com.company.IntelligentPlatform.common.model.ServiceAccountDuplicateCheckResourceConfigureProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

/**
 * Logic Manager CLASS FOR Service Entity [ServiceAccountDuplicateCheckResource]
 * 
 * @author
 * @date Thu May 14 14:44:51 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class ServiceAccountDuplicateCheckResourceManager extends
		ServiceEntityManager {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected ServiceAccountDuplicateCheckResourceRepository serviceAccountDuplicateCheckResourceDAO;
	@Autowired
	ServiceAccountDuplicateCheckResourceConfigureProxy serviceAccountDuplicateCheckResourceConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected ServiceAccountDuplicateCheckResourceIdHelper serviceAccountDuplicateCheckResourceIdHelper;

	public ServiceAccountDuplicateCheckResourceManager() {
		super.seConfigureProxy = new ServiceAccountDuplicateCheckResourceConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, serviceAccountDuplicateCheckResourceDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(serviceAccountDuplicateCheckResourceConfigureProxy);
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		ServiceAccountDuplicateCheckResource ServiceAccountDuplicateCheckResource = (ServiceAccountDuplicateCheckResource) super
				.newRootEntityNode(client);
		String ServiceAccountDuplicateCheckResourceID = serviceAccountDuplicateCheckResourceIdHelper
				.genDefaultId(client);
		ServiceAccountDuplicateCheckResource
				.setId(ServiceAccountDuplicateCheckResourceID);
		return ServiceAccountDuplicateCheckResource;
	}
	
	public void checkAccDupProxyClassValid(String className)
			throws AccountDuplicateCheckException {
		try {
			Class<?> accDupCheckClass = Class.forName(className);
			boolean checkInterface = checkClassInterfaceImplemented(
					accDupCheckClass,
					IAccountDuplicateCheckLogicCore.class.getSimpleName());
			if (!checkInterface) {
				throw new AccountDuplicateCheckException(
						AccountDuplicateCheckException.PARA2_WRG_CONTROL_INTERFACE,
						className,
						IAccountDuplicateCheckLogicCore.class.getSimpleName());
			}
		} catch (ClassNotFoundException e) {
			throw new AccountDuplicateCheckException(
					AccountDuplicateCheckException.PARA_WRG_DUPCHECK_CLASS,
					className);
		}
	}

	public boolean checkClassInterfaceImplemented(Class<?> classType,
			String interfaceName) {
		Class<?>[] interfaceArray = classType.getInterfaces();
		if (interfaceArray == null || interfaceArray.length == 0) {
			return false;
		}
		for (Class<?> interfaceClass : interfaceArray) {
			if (interfaceName.equals(interfaceClass.getSimpleName())) {
				return true;
			}
		}
		return false;
	}

}
