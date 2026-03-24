package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.MaterialSKUUnitReference;
import com.company.IntelligentPlatform.common.model.MaterialSKUUnitAttachment;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class MaterialSKUUnitServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = MaterialSKUUnitReference.NODENAME, nodeInstId = MaterialSKUUnitReference.NODENAME)
	protected MaterialSKUUnitReference materialSKUUnitReference;
	
	@IServiceModuleFieldConfig(nodeName = MaterialSKUUnitAttachment.NODENAME, nodeInstId = MaterialSKUUnitAttachment.NODENAME,
			blockUpdate = true)
	protected List<ServiceEntityNode> MaterialSKUUnitAttachmentList = new ArrayList<>();

	public MaterialSKUUnitReference getMaterialSKUUnitReference() {
		return materialSKUUnitReference;
	}

	public void setMaterialSKUUnitReference(MaterialSKUUnitReference materialSKUUnitReference) {
		this.materialSKUUnitReference = materialSKUUnitReference;
	}

	public List<ServiceEntityNode> getMaterialSKUUnitAttachmentList() {
		return MaterialSKUUnitAttachmentList;
	}

	public void setMaterialSKUUnitAttachmentList(List<ServiceEntityNode> materialUnitAttachmentList) {
		MaterialSKUUnitAttachmentList = materialUnitAttachmentList;
	}
}
