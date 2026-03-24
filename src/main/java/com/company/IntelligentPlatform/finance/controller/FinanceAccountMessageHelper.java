package com.company.IntelligentPlatform.finance.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceMessageHelper;

@Service
public class FinanceAccountMessageHelper extends ServiceMessageHelper {

	public static final String messageResourceName = "FinanceAccountMessage";

	public static final int TITLE_GEN_FIN_CHART = 1;
	
	public static final int SUBTITLE_GEN_FIN_CHART = 2;
	
	public static final int YAIXSLABLE_GEN_FIN = 3;
	
	public static final int LABEL_AUDIT = 4;
	
	public static final int LABEL_VERIFY = 5;
	
	public static final int LABEL_RECORD = 6;
	
	/**
	 * Get Error message by error code
	 * 
	 * @param exceptionCls
	 * @param errorCode
	 * @return
	 * @throws ServiceEntityInstallationException
	 * @throws IOException
	 */
	public String getMessage(int errorCode)
			throws ServiceEntityInstallationException, IOException {
		String path = FinanceAccountMessageHelper.class.getResource("").getPath();
		String resFileFullName = path + messageResourceName;
		Map<Integer, String> errorMSGMap = getDropDownMap(resFileFullName);
		return errorMSGMap.get(errorCode);
	}

}
