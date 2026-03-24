package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ResFinAccountSetting extends ReferenceNode{
	
	public static final String NODENAME = IServiceModelConstants.ResFinAccountSetting;

	public static final String SENAME = SystemResource.SENAME;
	
	public static final int SWITCH_ON = 1;
	
	public static final int SWITCH_OFF = 2;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;

	public ResFinAccountSetting() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		switchFlag = SWITCH_ON;
	}
	
	protected int refFinAccObjectKey;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String refFinAccObjectProxyClass;
	
	protected int switchFlag;
	
	protected String coreSettleID;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String settleUIModelName;
	
	protected String allAmountFieldName;
	
	protected String toSettleFieldName;
	
	protected String settledFieldName;

	public int getRefFinAccObjectKey() {
		return refFinAccObjectKey;
	}

	public void setRefFinAccObjectKey(int refFinAccObjectKey) {
		this.refFinAccObjectKey = refFinAccObjectKey;
	}

	public String getRefFinAccObjectProxyClass() {
		return refFinAccObjectProxyClass;
	}

	public void setRefFinAccObjectProxyClass(String refFinAccObjectProxyClass) {
		this.refFinAccObjectProxyClass = refFinAccObjectProxyClass;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getCoreSettleID() {
		return coreSettleID;
	}

	public void setCoreSettleID(String coreSettleID) {
		this.coreSettleID = coreSettleID;
	}

	public String getSettleUIModelName() {
		return settleUIModelName;
	}

	public void setSettleUIModelName(String settleUIModelName) {
		this.settleUIModelName = settleUIModelName;
	}

	public String getAllAmountFieldName() {
		return allAmountFieldName;
	}

	public void setAllAmountFieldName(String allAmountFieldName) {
		this.allAmountFieldName = allAmountFieldName;
	}

	public String getToSettleFieldName() {
		return toSettleFieldName;
	}

	public void setToSettleFieldName(String toSettleFieldName) {
		this.toSettleFieldName = toSettleFieldName;
	}

	public String getSettledFieldName() {
		return settledFieldName;
	}

	public void setSettledFieldName(String settledFieldName) {
		this.settledFieldName = settledFieldName;
	}

}
