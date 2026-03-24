package com.company.IntelligentPlatform.common.model;

/**
 * Service Entity Bind Model
 * 
 * @author ZhangHang
 * @date Nov 14, 2012
 * 
 */
public class ServiceEntityBindModel {

	public static final int PROCESSMODE_CREATE = 1;

	public static final int PROCESSMODE_UPDATE = 2;

	public static final int PROCESSMODE_DELETE = 3;

	public static final int PROCESSMODE_STANDBY = 4;

	protected ServiceEntityNode seNode;

	protected int processMode;

	/**
	 * Indicator to identify one specified data clusters to be processed from UI
	 * controller
	 */
	protected String baseUUID;

	public ServiceEntityBindModel() {

	}

	public ServiceEntityBindModel(ServiceEntityNode seNode, int processMode) {
		super();
		this.seNode = seNode;
		this.processMode = processMode;
	}

	public ServiceEntityBindModel(ServiceEntityNode seNode, int processMode,
			String baseUUID) {
		super();
		this.seNode = seNode;
		this.processMode = processMode;
		this.baseUUID = baseUUID;
	}

	public ServiceEntityNode getSeNode() {
		return seNode;
	}

	public void setSeNode(ServiceEntityNode seNode) {
		this.seNode = seNode;
	}

	public int getProcessMode() {
		return processMode;
	}

	public void setProcessMode(int processMode) {
		this.processMode = processMode;
	}

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

}
