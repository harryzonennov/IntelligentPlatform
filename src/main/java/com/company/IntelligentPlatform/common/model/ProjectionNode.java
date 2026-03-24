package com.company.IntelligentPlatform.common.model;

/**
 * Projection Node
 * 
 * @author Zhang,Hang
 * @date 2012-12-3
 * 
 */
public class ProjectionNode extends ServiceEntityNode {

	protected String refUUID;

	protected String refSEName;

	protected String refNodeName;

	protected String refPackageName;

	public ProjectionNode() {
		super.nodeSpecifyType = ServiceEntityNode.NODESPECIFYTYPE_PROJECTION;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getRefSEName() {
		return refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

	public String getRefNodeName() {
		return refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}

	public String getRefPackageName() {
		return refPackageName;
	}

	public void setRefPackageName(String refPackageName) {
		this.refPackageName = refPackageName;
	}

}
