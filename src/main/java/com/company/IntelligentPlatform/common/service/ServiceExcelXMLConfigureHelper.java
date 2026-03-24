package com.company.IntelligentPlatform.common.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO-LEGACY: import platform.foundation.Administration.InstallService.ServiceEntityInstallConstantsHelper;
// TODO-LEGACY: import platform.foundation.Administration.InstallService.Configure.IServiceExcelConfigureConstants;
import com.company.IntelligentPlatform.common.service.ServiceExcelCellConfig;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.common.dto.IFinSettleConfigure;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.model.AttachmentConstantHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceDocumentReportTemplate;

@Service
public class ServiceExcelXMLConfigureHelper {

	public static final String LABEL_ROOT = "excelConfigure";

	public static final String LABEL_EXCEL_FILE = "excelFile";

	public static final String ATTR_FILE_TITLE = "title";

	public static final String ATTR_FILE_TIMESTAMP = "timeStampFlag";

	public static final String ATTR_FILE_PATH = "filePath";

	public static final String LABEL_FIELD_LIST = "fieldList";

	public static final String LABEL_FIELD = "field";

	public static final String ATTR_FIELD_NAME = "fieldName";

	public static final String ATTR_FIELD_LABEL = "fieldLabel";

	public static final String ATTR_FIELD_COL_INDEX = "colIndex";

	public static final String ATTR_IDFLAG = "idFlag";

	public static final String ATTR_FIELD_WEIGHT = "weight";

	public static final String ATTR_FIELD_COLOR = "color";

	public static final String ATTR_FIELDLIST_INDEX = "listIndex";

	public static final String DEFAULT_CONFIG_POST_FIX = "ExcelConfigure";

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;
	
	@Autowired
	protected ServiceDocumentSettingManager serviceDocumentSettingManager;

	/**
	 * Return the path for excel configuration XML files
	 * 
	 * @return
	 * @throws ServiceExcelConfigException
	 * @throws IOException
	 */
	public static String getExcelConfigurePath()
			throws ServiceExcelConfigException {
		String path = IServiceExcelConfigureConstants.class.getResource("")
				.getPath();
		return path;
	}

	/**
	 * Return the path for excel configuration XML files
	 * 
	 * @return
	 * @throws ServiceExcelConfigException
	 * @throws IOException
	 */
	public static String getExcelSrcConfigurePath()
			throws ServiceExcelConfigException, IOException {
		String path = ServiceEntityInstallConstantsHelper
				.getSrcAnyClassPath(IServiceExcelConfigureConstants.class);
		return path;
	}

	/**
	 * Generate the default excel configure list by retrieving the information
	 * model properties file
	 * 
	 * @param propertyPath
	 * @param propertyFile
	 * @param locale
	 * @param excelUICls
	 * @return
	 * @throws ServiceExcelConfigException
	 */
	public List<ServiceExcelCellConfig> genDefCellConfigs(String propertyPath,
			String propertyFile, Locale locale, Class<?> excelUICls)
			throws ServiceExcelConfigException {
		// Get field list from excel model and all properties resource map
		List<ServiceExcelCellConfig> excelConfigList = new ArrayList<ServiceExcelCellConfig>();
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getSelfDefinedFieldsList(excelUICls);
		try {
			Map<String, String> propertyMap = serviceDropdownListHelper
					.getDropDownMap(propertyPath, propertyFile, locale);
			// Traverse all field list and set field label
			int colIndex = 0;
			for (Field field : fieldList) {
				ServiceExcelCellConfig serviceExcelCellConfig = new ServiceExcelCellConfig();
				serviceExcelCellConfig
						.setColor(ServiceExcelCellConfig.COLOR_NORMAL);
				serviceExcelCellConfig.setColIndex(colIndex);
				serviceExcelCellConfig.setFieldName(field.getName());
				serviceExcelCellConfig
						.setFieldWeight(ServiceExcelCellConfig.DEF_WEIGHT_MID);
				String fieldLabel = field.getName();
				if (propertyMap.get(field.getName()) != null
						&& !propertyMap.get(field.getName()).equals(
								ServiceEntityStringHelper.EMPTYSTRING)) {
					fieldLabel = propertyMap.get(field.getName());
				}
				// Filter out the acc object, no need to add to settle excel
				// configure
				if (field.getName().equals(
						IFinSettleConfigure.FIELDTYPE_FINACCOBJ)) {
					continue;
				}
				serviceExcelCellConfig.setFieldLabel(fieldLabel);
				excelConfigList.add(serviceExcelCellConfig);
				colIndex++;
			}
		} catch (IOException e) {
			throw new ServiceExcelConfigException(
					ServiceExcelConfigException.PARA_SYSTEM_WRONG, e.getMessage());
		}
		return excelConfigList;
	}

