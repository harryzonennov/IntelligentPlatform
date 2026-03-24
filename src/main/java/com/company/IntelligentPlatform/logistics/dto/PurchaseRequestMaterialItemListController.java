package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.PurchaseRequestMaterialItemServiceModel;
import com.company.IntelligentPlatform.logistics.service.PurchaseRequestManager;
import com.company.IntelligentPlatform.logistics.service.PurchaseRequestMaterialItemExcelHelper;
import com.company.IntelligentPlatform.logistics.service.PurchaseRequestSpecifier;
import com.company.IntelligentPlatform.logistics.model.PurchaseRequestMaterialItem;
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
@Controller(value = "purchaseRequestMaterialItemListController")
@RequestMapping(value = "/purchaseRequestMaterialItem")
public class PurchaseRequestMaterialItemListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.PurchaseRequest;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected PurchaseRequestManager purchaseRequestManager;

	@Autowired
	protected PurchaseRequestSpecifier purchaseRequestSpecifier;

	@Autowired
	protected PurchaseRequestMaterialItemExcelHelper purchaseRequestMaterialItemExcelHelper;

	protected List<PurchaseRequestMaterialItemServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(PurchaseRequestMaterialItemServiceUIModel.class,
				PurchaseRequestMaterialItemServiceModel.class, rawList,
				purchaseRequestManager, PurchaseRequestMaterialItem.NODENAME, purchaseRequestSpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				null,
				PurchaseRequestMaterialItemSearchModel.class, searchContext -> purchaseRequestManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				request,
				PurchaseRequestMaterialItemSearchModel.class, searchContext -> purchaseRequestManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(PurchaseRequestMaterialItemSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.PurchaseRequestMaterialItem,
						purchaseRequestMaterialItemExcelHelper, searchContext -> purchaseRequestManager.getSearchProxy().searchItemList(searchContext),
						null, PurchaseRequestMaterialItemUIModel.class, PurchaseRequestMaterialItem.NODENAME, purchaseRequestSpecifier);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, PurchaseRequestMaterialItemSearchModel.class, searchContext -> purchaseRequestManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

}
