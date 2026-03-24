package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceMessageHelper;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

public abstract class DefTargetMessageHandlerFrameWork implements
		IUserMessageProcessHandler {

	@Autowired
	protected ServiceMessageHelper serviceMessageHelper;

	public static String titleResourceName = "LogonUserMessageCategory_title";

	public static String messageTypeResourceName = "LogonUserMessage_type";

	/**
	 * Logic to get the raw target list
	 * 
	 * @param logonInfo
	 * @return
	 * @throws LogonInfoException
	 * @throws AuthorizationException
	 * @throws SearchConfigureException
	 * @throws ServiceEntityConfigureException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityInstallationException
	 */
	public abstract List<ServiceEntityNode> getRawTargetList(
			LogonInfo logonInfo) throws LogonInfoException,
			AuthorizationException, SearchConfigureException,
			ServiceEntityConfigureException, NodeNotFoundException,
			ServiceEntityInstallationException;

	protected abstract LogonUserMessage convertToMessage(
			ServiceEntityNode target) throws IOException,
			ServiceEntityInstallationException;

	protected abstract String genListURL();

	protected abstract String genRawTargetURL();

	/**
	 * The core method to return each message category title by category key.
	 * The category title is stored in resource file
	 * @param Key
	 * @return
	 * @throws LogonUserMessageException
	 */
	protected String getCategoryTitleByKey(int Key)
			throws LogonUserMessageException {
		try {
			String path = DefTargetMessageHandlerFrameWork.class
					.getResource("").getPath();
			String resFileFullName = path + titleResourceName;
			Map<Integer, String> errorMSGMap = serviceMessageHelper
					.getDropDownMap(resFileFullName);
			return errorMSGMap.get(Key);
		} catch (IOException e) {
			throw new LogonUserMessageException(
					LogonUserMessageException.TYPE_WRONG_CATEGORYTITLE_CONFIG);
		}
	}

	/**
	 * The core method to return each message category type by category key.
	 * The category type is stored in resource file
	 * @param Key
	 * @return
	 * @throws LogonUserMessageException
	 */
	protected String getMessageTypeByKey(int Key) throws IOException {
		String path = DefTargetMessageHandlerFrameWork.class.getResource("")
				.getPath();
		String resFileFullName = path + messageTypeResourceName;
		Map<Integer, String> errorMSGMap = serviceMessageHelper
				.getDropDownMap(resFileFullName);
		return errorMSGMap.get(Key);
	}

}
