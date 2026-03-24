package com.company.IntelligentPlatform.common.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
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
import com.company.IntelligentPlatform.common.model.ServiceModuleConvertPara;

@Scope("session")
@Controller(value = "materialStockKeepUnitListController")
@RequestMapping(value = "/materialStockKeepUnit")
public class MaterialStockKeepUnitListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected MaterialManager materialManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected MaterialStockKeepUnitListExcelHandler materialStockKeepUnitListExcelHandler;
	
	@Autowired
	protected MaterialStockKeepUnitServiceUIModelExtension materialStockKeepUnitServiceUIModelExtension;

	protected List<MaterialStockKeepUnitServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException {
		return serviceBasicUtilityController.convServiceUIModuleList(MaterialStockKeepUnitServiceUIModel.class,
				MaterialStockKeepUnitServiceModel.class, rawList,
				materialStockKeepUnitManager, materialStockKeepUnitServiceUIModelExtension, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				MaterialStockKeepUnitSearchModel.class, searchContext -> materialStockKeepUnitManager
						.getSearchProxy().searchDocList(searchContext),  null);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				MaterialStockKeepUnitSearchModel.class, searchContext -> materialStockKeepUnitManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer, ServiceEntityConfigureException {
		List<ServiceModuleConvertPara> additionalConvertParaList =
				materialManager.genStandardUnitConvertParaList(logonActionController.getSerialLogonInfo(), Material.SENAME);
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(MaterialStockKeepUnitSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.MaterialStockKeepUnit,
						materialStockKeepUnitListExcelHandler, searchContext -> materialStockKeepUnitManager.getSearchProxy().searchDocList(searchContext),
						additionalConvertParaList, materialStockKeepUnitServiceUIModelExtension);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

	@RequestMapping(value = "/searchLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchLeanModuleListService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				MaterialStockKeepUnitSearchModel.class, searchContext ->  materialStockKeepUnitManager
						.getSearchProxy().searchDocList(searchContext),  null);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				MaterialStockKeepUnitSearchModel.class, searchContext -> materialStockKeepUnitManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, MaterialStockKeepUnitSearchModel.class, searchContext -> materialStockKeepUnitManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

}
