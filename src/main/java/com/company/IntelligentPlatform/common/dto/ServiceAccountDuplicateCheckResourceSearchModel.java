package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.ServiceAccountDuplicateCheckResource;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * ServiceAccountDuplicateCheckResource UI Model
 ** 
 * @author
 * @date Thu May 14 14:58:20 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class ServiceAccountDuplicateCheckResourceSearchModel extends
		SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = ServiceAccountDuplicateCheckResource.NODENAME, seName = ServiceAccountDuplicateCheckResource.SENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "refAccountType", nodeName = ServiceAccountDuplicateCheckResource.NODENAME, seName = ServiceAccountDuplicateCheckResource.SENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ServiceAccountDuplicateCheckResourceSearch_refAccountType", valueFieldName = "")
	protected int refAccountType;

	@BSearchFieldConfig(fieldName = "switchFlag", nodeName = ServiceAccountDuplicateCheckResource.NODENAME, seName = ServiceAccountDuplicateCheckResource.SENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ServiceAccountDuplicateCheckResourceSearch_switchFlag", valueFieldName = "")
	protected int switchFlag;

	@BSearchFieldConfig(fieldName = "implementClassName", nodeName = ServiceAccountDuplicateCheckResource.NODENAME, seName = ServiceAccountDuplicateCheckResource.SENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME)
	protected String implementClassName;

	@BSearchFieldConfig(fieldName = "implementType", nodeName = ServiceAccountDuplicateCheckResource.NODENAME, seName = ServiceAccountDuplicateCheckResource.SENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ServiceAccountDuplicateCheckResourceSearch_implementType", valueFieldName = "")
	protected int implementType;

	@BSearchFieldConfig(fieldName = "logicRelationship", nodeName = ServiceAccountDuplicateCheckResource.NODENAME, seName = ServiceAccountDuplicateCheckResource.SENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ServiceAccountDuplicateCheckResourceSearch_logicRelationship", valueFieldName = "")
	protected int logicRelationship;
	/**
	 * Dummy search field, only be used for page split function on UI
	 * [Important], should be reset as 0 before real search
	 */
	protected int currentPage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRefAccountType() {
		return refAccountType;
	}

	public void setRefAccountType(int refAccountType) {
		this.refAccountType = refAccountType;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getImplementClassName() {
		return implementClassName;
	}

	public void setImplementClassName(String implementClassName) {
		this.implementClassName = implementClassName;
	}

	public int getImplementType() {
		return implementType;
	}

	public void setImplementType(int implementType) {
		this.implementType = implementType;
	}

	public int getLogicRelationship() {
		return logicRelationship;
	}

	public void setLogicRelationship(int logicRelationship) {
		this.logicRelationship = logicRelationship;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
