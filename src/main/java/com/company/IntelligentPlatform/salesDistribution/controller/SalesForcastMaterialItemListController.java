package com.company.IntelligentPlatform.salesDistribution.controller;

import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.salesDistribution.dto.*;
import com.company.IntelligentPlatform.salesDistribution.service.*;
import com.company.IntelligentPlatform.salesDistribution.model.SalesForcast;
import com.company.IntelligentPlatform.salesDistribution.model.SalesForcastMaterialItem;
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
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

import java.util.List;

@Scope("session")
@Controller(value = "salesForcastMaterialItemListController")
@RequestMapping(value = "/salesForcastMaterialItem")
public class SalesForcastMaterialItemListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.SalesForcast;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected SalesForcastManager salesForcastManager;

	@Autowired
	protected SalesForcastSpecifier salesForcastSpecifier;

	@Autowired
	protected SalesForcastMaterialItemExcelHelper salesForcastMaterialItemExcelHelper;

	protected List<SalesForcastMaterialItemServiceUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(SalesForcastMaterialItemServiceUIModel.class,
				SalesForcastMaterialItemServiceModel.class, rawList,
				salesForcastManager, SalesForcastMaterialItem.NODENAME, salesForcastSpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				null,
				SalesForcastMaterialItemSearchModel.class, searchContext -> salesForcastManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				request,
				SalesForcastMaterialItemSearchModel.class, searchContext -> salesForcastManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(SalesForcastMaterialItemSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.SalesForcastMaterialItem,
						salesForcastMaterialItemExcelHelper, searchContext -> salesForcastManager.getSearchProxy().searchItemList(searchContext),
						null, SalesForcastMaterialItemUIModel.class, SalesForcastMaterialItem.NODENAME, salesForcastSpecifier);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, SalesForcastMaterialItemSearchModel.class, searchContext -> salesForcastManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}
}