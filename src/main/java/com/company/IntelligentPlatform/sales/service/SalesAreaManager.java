package com.company.IntelligentPlatform.sales.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.sales.dto.SalesAreaUIModel;
import com.company.IntelligentPlatform.sales.repository.SalesAreaRepository;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.SalesAreaManagerProxy;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.sales.model.SalesArea;
import com.company.IntelligentPlatform.sales.model.SalesAreaConfigureProxy;

/**
 * Logic Manager CLASS FOR Service Entity [SalesArea]
 *
 * @author
 * @date Fri Feb 26 11:20:47 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
public class SalesAreaManager extends SalesAreaManagerProxy {

	@Autowired
	SalesAreaRepository salesAreaDAO;

	@Autowired
	SalesAreaConfigureProxy salesAreaConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	public SalesAreaManager() {
		super.seConfigureProxy = new SalesAreaConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new SalesAreaDAO();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(salesAreaDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(salesAreaConfigureProxy);
	}

	public SalesAreaUIModel convSalesAreaUIModelCom(SalesArea salesArea)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		SalesAreaUIModel salesAreaUIModel = new SalesAreaUIModel();
		convSalesAreaToUI(salesArea, salesAreaUIModel);
		SalesArea parentArea = (SalesArea) getEntityNodeByKey(
				salesArea.getParentAreaUUID(), "uuid", SalesArea.NODENAME, null);
		convParentAreaToUI(parentArea, salesAreaUIModel);
		SalesArea rootArea = (SalesArea) getEntityNodeByKey(
				salesArea.getRootAreaUUID(), "uuid", SalesArea.NODENAME, null);
		convRootAreaToUI(rootArea, salesAreaUIModel);
		setSalesAreaNameLabel(salesArea, parentArea, rootArea, salesAreaUIModel);
		return salesAreaUIModel;
	}

	public void convSalesAreaToUI(SalesArea salesArea,
			SalesAreaUIModel salesAreaUIModel)
			throws ServiceEntityInstallationException {
		if (salesArea != null) {
			salesAreaUIModel.setUuid(salesArea.getUuid());
			salesAreaUIModel.setId(salesArea.getId());
			salesAreaUIModel.setName(salesArea.getName());
			salesAreaUIModel.setNote(salesArea.getNote());
			salesAreaUIModel.setParentAreaUUID(salesArea.getParentAreaUUID());
			salesAreaUIModel.setLevel(salesArea.getLevel());
			salesAreaUIModel.setConvLabelType(salesArea.getConvLabelType());
			salesAreaUIModel.setRootAreaUUID(salesArea.getRootAreaUUID());
		}
	}

	public void setSalesAreaNameLabel(SalesArea salesArea,
			SalesArea parentArea, SalesArea rootArea,
			SalesAreaUIModel salesAreaUIModel) {
		String nameLabel = getNameLabel(salesArea, parentArea, rootArea);
		salesAreaUIModel.setNameLabel(nameLabel);
	}

	public void convUIToSalesArea(SalesArea rawEntity,
			SalesAreaUIModel salesAreaUIModel) {
		rawEntity.setUuid(salesAreaUIModel.getUuid());
		rawEntity.setId(salesAreaUIModel.getId());
		rawEntity.setName(salesAreaUIModel.getName());
		rawEntity.setNote(salesAreaUIModel.getNote());
		rawEntity.setConvLabelType(salesAreaUIModel.getConvLabelType());
	}
	
	@Override
	public List<ServiceEntityNode> getAllSubAreaByKeyList(List<ServiceBasicKeyStructure> keyList,String client) throws ServiceEntityConfigureException{
		List<ServiceEntityNode> rawAreaList = getEntityNodeListByKeyList(keyList, SalesArea.NODENAME, client,null);
		if(rawAreaList == null || rawAreaList.size() == 0){
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		List<ServiceEntityNode> allRawList = getEntityNodeListByKey(null, null,
				MaterialType.NODENAME, client, null);
		for(ServiceEntityNode seNode:rawAreaList){
			SalesArea salesArea = (SalesArea) seNode;
			resultList.add(salesArea);
			List<ServiceEntityNode> tmpSubAreaList = getAllSubArea(salesArea, allRawList);
			if(tmpSubAreaList != null && tmpSubAreaList.size() > 0){
				ServiceCollectionsHelper.mergeToList(resultList, tmpSubAreaList);
			}
		}
		return resultList;
	}
	
	/**
	 * Logic to get all sub sales area list from current sales area
	 * 
	 * @param materialType
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getAllSubArea(SalesArea salesArea, List<ServiceEntityNode> rawAreaList)
			throws ServiceEntityConfigureException {
		if (rawAreaList == null || rawAreaList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> subTypeList = filterAllSubSalesAreaOnline(
				salesArea.getUuid(), rawAreaList);
		return subTypeList;
	}

	public List<ServiceEntityNode> filterAllSubSalesAreaOnline(
			String parentAreaUUID, List<ServiceEntityNode> rawAreaList) {
		if (ServiceCollectionsHelper.checkNullList(rawAreaList)) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (ServiceEntityNode seNode : rawAreaList) {
			SalesArea salesArea = (SalesArea) seNode;
			if (parentAreaUUID.equals(salesArea.getParentAreaUUID())) {
				resultList.add(salesArea);
				List<ServiceEntityNode> deeperSubAreaList = filterAllSubSalesAreaOnline(
						salesArea.getUuid(), rawAreaList);
				if (deeperSubAreaList != null && deeperSubAreaList.size() > 0) {
					ServiceCollectionsHelper.mergeToList(resultList, deeperSubAreaList);
				}
			}
		}
		return resultList;
	}
	

	/**
	 * Logic to create sales area from parent node
	 * 
	 * @param parentNodeUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public SalesArea newSalesAreaFromParent(String parentUUID)
			throws ServiceEntityConfigureException {
		SalesArea parentArea = (SalesArea) getEntityNodeByKey(parentUUID,
				IServiceEntityNodeFieldConstant.UUID, SalesArea.NODENAME, null);
		if (parentArea == null) {
			return null;
		}
		SalesArea salesArea = (SalesArea) newRootEntityNode(parentArea
				.getClient());
		setParentAreaLogic(salesArea, parentArea);
		return salesArea;
	}

	public int calculateLevel(SalesArea parentArea) {
		if (parentArea == null) {
			return SalesArea.LEVEL1;
		}
		if (parentArea.getLevel() < SalesArea.LEVEL4) {
			return parentArea.getLevel() + 1;
		}
		return SalesArea.LEVEL4;
	}

	/**
	 * Core logic to calculate name label
	 * 
	 * @param parentArea
	 * @return
	 */
	public String getNameLabel(SalesArea salesArea, SalesArea parentArea,
			SalesArea rootArea) {
		if (salesArea == null) {
			return null;
		}
		if (salesArea.getConvLabelType() == SalesArea.CONVTYPE_STANDALONE) {
			return salesArea.getName();
		}
		if (salesArea.getConvLabelType() == SalesArea.CONVTYPE_COMBINE_FROMROOT) {
			if (salesArea.getLevel() == SalesArea.LEVEL1) {
				return salesArea.getName();
			}
			if (salesArea.getLevel() == SalesArea.LEVEL2) {
				if (parentArea != null) {
					if(salesArea.getName() != null && !salesArea.getName().equals(ServiceEntityStringHelper.EMPTYSTRING)){
						return parentArea.getName() + "-" + salesArea.getName();
					}else{
						return parentArea.getName();
					}
				}
				if (rootArea != null) {
					if(salesArea.getName() != null && !salesArea.getName().equals(ServiceEntityStringHelper.EMPTYSTRING)){
						return rootArea.getName() + "-" + rootArea.getName();
					}else{
						return rootArea.getName();
					}
					
				}
			}
			if (salesArea.getLevel() == SalesArea.LEVEL3) {
				if (parentArea != null && rootArea != null) {
					if(salesArea.getName() != null && !salesArea.getName().equals(ServiceEntityStringHelper.EMPTYSTRING)){
						return rootArea.getName() + "-" + parentArea.getName()
								+ "-" + salesArea.getName();
					}else{
						return rootArea.getName() + "-" + parentArea.getName();
					}				
				} else {
					return salesArea.getName();
				}
			}
		}
		return salesArea.getName();
	}

	public SalesArea filterSalesAreaByUUID(List<ServiceEntityNode> rawAreaList,
			String areaUUID) {
		if (ServiceCollectionsHelper.checkNullList(rawAreaList)) {
			return null;
		}
		if (ServiceEntityStringHelper.checkNullString(areaUUID)) {
			return null;
		}
		for (ServiceEntityNode seNode : rawAreaList) {
			SalesArea salesArea = (SalesArea) seNode;
			if (areaUUID.equals(salesArea.getUuid())) {
				return salesArea;
			}
		}
		return null;
	}
	
	public ServiceEntityNode filterSalesAreaByName(List<ServiceEntityNode> rawAreaList,
			String areaName) {
		if (ServiceCollectionsHelper.checkNullList(rawAreaList)) {
			return null;
		}
		if (ServiceEntityStringHelper.checkNullString(areaName)) {
			return null;
		}
		for (ServiceEntityNode seNode : rawAreaList) {
			SalesArea salesArea = (SalesArea) seNode;
			if (areaName.equals(salesArea.getName())) {
				return salesArea;
			}
		}
		return null;
	}

	/**
	 * Core logic to set parent area && root information from parent material
	 * 
	 * @param salesArea
	 * @param parent
	 */
	public void setParentAreaLogic(SalesArea salesArea, SalesArea parent) {
		if (salesArea != null && parent != null) {
			salesArea.setParentAreaUUID(parent.getUuid());
			if (parent.getRootAreaUUID() == null
					|| parent.getRootNodeUUID().equals(
							ServiceEntityStringHelper.EMPTYSTRING)) {
				// In case a parent material is on top level
				salesArea.setRootAreaUUID(parent.getUuid());
			} else {
				// In case a parent material is on top level
				salesArea.setRootAreaUUID(parent.getRootAreaUUID());
			}
		}
		int level = calculateLevel(parent);
		salesArea.setLevel(level);
	}

	public void convParentAreaToUI(SalesArea parent,
			SalesAreaUIModel salesAreaUIModel) {
		if (parent != null) {
			salesAreaUIModel.setParentAreaID(parent.getId());
			salesAreaUIModel.setParentAreaName(parent.getName());
		}
	}

	public void convUIToParentArea(SalesArea rawEntity,
			SalesAreaUIModel salesAreaUIModel) {
		rawEntity.setId(salesAreaUIModel.getParentAreaID());
		rawEntity.setName(salesAreaUIModel.getParentAreaName());
	}

	public void convRootAreaToUI(SalesArea root,
			SalesAreaUIModel salesAreaUIModel) {
		if (root != null) {
			salesAreaUIModel.setRootAreaID(root.getId());
			salesAreaUIModel.setRootAreaName(root.getName());
		}
	}

	public void convUIToRootArea(SalesArea rawEntity,
			SalesAreaUIModel salesAreaUIModel) {
		rawEntity.setId(salesAreaUIModel.getRootAreaID());
		rawEntity.setName(salesAreaUIModel.getRootAreaName());
	}
}