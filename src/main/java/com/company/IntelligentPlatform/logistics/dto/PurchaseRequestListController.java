package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.PurchaseRequestManager;
import com.company.IntelligentPlatform.logistics.service.PurchaseRequestServiceModel;
import com.company.IntelligentPlatform.logistics.service.PurchaseRequestSpecifier;
import com.company.IntelligentPlatform.logistics.model.PurchaseRequest;
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
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

@Scope("session")
@Controller(value = "purchaseRequestListController")
@RequestMapping(value = "/purchaseRequest")
public class PurchaseRequestListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.PurchaseRequest;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected PurchaseRequestManager purchaseRequestManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected PurchaseRequestSpecifier purchaseRequestSpecifier;

	protected Logger logger = LoggerFactory.getLogger(PurchaseRequestListController.class);

	protected List<PurchaseRequestServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(PurchaseRequestServiceUIModel.class,
				PurchaseRequestServiceModel.class, rawList,
				purchaseRequestManager, PurchaseRequest.SENAME, purchaseRequestSpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				PurchaseRequestSearchModel.class, searchContext -> purchaseRequestManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				PurchaseRequestSearchModel.class, searchContext -> purchaseRequestManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, PurchaseRequestSearchModel.class, searchContext -> purchaseRequestManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}
	
}
