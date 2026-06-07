package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ReferenceNode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ProdOrderSupplyWarehouse", catalog = "production")
public class ProdOrderSupplyWarehouse extends ReferenceNode{

	public final static String NODENAME = IServiceModelConstants.ProdOrderSupplyWarehouse;

	public final static String SENAME = ProductionOrder.SENAME;

	public ProdOrderSupplyWarehouse() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}
