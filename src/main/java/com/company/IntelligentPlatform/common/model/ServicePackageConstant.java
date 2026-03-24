package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * System Package constant interface
 * @author Zhang,Hang
 *
 */
public class ServicePackageConstant {
	
	public static final String platform_foundation = "platform.foundation";
	
	public static final String net_thorstein_logistics = "net.thorstein.logistics";
	
	public static final String net_thorstein_finance = "net.thorstein.finance";
	
	public static final String net_thorstein_socialMedia = "net.thorstein.socialEngagement";
	
	public static final String net_servicespinner = "net.serviceSpinner";
	
	/**
	 * Constants for package proxy class id
	 */
	// Should be consistency for package proxy class id: platformPackageProxy;
	public static final String PROXY_PLATFORM_FOUNDATION = "platformPackageProxy";
	
	// Should be consistency for package proxy class id: logisticsPacakageProxy;
	public static final String PROXY_LOGISTICS = "logisticsPacakageProxy";
	
	// Should be consistency for package proxy class id: financePackageProxy;
	public static final String PROXY_FINANCE = "financePackageProxy";
	
	// Should be consistency for package proxy class id: socialMediaPackageProxy;
	public static final String PROXY_SOCIALMEDIA = "socialMediaPackageProxy";
	
	// Should be consistency for package proxy class id: installationPackageProxy;
	public static final String PROXY_INSTALLATION = "installationPackageProxy";
	
	public static final List<String> getAllPackageNameList(){
		List<String> nameList = new ArrayList<String>();
		nameList.add(platform_foundation);
		nameList.add(net_thorstein_logistics);
		nameList.add(net_thorstein_finance);
		nameList.add(net_servicespinner);
		nameList.add(net_thorstein_socialMedia);
		return nameList;
	}
	
	public static final List<String> getAllPackageProxyList(){
		List<String> nameList = new ArrayList<String>();
		nameList.add(PROXY_PLATFORM_FOUNDATION);
		nameList.add(PROXY_LOGISTICS);
		nameList.add(PROXY_FINANCE);
		nameList.add(PROXY_SOCIALMEDIA);
		nameList.add(PROXY_INSTALLATION);
		return nameList;
	}

}
