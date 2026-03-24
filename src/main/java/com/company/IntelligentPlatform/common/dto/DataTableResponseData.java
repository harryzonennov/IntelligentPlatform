package com.company.IntelligentPlatform.common.dto;

import java.io.Serializable;
import java.util.List;

public class DataTableResponseData implements Serializable{

	/**
	 * 
	 */
	
	public static final String FIELD_content = "content";
	
	public static final String FIELD_search = "search";
	
	public static final String FIELD_value = "value";
		
	public static final String FIELD_start = "start";
	
	public static final String FIELD_length = "length";
	
	public static final String FIELD_draw = "draw";
	
	private static final long serialVersionUID = -1759585708753912362L;
	
	protected int draw;
	
	protected int recordsTotal;
	
	protected int recordsFiltered;
	
	protected List<?> data;
	
	protected String error;

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
