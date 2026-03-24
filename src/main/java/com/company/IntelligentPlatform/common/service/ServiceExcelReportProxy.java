package com.company.IntelligentPlatform.common.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
// TODO-LEGACY: import platform.foundation.Administration.InstallService.Configure.IServiceExcelConfigureConstants;
import com.company.IntelligentPlatform.common.service.ServiceExcelCellConfig;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.dto.ServiceExcelReportErrorLogUnion;
import com.company.IntelligentPlatform.common.dto.ServiceExcelReportResponseModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.model.AttachmentConstantHelper;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceDocExcelDownloadTemplate;
import com.company.IntelligentPlatform.common.model.ServiceDocExcelUploadTemplate;

/**
 * Basic class for Excel report generation
 * 
 * @author Zhang,Hang
 * 
 */
@Service
public class ServiceExcelReportProxy {

	protected String currentSheetName;

	// Attribute: configureName, only make sense if there is only one configure
	// to this proxy
	protected String configureName;

	protected String documentResourceId;

	public static final String LABEL_EXCELNAME = "excelName";

	public static final String LABEL_EXCELPATH = "excelPath";

	public static final String LABEL_EXCELCONTENT = "content";

	public static final String LABEL_EXCELREP_CONFIG = "excelReportConfigure";

	public static final String LABEL_EXCELREP_CONFIGLIST = "excelReportConfigList";

	public static final String LABEL_EXCELFIELD_LIST = "fieldList";

	public static final String LABEL_DOWNEXCEL_TITLE = "downloadExcel";

	public static final int DATA_START_ROWINDEX = 1;

	protected Class<?> excelModelClass;

	/**
	 * Attribute to identify [id] column, default value is 1
	 */
	protected int idColIndex = 0;

	public static final SimpleDateFormat DEF_MIN_FORMAT = new SimpleDateFormat(
			"yyyy_MM_dd_HHmm");

	@Autowired
	ServiceExcelXMLConfigureHelper serviceExcelXMLConfigureHelper;

	@Autowired
	ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	ServiceDocumentSettingManager serviceDocumentSettingManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	public String getCurrentSheetName() {
		return currentSheetName;
	}

	public void setCurrentSheetName(String currentSheetName) {
		this.currentSheetName = currentSheetName;
	}

