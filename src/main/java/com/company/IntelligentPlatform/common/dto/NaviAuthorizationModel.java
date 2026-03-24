package com.company.IntelligentPlatform.common.dto;

/**
 * UI Navigation authorization model, will be passed to UI layer to control the navigation access 
 * @author Zhang,Hang
 *
 */
public class NaviAuthorizationModel {
	
	public static int ACCESS_FLAG_ENABLED = 1;
	
	public static int ACCESS_FLAG_DISABLED = 2;
	
	/**
	 * Access Flag of LCM management Center
	 */
	protected int lcmAccessFlag;
	
	/**
	 * Access Flag of LCM resource Center
	 */
	protected int lcmResourceAccessFlag;
	
	/**
	 * Access Flag of Finance Center
	 */
	protected int finAccessFlag;
	
	/**
	 * Access Flag of Office Center
	 */
	protected int officeAccessFlag;
	
	/**
	 * Access Flag of System Admin Flag
	 */
	protected int systemAccessFlag;
	
	public NaviAuthorizationModel(){
		this.lcmAccessFlag = ACCESS_FLAG_DISABLED;
		this.lcmResourceAccessFlag = ACCESS_FLAG_DISABLED;
		this.officeAccessFlag = ACCESS_FLAG_DISABLED;
		this.finAccessFlag = ACCESS_FLAG_DISABLED;
		this.systemAccessFlag = ACCESS_FLAG_DISABLED;
	}

	public int getLcmAccessFlag() {
		return lcmAccessFlag;
	}

	public void setLcmAccessFlag(int lcmAccessFlag) {
		this.lcmAccessFlag = lcmAccessFlag;
	}

	public int getLcmResourceAccessFlag() {
		return lcmResourceAccessFlag;
	}

	public void setLcmResourceAccessFlag(int lcmResourceAccessFlag) {
		this.lcmResourceAccessFlag = lcmResourceAccessFlag;
	}

	public int getFinAccessFlag() {
		return finAccessFlag;
	}

	public void setFinAccessFlag(int finAccessFlag) {
		this.finAccessFlag = finAccessFlag;
	}

	public int getOfficeAccessFlag() {
		return officeAccessFlag;
	}

	public void setOfficeAccessFlag(int officeAccessFlag) {
		this.officeAccessFlag = officeAccessFlag;
	}

	public int getSystemAccessFlag() {
		return systemAccessFlag;
	}

	public void setSystemAccessFlag(int systemAccessFlag) {
		this.systemAccessFlag = systemAccessFlag;
	}
	
}
