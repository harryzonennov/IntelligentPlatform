package com.company.IntelligentPlatform.logistics.dto;

import java.util.List;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryExcelReportProxy;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryServiceModel;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliverySpecifier;
import com.company.IntelligentPlatform.logistics.model.OutboundDelivery;
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
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Scope("session")
@Controller(value = "outboundDeliveryListController")
@RequestMapping(value = "/outboundDelivery")
public class OutboundDeliveryListController extends SEListController {

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected OutboundDeliveryExcelReportProxy outboundDeliveryExcelReportProxy;

	@Autowired
	protected LogonActionController logonActionController;
	
	@Autowired
	protected OutboundDeliveryServiceUIModelExtension outboundDeliveryServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected OutboundDeliverySpecifier outboundDeliverySpecifier;

	public final static String AOID_RESOURCE = IServiceModelConstants.OutboundDelivery;
	
	public Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected List<OutboundDeliveryServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(OutboundDeliveryServiceUIModel.class,
				OutboundDeliveryServiceModel.class, rawList,
				outboundDeliveryManager, OutboundDelivery.SENAME,  outboundDeliverySpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				OutboundDeliverySearchModel.class, searchContext -> outboundDeliveryManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				OutboundDeliverySearchModel.class, searchContext -> outboundDeliveryManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, OutboundDeliverySearchModel.class, searchContext -> outboundDeliveryManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/preGenExcelReport", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preGenExcelReport() {
		String response = serviceBasicUtilityController.preCheckResourceAccess(
				AOID_RESOURCE, ISystemActionCode.ACID_EXCEL);
		return response;
	}

}
