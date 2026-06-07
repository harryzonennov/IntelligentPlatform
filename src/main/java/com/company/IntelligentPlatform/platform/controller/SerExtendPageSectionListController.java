package com.company.IntelligentPlatform.platform.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.dto.SerExtendPageSectionSearchModel;
import com.company.IntelligentPlatform.platform.dto.SerExtendPageSectionUIModel;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.AuthorizationException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.ServiceJSONParser;
import com.company.IntelligentPlatform.platform.service.BsearchService;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.service.SerExtendPageSectionManager;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.platform.model.LogonUser;
import com.company.IntelligentPlatform.platform.model.NodeNotFoundException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.SerExtendPageSection;

@Scope("session")
@Controller(value = "serExtendPageSectionListController")
@RequestMapping(value = "/serExtendPageSection")
public class SerExtendPageSectionListController extends SEListController {

	public static final String AOID_RESOURCE = SerExtendPageSectionEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected SerExtendPageSectionManager serExtendPageSectionManager;

	protected List<SerExtendPageSectionUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<SerExtendPageSectionUIModel> serExtendPageSectionList = new ArrayList<SerExtendPageSectionUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			SerExtendPageSectionUIModel serExtendPageSectionUIModel = new SerExtendPageSectionUIModel();
			SerExtendPageSection serExtendPageSection = (SerExtendPageSection) rawNode;
			serExtendPageSectionManager.convSerExtendPageSectionToUI(
					serExtendPageSection, serExtendPageSectionUIModel);
			serExtendPageSectionList.add(serExtendPageSectionUIModel);
		}
		return serExtendPageSectionList;
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
			SerExtendPageSectionSearchModel serExtendPageSectionSearchModel = new SerExtendPageSectionSearchModel();
			List<ServiceEntityNode> rawList = serExtendPageSectionManager
					.searchInternal(serExtendPageSectionSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());			
			String result = ServiceJSONParser
					.genDefOKJSONArray(rawList);
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
			SerExtendPageSectionSearchModel serExtendPageSectionSearchModel = new SerExtendPageSectionSearchModel();
			List<ServiceEntityNode> rawList = serExtendPageSectionManager
					.searchInternal(serExtendPageSectionSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<SerExtendPageSectionUIModel> serExtendPageSectionUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(serExtendPageSectionUIModelList);
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
			SerExtendPageSectionSearchModel serExtendPageSectionSearchModel = (SerExtendPageSectionSearchModel) JSONObject
					.toBean(jsonObject,
							SerExtendPageSectionSearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = serExtendPageSectionManager
					.searchInternal(serExtendPageSectionSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<SerExtendPageSectionUIModel> serExtendPageSectionUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(serExtendPageSectionUIModelList);
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
	
	@RequestMapping(value = "/searchLeanModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchLeanModuleService(@RequestBody String request) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(request);
			SerExtendPageSectionSearchModel serExtendPageSectionSearchModel = (SerExtendPageSectionSearchModel) JSONObject
					.toBean(jsonObject,
							SerExtendPageSectionSearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = serExtendPageSectionManager
					.searchInternal(serExtendPageSectionSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			String result = ServiceJSONParser
					.genDefOKJSONArray(rawList);
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

}
