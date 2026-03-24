package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.controller.FinAccountLogUIModel;
import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountAttachment;
import com.company.IntelligentPlatform.finance.model.FinAccountLog;
import com.company.IntelligentPlatform.finance.model.FinAccountMaterialItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class FinAccountServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = FinAccount.NODENAME, nodeInstId = FinAccount.SENAME, convToUIMethod = FinAccountManager.METHOD_ConvFinAccountToUI, convUIToMethod = FinAccountManager.METHOD_ConvUIToFinAccount)
	protected FinAccountUIModel finAccountUIModel;

	@IServiceUIModuleFieldConfig(nodeName = FinAccountLog.NODENAME, nodeInstId = FinAccountLog.NODENAME, 
			convToUIMethod = FinAccountManager.METHOD_ConvFinAccountLogToUI)
	protected List<FinAccountLogUIModel> finAccountLogUIModelList = new ArrayList<FinAccountLogUIModel>();

	@IServiceUIModuleFieldConfig(nodeName = FinAccountAttachment.NODENAME, nodeInstId = FinAccountAttachment.NODENAME, 
			convToUIMethod = FinAccountManager.METHOD_ConvFinAccountAttachmentToUI)
	protected List<FinAccountAttachmentUIModel> finAccountAttachmentUIModelList = new ArrayList<FinAccountAttachmentUIModel>();

	@IServiceUIModuleFieldConfig(nodeName = FinAccountMaterialItem.NODENAME , nodeInstId = FinAccountMaterialItem.NODENAME)
	protected List<FinAccountMaterialItemServiceUIModel> finAccountMaterialItemUIModelList = new ArrayList<FinAccountMaterialItemServiceUIModel>();
	
	public FinAccountUIModel getFinAccountUIModel() {
		return this.finAccountUIModel;
	}

	public void setFinAccountUIModel(FinAccountUIModel finAccountUIModel) {
		this.finAccountUIModel = finAccountUIModel;
	}

	public List<FinAccountLogUIModel> getFinAccountLogUIModelList() {
		return this.finAccountLogUIModelList;
	}

	public void setFinAccountLogUIModelList(
			List<FinAccountLogUIModel> finAccountLogUIModelList) {
		this.finAccountLogUIModelList = finAccountLogUIModelList;
	}

	public List<FinAccountAttachmentUIModel> getFinAccountAttachmentUIModelList() {
		return finAccountAttachmentUIModelList;
	}

	public void setFinAccountAttachmentUIModelList(
			List<FinAccountAttachmentUIModel> finAccountAttachmentUIModelList) {
		this.finAccountAttachmentUIModelList = finAccountAttachmentUIModelList;
	}

	public List<FinAccountMaterialItemServiceUIModel> getFinAccountMaterialItemUIModelList() {
		return finAccountMaterialItemUIModelList;
	}

	public void setFinAccountMaterialItemUIModelList(
			List<FinAccountMaterialItemServiceUIModel> finAccountMaterialItemUIModelList) {
		this.finAccountMaterialItemUIModelList = finAccountMaterialItemUIModelList;
	}

}
