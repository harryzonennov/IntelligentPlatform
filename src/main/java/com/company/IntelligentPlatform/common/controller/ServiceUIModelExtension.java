package com.company.IntelligentPlatform.common.controller;

import java.util.List;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class ServiceUIModelExtension {

	protected List<ServiceUIModelExtensionUnion> uiModelExtensionUnionList;

	protected List<ServiceUIModelExtension> childUIModelExtensionList;

	protected List<String> blockSaveSubNodeNameList;

	public ServiceUIModelExtension(){

	}
	
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() throws ServiceEntityConfigureException {
		return this.uiModelExtensionUnionList;
	}
	
	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		return this.childUIModelExtensionList;
	}

	public void setUIModelExtensionUnion(List<ServiceUIModelExtensionUnion> uiModelExtensionUnionList){
		this.uiModelExtensionUnionList = uiModelExtensionUnionList;
	}

	public void setChildUIModelExtensions(List<ServiceUIModelExtension> childUIModelExtentsionList){
		this.childUIModelExtensionList = childUIModelExtentsionList;
	}

	public List<String> getBlockSaveSubNodeNameList() {
		return blockSaveSubNodeNameList;
	}

	public void setBlockSaveSubNodeNameList(List<String> blockSaveSubNodeNameList) {
		this.blockSaveSubNodeNameList = blockSaveSubNodeNameList;
	}

}
