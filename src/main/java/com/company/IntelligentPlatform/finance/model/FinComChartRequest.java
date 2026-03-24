package com.company.IntelligentPlatform.finance.model;

import java.util.Date;

import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;

public class FinComChartRequest {

	protected String accountTitleUUID;

	protected String accountTitleName;

	protected String accountTitleID;

	protected int unit;

	protected String startDate;

	protected String endDate;

	protected String accObjectUUID1;

	protected String accObjectName1;

	protected String accObjectUUID2;

	protected String accObjectName2;

	protected String accObjectUUID3;

	protected String accObjectName3;

	protected String accObjectUUID4;

	protected String accObjectName4;

	public FinComChartRequest(){
		endDate = DefaultDateFormatConstant.DATE_MIN_FORMAT.format(new Date());
	}

	public String getAccountTitleUUID() {
		return accountTitleUUID;
	}

	public void setAccountTitleUUID(String accountTitleUUID) {
		this.accountTitleUUID = accountTitleUUID;
	}

	public String getAccountTitleName() {
		return accountTitleName;
	}

	public void setAccountTitleName(String accountTitleName) {
		this.accountTitleName = accountTitleName;
	}

	public String getAccountTitleID() {
		return accountTitleID;
	}

	public void setAccountTitleID(String accountTitleID) {
		this.accountTitleID = accountTitleID;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAccObjectUUID1() {
		return accObjectUUID1;
	}

	public void setAccObjectUUID1(String accObjectUUID1) {
		this.accObjectUUID1 = accObjectUUID1;
	}

	public String getAccObjectName1() {
		return accObjectName1;
	}

	public void setAccObjectName1(String accObjectName1) {
		this.accObjectName1 = accObjectName1;
	}

	public String getAccObjectUUID2() {
		return accObjectUUID2;
	}

	public void setAccObjectUUID2(String accObjectUUID2) {
		this.accObjectUUID2 = accObjectUUID2;
	}

	public String getAccObjectName2() {
		return accObjectName2;
	}

	public void setAccObjectName2(String accObjectName2) {
		this.accObjectName2 = accObjectName2;
	}

	public String getAccObjectUUID3() {
		return accObjectUUID3;
	}

	public void setAccObjectUUID3(String accObjectUUID3) {
		this.accObjectUUID3 = accObjectUUID3;
	}

	public String getAccObjectName3() {
		return accObjectName3;
	}

	public void setAccObjectName3(String accObjectName3) {
		this.accObjectName3 = accObjectName3;
	}

	public String getAccObjectUUID4() {
		return accObjectUUID4;
	}

	public void setAccObjectUUID4(String accObjectUUID4) {
		this.accObjectUUID4 = accObjectUUID4;
	}

	public String getAccObjectName4() {
		return accObjectName4;
	}

	public void setAccObjectName4(String accObjectName4) {
		this.accObjectName4 = accObjectName4;
	}

}
