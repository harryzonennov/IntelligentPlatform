package com.company.IntelligentPlatform.production.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.company.IntelligentPlatform.production.dto.ProcessBOMItemUIModel;
import com.company.IntelligentPlatform.production.service.ProcessBOMItemManager;
import com.company.IntelligentPlatform.production.service.ProcessBOMOrderManager;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
import com.company.IntelligentPlatform.production.service.ProdProcessManager;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.model.ProcessBOMItem;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "processBOMItemListController")
@RequestMapping(value = "/processBOMItem")
public class ProcessBOMItemListController extends SEListController {

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
	protected ProcessBOMItemManager processBOMItemManager;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;

	@Autowired
	protected ProdProcessManager prodProcessManager;



	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ProcessBOMItemUIModel.class.getResource("").getPath();
		String resFileName = ProcessBOMItem.NODENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	protected String getListViewName(int uiFlag) {
		if (uiFlag == UIFLAG_STANDARD) {
			return "ProcessBOMItemList";
		}
		if (uiFlag == UIFLAG_CHOOSER) {
			return "ProcessBOMItemChooser";
		}
		return "ProcessBOMItemList";
	}

	protected List<ProcessBOMItemUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ProcessBOMItemUIModel> processBOMItemList = new ArrayList<ProcessBOMItemUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ProcessBOMItemUIModel processBOMItemUIModel = new ProcessBOMItemUIModel();
			ProcessBOMItem processBOMItem = (ProcessBOMItem) rawNode;
			processBOMItemManager.convProcessBOMItemToUI(processBOMItem,
					processBOMItemUIModel);
			ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) processRouteOrderManager
					.getEntityNodeByKey(
							processBOMItem.getRefProssRouteProcessItemUUID(),
							"uuid", ProcessRouteProcessItem.NODENAME, null);
			processBOMItemManager.convProcessRouteProcessItemToUI(
					processRouteProcessItem, processBOMItemUIModel);
			ProdWorkCenter prodWorkCenter = (ProdWorkCenter) prodWorkCenterManager
					.getEntityNodeByKey(
							processRouteProcessItem.getRefWorkCenterUUID(),
							"uuid", ProdWorkCenter.NODENAME, null);
			processBOMItemManager.convProdWorkCenterToUI(prodWorkCenter,
					processBOMItemUIModel);
			if (processRouteProcessItem != null) {
				ProdProcess prodProcess = (ProdProcess) prodProcessManager
						.getEntityNodeByKey(
								processRouteProcessItem.getRefUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								ProdProcess.NODENAME, null);
				processBOMItemManager.convProdProcessToUI(prodProcess, processBOMItemUIModel);
			}
			processBOMItemList.add(processBOMItemUIModel);
		}
		return processBOMItemList;
	}

}
