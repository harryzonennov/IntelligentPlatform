package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.BootStrapTreeUIModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Bootstrap tree model helper class
 * @author Zhang,Hang
 *
 */
@Service
public class BootStrapTreeModelHelper {
	
	public static BootStrapTreeUIModel generateTreeComModel(
			List<BootStrapTreeUIModel> rawTreeModelList) {
		List<BootStrapTreeUIModel> rootNodeList = filterOutFirstLayer(rawTreeModelList);
		if(rootNodeList == null || rootNodeList.size() == 0){
			return null;
		}
		BootStrapTreeUIModel rootNode = rootNodeList.get(0);
		resetSpanClass(rootNode);
		List<BootStrapTreeUIModel> postModelList = rootNode.getPostModelList();
		if (postModelList != null && postModelList.size() > 0) {
			for(BootStrapTreeUIModel treeUIModel:postModelList){
				resetSpanClass(treeUIModel);
			}
		}
		List<BootStrapTreeUIModel> subModelList = filterSubModelList(
				rawTreeModelList, rootNode.getUuid());
		if (subModelList == null || subModelList.size() == 0) {
			return rootNode;
		}
		setTreeComModelRecursive(rootNode, subModelList, rawTreeModelList);
		return rootNode;
	}

	public static void resetSpanClass(BootStrapTreeUIModel bootStrapTreeUIModel) {
		if (ServiceEntityStringHelper.checkNullString(bootStrapTreeUIModel
				.getSpanClass())) {
			if (!ServiceEntityStringHelper.checkNullString(bootStrapTreeUIModel
					.getSpanSubClass())) {
				String spanClass = "badge  "
						+ bootStrapTreeUIModel.getSpanSubClass();
				bootStrapTreeUIModel.setSpanClass(spanClass);
			}
		}
	}

	public static void setTreeComModelRecursive(BootStrapTreeUIModel baseNode,
			List<BootStrapTreeUIModel> modelList,
			List<BootStrapTreeUIModel> rawTreeModelList) {
		if (modelList == null || modelList.size() == 0) {
			return;
		}
		baseNode.setSubModelList(modelList);
		for (BootStrapTreeUIModel treeUIModel : modelList) {
			resetSpanClass(treeUIModel);
			List<BootStrapTreeUIModel> postModelList = treeUIModel.getPostModelList();
			if (postModelList != null && postModelList.size() > 0) {
				for(BootStrapTreeUIModel tempUIModel:postModelList){
					resetSpanClass(tempUIModel);
				}
			}
			List<BootStrapTreeUIModel> subModelList = filterSubModelList(
					rawTreeModelList, treeUIModel.getUuid());
			setTreeComModelRecursive(treeUIModel, subModelList,
					rawTreeModelList);
		}
	}

	public static List<BootStrapTreeUIModel> filterOutFirstLayer(
			List<BootStrapTreeUIModel> rawTreeModelList) {
		List<BootStrapTreeUIModel> resultList = new ArrayList<BootStrapTreeUIModel>();
		if (rawTreeModelList != null && rawTreeModelList.size() > 0) {
			for (BootStrapTreeUIModel treeModel : rawTreeModelList) {
				if (treeModel.getLayer() == 1) {
					resultList.add(treeModel);
				}
			}
		}
		return resultList;
	}

	public static List<BootStrapTreeUIModel> filterSubModelList(
			List<BootStrapTreeUIModel> rawTreeModelList, String baseUUID) {
		List<BootStrapTreeUIModel> resultList = new ArrayList<BootStrapTreeUIModel>();
		if (rawTreeModelList != null && rawTreeModelList.size() > 0) {
			for (BootStrapTreeUIModel treeModel : rawTreeModelList) {
				if (baseUUID.equals(treeModel.getParentNodeUUID())) {
					if (baseUUID.equals(treeModel.getUuid())) {
						continue;
					}
					resultList.add(treeModel);
				}
			}
		}
		return resultList;
	}

}
