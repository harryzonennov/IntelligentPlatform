package com.company.IntelligentPlatform.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.OrganizationFunctionUIModel;
// TODO-DAO: import ...OrganizationFunctionDAO;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.OrganizationFunction;
import com.company.IntelligentPlatform.common.model.OrganizationFunctionConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

/**
 * Logic Manager CLASS FOR Service Entity [OrganizationFunction]
 *
 * @author
 * @date Thu Sep 01 16:39:44 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class OrganizationFunctionManager extends ServiceEntityManager {

	public static final String METHOD_ConvOrganizationFunctionToUI = "convOrganizationFunctionToUI";

	public static final String METHOD_ConvUIToOrganizationFunction = "convUIToOrganizationFunction";

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected OrganizationFunctionDAO organizationFunctionDAO;

	@Autowired
	protected OrganizationFunctionSearchProxy organizationFunctionSearchProxy;

	@Autowired
	protected OrganizationFunctionConfigureProxy organizationFunctionConfigureProxy;	
	
	private Map<String, String> refOrganizationFunctionMap ;

	public OrganizationFunctionManager() {
		super.seConfigureProxy = new OrganizationFunctionConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new OrganizationFunctionDAO();
	}

	public Map<String, String> getOrganizationFunctionMap(String client) throws ServiceEntityConfigureException {
		if(this.refOrganizationFunctionMap == null || this.refOrganizationFunctionMap.size() == 0){
			List<ServiceEntityNode> rawOrganizationFunctionList = getEntityNodeListByKey(
					null, null, OrganizationFunction.NODENAME, client, null);
			this.refOrganizationFunctionMap = new HashMap<String, String>();
			if(!ServiceCollectionsHelper.checkNullList(rawOrganizationFunctionList)){
				for(ServiceEntityNode seNode:rawOrganizationFunctionList){
					this.refOrganizationFunctionMap.put(seNode.getUuid(), seNode.getName());
				}
			}
		}
		return this.refOrganizationFunctionMap;
	}
	

	public void convOrganizationFunctionToUI(
			OrganizationFunction organizationFunction,
			OrganizationFunctionUIModel organizationFunctionUIModel) {
		DocFlowProxy.convServiceEntityNodeToUIModel(organizationFunction, organizationFunctionUIModel);
	}

	public void convUIToOrganizationFunction(OrganizationFunction rawEntity,
			OrganizationFunctionUIModel organizationFunctionUIModel) {
		DocFlowProxy.convUIToServiceEntityNode(organizationFunctionUIModel, rawEntity);
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(organizationFunctionDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(organizationFunctionConfigureProxy);
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return organizationFunctionSearchProxy;
	}
}
