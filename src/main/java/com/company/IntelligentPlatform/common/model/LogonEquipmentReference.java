package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Indicate the organization reference relationship from user to organization
 * 
 * @author Zhang,Hang
 * @date 2013-7-15
 * 
 */
public class LogonEquipmentReference extends ReferenceNode {

	public static final String NODENAME = IServiceModelConstants.LogonEquipmentReference;

	public static final String SENAME = LogonUser.SENAME;
	
	public static int EQUPMENT_TYPE_VEH = 10;

	/**
	 * equipment type code, please reference to the each application area
	 */
	public int equipmentType;
	

	public LogonEquipmentReference() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.nodeSpecifyType = ServiceEntityNode.NODESPECIFYTYPE_REFERENCE;
		this.nodeLevel = ServiceEntityNode.NODELEVEL_LEAVE;
	}

	public int getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(int equipmentType) {
		this.equipmentType = equipmentType;
	}

}
