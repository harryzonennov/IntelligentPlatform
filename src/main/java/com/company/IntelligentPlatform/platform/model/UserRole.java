package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.*;

/**
 * Migrated from: ThorsteinPlatform - UserRole.java
 * Old table: UserRole (single shared DB)
 * New table: UserRole (schema: platform)
 * Hierarchy: ServiceEntityNode → ReferenceNode → UserRole
 * Links a LogonUser to a Role (refUUID = Role.uuid)
 */
@Entity
@Table(name = "UserRole", catalog = "platform")
public class UserRole extends ReferenceNode {

	public static final String NODENAME = IServiceModelConstants.UserRole;

	public static final String SENAME = LogonUser.SENAME;

}
