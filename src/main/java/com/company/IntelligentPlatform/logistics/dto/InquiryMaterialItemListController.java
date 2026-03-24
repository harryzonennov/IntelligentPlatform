package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.InquiryManager;
import com.company.IntelligentPlatform.logistics.service.InquiryMaterialItemExcelHelper;

import com.company.IntelligentPlatform.logistics.service.InquiryMaterialItemServiceModel;
import com.company.IntelligentPlatform.logistics.service.InquirySpecifier;
import com.company.IntelligentPlatform.logistics.model.InquiryMaterialItem;
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
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

@Scope("session")
@Controller(value = "inquiryMaterialItemListController")
@RequestMapping(value = "/inquiryMaterialItem")
public class InquiryMaterialItemListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.Inquiry;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected InquiryManager inquiryManager;

	@Autowired
	protected InquirySpecifier inquirySpecifier;

	@Autowired
	protected InquiryMaterialItemExcelHelper inquiryMaterialItemExcelHelper;

	protected List<InquiryMaterialItemServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(InquiryMaterialItemServiceUIModel.class,
				InquiryMaterialItemServiceModel.class, rawList,
				inquiryManager, InquiryMaterialItem.NODENAME,  inquirySpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, InquiryMaterialItemSearchModel.class, searchContext -> inquiryManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				null,
				InquiryMaterialItemSearchModel.class, searchContext -> inquiryManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				request,
				InquiryMaterialItemSearchModel.class, searchContext -> inquiryManager
						.getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
			ServiceEntityExceptionContainer {
		ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
				new ServiceBasicUtilityController.ExcelDownloadRequest(InquiryMaterialItemSearchModel.class,
						request,
						AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.InquiryMaterialItem,
						inquiryMaterialItemExcelHelper, searchContext -> inquiryManager.getSearchProxy().searchItemList(searchContext),
						null, InquiryMaterialItemUIModel.class, InquiryMaterialItem.NODENAME, inquirySpecifier);
		return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
	}

}
