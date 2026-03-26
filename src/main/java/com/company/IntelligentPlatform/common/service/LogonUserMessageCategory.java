package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class LogonUserMessageCategory implements Comparable<LogonUserMessageCategory>{
	
	protected int category;
	
	protected String categoryTitle;
	
	protected String comment;
	
	protected String listURL;
	
	protected List<LogonUserMessage> messageList = new ArrayList<LogonUserMessage>();
	
	protected List<ServiceEntityNode> rawSEList = new ArrayList<>();
	
	protected boolean popupFlag = false;

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getListURL() {
		return listURL;
	}

	public void setListURL(String listURL) {
		this.listURL = listURL;
	}

	public List<LogonUserMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<LogonUserMessage> messageList) {
		this.messageList = messageList;
	}
	
	public void addMessage(LogonUserMessage logonUserMessage){
		this.messageList.add(logonUserMessage);
	}

	public List<ServiceEntityNode> getRawSEList() {
		return rawSEList;
	}

	public void setRawSEList(List<ServiceEntityNode> rawSEList) {
		this.rawSEList = rawSEList;
	}

	public boolean getPopupFlag() {
		return popupFlag;
	}

	public void setPopupFlag(boolean popupFlag) {
		this.popupFlag = popupFlag;
	}

	@Override
	public int compareTo(LogonUserMessageCategory otherModel) {
		String thisCategory = new Integer(this.getCategory()).toString(); 
		String otherCategory = new Integer(otherModel.getCategory()).toString();
		return thisCategory.compareTo(otherCategory);
	}

}
