package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.finance.service.SystemResourceException;
import com.company.IntelligentPlatform.finance.service.SystemResourceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ResFinAccountProcessCode;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;
import com.company.IntelligentPlatform.common.model.SystemResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "resFinAccountProcessCodeListController")
@RequestMapping(value = "/resFinAccountProcessCode")
public class ResFinAccountProcessCodeListController extends SEListController {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonInfoManager logonInfoManager;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected SystemResourceManager systemResourceManager;
	
	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;
	
	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_ROLE;

	protected List<ResFinAccountProcessCodeUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList,
			ResFinAccountSetting resFinAccountSetting,
			SystemResource systemResource)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, SystemResourceException {
		List<ResFinAccountProcessCodeUIModel> resFinAccountProcessCodeList = new ArrayList<ResFinAccountProcessCodeUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ResFinAccountProcessCodeUIModel resFinAccountProcessCodeUIModel = new ResFinAccountProcessCodeUIModel();
			ResFinAccountProcessCode resFinAccountProcessCode = (ResFinAccountProcessCode) rawNode;
			convResFinAccountProcessCodeToUI(systemResource,
					resFinAccountProcessCode, resFinAccountProcessCodeUIModel);
			convResFinAccountSettingToUI(resFinAccountSetting,
					resFinAccountProcessCodeUIModel);
			resFinAccountProcessCodeList.add(resFinAccountProcessCodeUIModel);
		}
		return resFinAccountProcessCodeList;
	}

	protected void convResFinAccountProcessCodeToUI(
			SystemResource systemResource,
			ResFinAccountProcessCode resFinAccountProcessCode,
			ResFinAccountProcessCodeUIModel resFinAccountProcessCodeUIModel)
			throws ServiceEntityInstallationException, SystemResourceException {
		if (resFinAccountProcessCode != null) {
			resFinAccountProcessCodeUIModel.setUuid(resFinAccountProcessCode
					.getUuid());
			resFinAccountProcessCodeUIModel
					.setParentNodeUUID(resFinAccountProcessCode
							.getParentNodeUUID());
			Map<Integer, String> processCodeMap = systemResourceManager
					.getProcessCodeMap(systemResource.getControllerClassName());
			resFinAccountProcessCodeUIModel.setProcessCodeValue(processCodeMap
					.get(resFinAccountProcessCode.getProcessCode()));
			resFinAccountProcessCodeUIModel
					.setProcessCode(resFinAccountProcessCode.getProcessCode());
		}
	}

	protected void convResFinAccountSettingToUI(
			ResFinAccountSetting resFinAccountSetting,
			ResFinAccountProcessCodeUIModel resFinAccountProcessCodeUIModel) {
		if (resFinAccountSetting != null) {
			resFinAccountProcessCodeUIModel.setId(resFinAccountSetting.getId());
			resFinAccountProcessCodeUIModel.setName(resFinAccountSetting
					.getName());
		}
	}

}
