package com.company.IntelligentPlatform.common.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.IServiceJSONBasicErrorCode;

@Service
public class ServiceMessageHelper {

    /**
     * Constant: common service entity resource file
     */
    public static final String COMMON_MSG_RESOURCE = "ServiceCommonResource";

    public static final int ERROR_CODE = 2;

    public static final String LABEL_ERROR_CODE = "errorCode";

    public static final String LABEL_ERROR_MSG = "errorMessage";

    public static final String UNKNOWN_ERROR = "Unkown error";

    public String genSimpleErrorResponse(String msg) {
        String content = "{\"" + LABEL_ERROR_CODE + "\":\""
                + IServiceJSONBasicErrorCode.UNKNOWN_SYS_ERROR + "\"," + "\""
                + LABEL_ERROR_MSG + "\":\"" + msg + "\"}";
        return content;
    }

    public String genSimpleDoneRespone(String msg) {
        String content = "{\"" + LABEL_ERROR_CODE + "\":\""
                + IServiceJSONBasicErrorCode.DEF_OK + "\"," + "\""
                + LABEL_ERROR_MSG + "\":\"" + msg + "\"}";
        return content;
    }

    public String genSimpleRespone(String msg, int errorCode) {
        String content = "{\"" + LABEL_ERROR_CODE + "\":\"" + errorCode + "\","
                + "\"" + LABEL_ERROR_MSG + "\":\"" + msg + "\"}";
        return content;
    }

    public String getMessage(int errorCode)
            throws ServiceEntityInstallationException, IOException {
        String path = ServiceMessageHelper.class.getResource("").getPath();
        String resFileFullName = path + COMMON_MSG_RESOURCE;
        Map<Integer, String> errorMSGMap = getDropDownMap(resFileFullName);
        return errorMSGMap.get(errorCode);
    }

    /**
     * generate the drop down list hash map in multi-language environment
     *
     * @param resFileName : FULL properties file name without language post fix
     * @return
     * @throws IOException
     */
    public Map<Integer, String> getDropDownMap(String resFileName)
            throws IOException {
        // Get current JVM locale setting
        Locale locale = ServiceLanHelper.getDefault();
        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        // Check if the resource file exist or not
        String resLanFileName = resFileName + "_" + locale.getLanguage() + "_"
                + locale.getCountry() + ".properties";
        Properties prop = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(
                resLanFileName));
        prop.load(inputStream);
        Iterator<Entry<Object, Object>> it = prop.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Object, Object> entry = it.next();
            String keyString = (String) entry.getKey();
            Integer key = Integer.valueOf(keyString);
            String value = (String) entry.getValue();
            valueMap.put(key, value);
        }
        return valueMap;
    }

    /**
     * generate the drop down list hash map in multi-language environment
     *
     * @param resFileName : FULL properties file name without language post fix
     * @return
     * @throws IOException
     */
    public Map<String, String> getStrDropDownMap(String resFileName)
            throws IOException {
        // Get current JVM locale setting
        Locale locale = ServiceLanHelper.getDefault();
        Map<String, String> valueMap = new HashMap<String, String>();
        // Check if the resource file exist or not
        String resLanFileName = resFileName + "_" + locale.getLanguage() + "_"
                + locale.getCountry() + ".properties";
        Properties prop = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(
                resLanFileName));
        prop.load(inputStream);
        Iterator<Entry<Object, Object>> it = prop.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Object, Object> entry = it.next();
            String keyString = (String) entry.getKey();
            String value = (String) entry.getValue();
            valueMap.put(keyString, value);
        }
        return valueMap;
    }

}
