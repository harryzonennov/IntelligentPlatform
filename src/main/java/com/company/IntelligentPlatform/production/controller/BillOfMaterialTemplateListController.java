package com.company.IntelligentPlatform.production.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import com.company.IntelligentPlatform.logistics.dto.InventoryTransferItemSearchModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateListExcelHandler;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateSearchModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateUIModel;
import com.company.IntelligentPlatform.production.service.BillOfMaterialException;
import com.company.IntelligentPlatform.production.service.BillOfMaterialTemplateManager;
import com.company.IntelligentPlatform.production.service.BillOfMaterialTemplateReportProxy;

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
@Controller(value = "billOfMaterialTemplateListController")
@RequestMapping(value = "/billOfMaterialTemplate")
public class BillOfMaterialTemplateListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BillOfMaterialTemplateManager billOfMaterialTemplateManager;

	@Autowired
	protected BillOfMaterialTemplateReportProxy billOfMaterialTemplateReportProxy;

	@Autowired
	protected BillOfMaterialTemplateListExcelHandler billOfMaterialTemplateListExcelHandler;

	@Autowired
	protected BillOfMaterialTemplateServiceUIModelExtension billOfMaterialTemplateServiceUIModelExtension;

	protected Logger logger = LoggerFactory.getLogger(BillOfMaterialTemplateListController.class);

	protected List<BillOfMaterialTemplateUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(BillOfMaterialTemplateUIModel.class, rawList,
				billOfMaterialTemplateManager, billOfMaterialTemplateServiceUIModelExtension);
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(InventoryTransferItemSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.BillOfMaterialTemplate,
						billOfMaterialTemplateListExcelHandler, searchContext -> billOfMaterialTemplateManager.getSearchProxy().searchDocList(searchContext),
						null, billOfMaterialTemplateServiceUIModelExtension);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

	@RequestMapping(value = "/uploadExcel", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody String uploadExcel(
			HttpServletRequest request) {
		return serviceBasicUtilityController.uploadExcelWrapper(new ServiceBasicUtilityController.ExcelUploadRequest(request,
				this.billOfMaterialTemplateListExcelHandler, AOID_RESOURCE, ISystemActionCode.ACID_EXCEL,
				(serviceExcelReportResponseModel, baseUUID) -> {
					try {
						billOfMaterialTemplateReportProxy.updateExcelBatch(
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
				BillOfMaterialTemplateSearchModel.class, searchContext -> billOfMaterialTemplateManager
						.getSearchProxy().searchDocList(searchContext));
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				BillOfMaterialTemplateSearchModel.class, searchContext -> billOfMaterialTemplateManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				BillOfMaterialTemplateSearchModel.class, searchContext -> billOfMaterialTemplateManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, BillOfMaterialTemplateSearchModel.class, searchContext -> billOfMaterialTemplateManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

}
