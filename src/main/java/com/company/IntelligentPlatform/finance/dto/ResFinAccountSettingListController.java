package com.company.IntelligentPlatform.finance.dto;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.service.SystemResourceException;
import com.company.IntelligentPlatform.finance.service.SystemResourceManager;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;
import com.company.IntelligentPlatform.common.model.SystemResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "resFinAccountSettingListController")
@RequestMapping(value = "/resFinAccountSetting")
public class ResFinAccountSettingListController extends SEListController {

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


	protected List<ResFinAccountSettingUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList, SystemResource systemResource)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, SystemResourceException {
		List<ResFinAccountSettingUIModel> resFinAccountSettingList = new ArrayList<ResFinAccountSettingUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ResFinAccountSettingUIModel resFinAccountSettingUIModel = new ResFinAccountSettingUIModel();
			ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) rawNode;
			convResFinAccountSettingToUI(resFinAccountSetting, systemResource,
					resFinAccountSettingUIModel);
			if (resFinAccountSetting != null) {
				FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
						.getEntityNodeByKey(resFinAccountSetting.getRefUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								FinAccountTitle.NODENAME, null);
				convFinAccountTitleToUI(finAccountTitle,
						resFinAccountSettingUIModel);
			}
			resFinAccountSettingList.add(resFinAccountSettingUIModel);
		}
		return resFinAccountSettingList;
	}


	protected void convResFinAccountSettingToUI(
			ResFinAccountSetting resFinAccountSetting,
			SystemResource systemResource,
			ResFinAccountSettingUIModel resFinAccountSettingUIModel)
			throws ServiceEntityInstallationException, SystemResourceException {
		if (resFinAccountSetting != null) {
			resFinAccountSettingUIModel.setUuid(resFinAccountSetting.getUuid());
			resFinAccountSettingUIModel.setParentNodeUUID(resFinAccountSetting
					.getParentNodeUUID());
			resFinAccountSettingUIModel.setId(resFinAccountSetting.getId());
			resFinAccountSettingUIModel.setName(resFinAccountSetting.getName());
			resFinAccountSettingUIModel.setNote(resFinAccountSetting.getNote());
			resFinAccountSettingUIModel
					.setRefFinAccObjectKey(resFinAccountSetting
							.getRefFinAccObjectKey());
			Map<Integer, String> finAccObjectKeyMap = systemResourceManager
					.getFinAccObjectKeyMap(systemResource
							.getControllerClassName());
			resFinAccountSettingUIModel
					.setRefFinAccObjectValue(finAccObjectKeyMap
							.get(resFinAccountSetting.getRefFinAccObjectKey()));
			Map<Integer, String> switchFlagMap = serviceDropdownListHelper
					.getUIDropDownMap(ResFinAccountSettingUIModel.class,
							"switchFlag");
			resFinAccountSettingUIModel.setSwitchFlagValue(switchFlagMap
					.get(resFinAccountSetting.getSwitchFlag()));
			resFinAccountSettingUIModel.setSwitchFlag(resFinAccountSetting
					.getSwitchFlag());
			resFinAccountSettingUIModel.setCoreSettleId(resFinAccountSetting.getCoreSettleID());
			resFinAccountSettingUIModel.setSettleUIModelName(resFinAccountSetting.getSettleUIModelName());
			resFinAccountSettingUIModel.setAllAmountFieldName(resFinAccountSetting.getAllAmountFieldName());
			resFinAccountSettingUIModel.setToSettleFieldName(resFinAccountSetting.getToSettleFieldName());
			resFinAccountSettingUIModel.setSettledFieldName(resFinAccountSetting.getSettledFieldName());
		}
	}

	protected void convFinAccountTitleToUI(FinAccountTitle finAccountTitle,
			ResFinAccountSettingUIModel resFinAccountSettingUIModel) {
		if (finAccountTitle != null) {
			resFinAccountSettingUIModel.setFinAccountTitleId(finAccountTitle
					.getId());
			resFinAccountSettingUIModel.setFinAccountTitleName(finAccountTitle
					.getName());
		}
	}

}
