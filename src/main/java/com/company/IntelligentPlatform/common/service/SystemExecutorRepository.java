package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;

@Service
public class SystemExecutorRepository {

	/**
	 * Registration Area to register executable Units here
	 */
	@Qualifier("logisticsDefItemIdBatchGenerator")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ARCHIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMUPGRADE)
	protected SystemExecutorTemplate logisticsDefItemIdBatchGenerator;

	@Qualifier("serviceExtensionLogInitialInstaller")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ARCHIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMUPGRADE)
	protected SystemExecutorTemplate serviceExtensionLogInitialInstaller;

	@Qualifier("logisticsEntityAdmDeleteExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_ADMINTOOL)
	protected SystemExecutorTemplate logisticsEntityAdmDeleteExecutor;

	@Qualifier("serviceEntityAdmDeleteExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_ADMINTOOL)
	protected SystemExecutorTemplate serviceEntityAdmDeleteExecutor;

	@Qualifier("serviceExtensionInitialInstaller")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ARCHIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_ADMINTOOL)
	protected SystemExecutorTemplate serviceExtensionInitialInstaller;	
	

	@Qualifier("serviceModelLogClearExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_ADMINTOOL)
	protected SystemExecutorTemplate serviceModelLogClearExecutor;

	@Qualifier("docItemRefMaterialSKUUUIDMigration")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ARCHIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMUPGRADE)
	protected SystemExecutorTemplate docItemRefMaterialSKUUUIDMigration;

	@Qualifier("salesEntityAdmDeleteExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_ADMINTOOL)
	protected SystemExecutorTemplate salesEntityAdmDeleteExecutor;
	
	@Qualifier("navigationDefaultImportExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_INITIALIZE)
	protected SystemExecutorTemplate navigationDefaultImportExecutor;

	@Qualifier("authorizationResourceGenerateExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_INITIALIZE)
	protected SystemExecutorTemplate authorizationResourceGenerateExecutor;

	@Qualifier("systemConfigureResourceInstallExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_INITIALIZE)
	protected SystemExecutorTemplate systemConfigureResourceInstallExecutor;

	@Qualifier("systemCodeCollectionInstallExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_INITIALIZE)
	protected SystemExecutorTemplate systemCodeCollectionInstallExecutor;

	@Qualifier("productionResourceAdmDeleteExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType =
			ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMUPGRADE)
	protected SystemExecutorTemplate productionResourceAdmDeleteExecutor;

	@Qualifier("registeredProductPropsMigrateExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType =
			ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMUPGRADE)
	protected SystemExecutorTemplate registeredProductPropsMigrateExecutor;

	@Qualifier("warehouseStoreRefMaterialTemplateExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType =
			ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMUPGRADE)
	protected SystemExecutorTemplate warehouseStoreRefMaterialTemplateExecutor;

	@Qualifier("productionBOMAdmExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMUPGRADE)
	protected SystemExecutorTemplate productionBOMAdmExecutor;

	@Qualifier("docItemProfFlowDataMigrateExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMUPGRADE)
	protected SystemExecutorTemplate docItemProfFlowDataMigrateExecutor;

	@Qualifier("docItemItemStatusUpdateExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMUPGRADE)
	protected SystemExecutorTemplate docItemItemStatusUpdateExecutor;

	@Qualifier("systemDebugExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType =
			ISystemExecutorAttr.EXECUTIONTYPE_INITIALIZE)
	protected SystemExecutorTemplate systemDebugExecutor;


	@Qualifier("systemRegularUpgradeExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType = ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMUPGRADE)
	protected SystemExecutorTemplate systemRegularUpgradeExecutor;


	@Qualifier("messageTemplateImportExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType =
			ISystemExecutorAttr.EXECUTIONTYPE_INITIALIZE)
	protected SystemExecutorTemplate messageTemplateImportExecutor;

	@Qualifier("flowRouterGenerateExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType =
			ISystemExecutorAttr.EXECUTIONTYPE_INITIALIZE)
	protected SystemExecutorTemplate flowRouterGenerateExecutor;

	@Qualifier("serviceFlowModelGenerateExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType =
			ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMINSTALL)
	protected SystemExecutorTemplate serviceFlowModelGenerateExecutor;


	@Qualifier("serviceDocumentSettingsGenerateExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType =
			ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMINSTALL)
	protected SystemExecutorTemplate serviceDocumentSettingsGenerateExecutor;


	@Qualifier("organizationManWorkRoleExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType =
			ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMUPGRADE)
	protected SystemExecutorTemplate organizationManWorkRoleExecutor;


	@Qualifier("warehouseStoreMigrateExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType =
			ISystemExecutorAttr.EXECUTIONTYPE_SYSTEMUPGRADE)
	protected SystemExecutorTemplate warehouseStoreMigrateExecutor;


	@Qualifier("systemCodeValueCollectionExecutor")
	@Autowired(required = false)
	@ISystemExecutorAttr(status = ISystemExecutorAttr.STATUS_ACTIVE, executionType =
			ISystemExecutorAttr.EXECUTIONTYPE_INITIALIZE)
	protected SystemExecutorTemplate systemCodeValueCollectionExecutor;
	/**
	 * ===============end of register area===============
	 */
	
	protected Logger logger = LoggerFactory.getLogger(SystemExecutorRepository.class);

	
	public SystemExecutorTemplate getExecutorTemplateById(String refExecutorId)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException {
		Field field = ServiceEntityFieldsHelper.getServiceField(SystemExecutorRepository.class, refExecutorId);
		if(field != null){
			field.setAccessible(true);
			return (SystemExecutorTemplate)field.get(this);
		}
		return null;
	}
	
	public List<SystemExecutorTemplate> getAllExecutorList(int status, int executionType){
		List<Field> allFieldList = this.getExecutorFieldList(status, executionType);
		if(ServiceCollectionsHelper.checkNullList(allFieldList)){
			return new ArrayList<>();
		}
		List<SystemExecutorTemplate> resultList = new ArrayList<>();
		for(Field field:allFieldList){
			field.setAccessible(true);
			ISystemExecutorAttr systemExecutorAttr = field.getAnnotation(ISystemExecutorAttr.class);
			if(systemExecutorAttr == null){
				continue;
			}
			try {
				SystemExecutorTemplate systemExecutorTemplate = (SystemExecutorTemplate) field.get(this);
				if(systemExecutorTemplate != null){
					resultList.add(systemExecutorTemplate);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// log the issue and configure
			}
		}
		return resultList;
	}
	
	private List<Field> getExecutorFieldList(int status, int executionType){
		List<Field> allFieldList = ServiceEntityFieldsHelper.getFieldsList(SystemExecutorRepository.class);
		if(ServiceCollectionsHelper.checkNullList(allFieldList)){
			return null;
		}
		List<Field> resultFieldList = new ArrayList<Field>();
		for(Field field:allFieldList){
			field.setAccessible(true);
			ISystemExecutorAttr systemExecutorAttr = field.getAnnotation(ISystemExecutorAttr.class);
			if(systemExecutorAttr == null){
				continue;
			}
			if(status > 0){
				// In case have to consider status
				if(systemExecutorAttr.status() != status){
					continue;
				}
			}
			if(executionType > 0){
				// In case have to consider status
				if(systemExecutorAttr.executionType() != executionType){
					continue;
				}
			}
			resultFieldList.add(field);
		}
		return resultFieldList;
		
	}
	

}
