package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;


public class ServiceDocConfigureParaGroupUIModel extends SEUIComModel {
	
	protected String groupId;
	
	protected String groupName;
	
	protected int logicOperator;
	
	protected String logicOperatorLabel;
	
	protected int layer;
	
	/**
	 * Only used to set the layer temp for choose parent group
	 */
	protected int targetLayer;
	
	protected String refParentGroupUUID;
	
	protected String refParentGroupId;
	
	protected String refParentGroupName;
	
	protected String refConfigureId;
	
	protected String refConfigureName;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getLogicOperator() {
		return logicOperator;
	}

	public void setLogicOperator(int logicOperator) {
		this.logicOperator = logicOperator;
	}

	public String getLogicOperatorLabel() {
		return logicOperatorLabel;
	}

	public void setLogicOperatorLabel(String logicOperatorLabel) {
		this.logicOperatorLabel = logicOperatorLabel;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public int getTargetLayer() {
		return targetLayer;
	}

	public void setTargetLayer(int targetLayer) {
		this.targetLayer = targetLayer;
	}

	public String getRefParentGroupUUID() {
		return refParentGroupUUID;
	}

	public void setRefParentGroupUUID(String refParentGroupUUID) {
		this.refParentGroupUUID = refParentGroupUUID;
	}

	public String getRefParentGroupId() {
		return refParentGroupId;
	}

	public void setRefParentGroupId(String refParentGroupId) {
		this.refParentGroupId = refParentGroupId;
	}

	public String getRefParentGroupName() {
		return refParentGroupName;
	}

	public void setRefParentGroupName(String refParentGroupName) {
		this.refParentGroupName = refParentGroupName;
	}

	public String getRefConfigureId() {
		return refConfigureId;
	}

	public void setRefConfigureId(String refConfigureId) {
		this.refConfigureId = refConfigureId;
	}

	public String getRefConfigureName() {
		return refConfigureName;
	}

	public void setRefConfigureName(String refConfigureName) {
		this.refConfigureName = refConfigureName;
	}

}
