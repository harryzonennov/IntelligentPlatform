package com.company.IntelligentPlatform.logistics.dto;

import java.util.List;

import com.company.IntelligentPlatform.logistics.dto.InboundItemSearchModel;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemLogManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreSearchProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "warehouseStoreItemLogListController")
@RequestMapping(value = "/warehouseStoreItemLog")
public class WarehouseStoreItemLogListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_WAREHOUSESTORE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected WarehouseStoreItemLogManager warehouseStoreItemLogManager;

	@Autowired
	protected WarehouseStoreSearchProxy warehouseStoreSearchProxy;

	@Autowired
	protected WarehouseStoreItemLogServiceUIModelExtension warehouseStoreItemLogServiceUIModelExtension;


	protected List<WarehouseStoreItemLogUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(WarehouseStoreItemLogUIModel.class, rawList,
				warehouseStoreManager, warehouseStoreItemLogServiceUIModelExtension);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				InboundItemSearchModel.class, searchContext -> warehouseStoreSearchProxy.searchItemLogList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				InboundItemSearchModel.class, searchContext -> warehouseStoreSearchProxy.searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, InboundItemSearchModel.class, searchContext -> warehouseStoreSearchProxy.searchItemList(searchContext),  this::getModuleListCore);
	}

}
