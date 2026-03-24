package com.company.IntelligentPlatform.production.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.company.IntelligentPlatform.production.dto.ProcessBOMMaterialItemUIModel;
import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.ProcessBOMMaterialItemManager;
import com.company.IntelligentPlatform.production.service.ProcessBOMOrderManager;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.ProcessBOMMaterialItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
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
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "processBOMMaterialItemListController")
@RequestMapping(value = "/processBOMMaterialItem")
public class ProcessBOMMaterialItemListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_PROD_WORKCENTER;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProcessBOMOrderManager processBOMOrderManager;	

	@Autowired
	protected ProcessBOMMaterialItemManager processBOMMaterialItemManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;


	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ProcessBOMMaterialItemUIModel.class.getResource("")
				.getPath();
		String resFileName = ProcessBOMMaterialItem.NODENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	protected String getListViewName(int uiFlag) {
		if (uiFlag == UIFLAG_STANDARD) {
			return "Production/ProcessBOMMaterialItemList";
		}
		if (uiFlag == UIFLAG_CHOOSER) {
			return "Production/ProcessBOMMaterialItemChooser";
		}
		return "ProcessBOMMaterialItemList";
	}

	protected List<ProcessBOMMaterialItemUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ProcessBOMMaterialItemUIModel> processBOMMaterialItemList = new ArrayList<ProcessBOMMaterialItemUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ProcessBOMMaterialItemUIModel processBOMMaterialItemUIModel = new ProcessBOMMaterialItemUIModel();
			ProcessBOMMaterialItem processBOMMaterialItem = (ProcessBOMMaterialItem) rawNode;
			processBOMMaterialItemManager.convProcessBOMMaterialItemToUI(
					processBOMMaterialItem, processBOMMaterialItemUIModel);
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) billOfMaterialOrderManager
					.getEntityNodeByKey(
							processBOMMaterialItem.getRefUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							BillOfMaterialItem.NODENAME, null);
			processBOMMaterialItemManager.convBillOfMaterialItemToUI(
					billOfMaterialItem, processBOMMaterialItemUIModel);
			if (billOfMaterialItem != null) {
				MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
						.getEntityNodeByKey(billOfMaterialItem.getRefMaterialSKUUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								MaterialStockKeepUnit.NODENAME, null);
				processBOMMaterialItemManager.convMaterialStockKeepUnitToUI(
						materialStockKeepUnit, processBOMMaterialItemUIModel);
			}
			processBOMMaterialItemList.add(processBOMMaterialItemUIModel);
		}
		return processBOMMaterialItemList;
	}

}
