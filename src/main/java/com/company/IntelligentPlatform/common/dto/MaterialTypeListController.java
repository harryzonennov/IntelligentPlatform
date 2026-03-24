package com.company.IntelligentPlatform.common.dto;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

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

import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModuleConvertPara;

@Scope("session")
@Controller(value = "materialTypeListController")
@RequestMapping(value = "/materialType")
public class MaterialTypeListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.MaterialType;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected MaterialTypeManager materialTypeManager;

	@Autowired
	protected MaterialTypeServiceUIModelExtension materialTypeServiceUIModelExtension;

	@Autowired
	protected MaterialTypeExcelHandler materialTypeExcelHandler;

	protected Logger logger = LoggerFactory.getLogger(MaterialTypeListController.class);

	@RequestMapping(value = "/uploadExcel", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody String uploadExcel(
			HttpServletRequest request) {
		return serviceBasicUtilityController.uploadExcelWrapper(new ServiceBasicUtilityController.ExcelUploadRequest(request,
				this.materialTypeExcelHandler, AOID_RESOURCE, ISystemActionCode.ACID_EXCEL,
				(serviceExcelReportResponseModel, baseUUID) -> {
					try {
						materialTypeExcelHandler.updateDefExcelBatch(serviceExcelReportResponseModel,
								null, false, logonActionController.getSerialLogonInfo());
					} catch (ServiceEntityConfigureException e) {
						logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
						throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getMessage());
					}
				}));
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer, ServiceEntityConfigureException {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(MaterialTypeSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.Material,
						materialTypeExcelHandler, searchContext -> materialTypeManager.getSearchProxy().searchDocList(searchContext), null, materialTypeServiceUIModelExtension);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

	@RequestMapping(value = "/loadTreeListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadTreeListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				MaterialTypeSearchModel.class, searchContext -> materialTypeManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchTreeListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTreeListService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				MaterialTypeSearchModel.class, searchContext -> materialTypeManager.searchTreeList(searchContext), this::getServiceModuleListCore);
	}

	protected List<MaterialTypeServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException {
		List<ServiceModuleConvertPara> addConvertParaList =
				materialTypeManager.genMaterialTypeConvertParaList(logonActionController.getSerialLogonInfo(), "RootMaterialType");
		return serviceBasicUtilityController.convServiceUIModuleList(MaterialTypeServiceUIModel.class,
				MaterialTypeServiceModel.class, rawList,
				materialTypeManager, materialTypeServiceUIModelExtension,  addConvertParaList, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, MaterialTypeSearchModel.class, searchContext -> materialTypeManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				MaterialTypeSearchModel.class, searchContext -> materialTypeManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				MaterialTypeSearchModel.class, searchContext -> materialTypeManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService() {
		return serviceBasicUtilityController.loadLeanModuleListWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, null,
				MaterialTypeSearchModel.class, searchContext -> materialTypeManager
						.getSearchProxy().searchDocList(searchContext));
	}


}
