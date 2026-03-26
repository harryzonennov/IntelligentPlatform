package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.OrganizationFactoryService;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
// TODO-LEGACY: import platform.foundation.Administration.LogicManager.ClientInfoManager;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.dto.LogonInfoUIModel;
import com.company.IntelligentPlatform.common.dto.LogonUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.ServiceEncodeException;
import com.company.IntelligentPlatform.common.service.LogonUserMessageCategory;
import com.company.IntelligentPlatform.common.service.LogonUserMessageException;
import com.company.IntelligentPlatform.common.service.LogonUserMessageManager;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSimModel;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ActionCode;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Logon controller to control the user logon on action
 * 
 * @author Zhang,Hang
 * 
 * @date Feb 20, 2013
 */
@Scope("session")
@Controller(value = "logonActionController")
@RequestMapping(value = "/common")
public class LogonActionController extends SEListController {

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected LogonInfoManager logonInfoManager;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected AuthorizationManager authorizationManager;

	@Autowired
	protected CommonErrorMessageHelper commonErrorMessageHelper;

	// TODO-LEGACY: @Autowired

	// TODO-LEGACY: 	protected ClientInfoManager clientInfoManager;

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected DocumentSpecifierFactory documentSpecifierFactory;

	@Autowired
	protected ServiceLanHelper serviceLanHelper;

	/**
	 * Class attribute to store the all navigation element list by this logon
	 * user
	 */
	protected List<NavigationElementUnion> navigationElementList = new ArrayList<>();

	protected List<NavigationGroupUnion> navigationGroupList = new ArrayList<>();

	public static final String LABEL_NAVI_LOGISTICSCONTROL = "logisticsNaviContent";

	public static final String LABEL_NAVIELEMENT_LIST = "navigationElementList";

	public static final String LABEL_NAVIGROUP_LIST = "navigationGroupList";

	public static final String LABEL_NAVIELEMENT_ID = "navigationElementID";

	public static final String LABEL_NAVIGROUP_ID = "navigationGroupID";

	@Autowired
	protected OrganizationFactoryService organizationFactoryService;

	@Autowired
	protected LogonUserMessageManager logonUserMessageManager;

	/**
	 * Page info to forward after logon
	 */
	protected PageComInfo forwardPage;

	protected Organization organization;

	protected List<ServiceEntityNode> organizationList;

	protected LogonUser logonUser;

	protected LogonInfo logonInfo;

	public LogonActionController() {
	}

