package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.common.dto.AccountUIModel;

public class AccountObjectFinUIModel extends AccountUIModel{
	
	protected double grossAmount;
	
	protected double grossRecordedAmount;
	
	protected double grossToRecorededAmount;
	
	protected int finAccountRecord;

	public double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}

	public double getGrossRecordedAmount() {
		return grossRecordedAmount;
	}

	public void setGrossRecordedAmount(double grossRecordedAmount) {
		this.grossRecordedAmount = grossRecordedAmount;
	}

	public double getGrossToRecorededAmount() {
		return grossToRecorededAmount;
	}

	public void setGrossToRecorededAmount(double grossToRecorededAmount) {
		this.grossToRecorededAmount = grossToRecorededAmount;
	}

	public int getFinAccountRecord() {
		return finAccountRecord;
	}

	public void setFinAccountRecord(int finAccountRecord) {
		this.finAccountRecord = finAccountRecord;
	}

}
