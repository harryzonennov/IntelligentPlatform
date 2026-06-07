package com.company.IntelligentPlatform.platform.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

import com.company.IntelligentPlatform.platform.service.DocActionException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.ServiceLanHelper;
import com.company.IntelligentPlatform.platform.model.SimpleSEMessageResponse;
import com.company.IntelligentPlatform.platform.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

public class ServiceExceptionHelper {

	public static Map<Integer, String> getExceptionClassDropDownMap(
			Class<?> exceptionCls) throws ServiceEntityInstallationException {
		try {
			return getDropDownMap(exceptionCls, exceptionCls.getSimpleName());
		} catch (IOException ex) {
			throw new ServiceEntityInstallationException(
					ServiceEntityInstallationException.PARA_CONFIG_DROPDOWN,
					exceptionCls.getSimpleName());
		} catch (SecurityException e) {
			throw new ServiceEntityInstallationException(
					ServiceEntityInstallationException.PARA_CONFIG_DROPDOWN,
					exceptionCls.getSimpleName());
		}
	}

	/**
	 * generate the drop down list hash map in multi-language environment
	 * Loads the properties file as a classpath resource relative to exceptionCls,
	 * so it works correctly inside a Spring Boot fat jar.
	 *
	 * @param exceptionCls the exception class whose package contains the resource
	 * @param resFileName  the base file name (no locale suffix, no extension)
	 * @return
	 * @throws IOException
	 */
	public static Map<Integer, String> getDropDownMap(Class<?> exceptionCls, String resFileName)
			throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String resourceName = resFileName + "_" + ServiceLanHelper.getLocaleKeyStr(locale) + ".properties";
		Properties prop = new Properties();
		try (InputStream inputStream = exceptionCls.getResourceAsStream(resourceName)) {
			if (inputStream == null) {
				throw new IOException("Classpath resource not found: " + resourceName + " (relative to " + exceptionCls.getName() + ")");
			}
			prop.load(inputStream);
		}
		Map<Integer, String> valueMap = new HashMap<Integer, String>();
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
	 * @deprecated Use {@link #getDropDownMap(Class, String)} instead.
	 * Legacy file-system based variant kept for binary compatibility.
	 */
	@Deprecated
	public static Map<Integer, String> getDropDownMap(String resFileName)
			throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		Map<Integer, String> valueMap = new HashMap<Integer, String>();
		String resLanFileName = resFileName + "_" + ServiceLanHelper.getLocaleKeyStr(locale) + ".properties";
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
	 * Get Error message by error code
	 * 
	 * @param exceptionCls
	 * @param errorCode
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public static String getErrorMessage(Class<?> exceptionCls, int errorCode)
			throws ServiceEntityInstallationException {
		Map<Integer, String> errorMSGMap = getExceptionClassDropDownMap(exceptionCls);
		return errorMSGMap.get(errorCode);
	}

	public static SimpleSEMessageResponse handleSimpleMessageResponse(
			SimpleSEMessageResponse simpleSEMessageResponse, Class<?> exceptionCls, int errorCode) throws ServiceEntityInstallationException {
		String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
				exceptionCls, errorCode);
		if (simpleSEMessageResponse.getErrorParas() != null) {
			String[] errorParas = simpleSEMessageResponse.getErrorParas();
			if (errorParas == null || errorParas.length == 0) {
				simpleSEMessageResponse.setMessageContent(errorMesTamplate);
			}
			if (errorParas.length == 1) {
				simpleSEMessageResponse.setMessageContent(String.format(errorMesTamplate, errorParas[0]));
			}
			if (errorParas.length == 2) {
				simpleSEMessageResponse.setMessageContent(String.format(errorMesTamplate, errorParas[0], errorParas[1]));
			}
		} else {
			String varStr = simpleSEMessageResponse.getMessageContent();
			simpleSEMessageResponse.setMessageContent(String.format(errorMesTamplate, varStr));
		}
		return simpleSEMessageResponse;
	}

	public static List<SimpleSEMessageResponse> handleErrorMessageMap(Map<String, List<SimpleSEMessageResponse>> errorMessageMap, Class<?> exceptionCls, int errorCode) {
		if (errorMessageMap == null) {
			return null;
		}
		List<SimpleSEMessageResponse> resultMessageResponseList = new ArrayList<>();
		errorMessageMap.forEach((key, messageResponseList) -> {
			try {
				ServiceCollectionsHelper.traverseListInterrupt(messageResponseList, simpleSEMessageResponse -> {
					// In error message map, set key as 1st para:
					try {
						String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(exceptionCls, errorCode);
						if (simpleSEMessageResponse.getErrorParas() != null) {
							String[] errorParas = simpleSEMessageResponse.getErrorParas();
							if (errorParas == null || errorParas.length == 0) {
								simpleSEMessageResponse.setMessageContent(errorMesTamplate);
							}
							if (errorParas.length == 1) {
								simpleSEMessageResponse.setMessageContent(String.format(errorMesTamplate, key, errorParas[0]));
							}
							if (errorParas.length == 2) {
								simpleSEMessageResponse.setMessageContent(String.format(errorMesTamplate, key, errorParas[0], errorParas[1]));
							}
						} else {
							String varStr = simpleSEMessageResponse.getMessageContent();
							simpleSEMessageResponse.setMessageContent(String.format(errorMesTamplate, varStr));
						}
						resultMessageResponseList.add(simpleSEMessageResponse);
						return true;
					} catch (ServiceEntityInstallationException e) {
						throw new RuntimeException(e);
					}
				});
			} catch (DocActionException e) {
				throw new RuntimeException(e);
			}
		});
		return resultMessageResponseList;
	}

	/**
	 * Get Error message by error code
	 *
	 * @param exceptionCls
	 * @param errorCode
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public static String getErrorMessageWrapper(Class<?> exceptionCls, int errorCode, Object var)
			throws ServiceEntityInstallationException {
		if (var != null && !var.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
					exceptionCls, errorCode);
			return String.format(errorMesTamplate, var);
		} else {
			return ServiceExceptionHelper.getErrorMessage(
					DocActionException.class, errorCode);
		}
	}

}
