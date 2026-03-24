package com.company.IntelligentPlatform.common.dto;

import java.util.Date;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * ServiceExceptionRecord UI Model
 ** 
 * @author
 * @date Mon Jul 22 13:47:40 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class ServiceExceptionRecordUIModel extends SEUIComModel {

	protected Date createdTime;
	
	protected String createdTimeValue;
	
	protected Date lastUpdateTime;
	
	protected String lastUpdateTimeValue;

	protected String callStack;
	
	protected String source;
	
	protected String processorUUID;
	
	protected String reporterUUID;
	
	protected String processorName;
	
	protected String reporterName;

	@ISEDropDownResourceMapping(resouceMapping = "ServiceExceptionRecord_category", valueFieldName = "categoryValue")
	protected int category;
	
	protected String categoryValue;

	@ISEDropDownResourceMapping(resouceMapping = "ServiceExceptionRecord_priority", valueFieldName = "priorityValue")
	protected int priority;

	protected String priorityValue;

	@ISEDropDownResourceMapping(resouceMapping = "ServiceExceptionRecord_status", valueFieldName = "statusValue")
	protected int status;
	
	protected String statusValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "ServiceExceptionRecord_sourceType", valueFieldName = "sourceTypeValue")
	protected int sourceType;
	
	protected String sourceTypeValue;

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getCallStack() {
		return callStack;
	}

	public void setCallStack(String callStack) {
		this.callStack = callStack;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getProcessorUUID() {
		return processorUUID;
	}

	public void setProcessorUUID(String processorUUID) {
		this.processorUUID = processorUUID;
	}

	public String getReporterUUID() {
		return reporterUUID;
	}

	public void setReporterUUID(String reporterUUID) {
		this.reporterUUID = reporterUUID;
	}

	public String getProcessorName() {
		return processorName;
	}

	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}

	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}

	public String getPriorityValue() {
		return priorityValue;
	}

	public void setPriorityValue(String priorityValue) {
		this.priorityValue = priorityValue;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceTypeValue() {
		return sourceTypeValue;
	}

	public void setSourceTypeValue(String sourceTypeValue) {
		this.sourceTypeValue = sourceTypeValue;
	}

	public String getCreatedTimeValue() {
		return createdTimeValue;
	}

	public void setCreatedTimeValue(String createdTimeValue) {
		this.createdTimeValue = createdTimeValue;
	}

	public String getLastUpdateTimeValue() {
		return lastUpdateTimeValue;
	}

	public void setLastUpdateTimeValue(String lastUpdateTimeValue) {
		this.lastUpdateTimeValue = lastUpdateTimeValue;
	}

}
