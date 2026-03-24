package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProdWorkCenterUIModel extends SEUIComModel {

	protected String fullName;

	protected int keyWorkCenterFlag;
	
	protected String keyWorkCenterValue;

	protected String refCostCenterUUID;

	protected String refCostCenterId;

	protected String refCostCenterName;

	protected String usageNote;

	protected String refGroupUUID;

	@ISEDropDownResourceMapping(resouceMapping = "ProdWorkCenter_capacityCalculateType", valueFieldName = "capacityCalculateTypeValue")
	protected int capacityCalculateType;

	protected String capacityCalculateTypeValue;
	
	protected boolean keyWorkCenter;
	
    protected String parentOrganizationUUID;

	protected String parentOrganizationId;	

	protected String parentOrganizationName;
	
	protected String telephone;

	protected int organType;

	protected int organLevel;
	
	protected String address;

	protected String mainContactUUID;

	protected String refCashierUUID;

	protected String refAccountantUUID;
	
	protected String refFinOrgUUID;
	
	protected int organizationFunction;
	
	protected String organizationFunctionValue;
	
	protected String refOrganizationFunction;
	
	protected String mobile;

	protected String fax;

	protected String email;

	protected String webPage;
	
	protected String postcode;
	
	protected String countryName;
	
	protected String stateName;
	
	protected String cityName;
	
	protected String refCityUUID;
	
	protected String townZone;
	
	protected String subArea;
	
	protected String streetName;
	
	protected String houseNumber;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getKeyWorkCenterFlag() {
		return keyWorkCenterFlag;
	}

	public void setKeyWorkCenterFlag(int keyWorkCenterFlag) {
		this.keyWorkCenterFlag = keyWorkCenterFlag;
	}

	public String getKeyWorkCenterValue() {
		return keyWorkCenterValue;
	}

	public void setKeyWorkCenterValue(String keyWorkCenterValue) {
		this.keyWorkCenterValue = keyWorkCenterValue;
	}

	public String getRefCostCenterUUID() {
		return refCostCenterUUID;
	}

	public void setRefCostCenterUUID(String refCostCenterUUID) {
		this.refCostCenterUUID = refCostCenterUUID;
	}

	public String getRefCostCenterId() {
		return refCostCenterId;
	}

	public void setRefCostCenterId(String refCostCenterId) {
		this.refCostCenterId = refCostCenterId;
	}

	public String getRefCostCenterName() {
		return refCostCenterName;
	}

	public void setRefCostCenterName(String refCostCenterName) {
		this.refCostCenterName = refCostCenterName;
	}

	public String getUsageNote() {
		return usageNote;
	}

	public void setUsageNote(String usageNote) {
		this.usageNote = usageNote;
	}

	public String getRefGroupUUID() {
		return refGroupUUID;
	}

	public void setRefGroupUUID(String refGroupUUID) {
		this.refGroupUUID = refGroupUUID;
	}

	public int getCapacityCalculateType() {
		return capacityCalculateType;
	}

	public void setCapacityCalculateType(int capacityCalculateType) {
		this.capacityCalculateType = capacityCalculateType;
	}

	public String getCapacityCalculateTypeValue() {
		return capacityCalculateTypeValue;
	}

	public void setCapacityCalculateTypeValue(String capacityCalculateTypeValue) {
		this.capacityCalculateTypeValue = capacityCalculateTypeValue;
	}

	public boolean getKeyWorkCenter() {
		return keyWorkCenter;
	}

	public void setKeyWorkCenter(boolean keyWorkCenter) {
		this.keyWorkCenter = keyWorkCenter;
	}

	public String getParentOrganizationUUID() {
		return parentOrganizationUUID;
	}

	public void setParentOrganizationUUID(String parentOrganizationUUID) {
		this.parentOrganizationUUID = parentOrganizationUUID;
	}

	public String getParentOrganizationId() {
		return parentOrganizationId;
	}

	public void setParentOrganizationId(String parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	public String getParentOrganizationName() {
		return parentOrganizationName;
	}

	public void setParentOrganizationName(String parentOrganizationName) {
		this.parentOrganizationName = parentOrganizationName;
	}

	public int getOrganType() {
		return organType;
	}

	public void setOrganType(int organType) {
		this.organType = organType;
	}

	public int getOrganLevel() {
		return organLevel;
	}

	public void setOrganLevel(int organLevel) {
		this.organLevel = organLevel;
	}

	public String getMainContactUUID() {
		return mainContactUUID;
	}

	public void setMainContactUUID(String mainContactUUID) {
		this.mainContactUUID = mainContactUUID;
	}

	public String getRefCashierUUID() {
		return refCashierUUID;
	}

	public void setRefCashierUUID(String refCashierUUID) {
		this.refCashierUUID = refCashierUUID;
	}

	public String getRefAccountantUUID() {
		return refAccountantUUID;
	}

	public void setRefAccountantUUID(String refAccountantUUID) {
		this.refAccountantUUID = refAccountantUUID;
	}

	public String getRefFinOrgUUID() {
		return refFinOrgUUID;
	}

	public void setRefFinOrgUUID(String refFinOrgUUID) {
		this.refFinOrgUUID = refFinOrgUUID;
	}

	public int getOrganizationFunction() {
		return organizationFunction;
	}

	public void setOrganizationFunction(int organizationFunction) {
		this.organizationFunction = organizationFunction;
	}

	public String getOrganizationFunctionValue() {
		return organizationFunctionValue;
	}

	public void setOrganizationFunctionValue(String organizationFunctionValue) {
		this.organizationFunctionValue = organizationFunctionValue;
	}

	public String getRefOrganizationFunction() {
		return refOrganizationFunction;
	}

	public void setRefOrganizationFunction(String refOrganizationFunction) {
		this.refOrganizationFunction = refOrganizationFunction;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getRefCityUUID() {
		return refCityUUID;
	}

	public void setRefCityUUID(String refCityUUID) {
		this.refCityUUID = refCityUUID;
	}

	public String getTownZone() {
		return townZone;
	}

	public void setTownZone(String townZone) {
		this.townZone = townZone;
	}

	public String getSubArea() {
		return subArea;
	}

	public void setSubArea(String subArea) {
		this.subArea = subArea;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	
}
