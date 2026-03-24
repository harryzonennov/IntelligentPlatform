package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;

@Service
public class SystemConfigureResourceProxy {

	@Autowired
	protected SystemConfigureCategoryManager systemConfigureCategoryManager;

	/**
	 * Main entrance method to check if the system configure scenario mode hit target mode
	 * as expected
	 * 
	 * @param resourceID
	 * @param nodeName
	 *            : system configure model node name
	 * @param targetScenarioMode
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws SystemConfigureException
	 */
	public boolean checkConfigureScenarioModeHit(String resourceID,
			String nodeName, int targetScenarioMode, String client)
			throws ServiceEntityConfigureException, SystemConfigureException {
		String localNodeName = nodeName;
		if (nodeName == null
				|| nodeName.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			localNodeName = ServiceEntityNode.NODENAME_ROOT;
		}
		ServiceEntityNode configureNode = systemConfigureCategoryManager
				.getEntityNodeByKey(resourceID,
						IServiceEntityNodeFieldConstant.ID, localNodeName, client, null, true);
		if (configureNode == null) {
			// should raise exception
			throw new SystemConfigureException(
					SystemConfigureException.PARA_NO_SYSCONFIG, resourceID);
		}
		if (SystemConfigureCategory.NODENAME
				.equals(configureNode.getNodeName())) {
			SystemConfigureCategory systemConfigureCategory = (SystemConfigureCategory) configureNode;
			if(targetScenarioMode == systemConfigureCategory.getScenarioMode()){
				return true;
			} else {
				return false;
			}
		}
		if (SystemConfigureResource.NODENAME
				.equals(configureNode.getNodeName())) {
			SystemConfigureResource systemConfigureResource = (SystemConfigureResource) configureNode;
			if(targetScenarioMode == systemConfigureResource.getScenarioMode()){
				return true;
			} else {
				return false;
			}

		}
		if (SystemConfigureElement.NODENAME
				.equals(configureNode.getNodeName())) {
			SystemConfigureElement systemConfigureElement = (SystemConfigureElement) configureNode;
			if(targetScenarioMode == systemConfigureElement.getScenarioMode()){
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public int getScenarioMode(String resourceID,
			String nodeName, String client) throws ServiceEntityConfigureException, SystemConfigureException{
		String localNodeName = nodeName;
		if (nodeName == null
				|| nodeName.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			localNodeName = ServiceEntityNode.NODENAME_ROOT;
		}
		ServiceEntityNode configureNode = systemConfigureCategoryManager
				.getEntityNodeByKey(resourceID,
						IServiceEntityNodeFieldConstant.ID, localNodeName, client, null, true);
		if (configureNode == null) {
			// should raise exception
			throw new SystemConfigureException(
					SystemConfigureException.PARA_NO_SYSCONFIG, resourceID);
		}
		if (SystemConfigureCategory.NODENAME
				.equals(configureNode.getNodeName())) {
			SystemConfigureCategory systemConfigureCategory = (SystemConfigureCategory) configureNode;
			return systemConfigureCategory.getScenarioMode();
		}
		if (SystemConfigureResource.NODENAME
				.equals(configureNode.getNodeName())) {
			SystemConfigureResource systemConfigureResource = (SystemConfigureResource) configureNode;
			return systemConfigureResource.getScenarioMode();
		}
		if (SystemConfigureElement.NODENAME
				.equals(configureNode.getNodeName())) {
			SystemConfigureElement systemConfigureElement = (SystemConfigureElement) configureNode;
			return systemConfigureElement.getScenarioMode();
		}
		return SystemConfigureCategory.SCENARIO_MODE_OFF;
	}
	
	public int getSubScenarioMode(String resourceID,
			String nodeName, String client) throws ServiceEntityConfigureException, SystemConfigureException{
		String localNodeName = nodeName;
		if (nodeName == null
				|| nodeName.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			localNodeName = ServiceEntityNode.NODENAME_ROOT;
		}
		ServiceEntityNode configureNode = systemConfigureCategoryManager
				.getEntityNodeByKey(resourceID,
						IServiceEntityNodeFieldConstant.ID, localNodeName, client, null, true);
		if (configureNode == null) {
			// should raise exception
			throw new SystemConfigureException(
					SystemConfigureException.PARA_NO_SYSCONFIG, resourceID);
		}		
		if (SystemConfigureElement.NODENAME
				.equals(configureNode.getNodeName())) {
			SystemConfigureElement systemConfigureElement = (SystemConfigureElement) configureNode;
			return systemConfigureElement.getSubScenarioMode();
		}
		return SystemConfigureCategory.SCENARIO_MODE_OFF;
	}
	
	/**
	 * Main entrance method to check if the system configure element sub-scenario mode hit target mode
	 * as expected
	 * 
	 * @param resourceID
	 * @param targetScenarioMode
	 *            : system configure model node name
	 * @param targetScenarioMode
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws SystemConfigureException
	 */
	public boolean checkConfigureSubScenarioModeHit(String resourceID,
			int targetScenarioMode, String client)
			throws ServiceEntityConfigureException, SystemConfigureException {		
		SystemConfigureElement systemConfigureElement = (SystemConfigureElement) systemConfigureCategoryManager
				.getEntityNodeByKey(resourceID,
						IServiceEntityNodeFieldConstant.ID, SystemConfigureElement.NODENAME, client, null, true);
		if (systemConfigureElement == null) {
			// should raise exception
			throw new SystemConfigureException(
					SystemConfigureException.PARA_NO_SYSCONFIG, resourceID);
		}		
		if(targetScenarioMode == systemConfigureElement.getSubScenarioMode()){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Main entrance to check if the target system configure i
	 * @param resourceID
	 * @param nodeName: node level of system configure
	 * @param targetScenarioMode
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws SystemConfigureException
	 */
	public boolean checkConfigureNodeSwitchON(String resourceID,
			String nodeName, String client)
			throws ServiceEntityConfigureException {
		String localNodeName = nodeName;
		if (nodeName == null
				|| nodeName.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			localNodeName = ServiceEntityNode.NODENAME_ROOT;
		}
		ServiceEntityNode configureNode = systemConfigureCategoryManager
				.getEntityNodeByKey(resourceID,
						IServiceEntityNodeFieldConstant.ID, localNodeName, client, null, true);
		if (configureNode == null) {
			// should raise exception
			return false;
		}
		if (SystemConfigureCategory.NODENAME
				.equals(configureNode.getNodeName())) {
			SystemConfigureCategory systemConfigureCategory = (SystemConfigureCategory) configureNode;
			if(systemConfigureCategory.getScenarioMode() == SystemConfigureCategory.SCENARIO_MODE_OFF){
				return false;
			} else {
				return true;
			}
		}
		if (SystemConfigureResource.NODENAME
				.equals(configureNode.getNodeName())) {
			SystemConfigureResource systemConfigureResource = (SystemConfigureResource) configureNode;
			if(systemConfigureResource.getScenarioMode() == SystemConfigureCategory.SCENARIO_MODE_OFF){
				return false;
			} else {
				return true;
			}

		}
		if (SystemConfigureElement.NODENAME
				.equals(configureNode.getNodeName())) {
			SystemConfigureElement systemConfigureElement = (SystemConfigureElement) configureNode;
			if(systemConfigureElement.getScenarioMode() == SystemConfigureCategory.SCENARIO_MODE_OFF){
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
}
