package com.company.IntelligentPlatform.common.model;

import java.util.List;

/**
 * Basic Service UI Module Additional Conversion Parameter Union
 * @author Zhang,hang
 *
 */
public class ServiceModuleConvertPara {
	
	protected String nodeInstId;

	protected String targetNodeInstId;

	protected ServiceConvertMeta meta;
	
	protected ServiceEntityNode serviceEntityNode;
	
	protected List<ServiceEntityNode> serviceEntityList;

	public ServiceModuleConvertPara() {
	}

	public ServiceModuleConvertPara(String nodeInstId, String targetNodeInstId,
									List<ServiceEntityNode> serviceEntityList) {
		this.nodeInstId = nodeInstId;
		this.targetNodeInstId = targetNodeInstId;
		this.serviceEntityList = serviceEntityList;
	}

	public ServiceModuleConvertPara(String targetNodeInstId, ServiceConvertMeta meta) {
		this.targetNodeInstId = targetNodeInstId;
		this.meta = meta;
	}

	public String getNodeInstId() {
		return nodeInstId;
	}

	public void setNodeInstId(String nodeInstId) {
		this.nodeInstId = nodeInstId;
	}

	public ServiceEntityNode getServiceEntityNode() {
		return serviceEntityNode;
	}

	public void setServiceEntityNode(ServiceEntityNode serviceEntityNode) {
		this.serviceEntityNode = serviceEntityNode;
	}

	public List<ServiceEntityNode> getServiceEntityList() {
		return serviceEntityList;
	}

	public void setServiceEntityList(List<ServiceEntityNode> serviceEntityList) {
		this.serviceEntityList = serviceEntityList;
	}

	public String getTargetNodeInstId() {
		return targetNodeInstId;
	}

	public void setTargetNodeInstId(String targetNodeInstId) {
		this.targetNodeInstId = targetNodeInstId;
	}

	public ServiceConvertMeta getMeta() {
		return meta;
	}

	public void setMeta(ServiceConvertMeta meta) {
		this.meta = meta;
	}
}
