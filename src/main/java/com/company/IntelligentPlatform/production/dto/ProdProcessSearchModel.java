package com.company.IntelligentPlatform.production.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * ProdProcess UI Model
 **
 * @author
 * @date Thu Mar 31 15:10:24 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class ProdProcessSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = ProdProcess.NODENAME, seName = ProdProcess.SENAME, nodeInstID = ProdProcess.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = ProdProcess.NODENAME, seName = ProdProcess.SENAME, nodeInstID = ProdProcess.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "keyProcessFlag", nodeName = ProdProcess.NODENAME, seName = ProdProcess.SENAME, nodeInstID = ProdProcess.SENAME)
	protected int keyProcessFlag;

	@BSearchFieldConfig(fieldName = "status", nodeName = ProdProcess.NODENAME, seName = ProdProcess.SENAME, nodeInstID = ProdProcess.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ProdProcessSearch_status", valueFieldName = "")
	protected int status;

	@BSearchFieldConfig(fieldName = "refWorkCenterUUID", nodeName = ProdProcess.NODENAME, seName = ProdProcess.SENAME, nodeInstID = ProdProcess.SENAME)
	protected String refWorkCenterUUID;

	@BSearchFieldConfig(fieldName = "id", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	protected String refWorkCenterID;

	@BSearchFieldConfig(fieldName = "name", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	protected String refWorkCenterName;
	/**
	 * Dummy search field, only be used for page split function on UI
	 * [Important], should be reset as 0 before real search
	 */
	protected int currentPage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKeyProcessFlag() {
		return keyProcessFlag;
	}

	public void setKeyProcessFlag(int keyProcessFlag) {
		this.keyProcessFlag = keyProcessFlag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRefWorkCenterUUID() {
		return refWorkCenterUUID;
	}

	public void setRefWorkCenterUUID(String refWorkCenterUUID) {
		this.refWorkCenterUUID = refWorkCenterUUID;
	}

	public String getRefWorkCenterID() {
		return refWorkCenterID;
	}

	public void setRefWorkCenterID(String refWorkCenterID) {
		this.refWorkCenterID = refWorkCenterID;
	}

	public String getRefWorkCenterName() {
		return refWorkCenterName;
	}

	public void setRefWorkCenterName(String refWorkCenterName) {
		this.refWorkCenterName = refWorkCenterName;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
