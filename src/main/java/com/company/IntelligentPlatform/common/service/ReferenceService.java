package com.company.IntelligentPlatform.common.service;

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

	@PostConstruct
	public void initApplication() {

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
