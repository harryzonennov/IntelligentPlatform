package com.company.IntelligentPlatform.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.service.ServiceEntityException;
import com.company.IntelligentPlatform.platform.service.ServiceExceptionRecordManager;
import com.company.IntelligentPlatform.platform.model.LogonUser;
import com.company.IntelligentPlatform.platform.model.Organization;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

@Service
public class ServiceDefaultExceptionRecordHelper {

	@Autowired
	protected ServiceExceptionRecordManager serviceExceptionRecordManager;

	public void autoRecordServiceException(ServiceEntityException ex,
			String note, String source, LogonUser logonUser, Organization organization) throws LogonInfoException,
			ServiceEntityConfigureException {
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		String orgUUID = ServiceEntityStringHelper.EMPTYSTRING;
		if (organization != null) {
			orgUUID = organization.getUuid();
		}
		String exceptionName = ex.getClass().getSimpleName();
		if (note == null) {
			note = ServiceEntityStringHelper.EMPTYSTRING;
		}
		note = note + "\n[Exception Name]" + ex.getClass().getName();
		note = note + "\n[System exception message]" + ex.getErrorMessage();
		int category = ex.getExceptionCategory(ex.getErrorCode());
		serviceExceptionRecordManager.autoRecord(exceptionName, category, note,
				source, logonUser.getUuid(), orgUUID, ex.getStackTrace());

	}

}
