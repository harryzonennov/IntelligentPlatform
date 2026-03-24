package com.company.IntelligentPlatform.common.model;

import java.util.Date;

import com.company.IntelligentPlatform.common.model.*;

public class Equipment extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = "Equipment";
	
	protected int equipmentType;
	
	public static final int EQT_TYPE_RFID_READER = 1;
	
	public static final int EQT_TYPE_BAR_CODE_PRINTER = 2;
	
	public static final int EQT_TYPE_BAR_CODE_SCANNER = 3;
	
	protected Date registerDate;
	
	protected String registerEmployeeUUID;
	
	protected String tradeMark;
	
	protected String modelType;
	
	protected String producerName;
	
	protected String producerUUID;
	
	protected Date producedDate;
	
	protected Date warrantyDate;
	
	public Equipment() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

	public int getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(int equipmentType) {
		this.equipmentType = equipmentType;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getRegisterEmployeeUUID() {
		return registerEmployeeUUID;
	}

	public void setRegisterEmployeeUUID(String registerEmployeeUUID) {
		this.registerEmployeeUUID = registerEmployeeUUID;
	}

	public String getTradeMark() {
		return tradeMark;
	}

	public void setTradeMark(String tradeMark) {
		this.tradeMark = tradeMark;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public String getProducerUUID() {
		return producerUUID;
	}

	public void setProducerUUID(String producerUUID) {
		this.producerUUID = producerUUID;
	}

	public Date getProducedDate() {
		return producedDate;
	}

	public void setProducedDate(Date producedDate) {
		this.producedDate = producedDate;
	}

	public Date getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(Date warrantyDate) {
		this.warrantyDate = warrantyDate;
	}
	
}
