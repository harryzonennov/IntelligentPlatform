package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.company.IntelligentPlatform.common.model.*;

/**
 * Basic super class of Service Entity Configuration proxy class
 * 
 * @author ZhangHang
 * @date Nov 7, 2012
 * 
 */
public class ServiceEntityConfigureProxy {

	protected List<ServiceEntityConfigureMap> seConfigMapList;

	protected String serviceEntityName;

	protected String packageName;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public ServiceEntityConfigureProxy() {
		initConfig();
	}

	public void initConfig() {
		// Empty at this level
	}

	public List<ServiceEntityConfigureMap> getSeConfigMapList() {
		return seConfigMapList;
	}

	public void setSeConfigMapList(
			List<ServiceEntityConfigureMap> seConfigMapList) {
		this.seConfigMapList = seConfigMapList;
	}

	/**
	 * Get node configure map by node name
	 * 
	 * @param nodeName
	 * @return
	 */
	public ServiceEntityConfigureMap getConfigureByNodeName(String nodeName)
			throws ServiceEntityConfigureException {
		for (ServiceEntityConfigureMap seConfigMap : seConfigMapList) {
			if (seConfigMap.getNodeName().equals(nodeName)) {
				return seConfigMap;
			}
		}
		throw new ServiceEntityConfigureException(
				ServiceEntityConfigureException.PARA_NON_NODE, nodeName);
	}

	public ServiceEntityConfigureMap getConfigureByNodeName(String nodeName,
			List<ServiceEntityConfigureMap> resultList)
			throws ServiceEntityConfigureException {
		if (ServiceCollectionsHelper.checkNullList(resultList)) {
			return null;
		}
		for (ServiceEntityConfigureMap seConfigMap : resultList) {
			if (seConfigMap.getNodeName().equals(nodeName)) {
				return seConfigMap;
			}
		}
		throw new ServiceEntityConfigureException(
				ServiceEntityConfigureException.PARA_NON_NODE, nodeName);
	}

	/**
	 * Get child node configure map by node name
	 * 
	 * @param seConfigMap
	 * @return
	 */
	public void mergeToList(ServiceEntityConfigureMap seConfigMap,
			List<ServiceEntityConfigureMap> resultList) {
		ServiceEntityConfigureMap serviceEntityConfigureMap = null;
		try {
			serviceEntityConfigureMap = getConfigureByNodeName(
					seConfigMap.getNodeName(), resultList);
		} catch (ServiceEntityConfigureException e) {
			// do nothing
		}
		if (serviceEntityConfigureMap == null) {
			if (seConfigMap != null) {
				resultList.add(seConfigMap);
			}
		}
	}

	public List<ServiceEntityConfigureMap> getDirectSubConfigureByNodeName(
			String nodeName) throws ServiceEntityConfigureException {
		if (ServiceEntityStringHelper.checkNullString(nodeName)) {
			return null;
		}
		if (nodeName.equals(ServiceEntityNode.NODENAME_ROOT)) {
			return this.getSeConfigMapList();
		}
		List<ServiceEntityConfigureMap> resultList = new ArrayList<>();
		for (ServiceEntityConfigureMap seConfigMap : seConfigMapList) {
			if (seConfigMap.getParentNodeName().equals(nodeName)) {
				mergeToList(seConfigMap, resultList);
			}
		}
		return resultList;
	}

	public List<ServiceEntityConfigureMap> getSubConfigureByNodeName(
			String nodeName, boolean includeFlag)
			throws ServiceEntityConfigureException {
		if (ServiceEntityStringHelper.checkNullString(nodeName)) {
			return null;
		}
		if (nodeName.equals(ServiceEntityNode.NODENAME_ROOT)) {
			return this.getSeConfigMapList();
		}
		List<ServiceEntityConfigureMap> resultList = new ArrayList<>();
		for (ServiceEntityConfigureMap seConfigMap : seConfigMapList) {
			if (seConfigMap.getParentNodeName().equals(nodeName)) {
				mergeToList(seConfigMap, resultList);
				List<ServiceEntityConfigureMap> subResultList = getSubConfigureByNodeName(
						seConfigMap.getNodeName(), false);
				if (!ServiceCollectionsHelper.checkNullList(subResultList)) {
					resultList.addAll(subResultList);
				}
			}
		}
		if (includeFlag) {
			ServiceEntityConfigureMap homeSEConfigMap = getConfigureByNodeName(nodeName);
			mergeToList(homeSEConfigMap, resultList);
		}
		return resultList;
	}

