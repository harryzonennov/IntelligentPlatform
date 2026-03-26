package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.AuthorizationGroupUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.AuthorizationGroupRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.SystemMandatoryModeProxy;
import com.company.IntelligentPlatform.common.service.SystemSerialParallelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.AuthorizationGroup;
import com.company.IntelligentPlatform.common.model.AuthorizationGroupConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [AuthorizationGroup]
 * 
 * @author
 * @date Fri Jun 14 10:24:56 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class AuthorizationGroupManager extends ServiceEntityManager {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected AuthorizationGroupRepository authorizationGroupDAO;
	@Autowired
	AuthorizationGroupConfigureProxy authorizationGroupConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected SystemSerialParallelProxy systemSerialParallelProxy;

	@Autowired
	protected SystemMandatoryModeProxy systemMandatoryModeProxy;

	@Autowired
	protected AuthorizationGroupSearchProxy authorizationGroupSearchProxy;

	public static final String METHOD_ConvAuthorizationGroupToUI = "convAuthorizationGroupToUI";

	public static final String METHOD_ConvUIToAuthorizationGroup = "convUIToAuthorizationGroup";

	public AuthorizationGroupManager() {
		super.seConfigureProxy = new AuthorizationGroupConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, authorizationGroupDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(authorizationGroupConfigureProxy);
	}

	/**
	 * Get all system Authorization Group list
	 * 
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getAllAuthorizationGroupList()
			throws ServiceEntityConfigureException {
		return getEntityNodeListByKey(null, null, AuthorizationGroup.NODENAME,
				null);
	}

	/**
	 * Update Authorization group instance to persistence, check the duplicate
	 * by ID, if same AG instance with ID exist, then update it, if not, insert
	 * the Authorization group instance to persistence
	 * 
	 * @param authorizationGroup
	 *            :Authorization group instance
	 * @param agID
	 *            :Authorization Group ID
	 * @throws ServiceEntityConfigureException
	 */
	public void genearateAuthorizationGroup(
			AuthorizationGroup authorizationGroup, String agID, String agName,
			String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException {
		// check the duplicate in persistence
		AuthorizationGroup agBack = (AuthorizationGroup) this
				.getEntityNodeByKey(agID, IServiceEntityNodeFieldConstant.ID,
						AuthorizationGroup.NODENAME, null, true);
		if (agBack == null) {
			this.insertSENode(authorizationGroup, logonUserUUID,
					organizationUUID);
		} else {			
			this.updateSENode(authorizationGroup, agBack, logonUserUUID, organizationUUID);
		}
	}

	public void convAuthorizationGroupToUI(
			AuthorizationGroup authorizationGroup,
			AuthorizationGroupUIModel authorizationGroupUIModel) throws ServiceEntityInstallationException {
		convAuthorizationGroupToUI(authorizationGroup, authorizationGroupUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException 
	 */
	public void convAuthorizationGroupToUI(
			AuthorizationGroup authorizationGroup,
			AuthorizationGroupUIModel authorizationGroupUIModel, LogonInfo logonInfo) throws ServiceEntityInstallationException {
		if (authorizationGroup != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(authorizationGroup,
					authorizationGroupUIModel);
			authorizationGroupUIModel.setNote(authorizationGroup.getNote());
			authorizationGroupUIModel
					.setCrossGroupProcessType(authorizationGroup
							.getCrossGroupProcessType());
			authorizationGroupUIModel.setInnerProcessType(authorizationGroup
					.getInnerProcessType());
			if(logonInfo != null){
				Map<Integer, String> innerProcessTypeMap = this.initInnerProcessTypeMap(logonInfo.getLanguageCode());
				authorizationGroupUIModel
						.setInnerProcessTypeValue(innerProcessTypeMap.get(authorizationGroup
								.getInnerProcessType()));
				Map<Integer, String> crossGroupProcessTypeMap =
						initCrossGroupProcessTypeMap(logonInfo.getLanguageCode());
				authorizationGroupUIModel
						.setCrossGroupProcessTypeValue(crossGroupProcessTypeMap.get(authorizationGroup
								.getCrossGroupProcessType()));
			}
			authorizationGroupUIModel.setId(authorizationGroup.getId());
			authorizationGroupUIModel.setName(authorizationGroup.getName());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:authorizationGroup
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToAuthorizationGroup(
			AuthorizationGroupUIModel authorizationGroupUIModel,
			AuthorizationGroup rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(authorizationGroupUIModel, rawEntity);
		rawEntity.setCrossGroupProcessType(authorizationGroupUIModel
				.getCrossGroupProcessType());
		rawEntity.setInnerProcessType(authorizationGroupUIModel
				.getInnerProcessType());
		rawEntity.setId(authorizationGroupUIModel.getId());
		rawEntity.setUuid(authorizationGroupUIModel.getUuid());
		rawEntity.setName(authorizationGroupUIModel.getName());
	}

	public Map<Integer, String> initCrossGroupProcessTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return systemMandatoryModeProxy.getMandatoryModelMap(languageCode);
	}

	public Map<Integer, String> initInnerProcessTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return systemSerialParallelProxy.getSerialParallelMap(languageCode);
	}

    @Override
	public ServiceSearchProxy getSearchProxy() {
		return authorizationGroupSearchProxy;
	}

}
