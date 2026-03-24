package com.company.IntelligentPlatform.common.dto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.service.ServiceExcelUploadResponseView;
import com.company.IntelligentPlatform.common.service.ServiceExcelErrorLogReportProxy;

@Service
public class ServiceExcelReportContextProxy {

	/**
	 * This constant will also be set on client web page to transfer excel file
	 */
	public static final String REQUESTID_EXCEL = "excel";

	public static final String VIEW_NAME = "ExcelUploadDialog";

	public static final String ELEMENTID_MODELNAME = "x_excel_modelName";

	public static final String MODELNAME = "modelName";

	public static final String ID_EXCEL_REQUEST = "excelRequest";

	public static final String RESFILE_LOCATION = "ServiceExcelErrorLog_location";

	public static final String RESFILE_ERROR_CATEGORY = "ServiceExcelErrorLog_errorCategory";

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected ServiceExcelErrorLogReportProxy serviceExcelErrorLogReportProxy;

	public void addExcelUploadResource(ModelAndView mav) throws IOException {
		String warnEmptyFile = getPreWarnMsg("warnEmptyFile");
		mav.addObject("warnEmptyFile", warnEmptyFile);
		String warnSystemError = getPreWarnMsg("warnSystemError");
		mav.addObject("warnSystemError", warnSystemError);
	}
	
	public void addUploadDoneResource(ModelAndView mav) throws IOException {
		String warnUploadDone = getPreWarnMsg("warnUploadDone");
		mav.addObject("warnUploadDone", warnUploadDone);		
	}

	protected String getPreWarnMsg(String key) throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = ServiceExcelReportController.class.getResource("")
				.getPath();
		String resFileName = "ServiceExcelUpload";
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap.get(key);
	}

	public ModelAndView uploadExcelDialogCore(String modelName,
			String errorViewName) {
		try {
			ServiceExcelReportRequestModel request = new ServiceExcelReportRequestModel();
			request.setModelName(modelName);
			request.setModelLabel(modelName);
			ModelAndView mav = new ModelAndView();
			mav.addObject(ServiceExcelReportContextProxy.ID_EXCEL_REQUEST,
					request);
			mav.setViewName(ServiceExcelReportContextProxy.VIEW_NAME);
			// Set flag of process mode
			mav.addObject(SEEditorController.LABEL_UIFLAG,
					SEEditorController.UIFLAG_FRAME);
			addExcelUploadResource(mav);
			mav.addObject(ServiceExcelReportContextProxy.MODELNAME, modelName);
			return mav;
		} catch (IOException e) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName(errorViewName);
			mav.addObject(SEEditorController.MESSAGE_TOKEN, e.getMessage());
			mav.addObject(SEEditorController.LABEL_UIFLAG,
					SEEditorController.UIFLAG_FRAME);
			return mav;
		}
	}

	public void convToExcelUIModel(
			ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion,
			ServiceExcelReportErrorLogExcelModel serviceExcelReportErrorLogExcelModel,
			Map<Integer, String> locationMap, Map<Integer, String> categoryMap) {
		serviceExcelReportErrorLogExcelModel
				.setId(serviceExcelReportErrorLogUnion.getId());
		serviceExcelReportErrorLogExcelModel
				.setErrorMessage(serviceExcelReportErrorLogUnion
						.getErrorMessage());
		serviceExcelReportErrorLogExcelModel
				.setRowIndex(serviceExcelReportErrorLogUnion.getRowIndex());
		serviceExcelReportErrorLogExcelModel
				.setSheetName(serviceExcelReportErrorLogUnion.getSheetName());
		serviceExcelReportErrorLogExcelModel
				.setLocationType(serviceExcelReportErrorLogUnion
						.getLocationType());
		int locationType = serviceExcelReportErrorLogUnion.getLocationType();
		serviceExcelReportErrorLogExcelModel.setLocationValue(locationMap
				.get(locationType));
		int errorCategory = serviceExcelReportErrorLogUnion.getErrorCategory();
		serviceExcelReportErrorLogExcelModel.setErrorCategory(errorCategory);
		String itemStatus = categoryMap.get(errorCategory);
		serviceExcelReportErrorLogExcelModel.setItemStatus(itemStatus);

	}

	public ModelAndView genErrorLogExcelReportCore(
			List<ServiceExcelReportErrorLogUnion> errorLogList, String client)
			throws ServiceExcelConfigException, IllegalArgumentException,
			IllegalAccessException, DocumentException, IOException {
		// TODO-LEGACY: AbstractExcelView removed in Spring 5+; Excel report view no longer renderable via ModelAndView
		// Return empty ModelAndView to avoid compile errors until Excel view migration is complete
		return new ModelAndView();
	}

}