	/**
	 * 
	 * @param propertyPath
	 * @param fileTitle
	 *            : title of excel file in configuration
	 * @param propertyFile
	 * @param locale
	 * @param excelUICls
	 * @return
	 * @throws ServiceExcelConfigException
	 */
	public Document generateDefaultCellConfigure(String propertyPath,
			String fileTitle, String propertyFile, Locale locale,
			Class<?> excelUICls) throws ServiceExcelConfigException {
		List<ServiceExcelCellConfig> excelConfigList = this.genDefCellConfigs(
				propertyPath, propertyFile, locale, excelUICls);
		if (excelConfigList == null || excelConfigList.size() == 0) {
			return null;
		}
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement(LABEL_ROOT);
		Element excelFileElement = rootElement.addElement(LABEL_EXCEL_FILE);
		excelFileElement.addAttribute(ATTR_FILE_TITLE, fileTitle);
		String defTimestamp = Integer.valueOf(ServiceExcelReportConfig.TIMESTAMP_FLAG_NEED).toString();
		excelFileElement.addAttribute(ATTR_FILE_TIMESTAMP, defTimestamp);
		excelFileElement.addAttribute(ATTR_FILE_PATH,
				IServiceExcelConfigureConstants.DEF_EXCEL_LOCPATH);
		Element fieldListElement = rootElement.addElement(LABEL_FIELD_LIST);
		for (ServiceExcelCellConfig cellConfig : excelConfigList) {
			Element fieldConfigElement = fieldListElement
					.addElement(LABEL_FIELD);
			fieldConfigElement.addAttribute(ATTR_FIELD_NAME,
					cellConfig.getFieldName());
			fieldConfigElement.addAttribute(ATTR_FIELD_LABEL,
					cellConfig.getFieldLabel());
			fieldConfigElement.addAttribute(ATTR_FIELD_COL_INDEX, Integer.valueOf(cellConfig.getColIndex()).toString());
			if (cellConfig.isIdFlag()) {
				fieldConfigElement.addAttribute(ATTR_IDFLAG,
						ServiceEntityStringHelper.TRUE);
			}
			fieldConfigElement.addAttribute(ATTR_FIELD_WEIGHT, Integer.valueOf(cellConfig.getFieldWeight()).toString());
			fieldConfigElement.addAttribute(ATTR_FIELD_COLOR, Integer.valueOf(cellConfig.getColor()).toString());
		}
		return document;
	}

	/**
	 * Read configure file and get the document
	 * 
	 * @param configURL
	 * @return
	 * @throws ServiceExcelConfigException
	 * @throws DocumentException
	 */
	public Document readExcelConfigure(String configURL)
			throws ServiceExcelConfigException, DocumentException {
		SAXReader reader = new SAXReader();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		File file = new File(configURL);
		if (file.exists()) {
			Document document = reader.read(file);
			return document;
		} else {
			throw new ServiceExcelConfigException(
					ServiceExcelConfigException.PARA_NO_CONFIGURE, configURL);
		}
	}
	
	/**
	 * Read configure file and get the document
	 * 
	 * @param configURL
	 * @return
	 * @throws ServiceExcelConfigException
	 * @throws DocumentException
	 * @throws ServiceEntityConfigureException 
	 */
	@Deprecated
	public Document readExcelConfigureByResId(String resourceId, String client)
			throws ServiceExcelConfigException, DocumentException, ServiceEntityConfigureException {
		SAXReader reader = new SAXReader();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		ServiceDocumentReportTemplate reportTemplate = serviceDocumentSettingManager
				.getDocumentReportTemplate(resourceId,
						AttachmentConstantHelper.TYPE_XML, client);
		if (reportTemplate == null) {
			throw new ServiceExcelConfigException(
					ServiceExcelConfigException.PARA_NO_CONFIGURE, resourceId);
		}
		InputStream in = new ByteArrayInputStream(reportTemplate.getContent());
		Document document = reader.read(in) ;
		return document;
	}
	
	public List<ServiceExcelCellConfig> getAllCellConfigList(Document document) {
		Element rootElement = document.getRootElement();
		Element fieldListElement = rootElement.element(LABEL_FIELD_LIST);
		List<ServiceExcelCellConfig> cellConfigList = new ArrayList<ServiceExcelCellConfig>();
		@SuppressWarnings("unchecked")
		List<Element> fieldElementList = fieldListElement.elements();
		if (fieldElementList != null && fieldElementList.size() > 0) {
			for (Element element : fieldElementList) {
				ServiceExcelCellConfig cellConfig = convElementToCellConfig(element);
				cellConfigList.add(cellConfig);
			}
		}
		return cellConfigList;
	}

