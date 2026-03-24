package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.model.ServiceEntityRegisterEntity;

public class UIModelMapComConfigure {
	
	protected List<UIModelNodeMapConfigure> uiModelNodeMapConfigureList = new ArrayList<UIModelNodeMapConfigure>();
	
	protected ServiceEntityRegisterEntity seRegisterEntity;
	
	protected Class<?> managerType;
	
	protected String seManagerVarName;
	
	protected boolean hostSEFlag;
	
	protected int appendToHostType;
	
	protected String serviceEntityName;

	public String getServiceEntityName() {
		return serviceEntityName;
	}

	public void setServiceEntityName(String serviceEntityName) {
		this.serviceEntityName = serviceEntityName;
	}

	public List<UIModelNodeMapConfigure> getUiModelNodeMapConfigureList() {
		return uiModelNodeMapConfigureList;
	}

	public void setUiModelNodeMapConfigureList(
			List<UIModelNodeMapConfigure> uiModelNodeMapConfigureList) {
		this.uiModelNodeMapConfigureList = uiModelNodeMapConfigureList;
	}

	public ServiceEntityRegisterEntity getSeRegisterEntity() {
		return seRegisterEntity;
	}

	public void setSeRegisterEntity(ServiceEntityRegisterEntity seRegisterEntity) {
		this.seRegisterEntity = seRegisterEntity;
	}

	public String getSeManagerVarName() {
		return seManagerVarName;
	}

	public void setSeManagerVarName(String seManagerVarName) {
		this.seManagerVarName = seManagerVarName;
	}

	public boolean isHostSEFlag() {
		return hostSEFlag;
	}

	public void setHostSEFlag(boolean hostSEFlag) {
		this.hostSEFlag = hostSEFlag;
	}

	public int getAppendToHostType() {
		return appendToHostType;
	}

	public void setAppendToHostType(int appendToHostType) {
		this.appendToHostType = appendToHostType;
	}

	public Class<?> getManagerType() {
		return managerType;
	}

	public void setManagerType(Class<?> managerType) {
		this.managerType = managerType;
	}

	/**
	 * Add uiModelNodeMapConfigure to list variables with unique check of node name
	 * @param uiModelNodeMapConfigure
	 */
	public void addUIModelNodeMapConfigure (UIModelNodeMapConfigure uiModelNodeMapConfigure){
		for(UIModelNodeMapConfigure tmpNodeMapConfigure:this.uiModelNodeMapConfigureList){
			String tempNodeInstId = tmpNodeMapConfigure.getNodeInstID();
			String nodeInstId = uiModelNodeMapConfigure.getNodeInstID();
			boolean equalFlag = nodeInstId.equals(tempNodeInstId);
			if(equalFlag){
				return;
			}
		}
		this.uiModelNodeMapConfigureList.add(uiModelNodeMapConfigure);
	}

}
