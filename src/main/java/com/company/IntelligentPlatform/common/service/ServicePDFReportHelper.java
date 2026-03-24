package com.company.IntelligentPlatform.common.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.SEUIModelFieldsHelper;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.SerialNumberSettingManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.DocumentBarCodeService;
import com.company.IntelligentPlatform.common.service.ServiceBarcodeException;
import com.company.IntelligentPlatform.common.model.HostCompany;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ServicePDFReportHelper {

	public static final String PREFIX_LABEL = "label";

	public static final String COMLOGO = "comLogo";

	public static final String COMTELEPHONE = "comTelephone";

	public static final String COMFAX = "comFax";

	public static final String CRATEDBYNAME = "createdByName";

	public static final String CRATEDTIME = "createdTime";

	public static final String BARCODE = "barcode";

	public static final String PRODUCT_SIGNATURE = "productSignature";
	
	public static final String SIGNATURE = "signature";

	public static final String REPORT_TITLE = "reportTitle";

	public static final String URL_LABEL_PROP = "SystemReportLabel";

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected DocumentBarCodeService documentBarCodeService;

	@Autowired
	protected SerialNumberSettingManager serialNumberSettingManager;

	public void getDefaultReportTitleInfo(Map<String, Object> parameters,
			Map<String, String> rawPropertiesMap, String modelName,
			String barcode, HostCompany hostCompany)
			throws ServiceEntityConfigureException {
		if (hostCompany == null) {
			return;
		}
		String reportTitle = hostCompany.getComReportTitle() + rawPropertiesMap.get(REPORT_TITLE);
		parameters.put(REPORT_TITLE, reportTitle);
		String reportSignature = getProductSignature();
		parameters.put(PRODUCT_SIGNATURE, reportSignature);
		if (hostCompany.getComLogo() != null
				&& hostCompany.getComLogo().length > 0) {
			InputStream sbs = new ByteArrayInputStream(hostCompany.getComLogo());
			parameters.put(COMLOGO, sbs);
		}
		parameters.put(COMTELEPHONE, hostCompany.getTelephone());
		parameters.put(COMFAX,hostCompany.getFax());
		// Should clear to null value firstly
		parameters.put(BARCODE, null);
		if (!ServiceEntityStringHelper.checkNullString(barcode)) {
			if (!ServiceEntityStringHelper.checkNullString(modelName)) {
				// In case need to print barcode
				try {
					String barcodeType = serialNumberSettingManager
							.getBarcodeType(modelName, hostCompany.getClient());
					byte[] barcodeBytes = documentBarCodeService
							.generateBarcodeStream(barcodeType, barcode);
					if(barcodeBytes != null && barcodeBytes.length > 0){
						InputStream barstream = new ByteArrayInputStream(barcodeBytes);
						parameters.put(BARCODE, barstream);
					}
				} catch (ServiceBarcodeException ex) {
					// should clear the barcode
				}
			}
		}
	}

	public String getProductSignature() {
		return "道森ERP软件";
	}

	public void getUIModelValueParameterMap(Class<?> uiModelClass,
			Map<String, Object> parameters, SEUIComModel seUIComModel)
			throws IllegalArgumentException, IllegalAccessException {
		List<Field> searchFields = SEUIModelFieldsHelper
				.getFieldsList(uiModelClass);
		if (searchFields == null || searchFields.size() == 0) {
			return;
		}
		for (Field field : searchFields) {			
			field.setAccessible(true);
			Object value = field.get(seUIComModel);
			parameters.put(field.getName(), value);
		}
	}

	public void getUIModelLabelParameterMap(Class<?> uiModelClass,
			Map<String, Object> parameters, Map<String, String> rawPropertiesMap)
			throws IllegalArgumentException, IllegalAccessException,
			IOException {
		List<Field> searchFields = SEUIModelFieldsHelper
				.getFieldsList(uiModelClass);
		if (searchFields == null || searchFields.size() == 0) {
			return;
		}
		for (Field field : searchFields) {
			field.setAccessible(true);
			String label = rawPropertiesMap.get(field.getName());
			if (!ServiceEntityStringHelper.checkNullString(label)) {
				parameters.put(
						PREFIX_LABEL
								+ ServiceEntityStringHelper
										.headerToUpperCase(field.getName()),
						label);
			}
		}
		/**
		 * Get system default label
		 */
		getSystemDefaultLabelMap(parameters);
	}

	/**
	 * Get system standard label name list, will be covered by framework
	 * 
	 * @return
	 */
	public List<String> getSystemDefaultElement() {
		List<String> systemElementList = new ArrayList<String>();
		systemElementList.add(COMTELEPHONE);
		systemElementList.add(COMFAX);
		systemElementList.add(CRATEDBYNAME);
		systemElementList.add(CRATEDTIME);
		systemElementList.add(SIGNATURE);
		return systemElementList;
	}

	public void getSystemDefaultLabelMap(Map<String, Object> parameters)
			throws IOException {
		Map<String, String> defLabelMap = getDefLabelMap();
		List<String> systemElementList = getSystemDefaultElement();
		for (String element : systemElementList) {
			parameters.put(
					PREFIX_LABEL
							+ ServiceEntityStringHelper
									.headerToUpperCase(element),
					defLabelMap.get(element));
		}
	}

	protected Map<String, String> getDefLabelMap() throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = ServicePDFReportHelper.class.getResource("").getPath();
		String resFileName = URL_LABEL_PROP;
		Map<String, String> labelMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return labelMap;
	}

	public String getLabelByKey(String key) throws IOException {
		Map<String, String> labelMap = getDefLabelMap();
		return labelMap.get(key);
	}

}
