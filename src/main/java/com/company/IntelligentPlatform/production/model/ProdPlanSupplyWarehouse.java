package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ReferenceNode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ProdPlanSupplyWarehouse", catalog = "production")
public class ProdPlanSupplyWarehouse extends ReferenceNode{

	public final static String NODENAME = IServiceModelConstants.ProdPlanSupplyWarehouse;

	public final static String SENAME = ProductionPlan.SENAME;

	public ProdPlanSupplyWarehouse() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}
