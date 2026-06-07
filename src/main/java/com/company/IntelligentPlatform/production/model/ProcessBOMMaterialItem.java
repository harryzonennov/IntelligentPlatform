package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ReferenceNode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Reference point to Material SKU BOM Item, belongs to a
 * special process
 *
 * @author Zhang,Hang
 *
 */
@Entity
@Table(name = "ProcessBOMMaterialItem", catalog = "production")
public class ProcessBOMMaterialItem extends ReferenceNode {

	public final static String NODENAME = IServiceModelConstants.ProcessBOMMaterialItem;

	public final static String SENAME = ProcessBOMOrder.SENAME;

	protected String refMaterialSKUUUID;

	protected String refUnitUUID;

	protected double amount;

	public ProcessBOMMaterialItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
