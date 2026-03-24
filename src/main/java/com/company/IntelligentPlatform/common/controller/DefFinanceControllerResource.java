package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

@Service
public class DefFinanceControllerResource {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	public static final String PROPERTIES_RESOURCE = "StandardProcessCode";

	public static final int PROCESS_CODE_SAVE = 1;

	public static final int PROCESS_CODE_RELEASEFIN = 2;

	public static final int PROCESS_CODE_CANCEL = 3;

	public static final int PROCESS_CODE_DELETE = 4;

	protected Map<Integer, String> standardProcessCodeMap;

	public Map<Integer, String> getStandardProcessCodeMap()
			throws ServiceEntityInstallationException {
		if (this.standardProcessCodeMap == null) {
			try {
				String path = this.getClass().getResource("").getPath();
				this.standardProcessCodeMap = serviceDropdownListHelper
						.getDropDownMap(path + PROPERTIES_RESOURCE);
			} catch (IOException e) {
				throw new ServiceEntityInstallationException(ServiceEntityInstallationException.PARA_SYSTEM_WRONG,
						e.getMessage());
			}
		}
		return this.standardProcessCodeMap;
	}

}
