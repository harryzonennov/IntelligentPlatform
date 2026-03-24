package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;

/**
 * City UI Model
 ** 
 * @author
 * @date Sun Mar 10 13:01:27 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class CityUIModel extends SEUIComModel {


	@ISEUIModelMapping(fieldName = "teleAreaCode", seName = "City", nodeName = "ROOT")
	protected String teleAreaCode;

	@ISEUIModelMapping(fieldName = "postcode", seName = "City", nodeName = "ROOT")
	protected String postcode;

	@ISEUIModelMapping(fieldName = "cityLevel", seName = "City", nodeName = "ROOT")
	protected int cityLevel;

	@ISEUIModelMapping(fieldName = "name", seName = "Province", nodeName = "ROOT")
	protected String provinceName;

	public String getTeleAreaCode() {
		return teleAreaCode;
	}

	public void setTeleAreaCode(String teleAreaCode) {
		this.teleAreaCode = teleAreaCode;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public int getCityLevel() {
		return cityLevel;
	}

	public void setCityLevel(int cityLevel) {
		this.cityLevel = cityLevel;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

}
