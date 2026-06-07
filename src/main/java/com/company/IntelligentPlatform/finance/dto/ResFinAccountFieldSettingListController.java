package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.service.SystemResourceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoManager;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.platform.model.ResFinAccountFieldSetting;

@Scope("session")
@Controller(value = "resFinAccountFieldSettingListController")
@RequestMapping(value = "/resFinAccountFieldSetting")
public class ResFinAccountFieldSettingListController extends SEListController {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonInfoManager logonInfoManager;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected SystemResourceManager systemResourceManager;

	@Autowired
	protected FinAccountTitleManager finAccountTitleManager;
	
	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;
	
	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_ROLE;

	protected void convResFinAccountFieldSettingToUI(
			ResFinAccountFieldSetting resFinAccountFieldSetting,
			ResFinAccountFieldSettingUIModel resFinAccountFieldSettingUIModel) {
		if (resFinAccountFieldSetting != null) {
			resFinAccountFieldSettingUIModel
					.setParentNodeUUID(resFinAccountFieldSetting
							.getParentNodeUUID());
			resFinAccountFieldSettingUIModel.setUuid(resFinAccountFieldSetting
					.getUuid());
			resFinAccountFieldSettingUIModel
					.setFieldName(resFinAccountFieldSetting.getFieldName());
			resFinAccountFieldSettingUIModel
					.setWeightFactor(resFinAccountFieldSetting
							.getWeightFactor());
			String fullName = resFinAccountFieldSetting
					.getFinAccProxyClassName();
			String simpleName = ServiceEntityFieldsHelper.getClassSimpleNameByFullName(fullName);
			resFinAccountFieldSettingUIModel
					.setFinAccProxyClassName(resFinAccountFieldSetting.getFinAccProxyClassName());
			resFinAccountFieldSettingUIModel.setFinAccProSimClassName(simpleName);
			resFinAccountFieldSettingUIModel
					.setFinAccProxyMethodName(resFinAccountFieldSetting
							.getFinAccProxyMethodName());
		}
	}

	protected List<ResFinAccountFieldSettingUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ResFinAccountFieldSettingUIModel> resFinAccountFieldSettingList = new ArrayList<ResFinAccountFieldSettingUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ResFinAccountFieldSettingUIModel resFinAccountFieldSettingUIModel = new ResFinAccountFieldSettingUIModel();
			ResFinAccountFieldSetting resFinAccountFieldSetting = (ResFinAccountFieldSetting) rawNode;
			convResFinAccountFieldSettingToUI(resFinAccountFieldSetting,
					resFinAccountFieldSettingUIModel);
			resFinAccountFieldSettingList.add(resFinAccountFieldSettingUIModel);
		}
		return resFinAccountFieldSettingList;
	}

}
