package com.company.IntelligentPlatform.production.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.company.IntelligentPlatform.production.dto.ProdJobMaterialItemUIModel;
import com.company.IntelligentPlatform.production.service.ProdJobOrderManager;
import com.company.IntelligentPlatform.production.model.ProdJobMaterialItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "prodJobMaterialItemListController")
@RequestMapping(value = "/prodJobMaterialItem")
public class ProdJobMaterialItemListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_PROD_WORKCENTER;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProdJobOrderManager prodJobOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;


	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ProdJobMaterialItemUIModel.class.getResource("")
				.getPath();
		String resFileName = ProdJobMaterialItem.NODENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}


	protected List<ProdJobMaterialItemUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ProdJobMaterialItemUIModel> prodJobMaterialItemList = new ArrayList<ProdJobMaterialItemUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ProdJobMaterialItemUIModel prodJobMaterialItemUIModel = new ProdJobMaterialItemUIModel();
			ProdJobMaterialItem prodJobMaterialItem = (ProdJobMaterialItem) rawNode;
			prodJobOrderManager.convProdJobMaterialItemToComUI(
					prodJobMaterialItem, prodJobMaterialItemUIModel);			
			prodJobMaterialItemList.add(prodJobMaterialItemUIModel);
		}
		return prodJobMaterialItemList;
	}


}