	/**
	 * Get field configure map by node and field name
	 * 
	 * @param nodeName
	 * @param fieldName
	 * @return
	 */
	public ServiceEntityNodeFieldConfigureMap getFieldConfigureMap(
			String nodeName, String fieldName)
			throws ServiceEntityConfigureException {
		ServiceEntityConfigureMap seConfigureMap = getConfigureByNodeName(nodeName);
		List<ServiceEntityNodeFieldConfigureMap> fieldList = seConfigureMap
				.getFieldList();
		for (ServiceEntityNodeFieldConfigureMap seFieldMap : fieldList) {
			if (seFieldMap.getFieldName().equals(fieldName)) {
				return seFieldMap;
			}
		}
		throw new ServiceEntityConfigureException(
				ServiceEntityConfigureException.PARA_NON_FIELD, fieldName);
	}

	public String getServiceEntityName() {
		return serviceEntityName;
	}

	public void setServiceEntityName(String serviceEntityName) {
		this.serviceEntityName = serviceEntityName;
	}

	/**
	 * @return Basic Service Entity Node fields configuration map
	 */
	public List<ServiceEntityNodeFieldConfigureMap> getBasicSENodeFieldMap() {
		List<ServiceEntityNodeFieldConfigureMap> seFieldConfigList = Collections
				.synchronizedList(new ArrayList<ServiceEntityNodeFieldConfigureMap>());
		ServiceEntityNodeFieldConfigureMap fieldMap1 = new ServiceEntityNodeFieldConfigureMap();
		fieldMap1.setFieldName("uuid");
		fieldMap1.setFieldType(String.class);
		seFieldConfigList.add(fieldMap1);
		ServiceEntityNodeFieldConfigureMap fieldMap2 = new ServiceEntityNodeFieldConfigureMap();
		fieldMap2.setFieldName("client");
		fieldMap2.setFieldType(String.class);
		seFieldConfigList.add(fieldMap2);
		ServiceEntityNodeFieldConfigureMap fieldMap3 = new ServiceEntityNodeFieldConfigureMap();
		fieldMap3.setFieldName("id");
		fieldMap3.setFieldType(String.class);
		seFieldConfigList.add(fieldMap3);
		ServiceEntityNodeFieldConfigureMap fieldMap4 = new ServiceEntityNodeFieldConfigureMap();
		fieldMap4.setFieldName("name");
		fieldMap4.setFieldType(String.class);
		seFieldConfigList.add(fieldMap4);
		ServiceEntityNodeFieldConfigureMap fieldMap5 = new ServiceEntityNodeFieldConfigureMap();
		fieldMap5.setFieldName("parentNodeUUID");
		fieldMap5.setFieldType(String.class);
		seFieldConfigList.add(fieldMap5);
		ServiceEntityNodeFieldConfigureMap fieldMap6 = new ServiceEntityNodeFieldConfigureMap();
		fieldMap6.setFieldName("rootNodeUUID");
		fieldMap6.setFieldType(String.class);
		seFieldConfigList.add(fieldMap6);
		ServiceEntityNodeFieldConfigureMap fieldMap7 = new ServiceEntityNodeFieldConfigureMap();
		fieldMap7.setFieldName("nodeLevel");
		fieldMap7.setFieldType(int.class);
		seFieldConfigList.add(fieldMap7);
		ServiceEntityNodeFieldConfigureMap fieldMap8 = new ServiceEntityNodeFieldConfigureMap();
		fieldMap8.setFieldName("serviceEntityName");
		fieldMap8.setFieldType(String.class);
		seFieldConfigList.add(fieldMap8);
		ServiceEntityNodeFieldConfigureMap fieldMap9 = new ServiceEntityNodeFieldConfigureMap();
		fieldMap9.setFieldName("nodeName");
		fieldMap9.setFieldType(String.class);
		seFieldConfigList.add(fieldMap9);
		ServiceEntityNodeFieldConfigureMap fieldMapa = new ServiceEntityNodeFieldConfigureMap();
		fieldMapa.setFieldName("createdBy");
		fieldMapa.setFieldType(String.class);
		seFieldConfigList.add(fieldMapa);
		ServiceEntityNodeFieldConfigureMap fieldMapb = new ServiceEntityNodeFieldConfigureMap();
		fieldMapb.setFieldName("createdTime");
		fieldMapb.setFieldType(Date.class);
		seFieldConfigList.add(fieldMapb);
		ServiceEntityNodeFieldConfigureMap fieldMapc = new ServiceEntityNodeFieldConfigureMap();
		fieldMapc.setFieldName("lastUpdateBy");
		fieldMapc.setFieldType(String.class);
		seFieldConfigList.add(fieldMapc);
		ServiceEntityNodeFieldConfigureMap fieldMapd = new ServiceEntityNodeFieldConfigureMap();
		fieldMapd.setFieldName("lastUpdateTime");
		fieldMapd.setFieldType(Date.class);
		seFieldConfigList.add(fieldMapd);
		ServiceEntityNodeFieldConfigureMap fieldMape = new ServiceEntityNodeFieldConfigureMap();
		fieldMape.setFieldName("nodeSpecifyType");
		fieldMape.setFieldType(int.class);
		seFieldConfigList.add(fieldMape);
		ServiceEntityNodeFieldConfigureMap fieldMapf = new ServiceEntityNodeFieldConfigureMap();
		fieldMapf.setFieldName("note");
		fieldMapf.setFieldType(String.class);
		seFieldConfigList.add(fieldMapf);
		ServiceEntityNodeFieldConfigureMap fieldMapg = new ServiceEntityNodeFieldConfigureMap();
		fieldMapg.setFieldName("resEmployeeUUID");
		fieldMapg.setFieldType(String.class);
		seFieldConfigList.add(fieldMapg);
		ServiceEntityNodeFieldConfigureMap fieldMapi = new ServiceEntityNodeFieldConfigureMap();
		fieldMapi.setFieldName("resOrgUUID");
		fieldMapi.setFieldType(String.class);
		seFieldConfigList.add(fieldMapi);
		return seFieldConfigList;
	}

