package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.City;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.dto.AccountSearchModel;

/**
 * IndividualCustomer UI Model
 ** 
 * @author
 * @date Mon Jul 01 16:04:38 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class IndividualCustomerSearchModel extends AccountSearchModel {

	@BSearchFieldConfig(fieldName = "uuid", nodeName = "ROOT", seName = "IndividualCustomer", nodeInstID = IndividualCustomer.SENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "name", nodeName = "ROOT", seName = "IndividualCustomer", nodeInstID = IndividualCustomer.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "mobile", nodeName = IndividualCustomer.NODENAME, seName = IndividualCustomer.SENAME, nodeInstID = IndividualCustomer.SENAME)
	protected String mobile;

	@BSearchFieldConfig(fieldName = "customerType", nodeName = "ROOT", seName = "IndividualCustomer", nodeInstID = IndividualCustomer.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "IndividualCustomerSearch_customerType", valueFieldName = "")
	protected int customerType;

	@BSearchFieldConfig(fieldName = "qqNumber", nodeName = IndividualCustomer.NODENAME, seName = IndividualCustomer.SENAME, nodeInstID = IndividualCustomer.SENAME)
	protected String qqNumber;

	@BSearchFieldConfig(fieldName = "weiboID", nodeName = IndividualCustomer.NODENAME, seName = IndividualCustomer.SENAME, nodeInstID = IndividualCustomer.SENAME)
	protected String weiboID;

	@BSearchFieldConfig(fieldName = "weiXinID", nodeName = IndividualCustomer.NODENAME, seName = IndividualCustomer.SENAME, nodeInstID = IndividualCustomer.SENAME)
	protected String weiXinID;
	
	@BSearchFieldConfig(fieldName = "address", nodeName = IndividualCustomer.NODENAME, seName = IndividualCustomer.SENAME, nodeInstID = IndividualCustomer.SENAME)
	protected String address;
	
	@BSearchFieldConfig(fieldName = "note", nodeName = IndividualCustomer.NODENAME, seName = IndividualCustomer.SENAME, nodeInstID = IndividualCustomer.SENAME)
	protected String note;

	@BSearchFieldConfig(fieldName = "faceBookID", nodeName = IndividualCustomer.NODENAME, seName = IndividualCustomer.SENAME, nodeInstID = IndividualCustomer.SENAME)
	protected String faceBookID;

	@BSearchFieldConfig(fieldName = "regularType", nodeName = IndividualCustomer.NODENAME, seName = IndividualCustomer.SENAME, nodeInstID = IndividualCustomer.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "IndividualCustomerSearch_regularType", valueFieldName = "")
	protected int regularType;

	/**
	 * Just indicate the current page on List UI should not be used for real
	 * search function, and should be set value 0 before search
	 */
	@BSearchFieldConfig(fieldName = "regularType", nodeName = "ROOT", seName = "IndividualCustomer", nodeInstID = IndividualCustomer.SENAME)
	protected int currentPage;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = City.NODENAME, seName = City.SENAME, nodeInstID = City.SENAME)
	protected String cityName;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getCustomerType() {
		return customerType;
	}

	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	public String getWeiboID() {
		return weiboID;
	}

	public void setWeiboID(String weiboID) {
		this.weiboID = weiboID;
	}

	public String getWeiXinID() {
		return weiXinID;
	}

	public void setWeiXinID(String weiXinID) {
		this.weiXinID = weiXinID;
	}

	public String getFaceBookID() {
		return faceBookID;
	}

	public void setFaceBookID(String faceBookID) {
		this.faceBookID = faceBookID;
	}

	public int getRegularType() {
		return regularType;
	}

	public void setRegularType(int regularType) {
		this.regularType = regularType;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
