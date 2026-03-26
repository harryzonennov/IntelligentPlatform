package com.company.IntelligentPlatform.common.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.EmployeeExcelModel;
import com.company.IntelligentPlatform.common.dto.EmployeeUIModel;
import com.company.IntelligentPlatform.common.repository.EmployeeRepository;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.AccountManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.StandardGenderProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.EmployeeConfigureProxy;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDateHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ISystemCodeValueCollectConstants;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [Employee]
 * 
 * @author
 * @date Sun Aug 11 12:34:27 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class EmployeeManager extends ServiceEntityManager {

	public static final String METHOD_ConvEmployeeToUI = "convEmployeeToUI";

	public static final String METHOD_ConvUIToEmployee = "convUIToEmployee";

	public static final String METHOD_ConvLogonUserToUI = "convLogonUserToUI";

	private List<ServiceEntityNode> rawWorkRoleMap;

	private List<ServiceEntityNode> rawJobLevelMap;
    @PersistenceContext
    private EntityManager entityManager;


	@Autowired
	protected EmployeeRepository employeeDAO;

	@Autowired
	protected EmployeeConfigureProxy employeeConfigureProxy;

	@Autowired
	protected LogonUserManager logonUserManager;
	
	@Autowired
	protected EmployeeSearchProxy employeeSearchProxy;

	@Autowired
	protected AccountManager accountManager;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

	@Autowired
	protected LogonInfoManager logonInfoManager;

	@Autowired
	protected StandardGenderProxy standardGenderProxy;

	protected Map<String, Map<Integer, String>> workRoleMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> operateTypeMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> regularTypeMapLan = new HashMap<>();

	protected Logger logger = LoggerFactory.getLogger(EmployeeManager.class);

	public EmployeeManager() {
		super.seConfigureProxy = new EmployeeConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, employeeDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(employeeConfigureProxy);
	}

	public Map<Integer, String> initGenderMap(String languageCode)
			throws ServiceEntityInstallationException {
		return standardGenderProxy.getGenderMap(languageCode);
	}

	public Map<Integer, String> initRegularTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.regularTypeMapLan, EmployeeUIModel.class,
				"regularType");
	}

	public Map<Integer, String> initStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, EmployeeUIModel.class,
				"status");
	}

	public Map<Integer, String> initOperateTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.operateTypeMapLan, EmployeeUIModel.class,
				"operateType");
	}

	public Map<Integer, String> initWorkRoleMap(String languageCode, String client, boolean refreshFlag)
			throws ServiceEntityInstallationException {
		List<ServiceEntityNode> systemCodeValueUnionList = this
				.initRawWorkRoleMap(languageCode, client, refreshFlag);
		if(ServiceCollectionsHelper.checkNullList(systemCodeValueUnionList)){
			// Return system default configure
			return ServiceLanHelper
					.initDefLanguageMapUIModel(languageCode, this.workRoleMapLan, EmployeeUIModel.class,
							"workRole");
		}
		return systemCodeValueCollectionManager
				.convertIntCodeValueUnionMap(systemCodeValueUnionList);
	}

	public String getWorkRoleValue(int workRole, String languageCode, String client, boolean refreshFlag){
		try {
			Map<Integer, String> workRoleMap = this.initWorkRoleMap(languageCode, client, refreshFlag);
			if(workRoleMap != null){
				return workRoleMap.get(workRole);
			}
			return String.valueOf(workRole);
		} catch (ServiceEntityInstallationException e) {
			return String.valueOf(workRole);
		}
	}

	public List<ServiceEntityNode> initRawWorkRoleMap(String languageCode, String client, boolean refreshFlag)
			throws ServiceEntityInstallationException {
		if (refreshFlag || ServiceCollectionsHelper.checkNullList(this.rawWorkRoleMap)) {
			try {
				this.rawWorkRoleMap = systemCodeValueCollectionManager
						.loadRawCodeValueUnionList(
								ISystemCodeValueCollectConstants.ID_EMPLOYEE_WORKROLE,
								client);
			} catch (ServiceModuleProxyException
					| ServiceEntityConfigureException e) {
				// just ignore
			}
		}
		return this.rawWorkRoleMap;
	}

	public Map<Integer, String> initJobLevelMap(String languageCode, String client, boolean refreshFlag)
			throws ServiceEntityInstallationException {
		List<ServiceEntityNode> systemCodeValueUnionList = this
				.initRawJobLevelMap(client, refreshFlag);
		return systemCodeValueCollectionManager
				.convertIntCodeValueUnionMap(systemCodeValueUnionList);
	}

	public List<ServiceEntityNode> initRawJobLevelMap(String client, boolean refreshFlag)
			throws ServiceEntityInstallationException {
		if (refreshFlag || ServiceCollectionsHelper.checkNullList(this.rawJobLevelMap)) {
			try {
				this.rawJobLevelMap = systemCodeValueCollectionManager
						.loadRawCodeValueUnionList(
								ISystemCodeValueCollectConstants.ID_EMPLOYEE_JOBLEVEL,
								client);
			} catch (ServiceModuleProxyException
					| ServiceEntityConfigureException e) {
				// just ignore
			}
		}
		return this.rawJobLevelMap;
	}

	public void admDeleteEmployee(String uuid)
			throws ServiceEntityConfigureException {
		/*
		 * [Step1] Execute deletion of this employee
		 */
		employeeDAO.deleteById(uuid);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convEmployeeToUI(Employee employee,
			EmployeeUIModel employeeUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (employee != null) {
			accountManager.convAccountToUI(employee, employeeUIModel, logonInfo);			
			employeeUIModel.setDriverLicenseNumber(employee
					.getDriverLicenseNumber());
			employeeUIModel.setHouseNumber(employee.getHouseNumber());
			employeeUIModel.setAge(employee.getAge());			
			employeeUIModel.setDriverLicenseType(employee
					.getDriverLicenseType());
			employeeUIModel.setIdentification(employee.getIdentification());
			employeeUIModel.setSubArea(employee.getSubArea());			
			employeeUIModel.setTownZone(employee.getTownZone());			
			employeeUIModel.setPostcode(employee.getPostcode());
			employeeUIModel.setOperateType(employee.getOperateType());
			employeeUIModel.setStatus(employee.getStatus());
			if(logonInfo != null){
				Map<Integer, String> operateTypeMap = initOperateTypeMap(logonInfo.getLanguageCode());
				employeeUIModel.setOperateTypeValue(operateTypeMap
						.get(employee.getOperateType()));
				Map<Integer, String> statusMap = this.initStatusMap(logonInfo.getLanguageCode());
				employeeUIModel.setStatusValue(statusMap.get(employee
						.getStatus()));
			}
			employeeUIModel.setMobile(employee.getMobile());
			employeeUIModel.setRefCityUUID(employee.getRefCityUUID());
			if(logonInfo != null){
				Map<Integer, String> genderMap = this.initGenderMap(logonInfo.getLanguageCode());
				employeeUIModel.setGenderValue(genderMap.get(employee.getGender()));
			}
			employeeUIModel.setGender(employee.getGender());			
			if (employee.getBoardDate() != null) {
				employeeUIModel
						.setBoardDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(java.util.Date.from(employee.getBoardDate().atStartOfDay(ZoneId.systemDefault()).toInstant())));
			}
			if (employee.getBirthDate() != null) {
				employeeUIModel
						.setBirthDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(java.util.Date.from(employee.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant())));
			}
			if (employee.getBirthDate() != null) {
				Calendar startCalendar = Calendar.getInstance();
				startCalendar.setTime(java.util.Date.from(employee.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
				Calendar endCalendar = Calendar.getInstance();
				endCalendar.setTime(new Date());
				int age = ServiceEntityDateHelper.getDiffYear(startCalendar,
						endCalendar);
				employeeUIModel.setAge(age);
			}
			employeeUIModel.setWorkRole(employee.getWorkRole());
			employeeUIModel.setJobLevel(employee.getJobLevel());
			if(logonInfo != null){
				Map<Integer, String> workRoleMap = this.initWorkRoleMap(logonInfo.getLanguageCode(),
						employee.getClient(), false);
				employeeUIModel.setWorkRoleValue(workRoleMap.get(employee
						.getWorkRole()));
				Map<Integer, String> jobLevelMap = this.initJobLevelMap(logonInfo.getLanguageCode(),employee
						.getClient(), false);
				employeeUIModel.setJobLevelValue(jobLevelMap.get(employee
						.getJobLevel()));
			}
			employeeUIModel.setStreetName(employee.getStreetName());
		}
	}

	public void convLogonUserToUI(LogonUser logonUser,
			EmployeeUIModel employeeUIModel) {
		if (logonUser != null) {
			employeeUIModel.setLogonUserUUID(logonUser.getUuid());
			employeeUIModel.setLogonUserId(logonUser.getId());
			employeeUIModel.setLogonUserName(logonUser.getName());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:employee
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToEmployee(EmployeeUIModel employeeUIModel,
			Employee rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(employeeUIModel, rawEntity);
		rawEntity.setDriverLicenseNumber(employeeUIModel
				.getDriverLicenseNumber());
		rawEntity.setHouseNumber(employeeUIModel.getHouseNumber());
		rawEntity.setAge(employeeUIModel.getAge());
		rawEntity.setJobLevel(employeeUIModel.getJobLevel());
		rawEntity.setDriverLicenseType(employeeUIModel.getDriverLicenseType());
		rawEntity.setEmail(employeeUIModel.getEmail());
		rawEntity.setIdentification(employeeUIModel.getIdentification());
		rawEntity.setSubArea(employeeUIModel.getSubArea());
		rawEntity.setCityName(employeeUIModel.getCityName());
		rawEntity.setAddress(employeeUIModel.getAddress());
		rawEntity.setTownZone(employeeUIModel.getTownZone());
		rawEntity.setStateName(employeeUIModel.getStateName());
		rawEntity.setPostcode(employeeUIModel.getPostcode());
		rawEntity.setOperateType(employeeUIModel.getOperateType());
		rawEntity.setMobile(employeeUIModel.getMobile());
		rawEntity.setRefCityUUID(employeeUIModel.getRefCityUUID());
		rawEntity.setGender(employeeUIModel.getGender());
		if (!ServiceEntityStringHelper.checkNullString(employeeUIModel
				.getBoardDate())) {
			try {
				rawEntity.setBoardDate(DefaultDateFormatConstant.DATE_FORMAT
				.parse(employeeUIModel.getBoardDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			} catch (ParseException e) {
				// do nothing
			}
		}
		if (!ServiceEntityStringHelper.checkNullString(employeeUIModel
				.getBirthDate())) {
			try {
				rawEntity.setBirthDate(DefaultDateFormatConstant.DATE_FORMAT
				.parse(employeeUIModel.getBirthDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setCountryName(employeeUIModel.getCountryName());
		rawEntity.setTelephone(employeeUIModel.getTelephone());
		rawEntity.setWorkRole(employeeUIModel.getWorkRole());
		rawEntity.setStreetName(employeeUIModel.getStreetName());
	}

	/**
	 * Conv to employee excel model
	 * 
	 * @param employeeUIModel
	 * @param employeeExcelModel
	 */
	public void convToExcelUIModel(EmployeeUIModel employeeUIModel,
			EmployeeExcelModel employeeExcelModel,
			Map<Integer, String> genderMap) {
		employeeExcelModel.setId(employeeUIModel.getId());
		employeeExcelModel.setName(employeeUIModel.getName());
		employeeExcelModel.setIdentification(employeeUIModel
				.getIdentification());
		if (!ServiceEntityStringHelper.checkNullString(employeeUIModel
				.getBoardDate())) {
			try {
				employeeExcelModel
						.setBoardDate(DefaultDateFormatConstant.DATE_FORMAT
								.parse(employeeUIModel.getBoardDate()));
			} catch (ParseException e) {
				// do nothing
			}
		}
		employeeExcelModel.setOrganizationId(employeeUIModel
				.getOrganizationId());
		employeeExcelModel.setOrganizationName(employeeUIModel
				.getOrganizationName());
		employeeExcelModel.setAge(employeeUIModel.getAge());
		employeeExcelModel.setMobile(employeeUIModel.getMobile());
		employeeExcelModel
				.setGender(genderMap.get(employeeUIModel.getGender()));
		employeeExcelModel.setWorkRoleValue(employeeUIModel.getWorkRoleValue());
		employeeExcelModel.setJobLevelValue(employeeUIModel.getJobLevelValue());
		employeeExcelModel.setLogonUserId(employeeUIModel.getLogonUserId());
		employeeExcelModel.setLogonUserName(employeeUIModel.getLogonUserName());
		employeeExcelModel.setLogonRoleName(employeeUIModel.getLogonRoleName());
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.Employee;
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return this.employeeSearchProxy;
	}

}
