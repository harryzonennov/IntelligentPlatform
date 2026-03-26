package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;

import java.util.Date;

/**
 * Basic model class for document Action data
 * @author Zhang,Hang
 *
 */
public class DocActionNode extends ServiceEntityNode {

	public static final String FIELD_DOCACTIONCODE = "docActionCode";

	public static final String FIELD_FLATNODE_SWITCH = "flatNodeSwitch";

	public static final String FIELD_EXECUTEBY_UUID = "executedByUUID";

	protected int processIndex;

	protected int flatNodeSwitch;
	
	protected int docActionCode;

	protected Date executionTime;

	protected String executedByUUID;

	protected int documentType;

	protected String refDocMatItemUUID;

	protected String refDocumentUUID;

	public DocActionNode(){
		this.flatNodeSwitch = StandardSwitchProxy.SWITCH_ON;
		this.processIndex = 1;
	}

	public int getFlatNodeSwitch() {
		return flatNodeSwitch;
	}

	public void setFlatNodeSwitch(int flatNodeSwitch) {
		this.flatNodeSwitch = flatNodeSwitch;
	}

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public int getDocActionCode() {
		return docActionCode;
	}

	public void setDocActionCode(int docActionCode) {
		this.docActionCode = docActionCode;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public String getExecutedByUUID() {
		return executedByUUID;
	}

	public void setExecutedByUUID(String executedByUUID) {
		this.executedByUUID = executedByUUID;
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
