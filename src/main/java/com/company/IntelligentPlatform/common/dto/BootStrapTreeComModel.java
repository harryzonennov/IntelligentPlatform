package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

public class BootStrapTreeComModel {
	
	protected BootStrapTreeUIModel homeModel;
	
	protected List<BootStrapTreeUIModel> subModelList = new ArrayList<BootStrapTreeUIModel>();

	public BootStrapTreeUIModel getHomeModel() {
		return homeModel;
	}

	public void setHomeModel(BootStrapTreeUIModel homeModel) {
		this.homeModel = homeModel;
	}

	public List<BootStrapTreeUIModel> getSubModelList() {
		return subModelList;
	}

	public void setSubModelList(List<BootStrapTreeUIModel> subModelList) {
		this.subModelList = subModelList;
	}

	public void addSubModelList(BootStrapTreeUIModel treeUIModel) {
		this.subModelList.add(treeUIModel);
	}

}
