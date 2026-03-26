package com.company.IntelligentPlatform.common.service;

import java.util.List;

import com.google.gson.Gson;
// TODO-LEGACY: import org.apache.commons.httpclient.HttpStatus; // replaced by local HttpStatus stub

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IServiceJSONBasicErrorCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class ServiceJSONParser {

    /**
     * Parse the content part JSON data from the default-format JSON data
     *
     * @param defaultJSONdata
     * @return
     */
    public static String parseDefaultContentData(String defaultJSONdata) {
        JSONObject defaultJSONObject = JSONObject.fromObject(defaultJSONdata);
        String contentJSON = defaultJSONObject.get(
                ServiceJSONDataConstants.ELE_CONTENT).toString();
        return contentJSON;
    }

    /**
     * Parse the API key value from the default-format JSON data
     *
     * @param defaultJSONdata
     * @return
     */
    public static String parseDefaultAPIKey(String defaultJSONdata) {
        JSONObject defaultJSONObject = JSONObject.fromObject(defaultJSONdata);
        String apiValue = defaultJSONObject.get(
                ServiceJSONDataConstants.ELE_API_KEY).toString();
        return apiValue;
    }

    public static String emptyJson() {
        JSONObject jsonObject = new JSONObject();
        return jsonObject.toString();
    }

    /**
     * Parse the Error Code Value from the Common JSON data
     *
     * @param commonJSONdata
     * @return
     */
    public static int parseCommonErrorCode(String commonJSONdata) {
        JSONObject defaultJSONObject = JSONObject.fromObject(commonJSONdata);
        String errorCodeString = defaultJSONObject.get(
                ServiceJSONDataConstants.ELE_CONTENT).toString();
        return Integer.parseInt(errorCodeString);
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
        String contentUnion = parseDefaultContentData(jsonData);
        JSONObject cotentJSONObject = JSONObject.fromObject(contentUnion);
        return cotentJSONObject.get(element);
    }

    /**
     * generate the simple error code JSON data, only with error code
     * information
     *
     * @param errorCode
     * @return
     */
    public static String generateSimpleErrorJSON(int errorCode) {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        String errorContent = "\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                + "\":\"" + errorCode + "\"";
        content = content + "{" + errorContent + "}";
        return content;
    }

    /**
     * generate the simple error code JSON data, only with error code
     * information
     *
     * @param errorMessage
     * @return
     */
    public static String generateSimpleErrorJSON(String errorMessage, int statusCode) {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        String errorContent = "\"" + ServiceJSONDataConstants.ELE_ERROR_MSG
                + "\":\"" + errorMessage + "\", \""
                + ServiceJSONDataConstants.ELE_ERROR_CODE + "\":"
                + IServiceJSONBasicErrorCode.UNKNOWN_SYS_ERROR;
        content = content + "{" + errorContent + "}";
        return content;
    }

    /**
     * generate the simple error code JSON data, only with error code
     * information
     *
     * @param errorMessage
     * @return
     */
    public static String generateSimpleErrorJSON(String errorMessage) {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        String errorContent = "\"" + ServiceJSONDataConstants.ELE_ERROR_MSG
                + "\":\"" + errorMessage + "\", \""
                + ServiceJSONDataConstants.ELE_ERROR_CODE + "\":"
                + IServiceJSONBasicErrorCode.UNKNOWN_SYS_ERROR;
        content = content + "{" + errorContent + "}";
        return content;
    }

    /**
     * generate the simple error code JSON data, only with error code
     * information
     *
     * @param object
     * @return
     */
    public static String generateErrorJSONMessageArray(Object object) {
        JSONArray jsonArray = JSONArray.fromObject(object);
        String innerContent = jsonArray.toString();
        String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                + "\":\"" + IServiceJSONBasicErrorCode.UNKNOWN_SYS_ERROR + "\",\"" + ServiceJSONDataConstants.ELE_CONTENT + "\":"
                + innerContent + "}";
        return content;
    }

    /**
     * Generate the default JSON union with single SE entity instance:
     * <code>Format:</code> [SE name]:{[inner each field value pair]}
     *
     * @return
     * @throws ServiceJSONDataException
     */
    public static String generateSingleEntityUnion(ServiceEntityNode seNode)
            throws ServiceJSONDataException {
        String innerContent = ServiceJSONDataHelper.genJSONUnit(seNode);
        String content = "{\"" + seNode.getServiceEntityName() + "\":"
                + innerContent + "}";
        return content;
    }

    /**
     * Generate the default JSON union with single SE entity instance:
     * <code>Format:</code> [SE name]:{[inner each field value pair]}
     *
     * @return
     * @throws ServiceJSONDataException
     */
    public static String generateSingleUIModelUnion(String seName,
                                                    SEUIComModel uiModel) throws ServiceJSONDataException {
        String innerContent = ServiceJSONDataHelper.genJSONUIModel(uiModel,
                null);
        String content = "{\"" + seName + "\":" + innerContent + "}";
        return content;
    }

    /**
     * Generate the default JSON union with single SE entity instance:
     * <code>Format:</code> [SE name]:{[inner each field value pair]}
     *
     * @return
     * @throws ServiceJSONDataException
     */
    public static String generateSingleEntityUnion(ServiceEntityNode seNode,
                                                   List<String> fieldList) throws ServiceJSONDataException {
        String innerContent = ServiceJSONDataHelper.genJSONUnit(seNode,
                fieldList);
        String content = "{\"" + seNode.getServiceEntityName() + "\":"
                + innerContent + "}";
        return content;
    }

    public static String generateServiceContentUnion(String innerContentUnion) {
        return "\"" + ServiceJSONDataConstants.ELE_CONTENT + "\":"
                + innerContentUnion;
    }

    /**
     * Generate the default JSON union JSON data <code>Format:</code> {apiKey:
     * <p/>
     * {content:{[SE name]:{[inner each field value pair]}}
     * <p/>
     *
     * @return
     * @throws ServiceJSONDataException
     */
    public static String genDefSingleEntityJSONData(String apiKey,
                                                    ServiceEntityNode seNode) throws ServiceJSONDataException {
        String innerContent = generateSingleEntityUnion(seNode);
        innerContent = generateServiceContentUnion(innerContent);
        String content = "{\"" + ServiceJSONDataConstants.ELE_API_KEY + "\":\""
                + apiKey + "\",\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                + "\":\"" + IServiceJSONBasicErrorCode.DEF_OK + "\","
                + innerContent + "}";
        return content;
    }

    /**
     * Generate the default JSON union JSON data <code>Format:</code> {apiKey:
     * <p/>
     * {content:{[SE name]:{[inner each field value pair]}}
     * <p/>
     *
     * @return
     * @throws ServiceJSONDataException
     */
    public static String genDefSingleUIModelJSONData(String apiKey,
                                                     String seName, SEUIComModel uiModel)
            throws ServiceJSONDataException {
        String innerContent = generateSingleUIModelUnion(seName, uiModel);
        innerContent = generateServiceContentUnion(innerContent);
        String content = "{\"" + ServiceJSONDataConstants.ELE_API_KEY + "\":\""
                + apiKey + "\",\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                + "\":\"" + IServiceJSONBasicErrorCode.DEF_OK + "\","
                + innerContent + "}";
        return content;
    }

    /**
     * Package the inner content with Default OK code
     * Scenario:1> existed JSON content string 2> With default OKcode, 3> With Content label
     *
     * @param innerContent
     * @return
     */
    public static String packageDefOKContent(String innerContent) {
        String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                + "\":\"" + IServiceJSONBasicErrorCode.DEF_OK + "\",\"" + ServiceJSONDataConstants.ELE_CONTENT + "\":"
                + innerContent + "}";
        return content;
    }

    /**
     * Generate default JSON object with content package and default OK code
     * Scenario:1> Only one Object instance, 2> With default OKcode, 3> With Content label
     *
     * @param object
     * @return
     */
    public static String genDefOKJSONObject(Object object) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setAllowNonStringKeys(true);
        // JSONObject jsonObject = parseToJsonObject(object, jsonConfig);
        Gson gson = new Gson();
        String innerContent = gson.toJson(object);
        // String innerContent = jsonObject.toString();
        String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                + "\":\"" + HttpStatus.SC_OK + "\",\"" + ServiceJSONDataConstants.ELE_CONTENT + "\":"
                + innerContent + "}";
        return content;
    }

    /**
     * Logic to parse value object into Json object
     *
     * @param object
     * @param jsonConfig
     * @return
     */
    // TODO-LEGACY: private static JSONObject parseToJsonObject(Object object, JsonConfig jsonConfig) {
    private static Object parseToJsonObject(Object object, Object jsonConfig) { // TODO-LEGACY: was JSONObject/JsonConfig
        if (object == null) {
            return null;
        }
        if (!isSimpleValue(object)) {
            // TODO-LEGACY: return JSONObject.fromObject(object, jsonConfig);
            return null;
        }
        // TODO-LEGACY: JSONObject valueObject = new JSONObject();
        // TODO-LEGACY: valueObject.put(ServiceJSONDataConstants.PROP_VALUE, object);
        // TODO-LEGACY: return valueObject;
        return null;
    }

    /**
     * @param object
     * @return
     * @private check if this object is Simple value
     */
    private static boolean isSimpleValue(Object object) {
        if (Integer.class.getSimpleName().equals(object.getClass().getSimpleName())) {
            return true;
        }
        if (int.class.getSimpleName().equals(object.getClass().getSimpleName())) {
            return true;
        }
        if (Double.class.getSimpleName().equals(object.getClass().getSimpleName())) {
            return true;
        }
        if (double.class.getSimpleName().equals(object.getClass().getSimpleName())) {
            return true;
        }
        if (Long.class.getSimpleName().equals(object.getClass().getSimpleName())) {
            return true;
        }
        return long.class.getSimpleName().equals(object.getClass().getSimpleName());
    }

    /**
     * Generate default JSON array object with content package and default OK code
     * Scenario:1> Only one Object instance, 2> With default OKcode, 3> With Content label
     *
     * @param object
     * @return
     */
    public static String genDefOKJSONArray(Object object) {
        JSONArray jsonArray = JSONArray.fromObject(object);
        String innerContent = jsonArray.toString();
        if(object == null){
            return genSimpleOKResponse();
        }
        String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                + "\":\"" + IServiceJSONBasicErrorCode.DEF_OK + "\",\"" + ServiceJSONDataConstants.ELE_CONTENT + "\":"
                + innerContent + "}";
        return content;
    }

    /**
     * Convert object directly to JSON Object String
     *
     * @param object
     * @return
     */
    public static String getPureJSONObject(Object object) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        String content = jsonObject.toString();
        return content;
    }

    /**
     * Generate default JSON object with content package and default OK code
     * Scenario:1> Only one Object instance, 2> With default OKcode, 3> With Content label
     *
     * @param text
     * @return
     */
    public static String genDefOKText(String text) {
        String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                + "\":\"" + IServiceJSONBasicErrorCode.DEF_OK + "\",\"" + ServiceJSONDataConstants.ELE_CONTENT + "\":\""
                + text + "\"}";
        return content;
    }

    public static String genSimpleOKResponse() {
        String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                + "\":\"" + HttpStatus.SC_OK + "\"" + "}";
        return content;
    }

    public static String genNotModifyResponse() {
        String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                + "\":\"" + HttpStatus.SC_NOT_MODIFIED + "\"" + "}";
        return content;
    }

    public static String genNotFoundResponse() {
        String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                + "\":\"" + HttpStatus.SC_NOT_FOUND + "\"" + "}";
        return content;
    }

    public static String genDeleteOKResponse() {
        String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                + "\":\"" + HttpStatus.SC_NO_CONTENT + "\"" + "}";
        return content;
    }

    public static String genSimpleErrorCodeResponse(int errorCode) {
        String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                + "\":\"" + errorCode + "\"" + "}";
        return content;
    }

}
