package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.ServiceExceptionRecordUIModel;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceExceptionRecordManager;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceExceptionRecord;
import com.company.IntelligentPlatform.common.model.LogonUser;

@Scope("session")
@Controller(value = "serviceExceptionRecordEditorController")
@RequestMapping(value = "/serviceExceptionRecord")
public class ServiceExceptionRecordEditorController extends SEEditorController {

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected ServiceExceptionRecordManager serviceExceptionRecordManager;
	
	@Autowired
	protected LogonActionController logonActionController;

	public ServiceExceptionRecordEditorController() {
	}


	protected void saveInternal(
			ServiceExceptionRecordUIModel serviceExceptionRecordUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		// Convert UI Model to SE entity list
		String baseUUID = serviceExceptionRecordUIModel.getUuid();
		ServiceExceptionRecord serviceExceptionRecord = (ServiceExceptionRecord) getServiceEntityNodeFromBuffer(
				ServiceExceptionRecord.NODENAME, baseUUID);
		convUIToServiceExceptionRecord(serviceExceptionRecord,
				serviceExceptionRecordUIModel);
		this.save(baseUUID, serviceExceptionRecordManager, logonActionController.getLogonUser(), logonActionController.getOrganization());
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 * 
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String preLockModule(String uuid) {
		try {
			ServiceExceptionRecord serviceExceptionRecord = (ServiceExceptionRecord) serviceExceptionRecordManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceExceptionRecord.NODENAME, null);
			String baseUUID = serviceExceptionRecord.getUuid();
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(serviceExceptionRecord);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					serviceExceptionRecord.getName(),
					serviceExceptionRecord.getId(), baseUUID);
		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getErrorMessage());
		}
	}

	protected void convServiceExceptionRecordToUI(
			ServiceExceptionRecord serviceExceptionRecord,
			ServiceExceptionRecordUIModel serviceExceptionRecordUIModel) {
		serviceExceptionRecordUIModel.setUuid(serviceExceptionRecord.getUuid());
		serviceExceptionRecordUIModel.setId(serviceExceptionRecord.getId());
		serviceExceptionRecordUIModel.setName(serviceExceptionRecord.getName());
		serviceExceptionRecordUIModel.setNote(serviceExceptionRecord.getNote());
		if (serviceExceptionRecord.getCreatedTime() != null
				&& !serviceExceptionRecord.getCreatedTime().equals(
						ServiceEntityStringHelper.EMPTYSTRING)) {
			serviceExceptionRecordUIModel
					.setCreatedTimeValue(DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(java.util.Date.from(serviceExceptionRecord.getCreatedTime().atZone(java.time.ZoneId.systemDefault()).toInstant())));
		}
		if (serviceExceptionRecord.getCreatedTime() != null) {
			serviceExceptionRecordUIModel.setCreatedTime(
				java.util.Date.from(serviceExceptionRecord.getCreatedTime().atZone(java.time.ZoneId.systemDefault()).toInstant()));
		}
		if (serviceExceptionRecord.getLastUpdateTime() != null
				&& !serviceExceptionRecord.getLastUpdateTime().equals(
						ServiceEntityStringHelper.EMPTYSTRING)) {
			serviceExceptionRecordUIModel
					.setLastUpdateTimeValue(DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(java.util.Date.from(serviceExceptionRecord.getLastUpdateTime().atZone(java.time.ZoneId.systemDefault()).toInstant())));
		}
		if (serviceExceptionRecord.getLastUpdateTime() != null) {
			serviceExceptionRecordUIModel.setLastUpdateTime(
				java.util.Date.from(serviceExceptionRecord.getLastUpdateTime().atZone(java.time.ZoneId.systemDefault()).toInstant()));
		}
		serviceExceptionRecordUIModel.setCallStack(serviceExceptionRecord
				.getCallStack());
		serviceExceptionRecordUIModel.setSource(serviceExceptionRecord
				.getSource());
		serviceExceptionRecordUIModel.setProcessorUUID(serviceExceptionRecord
				.getProcessorUUID());
		serviceExceptionRecordUIModel.setReporterUUID(serviceExceptionRecord
				.getReporterUUID());
		serviceExceptionRecordUIModel.setProcessorName(serviceExceptionRecord
				.getProcessorName());
		serviceExceptionRecordUIModel.setReporterName(serviceExceptionRecord
				.getReporterName());
		serviceExceptionRecordUIModel.setCategory(serviceExceptionRecord
				.getCategory());
		serviceExceptionRecordUIModel.setPriority(serviceExceptionRecord
				.getPriority());
		serviceExceptionRecordUIModel.setStatus(serviceExceptionRecord
				.getStatus());
		serviceExceptionRecordUIModel.setSourceType(serviceExceptionRecord
				.getSourceType());
	}

	protected void convUIToServiceExceptionRecord(
			ServiceExceptionRecord rawEntity,
			ServiceExceptionRecordUIModel serviceExceptionRecordUIModel) {
		rawEntity.setUuid(serviceExceptionRecordUIModel.getUuid());
		rawEntity.setId(serviceExceptionRecordUIModel.getId());
		rawEntity.setName(serviceExceptionRecordUIModel.getName());
		rawEntity.setNote(serviceExceptionRecordUIModel.getNote());
		if (serviceExceptionRecordUIModel.getLastUpdateTime() != null) {
			rawEntity.setLastUpdateTime(serviceExceptionRecordUIModel.getLastUpdateTime()
				.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
		}
		rawEntity.setCallStack(serviceExceptionRecordUIModel.getCallStack());
		rawEntity.setSource(serviceExceptionRecordUIModel.getSource());
		rawEntity.setProcessorUUID(serviceExceptionRecordUIModel
				.getProcessorUUID());
		rawEntity.setReporterUUID(serviceExceptionRecordUIModel
				.getReporterUUID());
		rawEntity.setProcessorName(serviceExceptionRecordUIModel
				.getProcessorName());
		rawEntity.setReporterName(serviceExceptionRecordUIModel
				.getReporterName());
		rawEntity.setCategory(serviceExceptionRecordUIModel.getCategory());
		rawEntity.setPriority(serviceExceptionRecordUIModel.getPriority());
		rawEntity.setStatus(serviceExceptionRecordUIModel.getStatus());
		rawEntity.setSourceType(serviceExceptionRecordUIModel.getSourceType());
	}

	@RequestMapping(value = "/exitEditor")
	public void exitEditor(String uuid) {
		try {
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			List<ServiceEntityNode> serviceExceptionRecordList = this
					.getSEListNodeFromBuffer(ServiceExceptionRecord.NODENAME,
							uuid);
			if (serviceExceptionRecordList != null
					&& serviceExceptionRecordList.size() > 0) {
				lockSEList.addAll(serviceExceptionRecordList);
				lockObjectManager.unLockServiceEntityList(lockSEList);
			}
			this.clearBuffer(uuid);
		} catch (ServiceEntityConfigureException e) {
			// do nothing?
		}
	}

}
