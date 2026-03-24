package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.UserRole;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchModelConfigHelper {

	public static BSearchNodeComConfigureBuilder genBuilder() {
		return new BSearchNodeComConfigureBuilder();
	}

	public static String getDefNodeInstIdByModelClass(Class<? extends ServiceEntityNode> modelClass)
			throws SearchConfigureException {
		try {
			String seName = ServiceEntityFieldsHelper.getStaticFieldValue(
					modelClass, IServiceEntityNodeFieldConstant.STA_SENAME).toString();
			String nodeName = ServiceEntityFieldsHelper.getStaticFieldValue(
					modelClass, IServiceEntityNodeFieldConstant.STA_NODENAME).toString();
			return ServiceEntityStringHelper.getDefModelId(seName, nodeName);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
		}
	}

	public static String getNodeInstIdWrapper(Class<? extends ServiceEntityNode> modelClass, String nodeInstId)
			throws SearchConfigureException {
		if (!ServiceEntityStringHelper.checkNullString(nodeInstId)) {
			return nodeInstId;
		} else {
			return getDefNodeInstIdByModelClass(modelClass);
		}
	}

	public static List<BSearchNodeComConfigure> buildParentChildConfigure(
			Class<? extends ServiceEntityNode> parentClass, Class<? extends ServiceEntityNode> childClass)
			throws SearchConfigureException {
		return buildParentChildConfigure(parentClass, childClass, null, null, null);
	}

	public static List<BSearchNodeComConfigure> buildParentChildConfigure(
			Class<? extends ServiceEntityNode> parentClass, Class<? extends ServiceEntityNode> childClass,
			String baseNodeInstId, String childNodeInstId, BSearchNodeComConfigureBuilder hostNodeBuilder)
			throws SearchConfigureException {
        if (hostNodeBuilder == null) {
			hostNodeBuilder = new BSearchNodeComConfigureBuilder().startNodeFlag(true);
		}
		String parentNodeInstId = getNodeInstIdWrapper(parentClass, baseNodeInstId);
		// generate default search header
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>(genDefServiceEntitySearchHeader(parentClass, parentNodeInstId));
		// start node:[parent node]
		searchNodeConfigList.add(
				hostNodeBuilder.modelClass(parentClass).nodeInstId(parentNodeInstId).build());
		String localChildNodeInstId = getNodeInstIdWrapper(childClass, childNodeInstId);
		// search node [parent node->child node]
		searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(childClass).nodeInstId(localChildNodeInstId)
				.toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).baseNodeInstId(parentNodeInstId)
				.build());
		return searchNodeConfigList;
	}

	public static List<BSearchNodeComConfigure> buildChildParentConfigure(
			Class<? extends ServiceEntityNode> childClass, Class<? extends ServiceEntityNode> parentClass)
			throws SearchConfigureException {
		return buildChildParentConfigure(childClass, parentClass, null, null, null);
	}

	public static List<BSearchNodeComConfigure> buildChildParentConfigure(
			Class<? extends ServiceEntityNode> childClass, Class<? extends ServiceEntityNode> parentClass,
			String baseNodeInstId, String childNodeInstId, BSearchNodeComConfigureBuilder hostNodeBuilder)
			throws SearchConfigureException {
		if (hostNodeBuilder == null) {
			hostNodeBuilder = new BSearchNodeComConfigureBuilder().startNodeFlag(true);
		}
		String localChildNodeInstId = getNodeInstIdWrapper(childClass, childNodeInstId);
		// generate default search header
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>(genDefServiceEntitySearchHeader(childClass, childNodeInstId));
		// start node:[child node]
		searchNodeConfigList.add(
				hostNodeBuilder.modelClass(childClass).nodeInstId(localChildNodeInstId).build());
		// search node [parent node->child node]
		String parentNodeInstId = getNodeInstIdWrapper(parentClass, baseNodeInstId);
		searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(parentClass).nodeInstId(parentNodeInstId)
				.toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD).baseNodeInstId(localChildNodeInstId)
				.build());
		return searchNodeConfigList;
	}

	public static List<BSearchNodeComConfigure> buildReference(
			Class<? extends ServiceEntityNode> sourceClass, Class<? extends ServiceEntityNode> targetClass,
			String sourceNodeInstId, String targetNodeInstId, BSearchNodeComConfigureBuilder hostNodeBuilder)
			throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		String localSourceNodeInstId = getNodeInstIdWrapper(sourceClass, sourceNodeInstId);
		if (hostNodeBuilder == null) {
			hostNodeBuilder = new BSearchNodeComConfigureBuilder();
		}
		searchNodeConfigList.add(hostNodeBuilder.modelClass(sourceClass).nodeInstId(localSourceNodeInstId).build());
		String localTargetNodeInstId = getNodeInstIdWrapper(sourceClass, targetNodeInstId);
		searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(targetClass).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE).baseNodeInstId(localTargetNodeInstId).build());
		return searchNodeConfigList;
	}

	public static List<BSearchNodeComConfigure> buildResUserOrgConfigure(
			Class<? extends ServiceEntityNode> baseModelClass,
			String baseNodeInstId)
			throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		String localBaseNodeInstId = getNodeInstIdWrapper(baseModelClass, baseNodeInstId);
		searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(LogonUser.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).
				mapFieldUUID("resEmployeeUUID").baseNodeInstId(localBaseNodeInstId).build());
		searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(Organization.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).
				mapFieldUUID("resOrgUUID").baseNodeInstId(localBaseNodeInstId).build());
		return searchNodeConfigList;
	}

	public static List<BSearchNodeComConfigure> genDefServiceEntitySearchHeader(Class<? extends ServiceEntityNode> serviceDocClass,
																				String nodeInstId) throws SearchConfigureException {
		try {
			List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
			searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(serviceDocClass).startNodeFlag(true).build());
			String localNodeInstId = ServiceEntityStringHelper.checkNullString(nodeInstId) ?
					BSearchNodeComConfigureBuilder.getDefaultNodeInstId(serviceDocClass) : nodeInstId;
			// Search node Array :[Last created, updated by]
			searchNodeConfigList.addAll(SearchDocConfigHelper.genCreatedUpdatedBySearchNodeConfigureList(localNodeInstId));
			return searchNodeConfigList;
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
		}
	}

}
