package com.company.IntelligentPlatform.common.dto;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceMessageHelper;

@Service
public class RoleMessageHelper extends ServiceMessageHelper {

	public static final String messageResourceName = "RoleNote";
	
	/**
	 * Get Error message by error code
	 * 
	 * @param exceptionCls
	 * @param errorCode
	 * @return
	 * @throws ServiceEntityInstallationException
	 * @throws IOException
	 */
	public String getNote(String roleID)
			throws ServiceEntityInstallationException, IOException {
		String path = RoleMessageHelper.class.getResource("").getPath();
		String resFileFullName = path + messageResourceName;
		Map<String, String> noteMap = this.getStrDropDownMap(resFileFullName);
		return noteMap.get(roleID);
	}

}
