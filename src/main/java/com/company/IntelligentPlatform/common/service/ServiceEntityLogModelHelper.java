package com.company.IntelligentPlatform.common.service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO-DAO: import platform.foundation.DAO.ServiceEntityLogModelDAO;
import com.company.IntelligentPlatform.common.service.ReferenceProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityLogModelManager;
import com.company.IntelligentPlatform.common.model.IReferenceNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityBindModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogItem;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogModel;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * 
 * @author zhanghang
 * @date Mon Nov 26 14:51:29 CST 2012
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
public class ServiceEntityLogModelHelper {

	// TODO-DAO: @Autowired
	@Autowired
	ServiceEntityLogModelDAO serviceEntityLogModelDAO; // TODO-DAO: stub

	@Autowired
	ServiceEntityLogModelManager serviceEntityLogModelManager;

	/**
	 * 
	 * @param seNode
	 * @param processMode
	 * @param messageType
	 * @param transactionFlag
	 *            : if true, log will be recored instantly, if false, log will
	 *            be commit by external application manually
	 * @throws ServiceEntityConfigureException
	 */
	public void logAction(ServiceEntityNode seNode, String logonUserUUID, String organizationUUID,  int processMode,
			int messageType) throws ServiceEntityConfigureException {
		ServiceEntityLogModel seLog = (ServiceEntityLogModel) serviceEntityLogModelManager
				.newRootEntityNode();

		seLog.setMessageType(messageType);
		seLog.setProcessMode(processMode);
		seLog.setId(seNode.getId());
		seLog.setName(seNode.getName());
		seLog.setClient(seNode.getClient());
		seLog.setResEmployeeUUID(logonUserUUID);
		seLog.setCreatedBy(logonUserUUID);
		seLog.setResOrgUUID(organizationUUID);
		// temp
		setAdminData(seLog, ServiceEntityBindModel.PROCESSMODE_CREATE);
		ReferenceProxy reProxy = ReferenceProxy.getInstance();
		reProxy.buildReferenceNode(seNode, seLog,
				ServiceEntityFieldsHelper.getPackage(seNode));
		serviceEntityLogModelDAO.insertEntity(seLog);
	}
	
	public void logDeleteAction(ServiceEntityNode seNode, String logonUserUUUID, String organizationUUID,
			int messageType) throws ServiceEntityConfigureException {
		ServiceEntityLogModel seLog = (ServiceEntityLogModel) serviceEntityLogModelManager
				.newRootEntityNode();
		seLog.setMessageType(messageType);
		seLog.setProcessMode(ServiceEntityBindModel.PROCESSMODE_DELETE);
		seLog.setId(seNode.getId());
		seLog.setName(seNode.getName());
		seLog.setCreatedBy(logonUserUUUID);
		seLog.setClient(seNode.getClient());
		seLog.setResEmployeeUUID(logonUserUUUID);
		seLog.setResOrgUUID(organizationUUID);
		ReferenceProxy reProxy = ReferenceProxy.getInstance();
		reProxy.buildReferenceNode(seNode, seLog,
				ServiceEntityFieldsHelper.getPackage(seNode));
		serviceEntityLogModelDAO.insertEntity(seLog);
	}
	
	public void logDeleteAction(List<ServiceEntityNode> seNodeList,  String logonUserUUUID, String organizationUUID,
			int messageType) throws ServiceEntityConfigureException {
		if(!ServiceCollectionsHelper.checkNullList(seNodeList)){
			for(ServiceEntityNode seNode:seNodeList){
				logDeleteAction(seNode, logonUserUUUID, organizationUUID, messageType);
			}
		}
	}

	/**
	 * 
	 * @param seNode
	 * @param processMode
	 * @param messageType
	 * @param transactionFlag
	 *            : if true, log will be recored instantly, if false, log will
	 *            be commit by external application manually
	 * @throws ServiceEntityConfigureException
	 */
	public void logAction(ServiceEntityNode seNode,
			ServiceEntityNode seNodeBack, String logonUserUUUID, String organizationUUID, int processMode, int messageType)
			throws ServiceEntityConfigureException {
		// TODO, to using switch in the future.
		boolean itemFlag = true;
		ServiceEntityLogModel seLog = new ServiceEntityLogModel();
		seLog.setNodeLevel(ServiceEntityNode.NODELEVEL_ROOT);
		seLog.setNodeName(ServiceEntityNode.NODENAME_ROOT);
		// For root node set parent uuid & root uuid as itself
		seLog.setParentNodeUUID(seLog.getUuid());
		seLog.setRootNodeUUID(seLog.getUuid());
		seLog.setId(seNode.getId());
		seLog.setName(seNode.getName());
		seLog.setClient(seNode.getClient());
		seLog.setMessageType(messageType);
		seLog.setProcessMode(processMode);
		seLog.setResEmployeeUUID(logonUserUUUID);
		seLog.setCreatedBy(logonUserUUUID);
		seLog.setResOrgUUID(organizationUUID);
		setAdminData(seLog, processMode);
		ReferenceProxy reProxy = ReferenceProxy.getInstance();
		reProxy.buildReferenceNode(seNode, seLog,
				ServiceEntityFieldsHelper.getPackage(seNode));
		serviceEntityLogModelDAO.insertEntity(seLog);
		if(itemFlag){
			logDiffValues(seNode, seNodeBack, seLog);
		}
	}

