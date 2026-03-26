package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "IndividualCustomer", catalog = "platform")
public class IndividualCustomer extends IndividualAccount {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.IndividualCustomer;

	protected int customerType;

	protected String qqNumber;

	protected String weiboID;

	protected String weiXinID;

	protected String faceBookID;

	protected String baseCityUUID;

	public IndividualCustomer() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.regularType = REGULAR_TYPE_TEMP;
		this.accountType = ACCOUNTTYPE_IND_CUSTOMER;
	}

	public int getCustomerType() {
		return customerType;
	}

	public void setCustomerType(int customerType) {
		this.customerType = customerType;
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

	public String getBaseCityUUID() {
		return baseCityUUID;
	}

	public void setBaseCityUUID(String baseCityUUID) {
		this.baseCityUUID = baseCityUUID;
	}

}
