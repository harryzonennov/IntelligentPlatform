package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class MaterialUnitServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = MaterialUnitReference.NODENAME, nodeInstId = MaterialUnitReference.NODENAME)
	protected MaterialUnitReference materialUnitReference;
	
	@IServiceModuleFieldConfig(nodeName = MaterialUnitAttachment.NODENAME, nodeInstId = MaterialUnitAttachment.NODENAME,
			blockUpdate = true)
	protected List<ServiceEntityNode> MaterialUnitAttachmentList = new ArrayList<>();

	public MaterialUnitReference getMaterialUnitReference() {
		return materialUnitReference;
	}

	public void setMaterialUnitReference(MaterialUnitReference materialUnitReference) {
		this.materialUnitReference = materialUnitReference;
	}

	public List<ServiceEntityNode> getMaterialUnitAttachmentList() {
		return MaterialUnitAttachmentList;
	}

	public void setMaterialUnitAttachmentList(List<ServiceEntityNode> materialUnitAttachmentList) {
		MaterialUnitAttachmentList = materialUnitAttachmentList;
	}
}
