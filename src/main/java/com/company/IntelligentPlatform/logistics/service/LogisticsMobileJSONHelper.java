package com.company.IntelligentPlatform.logistics.service;

import net.sf.json.JSONObject;

import com.company.IntelligentPlatform.logistics.service.ILogisticsMobileJSONConstant;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * The Common JSON data format and process helper class for mobile client
 * 
 * @author Zhang,Hang
 * 
 */
public class LogisticsMobileJSONHelper {

	/**
	 * Generate the default-format JSON data format to Mobile client </p>Common
	 * JSON data is composed of content, errorCode, API key, and version
	 * information
	 * 
	 * 
	 * @param errorCode
	 * @param apiKey
	 * @param content
	 *            : content already in JSON format data
	 * @param versionJSONData
	 *            : Version list JSON format data
	 * @return
	 */
	public static String generateDefaultJSONData(int errorCode, String apiKey,
			String contentUnit, String versionJSONUnit) {
		String jsonData = "{";
		String apiKeyContent = "\"" + ILogisticsMobileJSONConstant.ELE_API_KEY
				+ "\":\"" + apiKey + "\"";
		jsonData = jsonData + apiKeyContent;
		if (errorCode != 0) {
			String errorCodeContent = "\""
					+ ILogisticsMobileJSONConstant.ELE_ERROR_CODE + "\":"
					+ errorCode;
			jsonData = jsonData + "," + errorCodeContent;
		}
		if (versionJSONUnit != null
				&& !versionJSONUnit
						.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			String versionContent = "\""
					+ ILogisticsMobileJSONConstant.ELE_VERSION + "\":"
					+ versionJSONUnit;
			jsonData = jsonData + "," + versionContent;
		}
		if (contentUnit != null
				&& !contentUnit.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			// String contentString = "\"" +
			// ILogisticsMobileJSONConstant.ELE_CONTENT
			// + "\":" + contentUnit;
			jsonData = jsonData + "," + contentUnit;
		}
		jsonData = jsonData + "}";
		return jsonData;
	}

	/**
	 * Parse the content part JSON data from the default-format JSON data
	 * 
	 * @param commonJSONdata
	 * @return
	 */
	public static String parseDefaultContentData(String defaultJSONdata) {
		return ServiceJSONParser.parseDefaultContentData(defaultJSONdata);
	}

	/**
	 * Parse the version part JSON data from the default-format JSON data
	 * 
	 * @param commonJSONdata
	 * @return
	 */
	public static String parseDefaultVersionData(String defaultJSONdata) {
		JSONObject defaultJSONObject = JSONObject.fromObject(defaultJSONdata);
		String versionJSON = defaultJSONObject.get(
				ILogisticsMobileJSONConstant.ELE_VERSION).toString();
		return versionJSON;
	}

	/**
	 * Parse the version part JSON data from the default-format JSON data
	 * 
	 * @param commonJSONdata
	 * @return
	 */
	public static int parseDefaultVersionValue(String defaultJSONdata,
			String subVersionTitle) {
		JSONObject defaultJSONObject = JSONObject.fromObject(defaultJSONdata);
		Object versionJSON = defaultJSONObject
				.get(ILogisticsMobileJSONConstant.ELE_VERSION);
		JSONObject subVerJSONObject = JSONObject.fromObject(versionJSON);
		String subVersionString = subVerJSONObject.get(subVersionTitle)
				.toString();
		return Integer.parseInt(subVersionString);
	}

	/**
	 * Parse the API key value from the default-format JSON data
	 * 
	 * @param commonJSONdata
	 * @return
	 */
	public static String parseDefaultAPIKey(String defaultJSONdata) {
		return ServiceJSONParser.parseDefaultAPIKey(defaultJSONdata);
	}

	/**
	 * Parse the Error Code Value from the Common JSON data
	 * 
	 * @param commonJSONdata
	 * @return
	 */
	public static int parseCommonErrorCode(String commonJSONdata) {
		return ServiceJSONParser.parseCommonErrorCode(commonJSONdata);
	}

	/**
	 * parse the value from [content] section in default JSON data, but the data
	 * element should exist in first layer
	 * 
	 * @param element
	 * @return
	 */
	public static Object parseValueFromContentSimpleModule(String jsonData,
			String element) {
		return ServiceJSONParser.parseValueFromContentSimpleModule(jsonData,
				element);
	}

	/**
	 * generate the simple error code JSON data, only with error code
	 * information
	 * 
	 * @param errorCode
	 * @return
	 */
	public static String generateSimpleErrorJSON(int errorCode) {
		return ServiceJSONParser.generateSimpleErrorJSON(errorCode);
	}

	// protected static String genJSONField(Field field, Object object)
	// throws IllegalArgumentException, IllegalAccessException {
	// field.setAccessible(true);
	// boolean stringTypeFlag = false;
	// String stringTypeName = String.class.getSimpleName();
	// String dateTypeName = Date.class.getSimpleName();
	// String fieldTypeName = field.getType().getSimpleName();
	// String content = "\"" + field.getName() + "\":";
	// if (fieldTypeName.equals(stringTypeName)) {
	// // Indicate this is "String" type field, need quotation later
	// stringTypeFlag = true;
	// }
	// if (fieldTypeName.equals(dateTypeName)) {
	// // Indicate this is "String" type field, need quotation later
	// stringTypeFlag = true;
	// }
	// Object value = field.get(object);
	// if (value == null)
	// return content = content + "\"" + "\"";
	// String valueField = field.get(object).toString();
	// if (stringTypeFlag) {
	// // In case a string type field, need to add quotation marks
	// content = content + "\"" + valueField + "\"";
	// } else {
	// content = content + "\"" + valueField + "\"";
	// // content = content + valueField + "";
	// }
	// return content;
	// }

}
