package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.ProdWorkCenterCalendarItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProdWorkCenterCalendarItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProdWorkCenterCalendarItem.NODENAME, nodeInstId = ProdWorkCenterCalendarItem.NODENAME)
	protected ProdWorkCenterCalendarItem prodWorkCenterCalendarItem;

	public ProdWorkCenterCalendarItem getProdWorkCenterCalendarItem() {
		return prodWorkCenterCalendarItem;
	}

	public void setProdWorkCenterCalendarItem(ProdWorkCenterCalendarItem prodWorkCenterCalendarItem) {
		this.prodWorkCenterCalendarItem = prodWorkCenterCalendarItem;
	}
}
