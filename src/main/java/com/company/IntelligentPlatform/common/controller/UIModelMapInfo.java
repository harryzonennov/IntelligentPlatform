package com.company.IntelligentPlatform.common.controller;

@Deprecated
public class UIModelMapInfo {

	public static final int TO_CHILD = 1;

	public static final int TO_PARENT = 2;

	public static final int REF_TO_SOURCE = 3;

	public static final int REF_TO_TARGET = 4;

	public static final int OTHERS = 5;

	protected boolean hostNodeFlag;

	protected boolean editNodeFlag;

	protected String seName;

	protected String nodeName;

	protected int toPreviousRelationType;

	protected String previousSEName;

	protected String previousNodeName;

	public boolean isHostNodeFlag() {
		return hostNodeFlag;
	}

	public void setHostNodeFlag(boolean hostNodeFlag) {
		this.hostNodeFlag = hostNodeFlag;
	}

	public boolean isEditNodeFlag() {
		return editNodeFlag;
	}

	public void setEditNodeFlag(boolean editNodeFlag) {
		this.editNodeFlag = editNodeFlag;
	}

	public String getSeName() {
		return seName;
	}

	public void setSeName(String seName) {
		this.seName = seName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getToPreviousRelationType() {
		return toPreviousRelationType;
	}

	public void setToPreviousRelationType(int toPreviousRelationType) {
		this.toPreviousRelationType = toPreviousRelationType;
	}

	public String getPreviousSEName() {
		return previousSEName;
	}

	public void setPreviousSEName(String previousSEName) {
		this.previousSEName = previousSEName;
	}

	public String getPreviousNodeName() {
		return previousNodeName;
	}

	public void setPreviousNodeName(String previousNodeName) {
		this.previousNodeName = previousNodeName;
	}

}
