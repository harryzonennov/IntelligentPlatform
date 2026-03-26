package com.company.IntelligentPlatform.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * Represents an optimistic/pessimistic edit lock on a ServiceEntityNode.
 *
 * <p>When a user opens a record for editing, a LockObject is created with:</p>
 * <ul>
 *   <li>{@code refUUID} = UUID of the locked entity</li>
 *   <li>{@code resEmployeeUUID} = UUID of the user who holds the lock</li>
 *   <li>{@code lockTimeDate} = timestamp when the lock was acquired</li>
 * </ul>
 *
 * <p>Table: {@code platform.LockObject}</p>
 */
@Entity
@Table(name = "LockObject", catalog = "platform")
public class LockObject extends ReferenceNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.LockObject;

	protected Date lockTimeDate;

	public LockObject() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

	public Date getLockTimeDate() {
		return lockTimeDate;
	}

	public void setLockTimeDate(Date lockTimeDate) {
		this.lockTimeDate = lockTimeDate;
	}

}
