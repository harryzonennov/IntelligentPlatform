package com.company.IntelligentPlatform.common.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * serviceExceptionRecord UI Model
 ** 
 * @author
 * @date Thu Aug 15 00:23:12 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class ServiceExceptionRecordSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "uuid", nodeName = "ROOT", seName = "ServiceExceptionRecord", nodeInstID = "ServiceExceptionRecord", showOnUI = false)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "id", nodeName = "ROOT", seName = "ServiceExceptionRecord", nodeInstID = "ServiceExceptionRecord")
	protected String id;

	@BSearchFieldConfig(fieldName = "createdTime", nodeName = "ROOT", seName = "ServiceExceptionRecord", nodeInstID = "ServiceExceptionRecord")
	protected Date createdTime;

	@BSearchFieldConfig(fieldName = "lastUpdateTime", nodeName = "ROOT", seName = "ServiceExceptionRecord", nodeInstID = "ServiceExceptionRecord")
	protected Date lastUpdateTime;

	@BSearchFieldConfig(fieldName = "processorName", nodeName = "ROOT", seName = "ServiceExceptionRecord", nodeInstID = "ServiceExceptionRecord")
	protected String processorName;

	@BSearchFieldConfig(fieldName = "reporterName", nodeName = "ROOT", seName = "ServiceExceptionRecord", nodeInstID = "ServiceExceptionRecord")
	protected String reporterName;

	@BSearchFieldConfig(fieldName = "category", nodeName = "ROOT", seName = "ServiceExceptionRecord", nodeInstID = "ServiceExceptionRecord")
	@ISEDropDownResourceMapping(resouceMapping = "ServiceExceptionRecorSearch_category", valueFieldName = "")
	protected int category;

	@BSearchFieldConfig(fieldName = "priority", nodeName = "ROOT", seName = "ServiceExceptionRecord", nodeInstID = "ServiceExceptionRecord")
	@ISEDropDownResourceMapping(resouceMapping = "ServiceExceptionRecorSearch_priority", valueFieldName = "")
	protected int priority;

	@BSearchFieldConfig(fieldName = "status", nodeName = "ROOT", seName = "ServiceExceptionRecord", nodeInstID = "ServiceExceptionRecord")
	@ISEDropDownResourceMapping(resouceMapping = "ServiceExceptionRecorSearch_status", valueFieldName = "")
	protected int status;

	@BSearchFieldConfig(fieldName = "sourceType", nodeName = "ROOT", seName = "ServiceExceptionRecord", nodeInstID = "ServiceExceptionRecord")
	@ISEDropDownResourceMapping(resouceMapping = "ServiceExceptionRecorSearch_sourceType", valueFieldName = "")
	protected int sourceType;

	/**
	 * This attribute is just for get the page on UI, must be cleared before
	 * each search
	 */
	@BSearchFieldConfig(fieldName = "status", nodeInstID = "ServiceExceptionRecord")
	protected int currentPage;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
