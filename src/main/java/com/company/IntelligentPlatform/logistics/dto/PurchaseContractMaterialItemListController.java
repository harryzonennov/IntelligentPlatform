package com.company.IntelligentPlatform.logistics.dto;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractManager;

import com.company.IntelligentPlatform.logistics.service.PurchaseContractMaterialItemExcelHelper;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractMaterialItemServiceModel;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractSpecifier;
import com.company.IntelligentPlatform.logistics.model.PurchaseContractMaterialItem;
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
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

@Scope("session")
@Controller(value = "purchaseContractMaterialItemListController")
@RequestMapping(value = "/purchaseContractMaterialItem")
public class PurchaseContractMaterialItemListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected PurchaseContractManager purchaseContractManager;

	@Autowired
	protected PurchaseContractSpecifier purchaseContractSpecifier;

	@Autowired
	protected PurchaseContractMaterialItemExcelHelper purchaseContractMaterialItemExcelHelper;

	protected List<PurchaseContractMaterialItemServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(PurchaseContractMaterialItemServiceUIModel.class,
				PurchaseContractMaterialItemServiceModel.class, rawList,
				purchaseContractManager, PurchaseContractMaterialItem.NODENAME, purchaseContractSpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, PurchaseContractMaterialItemSearchModel.class, searchContext -> purchaseContractManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				null,
				PurchaseContractMaterialItemSearchModel.class, searchContext -> purchaseContractManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				request,
				PurchaseContractMaterialItemSearchModel.class, searchContext -> purchaseContractManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(PurchaseContractMaterialItemSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.PurchaseContractMaterialItem,
						purchaseContractMaterialItemExcelHelper, searchContext -> purchaseContractManager.getSearchProxy().searchItemList(searchContext),
						null, PurchaseContractMaterialItemUIModel.class, PurchaseContractMaterialItem.NODENAME, purchaseContractSpecifier);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

}
