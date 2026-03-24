package com.company.IntelligentPlatform.common.service;

import java.util.List;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * A Search Response Model class for returning the search results.
 *
 * This class contains:
 * - A list of search results (resultList).
 * - The total number of records (recordsTotal).
 * - A list of UUIDs corresponding to the search results, if required (uuidList).
 */
public class BSearchResponse {
	
	private List<ServiceEntityNode> resultList;

	private List<String> uuidList;
	
	private int recordsTotal;

	public BSearchResponse() {		
	}

	public BSearchResponse(List<ServiceEntityNode> resultList, int recordsTotal) {
		super();
		this.resultList = resultList;
		this.recordsTotal = recordsTotal;
	}

	public List<String> getUuidList() {
		return uuidList;
	}

	public void setUuidList(List<String> uuidList) {
		this.uuidList = uuidList;
	}

	public List<ServiceEntityNode> getResultList() {
		return resultList;
	}

	public void setResultList(List<ServiceEntityNode> resultList) {
		this.resultList = resultList;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

}