	@RequestMapping(value = "/getServiceDocumentMeta", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getServiceDocumentMeta(String modelId, String coreModelId) {
		try {
			DocumentContentSpecifier documentContentSpecifier = documentSpecifierFactory.getDocumentSpecifier(modelId);
			if(documentContentSpecifier == null){
				throw new DocActionException(DocActionException.PARA_MISS_CONFIG_SPECIFIER, modelId);
			}
			List<ServiceExtensionSimModel> serviceDocMetaList =
					documentContentSpecifier.getServiceDocumentMeta(modelId, coreModelId, this.getSerialLogonInfo());
			return ServiceJSONParser.genDefOKJSONArray(serviceDocMetaList);
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getMessage());
		} catch (DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/getLanguageMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getLanguageMap() {
		try {
			Map<String, String> languageMap =
					serviceLanHelper.getLanguageMap(getLanguageCode());
			return logonInfoManager.getSelectStringTypeMap(languageMap, false);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	@RequestMapping(value = "/getComNonImplementError", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getComNonImplementError() {
		String message = commonErrorMessageHelper
				.getMessage(CommonErrorMessageHelper.TYPE_NON_IMPLEMENT_FUNCTION);
		return ServiceJSONParser.generateSimpleErrorJSON(message);
	}

	public List<NavigationElementUnion> getNavigationElementList() {
		return navigationElementList;
	}

	public void setNavigationElementList(
			List<NavigationElementUnion> navigationElementList) {
		this.navigationElementList = navigationElementList;
	}

	public List<NavigationGroupUnion> getNavigationGroupList() {
		return navigationGroupList;
	}

	public void setNavigationGroupList(
			List<NavigationGroupUnion> navigationGroupList) {
		this.navigationGroupList = navigationGroupList;
	}

	public void registerSystemWarnMessage(LogonInfo logonInfo) {
		try {
			List<LogonUserMessageCategory> popupMessageList = logonUserMessageManager
					.generatePopupMessageCategoryList(logonInfo);
			if (popupMessageList != null && popupMessageList.size() > 0) {
				logonUser.setCheckSystemMessageFlag(true);
			}
		} catch (AuthorizationException e) {
			// do noting
		} catch (LogonUserMessageException e) {
			// do noting
		} catch (ServiceEntityConfigureException e) {
			// do noting
		} catch (LogonInfoException e) {
			// do noting
		}
	}

	public List<NavigationElementUnion> filterOutFirstLayerElementList(
			List<NavigationElementUnion> rawElementList) {
		if (ServiceCollectionsHelper.checkNullList(rawElementList)) {
			return null;
		}
		List<NavigationElementUnion> resultList = new ArrayList<>();
		for (NavigationElementUnion navigationElementUnion : rawElementList) {
			if (ServiceCollectionsHelper.checkNullList(navigationElementUnion
					.getSecondNavigationList())) {
				resultList.add(navigationElementUnion);
			}
		}
		return resultList;
	}

	@RequestMapping(value = "/unLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String unLock(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		try {
			LogonUser logonUser = this.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			lockObjectManager.unLockSEListByLogonUser(logonUser);
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/unLockAll", produces = "text/html;charset=UTF-8")
	public @ResponseBody String unLockAll(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		try {
			LogonUser logonUser = this.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			lockObjectManager.unLockAllSEList();
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/logoutService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String logoutService() {
		try {
			LogonUser logonUser = getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			lockObjectManager.unLockSEListByLogonUser(logonUser);
			setLogonUser(null);
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/loginService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loginService(
			@RequestBody LogonUIModel logonUIModel, @RequestHeader("accept-language") String language) {
		try {
			LoginComModel loginComModel = logonInfoManager.logon(
					logonUIModel,
					false);
			setCompoundLogon(loginComModel);
			return ServiceJSONParser.genDefOKJSONObject(logonUser);
		} catch (LogonInfoException | ServiceComExecuteException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (ServiceEncodeException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (ServiceEntityConfigureException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	} 	
	
	@RequestMapping(value = "/getLoginInfoService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getLoginInfoService() {
		try {
			LogonInfo logonInfo = this.getLogonInfo();
			LogonUser logonUser = this.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			LogonInfoUIModel logonInfoUIModel = new LogonInfoUIModel();
			logonInfoManager.convLogonInfoToUI(logonInfo, logonInfoUIModel);
			return ServiceJSONParser.genDefOKJSONObject(logonInfoUIModel);
		} catch (LogonInfoException ex) {
			return ex.generateSimpleErrorJSON();
		} catch (ServiceEncodeException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} 
	}

	@RequestMapping(value = "/initialLoginService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String initialLoginService(
			@RequestBody LogonUIModel logonUIModel) {
		try {
			// [Step1] check if new password and confirmed password matches
			if (logonUIModel.getNewPassword() == null
					|| logonUIModel.getNewPassword().equals(
							ServiceEntityStringHelper.EMPTYSTRING)) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_EMP_PASSWORD);
			}
			if (!logonUIModel.getNewPassword().equals(
					logonUIModel.getConfirmPassword())) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NOMATCH_PASSWORD);
			}
			LoginComModel loginComModel = logonInfoManager.initiallogon(
					logonUIModel);
			setCompoundLogon(loginComModel);
			return ServiceJSONParser.genDefOKJSONObject(logonUser);
		} catch (LogonInfoException | ServiceComExecuteException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (ServiceEncodeException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (ServiceEntityConfigureException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	public PageComInfo getForwardPage() {
		return forwardPage;
	}

	public void setForwardPage(PageComInfo forwardPage) {
		this.forwardPage = forwardPage;
	}

	/**
	 * When user first logon, using the Logon User information to get all the
	 * necessary
	 * 
	 * @param loginComModel
	 * @throws ServiceEntityConfigureException
	 */
	public void setCompoundLogon(LoginComModel loginComModel)
			throws ServiceEntityConfigureException {
		LogonUser logonUser = loginComModel.getLogonUser();
		LogonUserOrgReference logonOrg = (LogonUserOrgReference) logonUserManager
				.getEntityNodeByKey(logonUser.getUuid(),
						IServiceEntityNodeFieldConstant.ROOTNODEUUID,
						LogonUserOrgReference.NODENAME, null);
		if (logonOrg != null) {
			Organization organization = organizationFactoryService
					.getRefOrganization(logonOrg);
			loginComModel.getLogonInfo().setResOrgUUID(organization.getUuid());
			loginComModel.getLogonInfo().setHomeOrganization(organization);
    		setOrganization(organization);
//			List<ServiceEntityNode> allOrganizationList = organizationManager
//					.getAllSubOrganizationList(organization, true);
			List<ServiceEntityNode> allOrganizationList = organizationManager
					.getAllOrganizationList(organization.getClient());
			setOrganizationList(allOrganizationList);
			loginComModel.getLogonInfo().setOrganizationList(allOrganizationList);
			Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap = authorizationManager.getAuthorizationACListMap(logonUser);
			loginComModel.getLogonInfo().setAuthorizationActionCodeMap(authorizationActionCodeMap);
			List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList =
					authorizationManager.getAuthorizationACList(logonUser);
			loginComModel.getLogonInfo().setAuthorizationACUnionList(authorizationACUnionList);
		}
		setLogonUser(logonUser);
		setLogonInfo(loginComModel.getLogonInfo());
	}
	
	/**
	 * Manually sync user, organization information from login session
	 * @throws ServiceEntityConfigureException 
	 */
	public void syncLoginInfoFromSession() throws ServiceEntityConfigureException{
		LogonInfo logonInfo = this.getLogonInfo();
		LogonUser logonUser = this.getLogonUser();
		List<ServiceEntityNode> allOrganizationList = this.getOrganizationListByUser(logonUser);
		logonInfo.setLogonUser(logonUser);
		logonInfo.setHomeOrganization(this.getOrganizationByUser(logonUser.getUuid()));
		logonInfo.setOrganizationList(allOrganizationList);
		Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap = authorizationManager.getAuthorizationACListMap(logonUser);
		logonInfo.setAuthorizationActionCodeMap(authorizationActionCodeMap);
	}

	/**
	 * Get Organization information by current logonUser
	 * 
	 * @param logonUserUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Organization getOrganizationByUser(String logonUserUUID)
			throws ServiceEntityConfigureException {
		// get current registered logon organization
		Organization organization = getOrganization();
		if (organization == null) {
			organization = logonInfoManager
					.getOrganizationByUserBackend(logonUserUUID);
		}
		return organization;
	}

	/**
	 * Get Organization information by current logonUser
	 * 
	 * @param logonUser
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getOrganizationListByUser(LogonUser logonUser)
			throws ServiceEntityConfigureException {
		// get current registered logon organization
		List<ServiceEntityNode> resultList = getOrganizationList();
		if (resultList == null) {
			resultList = organizationManager
					.getOrganizationListByUserBackend(logonUser);
		}
		return resultList;
	}

	public Organization getOrganization()
			throws ServiceEntityConfigureException {
		if (organization == null) {
			if (this.logonUser != null && this.logonInfo != null) {
				setCompoundLogon(new LoginComModel(this.logonUser, this.logonInfo));
			}
		}
		return organization;
	}

	public SerialLogonInfo cloneToSerialLogonInfo(LogonInfo logonInfo){
		return LogonInfoManager.cloneToSerialLogonInfo(logonInfo);
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<ServiceEntityNode> getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(List<ServiceEntityNode> organizationList) {
		this.organizationList = organizationList;
	}

	public LogonUser getLogonUser() {
		return logonUser;
	}

	public void setLogonUser(LogonUser logonUser) {
		this.logonUser = logonUser;
	}

	public SerialLogonInfo getSerialLogonInfo(){
		return this.cloneToSerialLogonInfo(this.getLogonInfo());
	}

	public LogonInfo getLogonInfo() {
		return logonInfo;
	}

	public void setLogonInfo(LogonInfo logonInfo) {
		this.logonInfo = logonInfo;
	}
	
	/**
	 * Getter of get logon lanugage code from logonInfo, if no logonInfo, then return default
	 * @return
	 */
	public String getLanguageCode(){
		if(this.logonInfo != null){
			return this.logonInfo.getLanguageCode();
		}
		return ServiceLanHelper.getDefault().getLanguage();
	}

	/**
	 * Getter of get Res Org UUID from logonInfo, if no logonInfo, then return null
	 * @return
	 */
	public String getResOrgUUID(){
		if(this.logonInfo != null){
			return this.logonInfo.getResOrgUUID();
		}
		return null;
	}

	/**
	 * Getter of get Res User UUID from logonInfo, if no logonInfo, then return null
	 * @return
	 */
	public String getResUserUUID(){
		if(this.logonInfo != null){
			return this.logonInfo.getRefUserUUID();
		}
		return null;
	}

	/**
	 * Getter of get Client from logonInfo, if no logonInfo, then return null
	 * @return
	 */
	public String getClient(){
		if(this.logonInfo != null){
			return this.logonInfo.getClient();
		}
		return null;
	}

}
