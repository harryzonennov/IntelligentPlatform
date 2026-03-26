package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
@Entity
@Table(name = "MaterialConfigureTemplate", catalog = "platform")
public class MaterialConfigureTemplate extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.MaterialConfigureTemplate;

	public MaterialConfigureTemplate() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}
