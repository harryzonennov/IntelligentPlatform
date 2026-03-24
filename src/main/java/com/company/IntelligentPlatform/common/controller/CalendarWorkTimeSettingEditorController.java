package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.DoubleEditor;
import com.company.IntelligentPlatform.common.controller.IntegerEditor;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.dto.CalendarWorkTimeSettingUIModel;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.CalendarSettingManager;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.CalendarWorkTimeSetting;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "calendarWorkTimeSettingEditorController")
@RequestMapping(value = "/calendarWorkTimeSetting")
public class CalendarWorkTimeSettingEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_ORGANIZATION;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected CalendarSettingManager calendarSettingManager;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		CustomDateEditor editor = new CustomDateEditor(
				DefaultDateFormatConstant.DATE_MIN_FORMAT, true);
		binder.registerCustomEditor(Date.class, editor);
		CustomDateEditor editor2 = new CustomDateEditor(
				DefaultDateFormatConstant.HOUR_MIN_FORMAT, true);
		binder.registerCustomEditor(Date.class, editor2);
		binder.registerCustomEditor(int.class, new IntegerEditor());
		binder.registerCustomEditor(double.class, new DoubleEditor());
	}


	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = CalendarWorkTimeSettingUIModel.class.getResource("")
				.getPath();
		String resFileName = CalendarWorkTimeSetting.NODENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	protected void saveInternal(
			CalendarWorkTimeSettingUIModel calendarWorkTimeSettingUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = calendarWorkTimeSettingUIModel.getUuid();
		CalendarWorkTimeSetting calendarWorkTimeSetting = (CalendarWorkTimeSetting) getServiceEntityNodeFromBuffer(
				CalendarWorkTimeSetting.NODENAME, baseUUID);
		calendarSettingManager.convUIToCalendarWorkTimeSetting(
				calendarWorkTimeSetting, calendarWorkTimeSettingUIModel);
		LogonUser logonUser = logonActionController.getLogonUser();
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		Organization organization = logonActionController
				.getOrganizationByUser(logonUser.getUuid());
		this.save(baseUUID, calendarSettingManager, logonUser, organization);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super
				.checkDuplicateIDCore(simpleRequest, calendarSettingManager);
	}


	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			CalendarWorkTimeSetting calendarWorkTimeSetting = (CalendarWorkTimeSetting) calendarSettingManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							CalendarWorkTimeSetting.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = calendarWorkTimeSetting.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(calendarWorkTimeSetting);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					calendarWorkTimeSetting.getName(),
					calendarWorkTimeSetting.getId(), baseUUID);

		} catch (ServiceEntityConfigureException | LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
				.getErrorMessage());
		}
    }

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
