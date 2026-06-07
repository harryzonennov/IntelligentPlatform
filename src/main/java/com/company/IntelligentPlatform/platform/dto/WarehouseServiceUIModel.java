package com.company.IntelligentPlatform.platform.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.model.Warehouse;
import com.company.IntelligentPlatform.platform.model.WarehouseArea;
import com.company.IntelligentPlatform.platform.model.WarehouseAttachment;
import com.company.IntelligentPlatform.platform.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

@Component
public class WarehouseServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = Warehouse.NODENAME, nodeInstId = Warehouse.SENAME)
	protected WarehouseUIModel warehouseUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = WarehouseArea.NODENAME, nodeInstId = WarehouseArea.NODENAME)
	protected List<WarehouseAreaServiceUIModel> warehouseAreaUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = WarehouseAttachment.NODENAME
			, nodeInstId = WarehouseAttachment.NODENAME)
	protected List<WarehouseAttachmentUIModel> warehouseAttachmentUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreSetting.NODENAME, nodeInstId = WarehouseStoreSetting.NODENAME)
	protected List<WarehouseStoreSettingUIModel> warehouseStoreSettingUIModelList = new ArrayList<>();

	public WarehouseUIModel getWarehouseUIModel() {
		return this.warehouseUIModel;
	}

	public void setWarehouseUIModel(WarehouseUIModel warehouseUIModel) {
		this.warehouseUIModel = warehouseUIModel;
	}

	public List<WarehouseAreaServiceUIModel> getWarehouseAreaUIModelList() {
		return warehouseAreaUIModelList;
	}

	public void setWarehouseAreaUIModelList(List<WarehouseAreaServiceUIModel> warehouseAreaUIModelList) {
		this.warehouseAreaUIModelList = warehouseAreaUIModelList;
	}

	public List<WarehouseStoreSettingUIModel> getWarehouseStoreSettingUIModelList() {
		return warehouseStoreSettingUIModelList;
	}

	public void setWarehouseStoreSettingUIModelList(
			List<WarehouseStoreSettingUIModel> warehouseStoreSettingUIModelList) {
		this.warehouseStoreSettingUIModelList = warehouseStoreSettingUIModelList;
	}

	public List<WarehouseAttachmentUIModel> getWarehouseAttachmentUIModelList() {
		return warehouseAttachmentUIModelList;
	}

	public void setWarehouseAttachmentUIModelList(List<WarehouseAttachmentUIModel> warehouseAttachmentUIModelList) {
		this.warehouseAttachmentUIModelList = warehouseAttachmentUIModelList;
	}
}
