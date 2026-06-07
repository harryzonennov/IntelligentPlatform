package com.company.IntelligentPlatform.platform.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.platform.dto.*;
import com.company.IntelligentPlatform.platform.dto.MaterialSearchModel;
import com.company.IntelligentPlatform.platform.service.EmployeeListExcelHandler;
import com.company.IntelligentPlatform.platform.service.EmployeeManager;
import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.*;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.service.AuthorizationException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.ServiceJSONParser;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.model.Employee;
import com.company.IntelligentPlatform.platform.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.LogonUser;

@Scope("session")
@Controller(value = "employeeListController")
@RequestMapping(value = "/employee")
public class EmployeeListController extends SEListController {

	@Autowired
	protected EmployeeManager employeeManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected LogonActionController logonActionController;

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_EMPLOYEE;
	
	@Autowired
	protected EmployeeServiceUIModelExtension employeeServiceUIModelExtension;

	@Autowired
	protected EmployeeListExcelHandler employeeListExcelHandler;
	
	protected Logger logger = LoggerFactory.getLogger(EmployeeListController.class);

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer, ServiceEntityConfigureException {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(EmployeeSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.Employee,
						employeeListExcelHandler, searchContext -> employeeManager.getSearchProxy().searchDocList(searchContext),
						null, employeeServiceUIModelExtension);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService() {
		return serviceBasicUtilityController.loadLeanModuleListWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, null,
				EmployeeSearchModel.class, searchContext -> employeeManager
						.getSearchProxy().searchDocList(searchContext));
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, EmployeeSearchModel.class, searchContext -> employeeManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				EmployeeSearchModel.class, searchContext -> employeeManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				EmployeeSearchModel.class, searchContext -> employeeManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	protected List<EmployeeUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(EmployeeUIModel.class, rawList,
				employeeManager, employeeServiceUIModelExtension);
	}

}
