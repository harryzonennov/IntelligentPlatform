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

import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Scope("session")
@Controller(value = "materialListController")
@RequestMapping(value = "/material")
public class MaterialListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected MaterialManager materialManager;
	
	@Autowired
	protected MaterialServiceUIModelExtension materialServiceUIModelExtension;

	@Autowired
	protected MaterialListExcelHandler materialListExcelHandler;

	protected Logger logger = LoggerFactory.getLogger(MaterialListController.class);

	protected List<MaterialServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException {
		return serviceBasicUtilityController.convServiceUIModuleList(MaterialServiceUIModel.class,
				MaterialServiceModel.class, rawList,
				materialManager, materialServiceUIModelExtension, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/uploadExcel", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody String uploadExcel(
			HttpServletRequest request) {
		return serviceBasicUtilityController.uploadExcelWrapper(new ServiceBasicUtilityController.ExcelUploadRequest(request,
				this.materialListExcelHandler, AOID_RESOURCE, ISystemActionCode.ACID_EXCEL,
				(serviceExcelReportResponseModel, baseUUID) -> {
					try {
						materialListExcelHandler.updateDefExcelBatch(serviceExcelReportResponseModel,
								(ServiceExcelHandlerProxy.IConvertDocumentModel<MaterialUIModel,
										MaterialServiceModel>) (materialUIModel, materialServiceModel, serialLogonInfo) -> {
									Material material = materialServiceModel.getMaterial();
									materialManager.convUIToMaterial(materialUIModel, material);
								}, false, logonActionController.getSerialLogonInfo());
					} catch (ServiceEntityConfigureException e) {
						logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
						throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getMessage());
					}
				}));
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
            ServiceEntityExceptionContainer, ServiceEntityConfigureException {
		List<ServiceModuleConvertPara> additionalConvertParaList =
				materialManager.genStandardUnitConvertParaList(logonActionController.getSerialLogonInfo(), Material.SENAME);
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(MaterialSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.Material,
						materialListExcelHandler, searchContext -> materialManager.getSearchProxy().searchDocList(searchContext), additionalConvertParaList, materialServiceUIModelExtension);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, MaterialSearchModel.class, searchContext -> materialManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				MaterialSearchModel.class, searchContext -> materialManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				MaterialSearchModel.class, searchContext -> materialManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

}
