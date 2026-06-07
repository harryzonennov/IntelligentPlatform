package com.company.IntelligentPlatform.salesDistribution.controller;

import java.util.List;

import com.company.IntelligentPlatform.salesDistribution.dto.*;
import com.company.IntelligentPlatform.salesDistribution.service.SalesReturnMaterialItemServiceModel;
import com.company.IntelligentPlatform.salesDistribution.service.SalesReturnMaterialItemExcelHelper;
import com.company.IntelligentPlatform.salesDistribution.service.SalesReturnOrderManager;

import com.company.IntelligentPlatform.salesDistribution.service.SalesReturnOrderSpecifier;
import com.company.IntelligentPlatform.salesDistribution.model.SalesReturnMaterialItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.DocActionException;
import com.company.IntelligentPlatform.platform.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "salesReturnMaterialItemListController")
@RequestMapping(value = "/salesReturnMaterialItem")
public class SalesReturnMaterialItemListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.SalesReturnOrder;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected SalesReturnOrderManager salesReturnOrderManager;

	@Autowired
	protected SalesReturnMaterialItemExcelHelper salesReturnMaterialItemExcelHelper;
	
	@Autowired
	private SalesReturnOrderSpecifier salesReturnOrderSpecifier;

	protected List<SalesReturnMaterialItemServiceUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(SalesReturnMaterialItemServiceUIModel.class,
				SalesReturnMaterialItemServiceModel.class, rawList,
				salesReturnOrderManager, SalesReturnMaterialItem.NODENAME, salesReturnOrderSpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(SalesReturnMaterialItemSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.SalesReturnMaterialItem,
						salesReturnMaterialItemExcelHelper, searchContext -> salesReturnOrderManager.getSearchProxy().searchItemList(searchContext),
						null, SalesReturnMaterialItemUIModel.class,  SalesReturnMaterialItem.NODENAME, salesReturnOrderSpecifier);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				null,
				SalesReturnMaterialItemSearchModel.class, searchContext -> salesReturnOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				request,
				SalesReturnMaterialItemSearchModel.class, searchContext -> salesReturnOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, SalesReturnMaterialItemSearchModel.class, searchContext -> salesReturnOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

}