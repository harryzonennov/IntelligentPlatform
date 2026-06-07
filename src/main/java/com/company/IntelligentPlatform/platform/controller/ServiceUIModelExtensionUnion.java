package com.company.IntelligentPlatform.platform.controller;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.platform.service.ServiceEntityManager;

public class ServiceUIModelExtensionUnion {

	protected String nodeInstId;

	protected String nodeName;

	protected UIModelNodeMapConfigure toParentModelNodeMapConfigure;

	protected List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();

	protected ServiceEntityManager serviceEntityManager;

	public List<UIModelNodeMapConfigure> getUiModelNodeMapList() {
		return uiModelNodeMapList;
	}

	public void setUiModelNodeMapList(
			List<UIModelNodeMapConfigure> uiModelNodeMapList) {
		this.uiModelNodeMapList = uiModelNodeMapList;
	}

	public UIModelNodeMapConfigure getToParentModelNodeMapConfigure() {
		return toParentModelNodeMapConfigure;
	}

	public void setToParentModelNodeMapConfigure(
			UIModelNodeMapConfigure toParentModelNodeMapConfigure) {
		this.toParentModelNodeMapConfigure = toParentModelNodeMapConfigure;
	}

	public String getNodeInstId() {
		return nodeInstId;
	}

	public void setNodeInstId(String nodeInstId) {
		this.nodeInstId = nodeInstId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public ServiceEntityManager getServiceEntityManager() {
		return serviceEntityManager;
	}

	public void setServiceEntityManager(ServiceEntityManager serviceEntityManager) {
		this.serviceEntityManager = serviceEntityManager;
	}

}
