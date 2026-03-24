package com.company.IntelligentPlatform.common.controller;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Service
public class ServiceUIModelExtensionHelper {

	public static UIModelNodeMapConfigureBuilder genUIConfBuilder() {
		return new UIModelNodeMapConfigureBuilder();
	}

	public static UIModelNodeMapConfigureBuilder genUIConfBuilder(Class<? extends ServiceEntityNode> modelClass) {
		return new UIModelNodeMapConfigureBuilder().modelClass(modelClass);
	}

	public static UIModelNodeMapConfigureBuilder genUIConfBuilder(Class<? extends ServiceEntityNode> modelClass, Class<? extends SEUIComModel> uiModelClass) {
		return new UIModelNodeMapConfigureBuilder().modelClass(modelClass).uiModelClass(uiModelClass);
	}

	public static ServiceUIModelUnionBuilder genUnionBuilder(Class<? extends ServiceEntityNode> modelClass, Class<? extends SEUIComModel> uiModelClass,
																			   UIModelNodeMapConfigureBuilder.IProcessUIModelNode iProcessUIModelNode)
			throws ServiceEntityConfigureException {
		ServiceUIModelUnionBuilder serviceUIModelUnionBuilder = new ServiceUIModelUnionBuilder();
		serviceUIModelUnionBuilder.modelClass(modelClass).uiModelClass(uiModelClass);
		UIModelNodeMapConfigureBuilder hostNodeMapConfigureBuilder = genUIConfBuilder(modelClass, uiModelClass).hostNodeFlag(true);
		if (iProcessUIModelNode != null) {
			hostNodeMapConfigureBuilder = iProcessUIModelNode.execute(hostNodeMapConfigureBuilder);
		}
		serviceUIModelUnionBuilder.addMapConfigureBuilder(hostNodeMapConfigureBuilder);
		return serviceUIModelUnionBuilder;
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

//	public static List<UIModelNodeMapConfigure> buildParentChildConfigure(
//			Class<? extends ServiceEntityNode> parentClass, Class<? extends ServiceEntityNode> childClass)
//			throws SearchConfigureException {
//		return buildParentChildConfigure(parentClass, childClass, null, null, null);
//	}
//
//	public static List<UIModelNodeMapConfigure> buildParentChildConfigure(
//			Class<? extends ServiceEntityNode> parentClass, Class<? extends ServiceEntityNode> childClass,
//			String baseNodeInstId, String childNodeInstId, UIModelNodeMapConfigureBuilder hostNodeBuilder)
//			throws SearchConfigureException {
//        if (hostNodeBuilder == null) {
//			hostNodeBuilder = new UIModelNodeMapConfigureBuilder().hostNodeFlag(true);
//		}
//		String parentNodeInstId = getNodeInstIdWrapper(parentClass, baseNodeInstId);
//		// generate default search header
//        List<UIModelNodeMapConfigure> searchNodeConfigList = new ArrayList<>(genDefServiceEntityHeader(parentClass, parentNodeInstId));
//		// start node:[parent node]
//		searchNodeConfigList.add(
//				hostNodeBuilder.modelClass(parentClass).nodeInstId(parentNodeInstId).build());
//		String localChildNodeInstId = getNodeInstIdWrapper(childClass, childNodeInstId);
//		// search node [parent node->child node]
//		searchNodeConfigList.add(new UIModelNodeMapConfigureBuilder().modelClass(childClass).nodeInstId(localChildNodeInstId)
//				.toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).baseNodeInstId(parentNodeInstId)
//				.build());
//		return searchNodeConfigList;
//	}
//
//	public static List<UIModelNodeMapConfigure> buildChildParentConfigure(
//			Class<? extends ServiceEntityNode> childClass, Class<? extends ServiceEntityNode> parentClass)
//			throws SearchConfigureException {
//		return buildChildParentConfigure(childClass, parentClass, null, null, null);
//	}

//	public static List<UIModelNodeMapConfigure> buildChildParentConfigure(
//			Class<? extends ServiceEntityNode> childClass, Class<? extends ServiceEntityNode> parentClass,
//			String baseNodeInstId, String childNodeInstId, UIModelNodeMapConfigureBuilder hostNodeBuilder)
//			throws SearchConfigureException {
//		if (hostNodeBuilder == null) {
//			hostNodeBuilder = new UIModelNodeMapConfigureBuilder().hostNodeFlag(true);
//		}
//		String localChildNodeInstId = getNodeInstIdWrapper(childClass, childNodeInstId);
//		// generate default search header
//		List<UIModelNodeMapConfigure> searchNodeConfigList = new ArrayList<>(genDefServiceEntityHeader(childClass, childNodeInstId));
//		// start node:[child node]
//		searchNodeConfigList.add(
//				hostNodeBuilder.modelClass(childClass).nodeInstId(localChildNodeInstId).build());
//		// search node [parent node->child node]
//		String parentNodeInstId = getNodeInstIdWrapper(parentClass, baseNodeInstId);
//		searchNodeConfigList.add(new UIModelNodeMapConfigureBuilder().modelClass(parentClass).nodeInstId(parentNodeInstId)
//				.toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD).baseNodeInstId(localChildNodeInstId)
//				.build());
//		return searchNodeConfigList;
//	}

//	public static List<UIModelNodeMapConfigure> buildReference(
//			Class<? extends ServiceEntityNode> sourceClass, Class<? extends ServiceEntityNode> targetClass,
//			String sourceNodeInstId, String targetNodeInstId, UIModelNodeMapConfigureBuilder hostNodeBuilder)
//			throws SearchConfigureException {
//		List<UIModelNodeMapConfigure> searchNodeConfigList = new ArrayList<>();
//		String localSourceNodeInstId = getNodeInstIdWrapper(sourceClass, sourceNodeInstId);
//		if (hostNodeBuilder == null) {
//			hostNodeBuilder = new UIModelNodeMapConfigureBuilder();
//		}
//		searchNodeConfigList.add(hostNodeBuilder.modelClass(sourceClass).nodeInstId(localSourceNodeInstId).build());
//		String localTargetNodeInstId = getNodeInstIdWrapper(sourceClass, targetNodeInstId);
//		searchNodeConfigList.add(new UIModelNodeMapConfigureBuilder().modelClass(targetClass).
//				toBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE).baseNodeInstId(localTargetNodeInstId).build());
//		return searchNodeConfigList;
//	}
//
//	public static List<UIModelNodeMapConfigure> buildResUserOrgConfigure(
//			Class<? extends ServiceEntityNode> baseModelClass,
//			String baseNodeInstId)
//			throws SearchConfigureException {
//		List<UIModelNodeMapConfigure> searchNodeConfigList = new ArrayList<>();
//		String localBaseNodeInstId = getNodeInstIdWrapper(baseModelClass, baseNodeInstId);
//		searchNodeConfigList.add(new UIModelNodeMapConfigureBuilder().modelClass(LogonUser.class).
//				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).
//				mapFieldUUID("resEmployeeUUID").baseNodeInstId(localBaseNodeInstId).build());
//		searchNodeConfigList.add(new UIModelNodeMapConfigureBuilder().modelClass(Organization.class).
//				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).
//				mapFieldUUID("resOrgUUID").baseNodeInstId(localBaseNodeInstId).build());
//		return searchNodeConfigList;
//	}

//	public static List<UIModelNodeMapConfigure> genDefServiceEntityHeader(Class<? extends ServiceEntityNode> serviceDocClass,
//																				String nodeInstId)  {
//		try {
//			List<UIModelNodeMapConfigure> searchNodeConfigList = new ArrayList<>();
//			searchNodeConfigList.add(ServiceUIModelExtensionHelper.genUIConfBuilder().modelClass(serviceDocClass).hostNodeFlag(true).build());
//			String localNodeInstId = ServiceEntityStringHelper.checkNullString(nodeInstId) ?
//					UIModelNodeMapConfigureBuilder.getDefaultNodeInstId(serviceDocClass) : nodeInstId;
//			// Search node Array :[Last created, updated by]
//			// searchNodeConfigList.addAll(SearchDocConfigHelper.genCreatedUpdatedBySearchNodeConfigureList(localNodeInstId));
//			return searchNodeConfigList;
//		} catch (NoSuchFieldException | IllegalAccessException | SearchConfigureException e) {
//			throw new RuntimeException(e);
//		}
//    }

}
