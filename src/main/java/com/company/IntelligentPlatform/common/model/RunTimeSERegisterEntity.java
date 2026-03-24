package com.company.IntelligentPlatform.common.model;

// TODO-DAO: import platform.foundation.DAO.IServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Class to record runtime object for service entity registration will be used
 * by referenceProxy
 *
 * @author ZhangHang
 * @date Nov 26, 2012
 *
 */
public class RunTimeSERegisterEntity {

	protected String refSEName;

	protected ServiceEntityManager refSEManagerIns;

	protected Object refSEDAOIns; // TODO-DAO: was IServiceEntityDAO

	protected ServiceEntityConfigureProxy refSEConfigProxy;

	protected String refPackageName;

	public String getRefSEName() {
		return refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

	public ServiceEntityManager getRefSEManagerIns() {
		return refSEManagerIns;
	}

	public void setRefSEManagerIns(ServiceEntityManager refSEManagerIns) {
		this.refSEManagerIns = refSEManagerIns;
	}

	public Object getRefSEDAOIns() { // TODO-DAO: was IServiceEntityDAO
		return refSEDAOIns;
	}

	public void setRefSEDAOIns(Object refSEDAOIns) { // TODO-DAO: was IServiceEntityDAO
		this.refSEDAOIns = refSEDAOIns;
	}

	public ServiceEntityConfigureProxy getRefSEConfigProxy() {
		return refSEConfigProxy;
	}

	public void setRefSEConfigProxy(ServiceEntityConfigureProxy refSEConfigProxy) {
		this.refSEConfigProxy = refSEConfigProxy;
	}

	public String getRefPackageName() {
		return refPackageName;
	}

	public void setRefPackageName(String refPackageName) {
		this.refPackageName = refPackageName;
	}

}
