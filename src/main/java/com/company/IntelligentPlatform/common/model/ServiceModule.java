package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;

/**
 * Basic service model
 * @author Zhang,hang
 *
 */
public class ServiceModule implements Cloneable {

	protected ServiceJSONRequest serviceJSONRequest;
	
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			// Should raise exception
			return null;
		}
		return o;
	}

	public ServiceJSONRequest getServiceJSONRequest() {
		return serviceJSONRequest;
	}

	public void setServiceJSONRequest(ServiceJSONRequest serviceJSONRequest) {
		this.serviceJSONRequest = serviceJSONRequest;
	}
}
