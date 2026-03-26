package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "HostCompany", catalog = "platform")
public class HostCompany extends Organization{	
	
	protected byte[] comLogo;

    protected String comReportTitle;
    
	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	
	public static final String SENAME = IServiceModelConstants.HostCompany;
	
	public HostCompany(){
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.organizationFunction = ORGFUNCTION_HOSTCOMPANY;
	}	

	public byte[] getComLogo() {
		return comLogo;
	}

	public void setComLogo(byte[] comLogo) {
		this.comLogo = comLogo;
	}

	public String getComReportTitle() {
		return comReportTitle;
	}

	public void setComReportTitle(String comReportTitle) {
		this.comReportTitle = comReportTitle;
	}

}
