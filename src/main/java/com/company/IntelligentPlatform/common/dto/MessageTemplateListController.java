package com.company.IntelligentPlatform.common.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.MessageTemplateManager;
import com.company.IntelligentPlatform.common.service.MessageTemplateServiceModel;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollection;

@Scope("session")
@Controller(value = "messageTemplateListController")
@RequestMapping(value = "/messageTemplate")
public class MessageTemplateListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.MessageTemplate;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected MessageTemplateManager messageTemplateManager;

	@Autowired
	protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

	@Autowired
	protected MessageTemplateServiceUIModelExtension messageTemplateServiceUIModelExtension;

	protected List<MessageTemplateServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException {
		return serviceBasicUtilityController.convServiceUIModuleList(MessageTemplateServiceUIModel.class,
				MessageTemplateServiceModel.class, rawList,
				messageTemplateManager, messageTemplateServiceUIModelExtension, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, MessageTemplateSearchModel.class, searchContext -> messageTemplateManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				MessageTemplateSearchModel.class, searchContext -> messageTemplateManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				MessageTemplateSearchModel.class, searchContext -> messageTemplateManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService() {
		return serviceBasicUtilityController.loadLeanModuleListWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, null,
				MessageTemplateSearchModel.class, searchContext -> messageTemplateManager
						.getSearchProxy().searchDocList(searchContext));
	}

	@RequestMapping(value = "/loadSystemCodeValueCollectionSelectList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadSystemCodeValueCollectionSelectList() {
		return serviceBasicUtilityController.getListMeta(() -> systemCodeValueCollectionManager
                .getEntityNodeListByKey(null, null,
                        SystemCodeValueCollection.NODENAME, null));
	}

}
