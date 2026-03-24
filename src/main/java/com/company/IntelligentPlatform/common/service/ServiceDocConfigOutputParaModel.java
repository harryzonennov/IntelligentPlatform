package com.company.IntelligentPlatform.common.service;

import java.util.List;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class ServiceDocConfigOutputParaModel extends SEUIComModel{
	
	protected List<ServiceEntityNode> serviceDocList;
	
	protected List<ServiceEntityNode> docMaterialSKUItemList;
	
	protected double materialSKUAmount;
	
	protected String materialSKUUnit;

	public List<ServiceEntityNode> getServiceDocList() {
		return serviceDocList;
	}

	public void setServiceDocList(List<ServiceEntityNode> serviceDocList) {
		this.serviceDocList = serviceDocList;
	}

	public List<ServiceEntityNode> getDocMaterialSKUItemList() {
		return docMaterialSKUItemList;
	}

	public void setDocMaterialSKUItemList(
			List<ServiceEntityNode> docMaterialSKUItemList) {
		this.docMaterialSKUItemList = docMaterialSKUItemList;
	}

	public double getMaterialSKUAmount() {
		return materialSKUAmount;
	}

	public void setMaterialSKUAmount(double materialSKUAmount) {
		this.materialSKUAmount = materialSKUAmount;
	}

	public String getMaterialSKUUnit() {
		return materialSKUUnit;
	}

	public void setMaterialSKUUnit(String materialSKUUnit) {
		this.materialSKUUnit = materialSKUUnit;
	}

}
