package com.company.IntelligentPlatform.logistics.dto;

import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InventoryTransferOrderManager;
import com.company.IntelligentPlatform.logistics.service.InventoryTransferItemExcelHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "inventoryTransferItemListController")
@RequestMapping(value = "/inventoryTransferItem")
public class InventoryTransferItemListController extends
		SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_WAREHOUSESTORE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected InventoryTransferOrderManager inventoryTransferOrderManager;

	@Autowired
	protected InventoryTransferItemExcelHelper inventoryTransferItemExcelHelper;
	
	@Autowired
	protected InventoryTransferItemServiceUIModelExtension inventoryTransferItemServiceUIModelExtension;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected List<InventoryTransferItemUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(InventoryTransferItemUIModel.class, rawList,
				inventoryTransferOrderManager, inventoryTransferItemServiceUIModelExtension);
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(InventoryTransferItemSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.InventoryTransferItem,
						inventoryTransferItemExcelHelper, searchContext -> inventoryTransferOrderManager.getSearchProxy().searchItemList(searchContext),
						null, inventoryTransferItemServiceUIModelExtension);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				InventoryTransferItemSearchModel.class, searchContext -> inventoryTransferOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				InventoryTransferItemSearchModel.class, searchContext -> inventoryTransferOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, InventoryTransferItemSearchModel.class, searchContext -> inventoryTransferOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

}
