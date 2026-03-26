package com.company.IntelligentPlatform.finance.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.finance.model.FinAccountTitle;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;

@Component
public class FinAccountTitleSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "settleType", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected int settleType;

	@BSearchFieldConfig(fieldName = "originalType", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected int originalType;

	@BSearchFieldConfig(fieldName = "finAccountType", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected int finAccountType;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "rootAccountTitleUUID", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected String rootAccountTitleUUID;

	@BSearchFieldConfig(fieldName = "client", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected String client;

	@BSearchFieldConfig(fieldName = "generateType", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected int generateType;

	@BSearchFieldConfig(fieldName = "category", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected int category;

	@BSearchFieldConfig(fieldName = "name", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "parentAccountTitleUUID", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected String parentAccountTitleUUID;

	@BSearchFieldConfig(fieldName = "id", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "id", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected String parentAccountTitleId;

	@BSearchFieldConfig(fieldName = "name", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected String parentAccountTitleName;

	@BSearchFieldConfig(fieldName = "name", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected String rootAccountTitleName;

	@BSearchFieldConfig(fieldName = "id", nodeName = FinAccountTitle.NODENAME, seName = FinAccountTitle.SENAME, nodeInstID = FinAccountTitle.SENAME)
	protected String rootAccountTitleId;

	public int getSettleType() {
		return this.settleType;
	}

	public void setSettleType(int settleType) {
		this.settleType = settleType;
	}

	public int getOriginalType() {
		return this.originalType;
	}

	public void setOriginalType(int originalType) {
		this.originalType = originalType;
	}

	public int getFinAccountType() {
		return this.finAccountType;
	}

	public void setFinAccountType(int finAccountType) {
		this.finAccountType = finAccountType;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRootAccountTitleUUID() {
		return rootAccountTitleUUID;
	}

	public void setRootAccountTitleUUID(String rootAccountTitleUUID) {
		this.rootAccountTitleUUID = rootAccountTitleUUID;
	}

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public int getGenerateType() {
		return this.generateType;
	}

	public void setGenerateType(int generateType) {
		this.generateType = generateType;
	}

	public int getCategory() {
		return this.category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getParentAccountTitleUUID() {
		return parentAccountTitleUUID;
	}

	public void setParentAccountTitleUUID(String parentAccountTitleUUID) {
		this.parentAccountTitleUUID = parentAccountTitleUUID;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentAccountTitleId() {
		return this.parentAccountTitleId;
	}

	public void setParentAccountTitleId(String parentAccountTitleId) {
		this.parentAccountTitleId = parentAccountTitleId;
	}

	public String getParentAccountTitleName() {
		return this.parentAccountTitleName;
	}

	public void setParentAccountTitleName(String parentAccountTitleName) {
		this.parentAccountTitleName = parentAccountTitleName;
	}

	public String getRootAccountTitleName() {
		return this.rootAccountTitleName;
	}

	public void setRootAccountTitleName(String rootAccountTitleName) {
		this.rootAccountTitleName = rootAccountTitleName;
	}

	public String getRootAccountTitleId() {
		return this.rootAccountTitleId;
	}

	public void setRootAccountTitleId(String rootAccountTitleId) {
		this.rootAccountTitleId = rootAccountTitleId;
	}

}
