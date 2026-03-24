package com.company.IntelligentPlatform.logistics.dto;

import java.util.List;

import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderManager;

import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderServiceModel;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderSpecifier;
import com.company.IntelligentPlatform.logistics.model.QualityInspectOrder;
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
@Controller(value = "qualityInspectOrderListController")
@RequestMapping(value = "/qualityInspectOrder")
public class QualityInspectOrderListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.QualityInspectOrder;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected QualityInspectOrderManager qualityInspectOrderManager;

	@Autowired
	protected QualityInspectOrderSpecifier qualityInspectOrderSpecifier;
	
	@Autowired
	protected QualityInspectOrderServiceUIModelExtension qualityInspectOrderServiceUIModelExtension;

	protected List<QualityInspectOrderServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(QualityInspectOrderServiceUIModel.class,
				QualityInspectOrderServiceModel.class, rawList,
				qualityInspectOrderManager, QualityInspectOrder.SENAME, qualityInspectOrderSpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				QualityInspectOrderSearchModel.class, searchContext -> qualityInspectOrderManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				QualityInspectOrderSearchModel.class, searchContext -> qualityInspectOrderManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}
	
	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, QualityInspectOrderSearchModel.class, searchContext -> qualityInspectOrderManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

}
