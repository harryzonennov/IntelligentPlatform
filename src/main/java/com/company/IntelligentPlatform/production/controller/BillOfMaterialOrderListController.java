package com.company.IntelligentPlatform.production.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import com.company.IntelligentPlatform.logistics.dto.InventoryTransferItemSearchModel;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.service.BillOfMaterialException;
import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderReportProxy;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.LogonUserSearchModel;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "billOfMaterialOrderListController")
@RequestMapping(value = "/billOfMaterialOrder")
public class BillOfMaterialOrderListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;
	
	@Autowired
	protected BillOfMaterialOrderReportProxy billOfMaterialOrderReportProxy;

	@Autowired
	protected BillOfMaterialOrderListExcelHandler billOfMaterialOrderListExcelHandler;

	@Autowired
	protected BillOfMaterialOrderServiceUIModelExtension billOfMaterialOrderServiceUIModelExtension;

	protected Logger logger = LoggerFactory.getLogger(BillOfMaterialOrderListController.class);

	protected List<BillOfMaterialOrderUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(BillOfMaterialOrderUIModel.class, rawList,
				billOfMaterialOrderManager, billOfMaterialOrderServiceUIModelExtension);
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(InventoryTransferItemSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.BillOfMaterialTemplate,
						billOfMaterialOrderListExcelHandler, searchContext -> billOfMaterialOrderManager.getSearchProxy().searchDocList(searchContext),
						null, billOfMaterialOrderServiceUIModelExtension);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

	@RequestMapping(value = "/uploadExcel", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody String uploadExcel(
			HttpServletRequest request) {
		return serviceBasicUtilityController.uploadExcelWrapper(new ServiceBasicUtilityController.ExcelUploadRequest(request,
				this.billOfMaterialOrderListExcelHandler, AOID_RESOURCE, ISystemActionCode.ACID_EXCEL,
				(serviceExcelReportResponseModel, baseUUID) -> {
					try {
						billOfMaterialOrderReportProxy.updateExcelBatch(
								serviceExcelReportResponseModel, true,
								false, logonActionController.getResUserUUID(),
								logonActionController.getResOrgUUID(), logonActionController.getClient(),
								logonActionController.getLanguageCode());
					} catch (ServiceEntityConfigureException | BillOfMaterialException |
							 ServiceEntityInstallationException e) {
						logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
						throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getErrorMessage());
					}
				}));
	}
	
	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService() {
		return serviceBasicUtilityController.loadLeanModuleListWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, null,
				BillOfMaterialOrderSearchModel.class, searchContext -> {
					BillOfMaterialOrderSearchModel billOfMaterialOrderSearchModel = (BillOfMaterialOrderSearchModel) searchContext.getSearchModel();
					billOfMaterialOrderSearchModel.setServiceEntityName(BillOfMaterialOrder.SENAME);
					return billOfMaterialOrderManager
							.getSearchProxy().searchDocList(searchContext);
				});
	}

	@RequestMapping(value = "/loadModuleListByTemplate", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListByTemplate(String baseUUID) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				BillOfMaterialOrderSearchModel.class, searchContext -> {
					BillOfMaterialOrderSearchModel billOfMaterialOrderSearchModel = (BillOfMaterialOrderSearchModel) searchContext.getSearchModel();
					billOfMaterialOrderSearchModel.setRefMaterialSKUUUID(baseUUID);
					billOfMaterialOrderSearchModel.setStatus(BillOfMaterialOrder.STATUS_INUSE);
					billOfMaterialOrderSearchModel.setServiceEntityName(BillOfMaterialOrder.SENAME);
					return billOfMaterialOrderManager
							.getSearchProxy().searchDocList(searchContext);
				},  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				BillOfMaterialOrderSearchModel.class, searchContext -> {
					BillOfMaterialOrderSearchModel billOfMaterialOrderSearchModel = (BillOfMaterialOrderSearchModel) searchContext.getSearchModel();
					billOfMaterialOrderSearchModel.setServiceEntityName(BillOfMaterialOrder.SENAME);
					return billOfMaterialOrderManager
							.getSearchProxy().searchDocList(searchContext);
				},  this::getModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				LogonUserSearchModel.class,  searchContext -> {
					BillOfMaterialOrderSearchModel billOfMaterialOrderSearchModel = (BillOfMaterialOrderSearchModel) searchContext.getSearchModel();
					billOfMaterialOrderSearchModel.setServiceEntityName(BillOfMaterialOrder.SENAME);
					return billOfMaterialOrderManager
							.getSearchProxy().searchDocList(searchContext);
				},  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, BillOfMaterialOrderSearchModel.class,  searchContext -> {
					BillOfMaterialOrderSearchModel billOfMaterialOrderSearchModel = (BillOfMaterialOrderSearchModel) searchContext.getSearchModel();
					billOfMaterialOrderSearchModel.setServiceEntityName(BillOfMaterialOrder.SENAME);
					return billOfMaterialOrderManager
							.getSearchProxy().searchDocList(searchContext);
				},  this::getModuleListCore);
	}

}
