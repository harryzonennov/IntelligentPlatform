package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.PurchaseContractManager;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractServiceModel;

import com.company.IntelligentPlatform.logistics.service.PurchaseContractSpecifier;
import com.company.IntelligentPlatform.logistics.model.PurchaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

@Scope("session")
@Controller(value = "purchaseContractListController")
@RequestMapping(value = "/purchaseContract")
public class PurchaseContractListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.PurchaseContract;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected PurchaseContractManager purchaseContractManager;

	@Autowired
	protected PurchaseContractSpecifier purchaseContractSpecifier;

	protected List<PurchaseContractServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(PurchaseContractServiceUIModel.class,
				PurchaseContractServiceModel.class, rawList,
				purchaseContractManager, PurchaseContract.SENAME, purchaseContractSpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				PurchaseContractSearchModel.class, searchContext -> purchaseContractManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				PurchaseContractSearchModel.class, searchContext -> purchaseContractManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, PurchaseContractSearchModel.class, searchContext -> purchaseContractManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

}
