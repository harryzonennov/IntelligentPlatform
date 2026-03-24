package com.company.IntelligentPlatform.common.service;

import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class BSearchSettings {
	
	private Map<String, List<?>> multiValueMap;

	private String client;

	private boolean fuzzyFlag;
	
	private int start = 0;
	
	private int length = 0;
	
	private SEUIComModel searchModel;
	
	public BSearchSettings(){
		
	}

	public BSearchSettings(Map<String, List<?>> multiValueMap, String client,
			boolean fuzzyFlag, int start, int length) {
		super();
		this.multiValueMap = multiValueMap;
		this.client = client;
		this.fuzzyFlag = fuzzyFlag;
		this.start = start;
		this.length = length;
	}

	public BSearchSettings(Map<String, List<?>> multiValueMap, String client,
			boolean fuzzyFlag, int start, int length, SEUIComModel searchModel) {
		super();
		this.multiValueMap = multiValueMap;
		this.client = client;
		this.fuzzyFlag = fuzzyFlag;
		this.start = start;
		this.length = length;
		this.searchModel = searchModel;
	}

	public Map<String, List<?>> getMultiValueMap() {
		return multiValueMap;
	}

	public void setMultiValueMap(Map<String, List<?>> multiValueMap) {
		this.multiValueMap = multiValueMap;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public boolean getFuzzyFlag() {
		return fuzzyFlag;
	}

	public void setFuzzyFlag(boolean fuzzyFlag) {
		this.fuzzyFlag = fuzzyFlag;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public SEUIComModel getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(SEUIComModel searchModel) {
		this.searchModel = searchModel;
	}

}
