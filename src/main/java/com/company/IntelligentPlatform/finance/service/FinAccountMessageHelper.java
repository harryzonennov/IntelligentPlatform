package com.company.IntelligentPlatform.finance.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceMessageHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class FinAccountMessageHelper extends ServiceMessageHelper {

	public static final String messageResourceName = "FinAccountMessage";

	public static final int MSG_AUDIT_DONE = 1;

	public static final int MSG_VERIFY_DONE = 2;

	public static final int MSG_RECORD_DONE = 3;

	public static final int MSG_CANNOT_VERIFY_BK_NOTPICKUP = 4;

	public static final int MSG_CANNOT_RECORD_NOT_VERIFY = 5;

	public static final int MSG_CANNOT_VERIFY_NOT_AUDIT = 6;

	public static final int MSG_CANNOT_VERIFY_PRE_NOT_RECORD = 7;
	
	public static final int TITLE_REP_VOUCHER = 8;
	
	public static final int TITLE_REP_RECEIPT = 9;
	

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
			throws ServiceEntityInstallationException {
		String resFileFullName = ServiceEntityStringHelper.EMPTYSTRING;
		try {
			String path = FinAccountMessageHelper.class.getResource("")
					.getPath();
			resFileFullName = path + messageResourceName;
			Map<Integer, String> errorMSGMap = getDropDownMap(resFileFullName);
			return errorMSGMap.get(errorCode);
		} catch (IOException ex) {
			throw new ServiceEntityInstallationException(
					ServiceEntityInstallationException.PARA_FIELD_CONFIG_DROPDOWN,
					resFileFullName);
		}
	}

}
