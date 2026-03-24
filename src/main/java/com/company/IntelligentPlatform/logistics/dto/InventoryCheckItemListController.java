package com.company.IntelligentPlatform.logistics.dto;

import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InventoryCheckItemServiceModel;
import com.company.IntelligentPlatform.logistics.service.InventoryCheckOrderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "inventoryCheckItemListController")
@RequestMapping(value = "/inventoryCheckItem")
public class InventoryCheckItemListController extends SEListController {

	public static final String AOID_RESOURCE = InventoryCheckOrderEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected InventoryCheckOrderManager inventoryCheckOrderManager;

	@Autowired
	protected InventoryCheckItemServiceUIModelExtension inventoryCheckItemServiceUIModelExtension;

	protected Logger logger = LoggerFactory.getLogger(InventoryCheckItemListController.class);

	protected List<InventoryCheckItemServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException {
		return serviceBasicUtilityController.convServiceUIModuleList(InventoryCheckItemServiceUIModel.class,
				InventoryCheckItemServiceModel.class, rawList,
				inventoryCheckOrderManager, inventoryCheckItemServiceUIModelExtension, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				InventoryCheckItemSearchModel.class, searchContext -> inventoryCheckOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				InventoryCheckItemSearchModel.class, searchContext -> inventoryCheckOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, InventoryCheckItemSearchModel.class, searchContext -> inventoryCheckOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

}
