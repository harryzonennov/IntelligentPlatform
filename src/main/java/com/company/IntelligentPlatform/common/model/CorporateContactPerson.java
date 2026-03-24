package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class CorporateContactPerson extends ReferenceNode {

	public static final String NODENAME = IServiceModelConstants.CorporateContactPerson;

	public static final String SENAME = CorporateCustomer.SENAME;

	public static final int CONTACTROLE_MAIN_CONTACT = 1;

	public static final int CONTACTROLE_NORM_CONTACT = 2;

	public static final int CONTACTROLE_OTHERS = 3;

	public static final int CONTACTPOSITION_SALESMAN = 1;
	
	public static final int CONTACTPOSITION_SALESMANAGER = 2;
	
	public static final int CONTACTPOSITION_DEPTMANAGER = 3;	

	protected int contactRole;

	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)	
	protected String contactRoleNote;
	
	protected int contactPosition;
	
	protected String contactPositionNote;

	public CorporateContactPerson() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		super.nodeLevel = ServiceEntityNode.NODELEVEL_LEAVE;
		this.contactRole = CONTACTROLE_NORM_CONTACT;
		this.contactRole = CONTACTPOSITION_SALESMAN;
	}

	public int getContactRole() {
		return contactRole;
	}

	public void setContactRole(int contactRole) {
		this.contactRole = contactRole;
	}

	public String getContactRoleNote() {
		return contactRoleNote;
	}

	public void setContactRoleNote(String contactRoleNote) {
		this.contactRoleNote = contactRoleNote;
	}

	public int getContactPosition() {
		return contactPosition;
	}

	public void setContactPosition(int contactPosition) {
		this.contactPosition = contactPosition;
	}

	public String getContactPositionNote() {
		return contactPositionNote;
	}

	public void setContactPositionNote(String contactPositionNote) {
		this.contactPositionNote = contactPositionNote;
	}

}
