package com.company.IntelligentPlatform.logistics.dto;

import java.util.List;

import com.company.IntelligentPlatform.logistics.service.*;

import com.company.IntelligentPlatform.logistics.model.InboundItem;
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
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "inboundItemListController")
@RequestMapping(value = "/inboundItem")
public class InboundItemListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.InboundDelivery;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	private InboundDeliverySpecifier inboundDeliverySpecifier;

	@Autowired
	protected InboundItemServiceUIModelExtension inboundItemServiceUIModelExtension;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected List<InboundItemServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(InboundItemServiceUIModel.class,
				InboundItemServiceModel.class, rawList,
				inboundDeliveryManager, InboundItem.NODENAME,  inboundDeliverySpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				InboundItemSearchModel.class, searchContext -> inboundDeliveryManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				InboundItemSearchModel.class, searchContext -> inboundDeliveryManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, InboundItemSearchModel.class, searchContext -> inboundDeliveryManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

}
