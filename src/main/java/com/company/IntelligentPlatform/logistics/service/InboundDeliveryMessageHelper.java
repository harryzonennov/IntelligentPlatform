package com.company.IntelligentPlatform.logistics.service;

import java.io.IOException;
import java.util.Map;
import java.util.MissingFormatArgumentException;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceMessageHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class InboundDeliveryMessageHelper extends ServiceMessageHelper {

	public static final String messageResourceName = "InboundDeliveryMessage";
	
	public static final int COMMENT_APPROVE_TITLE = 1;
	
	public static final int COMMENT_APPROVE_CONTENT = 2;
	
	public static final int COMMENT_RECORDSTORE_TITLE = 3;

	public static final int COMMENT_RECORDSTORE_CONTENT = 4;
	
	public static final int REPORT_TITLE = 5;

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
		try {
			String path = InboundDeliveryMessageHelper.class.getResource("")
					.getPath();
			String resFileFullName = path + messageResourceName;
			Map<Integer, String> errorMSGMap = getDropDownMap(resFileFullName);
			return errorMSGMap.get(errorCode);
		} catch (java.util.MissingFormatArgumentException ex) {
			return null;
		}
	}

	public String getMessage(int errorCode, Object var) throws IOException {
		try {
			String path = InboundDeliveryMessageHelper.class.getResource("")
					.getPath();
			String resFileFullName = path + messageResourceName;
			Map<Integer, String> errorMSGMap = getDropDownMap(resFileFullName);
			String errorMesTamplate = errorMSGMap.get(errorCode);
			String errorMessage = errorMesTamplate;
			if (var != null
					& !var.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
				errorMessage = String.format(errorMesTamplate, var.toString());
			}
			return errorMessage;
		} catch (MissingFormatArgumentException ex) {
			return null;
		}
	}

	public String getMessage(int errorCode, Object var1, Object var2)
			throws IOException {
		try {
			String path = InboundDeliveryMessageHelper.class.getResource("")
					.getPath();
			String resFileFullName = path + messageResourceName;
			Map<Integer, String> errorMSGMap = getDropDownMap(resFileFullName);
			String errorMesTamplate = errorMSGMap.get(errorCode);
			String errorMessage = errorMesTamplate;
			if (var1 == null) {
				return errorMessage;
			}
			//errorMessage = String.format(errorMesTamplate, var1.toString());
			if (var2 == null) {
				return errorMessage;
			}
			errorMessage = String.format(errorMesTamplate, var1.toString(),
					var2.toString());
			return errorMessage;
		} catch (MissingFormatArgumentException ex) {
			return null;
		}
	}

	public String getMessage(int errorCode, Object var1, Object var2,
			Object var3) throws IOException {
		try {
			String path = InboundDeliveryMessageHelper.class.getResource("")
					.getPath();
			String resFileFullName = path + messageResourceName;
			Map<Integer, String> errorMSGMap = getDropDownMap(resFileFullName);
			String errorMesTamplate = errorMSGMap.get(errorCode);
			String errorMessage = errorMesTamplate;
			if (var1 == null) {
				return errorMessage;
			}
			//errorMessage = String.format(errorMesTamplate, var1.toString());
			if (var2 == null) {
				return errorMessage;
			}
			//errorMessage = String.format(errorMesTamplate, var1.toString(),var2.toString());
			if (var3 == null) {
				return errorMessage;
			}
			errorMessage = String.format(errorMesTamplate, var1.toString(),
					var2.toString(), var3.toString());
			return errorMessage;
		} catch (MissingFormatArgumentException ex) {
			return null;
		}
	}

}
