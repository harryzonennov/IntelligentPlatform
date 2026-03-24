package com.company.IntelligentPlatform.finance.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.finance.dto.FinAccountTitleSearchModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountTitleUIModel;
import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "finAccountTitleListController")
@RequestMapping(value = "/finAccountTitle")
public class FinAccountTitleListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_FINACCOUNT;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected FinAccountTitleManager finAccountTitleManager;

	@RequestMapping(value = "/loadFinAccountTitleSelectList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadFinAccountTitleSelectList() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = finAccountTitleManager
					.getEntityNodeListByKey(null, null,
							FinAccountTitle.NODENAME, null);
			return ServiceJSONParser.genDefOKJSONArray(rawList);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	protected List<FinAccountTitleUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<FinAccountTitleUIModel> finAccountTitleList = new ArrayList<FinAccountTitleUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			FinAccountTitleUIModel finAccountTitleUIModel = new FinAccountTitleUIModel();
			FinAccountTitle finAccountTitle = (FinAccountTitle) rawNode;
			finAccountTitleManager.convFinAccountTitleToUI(finAccountTitle,
					finAccountTitleUIModel);
			FinAccountTitle parentAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(
							finAccountTitle.getParentAccountTitleUUID(),
							"uuid", FinAccountTitle.NODENAME, null);
			finAccountTitleManager.convParentFinAccountTitleToUI(
					parentAccountTitle, finAccountTitleUIModel);
			FinAccountTitle rootAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(
							finAccountTitle.getRootAccountTitleUUID(), "uuid",
							FinAccountTitle.NODENAME, null);
			finAccountTitleManager.convRootFinAccountTitleToUI(
					rootAccountTitle, finAccountTitleUIModel);

			finAccountTitleList.add(finAccountTitleUIModel);
		}
		return finAccountTitleList;
	}

	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccountTitleSearchModel finAccountTitleSearchModel = new FinAccountTitleSearchModel();
			List<ServiceEntityNode> rawList = finAccountTitleManager
					.searchInternal(finAccountTitleSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			String result = ServiceJSONParser.genDefOKJSONArray(rawList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccountTitleSearchModel finAccountTitleSearchModel = new FinAccountTitleSearchModel();
			List<ServiceEntityNode> rawList = finAccountTitleManager
					.searchInternal(finAccountTitleSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<FinAccountTitleUIModel> finAccountTitleUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(finAccountTitleUIModelList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(request);
			FinAccountTitleSearchModel finAccountTitleSearchModel = (FinAccountTitleSearchModel) JSONObject
					.toBean(jsonObject, FinAccountTitleSearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = finAccountTitleManager
					.searchInternal(finAccountTitleSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<FinAccountTitleUIModel> finAccountTitleUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(finAccountTitleUIModelList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/searchModuleTreeService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleTreeService(
			@RequestBody String request) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(request);
			FinAccountTitleSearchModel finAccountTitleSearchModel = (FinAccountTitleSearchModel) JSONObject
					.toBean(jsonObject, FinAccountTitleSearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = finAccountTitleManager
					.searchInternal(finAccountTitleSearchModel,
							logonUser.getClient());
			List<ServiceEntityNode> allList = finAccountTitleManager.getEntityNodeListByKey(null, null, FinAccountTitle.NODENAME, logonUser.getClient(), null);
			
			@SuppressWarnings("unchecked")
			List<ServiceEntityNode> resultList = (List<ServiceEntityNode>) ServiceCollectionsHelper
					.mergeToTreeListCore(rawList, allList, rawNode->{
						if(rawNode == null)return null;
						FinAccountTitle finAccountTitle = (FinAccountTitle)rawNode;
						return finAccountTitle.getParentAccountTitleUUID();
					},rawNode->{
						if(rawNode == null)return null;
						FinAccountTitle finAccountTitle = (FinAccountTitle)rawNode;
						return finAccountTitle.getUuid();
					});
			Collections.sort(resultList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<FinAccountTitleUIModel> finAccountTitleUIModelList = getModuleListCore(resultList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(finAccountTitleUIModelList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String result = this
					.searchTableCore(
							request,
							logonUser.getClient(),
							new ServiceEntityNodeLastUpdateTimeCompare(),
							FinAccountTitleSearchModel.class,
							searchModel -> {
								FinAccountTitleSearchModel finAccountTitleSearchModel = (FinAccountTitleSearchModel) searchModel;
								try {
									List<ServiceEntityNode> rawList = finAccountTitleManager
											.searchInternal(
													finAccountTitleSearchModel,
													logonUser.getClient());
									return rawList;
								} catch (SearchConfigureException | ServiceEntityInstallationException e) {
									throw e;
								} catch (NodeNotFoundException | ServiceEntityConfigureException e) {
									throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
								}
							},
							rawList -> {
								return getModuleListCore(rawList);
							});
			return result;
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | IOException | ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch ( ServiceModuleProxyException | SearchConfigureException  e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (DocActionException e) {
            throw new RuntimeException(e);
        }
    }

}
