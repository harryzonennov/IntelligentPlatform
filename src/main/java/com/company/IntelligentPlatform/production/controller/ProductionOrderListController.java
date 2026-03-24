package com.company.IntelligentPlatform.production.controller;

import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProductionOrderSearchModel;
import com.company.IntelligentPlatform.production.dto.ProductionOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionOrderUIModel;
// TODO-DAO: import net.thorstein.production.DAO.ProductionOrderDAO;
import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;

import com.company.IntelligentPlatform.sales.dto.SalesContractSearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "productionOrderListController")
@RequestMapping(value = "/productionOrder")
public class ProductionOrderListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_PRODUCTIONORDER;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected ProductionOrderDAO productionOrderDAO;
	
	@Autowired
	protected ProductionOrderServiceUIModelExtension productionOrderServiceUIModelExtension;


	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService() {
		return serviceBasicUtilityController.loadLeanModuleListWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, null,
				ProductionOrderSearchModel.class, searchContext -> productionOrderManager
						.getSearchProxy().searchDocList(searchContext));
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				ProductionOrderSearchModel.class, searchContext -> productionOrderManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				ProductionOrderSearchModel.class, searchContext -> productionOrderManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, SalesContractSearchModel.class, searchContext -> productionOrderManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}
	
	protected List<ProductionOrderUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(ProductionOrderUIModel.class, rawList,
				productionOrderManager, productionOrderServiceUIModelExtension);
	}


}
