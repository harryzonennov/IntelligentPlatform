package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.ProdWorkCenterCalendarItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProdWorkCenterCalendarItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProdWorkCenterCalendarItem.NODENAME, nodeInstId = ProdWorkCenterCalendarItem.NODENAME)
	protected ProdWorkCenterCalendarItemUIModel prodWorkCenterCalendarItemUIModel;

	public ProdWorkCenterCalendarItemUIModel getProdWorkCenterCalendarItemUIModel() {
		return prodWorkCenterCalendarItemUIModel;
	}

	public void setProdWorkCenterCalendarItemUIModel(ProdWorkCenterCalendarItemUIModel prodWorkCenterCalendarItemUIModel) {
		this.prodWorkCenterCalendarItemUIModel = prodWorkCenterCalendarItemUIModel;
	}
}
