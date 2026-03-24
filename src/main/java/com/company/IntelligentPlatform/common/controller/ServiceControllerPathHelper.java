package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class ServiceControllerPathHelper {

	/**
	 * In case the HTTP parameter with wrong postfix, then delete it
	 * 
	 * @param para
	 * @param errorCode
	 * @return
	 */
	public static String filterParaWithWrongPostfix(String para,
			String errorCode) {
		String content = para;
		if (para == null || para == ServiceEntityStringHelper.EMPTYSTRING)
			return content;
		String postfix = content.substring(content.lastIndexOf(errorCode));
		if (postfix.equals(errorCode))
			return content.substring(0, content.lastIndexOf(errorCode));
		else
			return content;
	}

}
