package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class NavigationSystemSettingActionNode extends DocActionNode {

	public static final String NODENAME = IServiceModelConstants.NavigationSystemSettingActionNode;

	public static final String SENAME = IServiceModelConstants.NavigationSystemSetting;

	public static final int DOC_ACTION_ACTIVE = SystemDefDocActionCodeProxy.DOC_ACTION_ACTIVE;

	public static final int DOC_ACTION_REINIT = SystemDefDocActionCodeProxy.DOC_ACTION_REINIT;

	public static final int DOC_ACTION_APPROVE = SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE;

	public static final int DOC_ACTION_REJECT_APPROVE = SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE;

	public static final int DOC_ACTION_SUBMIT = SystemDefDocActionCodeProxy.DOC_ACTION_SUBMIT;

	public static final int DOC_ACTION_REVOKE_SUBMIT = SystemDefDocActionCodeProxy.DOC_ACTION_REVOKE_SUBMIT;

	public static final int DOC_ACTION_ARCHIVE = SystemDefDocActionCodeProxy.DOC_ACTION_ARCHIVE;

	public static final String NODEINST_ACTION_ACTIVE = SystemDefDocActionCodeProxy.NODEINST_ACTION_ACTIVE;

	public static final String NODEINST_ACTION_APPROVE = SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE;

	public static final String NODEINST_ACTION_REJECT_APPROVE = SystemDefDocActionCodeProxy.NODEINST_ACTION_REJECT_APPROVE;

	public static final String NODEINST_ACTION_SUBMIT = SystemDefDocActionCodeProxy.NODEINST_ACTION_SUBMIT;

	public static final String NODEINST_ACTION_REVOKE_SUBMIT = SystemDefDocActionCodeProxy.NODEINST_ACTION_REVOKE_SUBMIT;

	public static final String NODEINST_ACTION_ARCHIVE = SystemDefDocActionCodeProxy.NODEINST_ACTION_ARCHIVE;

	public static final String NODEINST_ACTION_REINIT = SystemDefDocActionCodeProxy.NODEINST_ACTION_REINIT;

	public NavigationSystemSettingActionNode() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

	@Deprecated
	protected int actionCode;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_8000)
	protected String updateFieldsArray;
	
	protected int documentType;
	
	protected String refDocMatItemUUID;
	
	protected String refDocumentUUID;

	@Deprecated
	public int getActionCode() {
		return actionCode;
	}

	@Deprecated
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	public String getUpdateFieldsArray() {
		return updateFieldsArray;
	}

	public void setUpdateFieldsArray(String updateFieldsArray) {
		this.updateFieldsArray = updateFieldsArray;
	}

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	public String getRefDocMatItemUUID() {
		return refDocMatItemUUID;
	}

	public void setRefDocMatItemUUID(String refDocMatItemUUID) {
		this.refDocMatItemUUID = refDocMatItemUUID;
	}

	public String getRefDocumentUUID() {
		return refDocumentUUID;
	}

	public void setRefDocumentUUID(String refDocumentUUID) {
		this.refDocumentUUID = refDocumentUUID;
	}
	
}
