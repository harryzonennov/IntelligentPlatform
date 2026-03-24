package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;

public class ServiceSelect2ModelProxy {
	
	public static List<ServiceSelect2Model> convertToSelec2JSONList(
			Map<?, String> dataMap) {
		Set<?> keySet = dataMap.keySet();
		Iterator<?> it = keySet.iterator();
		List<ServiceSelect2Model> resultList = new ArrayList<ServiceSelect2Model>();
		while (it.hasNext()) {
			Object key = it.next();
			ServiceSelect2Model select2Model = new ServiceSelect2Model();
			select2Model.setId(key.toString());
			select2Model.setText(dataMap.get(key));
			resultList.add(select2Model);
		}
		return resultList;
	}
	
//	public static List<ServiceSelect2Model> convSeNodeToSelect2ModelWithName(List<ServiceEntityNode> rawList){
//		List<ServiceSelect2Model> resultList = new ArrayList<ServiceSelect2Model>();
//		if(ServiceCollectionsHelper.checkNullList(rawList)){
//			return null;
//		}
//		for(ServiceEntityNode seNode:rawList){
//			ServiceSelect2Model select2Model = new ServiceSelect2Model();
//			select2Model.setId(seNode.getUuid());
//			select2Model.setText(seNode.getName());
//			resultList.add(select2Model);
//		}
//		return resultList;
//	}
//	
//	public static List<ServiceSelect2Model> convSeNodeToSelect2ModelWithId(List<ServiceEntityNode> rawList){
//		List<ServiceSelect2Model> resultList = new ArrayList<ServiceSelect2Model>();
//		if(ServiceCollectionsHelper.checkNullList(rawList)){
//			return null;
//		}
//		for(ServiceEntityNode seNode:rawList){
//			ServiceSelect2Model select2Model = new ServiceSelect2Model();
//			select2Model.setId(seNode.getUuid());
//			select2Model.setText(seNode.getId());
//			resultList.add(select2Model);
//		}
//		return resultList;
//	}
//	
//	public static List<ServiceSelect2Model> convSeNodeToSelect2ModelWithNamePairs(List<ServiceEntityNode> rawList){
//		List<ServiceSelect2Model> resultList = new ArrayList<ServiceSelect2Model>();
//		if(ServiceCollectionsHelper.checkNullList(rawList)){
//			return null;
//		}
//		for(ServiceEntityNode seNode:rawList){
//			ServiceSelect2Model select2Model = new ServiceSelect2Model();
//			select2Model.setId(seNode.getName());
//			select2Model.setText(seNode.getName());
//			resultList.add(select2Model);
//		}
//		return resultList;
//	}
//	
//	public static List<ServiceSelect2Model> convSeNodeToSelect2ModelWithIdPairs(List<ServiceEntityNode> rawList){
//		List<ServiceSelect2Model> resultList = new ArrayList<ServiceSelect2Model>();
//		if(ServiceCollectionsHelper.checkNullList(rawList)){
//			return null;
//		}
//		for(ServiceEntityNode seNode:rawList){
//			ServiceSelect2Model select2Model = new ServiceSelect2Model();
//			select2Model.setId(seNode.getId());
//			select2Model.setText(seNode.getId());
//			resultList.add(select2Model);
//		}
//		return resultList;
//	}
//	
//	public static List<ServiceSelect2Model> convSeNodeToSelect2ModelWithIDName(List<ServiceEntityNode> rawList){
//		List<ServiceSelect2Model> resultList = new ArrayList<ServiceSelect2Model>();
//		if(ServiceCollectionsHelper.checkNullList(rawList)){
//			return null;
//		}
//		for(ServiceEntityNode seNode:rawList){
//			ServiceSelect2Model select2Model = new ServiceSelect2Model();
//			select2Model.setId(seNode.getUuid());
//			select2Model.setText(seNode.getId() + "-" + seNode.getName());
//			resultList.add(select2Model);
//		}
//		return resultList;
//	}
	
	public static List<ServiceSelect2Model> convStringListToSelect2Model(List<String> rawList){
		List<ServiceSelect2Model> resultList = new ArrayList<ServiceSelect2Model>();
		if(ServiceCollectionsHelper.checkNullList(rawList)){
			return null;
		}
		for(String content:rawList){
			ServiceSelect2Model select2Model = new ServiceSelect2Model();
			select2Model.setId(content);
			select2Model.setText(content);
			resultList.add(select2Model);
		}
		return resultList;
	}

}
