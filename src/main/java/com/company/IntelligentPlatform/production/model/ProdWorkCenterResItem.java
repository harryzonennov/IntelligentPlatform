package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.platform.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ReferenceNode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ProdWorkCenterResItem", catalog = "production")
public class ProdWorkCenterResItem extends ReferenceNode {

	public final static String NODENAME = IServiceModelConstants.ProdWorkCenterResItem;

	public final static String SENAME = ProdWorkCenter.SENAME;

	protected int keyResourceFlag;

	public ProdWorkCenterResItem(){
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.keyResourceFlag = StandardKeyFlagProxy.NON_KEY;
	}

	public int getKeyResourceFlag() {
		return keyResourceFlag;
	}

	public void setKeyResourceFlag(int keyResourceFlag) {
		this.keyResourceFlag = keyResourceFlag;
	}

}
