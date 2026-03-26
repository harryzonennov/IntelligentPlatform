package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.ActionCodeUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.ActionCodeRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ActionCode;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.RoleAuthorization;
import com.company.IntelligentPlatform.common.model.RoleAuthorizationActionCode;
import com.company.IntelligentPlatform.common.model.ActionCodeConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Logic Manager CLASS FOR Service Entity [ActionCode]
 * 
 * @author
 * @date Sun Jun 09 14:37:44 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class ActionCodeManager extends ServiceEntityManager {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected ActionCodeRepository actionCodeDAO;
	@Autowired
	ActionCodeConfigureProxy actionCodeConfigureProxy;

	@Autowired
	AuthorizationObjectManager authorizationObjectManager;

	public ActionCodeManager() {
		super.seConfigureProxy = new ActionCodeConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, actionCodeDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(actionCodeConfigureProxy);
	}

	/**
	 * Get all system action code list
	 * 
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getAllActionCodeList()
			throws ServiceEntityConfigureException {
		return getEntityNodeListByKey(null, null, ActionCode.NODENAME, null);
	}

	/**
	 * Filter out the action code list under the role Authorization UUID
	 * 
	 * @param parentUUID
	 * @param rawRoleActionCodeList
	 * @param rawActionCodeList
	 * @return
	 */
	public List<ActionCode> filterActionCodeByParentUUID(String parentUUID,
			List<ServiceEntityNode> rawRoleActionCodeList,
			List<ServiceEntityNode> rawActionCodeList) {
		List<ServiceEntityNode> roleActionCodeList = ServiceCollectionsHelper
				.filterSENodeByParentUUID(parentUUID, rawRoleActionCodeList);
		if (roleActionCodeList == null || roleActionCodeList.size() == 0) {
			return null;
		}
		List<ActionCode> resultList = new ArrayList<ActionCode>();
		for (ServiceEntityNode seNode : roleActionCodeList) {
			RoleAuthorizationActionCode roleAuthorizationActionCode = (RoleAuthorizationActionCode) seNode;
			ActionCode actionCode = (ActionCode) ServiceCollectionsHelper
					.filterSENodeOnline(
							roleAuthorizationActionCode.getRefUUID(),
							rawActionCodeList);
			if (actionCode != null) {
				resultList.add(actionCode);
			}
		}
		return resultList;
	}
	
	/**
	 * Filter out the action code list under the role Authorization by "ActionCode" array information from role authorization
	 * 
	 * @param roleAuthorization
	 * @param rawActionCodeList
	 * @return
	 */
	public List<ActionCode> filterActionCodeByActionCodeArray(RoleAuthorization roleAuthorization,
			List<ServiceEntityNode> rawActionCodeList) {
		if(ServiceEntityStringHelper.checkNullString(roleAuthorization.getActionCodeArray())){
			return null;
		}
		List<ActionCode> resultList = new ArrayList<ActionCode>();
		String[] roleACUUIDList = ServiceEntityStringHelper.convStringToArray(roleAuthorization.getActionCodeArray());
		for (String acUUID : roleACUUIDList) {
			ActionCode actionCode = (ActionCode) ServiceCollectionsHelper
					.filterSENodeOnline(
							acUUID,
							rawActionCodeList);
			if (actionCode != null) {
				resultList.add(actionCode);
			}
		}
		return resultList;
	}

	public AuthorizationObject getAuthorizationObject(String aoID, String aoType)
			throws ServiceEntityConfigureException, AuthorizationException {
		// In case a system authorization object
		if (aoType.equals(AuthorizationObject.AUTH_TYPE_SYS)) {
			return (AuthorizationObject) authorizationObjectManager
					.getEntityNodeByKey(aoID,
							IServiceEntityNodeFieldConstant.ID,
							AuthorizationObject.NODENAME, null, true);
		}
		// In case a resource authorization object
		if (aoType.equals(AuthorizationObject.AUTH_TYPE_RESOURCE)) {
			return (AuthorizationObject) authorizationObjectManager
					.getEntityNodeByKey(aoID,
							IServiceEntityNodeFieldConstant.ID,
							AuthorizationObject.NODENAME, null, true);
		}
		// In case authorization object is finance account title
		if (aoType.equals(AuthorizationObject.AUTH_TYPE_ACCTITLE)) {
			// need to redefine in the future
			return (AuthorizationObject) authorizationObjectManager
					.getEntityNodeByKey(aoID,
							IServiceEntityNodeFieldConstant.ID,
							AuthorizationObject.NODENAME, null, true);
		}
		throw new AuthorizationException(
				AuthorizationException.TYPE_WRONG_AO_TYPE);
	}

	public void convActionCodeToUI(ActionCode actionCode,
			ActionCodeUIModel actionCodeUIModel) {
		if (actionCode != null) {
			actionCodeUIModel.setUuid(actionCode.getUuid());
			actionCodeUIModel.setId(actionCode.getId());
			actionCodeUIModel.setName(actionCode.getName());
			actionCodeUIModel.setNote(actionCode.getNote());
		}
	}

}
