package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.RoleManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.RoleMessageCategory;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class LogonUserMessageManager {

	@Autowired
	LogonUserManager logonUserManager;

	@Autowired
	RoleManager roleManager;

	@Autowired
	MessageCenterProcessFactory messageCenterProcessFactory;

	@Autowired
	ServiceDropdownListHelper serviceDropdownListHelper;

	public List<LogonUserMessageCategory> generatePopupMessageCategoryList(
			LogonInfo logonInfo) throws AuthorizationException,
			LogonInfoException, LogonUserMessageException,
			ServiceEntityConfigureException {
		/**
		 * Get all role message instance list from logon user
		 */
		List<Role> roleList = logonUserManager.getRoleList(logonInfo.getRefUserUUID(),
				logonInfo.getClient());
		if (roleList == null || roleList.size() == 0) {
			throw new AuthorizationException(
					AuthorizationException.TYPE_NO_AUTHORIZATION);
		}
		List<ServiceEntityNode> roleMessageCategoryList = new ArrayList<>();
		for (Role role : roleList) {
			List<ServiceEntityNode> tmpRoleMessageList = roleManager
					.getEntityNodeListByKey(role.getUuid(),
							IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							RoleMessageCategory.NODENAME, logonInfo.getClient(), null);
			if (tmpRoleMessageList != null && tmpRoleMessageList.size() > 0) {
				roleMessageCategoryList.addAll(tmpRoleMessageList);
			}
		}
		/**
		 * Refresh the message category list and delete the duplicate item
		 */
		List<ServiceEntityNode> refreshMessageCategoryList = new ArrayList<>();
		if (roleMessageCategoryList == null
				|| roleMessageCategoryList.size() == 0) {
			throw new AuthorizationException(
					AuthorizationException.TYPE_NO_AUTHORIZATION);
		}
		for (ServiceEntityNode seNode : roleMessageCategoryList) {
			RoleMessageCategory roleMessageCategory = (RoleMessageCategory) seNode;
			boolean duplicateFlag = false;
			for (ServiceEntityNode tmpSENode : refreshMessageCategoryList) {
				RoleMessageCategory tmpMessageCategory = (RoleMessageCategory) tmpSENode;
				if (tmpMessageCategory.getMessageCategory() == roleMessageCategory
						.getMessageCategory()) {
					duplicateFlag = true;
					break;
				}
			}
			if (!duplicateFlag) {
				refreshMessageCategoryList.add(roleMessageCategory);
			}
		}
		List<LogonUserMessageCategory> popupMSGCategoryTrunkList = new ArrayList<LogonUserMessageCategory>();
		for (ServiceEntityNode tmpSENode : refreshMessageCategoryList) {
			RoleMessageCategory tmpMessageCategory = (RoleMessageCategory) tmpSENode;
			IUserMessageProcessHandler userMessageProcessHandler = messageCenterProcessFactory
					.getMessageProcessHandler(tmpMessageCategory
							.getMessageCategory());
			if (userMessageProcessHandler != null) {
				LogonUserMessageCategory refreshCategory =
						userMessageProcessHandler.generateCategoryTurnk(logonInfo);
				if (refreshCategory.getPopupFlag()) {
					// In case message need popup on screen
					popupMSGCategoryTrunkList.add(refreshCategory);
				}
			}
		}
		if(popupMSGCategoryTrunkList == null || popupMSGCategoryTrunkList.size() == 0){
			return null;
		}
		List<LogonUserMessageCategory> popupMSGCategoryList = new ArrayList<LogonUserMessageCategory>();
		for (LogonUserMessageCategory messageTrunk : popupMSGCategoryTrunkList) {
			
			IUserMessageProcessHandler userMessageProcessHandler = messageCenterProcessFactory
					.getMessageProcessHandler(messageTrunk.getCategory());
			if (userMessageProcessHandler != null) {
				LogonUserMessageCategory refreshCategory = userMessageProcessHandler
						.generateCategory(logonInfo);
				List<LogonUserMessage> logonUserMessageList = refreshCategory
						.getMessageList();
				if (logonUserMessageList != null
						&& logonUserMessageList.size() > 0) {
					if (refreshCategory.getPopupFlag()) {
						// In case message need popup on screen
						popupMSGCategoryList.add(refreshCategory);
					}
				}
			}
		}
		return popupMSGCategoryList;

	}

	/**
	 * Generate compound message category list by logon User The compund message
	 * category list is actually generated by logon user's role
	 * 
	 * @param logonInfo
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws AuthorizationException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws IOException
	 * @throws SearchConfigureException
	 * @throws LogonInfoException
	 */
	public List<LogonUserMessageCategory> generateMessageCategoryList(
			LogonInfo logonInfo) throws AuthorizationException,
			LogonUserMessageException, ServiceEntityConfigureException,
			LogonInfoException {
		/**
		 * Get all role message instance list from logon user
		 */
		List<Role> roleList = logonUserManager.getRoleList(logonInfo.getRefUserUUID(),
				logonInfo.getClient());
		if (roleList == null || roleList.size() == 0) {
			throw new AuthorizationException(
					AuthorizationException.TYPE_NO_AUTHORIZATION);
		}
		List<ServiceEntityNode> roleMessageCategoryList = new ArrayList<>();
		for (Role role : roleList) {
			List<ServiceEntityNode> tmpRoleMessageList = roleManager
					.getEntityNodeListByKey(role.getUuid(),
							IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							RoleMessageCategory.NODENAME, logonInfo.getClient(), null);
			if (tmpRoleMessageList != null && tmpRoleMessageList.size() > 0) {
				roleMessageCategoryList.addAll(tmpRoleMessageList);
			}
		}
		/**
		 * Refresh the message category list and delete the duplicate item
		 */
		List<ServiceEntityNode> refreshMessageCategoryList = new ArrayList<>();
		if (roleMessageCategoryList == null
				|| roleMessageCategoryList.size() == 0) {
			throw new AuthorizationException(
					AuthorizationException.TYPE_NO_AUTHORIZATION);
		}
		for (ServiceEntityNode seNode : roleMessageCategoryList) {
			RoleMessageCategory roleMessageCategory = (RoleMessageCategory) seNode;
			boolean duplicateFlag = false;
			for (ServiceEntityNode tmpSENode : refreshMessageCategoryList) {
				RoleMessageCategory tmpMessageCategory = (RoleMessageCategory) tmpSENode;
				if (tmpMessageCategory.getMessageCategory() == roleMessageCategory
						.getMessageCategory()) {
					duplicateFlag = true;
					break;
				}
			}
			if (!duplicateFlag) {
				refreshMessageCategoryList.add(roleMessageCategory);
			}
		}
		List<LogonUserMessageCategory> messageCategoryList = new ArrayList<LogonUserMessageCategory>();
		for (ServiceEntityNode tmpSENode : refreshMessageCategoryList) {
			RoleMessageCategory tmpMessageCategory = (RoleMessageCategory) tmpSENode;
			IUserMessageProcessHandler userMessageProcessHandler = messageCenterProcessFactory
					.getMessageProcessHandler(tmpMessageCategory
							.getMessageCategory());
			if (userMessageProcessHandler != null) {
				LogonUserMessageCategory refreshCategory = userMessageProcessHandler
						.generateCategory(logonInfo);
				List<LogonUserMessage> logonUserMessageList = refreshCategory
						.getMessageList();
				if (logonUserMessageList != null
						&& logonUserMessageList.size() > 0) {
					messageCategoryList.add(refreshCategory);
				}
			}
		}
		return messageCategoryList;
	}

	/**
	 * Get the category title by category key
	 * 
	 * @param category
	 * @return
	 * @throws LogonUserMessageException
	 */
	public String getMessageCategoryValue(int category)
			throws LogonUserMessageException {
		Map<Integer, String> categoryMap = getAllCategoryMap();
		return categoryMap.get(category);
	}

	/**
	 * Get all the category title map by retrieve from system resource file
	 * 
	 * @return
	 * @throws LogonUserMessageException
	 */
	public Map<Integer, String> getAllCategoryMap()
			throws LogonUserMessageException {
		try {
			String path = LogonUserMessageManager.class.getResource("")
					.getPath();
			Locale locale = ServiceLanHelper.getDefault();
			String resFileName = "LogonUserMessageCategory_title";
			Map<Integer, String> categoryMap = serviceDropdownListHelper
					.getDropDownMapInteger(path, resFileName, locale);
			return categoryMap;
		} catch (IOException ex) {
			throw new LogonUserMessageException(
					LogonUserMessageException.TYPE_WRONG_CATEGORYTITLE_CONFIG);
		}
	}

}
