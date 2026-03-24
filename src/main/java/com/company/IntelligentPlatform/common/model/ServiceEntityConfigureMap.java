package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Configure mapping class to store architecture information for each service
 * entity node, including the node list, their relationships, field list of each
 * node
 * 
 * @author ZhangHang
 * @date Nov 7, 2012
 * 
 */
public class ServiceEntityConfigureMap {

	protected String nodeName;

	@SuppressWarnings("rawtypes")
	protected Class nodeType;

	protected String tableName;

	protected String parentNodeName;

	protected List<ServiceEntityNodeFieldConfigureMap> fieldList;

	/**
	 * Register field information to SE configure Map
	 * 
	 * @param fieldName
	 *            : name of field
	 * @param fieldType
	 *            : Runtime type of the field
	 */
	@SuppressWarnings({ "rawtypes" })
	public void addNodeFieldMap(String fieldName, Class fieldType) {
		ServiceEntityNodeFieldConfigureMap seNodeConfigMap = new ServiceEntityNodeFieldConfigureMap();
		seNodeConfigMap.fieldName = fieldName;
		seNodeConfigMap.fieldType = fieldType;
		if (fieldList == null) {
			fieldList = Collections
					.synchronizedList(new ArrayList<ServiceEntityNodeFieldConfigureMap>());
		}
		fieldList.add(seNodeConfigMap);
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@SuppressWarnings("rawtypes")
	public Class getNodeType() {
		return nodeType;
	}

	@SuppressWarnings("rawtypes")
	public void setNodeType(Class nodeType) {
		this.nodeType = nodeType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getParentNodeName() {
		return parentNodeName;
	}

	public void setParentNodeName(String parentNodeName) {
		this.parentNodeName = parentNodeName;
	}

	public List<ServiceEntityNodeFieldConfigureMap> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<ServiceEntityNodeFieldConfigureMap> fieldList) {
		this.fieldList = fieldList;
	}

}
