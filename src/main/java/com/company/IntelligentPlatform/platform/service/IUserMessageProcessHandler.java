package com.company.IntelligentPlatform.platform.service;

import java.util.List;

import com.company.IntelligentPlatform.platform.service.AuthorizationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.model.LogonInfo;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.AuthorizationObject;
import com.company.IntelligentPlatform.platform.model.LogonUser;

public interface IUserMessageProcessHandler {

	public List<LogonUserMessageCategory> generateCategoryList(
			LogonInfo logonInfo, AuthorizationObject authorizationObject);

	/**
	 * Get Compound message category information, including message data
	 * @param logonInfo
	 * @return
	 * @throws AuthorizationException
	 * @throws LogonUserMessageException
	 * @throws LogonInfoException
	 */
	public LogonUserMessageCategory generateCategory(LogonInfo logonInfo) throws
			AuthorizationException,LogonUserMessageException, LogonInfoException;
	
	/**
	 * Get message category Trunk information, only basic attribute information
	 * @param logonInfo
	 * @return
	 * @throws AuthorizationException
	 * @throws LogonUserMessageException
	 * @throws LogonInfoException
	 */
	public LogonUserMessageCategory generateCategoryTurnk(LogonInfo logonInfo)throws LogonUserMessageException;

	public List<LogonUserMessage> generateMessageList(LogonInfo logonInfo, List<ServiceEntityNode> rawList) throws LogonInfoException,
	AuthorizationException,LogonUserMessageException;

	public String getCategoryTitle() throws LogonUserMessageException;

}
