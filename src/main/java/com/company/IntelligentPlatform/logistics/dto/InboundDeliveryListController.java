package com.company.IntelligentPlatform.logistics.dto;

import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryServiceModel;
import com.company.IntelligentPlatform.logistics.service.InboundDeliverySpecifier;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
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
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Scope("session")
@Controller(value = "inboundDeliveryListController")
@RequestMapping(value = "/inboundDelivery")
public class InboundDeliveryListController extends SEListController {

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected LogonActionController logonActionController;
	
	@Autowired
	protected InboundDeliveryServiceUIModelExtension inboundDeliveryServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	private InboundDeliverySpecifier inboundDeliverySpecifier;

	public Logger logger = LoggerFactory.getLogger(InboundDeliveryListController.class);

	public static final String AOID_RESOURCE = IServiceModelConstants.InboundDelivery;

	protected List<InboundDeliveryServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(InboundDeliveryServiceUIModel.class,
				InboundDeliveryServiceModel.class, rawList,
				inboundDeliveryManager, InboundDelivery.SENAME, inboundDeliverySpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				InboundDeliverySearchModel.class, searchContext -> inboundDeliveryManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				InboundDeliverySearchModel.class, searchContext -> inboundDeliveryManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, InboundDeliverySearchModel.class, searchContext -> inboundDeliveryManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/preGenExcelReport", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preGenExcelReport() {
		String response = serviceBasicUtilityController.preCheckResourceAccess(
				AOID_RESOURCE, ISystemActionCode.ACID_EXCEL);
		return response;
	}


}