	/**
	 * Generate the Service Excel Report report configure instance by excel
	 * configure file core name Pay attention: the XML-format configure name
	 * should be place at central configure folder:the same folder as interface
	 * [IServiceExcelConfigureConstants]
	 * 
	 * @param configureName
	 *            :XML-format excel report configure file core name
	 * @return
	 * @throws ServiceExcelConfigException
	 * @throws DocumentException
	 */
	public ServiceExcelReportConfig getUploadExcelReportConfigure(
			String configureName, String client, String languageCode)
			throws ServiceExcelConfigException, DocumentException {
		Document document = null;
		try {
			document = getCustomUploadExcelReportConfigure(configureName,
					client);
		} catch (ServiceEntityConfigureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (document == null) {
			String configURL = serviceExcelXMLConfigureHelper
					.getFullFileAbsURL(configureName, ServiceLanHelper.getDefault());
			document = serviceExcelXMLConfigureHelper
					.readExcelConfigure(configURL);
		}
		List<ServiceExcelCellConfig> cellConfigs = serviceExcelXMLConfigureHelper
				.getAllCellConfigList(document);
		ServiceExcelReportConfig serviceExcelReportConfig = serviceExcelXMLConfigureHelper
				.getExcelReportConfigure(document);
		serviceExcelReportConfig.setServiceExcelCellConfigs(cellConfigs);
		return serviceExcelReportConfig;
	}
//
//	@Deprecated
//	public ServiceExcelReportConfig getExcelReportConfigByResId(
//			String resourceId, String client)
//			throws ServiceExcelConfigException, DocumentException,
//			ServiceEntityConfigureException {
//		Document document = serviceExcelXMLConfigureHelper
//				.readExcelConfigureByResId(resourceId, client);
//		List<ServiceExcelCellConfig> cellConfigs = serviceExcelXMLConfigureHelper
//				.getAllCellConfigList(document);
//		ServiceExcelReportConfig serviceExcelReportConfig = serviceExcelXMLConfigureHelper
//				.getExcelReportConfigure(document);
//		serviceExcelReportConfig.setServiceExcelCellConfigs(cellConfigs);
//		return serviceExcelReportConfig;
//	}

	/**
	 * Generate the Service Excel Report report configure instance by excel
	 * configure file core name Pay attention: the XML-format configure name
	 * should be place at central configure folder:the same folder as interface
	 * [IServiceExcelConfigureConstants]
	 * 
	 * @param configureName
	 *            :XML-format excel report configure file core name
	 * @return
	 * @throws ServiceExcelConfigException
	 * @throws DocumentException
	 */
	public ServiceExcelReportConfig getDownloadExcelReportConfigure(
			String configureName, String client)
			throws ServiceExcelConfigException, DocumentException {
		Document document = null;
		try {
			document = getCustomDownloadExcelReportConfigure(configureName,
					client);
		} catch (ServiceEntityConfigureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (document == null) {
			String configURL = serviceExcelXMLConfigureHelper
					.getFullFileAbsURL(configureName, ServiceLanHelper.getDefault());
			document = serviceExcelXMLConfigureHelper
					.readExcelConfigure(configURL);
		}
		List<ServiceExcelCellConfig> cellConfigs = serviceExcelXMLConfigureHelper
				.getAllCellConfigList(document);
		ServiceExcelReportConfig serviceExcelReportConfig = serviceExcelXMLConfigureHelper
				.getExcelReportConfigure(document);
		serviceExcelReportConfig.setServiceExcelCellConfigs(cellConfigs);
		return serviceExcelReportConfig;
	}

	private Document getCustomDownloadExcelReportConfigure(
			String configureName, String client)
			throws ServiceExcelConfigException, DocumentException,
			ServiceEntityConfigureException {
		ServiceDocExcelDownloadTemplate serviceDocExcelDownloadTemplate = serviceDocumentSettingManager
				.getDownloadExcelTemplate(configureName,
						AttachmentConstantHelper.TYPE_XML, client);
		if (serviceDocExcelDownloadTemplate == null) {
			return null;
		}
		InputStream in = new ByteArrayInputStream(
				serviceDocExcelDownloadTemplate.getContent());
		SAXReader reader = new SAXReader();
		Document document = reader.read(in);
		return document;
	}

	private Document getCustomUploadExcelReportConfigure(String configureName,
			String client) throws ServiceExcelConfigException,
			DocumentException, ServiceEntityConfigureException {
		SAXReader reader = new SAXReader();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		ServiceDocExcelUploadTemplate serviceDocExcelUploadTemplate = serviceDocumentSettingManager
				.getUploadExcelTemplate(configureName,
						AttachmentConstantHelper.TYPE_XML, client);
		if(serviceDocExcelUploadTemplate == null){
			return null;
		}
		InputStream in = new ByteArrayInputStream(
				serviceDocExcelUploadTemplate.getContent());
		Document document = reader.read(in);
		return document;
	}

	/**
	 * Logic to Get the id column index from service exel configure list
	 * 
	 * @return
	 */
	public int getIdColIndex(List<ServiceExcelCellConfig> cellConfigList) {
		if (cellConfigList != null && cellConfigList.size() > 0) {
			for (ServiceExcelCellConfig cellConfig : cellConfigList) {
				if (cellConfig.isIdFlag()) {
					return cellConfig.getColIndex();
				}
			}
		}
		/**
		 * Or else, return the default value
		 */
		return this.idColIndex;
	}

	public void addExcelDefResource(ModelAndView mav)
			throws ServiceEntityInstallationException {
		mav.addObject(LABEL_DOWNEXCEL_TITLE, getDefaultDownloadExcelTitle());
	}

	protected String getDefaultDownloadExcelTitle()
			throws ServiceEntityInstallationException {
		try {
			Locale locale = ServiceLanHelper.getDefault();
			String path = SEEditorController.class.getResource("").getPath();
			String resFileName = "ComElements";
			Map<String, String> paymentTypeMap = serviceDropdownListHelper
					.getDropDownMap(path, resFileName, locale);
			return paymentTypeMap.get(LABEL_DOWNEXCEL_TITLE);
		} catch (IOException e) {
			throw new ServiceEntityInstallationException(
					ServiceEntityInstallationException.PARA_SYSTEM_WRONG, e.getMessage());
		}
	}

	/**
	 * Public API to build the excel configure map:For the case only one
	 * configure is needed
	 * 
	 * @param excelFilePath
	 *            :external input file path, if null will use report
	 *            configuration
	 * @param excelFileName
	 *            :external input file name, if null will use report
	 *            configuration
	 * 
	 * @param content
	 * @param configureName
	 *            ::XML-format excel report configure file core name should be
	 *            placed in central installation excel configure folder
	 * @return
	 * @throws ServiceExcelConfigException
	 * @throws DocumentException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Map<String, Object> genExcelConfigure(String excelFilePath,
			String excelFileName, List<SEUIComModel> content,
			String configureName, String client)
			throws ServiceExcelConfigException, DocumentException,
			IllegalArgumentException, IllegalAccessException {
		Map<String, Object> excelConfigureMap = new HashMap<String, Object>();
		List<ServiceExcelReportConfig> serviceExcelReportConfigList = new ArrayList<ServiceExcelReportConfig>();
		ServiceExcelReportConfig serviceExcelReportConfig = buildRunTimeReportConfigure(
				excelFilePath, excelFileName, excelModelClass, content,
				configureName, client);
		serviceExcelReportConfigList.add(serviceExcelReportConfig);
		excelConfigureMap.put(
				ServiceExcelReportProxy.LABEL_EXCELREP_CONFIGLIST,
				serviceExcelReportConfigList);
		return excelConfigureMap;
	}

	/**
	 * Public API to build the excel configure map:For the case only one
	 * configure is needed
	 * 
	 * @param excelFilePath
	 *            :external input file path, if null will use report
	 *            configuration
	 * @param excelFileName
	 *            :external input file name, if null will use report
	 *            configuration
	 * @param content
	 * @return
	 * @throws ServiceExcelConfigException
	 * @throws DocumentException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Map<String, Object> genExcelConfigure(String excelFilePath,
			String excelFileName, List<?> content, String client)
			throws ServiceExcelConfigException, DocumentException,
			IllegalArgumentException, IllegalAccessException {
		Map<String, Object> excelConfigureMap = new HashMap<>();
		List<ServiceExcelReportConfig> serviceExcelReportConfigList = new ArrayList<>();
		ServiceExcelReportConfig serviceExcelReportConfig = buildRunTimeReportConfigure(
				excelFilePath, excelFileName, excelModelClass, content,
				configureName, client);
		serviceExcelReportConfigList.add(serviceExcelReportConfig);
		excelConfigureMap.put(
				ServiceExcelReportProxy.LABEL_EXCELREP_CONFIGLIST,
				serviceExcelReportConfigList);
		return excelConfigureMap;
	}

	/**
	 * Logic of retrieve the configuration from configure file by core configure
	 * name, afterwards set the run time information into the configure instance
	 * 
	 * @param excelFilePath
	 * @param excelFileName
	 * @param content
	 * @param configureName
	 *            :XML-format excel report configure file core name
	 * @return
	 * @throws ServiceExcelConfigException
	 * @throws DocumentException
	 */
	public ServiceExcelReportConfig buildRunTimeReportConfigure(
			String excelFilePath, String excelFileName,
			Class<?> excelModelClass, List<?> content,
			String configureName, String client)
			throws ServiceExcelConfigException, DocumentException {
		ServiceExcelReportConfig serviceExcelReportConfig = getDownloadExcelReportConfigure(
				configureName, client);
		List<Field> fieldList = ServiceEntityFieldsHelper.getServiceSelfDefinedFieldsList(excelModelClass, SEUIComModel.class);
//		List<Field> fieldList = ServiceEntityFieldsHelper
//				.getSelfDefinedFieldsList(excelModelClass);
		serviceExcelReportConfig.setFieldList(fieldList);
		String locFilePath = excelFilePath;
		String locFileName = excelFileName;
		if (locFilePath == null
				|| locFilePath.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			locFilePath = serviceExcelReportConfig.getFilePath();
		}
		if (locFileName == null
				|| locFileName.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			locFileName = serviceExcelReportConfig.getReportName();
		}
		if (serviceExcelReportConfig.getTimeStampFlag() == ServiceExcelReportConfig.TIMESTAMP_FLAG_NEED) {
			String dataString = DEF_MIN_FORMAT.format(new Date());
			locFileName = locFileName + "_" + dataString;
		}
		serviceExcelReportConfig.setFilePath(locFilePath);
		serviceExcelReportConfig.setReportName(locFileName);
		serviceExcelReportConfig.setExcelContent(content);
		return serviceExcelReportConfig;
	}

	/**
	 * Public API to build the excel configure map:For the case multiple
	 * configures is needed
	 * 
	 * @param serviceExcelReportConfigList
	 * @return
	 * @throws ServiceExcelConfigException
	 * @throws DocumentException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Map<String, Object> genExcelConfigure(
			List<ServiceExcelReportConfig> serviceExcelReportConfigList)
			throws ServiceExcelConfigException, DocumentException,
			IllegalArgumentException, IllegalAccessException {
		Map<String, Object> excelConfigureMap = new HashMap<String, Object>();
		excelConfigureMap.put(LABEL_EXCELREP_CONFIGLIST,
				serviceExcelReportConfigList);
		return excelConfigureMap;
	}

	public static void insertBlankRow(HSSFSheet curSheet, int rowIndex) {
		curSheet.createRow(rowIndex);
	}

	public static void insertBlankRow(XSSFSheet curSheet, int rowIndex) {
		curSheet.createRow(rowIndex);
	}

	/**
	 * 
	 * @param serviceExcelReportConfigList
	 * @param workbook
	 * @throws Exception
	 */
	public void buildExcelDocument(
			List<ServiceExcelReportConfig> serviceExcelReportConfigList,
			HSSFWorkbook workbook) throws Exception {
		int rowIndex = 0;
		if (serviceExcelReportConfigList != null
				&& serviceExcelReportConfigList.size() > 0) {
			for (ServiceExcelReportConfig serviceExcelReportConfig : serviceExcelReportConfigList) {
				List<ServiceExcelCellConfig> cellConfigList = serviceExcelReportConfig
						.getServiceExcelCellConfigs();
				List<Field> fieldList = serviceExcelReportConfig.getFieldList();
				HSSFSheet curSheet = workbook
						.getSheet(IServiceExcelConfigureConstants.DEF_EXCEL_SHEETNAME);
				if (curSheet == null) {
					curSheet = workbook
							.createSheet(IServiceExcelConfigureConstants.DEF_EXCEL_SHEETNAME);
				}
				ServiceExcelReportProxy.insertTitle(workbook, curSheet,
						cellConfigList, rowIndex);
				rowIndex++;
				List<SEUIComModel> content = (List<SEUIComModel>) serviceExcelReportConfig
						.getExcelContent();
				for (SEUIComModel seData : content) {
					ServiceExcelReportProxy.insertRow(workbook, curSheet,
							seData, fieldList, cellConfigList, rowIndex);
					rowIndex++;
				}
				ServiceExcelReportProxy.insertBlankRow(curSheet, rowIndex);
				rowIndex++;
			}
		}
	}

	/**
	 * 
	 * @param serviceExcelReportConfigList
	 * @param workbook
	 * @throws Exception
	 */
	public void buildExcelDocument(
			List<ServiceExcelReportConfig> serviceExcelReportConfigList,
			XSSFWorkbook workbook) throws Exception {
		int rowIndex = 0;
		if (serviceExcelReportConfigList != null
				&& serviceExcelReportConfigList.size() > 0) {
			for (ServiceExcelReportConfig serviceExcelReportConfig : serviceExcelReportConfigList) {
				List<ServiceExcelCellConfig> cellConfigList = serviceExcelReportConfig
						.getServiceExcelCellConfigs();
				List<Field> fieldList = serviceExcelReportConfig.getFieldList();
				XSSFSheet curSheet = workbook
						.getSheet(IServiceExcelConfigureConstants.DEF_EXCEL_SHEETNAME);
				if (curSheet == null) {
					curSheet = workbook
							.createSheet(IServiceExcelConfigureConstants.DEF_EXCEL_SHEETNAME);
				}
				ServiceExcelReportProxy.insertTitle(workbook, curSheet,
						cellConfigList, rowIndex);
				rowIndex++;
				List<SEUIComModel> content = (List<SEUIComModel>) serviceExcelReportConfig
						.getExcelContent();
				for (SEUIComModel seData : content) {
					ServiceExcelReportProxy.insertRow(workbook, curSheet,
							seData, fieldList, cellConfigList, rowIndex);
					rowIndex++;
				}
				ServiceExcelReportProxy.insertBlankRow(curSheet, rowIndex);
				rowIndex++;
			}
		}
	}

	public static void insertTitle(HSSFWorkbook workbook, HSSFSheet curSheet,
			List<ServiceExcelCellConfig> configList, int rowIndex) {
		HSSFRow row = curSheet.createRow(rowIndex);
		for (ServiceExcelCellConfig cellConfig : configList) {
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			// Set color value
			if (cellConfig.getColor() == ServiceExcelCellConfig.COLOR_RED) {
				font.setColor(HSSFFont.COLOR_RED);
			} else {
				font.setColor(HSSFFont.COLOR_NORMAL);
			}
			font.setBold(true);
			int columIndex = cellConfig.getColIndex();
			// Set weight
			if (cellConfig.getFieldWeight() > 0) {
				curSheet.setColumnWidth(columIndex, cellConfig.getFieldWeight()
						* ServiceExcelCellConfig.SIZE_FACTOR);
			}
			cellStyle.setWrapText(false);
			cellStyle.setFont(font);
			cellStyle.setFillForegroundColor(org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
			cellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
			HSSFCell cell = row.createCell(columIndex);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(cellConfig.getFieldLabel());
		}
	}

	public static void insertTitle(XSSFWorkbook workbook, XSSFSheet curSheet,
			List<ServiceExcelCellConfig> configList, int rowIndex) {
		XSSFRow row = curSheet.createRow(rowIndex);
		for (ServiceExcelCellConfig cellConfig : configList) {
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			// Set color value
			if (cellConfig.getColor() == ServiceExcelCellConfig.COLOR_RED) {
				font.setColor(HSSFFont.COLOR_RED);
			} else {
				font.setColor(HSSFFont.COLOR_NORMAL);
			}
			font.setBold(true);
			int columIndex = cellConfig.getColIndex();
			// Set weight
			if (cellConfig.getFieldWeight() > 0) {
				curSheet.setColumnWidth(columIndex, cellConfig.getFieldWeight()
						* ServiceExcelCellConfig.SIZE_FACTOR);
			}
			cellStyle.setWrapText(false);
			cellStyle.setFont(font);
			cellStyle.setFillForegroundColor(org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
			cellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
			XSSFCell cell = row.createCell(columIndex);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(cellConfig.getFieldLabel());
		}
	}

	public static void insertTitle(HSSFWorkbook workbook, HSSFSheet curSheet,
			List<ServiceExcelCellConfig> configList) {
		insertTitle(workbook, curSheet, configList, 0);
	}

	public static void insertTitle(XSSFWorkbook workbook, XSSFSheet curSheet,
			List<ServiceExcelCellConfig> configList) {
		insertTitle(workbook, curSheet, configList, 0);
	}

	/**
	 * Core Logic to get really HSSFontColor by input ServiceExcelCellConfig
	 * color setting
	 * 
	 * @param colorValue
	 * @return
	 */
	public static short convertHSSFontColor(int colorValue) {
		if (colorValue == ServiceExcelCellConfig.COLOR_RED) {
			return HSSFFont.COLOR_RED;
		}
		if (colorValue == ServiceExcelCellConfig.COLOR_NORMAL) {
			return HSSFFont.COLOR_NORMAL;
		}
		return (short) colorValue;
	}

	public static void insertRow(XSSFWorkbook workbook, XSSFSheet curSheet,
			SEUIComModel seData, List<Field> fieldList,
			List<ServiceExcelCellConfig> configList, int rowIndex)
			throws IllegalArgumentException, IllegalAccessException,
			ServiceExcelConfigException {
		XSSFRow row = curSheet.createRow(rowIndex);
		if (ServiceCollectionsHelper.checkNullList(configList)) {
			// should raise exception
			throw new ServiceExcelConfigException(
					ServiceExcelConfigException.PARA_NO_CONFIGURE, "");
		}
		for (ServiceExcelCellConfig serviceExcelCellConfig : configList) {
			Field field = ServiceEntityFieldsHelper.filterFieldByNameOnline(
					fieldList, serviceExcelCellConfig.getFieldName());
			if (field == null) {
				continue;
			}
			// Set cell style
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			// Set color value
			short color = convertHSSFontColor(serviceExcelCellConfig.getColor());
			font.setColor(color);
			int columIndex = serviceExcelCellConfig.getColIndex();
			// Set weight
			if (serviceExcelCellConfig.getFieldWeight() > 0) {
				curSheet.setColumnWidth(columIndex,
						serviceExcelCellConfig.getFieldWeight()
								* ServiceExcelCellConfig.SIZE_FACTOR);
			}
			cellStyle.setWrapText(false);
			cellStyle.setFont(font);
			cellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
			field.setAccessible(true);
			XSSFCell cell = row.createCell(columIndex);
			cell.setCellStyle(cellStyle);
			Object fieldValue = field.get(seData);
			if (fieldValue != null) {
				cell.setCellValue(fieldValue.toString());
			}
		}
	}

	public static void insertRow(HSSFWorkbook workbook, HSSFSheet curSheet,
			SEUIComModel seData, List<Field> fieldList,
			List<ServiceExcelCellConfig> configList, int rowIndex)
			throws IllegalArgumentException, IllegalAccessException,
			ServiceExcelConfigException {
		HSSFRow row = curSheet.createRow(rowIndex);
		if (ServiceCollectionsHelper.checkNullList(configList)) {
			// should raise exception
			throw new ServiceExcelConfigException(
					ServiceExcelConfigException.PARA_NO_CONFIGURE, "");
		}
		for (ServiceExcelCellConfig serviceExcelCellConfig : configList) {
			Field field = ServiceEntityFieldsHelper.filterFieldByNameOnline(
					fieldList, serviceExcelCellConfig.getFieldName());
			if (field == null) {
				continue;
			}
			// Set cell style
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			// Set color value
			short color = convertHSSFontColor(serviceExcelCellConfig.getColor());
			font.setColor(color);
			int columIndex = serviceExcelCellConfig.getColIndex();
			// Set weight
			if (serviceExcelCellConfig.getFieldWeight() > 0) {
				curSheet.setColumnWidth(columIndex,
						serviceExcelCellConfig.getFieldWeight()
								* ServiceExcelCellConfig.SIZE_FACTOR);
			}
			cellStyle.setWrapText(false);
			cellStyle.setFont(font);
			cellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
			field.setAccessible(true);
			HSSFCell cell = row.createCell(columIndex);
			cell.setCellStyle(cellStyle);
			Object fieldValue = field.get(seData);
			if (fieldValue != null) {
				cell.setCellValue(fieldValue.toString());
			} else {
				cell.setCellValue(ServiceEntityStringHelper.EMPTYSTRING);
			}
		}
	}

	/**
	 * This method should implemented in each child class
	 * 
	 * @param serviceExcelReportResponseModel
	 * @throws ServiceExcelConfigException
	 */
	public void insertExcelBatchData(
			ServiceExcelReportResponseModel serviceExcelReportResponseModel,
			String modelName) throws ServiceExcelConfigException,
			AuthorizationException, LogonInfoException {
		throw new ServiceExcelConfigException(
				ServiceExcelConfigException.PARA_NO_CONFIGURE);
	}

	public ServiceExcelReportResponseModel readDataArrayFromSheet(
			XSSFWorkbook xssfWorkbook, String client, String languageCode)
			throws ServiceExcelConfigException, DocumentException,
			ServiceEntityConfigureException {
		if (ServiceEntityStringHelper.checkNullString(this.documentResourceId)) {
			// In case documentResourceId is null, then switch to system default
			// template
			ServiceExcelReportConfig serviceExcelReportConfig = getUploadExcelReportConfigure(
					configureName, client, languageCode);
			if (serviceExcelReportConfig == null) {
				throw new ServiceExcelConfigException(
						ServiceExcelConfigException.PARA_NO_CONFIGURE,
						configureName);
			}
			return this.parseIntoExcelModelCore(xssfWorkbook,
					serviceExcelReportConfig);
		}
		ServiceExcelReportConfig serviceExcelReportConfig = getUploadExcelReportConfigure(
				this.documentResourceId, client, languageCode);
		if (serviceExcelReportConfig == null) {
			// In case custom template is null, then switch to system default
			// template
			serviceExcelReportConfig = getUploadExcelReportConfigure(
					configureName, client, languageCode);
			if (serviceExcelReportConfig == null) {
				throw new ServiceExcelConfigException(
						ServiceExcelConfigException.PARA_NO_CONFIGURE,
						configureName);
			}
		}
		return this.parseIntoExcelModelCore(xssfWorkbook,
				serviceExcelReportConfig);
	}

	public ServiceExcelReportResponseModel readDataArrayFromSheet(
			HSSFWorkbook hssfWorkbook, String client, String languageCode)
			throws ServiceExcelConfigException, DocumentException,
			ServiceEntityConfigureException {
		if (ServiceEntityStringHelper.checkNullString(this.documentResourceId)) {
			// In case documentResourceId is null, then switch to system default
			// template
			ServiceExcelReportConfig serviceExcelReportConfig = getUploadExcelReportConfigure(
					configureName, client, languageCode);
			if (serviceExcelReportConfig == null) {
				throw new ServiceExcelConfigException(
						ServiceExcelConfigException.PARA_NO_CONFIGURE,
						configureName);
			}
			return this.parseIntoExcelModelCore(hssfWorkbook,
					serviceExcelReportConfig);
		}
		ServiceExcelReportConfig serviceExcelReportConfig = getUploadExcelReportConfigure(
				this.documentResourceId, client, languageCode);
		if (serviceExcelReportConfig == null) {
			// In case custom template is null, then switch to system default
			// template
			serviceExcelReportConfig = getUploadExcelReportConfigure(
					configureName, client, languageCode);
			if (serviceExcelReportConfig == null) {
				throw new ServiceExcelConfigException(
						ServiceExcelConfigException.PARA_NO_CONFIGURE,
						configureName);
			}
		}
		return this.parseIntoExcelModelCore(hssfWorkbook,
				serviceExcelReportConfig);
	}

	/**
	 * Core Logic to generate ServiceExcelReportConfig instance by HSSFWorkbook
	 * 
	 * @param hssfWorkbook
	 * @param serviceExcelReportConfig
	 * @return
	 */
	protected ServiceExcelReportResponseModel parseIntoExcelModelCore(
			HSSFWorkbook hssfWorkbook,
			ServiceExcelReportConfig serviceExcelReportConfig) {
		ServiceExcelReportResponseModel serviceExcelReportResponseModel = new ServiceExcelReportResponseModel();
		List<SEUIComModel> seDataList = new ArrayList<SEUIComModel>();
		List<ServiceExcelReportErrorLogUnion> errorLogList = new ArrayList<ServiceExcelReportErrorLogUnion>();
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(excelModelClass);
		int locIDColIndex = getIdColIndex(serviceExcelReportConfig
				.getServiceExcelCellConfigs());
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			ServiceExcelReportResponseModel tmpExcelReportResponseModel = readDataArrayFromSheet(
					hssfSheet, fieldList,
					serviceExcelReportConfig.getServiceExcelCellConfigs(),
					locIDColIndex);
			if (tmpExcelReportResponseModel.getDataList() != null
					&& tmpExcelReportResponseModel.getDataList().size() > 0) {
				seDataList.addAll(tmpExcelReportResponseModel.getDataList());
			}
			if (tmpExcelReportResponseModel.getErrorLogList() != null
					&& tmpExcelReportResponseModel.getErrorLogList().size() > 0) {
				errorLogList.addAll(tmpExcelReportResponseModel
						.getErrorLogList());
			}
			if (!tmpExcelReportResponseModel.getDataMap().isEmpty()) {
				// TODO: currently only support one sheet dataMap
				serviceExcelReportResponseModel
						.setDataMap(tmpExcelReportResponseModel.getDataMap());
			}
		}
		serviceExcelReportResponseModel.setDataList(seDataList);
		serviceExcelReportResponseModel.setErrorLogList(errorLogList);
		return serviceExcelReportResponseModel;
	}

	/**
	 * Core Logic to generate ServiceExcelReportConfig instance by XSSFWorkbook
	 * 
	 * @param xssfWorkbook
	 * @param serviceExcelReportConfig
	 * @return
	 */
	protected ServiceExcelReportResponseModel parseIntoExcelModelCore(
			XSSFWorkbook xssfWorkbook,
			ServiceExcelReportConfig serviceExcelReportConfig) {
		ServiceExcelReportResponseModel serviceExcelReportResponseModel = new ServiceExcelReportResponseModel();
		List<SEUIComModel> seDataList = new ArrayList<SEUIComModel>();
		List<ServiceExcelReportErrorLogUnion> errorLogList = new ArrayList<ServiceExcelReportErrorLogUnion>();
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(excelModelClass);
		int locIDColIndex = getIdColIndex(serviceExcelReportConfig
				.getServiceExcelCellConfigs());
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			ServiceExcelReportResponseModel tmpExcelReportResponseModel = readDataArrayFromSheet(
					xssfSheet, fieldList,
					serviceExcelReportConfig.getServiceExcelCellConfigs(),
					locIDColIndex);
			if (tmpExcelReportResponseModel.getDataList() != null
					&& tmpExcelReportResponseModel.getDataList().size() > 0) {
				seDataList.addAll(tmpExcelReportResponseModel.getDataList());
			}
			if (tmpExcelReportResponseModel.getErrorLogList() != null
					&& tmpExcelReportResponseModel.getErrorLogList().size() > 0) {
				errorLogList.addAll(tmpExcelReportResponseModel
						.getErrorLogList());
			}
			if (!tmpExcelReportResponseModel.getDataMap().isEmpty()) {
				serviceExcelReportResponseModel
						.setDataMap(tmpExcelReportResponseModel.getDataMap());
			}
		}
		serviceExcelReportResponseModel.setDataList(seDataList);
		serviceExcelReportResponseModel.setErrorLogList(errorLogList);
		return serviceExcelReportResponseModel;
	}

	public ServiceExcelReportResponseModel readDataArrayFromSheet(
			XSSFSheet xssfSheet, List<Field> fieldList,
			List<ServiceExcelCellConfig> configList, int idColIndex) {
		ServiceExcelReportResponseModel serviceExcelReportResponseModel = new ServiceExcelReportResponseModel();
		List<SEUIComModel> seDataList = new ArrayList<SEUIComModel>();
		List<ServiceExcelReportErrorLogUnion> errorLogList = new ArrayList<ServiceExcelReportErrorLogUnion>();
		Map<Integer, SEUIComModel> dataMap = serviceExcelReportResponseModel
				.getDataMap();
		for (int rowIndex = DATA_START_ROWINDEX; rowIndex <= xssfSheet
				.getLastRowNum(); rowIndex++) {
			try {
				SEUIComModel seData = (SEUIComModel) this.excelModelClass
						.newInstance();
				readRow(xssfSheet, seData, fieldList, configList, rowIndex);
				if (seData != null) {
					seDataList.add(seData);
				}
				dataMap.put(rowIndex, seData);
			} catch (IllegalArgumentException e) {
				ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion = recordExcelReadErrorLog(
						xssfSheet, rowIndex, e.getMessage(), idColIndex);
				errorLogList.add(serviceExcelReportErrorLogUnion);
			} catch (IllegalAccessException e) {
				ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion = recordExcelReadErrorLog(
						xssfSheet, rowIndex, e.getMessage(), idColIndex);
				errorLogList.add(serviceExcelReportErrorLogUnion);
			} catch (ParseException e) {
				ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion = recordExcelReadErrorLog(
						xssfSheet, rowIndex, e.getMessage(), idColIndex);
				errorLogList.add(serviceExcelReportErrorLogUnion);
			} catch (InstantiationException e) {
				ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion = recordExcelReadErrorLog(
						xssfSheet, rowIndex, e.getMessage(), idColIndex);
				errorLogList.add(serviceExcelReportErrorLogUnion);
			}
		}
		serviceExcelReportResponseModel.setErrorLogList(errorLogList);
		serviceExcelReportResponseModel.setDataList(seDataList);
		return serviceExcelReportResponseModel;
	}

	public ServiceExcelReportResponseModel readDataArrayFromSheet(
			HSSFSheet hssfSheet, List<Field> fieldList,
			List<ServiceExcelCellConfig> configList, int idColIndex) {
		ServiceExcelReportResponseModel serviceExcelReportResponseModel = new ServiceExcelReportResponseModel();
		List<SEUIComModel> seDataList = new ArrayList<SEUIComModel>();
		Map<Integer, SEUIComModel> seDataMap = serviceExcelReportResponseModel
				.getDataMap();
		List<ServiceExcelReportErrorLogUnion> errorLogList = new ArrayList<ServiceExcelReportErrorLogUnion>();
		for (int rowIndex = DATA_START_ROWINDEX; rowIndex <= hssfSheet
				.getLastRowNum(); rowIndex++) {
			try {
				SEUIComModel seData = (SEUIComModel) this.excelModelClass
						.newInstance();
				readRow(hssfSheet, seData, fieldList, configList, rowIndex);
				if (seData != null) {
					seDataList.add(seData);
				}
				seDataMap.put(rowIndex, seData);
			} catch (IllegalArgumentException e) {
				ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion = recordExcelReadErrorLog(
						hssfSheet, rowIndex, e.getMessage(), idColIndex);
				errorLogList.add(serviceExcelReportErrorLogUnion);
			} catch (IllegalAccessException e) {
				ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion = recordExcelReadErrorLog(
						hssfSheet, rowIndex, e.getMessage(), idColIndex);
				errorLogList.add(serviceExcelReportErrorLogUnion);
			} catch (ParseException e) {
				ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion = recordExcelReadErrorLog(
						hssfSheet, rowIndex, e.getMessage(), idColIndex);
				errorLogList.add(serviceExcelReportErrorLogUnion);
			} catch (InstantiationException e) {
				ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion = recordExcelReadErrorLog(
						hssfSheet, rowIndex, e.getMessage(), idColIndex);
				errorLogList.add(serviceExcelReportErrorLogUnion);
			}
		}
		serviceExcelReportResponseModel.setErrorLogList(errorLogList);
		serviceExcelReportResponseModel.setDataList(seDataList);
		return serviceExcelReportResponseModel;
	}

	public static ServiceExcelReportErrorLogUnion recordExcelReadErrorLog(
			HSSFSheet hssfSheet, int rowIndex, String errorMessage,
			int idColIndex) {
		ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion = new ServiceExcelReportErrorLogUnion();
		serviceExcelReportErrorLogUnion.setErrorMessage(errorMessage);
		serviceExcelReportErrorLogUnion.setSheetName(hssfSheet.getSheetName());
		serviceExcelReportErrorLogUnion.setRowIndex(rowIndex + "");
		serviceExcelReportErrorLogUnion
				.setLocationType(ServiceExcelReportErrorLogUnion.LOCATION_READEXCEL);
		serviceExcelReportErrorLogUnion
				.setErrorCategory(ServiceExcelReportErrorLogUnion.CATEGORY_ERROR);
		String id = readIDFromExcel(hssfSheet, rowIndex, idColIndex);
		serviceExcelReportErrorLogUnion.setId(id);
		return serviceExcelReportErrorLogUnion;
	}

	public static ServiceExcelReportErrorLogUnion recordExcelReadErrorLog(
			XSSFSheet xssfSheet, int rowIndex, String errorMessage,
			int idColIndex) {
		ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion = new ServiceExcelReportErrorLogUnion();
		serviceExcelReportErrorLogUnion.setErrorMessage(errorMessage);
		serviceExcelReportErrorLogUnion.setSheetName(xssfSheet.getSheetName());
		serviceExcelReportErrorLogUnion.setRowIndex(rowIndex + "");
		serviceExcelReportErrorLogUnion
				.setLocationType(ServiceExcelReportErrorLogUnion.LOCATION_READEXCEL);
		serviceExcelReportErrorLogUnion
				.setErrorCategory(ServiceExcelReportErrorLogUnion.CATEGORY_ERROR);
		String id = readIDFromExcel(xssfSheet, rowIndex, idColIndex);
		serviceExcelReportErrorLogUnion.setId(id);
		return serviceExcelReportErrorLogUnion;
	}

	/**
	 * Reflective read id value from excel
	 * 
	 * @return
	 */
	public static String readIDFromExcel(HSSFSheet curSheet, int rowIndex,
			int idColIndex) {
		HSSFRow hssfRow = curSheet.getRow(rowIndex);
		HSSFCell hssfCell = hssfRow.getCell(idColIndex);
		return hssfCell.getStringCellValue();
	}

	/**
	 * Reflective read id value from excel
	 * 
	 * @return
	 */
	public static String readIDFromExcel(XSSFSheet curSheet, int rowIndex,
			int idColIndex) {
		XSSFRow xssfRow = curSheet.getRow(rowIndex);
		XSSFCell xssfCell = xssfRow.getCell(idColIndex);
		return xssfCell.getStringCellValue();
	}

	/**
	 * Read excel row into UIModel instance
	 *
	 * @param curSheet
	 * @param fieldList
	 * @param configList
	 * @param rowIndex
	 * @return
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws NumberFormatException
	 */
	public void readRow(XSSFSheet curSheet, SEUIComModel seData,
			List<Field> fieldList, List<ServiceExcelCellConfig> configList,
			int rowIndex) throws IllegalArgumentException,
			IllegalAccessException, ParseException {
		XSSFRow xssfRow = curSheet.getRow(rowIndex);
		if (xssfRow == null) {
			return;
		}
		if (ServiceCollectionsHelper.checkNullList(configList)) {
			return;
		}
		for (ServiceExcelCellConfig cellConfig : configList) {
			Field field = ServiceEntityFieldsHelper.filterFieldByNameOnline(
					fieldList, cellConfig.getFieldName());
			if (field == null) {
				continue;
			}
			XSSFCell cell = xssfRow.getCell(cellConfig.getColIndex());
			if (cell != null) {
				try {
					cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
					ServiceReflectiveHelper.reflectSetValue(field, seData,
							getValue(cell), null);
				} catch (NumberFormatException e) {
					// In case Number format exception, just ignore it.
				}
			}
		}
	}

	/**
	 * Read excel row into UIModel instance
	 *
	 * @param curSheet
	 * @param fieldList
	 * @param configList
	 * @param rowIndex
	 * @return
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws NumberFormatException
	 */
	public void readRow(HSSFSheet curSheet, SEUIComModel seData,
			List<Field> fieldList, List<ServiceExcelCellConfig> configList,
			int rowIndex) throws IllegalArgumentException,
			IllegalAccessException, ParseException {
		HSSFRow hssfRow = curSheet.getRow(rowIndex);
		if (hssfRow == null) {
			return;
		}
		if (ServiceCollectionsHelper.checkNullList(configList)) {
			return;
		}
		for (ServiceExcelCellConfig cellConfig : configList) {
			Field field = ServiceEntityFieldsHelper.filterFieldByNameOnline(
					fieldList, cellConfig.getFieldName());
			if (field == null) {
				continue;
			}
			HSSFCell cell = hssfRow.getCell(cellConfig.getColIndex());
			if (cell != null) {
				try {
					cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
					ServiceReflectiveHelper.reflectSetValue(field, seData,
							getValue(cell), null);
				} catch (NumberFormatException e) {
					// In case Number format exception, just ignore it.
				}
			}
		}
	}

	public static String getValue(XSSFCell xssfCell) {
		if (xssfCell.getCellType() == org.apache.poi.ss.usermodel.CellType.BOOLEAN) {
			// return the boolean value
			return String.valueOf(xssfCell.getBooleanCellValue());
		} else if (xssfCell.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
			// return the numeric value
			return String.valueOf(xssfCell.getNumericCellValue());
		} else {
			// return string type value
			return String.valueOf(xssfCell.getStringCellValue());
		}
	}

	public static String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.CellType.BOOLEAN) {
			// return the boolean value
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
			// return the numeric value
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			// return string type value
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	public void updateExcelToDocMatItem(
			DocMatItemUIModel docMatItemUIModel,
			ServiceEntityNode parentDocNode,
			List<ServiceEntityNode> rawDocMatItemList, boolean overwriteFlag,
			ExcelUpdateExecutor excelUpdateExecutor, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException,
			MaterialException {
		/*
		 * [Step1] Check if the material SKU ID exist, Process Material SKU info
		 */
		String refMaterialSKUId = docMatItemUIModel.getRefMaterialSKUId();
		if (ServiceEntityStringHelper.checkNullString(refMaterialSKUId)) {
			// If not, this is invalid record
			return;
		}
		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(refMaterialSKUId,
						IServiceEntityNodeFieldConstant.ID,
						MaterialStockKeepUnit.NODENAME,
						serialLogonInfo.getClient(), null, true);
		if (materialStockKeepUnit == null) {
			// log error and return
			logger.error("Material SKU can not be found by:" + refMaterialSKUId);
			return;
		}
		String refMaterialSKUUUID = materialStockKeepUnit.getUuid();
		docMatItemUIModel.setRefMaterialSKUUUID(materialStockKeepUnit
				.getUuid());
		/*
		 * [Step2] Process Unit
		 */
		Map<String, String> materialUnitMap = materialStockKeepUnitManager
				.initMaterialUnitMap(refMaterialSKUUUID, serialLogonInfo.getClient());
		String refUnitValueKey = ServiceCollectionsHelper.getStringKeyByValue(
				materialUnitMap, docMatItemUIModel.getRefUnitName());
		if (ServiceEntityStringHelper.checkNullString(refUnitValueKey)) {
			logger.error("NO Unit:" + refMaterialSKUId);
			return;
		}
		docMatItemUIModel.setRefUnitUUID(refUnitValueKey);
		/*
		 * [Step2] Ignore insert this purchase contract material item, if same
		 * material uuid exist.
		 */
		ServiceEntityNode docMatItemBack = _filterMatItemOnline(
				rawDocMatItemList, docMatItemUIModel.getRefMaterialSKUUUID(),
				parentDocNode.getUuid());
		if (docMatItemBack != null && !overwriteFlag) {
			return;
		} else {

		}
		if(excelUpdateExecutor != null){
			excelUpdateExecutor.insertExcelModelUnion(docMatItemUIModel, parentDocNode);
		}
	}

	public interface ExcelUpdateExecutor<T extends SEUIComModel, R extends ServiceEntityNode>{

		void insertExcelModelUnion(T seUIModel, R parentNode);

	}


	private ServiceEntityNode _filterMatItemOnline(
			List<ServiceEntityNode> rawDocMatItemList,
			String refMaterialSKUUUID, String baseUUID) {
		if (ServiceCollectionsHelper.checkNullList(rawDocMatItemList)) {
			return null;
		}
		if (ServiceEntityStringHelper.checkNullString(refMaterialSKUUUID)
				|| ServiceEntityStringHelper.checkNullString(baseUUID)) {
			return null;
		}
		for (ServiceEntityNode seNode : rawDocMatItemList) {
			DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
			if (baseUUID.equals(docMatItemNode.getParentNodeUUID())
					&& refMaterialSKUUUID.equals(docMatItemNode
					.getRefMaterialSKUUUID())) {
				return docMatItemNode;
			}
		}
		return null;
	}

}
