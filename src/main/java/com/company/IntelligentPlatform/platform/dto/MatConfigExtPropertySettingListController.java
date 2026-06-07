package com.company.IntelligentPlatform.platform.dto;

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

import com.company.IntelligentPlatform.platform.service.MatConfigExtPropertyManager;
import com.company.IntelligentPlatform.platform.service.MaterialConfigureTemplateManager;
import com.company.IntelligentPlatform.platform.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.AuthorizationException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.ServiceJSONParser;
import com.company.IntelligentPlatform.platform.service.BsearchService;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.platform.model.LogonUser;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.NodeNotFoundException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "matConfigExtPropertySettingListController")
@RequestMapping(value = "/matConfigExtPropertySetting")
public class MatConfigExtPropertySettingListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.MaterialConfigureTemplate;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

	@Autowired
	protected MatConfigExtPropertyManager matConfigExtPropertyManager;

	protected List<MatConfigExtPropertySettingUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<MatConfigExtPropertySettingUIModel> matConfigExtPropertySettingList = new ArrayList<MatConfigExtPropertySettingUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			MatConfigExtPropertySettingUIModel matConfigExtPropertySettingUIModel = new MatConfigExtPropertySettingUIModel();
			MatConfigExtPropertySetting matConfigExtPropertySetting = (MatConfigExtPropertySetting) rawNode;
			matConfigExtPropertyManager.convMatConfigExtPropertySettingToUI(matConfigExtPropertySetting, matConfigExtPropertySettingUIModel);
			matConfigExtPropertySettingList.add(matConfigExtPropertySettingUIModel);
		}
		return matConfigExtPropertySettingList;
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
			MatConfigExtPropertySettingSearchModel matConfigExtPropertySettingSearchModel = new MatConfigExtPropertySettingSearchModel();
			List<ServiceEntityNode> rawList = materialConfigureTemplateManager
					.searchExtPropertySetting(matConfigExtPropertySettingSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			String result = ServiceJSONParser
					.genDefOKJSONArray(rawList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
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
			MatConfigExtPropertySettingSearchModel matConfigExtPropertySettingSearchModel = new MatConfigExtPropertySettingSearchModel();
			List<ServiceEntityNode> rawList = materialConfigureTemplateManager
					.searchExtPropertySetting(matConfigExtPropertySettingSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<MatConfigExtPropertySettingUIModel> matConfigExtPropertySettingUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(matConfigExtPropertySettingUIModelList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(request);
			MatConfigExtPropertySettingSearchModel matConfigExtPropertySettingSearchModel = (MatConfigExtPropertySettingSearchModel) JSONObject
					.toBean(jsonObject,
							MatConfigExtPropertySettingSearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = materialConfigureTemplateManager
					.searchExtPropertySetting(matConfigExtPropertySettingSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<MatConfigExtPropertySettingUIModel> matConfigExtPropertySettingUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(matConfigExtPropertySettingUIModelList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

}
