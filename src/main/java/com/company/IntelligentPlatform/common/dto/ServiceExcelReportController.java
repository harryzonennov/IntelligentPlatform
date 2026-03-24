package com.company.IntelligentPlatform.common.dto;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.service.CorporateDealerExcelReportProxy;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;


/**
 * Central controller class to handle excel import and export
 * @author Zhang,hang
 *
 */
@Scope("session")
@Controller(value = "serviceExcelReportController")
@RequestMapping(value = "/serviceExcelReport")
public class ServiceExcelReportController extends SEEditorController {
	
	/**
	 * This constant will also be set on client web page to transfer excel file
	 */
	public static final String REQUESTID_EXCEL = "excel";
	
	public static final String VIEW_NAME = "ExcelUploadDialog";
	
	public static final String ELEMENTID_MODELNAME = "x_excel_modelName";
	
	public static final String MODELNAME = "modelName";
	
	public static final String ID_EXCEL_REQUEST = "excelRequest";
	
	@Autowired
	protected LogonActionController logonActionController;
	
	
	
	@Autowired
	protected CorporateDealerExcelReportProxy corporateDealerExcelReportProxy;
	
	
	
	/**
	 * Obsolete method
	 * @param configureName
	 * @return
	 */
	public ServiceExcelReportProxy getExcelReportProxy(String configureName){
		
		return null;
	}
	
	public void addExcelUploadResource(ModelAndView mav) throws IOException{
		String warnEmptyFile = getPreWarnMsg("warnEmptyFile");
		mav.addObject("warnEmptyFile", warnEmptyFile);
	}
	
	protected String getPreWarnMsg(String key) throws IOException {
		Locale locale = Locale.getDefault();
		String path = ServiceExcelReportController.class.getResource("").getPath();
		String resFileName = "ServiceExcelUpload";
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap.get(key);
	}
	
	


}
