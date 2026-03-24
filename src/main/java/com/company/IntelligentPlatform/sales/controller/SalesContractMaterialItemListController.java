package com.company.IntelligentPlatform.sales.controller;
import com.company.IntelligentPlatform.sales.dto.SalesContractMaterialItemSearchModel;
import com.company.IntelligentPlatform.sales.dto.SalesContractMaterialItemServiceUIModel;
import com.company.IntelligentPlatform.sales.dto.SalesContractMaterialItemUIModel;
import com.company.IntelligentPlatform.sales.service.SalesContractManager;
import com.company.IntelligentPlatform.sales.service.SalesContractMaterialItemExcelHelper;

import com.company.IntelligentPlatform.sales.service.SalesContractMaterialItemServiceModel;
import com.company.IntelligentPlatform.sales.service.SalesContractSpecifier;
import com.company.IntelligentPlatform.sales.model.SalesContractMaterialItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

@Scope("session")
@Controller(value = "salesContractMaterialItemListController")
@RequestMapping(value = "/salesContractMaterialItem")
public class SalesContractMaterialItemListController extends SEListController {

	public static final String AOID_RESOURCE = SalesContractListController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected SalesContractManager salesContractManager;

	@Autowired
	protected SalesContractSpecifier salesContractSpecifier;

	@Autowired
	protected SalesContractMaterialItemExcelHelper salesContractMaterialItemExcelHelper;

	protected List<SalesContractMaterialItemServiceUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(SalesContractMaterialItemServiceUIModel.class,
				SalesContractMaterialItemServiceModel.class, rawList,
				salesContractManager, SalesContractMaterialItem.NODENAME, salesContractSpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				null,
				SalesContractMaterialItemSearchModel.class, searchContext -> salesContractManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				request,
				SalesContractMaterialItemSearchModel.class, searchContext -> salesContractManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(SalesContractMaterialItemSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.SalesContractMaterialItem,
						salesContractMaterialItemExcelHelper, searchContext -> salesContractManager.getSearchProxy().searchItemList(searchContext),
						null, SalesContractMaterialItemUIModel.class, SalesContractMaterialItem.NODENAME, salesContractSpecifier);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, SalesContractMaterialItemSearchModel.class, searchContext -> salesContractManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}
	
}