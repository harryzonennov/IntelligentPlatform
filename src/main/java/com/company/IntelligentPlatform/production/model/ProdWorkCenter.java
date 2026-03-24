package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

/**
 * Migrated from: ThorsteinProduction - ProdWorkCenter (extends Organization/ServiceEntityNode)
 * Table: ProdWorkCenter (schema: production)
 *
 * Note: In the original, ProdWorkCenter extended Organization (a platform entity).
 * In the unified project it extends ServiceEntityNode directly to avoid cross-schema inheritance.
 */
@Entity
@Table(name = "ProdWorkCenter", schema = "production")
public class ProdWorkCenter extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.ProdWorkCenter;

	public static final int CAPACITY_CAL_TYPE_MACHINE  = 1;

	public static final int CAPACITY_CAL_TYPE_PERSON   = 2;

	public static final int CAPACITY_CAL_TYPE_COMBINED = 3;

	@Column(name = "keyWorkCenterFlag")
	protected int keyWorkCenterFlag;

	@Column(name = "refCostCenterUUID")
	protected String refCostCenterUUID;

	@Column(name = "usageNote", length = 1000)
	protected String usageNote;

	@Column(name = "refGroupUUID")
	protected String refGroupUUID;

	@Column(name = "capacityCalculateType")
	protected int capacityCalculateType;

	@Column(name = "fullName", length = 500)
	protected String fullName;

	@Column(name = "keyWorkCenter")
	protected boolean keyWorkCenter;

	@Column(name = "address", length = 500)
	protected String address;

	@Column(name = "telephone", length = 100)
	protected String telephone;

	@Column(name = "fax", length = 100)
	protected String fax;

	@Column(name = "email", length = 200)
	protected String email;

	@Column(name = "webPage", length = 200)
	protected String webPage;

	@Column(name = "refCityUUID")
	protected String refCityUUID;

	@Column(name = "cityName", length = 200)
	protected String cityName;

	@Column(name = "countryName", length = 200)
	protected String countryName;

	@Column(name = "stateName", length = 200)
	protected String stateName;

	@Column(name = "townZone", length = 200)
	protected String townZone;

	@Column(name = "subArea", length = 200)
	protected String subArea;

	@Column(name = "streetName", length = 200)
	protected String streetName;

	@Column(name = "houseNumber", length = 100)
	protected String houseNumber;

	@Column(name = "postcode", length = 50)
	protected String postcode;

	@Column(name = "parentOrganizationUUID")
	protected String parentOrganizationUUID;

	@Column(name = "organizationFunction")
	protected int organizationFunction;

	public int getKeyWorkCenterFlag() {
		return keyWorkCenterFlag;
	}

	public void setKeyWorkCenterFlag(int keyWorkCenterFlag) {
		this.keyWorkCenterFlag = keyWorkCenterFlag;
	}

	public String getRefCostCenterUUID() {
		return refCostCenterUUID;
	}

	public void setRefCostCenterUUID(String refCostCenterUUID) {
		this.refCostCenterUUID = refCostCenterUUID;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean getKeyWorkCenter() {
		return keyWorkCenter;
	}

	public void setKeyWorkCenter(boolean keyWorkCenter) {
		this.keyWorkCenter = keyWorkCenter;
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

	public String getRefCityUUID() {
		return refCityUUID;
	}

	public void setRefCityUUID(String refCityUUID) {
		this.refCityUUID = refCityUUID;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getParentOrganizationUUID() {
		return parentOrganizationUUID;
	}

	public void setParentOrganizationUUID(String parentOrganizationUUID) {
		this.parentOrganizationUUID = parentOrganizationUUID;
	}

	public int getOrganizationFunction() {
		return organizationFunction;
	}

	public void setOrganizationFunction(int organizationFunction) {
		this.organizationFunction = organizationFunction;
	}

}
