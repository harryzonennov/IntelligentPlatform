package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * wareHouse UI Model
 ** 
 * @author
 * @date Sun Nov 24 00:51:08 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class WarehouseSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = Warehouse.NODENAME, seName = "Warehouse", nodeInstID = "Warehouse")
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = Warehouse.NODENAME, seName = "Warehouse", nodeInstID = "Warehouse")
	protected String name;

	@BSearchFieldConfig(fieldName = "note", nodeName = Warehouse.NODENAME, seName = "Warehouse", nodeInstID = "Warehouse")
	protected String note;
	
	@BSearchFieldConfig(fieldName = "operationMode", nodeName = Warehouse.NODENAME, seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "WarehouseSearch_operationMode", valueFieldName = "")
	protected int operationMode;
	
	@BSearchFieldConfig(fieldName = "refMaterialCategory", nodeName = Warehouse.NODENAME, seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	protected int refMaterialCategory;

	@BSearchFieldConfig(fieldName = "address", nodeName = Warehouse.NODENAME, seName = "Warehouse", nodeInstID = "Warehouse")
	protected String address;

	@BSearchFieldConfig(fieldName = "telephone", nodeName = Warehouse.NODENAME, seName = Warehouse.SENAME,
			nodeInstID = Warehouse.SENAME)
	protected String telephone;

	@BSearchFieldConfig(fieldName = "mobile", nodeName = Warehouse.NODENAME, seName = "Warehouse", nodeInstID = "Warehouse")
	protected String contactMobileNumber;

	@BSearchFieldConfig(fieldName = "switchFlag", nodeName = Employee.NODENAME, seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	protected int switchFlag;

	@BSearchFieldConfig(fieldName = "name", nodeName = Employee.NODENAME, seName = "Employee", nodeInstID = "Employee")
	protected String contactEmployeeName;

	@BSearchFieldConfig(fieldName = "id", nodeName = Employee.NODENAME, seName = "Employee", nodeInstID = "Employee")
	protected String contactEmployeeID;

	@BSearchFieldConfig(fieldName = "id", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	protected String organizationId;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	protected String organizationUUID;

	@BSearchFieldConfig(fieldName = "name", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	protected String organizationName;
	
	@BSearchFieldConfig(fieldName = "uuid", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME, showOnUI = false)
	protected String refMaterialSKUUUID;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME, showOnUI = false)
	protected String refMaterialSKUName;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME, showOnUI = false)
	protected String refMaterialSKUID;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = CorporateCustomer.SENAME, showOnUI = false)
	protected String parentCustomerUUID;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = CorporateCustomer.SENAME)
	protected String parentCustomerName;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = CorporateCustomer.SENAME)
	protected String parentCustomerID;

	@BSearchFieldConfig(fieldName = "status", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = CorporateCustomer.SENAME)
	protected int parentCustomerStatus;
	
	/**
	 * Dummy search field, only be used for page split function on UI
	 * [Important], should be reset as 0 before real search
	 */
	protected int currentPage;
	
	@BSearchFieldConfig(fieldName = "systemDefault", nodeName = Warehouse.NODENAME, seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	protected int systemDefault;	

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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getRefMaterialCategory() {
		return refMaterialCategory;
	}

	public void setRefMaterialCategory(int refMaterialCategory) {
		this.refMaterialCategory = refMaterialCategory;
	}

	public String getContactMobileNumber() {
		return contactMobileNumber;
	}

	public void setContactMobileNumber(String contactMobileNumber) {
		this.contactMobileNumber = contactMobileNumber;
	}

	public String getContactEmployeeName() {
		return contactEmployeeName;
	}

	public void setContactEmployeeName(String contactEmployeeName) {
		this.contactEmployeeName = contactEmployeeName;
	}

	public String getContactEmployeeID() {
		return contactEmployeeID;
	}

	public void setContactEmployeeID(String contactEmployeeID) {
		this.contactEmployeeID = contactEmployeeID;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationUUID() {
		return organizationUUID;
	}

	public void setOrganizationUUID(String organizationUUID) {
		this.organizationUUID = organizationUUID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}

	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}

	public String getRefMaterialSKUID() {
		return refMaterialSKUID;
	}

	public void setRefMaterialSKUID(String refMaterialSKUID) {
		this.refMaterialSKUID = refMaterialSKUID;
	}

	public int getOperationMode() {
		return operationMode;
	}

	public void setOperationMode(int operationMode) {
		this.operationMode = operationMode;
	}

	public String getParentCustomerUUID() {
		return parentCustomerUUID;
	}

	public void setParentCustomerUUID(String parentCustomerUUID) {
		this.parentCustomerUUID = parentCustomerUUID;
	}

	public String getParentCustomerName() {
		return parentCustomerName;
	}

	public void setParentCustomerName(String parentCustomerName) {
		this.parentCustomerName = parentCustomerName;
	}

	public String getParentCustomerID() {
		return parentCustomerID;
	}

	public void setParentCustomerID(String parentCustomerID) {
		this.parentCustomerID = parentCustomerID;
	}

	public int getParentCustomerStatus() {
		return parentCustomerStatus;
	}

	public void setParentCustomerStatus(int parentCustomerStatus) {
		this.parentCustomerStatus = parentCustomerStatus;
	}

	public int getSystemDefault() {
		return systemDefault;
	}

	public void setSystemDefault(int systemDefault) {
		this.systemDefault = systemDefault;
	}

}
