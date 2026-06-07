package com.company.IntelligentPlatform.production.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Entity
@Table(name = "ProdJobMaterialItem", catalog = "production")
public class ProdJobMaterialItem extends ServiceEntityNode {

	public final static String NODENAME = IServiceModelConstants.ProdJobMaterialItem;

	public final static String SENAME = ProdJobOrder.SENAME;

	protected String refMaterialSKUUUID;

	protected double amount;

	protected String refUnitUUID;

	public ProdJobMaterialItem(){
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

}
