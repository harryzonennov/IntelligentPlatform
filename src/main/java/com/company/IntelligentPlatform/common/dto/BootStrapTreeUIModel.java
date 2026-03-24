package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

public class BootStrapTreeUIModel {
	
	protected String uuid;
	
	protected String parentNodeUUID;
	
	protected String rootNodeUUID;
	
	protected String editURL;
	
	protected String viewURL;
	
	protected String spanContent;
	
	protected String postfixContent;
	
	protected int layer;
	
	protected String iconClass;
	
	protected String postIconClass;
	
	protected String spanSubClass;
	
	protected String spanClass;
	
	protected List<BootStrapTreeUIModel> postModelList = new ArrayList<BootStrapTreeUIModel>();
	
	protected List<BootStrapTreeUIModel> subModelList = new ArrayList<BootStrapTreeUIModel>();

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParentNodeUUID() {
		return parentNodeUUID;
	}

	public void setParentNodeUUID(String parentNodeUUID) {
		this.parentNodeUUID = parentNodeUUID;
	}

	public String getRootNodeUUID() {
		return rootNodeUUID;
	}

	public void setRootNodeUUID(String rootNodeUUID) {
		this.rootNodeUUID = rootNodeUUID;
	}

	public String getEditURL() {
		return editURL;
	}

	public void setEditURL(String editURL) {
		this.editURL = editURL;
	}

	public String getViewURL() {
		return viewURL;
	}

	public void setViewURL(String viewURL) {
		this.viewURL = viewURL;
	}

	public String getSpanContent() {
		return spanContent;
	}

	public void setSpanContent(String spanContent) {
		this.spanContent = spanContent;
	}

	public String getPostfixContent() {
		return postfixContent;
	}

	public void setPostfixContent(String postfixContent) {
		this.postfixContent = postfixContent;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getPostIconClass() {
		return postIconClass;
	}

	public void setPostIconClass(String postIconClass) {
		this.postIconClass = postIconClass;
	}

	public String getSpanSubClass() {
		return spanSubClass;
	}

	public void setSpanSubClass(String spanSubClass) {
		this.spanSubClass = spanSubClass;
	}

	public String getSpanClass() {
		return spanClass;
	}

	public void setSpanClass(String spanClass) {
		this.spanClass = spanClass;
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

	public List<BootStrapTreeUIModel> getPostModelList() {
		return postModelList;
	}

	public void setPostModelList(List<BootStrapTreeUIModel> postModelList) {
		this.postModelList = postModelList;
	}
	
	public void addPostModelList(BootStrapTreeUIModel treeUIModel) {
		this.postModelList.add(treeUIModel);
	}

}
