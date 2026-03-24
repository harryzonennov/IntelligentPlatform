package com.company.IntelligentPlatform.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Concrete (instantiable) ServiceEntityNode for use in legacy code that created ServiceEntityNode()
 * objects purely as data transfer containers (id/name/note etc.).
 */
@Entity
@Table(name = "GenericServiceEntityNode")
public class GenericServiceEntityNode extends ServiceEntityNode {
    public static final String NODENAME = "GenericServiceEntityNode";
}
