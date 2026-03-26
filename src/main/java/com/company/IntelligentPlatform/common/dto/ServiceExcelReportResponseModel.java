package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceExcelReportResponseModel extends SEUIComModel{
	
	protected List<SEUIComModel> dataList = new ArrayList<>();
	
	protected List<ServiceExcelReportErrorLogUnion> errorLogList = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	protected Map<Integer, SEUIComModel> dataMap = new java.util.HashMap<>();
	
	public List<SEUIComModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<SEUIComModel> dataList) {
		this.dataList = dataList;
	}

	public List<ServiceExcelReportErrorLogUnion> getErrorLogList() {
		return errorLogList;
	}

	public void setErrorLogList(List<ServiceExcelReportErrorLogUnion> errorLogList) {
		this.errorLogList = errorLogList;
	}
	
	public void addErrorLog(ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion){
		this.errorLogList.add(serviceExcelReportErrorLogUnion);
	}

	public Map<Integer, SEUIComModel> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<Integer, SEUIComModel> dataMap) {
		this.dataMap = dataMap;
	}

}
