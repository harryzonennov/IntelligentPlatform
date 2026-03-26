package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Organization Function
 * 
 * @author ZhangHang
 * @date Nov 25, 2016
 * 
 */
@Entity
@Table(name = "OrganizationFunction", schema = "platform")
public class OrganizationFunction extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;	

	public static final String SENAME = IServiceModelConstants.OrganizationFunction;
	
	public static final String ID_HOSTCOMPANY = "OT01";
	
    public static final String ID_SUBSIDIARY = "OT02";
	
	public static final String ID_SALES_DEPT = "OT03";
	
	public static final String ID_PROD_DEPT = "OT04";
	
	public static final String ID_DEV_DEPT = "OT05";
	
	public static final String ID_TRANSSITE = "OT06";
	
	public static final String ID_UNIDEPT = "OT07";
	
	protected int switchFlag;

	public OrganizationFunction() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.switchFlag = StandardSwitchProxy.SWITCH_ON;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

}
