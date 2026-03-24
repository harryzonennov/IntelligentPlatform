package com.company.IntelligentPlatform.finance.model;

import com.company.IntelligentPlatform.common.model.ReferenceNode;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinFinance - FinAccountObjectRef.java
 * New table: FinAccountObjectRef (schema: finance)
 * Links a FinAccount to an account object (Customer, Employee, Org) via refUUID.
 */
@Entity
@Table(name = "FinAccountObjectRef", schema = "finance")
public class FinAccountObjectRef extends ReferenceNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.FinAccountObjectRef;

}
