package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.PurchaseReturnMaterialItemServiceModel;
import com.company.IntelligentPlatform.logistics.service.PurchaseReturnMaterialItemExcelHelper;
import com.company.IntelligentPlatform.logistics.service.PurchaseReturnOrderManager;
import com.company.IntelligentPlatform.logistics.service.PurchaseReturnOrderSpecifier;
import com.company.IntelligentPlatform.logistics.model.PurchaseReturnMaterialItem;
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
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

@Scope("session")
@Controller(value = "purchaseReturnMaterialItemListController")
@RequestMapping(value = "/purchaseReturnMaterialItem")
public class PurchaseReturnMaterialItemListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.PurchaseReturnOrder;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected PurchaseReturnOrderManager purchaseReturnOrderManager;

	@Autowired
	protected PurchaseReturnOrderSpecifier purchaseReturnOrderSpecifier;

	@Autowired
	protected PurchaseReturnMaterialItemExcelHelper purchaseReturnOrderMaterialItemExcelHelper;

	protected List<PurchaseReturnMaterialItemServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(PurchaseReturnMaterialItemServiceUIModel.class,
				PurchaseReturnMaterialItemServiceModel.class, rawList,
				purchaseReturnOrderManager, PurchaseReturnMaterialItem.NODENAME, purchaseReturnOrderSpecifier,logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(PurchaseReturnMaterialItemSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.PurchaseReturnMaterialItem,
						purchaseReturnOrderMaterialItemExcelHelper, searchContext -> purchaseReturnOrderManager.getSearchProxy().searchItemList(searchContext),
						null, PurchaseReturnMaterialItemUIModel.class, PurchaseReturnMaterialItem.NODENAME, purchaseReturnOrderSpecifier);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}
	
	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, PurchaseReturnMaterialItemSearchModel.class, searchContext -> purchaseReturnOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				null,
				PurchaseReturnMaterialItemSearchModel.class, searchContext -> purchaseReturnOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				request,
				PurchaseReturnMaterialItemSearchModel.class, searchContext -> purchaseReturnOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

}
