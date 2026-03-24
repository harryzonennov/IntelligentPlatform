package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class SEUIModelMapConfigure {

	protected List<UIModelMapInfo> uiModelMapList;
	
	protected List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<UIModelNodeMapConfigure>();

	public List<UIModelNodeMapConfigure> getUiModelNodeMapList() {
		return uiModelNodeMapList;
	}

	public void setUiModelNodeMapList(
			List<UIModelNodeMapConfigure> uiModelNodeMapList) {
		this.uiModelNodeMapList = uiModelNodeMapList;
	}

	public void setUiModelMapList(List<UIModelMapInfo> uiModelMapList) {
		this.uiModelMapList = uiModelMapList;
	}

	public SEUIModelMapConfigure() {
		uiModelMapList = new ArrayList<UIModelMapInfo>();
		initConfigure();
	}

	public void initConfigure() {

	}

	public List<UIModelMapInfo> getUiModelMapList() {
		return uiModelMapList;
	}

}
