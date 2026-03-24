package com.company.IntelligentPlatform.finance.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountAttachment;
import com.company.IntelligentPlatform.finance.model.FinAccountLog;
import com.company.IntelligentPlatform.finance.model.FinAccountMaterialItem;
import com.company.IntelligentPlatform.finance.model.FinAccountObjectRef;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class FinAccountServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = FinAccount.NODENAME, nodeInstId = FinAccount.SENAME)
	protected FinAccount finAccount;
	
	@IServiceModuleFieldConfig(nodeName = FinAccountObjectRef.NODENAME, nodeInstId = FinAccountObjectRef.NODENAME)
	protected FinAccountObjectRef finAccountObjectRef;

	@IServiceModuleFieldConfig(nodeName = FinAccountLog.NODENAME, nodeInstId = FinAccountLog.NODENAME)
	protected List<ServiceEntityNode> finAccountLogList = new ArrayList<ServiceEntityNode>();
	
	@IServiceModuleFieldConfig(nodeName = FinAccountAttachment.NODENAME, nodeInstId = FinAccountAttachment.NODENAME)
	protected List<ServiceEntityNode> finAccountAttachmentList = new ArrayList<ServiceEntityNode>();
	
	@IServiceModuleFieldConfig(nodeName = FinAccountMaterialItem.NODENAME, nodeInstId = FinAccountMaterialItem.NODENAME)
	protected List<FinAccountMaterialItemServiceModel> finAccountMaterialItemList = new ArrayList<>();

	public List<ServiceEntityNode> getFinAccountLogList() {
		return this.finAccountLogList;
	}

	public void setFinAccountLogList(List<ServiceEntityNode> finAccountLogList) {
		this.finAccountLogList = finAccountLogList;
	}

	public FinAccount getFinAccount() {
		return this.finAccount;
	}

	public void setFinAccount(FinAccount finAccount) {
		this.finAccount = finAccount;
	}

	public List<ServiceEntityNode> getFinAccountAttachmentList() {
		return finAccountAttachmentList;
	}

	public void setFinAccountAttachmentList(
			List<ServiceEntityNode> finAccountAttachmentList) {
		this.finAccountAttachmentList = finAccountAttachmentList;
	}

	public FinAccountObjectRef getFinAccountObjectRef() {
		return finAccountObjectRef;
	}

	public void setFinAccountObjectRef(FinAccountObjectRef finAccountObjectRef) {
		this.finAccountObjectRef = finAccountObjectRef;
	}

	public List<FinAccountMaterialItemServiceModel> getFinAccountMaterialItemList() {
		return finAccountMaterialItemList;
	}

	public void setFinAccountMaterialItemList(
			List<FinAccountMaterialItemServiceModel> finAccountMaterialItemList) {
		this.finAccountMaterialItemList = finAccountMaterialItemList;
	}

}
