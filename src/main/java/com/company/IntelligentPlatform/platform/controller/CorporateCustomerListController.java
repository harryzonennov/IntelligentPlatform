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
import com.company.IntelligentPlatform.platform.service.*;
import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;

@Scope("session")
@Controller(value = "corporateCustomerListController")
@RequestMapping(value = "/corporateCustomer")
public class CorporateCustomerListController extends SEListController {

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;
	
	@Autowired
	protected CorporateCustomerServiceUIModelExtension corporateCustomerServiceUIModelExtension;

	@Autowired
	protected CorporateCustomerListExcelHandler corporateCustomerListExcelHandler;

	protected Logger logger = LoggerFactory.getLogger(CorporateCustomerListController.class);

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_COR_CUSTOMER;

	protected List<CorporateCustomerServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException {
		return serviceBasicUtilityController.convServiceUIModuleList(CorporateCustomerServiceUIModel.class,
				CorporateCustomerServiceModel.class, rawList,
				corporateCustomerManager, corporateCustomerServiceUIModelExtension, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer, ServiceEntityConfigureException {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(CorporateCustomerSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, "CorporateCustomer",
						corporateCustomerListExcelHandler, searchContext -> corporateCustomerManager.getSearchProxy().searchDocList(searchContext),
						null, corporateCustomerServiceUIModelExtension);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

	protected List<CorporateCustomerUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(CorporateCustomerUIModel.class, rawList,
				corporateCustomerManager, corporateCustomerServiceUIModelExtension);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, CorporateCustomerSearchModel.class, searchContext -> corporateCustomerManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				CorporateCustomerSearchModel.class, searchContext -> corporateCustomerManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				CorporateCustomerSearchModel.class, searchContext -> corporateCustomerManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

}
