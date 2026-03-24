package com.company.IntelligentPlatform.common.service;

import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO-DAO: import platform.foundation.DAO.HibernateDefaultImpDAO; // replaced by local stub
// TODO-DAO: import platform.foundation.DAO.ServiceEntityRegisterEntityDAO;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ReferenceNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityRegisterEntity;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ReferenceService {

	// TODO-DAO: @Autowired
	@Autowired
	ServiceEntityRegisterEntityDAO serviceEntityRegisterEntityDAO; // TODO-DAO: stub

	@Autowired
	SpringContextBeanService springContextBeanService;

	@PostConstruct
	public void initApplication() {

	}

	/**
	 * Get reference target service entity node instance by source reference
	 * node
	 * 
	 * @param source
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ServiceEntityNode getRefTarget(ReferenceNode source)
			throws ServiceEntityConfigureException {
		// Check input parameters firstly
		if (source == null)
			return null;
		if (source.getRefUUID() == null)
			return null;
		if (source.getRefNodeName() == null)
			return null;
		if (source.getRefSEName() == null)
			return null;
		ServiceEntityRegisterEntity seRegisterEntity = this.serviceEntityRegisterEntityDAO
				.getSERegEntity(source.getRefSEName());
		String daoName = seRegisterEntity.getSeDAOName();
		// Fetch the relative DAO type from Spring context
		HibernateDefaultImpDAO seDAO = (HibernateDefaultImpDAO) springContextBeanService
				.getBean(ServiceEntityStringHelper.headerToLowerCase(daoName));
		// register SE DAO to buffer
		List<ServiceEntityNode> tempList = seDAO.getEntityNodeListByKey(
				source.getRefUUID(), IServiceEntityNodeFieldConstant.UUID,
				source.getRefNodeName());
		if (tempList != null && tempList.size() > 0) {
			return tempList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Build the reference information from target node to reference node
	 * 
	 * @param target
	 * @param refNode
	 * @param refPackageName
	 *            optional = "true", if value is empty, package information will
	 *            be retrieved from persistency
	 * @throws ServiceEntityConfigureException
	 */
	public void buildReferenceNode(ServiceEntityNode target,
			ReferenceNode refNode, String refPackageName)
			throws ServiceEntityConfigureException {
		refNode.setRefNodeName(target.getNodeName());
		refNode.setRefSEName(target.getServiceEntityName());
		refNode.setRefUUID(target.getUuid());
		if (refPackageName != null
				|| refPackageName != ServiceEntityStringHelper.EMPTYSTRING) {
			// In case get the package information online
			refNode.setRefPackageName(refPackageName);
		} else {
			// retrieve package information from persistency
			ServiceEntityRegisterEntity seReg = serviceEntityRegisterEntityDAO
					.getSERegEntity(target.getServiceEntityName());
			if(seReg != null){
				refNode.setRefPackageName(seReg.getPackageName());
			}
		}
	}

}
