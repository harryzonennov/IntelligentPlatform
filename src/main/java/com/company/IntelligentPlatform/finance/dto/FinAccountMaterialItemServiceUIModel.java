package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;





import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.model.FinAccountMatItemAttachment;
import com.company.IntelligentPlatform.finance.model.FinAccountMaterialItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class FinAccountMaterialItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = FinAccountMaterialItem.NODENAME, nodeInstId = FinAccountMaterialItem.NODENAME, 
			convToUIMethod = FinAccountManager.METHOD_ConvFinAccountMaterialItemToUI, convUIToMethod = FinAccountManager.METHOD_ConvUIToFinAccountMaterialItem)
	protected FinAccountMaterialItemUIModel finAccountMaterialItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = FinAccountMatItemAttachment.NODENAME, nodeInstId = FinAccountMatItemAttachment.NODENAME, 
			convToUIMethod = FinAccountManager.METHOD_ConvFinAccountMatItemAttachmentToUI)
	protected List<FinAccountMatItemAttachmentUIModel> finAccountMatItemAttachmentUIModelList = new ArrayList<FinAccountMatItemAttachmentUIModel>();

	public FinAccountMaterialItemUIModel getFinAccountMaterialItemUIModel() {
		return this.finAccountMaterialItemUIModel;
	}

	public void setFinAccountMaterialItemUIModel(
			FinAccountMaterialItemUIModel purchaseContractMaterialItemUIModel) {
		this.finAccountMaterialItemUIModel = purchaseContractMaterialItemUIModel;
	}

	public List<FinAccountMatItemAttachmentUIModel> getFinAccountMatItemAttachmentUIModelList() {
		return finAccountMatItemAttachmentUIModelList;
	}

	public void setFinAccountMatItemAttachmentUIModelList(
			List<FinAccountMatItemAttachmentUIModel> finAccountMatItemAttachmentUIModelList) {
		this.finAccountMatItemAttachmentUIModelList = finAccountMatItemAttachmentUIModelList;
	}

}
