package com.company.IntelligentPlatform.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

// TODO-LEGACY: import platform.foundation.Administration.InstallService.ServiceEntityInstallConstantsHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallConstantsHelper; // migrated from legacy platform.foundation
import com.company.IntelligentPlatform.common.controller.SEUIModelFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Generator class for generate International/Locallization resouce file from UI
 * model
 * 
 * @author Zhang,Hang
 * @date 2012-12-27
 * 
 */
public class UIResourceGenerator {

	public static final String DEFAULT_FILE_PRENAME = "defaultUIResources";

	public static final String KEY_BUTTON_NEW = "button.new";

	public static final String KEY_BUTTON_EDIT = "button.edit";

	public static final String KEY_BUTTON_VIEW = "button.view";

	public static final String KEY_BUTTON_DISPLAY = "button.display";

	public static final String KEY_LISTVIEW_TITLE = "listView.title";

	public static final String KEY_EDITVIEW_TITLE = "editView.title";

	public static void genResoucePropertiesFile(Class<?> seUIClass,
			Locale locale, boolean overFlag, String seName) throws IOException {
		String fileName = seName + getDefLocalFileNamePost(locale)
				+ ".properties";
		String path = ServiceEntityInstallConstantsHelper
				.getSrcAnyClassPath(UIResourceGenerator.class);
		File file = new File(path + File.separator + fileName);
		if (file.isFile()) {
			if (overFlag) {
				file = new File(file.getAbsolutePath());
			} else {
				return;
			}
		}
		Properties properties = genResouceProperties(seUIClass, locale);
		FileOutputStream output = new FileOutputStream(file);
		properties.store(output, null);
		output.close();
	}

	public static Properties genResouceProperties(Class<?> seUIClass,
			Locale locale) {
		Properties properties = new Properties();
		Properties defProperties = loadDefaultUIResourceProperties(locale);
		List<Field> fieldList = SEUIModelFieldsHelper.getFieldsList(seUIClass);
		for (Field field : fieldList) {
			String defValue = getDefaultValue(field.getName(), defProperties);
			properties.setProperty(field.getName(), defValue);
		}
		properties.setProperty(KEY_BUTTON_NEW,
				getDefaultValue(KEY_BUTTON_NEW, defProperties));
		properties.setProperty(KEY_BUTTON_EDIT,
				getDefaultValue(KEY_BUTTON_EDIT, defProperties));
		properties.setProperty(KEY_BUTTON_VIEW,
				getDefaultValue(KEY_BUTTON_VIEW, defProperties));
		properties.setProperty(KEY_BUTTON_DISPLAY,
				getDefaultValue(KEY_BUTTON_DISPLAY, defProperties));
		properties.setProperty(KEY_LISTVIEW_TITLE,
				getDefaultValue(KEY_LISTVIEW_TITLE, defProperties));
		properties.setProperty(KEY_EDITVIEW_TITLE,
				getDefaultValue(KEY_EDITVIEW_TITLE, defProperties));
		return properties;
	}

	/**
	 * Get default resource file name postfix by localization setting
	 * 
	 * @param locale
	 * @return
	 */
	public static String getDefLocalFileNamePost(Locale locale) {
		String postFix = "_" + locale.getLanguage() + "_" + locale.getCountry();
		if (locale.getLanguage().equals(Locale.ENGLISH.getLanguage()))
			postFix = ServiceEntityStringHelper.EMPTYSTRING;
		return postFix;
	}

	public static Properties loadDefaultUIResourceProperties(Locale locale) {
		String postFix = getDefLocalFileNamePost(locale);
		String fileNameString = DEFAULT_FILE_PRENAME + postFix + ".properties";
		// Default resources file should be located in the same path with this
		// class
		String path;
		path = ServiceEntityInstallConstantsHelper
				.getSrcAnyClassPath(UIResourceGenerator.class);
		InputStream input = null;
		try {
			File f = new File(path + File.separator + fileNameString);
			input = new FileInputStream(f);
			Properties properties = new Properties();
			properties.load(input);
			if (properties.isEmpty()) {
				// should raise exception
				return null;
			}
			input.close();
			return properties;
		} catch (IOException ex) {
			return null;
		}
	}

	protected static String getDefaultValue(String fieldName,
			Properties defProperties) {
		if (defProperties == null)
			return fieldName;
		if (defProperties.containsKey(fieldName)) {
			return defProperties.getProperty(fieldName);
		} else {
			return ServiceEntityStringHelper.headerToUpperCase(fieldName);
		}
	}

}
