package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.production.model.ProdWorkCenterCalendarItem;
import com.company.IntelligentPlatform.production.model.ProdWorkCenterResItem;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProdWorkCenterServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProdWorkCenter.NODENAME, nodeInstId = ProdWorkCenter.SENAME)
	protected ProdWorkCenter prodWorkCenter;

	@IServiceModuleFieldConfig(nodeName = ProdWorkCenterResItem.NODENAME, nodeInstId = ProdWorkCenterResItem.NODENAME)
	protected List<ProdWorkCenterResItemServiceModel> prodWorkCenterResItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ProdWorkCenterCalendarItem.NODENAME, nodeInstId = ProdWorkCenterCalendarItem.NODENAME)
	protected List<ProdWorkCenterCalendarItemServiceModel> prodWorkCenterCalendarItemList = new ArrayList<>();

	public List<ProdWorkCenterResItemServiceModel> getProdWorkCenterResItemList() {
		return this.prodWorkCenterResItemList;
	}

	public void setProdWorkCenterResItemList(
			List<ProdWorkCenterResItemServiceModel> prodWorkCenterResItemList) {
		this.prodWorkCenterResItemList = prodWorkCenterResItemList;
	}

	public ProdWorkCenter getProdWorkCenter() {
		return this.prodWorkCenter;
	}

	public void setProdWorkCenter(ProdWorkCenter prodWorkCenter) {
		this.prodWorkCenter = prodWorkCenter;
	}

	public List<ProdWorkCenterCalendarItemServiceModel> getProdWorkCenterCalendarItemList() {
		return this.prodWorkCenterCalendarItemList;
	}

	public void setProdWorkCenterCalendarItemList(
			List<ProdWorkCenterCalendarItemServiceModel> prodWorkCenterCalendarItemList) {
		this.prodWorkCenterCalendarItemList = prodWorkCenterCalendarItemList;
	}

}
