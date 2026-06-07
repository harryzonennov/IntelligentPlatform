package com.company.IntelligentPlatform.platform.model;

import com.company.IntelligentPlatform.platform.model.*;
import com.company.IntelligentPlatform.platform.model.*;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Indicate the organization reference relationship from user to organization
 * 
 * @author Zhang,Hang
 * @date 2013-7-15
 * 
 */
@Entity
@Table(name = "LogonEquipmentReference", catalog = "platform")
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
