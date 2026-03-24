package com.company.IntelligentPlatform.finance.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.model.FinAccountMatItemAttachment;
import com.company.IntelligentPlatform.finance.model.FinAccountMaterialItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class FinAccountMaterialItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = FinAccountMaterialItem.NODENAME, nodeInstId = FinAccountMaterialItem.NODENAME)
	protected FinAccountMaterialItem finAccountMaterialItem;	

	@IServiceModuleFieldConfig(nodeName = FinAccountMatItemAttachment.NODENAME, nodeInstId = FinAccountMatItemAttachment.NODENAME)
	protected List<ServiceEntityNode> finAccountMatItemAttachmentList = new ArrayList<ServiceEntityNode>();

	public FinAccountMaterialItem getFinAccountMaterialItem() {
		return finAccountMaterialItem;
	}

	public void setFinAccountMaterialItem(
			FinAccountMaterialItem finAccountMaterialItem) {
		this.finAccountMaterialItem = finAccountMaterialItem;
	}

	public List<ServiceEntityNode> getFinAccountMatItemAttachmentList() {
		return finAccountMatItemAttachmentList;
	}

	public void setFinAccountMatItemAttachmentList(
			List<ServiceEntityNode> finAccountMatItemAttachmentList) {
		this.finAccountMatItemAttachmentList = finAccountMatItemAttachmentList;
	}

}
