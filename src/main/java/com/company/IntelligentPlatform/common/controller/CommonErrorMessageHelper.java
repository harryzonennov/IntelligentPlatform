package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceMessageHelper;

@Service
public class CommonErrorMessageHelper extends ServiceMessageHelper {

	public static final String messageResourceName = "CommonError";

	public static final String MSG_INSTRUCTION_TO_USER = "warn_userInstruction";
	
	public static final String TITLE_ERROR_CONTENT = "label_errorContent";
	
	public static final String TITLE_ERROR_HAPPEN = "warn_errorHappen";
	
	public static final String TYPE_NON_IMPLEMENT_FUNCTION = "non_implement_function";
	
	public static final String MSG_IOEXCEPTION = "找不到通用错误消息配置文件，Can not get the commen error properties file";

	/**
	 * Get Error message by error code
	 * 
	 * @param exceptionCls
	 * @param errorCode
	 * @return
	 * @throws ServiceEntityInstallationException
	 * @throws IOException
	 */
	public String getMessage(String errorCode){
		// Pay attention: using class [SEUIComModel] to point to resource
		// path:[CommonError], which might cause problem if class SEUIComModel
		// is changed or moved
		try{
			String path = SEUIComModel.class.getResource("").getPath();
			String resFileFullName = path + messageResourceName;
			Map<String, String> errorMSGMap = getStrDropDownMap(resFileFullName);
			return errorMSGMap.get(errorCode);
		}catch(IOException ex){
			return MSG_IOEXCEPTION;
		}
		
	}

}
