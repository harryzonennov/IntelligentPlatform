package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.LogonUserUIModel;
import com.company.IntelligentPlatform.common.dto.RoleMessageHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.LogonUserRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.RoleManager;
import com.company.IntelligentPlatform.common.service.ServiceEncodeException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.LogonUserConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

/**
 * Logic Manager CLASS FOR Service Entity [LogonUser]
 * 
 * @author
 * @date Mon Nov 26 14:41:18 CST 2012
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
public class LogonUserManager extends ServiceEntityManager {

	public static final String METHOD_convLogonUserToUI = "convLogonUserToUI";

	public static final String METHOD_convUIToLogonUser = "convUIToLogonUser";

	public static final String METHOD_convRoleToUI = "convRoleToUI";

	public static final String METHOD_convOrganizationToUI = "convOrganizationToUI";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected LogonUserRepository logonUserDAO;
	@Autowired
	protected LogonUserConfigureProxy logonUserConfigureProxy;

	@Autowired
	protected RoleManager roleManager;

	@Autowired
	protected EmployeeManager employeeManager;

	@Autowired
	protected LogonUserOrgManager logonUserOrgManager;

	@Autowired
	protected LogonUserIdHelper LogonUserIdHelper;

	@Autowired
	protected OrganizationFactoryService organizationFactoryService;
	
	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected LogonInfoManager logonInfoManager;
	
	@Autowired
	protected LogonUserSearchProxy logonUserSearchProxy;

	public static final String USERID_SUPER = "super";

	public static final String USERNAME_SUPER = "super";

	@Autowired
	protected RoleMessageHelper roleMessageHelper;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected Map<String, Map<Integer, String>> userTypeMapLan;

	protected Map<String, Map<Integer, String>> statusMapLan;

	public LogonUserManager() {

	}

	public Map<Integer, String> initUserTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.userTypeMapLan, LogonUserUIModel.class,
				"userType");
	}

	public Map<Integer, String> initStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, LogonUserUIModel.class,
				"status");
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		String id = LogonUserIdHelper.genDefaultId(client);
		LogonUser logonUser = (LogonUser) super.newRootEntityNode(client);
		logonUser.setId(id);
		return logonUser;
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, logonUserDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(logonUserConfigureProxy);
	}

	public LogonUser getSuperUser(String client)
			throws ServiceEntityConfigureException {
		LogonUser superUser = (LogonUser) getEntityNodeByKey(USERID_SUPER,
				IServiceEntityNodeFieldConstant.ID, LogonUser.NODENAME, client,
				null, true);
		return superUser;
	}

	/**
	 * get the role list belongs to this logon user uuid
	 * 
	 * @param logonUserUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<Role> getRoleList(String logonUserUUID, String client)
			throws ServiceEntityConfigureException {
		List<Role> roleList = new ArrayList<>();
		List<ServiceEntityNode> userRoleList = getEntityNodeListByKey(
				logonUserUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				UserRole.NODENAME, client, null);
		if (userRoleList == null || userRoleList.size() == 0) {
			return roleList;
		}
		for (ServiceEntityNode seNode : userRoleList) {
			UserRole userRole = (UserRole) seNode;
			Role role = (Role) roleManager.getEntityNodeByKey(
					userRole.getRefUUID(),
					IServiceEntityNodeFieldConstant.UUID, Role.NODENAME,
					client, null);
			if (role != null) {
				roleList.add(role);
			}
		}
		return roleList;
	}

	/**
	 * Logic to return the main role for this logon User
	 * 
	 * @param logonUserUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Role getMainRole(String logonUserUUID, String client)
			throws ServiceEntityConfigureException {
		List<Role> roleList = getRoleList(logonUserUUID, client);
		if (roleList != null && roleList.size() > 0) {
			return roleList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Core Logic to reset password
	 * 
	 * @param logonUser
	 * @param newPassword
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @param encodedFlag
	 * @throws ServiceEncodeException
	 */
	public void resetPassword(LogonUser logonUser, String newPassword,
			String logonUserUUID, String organizationUUID, boolean encodedFlag)
			throws ServiceEncodeException {
		String encodePassword = newPassword;
		if (!encodedFlag) {
			encodePassword = logonInfoManager.getEncodedPassword(newPassword);
		}
		LogonUser logonUserBack = (LogonUser) logonUser.clone();
		logonUser.setPassword(encodePassword);
		logonUser.setPasswordInitFlag(StandardSwitchProxy.SWITCH_ON);
		logonUser.setInitPassword(encodePassword);
		logonUser.setPasswordNeedFlag(StandardSwitchProxy.SWITCH_ON);
		updateSENode(logonUser, logonUserBack, logonUserUUID, organizationUUID);
	}

	public Map<Integer, String> initPasswordInitMap(String languageCode)
			throws ServiceEntityInstallationException {
		return standardSwitchProxy.getSimpleSwitchMap(languageCode);
	}

	public Map<Integer, String> initLockUserFlagMap(String languageCode)
			throws ServiceEntityInstallationException {
		return standardSwitchProxy.getSimpleSwitchMap(languageCode);
	}

	public void convLogonUserToUI(LogonUser logonUser,
								  LogonUserUIModel logonUserUIModel) throws ServiceEntityInstallationException {
		convLogonUserToUI(logonUser, logonUserUIModel, null);
	}

	public void convLogonUserToUI(LogonUser logonUser,
			LogonUserUIModel logonUserUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		DocFlowProxy.convServiceEntityNodeToUIModel(logonUser, logonUserUIModel);
		logonUserUIModel.setUserType(logonUser.getUserType());
		logonUserUIModel.setLockUserFlag(logonUser.getLockUserFlag());
		logonUserUIModel.setPasswordInitFlag(logonUser.getPasswordInitFlag());
		logonUserUIModel.setStatus(logonUser.getStatus());
		if(logonInfo != null){
			Map<Integer, String> statusMap = this.initStatusMap(logonInfo.getLanguageCode());
			logonUserUIModel.setStatusValue(statusMap.get(logonUser
					.getStatus()));
			Map<Integer, String> lockUserMap = this.initLockUserFlagMap(logonInfo.getLanguageCode());
			logonUserUIModel.setLockUserFlagValue(lockUserMap.get(logonUser.getLockUserFlag()));
			Map<Integer, String> passwordInitMap = this.initPasswordInitMap(logonInfo.getLanguageCode());
			logonUserUIModel.setPasswordInitFlagValue(passwordInitMap.get(logonUser.getPasswordInitFlag()));
		}
	}

	public void convRoleToUI(Role role, LogonUserUIModel logonUserUIModel)
			throws ServiceEntityInstallationException, IOException {
		if (role != null) {
			logonUserUIModel.setRoleUUID(role.getUuid());
			logonUserUIModel.setRoleId(role.getId());
			logonUserUIModel.setRoleName(role.getName());
			logonUserUIModel
					.setRoleNote(roleMessageHelper.getNote(role.getId()));
		}
	}

	public void convOrganizationToUI(Organization organization,
			LogonUserUIModel logonUserUIModel) {
		if (organization != null) {
			logonUserUIModel.setOrganizationUUID(organization.getUuid());
			logonUserUIModel.setOrganizationId(organization.getId());
			logonUserUIModel.setOrganizationName(organization.getName());
			logonUserUIModel.setOrganizationType(organization.getOrganType());
			logonUserUIModel.setOrganizationAddress(organization.getAddress());
		}
	}

	public void convUIToLogonUser(LogonUserUIModel logonUserUIModel,
			LogonUser rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(logonUserUIModel, rawEntity);
		rawEntity.setUserType(logonUserUIModel.getUserType());
		rawEntity.setLockUserFlag(logonUserUIModel.getLockUserFlag());
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.LogonUser;
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return logonUserSearchProxy;
	}

}
