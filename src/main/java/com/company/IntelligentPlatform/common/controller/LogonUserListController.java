package com.company.IntelligentPlatform.common.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.OrganizationFactoryService;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonUserListExcelHandler;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.dto.LogonUserSearchModel;
import com.company.IntelligentPlatform.common.dto.LogonUserServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.LogonUserUIModel;
import com.company.IntelligentPlatform.common.dto.RoleMessageHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.RoleManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;

@Scope("session")
@Controller(value = "logonUserListController")
@RequestMapping(value = "/logonUser")
public class LogonUserListController extends SEListController {

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected OrganizationFactoryService organizationFactoryService;

	@Autowired
	protected RoleManager roleManager;

	@Autowired
	protected LogonUserListExcelHandler logonUserListExcelHandler;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected RoleMessageHelper roleMessageHelper;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;	
	
	@Autowired
	protected LogonUserServiceUIModelExtension logonUserServiceUIModelExtension;
	

	public final static String AOID_RESOURCE = IServiceModelConstants.LogonUser;

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, LogonUserSearchModel.class, searchContext -> logonUserManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(LogonUserSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.RegisteredProduct,
						logonUserListExcelHandler, searchContext -> logonUserManager.getSearchProxy().searchDocList(searchContext),
						null, logonUserServiceUIModelExtension);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService() {
		return serviceBasicUtilityController.loadLeanModuleListWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, null,
				LogonUserSearchModel.class, searchContext -> logonUserManager
						.getSearchProxy().searchDocList(searchContext));
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				LogonUserSearchModel.class, searchContext -> logonUserManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				LogonUserSearchModel.class, searchContext -> logonUserManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	protected List<LogonUserUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(LogonUserUIModel.class, rawList,
				logonUserManager, logonUserServiceUIModelExtension);
	}

}
