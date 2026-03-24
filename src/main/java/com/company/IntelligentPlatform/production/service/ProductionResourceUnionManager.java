package com.company.IntelligentPlatform.production.service;

import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.production.dto.ProductionResourceUnionUIModel;
// TODO-DAO: import net.thorstein.production.DAO.ProductionResourceUnionDAO;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.production.model.ProductionResourceUnion;
import com.company.IntelligentPlatform.production.model.ProductionResourceUnionConfigureProxy;

/**
 * Logic Manager CLASS FOR Service Entity [ProductionResourceUnion]
 *
 * @author
 * @date Wed Mar 23 23:23:54 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class ProductionResourceUnionManager extends ServiceEntityManager {

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected ProductionResourceUnionDAO productionResourceUnionDAO;

	@Autowired
	protected ProductionResourceUnionConfigureProxy productionResourceUnionConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;
	
	@Autowired
	protected ProductionResourceUnionIdHelper productionResourceUnionIdHelper;
	
	@Autowired
	protected OrganizationManager organizationManager;
	
	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;
	
	protected Map<Integer, String>  resourceTypeMap;
	
	protected Map<Integer, String>  keyResourceFlagMap;
	
	public static final String METHOD_ConvProductionResourceUnionToUI = "convProductionResourceUnionToUI";
	
	public static final String METHOD_ConvUIToProductionResourceUnion = "convUIToProductionResourceUnion";
	
	public static final String METHOD_ConvCostCenterToUI = "convCostCenterToUI";

	public ProductionResourceUnionManager() {
		super.seConfigureProxy = new ProductionResourceUnionConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new ProductionResourceUnionDAO();
	}
	
	public Map<Integer, String> initResourceTypeMap()
			throws ServiceEntityInstallationException {
		if (this.resourceTypeMap == null) {
			this.resourceTypeMap = serviceDropdownListHelper
					.getUIDropDownMap(ProductionResourceUnionUIModel.class,
							"resourceType");
		}
		return this.resourceTypeMap;
	}
	
	public Map<Integer, String> initKeyResourceFlagMap()
			throws ServiceEntityInstallationException {
		if (this.keyResourceFlagMap == null) {
			this.keyResourceFlagMap = standardKeyFlagProxy.getKeyFlagMap();
		}
		return this.keyResourceFlagMap;
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(productionResourceUnionDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(productionResourceUnionConfigureProxy);
	}

	public void convProductionResourceUnionToUI(
			ProductionResourceUnion productionResourceUnion,
			ProductionResourceUnionUIModel productionResourceUnionUIModel)
			throws ServiceEntityInstallationException {
		if (productionResourceUnion != null) {
			productionResourceUnionUIModel.setUuid(productionResourceUnion
					.getUuid());
			productionResourceUnionUIModel.setParentNodeUUID(productionResourceUnion
					.getParentNodeUUID());
			productionResourceUnionUIModel.setRootNodeUUID(productionResourceUnion
					.getRootNodeUUID());
			productionResourceUnionUIModel.setClient(productionResourceUnion
					.getClient());
			productionResourceUnionUIModel.setId(productionResourceUnion
					.getId());
			productionResourceUnionUIModel.setName(productionResourceUnion
					.getName());
			productionResourceUnionUIModel
					.setRefCostCenterUUID(productionResourceUnion
							.getRefCostCenterUUID());
			productionResourceUnionUIModel
					.setKeyResourceFlag(productionResourceUnion.getKeyResourceFlag());
			this.initKeyResourceFlagMap();
			productionResourceUnionUIModel.setKeyResourceValue(this.keyResourceFlagMap.get(productionResourceUnion.getKeyResourceFlag()));
			this.initResourceTypeMap();
			productionResourceUnionUIModel.setResourceTypeValue(this.resourceTypeMap
					.get(productionResourceUnion.getResourceType()));
			productionResourceUnionUIModel
					.setResourceType(productionResourceUnion.getResourceType());
			productionResourceUnionUIModel.setNote(productionResourceUnion
					.getNote());
			productionResourceUnionUIModel
					.setUtilizationRate(productionResourceUnion
							.getUtilizationRate());
			productionResourceUnionUIModel
					.setEfficiency(productionResourceUnion.getEfficiency());
			productionResourceUnionUIModel
					.setDailyShift((int) productionResourceUnion.getDailyShift());
			productionResourceUnionUIModel
					.setCostInhour(productionResourceUnion.getCostInhour());
			productionResourceUnionUIModel
					.setWorkHoursInShift(productionResourceUnion
							.getWorkHoursInShift());
		}
	}

	public void convUIToProductionResourceUnion(
			ProductionResourceUnionUIModel productionResourceUnionUIModel, ProductionResourceUnion rawEntity) {
		if(!ServiceEntityStringHelper.checkNullString(productionResourceUnionUIModel.getUuid())){
			rawEntity.setUuid(productionResourceUnionUIModel.getUuid());
		}
		if(!ServiceEntityStringHelper.checkNullString(productionResourceUnionUIModel.getParentNodeUUID())){
			rawEntity.setParentNodeUUID(productionResourceUnionUIModel.getParentNodeUUID());
		}
		if(!ServiceEntityStringHelper.checkNullString(productionResourceUnionUIModel.getRootNodeUUID())){
			rawEntity.setRootNodeUUID(productionResourceUnionUIModel.getRootNodeUUID());
		}
		if(!ServiceEntityStringHelper.checkNullString(productionResourceUnionUIModel.getClient())){
			rawEntity.setClient(productionResourceUnionUIModel.getClient());
		}
		rawEntity.setId(productionResourceUnionUIModel.getId());
		rawEntity.setName(productionResourceUnionUIModel.getName());
		rawEntity.setRefCostCenterUUID(productionResourceUnionUIModel
				.getRefCostCenterUUID());
		rawEntity.setKeyResourceFlag(productionResourceUnionUIModel
				.getKeyResourceFlag());
		rawEntity.setResourceType(productionResourceUnionUIModel
				.getResourceType());
		rawEntity.setNote(productionResourceUnionUIModel.getNote());
		rawEntity.setUtilizationRate(productionResourceUnionUIModel
				.getUtilizationRate());
		rawEntity.setEfficiency(productionResourceUnionUIModel.getEfficiency());
		rawEntity.setDailyShift(productionResourceUnionUIModel.getDailyShift());
		rawEntity.setCostInhour(productionResourceUnionUIModel.getCostInhour());
		rawEntity.setWorkHoursInShift(productionResourceUnionUIModel
				.getWorkHoursInShift());
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		ProductionResourceUnion productionResourceUnion = (ProductionResourceUnion) super
				.newRootEntityNode(client);
		String productionResourceUnionID = productionResourceUnionIdHelper
				.genDefaultId(client);
		productionResourceUnion.setId(productionResourceUnionID);
		return productionResourceUnion;
	}
	
	public void convCostCenterToUI(Organization organization,
			ProductionResourceUnionUIModel productionResourceUnionUIModel) {
		if (organization != null) {
			productionResourceUnionUIModel.setRefCostCenterID(organization
					.getId());
			productionResourceUnionUIModel.setRefCostCenterUUID(organization.getUuid());
			productionResourceUnionUIModel.setRefCostCenterName(organization
					.getName());
		}
	}


}
