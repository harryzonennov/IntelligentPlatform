package com.company.IntelligentPlatform.common.service;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

// TODO-LEGACY: import platform.foundation.Administration.InstallService.ServiceEntityInstallConstantsHelper;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * This class is used to generate drop down list in multi-language environment
 *
 * @author Zhang, Hang
 * @date 2013-2-12
 */
@Service
public class ServiceDropdownListHelper {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public Map<Integer, String> getUIDropDownMap(Class<?> uiModelCls,
                                                 String fieldName) throws ServiceEntityInstallationException {
        try {
            Field field = uiModelCls.getDeclaredField(fieldName);
            ISEDropDownResourceMapping seDropDownConfig = field
                    .getAnnotation(ISEDropDownResourceMapping.class);
            if (seDropDownConfig == null
                    || seDropDownConfig.resouceMapping() == null)
                throw new ServiceEntityInstallationException(
                        ServiceEntityInstallationException.PARA_FIELD_CONFIG_DROPDOWN,
                        field.getName());
            String resFileFullName = ServiceEntityStringHelper.EMPTYSTRING;
            if (seDropDownConfig.resourceFile().equals(
                    ServiceEntityStringHelper.EMPTYSTRING)) {
                String resFileName = seDropDownConfig.resouceMapping();
                String path = uiModelCls.getResource("").getPath();
                resFileFullName = path + resFileName;
            } else {
                String fileInPackage = seDropDownConfig.resourceFile();
                resFileFullName = ServiceEntityInstallConstantsHelper
                        .convertPackageIntoPath(fileInPackage);
            }
            return getDropDownMap(resFileFullName);
        } catch (IOException ex) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(ex,
                    fieldName));
            throw new ServiceEntityInstallationException(
                    ServiceEntityInstallationException.PARA_FIELD_CONFIG_DROPDOWN,
                    fieldName);
        } catch (SecurityException | NoSuchFieldException e) {
            throw new ServiceEntityInstallationException(
                    ServiceEntityInstallationException.PARA_FIELD_CONFIG_DROPDOWN,
                    fieldName);
        }
    }

    public Map<Integer, String> getUIDropDownMap(Class<?> uiModelCls,
                                                 String fieldName, String rawLanCode)
            throws ServiceEntityInstallationException {
        try {
            Field field = uiModelCls.getDeclaredField(fieldName);
            ISEDropDownResourceMapping seDropDownConfig = field
                    .getAnnotation(ISEDropDownResourceMapping.class);
            if (seDropDownConfig == null)
                throw new ServiceEntityInstallationException(
                        ServiceEntityInstallationException.PARA_FIELD_CONFIG_DROPDOWN,
                        field.getName());
            String resFileFullName = ServiceEntityStringHelper.EMPTYSTRING;
            if (seDropDownConfig.resourceFile().equals(
                    ServiceEntityStringHelper.EMPTYSTRING)) {
                String resFileName = seDropDownConfig.resouceMapping();
                String path = uiModelCls.getResource("").getPath();
                resFileFullName = path + resFileName;
            } else {
                String fileInPackage = seDropDownConfig.resourceFile();
                resFileFullName = ServiceEntityInstallConstantsHelper
                        .convertPackageIntoPath(fileInPackage);
            }
            return getDropDownMap(resFileFullName, rawLanCode);
        } catch (IOException | NoSuchFieldException | SecurityException ex) {
            throw new ServiceEntityInstallationException(
                    ServiceEntityInstallationException.PARA_FIELD_CONFIG_DROPDOWN,
                    fieldName);
        }
    }

    public static Map<Integer, String> getStaticUIDropDownMap(
            Class<?> uiModelCls, String fieldName, String rawLanCode)
            throws ServiceEntityInstallationException {
        try {
            Field field = uiModelCls.getDeclaredField(fieldName);
            ISEDropDownResourceMapping seDropDownConfig = field
                    .getAnnotation(ISEDropDownResourceMapping.class);
            if (seDropDownConfig == null)
                throw new ServiceEntityInstallationException(
                        ServiceEntityInstallationException.PARA_FIELD_CONFIG_DROPDOWN,
                        field.getName());
            String resFileFullName = ServiceEntityStringHelper.EMPTYSTRING;
            if (seDropDownConfig.resourceFile().equals(
                    ServiceEntityStringHelper.EMPTYSTRING)) {
                String resFileName = seDropDownConfig.resouceMapping();
                String path = uiModelCls.getResource("").getPath();
                resFileFullName = path + resFileName;
            } else {
                String fileInPackage = seDropDownConfig.resourceFile();
                resFileFullName = ServiceEntityInstallConstantsHelper
                        .convertPackageIntoPath(fileInPackage);
            }
            return getStaticDropDownMap(resFileFullName, rawLanCode);
        } catch (IOException | SecurityException | NoSuchFieldException ex) {
            throw new ServiceEntityInstallationException(
                    ServiceEntityInstallationException.PARA_FIELD_CONFIG_DROPDOWN,
                    fieldName);
        }
    }

    public static Map<String, String> getStaticUIStrDropDownMap(
            Class<?> uiModelCls, String fieldName, String rawLanCode)
            throws ServiceEntityInstallationException {
        try {
            Field field = uiModelCls.getDeclaredField(fieldName);
            ISEDropDownResourceMapping seDropDownConfig = field
                    .getAnnotation(ISEDropDownResourceMapping.class);
            if (seDropDownConfig == null)
                throw new ServiceEntityInstallationException(
                        ServiceEntityInstallationException.PARA_FIELD_CONFIG_DROPDOWN,
                        field.getName());
            String resFileFullName = ServiceEntityStringHelper.EMPTYSTRING;
            if (seDropDownConfig.resourceFile().equals(
                    ServiceEntityStringHelper.EMPTYSTRING)) {
                String resFileName = seDropDownConfig.resouceMapping();
                String path = uiModelCls.getResource("").getPath();
                resFileFullName = path + resFileName;
            } else {
                String fileInPackage = seDropDownConfig.resourceFile();
                resFileFullName = ServiceEntityInstallConstantsHelper
                        .convertPackageIntoPath(fileInPackage);
            }
            return getStrStaticDropDownMap(resFileFullName, rawLanCode);
        } catch (IOException | SecurityException | NoSuchFieldException ex) {
            throw new ServiceEntityInstallationException(
                    ServiceEntityInstallationException.PARA_FIELD_CONFIG_DROPDOWN,
                    fieldName);
        }
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
        Map<Integer, String> valueMap = new HashMap<>();
        // Check if the resource file exist or not
        String resLanFileName = resFileName + "_" + locale.getLanguage() + "_"
                + locale.getCountry() + ".properties";
        Properties prop = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(
                resLanFileName));
        prop.load(inputStream);
        for (Entry<Object, Object> entry : prop.entrySet()) {
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
    public Map<Integer, String> getDropDownMap(String resFileName,
                                               String rawLanCode) throws IOException {
        // Get current JVM locale setting
        Map<Integer, String> valueMap = new HashMap<>();
        // Check if the resource file exist or not
        String resLanFileName = genResLanFileName(resFileName, rawLanCode);
        Properties prop = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(
                resLanFileName));
        prop.load(inputStream);
        for (Entry<Object, Object> entry : prop.entrySet()) {
            String keyString = (String) entry.getKey();
            Integer key = Integer.valueOf(keyString);
            String value = (String) entry.getValue();
            valueMap.put(key, value);
        }
        return valueMap;
    }

    /**
     * Rule to generate res file with language postfix
     *
     * @param resFileName
     * @param locale
     * @return
     */
    public static String genResLanFileName(String resFileName, Locale locale) {
        return resFileName + "_" + ServiceLanHelper.getLocaleKeyStr(locale) + ".properties";
    }

    public static String genResLanFileName(String resFileName, String rawLanCode) {
        Locale locale = ServiceLanHelper.parseToLocale(rawLanCode);
        return genResLanFileName(resFileName, locale);
    }

    /**
     * generate the drop down list hash map in multi-language environment
     *
     * @param resFileName : FULL properties file name without language post fix
     * @return
     * @throws IOException
     */
    public static Map<Integer, String> getStaticDropDownMap(String resFileName,
                                                            String rawLanCode) throws IOException {
        // Get current JVM locale setting
        Locale locale = ServiceLanHelper.parseToLocale(rawLanCode);
        Map<Integer, String> valueMap = new HashMap<>();
        // Check if the resource file exist or not
        String resLanFileName = genResLanFileName(resFileName, locale);
        Properties prop = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(
                resLanFileName));
        prop.load(inputStream);
        for (Entry<Object, Object> entry : prop.entrySet()) {
            String keyString = (String) entry.getKey();
            Integer key = Integer.valueOf(keyString);
            String value = (String) entry.getValue();
            valueMap.put(key, value);
        }
        return valueMap;
    }

    public static boolean existsResLanResFile(String resFileName,
                                              String rawLanCode) {
        String resLanFileName = genResLanFileName(resFileName, rawLanCode);
        Path path = Paths.get(resLanFileName);
        return Files.exists(path);
    }

    /**
     * generate the drop down list hash map<String,String> by setting the path
     * and locale manually
     *
     * @param resFileName : FULL properties file name without language post fix
     * @return
     * @throws IOException
     */
    public static Map<String, String> getStrStaticDropDownMap(
            String resFileName, String rawLanCode) throws IOException {
        // Get current JVM locale setting
        Locale locale = ServiceLanHelper.parseToLocale(rawLanCode);
        return getStrStaticDropDownMap(resFileName, locale);
    }

    /**
     * generate the drop down list hash map<String,String> by setting the path
     * and locale manually
     *
     * @param resFileName : FULL properties file name without language post fix
     * @return
     * @throws IOException
     */
    public static Map<String, String> getStrStaticDropDownMap(
            String resFileName, Locale locale) throws IOException {
        // Get current JVM locale setting
        Map<String, String> valueMap = new HashMap<>();
        // Check if the resource file exist or not
        String resLanFileName = genResLanFileName(resFileName, locale);
        Properties prop = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(
                resLanFileName));
        prop.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        for (Entry<Object, Object> entry : prop.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            valueMap.put(key, value);
        }
        return valueMap;
    }

    /**
     * generate the drop down list hash map<String,String> by setting the path
     * and locale manually
     *
     * @param resFileName : FULL properties file name without language post fix
     * @return
     * @throws IOException
     */
    public Map<String, String> getDropDownMap(String path, String resFileName,
                                              Locale locale) throws IOException {
        Map<String, String> valueMap = new HashMap<>();
        String resLanFileName = genResLanFileName(resFileName, locale);
        Properties prop = new Properties();
        InputStream inputStream = null;
        try {
            if (ServiceEntityStringHelper.checkNullString(path)) {
                inputStream = new BufferedInputStream(new FileInputStream(
                        resLanFileName));
            } else {
                inputStream = new BufferedInputStream(new FileInputStream(path
                        + File.separator + resLanFileName));
            }
            prop.load(inputStream);
        } catch (IOException ex) {
            // If error, re-try default China Locale
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(ex, resLanFileName));
            resLanFileName = genResLanFileName(resFileName, Locale.CHINA);
            if (ServiceEntityStringHelper.checkNullString(path)) {
                inputStream = new BufferedInputStream(new FileInputStream(
                        resLanFileName));
            } else {
                inputStream = new BufferedInputStream(new FileInputStream(path
                        + File.separator + resLanFileName));
            }
            prop.load(inputStream);
        }
        for (Entry<Object, Object> entry : prop.entrySet()) {
            String keyString = (String) entry.getKey();
            String value = (String) entry.getValue();
            valueMap.put(keyString, value);
        }
        return valueMap;
    }

    /**
     * generate the drop down list hash map<String,String> by setting the path
     * and locale manually
     *
     * @param resFileName : FULL properties file name without language post fix
     * @return
     * @throws IOException
     */
    public Map<String, String> getDropDownMap(String path, String resFileName,
                                              String rawLanCode) throws IOException {
        Locale locale = ServiceLanHelper.parseToLocale(rawLanCode);
        Map<String, String> valueMap = new HashMap<>();
        // Check if the resource file exist or not
        String resLanFileName = resFileName + "_" + locale.getLanguage() + "_"
                + locale.getCountry() + ".properties";
        Properties prop = new Properties();
        InputStream inputStream = null;
        if (ServiceEntityStringHelper.checkNullString(path)) {
            inputStream = new BufferedInputStream(new FileInputStream(
                    resLanFileName));
        } else {
            inputStream = new BufferedInputStream(new FileInputStream(path
                    + File.separator + resLanFileName));
        }
        prop.load(inputStream);
        for (Entry<Object, Object> entry : prop.entrySet()) {
            String keyString = (String) entry.getKey();
            String value = (String) entry.getValue();
            valueMap.put(keyString, value);
        }
        return valueMap;
    }

    /**
     * generate the drop down list hash map<String,String> by setting the path
     * and locale manually
     *
     * @param resFileName : FULL properties file name without language post fix
     * @return
     * @throws IOException
     */
    public Map<Integer, String> getDropDownMapInteger(String path,
                                                      String resFileName, Locale locale) throws IOException {
        Map<Integer, String> valueMap = new HashMap<>();
        // Check if the resource file exist or not
        String resLanFileName = resFileName + "_" + locale.getLanguage() + "_"
                + locale.getCountry() + ".properties";
        Properties prop = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(
                path + File.separator + resLanFileName));
        prop.load(inputStream);
        for (Entry<Object, Object> entry : prop.entrySet()) {
            String keyString = (String) entry.getKey();
            Integer key = Integer.valueOf(keyString);
            String value = (String) entry.getValue();
            valueMap.put(key, value);
        }
        return valueMap;
    }

}