	/**
	 * @return Basic Document Node fields configuration map
	 */
	public List<ServiceEntityNodeFieldConfigureMap> getBasicDocumentFieldMap() {
		List<ServiceEntityNodeFieldConfigureMap> seFieldConfigList = getBasicSENodeFieldMap();
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap("status",
				int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"priorityCode", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"documentCategoryType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"prevProfDocType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"prevProfDocUUID", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"prevDocType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"prevDocUUID", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"nextDocType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"nextDocUUID", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"nextProfDocType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"nextProfDocUUID", String.class));
		return seFieldConfigList;
	}

	/**
	 * @return Basic Reference Node fields configuration map
	 */
	public List<ServiceEntityNodeFieldConfigureMap> getBasicReferenceFieldMap() {
		List<ServiceEntityNodeFieldConfigureMap> seFieldConfigList = getBasicSENodeFieldMap();
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap("refUUID",
				java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"refSEName", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"refNodeName", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"refPackageName", java.lang.String.class));
		return seFieldConfigList;
	}

	/**
	 * @return Basic Reference Node fields configuration map
	 */
	public List<ServiceEntityNodeFieldConfigureMap> getBasicDocMatItemMap() {
		List<ServiceEntityNodeFieldConfigureMap> seFieldConfigList = getBasicReferenceFieldMap();
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"reservedMatItemUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"reservedDocType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"reservedDocMatItemArrayUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"reserveTargetMatItemUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"reserveTargetDocType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"reserveTargetDocMatItemArrayUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"prevDocMatItemArrayUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"itemStatus", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"materialStatus", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"homeDocumentType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"prevDocMatItemUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"prevDocType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"nextDocMatItemArrayUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"nextDocMatItemUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"nextDocType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"refMaterialSKUUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"refUnitUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap("amount",
				double.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"itemPrice", double.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"unitPrice", double.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"itemPriceDisplay", double.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"unitPriceDisplay", double.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"currencyCode", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"refFinMatItemUUID", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"prevProfDocType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"prevProfDocMatItemUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"prevProfDocMatItemArrayUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"nextProfDocType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"nextProfDocMatItemUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"nextProfDocMatItemArrayUUID", java.lang.String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"productionBatchNumber", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"purchaseBatchNumber", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"materialStatus", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"homeDocumentType", int.class));
		return seFieldConfigList;
	}
	
	/**
	 * @return Basic Involved party fields configuration map
	 */
	public List<ServiceEntityNodeFieldConfigureMap> getBasicInvolvePartyMap() {
		List<ServiceEntityNodeFieldConfigureMap> seFieldConfigList = getBasicReferenceFieldMap();
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"partyRole", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"email", String.class));	
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"fax", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"taxNumber", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"telephone", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"bankAccount", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"depositBank", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"postcode", String.class));		
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"cityName", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"townZone", String.class));		
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"subArea", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"streetName", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"houseNumber", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"refContactUUID", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"contactName", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"contactId", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"contactMobile", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"address", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"refDocumentType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"refDocMatItemUUID", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"refDocumentDate", Date.class));
		return seFieldConfigList;
	}

	/**
	 * @return Basic Involved party fields configuration map
	 */
	public List<ServiceEntityNodeFieldConfigureMap> getBasicActionCodeNodeMap() {
		List<ServiceEntityNodeFieldConfigureMap> seFieldConfigList = getBasicSENodeFieldMap();
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"processIndex", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"flatNodeSwitch", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"docActionCode", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"executionTime", Date.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"executedByUUID", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"documentType", int.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"refDocMatItemUUID", String.class));
		seFieldConfigList.add(new ServiceEntityNodeFieldConfigureMap(
				"refDocumentUUID", String.class));
		return seFieldConfigList;
	}

}
