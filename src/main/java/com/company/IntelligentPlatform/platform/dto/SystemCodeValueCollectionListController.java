package com.company.IntelligentPlatform.platform.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.SystemCodeValueCollectionServiceModel;
import com.company.IntelligentPlatform.platform.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "systemCodeValueCollectionListController")
@RequestMapping(value = "/systemCodeValueCollection")
public class SystemCodeValueCollectionListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.SystemCodeValueCollection;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

	@Autowired
	protected SystemCodeValueCollectionServiceUIModelExtension systemCodeValueCollectionServiceUIModelExtension;

	protected List<SystemCodeValueCollectionServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException {
		return serviceBasicUtilityController.convServiceUIModuleList(SystemCodeValueCollectionServiceUIModel.class,
				SystemCodeValueCollectionServiceModel.class, rawList,
				systemCodeValueCollectionManager, systemCodeValueCollectionServiceUIModelExtension, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				SystemCodeValueCollectionSearchModel.class, searchContext -> systemCodeValueCollectionManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				SystemCodeValueCollectionSearchModel.class, searchContext -> systemCodeValueCollectionManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, SystemCodeValueCollectionSearchModel.class, searchContext -> systemCodeValueCollectionManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

}
