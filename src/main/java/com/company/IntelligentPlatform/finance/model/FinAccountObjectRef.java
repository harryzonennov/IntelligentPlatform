package com.company.IntelligentPlatform.finance.model;

import com.company.IntelligentPlatform.platform.model.ReferenceNode;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinFinance - FinAccountObjectRef.java
 * New table: FinAccountObjectRef (schema: finance)
 * Links a FinAccount to an account object (Customer, Employee, Org) via refUUID.
 */
@Entity
@Table(name = "FinAccountObjectRef", catalog = "finance")
public class FinAccountObjectRef extends ReferenceNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.FinAccountObjectRef;

}
