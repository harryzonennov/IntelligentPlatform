package com.company.IntelligentPlatform.finance.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.finance.dto.FinAccountSearchModel;
import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.model.FinAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.DefTargetMessageHandlerFrameWork;
import com.company.IntelligentPlatform.common.service.IMessageCategory;
import com.company.IntelligentPlatform.common.service.LogonUserMessage;
import com.company.IntelligentPlatform.common.service.LogonUserMessageCategory;
import com.company.IntelligentPlatform.common.service.LogonUserMessageException;
import com.company.IntelligentPlatform.common.service.BSearchResponse;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchContextBuilder;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class FinAccountToVerifyMessageHandler extends
		DefTargetMessageHandlerFrameWork {

	@Autowired
	protected FinAccountManager finAccountManager;

	@Autowired
	protected LogonInfoManager logonInfoManager;

	@Autowired
	protected LogonUserManager logonUserManager;
	
	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	/**
	 * Logic to find the raw target object list
	 * 
	 * @param logonInfo
	 * @return
	 * @throws LogonInfoException
	 * @throws AuthorizationException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityConfigureException
	 * @throws SearchConfigureException
	 */
	@Override
	public List<ServiceEntityNode> getRawTargetList(LogonInfo logonInfo) throws LogonInfoException,
			AuthorizationException, SearchConfigureException,
			ServiceEntityConfigureException, NodeNotFoundException,
			ServiceEntityInstallationException {
		Organization organization = logonInfoManager
				.getOrganizationByUserBackend(logonInfo.getRefUserUUID());
		if (organization == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_ORGNIZATION);
		}		
		FinAccountSearchModel finAccountSearchModel = new FinAccountSearchModel();
		finAccountSearchModel.setResOrgUUID(organization.getUuid());
		finAccountSearchModel.setVerifyStatus(FinAccount.VERIFIED_UNDONE);
		finAccountSearchModel.setAuditStatus(FinAccount.AUDIT_DONE);
		SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(finAccountSearchModel);
		BSearchResponse bSearchResponse = finAccountManager.getSearchProxy().searchDocList(searchContextBuilder.build());
		return bSearchResponse.getResultList();
	}

	/**
	 * Logic to convert target object to logonUser message
	 * 
	 * @param target
	 * @return
	 * @throws IOException
	 * @throws ServiceEntityInstallationException 
	 */
	@Override
	protected LogonUserMessage convertToMessage(ServiceEntityNode target)
			throws IOException, ServiceEntityInstallationException {
		FinAccount finAccount = (FinAccount) target;
		LogonUserMessage logonUserMessage = new LogonUserMessage();
		logonUserMessage.setCategory(IMessageCategory.FINACC_TO_VERIFY);
		logonUserMessage.setTargetID(finAccount.getId());
		logonUserMessage.setTargetUUID(finAccount.getUuid());
		logonUserMessage.setTargetStatus(finAccount.getVerifyStatus());
		logonUserMessage.setMessageType(LogonUserMessage.MESSAGE_TYPE_NOTIFICATION);
		String typeValue = getMessageTypeByKey(LogonUserMessage.MESSAGE_TYPE_NOTIFICATION);
		logonUserMessage.setMessageTypeValue(typeValue);
		Map<Integer, String> verifyStatusMap = serviceDropdownListHelper
				.getUIDropDownMap(FinAccountSearchModel.class, "verifyStatus");
		logonUserMessage.setTargetStatusValue(verifyStatusMap.get(finAccount.getVerifyStatus()));
		String targetURL = genRawTargetURL() + "?uuid=" + finAccount.getUuid();
		logonUserMessage.setTargetURL(targetURL);
 		return logonUserMessage;
	}

	@Override
	public List<LogonUserMessageCategory> generateCategoryList(
			LogonInfo logonInfo, AuthorizationObject authorizationObject) {
		return null;
	}

	@Override
	public List<LogonUserMessage> generateMessageList(LogonInfo logonInfo, List<ServiceEntityNode> rawList) throws LogonInfoException,
	AuthorizationException,LogonUserMessageException {		
		try {
			rawList = 	getRawTargetList(logonInfo);
			List<LogonUserMessage> logonUserMessageList = new ArrayList<LogonUserMessage>();
			if (rawList != null && rawList.size() > 0) {
				for (ServiceEntityNode seNode : rawList) {
					LogonUserMessage logonUserMessage = convertToMessage(seNode);
					logonUserMessageList.add(logonUserMessage);
				}
			}
			return logonUserMessageList;
		} catch (SearchConfigureException e) {
			throw new LogonUserMessageException(
					LogonUserMessageException.TYPE_SERVICE_INNER_CONFIG,
					e.getErrorMessage());
		} catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
			throw new LogonUserMessageException(
					LogonUserMessageException.TYPE_SERVICE_INNER_CONFIG,
					e.getMessage());
		} catch (IOException e) {
			throw new LogonUserMessageException(
					LogonUserMessageException.TYPE_WRONG_CATEGORYTITLE_CONFIG);
		}		
	}
	
	@Override
	public LogonUserMessageCategory generateCategoryTurnk(LogonInfo logonInfo)
			throws LogonUserMessageException {
		LogonUserMessageCategory logonUserMessageCategory = new LogonUserMessageCategory();
		logonUserMessageCategory.setCategory(IMessageCategory.FINACC_TO_VERIFY);
		String categoryTitle = getCategoryTitle();
		logonUserMessageCategory.setCategoryTitle(categoryTitle);
		logonUserMessageCategory.setListURL(genListURL());	
		return logonUserMessageCategory;
	}

	@Override
	public LogonUserMessageCategory generateCategory(LogonInfo logonInfo)
			throws LogonInfoException, AuthorizationException,
			LogonUserMessageException {
		LogonUserMessageCategory logonUserMessageCategory = generateCategoryTurnk(logonInfo);
		List<ServiceEntityNode> rawList;
		try {
			rawList = getRawTargetList(logonInfo);
			List<LogonUserMessage> messageList = generateMessageList(logonInfo,
					rawList);
			logonUserMessageCategory.setMessageList(messageList);
			logonUserMessageCategory.setRawSEList(rawList);
			return logonUserMessageCategory;
		} catch (SearchConfigureException e) {
			throw new LogonUserMessageException(
					LogonUserMessageException.TYPE_SERVICE_INNER_CONFIG,
					e.getErrorMessage());
		} catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
			throw new LogonUserMessageException(
					LogonUserMessageException.TYPE_SERVICE_INNER_CONFIG,
					e.getMessage());
		}
	}

	@Override
	public String getCategoryTitle() throws LogonUserMessageException {
		return getCategoryTitleByKey(IMessageCategory.FINACC_TO_VERIFY);
	}

	@Override
	protected String genListURL() {
		String listURL = "../finAccount/loadModuleList.html";
		return listURL;
	}

	@Override
	protected String genRawTargetURL() {
		String rawURL = "../finAccount/loadModuleEdit.html";
		return rawURL;
	}

}