	public void logDiffValues(ServiceEntityNode seNode,
			ServiceEntityNode seNodeBack,
			ServiceEntityLogModel serviceEntityLogModel) {
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getServiceSelfDefinedFieldsList(seNode.getClass(),
						ServiceEntityNode.class);
		for (Field field : fieldList) {
			boolean excludeFlag = checkExcludeFieldName(field.getName());
			if (excludeFlag) {
				continue;
			}
			excludeFlag = checkExcludeByFieldType(field);
			if (excludeFlag) {
				continue;
			}
			PropertyDescriptor pd;
//			try {
//				pd = new PropertyDescriptor(field.getName(), seNode.getClass());
//				Method getMethod = pd.getReadMethod();
//				Object objectNew = getMethod.invoke(seNode);
//				excludeFlag = checkExcludeByFieldValue(objectNew);
//				String sNew = ServiceEntityStringHelper.EMPTYSTRING;
//				sNew = objectNew == null ? "" : objectNew.toString();
//				if (excludeFlag) {
//					continue;
//				}
//				String sBack = ServiceEntityStringHelper.EMPTYSTRING;
//				Object objectBack = null;
//				if(seNodeBack != null){
//					objectBack = getMethod.invoke(seNodeBack);
//				}
//				sBack = objectBack == null ? "" : objectBack.toString();
//				if (!sNew.equals(sBack)) {
//					// In case values are different
//					ServiceEntityLogItem serviceEntityLogItem = (ServiceEntityLogItem) serviceEntityLogModelManager
//							.newEntityNode(serviceEntityLogModel,
//									ServiceEntityLogItem.NODENAME);
//					serviceEntityLogItem
//							.setFieldType(field.getType().getName());
//					serviceEntityLogItem.setId(field.getName());
//					serviceEntityLogItem.setName(field.getName());
//					serviceEntityLogItem.setOldValue(sBack);
//					serviceEntityLogItem.setNewValue(sNew);
//					serviceEntityLogModelDAO.insertEntity(serviceEntityLogItem);
//				}
//			} catch (IntrospectionException e) {
//				// Just skip this run
//			} catch (IllegalAccessException e) {
//				// Just skip this run
//			} catch (IllegalArgumentException e) {
//				// Just skip this run
//			} catch (InvocationTargetException e) {
//				// Just skip this run
//			} catch (ServiceEntityConfigureException e) {
//				// Just skip this run
//			}
		}
	}

	public boolean checkExcludeByFieldType(Field field) {
        return field.getType().getName().equals(byte[].class.getName());
    }

	public boolean checkExcludeByFieldValue(Object rawValue) {
		if (rawValue == null) {
			return false;
		}
        return rawValue.toString().length() > 2500;
    }

	public boolean checkExcludeFieldName(String fieldName) {
		if (ServiceEntityStringHelper.checkNullString(fieldName)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.UUID)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.CLIENT)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.PARENTNODEUUID)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.ROOTNODEUUID)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.CREATEDBY)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.CREATEDTIME)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.LASTUPDATETIME)) {
			return true;
		}
		if (fieldName.equals("lastUpdateBy")) {
			return true;
		}
		if (fieldName.equals(IReferenceNodeFieldConstant.REFNODENAME)) {
			return true;
		}
		if (fieldName.equals(IReferenceNodeFieldConstant.REFPACKAGENAME)) {
			return true;
		}
        return fieldName.equals(IReferenceNodeFieldConstant.REFSENAME);
    }

	/**
	 * [Internal method] set admin data for log module
	 * 
	 * @param seNode
	 * @param processMode
	 */
	protected void setAdminData(ServiceEntityNode seNode, int processMode) {
		if (processMode == ServiceEntityBindModel.PROCESSMODE_CREATE) {
			seNode.setCreatedTime(LocalDateTime.now());
			seNode.setLastUpdateTime(LocalDateTime.now());
		}
		if (processMode == ServiceEntityBindModel.PROCESSMODE_UPDATE) {
			seNode.setLastUpdateTime(LocalDateTime.now());
		}
	}

}