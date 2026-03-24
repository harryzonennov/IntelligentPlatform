package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.production.model.ProdWorkCenterCalendarItem;
import com.company.IntelligentPlatform.production.model.ProdWorkCenterResItem;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProdWorkCenterServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProdWorkCenter.NODENAME, nodeInstId = ProdWorkCenter.SENAME)
	protected ProdWorkCenterUIModel prodWorkCenterUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ProdWorkCenterResItem.NODENAME, nodeInstId = ProdWorkCenterResItem.NODENAME)
	protected List<ProdWorkCenterResItemServiceUIModel> prodWorkCenterResItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ProdWorkCenterCalendarItem.NODENAME, nodeInstId = ProdWorkCenterCalendarItem.NODENAME)
	protected List<ProdWorkCenterCalendarItemServiceUIModel> prodWorkCenterCalendarItemUIModelList = new ArrayList<>();

	public ProdWorkCenterUIModel getProdWorkCenterUIModel() {
		return this.prodWorkCenterUIModel;
	}

	public void setProdWorkCenterUIModel(
			ProdWorkCenterUIModel prodWorkCenterUIModel) {
		this.prodWorkCenterUIModel = prodWorkCenterUIModel;
	}

	public List<ProdWorkCenterResItemServiceUIModel> getProdWorkCenterResItemUIModelList() {
		return this.prodWorkCenterResItemUIModelList;
	}

	public void setProdWorkCenterResItemUIModelList(
			List<ProdWorkCenterResItemServiceUIModel> prodWorkCenterResItemUIModelList) {
		this.prodWorkCenterResItemUIModelList = prodWorkCenterResItemUIModelList;
	}

	public List<ProdWorkCenterCalendarItemServiceUIModel> getProdWorkCenterCalendarItemUIModelList() {
		return this.prodWorkCenterCalendarItemUIModelList;
	}

	public void setProdWorkCenterCalendarItemUIModelList(
			List<ProdWorkCenterCalendarItemServiceUIModel> prodWorkCenterCalendarItemUIModelList) {
		this.prodWorkCenterCalendarItemUIModelList = prodWorkCenterCalendarItemUIModelList;
	}

}