	public ServiceExcelReportConfig getExcelReportConfigure(Document document) {
		Element rootElement = document.getRootElement();
		Element excelElement = rootElement.element(LABEL_EXCEL_FILE);
		ServiceExcelReportConfig serviceExcelReportConfig = new ServiceExcelReportConfig();
		serviceExcelReportConfig.setReportName(excelElement
				.attributeValue(ATTR_FILE_TITLE));
		serviceExcelReportConfig.setFilePath(excelElement
				.attributeValue(ATTR_FILE_PATH));
		int timeStampFlag = Integer.valueOf(excelElement.attributeValue(ATTR_FILE_TIMESTAMP));
		serviceExcelReportConfig.setTimeStampFlag(timeStampFlag);
		return serviceExcelReportConfig;
	}

	public ServiceExcelCellConfig getFieldConfigByName(Document document,
			String fieldName) {
		List<ServiceExcelCellConfig> cellConfigList = new ArrayList<ServiceExcelCellConfig>();
		return getFieldConfigByName(cellConfigList, fieldName);
	}

	public static ServiceExcelCellConfig getFieldConfigByName(
			List<ServiceExcelCellConfig> cellConfigList, String fieldName) {
		for (ServiceExcelCellConfig cellConfig : cellConfigList) {
			if (fieldName.equals(cellConfig.getFieldName())) {
				return cellConfig;
			}
		}
		return null;
	}

	protected ServiceExcelCellConfig convElementToCellConfig(Element element) {
		ServiceExcelCellConfig serviceExcelCellConfig = new ServiceExcelCellConfig();
		serviceExcelCellConfig.setFieldName(element
				.attributeValue(ATTR_FIELD_NAME));
		serviceExcelCellConfig.setFieldLabel(element
				.attributeValue(ATTR_FIELD_LABEL));
		String colIndexStr = element.attributeValue(ATTR_FIELD_COL_INDEX);
		int colIndex = Integer.valueOf(colIndexStr);
		serviceExcelCellConfig.setColIndex(colIndex);
		String fieldWeightStr = element.attributeValue(ATTR_FIELD_WEIGHT);
		int fieldWeight = Integer.valueOf(fieldWeightStr);
		serviceExcelCellConfig.setFieldWeight(fieldWeight);
		String colorStr = element.attributeValue(ATTR_FIELD_COLOR);
		int color = Integer.valueOf(colorStr);
		serviceExcelCellConfig.setColor(color);
		if (element.attributeValue(ATTR_IDFLAG) != null
				&& element.attributeValue(ATTR_IDFLAG).equals(
						ServiceEntityStringHelper.TRUE)) {
			serviceExcelCellConfig.setIdFlag(true);
		}
		return serviceExcelCellConfig;
	}

	public void writeDefaultCellConfigure(String fileTitle,
			String fileCoreName, String propertyPath, String propertyFile,
			Locale locale, Class<?> excelUICls, boolean overFlag)
			throws ServiceExcelConfigException, IOException {
		Document document = generateDefaultCellConfigure(propertyPath,
				fileTitle, propertyFile, locale, excelUICls);
		String fileURL = getExcelSrcConfigurePath() + File.separator
				+ getFullFileName(fileCoreName, locale);
		File file = new File(fileURL);
		try {
			if (file.isFile()) {
				if (overFlag) {
					file = new File(file.getAbsolutePath());
				} else {
					return;
				}
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter xmlWriter = new XMLWriter(new FileWriter(file), format);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException ex) {
			throw new ServiceExcelConfigException(
					ServiceExcelConfigException.WRONG_WRITE_REPORT);
		}
	}

	/**
	 * Logic to construct full name of excel configure file
	 * 
	 * @param fileCoreName
	 * @param locale
	 * @return
	 */
	public String getFullFileName(String fileCoreName, Locale locale) {
		String fullFileName = fileCoreName + "_" + locale.getLanguage() + "_"
				+ locale.getCountry() + ".xml";
		return fullFileName;
	}

	/**
	 * Logic to construct the full absolute URL of excel configure file
	 * 
	 * @param fileCoreName
	 * @param locale
	 * @return
	 * @throws IOException
	 * @throws ServiceExcelConfigException
	 */
	public String getFullFileAbsURL(String fileCoreName, Locale locale)
			throws ServiceExcelConfigException {
		String configURL = ServiceExcelXMLConfigureHelper
				.getExcelConfigurePath()
				+ File.separator
				+ getFullFileName(fileCoreName, locale);
		return configURL;
	}

}
