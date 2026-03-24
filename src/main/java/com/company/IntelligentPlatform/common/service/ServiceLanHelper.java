package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ServiceLanHelper {

    protected Map<String, String> lanMap;

    protected Map<String, Map<String, String>> languageMapLan = new HashMap<>();

    /**
     * Core Logic in system to return the default locale
     *
     */
    public static Locale getDefault() {
        return Locale.CHINA;
    }

    public Map<String, String> getLanguageMap(String languageCode)
            throws ServiceEntityInstallationException {
        String resourcePath = this.getClass().getResource("").getPath() + "SystemLanguage";
        return ServiceLanHelper.initDefLanguageStrMapResource(languageCode,
                this.languageMapLan, resourcePath);
    }

    /**
     * Core Logic in system to return default locale array
     *
     */
    public static List<Locale> getDefLanList() {
        return ServiceCollectionsHelper.asList(Locale.CHINA, Locale.US);
    }

    public static Locale parseToLocale(String rawLanCode) {
        if (ServiceEntityStringHelper.checkNullString(rawLanCode)) {
            return null;
        }
        String[] lanArray = rawLanCode.split("_");
        String lanCode = lanArray[0];
        if (lanArray.length > 1) {
            String countryCode = lanArray[1];
            return new Locale(lanCode, countryCode);
        } else {
            if (Locale.CHINA.getLanguage().equals(lanCode)) {
                return Locale.CHINA;
            }
            if (Locale.US.getLanguage().equals(lanCode)) {
                return Locale.US;
            }
            return new Locale(lanCode);
        }
    }

    /**
     * Utility method to initial and return the default Map with language key
     *
     */
    public static Map<Integer, String> initDefLanguageMapUIModel(
            String languageCode, Map<String, Map<Integer, String>> rawLanMap,
            Class<?> UIModelClass, String fieldName)
            throws ServiceEntityInstallationException {
        if (rawLanMap == null) {
            rawLanMap = new HashMap<>();
        }
        return ServiceLanHelper
                .initDefaultLanguageMap(
                        languageCode,
                        rawLanMap,
                        lanCode -> {
                            try {
                                String defResFileName = buildDefaultResFile(UIModelClass, fieldName);
                                if (ServiceDropdownListHelper.existsResLanResFile(defResFileName, lanCode)) {
                                    try {
                                        return ServiceDropdownListHelper
                                                .getStaticDropDownMap(defResFileName, lanCode);
                                    } catch (IOException e) {
                                        // do nothing
                                    }
                                }
                                return ServiceDropdownListHelper
                                        .getStaticUIDropDownMap(UIModelClass,
                                                fieldName, lanCode);
                            } catch (ServiceEntityInstallationException e) {
                                return null;
                            }
                        });
    }

    public static String buildDefaultResFile(Class<?> UIModelClass, String fieldName) {
        String uiModelPath = UIModelClass.getResource("").getPath();
        String defProperty = ServiceEntityStringHelper.removePostfix(UIModelClass.getSimpleName(), "UIModel") + "_" + fieldName;
        return uiModelPath + defProperty;
    }

    /**
     * Utility method to initial and return the default Map with language key
     *
     */
    public static Map<String, String> initDefLanguageStrMapUIModel(
            String languageCode, Map<String, Map<String, String>> rawLanMap,
            Class<?> UIModelClass, String fieldName)
            throws ServiceEntityInstallationException {
        if (rawLanMap == null) {
            rawLanMap = new HashMap<>();
        }
        return ServiceLanHelper
                .initDefaultLanguageStrMap(
                        languageCode,
                        rawLanMap,
                        lanCode -> {
                            try {
                                String defResFileName = buildDefaultResFile(UIModelClass, fieldName);
                                if (ServiceDropdownListHelper.existsResLanResFile(defResFileName, lanCode)) {
                                    try {
                                        return ServiceDropdownListHelper
                                                .getStrStaticDropDownMap(defResFileName, lanCode);
                                    } catch (IOException e) {
                                        // do nothing
                                    }
                                }
                                return ServiceDropdownListHelper
                                        .getStaticUIStrDropDownMap(UIModelClass,
                                                fieldName, lanCode);
                            } catch (ServiceEntityInstallationException e) {
                                return null;
                            }
                        });
    }

    /**
     * Utility method to initial and return the default Map with language key
     *
     */
    public static Map<Integer, String> initDefLanguageMapResource(
            String languageCode, Map<String, Map<Integer, String>> rawLanMap,
            String resourcePath)
            throws ServiceEntityInstallationException {
        if (rawLanMap == null) {
            rawLanMap = new HashMap<>();
        }
        return ServiceLanHelper
                .initDefaultLanguageMap(
                        languageCode,
                        rawLanMap,
                        lanCode -> {
                            try {
                                return ServiceDropdownListHelper.getStaticDropDownMap(resourcePath,
                                                lanCode);
                            } catch (IOException e) {
                                return null;
                            }
                        });
    }


    /**
     * Utility method to initial and return the default Map with language key
     *
     */
    public static Map<Integer, String> initDefaultLanguageMap(
            String languageCode, Map<String, Map<Integer, String>> rawLanMap,
            Function<String, Map<Integer, String>> setLanMapCallback)
            throws ServiceEntityInstallationException {
        String localLanCode = (languageCode != null) ? languageCode : Locale
                .getDefault().getLanguage();
        if (rawLanMap != null && rawLanMap.containsKey(localLanCode)
                && rawLanMap.get(localLanCode) != null) {
            return rawLanMap.get(localLanCode);
        } else {
            Map<Integer, String> tmpMap = setLanMapCallback.apply(localLanCode);
            if (tmpMap != null) {
                rawLanMap.put(languageCode, tmpMap);
            }
            return tmpMap;
        }
    }

    /**
     * Utility method to initial and return the default Map with language key
     *
     */
    public static Map<String, String> initDefaultLanguageStrMap(
            String languageCode, Map<String, Map<String, String>> rawLanMap,
            Function<String, Map<String, String>> setLanMapCallback) {
        String localLanCode = (languageCode != null) ? languageCode : ServiceLanHelper
                .getDefault().getLanguage();
        if (rawLanMap != null && rawLanMap.containsKey(localLanCode)
                && rawLanMap.get(localLanCode) != null) {
            return rawLanMap.get(localLanCode);
        } else {
            Map<String, String> tmpMap = setLanMapCallback.apply(localLanCode);
            if (tmpMap != null) {
                rawLanMap.put(languageCode, tmpMap);
            }
            return tmpMap;
        }
    }

    /**
     * Utility method to initial and return the default Map with language key
     *
     */
    public static Map<String, String> initDefLanguageStrMapResource(
            String languageCode, Map<String, Map<String, String>> rawLanMap,
            String resourcePath) {
        if (rawLanMap == null) {
            rawLanMap = new HashMap<>();
        }
        return ServiceLanHelper
                .initDefaultLanguageStrMap(
                        languageCode,
                        rawLanMap,
                        lanCode -> {
                            try {
                                return ServiceDropdownListHelper.getStrStaticDropDownMap(resourcePath,
                                                lanCode);
                            } catch (IOException e) {
                                return null;
                            }
                        });
    }


    /**
     * Get standard String format key for Locale
     *
     */
    public static String getLocaleKeyStr(Locale locale) {
        return locale.getLanguage() + "_"
                + locale.getCountry();
    }

    public Map<String, String> getLanMap() {
        this.lanMap = new HashMap<>();
        this.lanMap.put(Locale.CHINA.getLanguage(), Locale.CHINA.getLanguage());
        this.lanMap.put(Locale.US.getLanguage(), Locale.US.getLanguage());
        this.lanMap.put(Locale.JAPAN.getLanguage(), Locale.JAPAN.getLanguage());
        this.lanMap.put(Locale.GERMANY.getLanguage(),
                Locale.GERMANY.getLanguage());
        return this.lanMap;
    }

    /**
     * Format and trim the language code: E.g: 'zh-CN''zh_CN' return 'zh'; 'zh' return
     * 'zh'.
     */
    public static String formatLanCode(String languageTag) {
        if (ServiceEntityStringHelper.checkNullString(languageTag)) {
            return null;
        }
        Locale locale = Locale.forLanguageTag(languageTag);
        if (languageTag.contains("_")) {
            locale = parseToLocale(languageTag);
        }
        return locale.getLanguage();
    }

}
