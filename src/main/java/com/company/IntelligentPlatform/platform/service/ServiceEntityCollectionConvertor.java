package com.company.IntelligentPlatform.platform.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceCollectionsHelper;

@Component
public class ServiceEntityCollectionConvertor<Template extends ServiceEntityNode> {
	
	public List<Template> generateResultList(List<ServiceEntityNode> rawList){
		if(ServiceCollectionsHelper.checkNullList(rawList)){
			return null;
		}
		List<Template> resultList = new ArrayList<Template>();
		for(ServiceEntityNode seNode:rawList){
			@SuppressWarnings("unchecked")
			Template template = (Template) seNode;
			resultList.add(template);
		}
		return resultList;
	}

}
