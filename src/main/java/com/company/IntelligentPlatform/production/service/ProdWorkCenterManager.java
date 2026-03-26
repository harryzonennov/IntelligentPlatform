package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.production.dto.ProdWorkCenterCalendarItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProdWorkCenterResItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProdWorkCenterSearchModel;
import com.company.IntelligentPlatform.production.dto.ProdWorkCenterUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionResourceUnionUIModel;
import com.company.IntelligentPlatform.production.repository.ProdWorkCenterRepository;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.production.model.ProdWorkCenterCalendarItem;
import com.company.IntelligentPlatform.production.model.ProdWorkCenterConfigureProxy;
import com.company.IntelligentPlatform.production.model.ProdWorkCenterResItem;
import com.company.IntelligentPlatform.production.model.ProductionResourceUnion;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [ProdWorkCenter]
 *
 * @author
 * @date Thu Mar 24 00:07:53 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class ProdWorkCenterManager extends ServiceEntityManager {

	public static final String METHOD_ConvProdWorkCenterResItemToUI = "convProdWorkCenterResItemToUI";

	public static final String METHOD_ConvUIToProdWorkCenterResItem = "convUIToProdWorkCenterResItem";

	public static final String METHOD_ConvProductionResourceUnionToItemUI = "convProductionResourceUnionToItemUI";

	public static final String METHOD_ConvParentWorkCenterToItemUI = "convParentWorkCenterToItemUI";

	public static final String METHOD_ConvProdWorkCenterToUI = "convProdWorkCenterToUI";

	public static final String METHOD_ConvUIToProdWorkCenter = "convUIToProdWorkCenter";

	public static final String METHOD_ConvRefCostCenterToUI = "convRefCostCenterToUI";

	public static final String METHOD_ConvProdWorkCenterCalendarItemToUI = "convProdWorkCenterCalendarItemToUI";

	public static final String METHOD_ConvUIToProdWorkCenterCalendarItem = "convUIToProdWorkCenterCalendarItem";
	
	public static final String METHOD_ConvParentOrganizationToUI = "convParentOrganizationToUI";
    @PersistenceContext
    private EntityManager entityManager;


	@Autowired
	protected ProdWorkCenterRepository prodWorkCenterDAO;

	@Autowired
	protected ProdWorkCenterConfigureProxy prodWorkCenterConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected ProdWorkCenterIdHelper prodWorkCenterIdHelper;

	@Autowired
	protected ProductionResourceUnionManager productionResourceUnionManager;

	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;

	@Autowired
	protected BsearchService bsearchService;
	
	@Autowired
	protected OrganizationManager organizationManager;

	private Map<Integer, String> capacityCalculateTypeMap;

	private Map<Integer, String> keyWorkCenterMap;

	private Map<Integer, String> keyResourceMap;

	public ProdWorkCenterManager() {
		super.seConfigureProxy = new ProdWorkCenterConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, prodWorkCenterDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(prodWorkCenterConfigureProxy);
	}

	public ProdWorkCenterResItem getWorkCenterResItem(String resourceUnionUUID,
			String baseUUID, String client)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> rawResItemList = getEntityNodeListByKey(
				baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				ProdWorkCenterResItem.NODENAME, client, null);
		if (rawResItemList == null || rawResItemList.size() == 0) {
			return null;
		}
		for (ServiceEntityNode seNode : rawResItemList) {
			ProdWorkCenterResItem prodWorkCenterResItem = (ProdWorkCenterResItem) seNode;
			if (resourceUnionUUID.equals(prodWorkCenterResItem.getRefUUID())) {
				return prodWorkCenterResItem;
			}
		}
		return null;
	}

	/**
	 * Core method to get sub production resource union list
	 * 
	 * @param baseUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getRawSubProdResourceUnionList(
			String baseUUID, String client)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> rawResItemList = getEntityNodeListByKey(
				baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				ProdWorkCenterResItem.NODENAME, client, null);
		if (rawResItemList == null || rawResItemList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawResItemList) {
			ProdWorkCenterResItem prodWorkCenterResItem = (ProdWorkCenterResItem) seNode;
			ProductionResourceUnion productionResourceUnion = (ProductionResourceUnion) productionResourceUnionManager
					.getEntityNodeByKey(prodWorkCenterResItem.getRefUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProductionResourceUnion.NODENAME, client, null);
			resultList.add(productionResourceUnion);
		}
		return resultList;
	}

	/**
	 * Core method to get sub production resource union list
	 * 
	 * @param baseUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 */
	public List<ProductionResourceUnionUIModel> getSubProdResourceUnionList(
			String baseUUID, String client)
			throws ServiceEntityConfigureException,
			ServiceEntityInstallationException {
		List<ServiceEntityNode> rawProdResourceUnionList = getRawSubProdResourceUnionList(
				baseUUID, client);
		if (rawProdResourceUnionList == null
				|| rawProdResourceUnionList.size() == 0) {
			return null;
		}
		List<ProductionResourceUnionUIModel> resultList = new ArrayList<ProductionResourceUnionUIModel>();
		for (ServiceEntityNode seNode : rawProdResourceUnionList) {
			ProductionResourceUnion productionResourceUnion = (ProductionResourceUnion) seNode;
			ProductionResourceUnionUIModel productionResourceUnionUIModel = new ProductionResourceUnionUIModel();
			productionResourceUnionManager.convProductionResourceUnionToUI(
					productionResourceUnion, productionResourceUnionUIModel);
			resultList.add(productionResourceUnionUIModel);
		}
		return resultList;
	}

	public void convProdWorkCenterToUI(ProdWorkCenter prodWorkCenter,
									   ProdWorkCenterUIModel prodWorkCenterUIModel)
			throws ServiceEntityInstallationException {
		convProdWorkCenterToUI(prodWorkCenter, prodWorkCenterUIModel, null);
	}

	public void convProdWorkCenterToUI(ProdWorkCenter prodWorkCenter,
									   ProdWorkCenterUIModel prodWorkCenterUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (prodWorkCenter != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(prodWorkCenter, prodWorkCenterUIModel);
			prodWorkCenterUIModel.setId(prodWorkCenter.getId());
			prodWorkCenterUIModel.setName(prodWorkCenter.getName());
			prodWorkCenterUIModel.setNote(prodWorkCenter.getNote());
			prodWorkCenterUIModel.setFullName(prodWorkCenter.getFullName());
			prodWorkCenterUIModel.setKeyWorkCenterFlag(prodWorkCenter
					.getKeyWorkCenterFlag());
			prodWorkCenterUIModel.setRefCostCenterUUID(prodWorkCenter
					.getRefCostCenterUUID());
			prodWorkCenterUIModel.setUsageNote(prodWorkCenter.getUsageNote());
			prodWorkCenterUIModel.setRefGroupUUID(prodWorkCenter
					.getRefGroupUUID());
			this.initCapacityCalculateTypeMap();
			prodWorkCenterUIModel
					.setCapacityCalculateTypeValue(this.capacityCalculateTypeMap
							.get(prodWorkCenter.getCapacityCalculateType()));
			this.initKeyWorkCenterMap();
			String keyWorkCenterValue = this.keyWorkCenterMap
					.get(prodWorkCenter.getKeyWorkCenterFlag());
			prodWorkCenterUIModel.setKeyWorkCenterValue(keyWorkCenterValue);
			prodWorkCenterUIModel.setKeyWorkCenter(prodWorkCenter
					.getKeyWorkCenter());
			prodWorkCenterUIModel.setCapacityCalculateType(prodWorkCenter
					.getCapacityCalculateType());
			prodWorkCenterUIModel.setAddress(prodWorkCenter.getAddress());
			prodWorkCenterUIModel.setTelephone(prodWorkCenter.getTelephone());
			prodWorkCenterUIModel.setFax(prodWorkCenter.getFax());
			prodWorkCenterUIModel.setEmail(prodWorkCenter.getEmail());
			prodWorkCenterUIModel.setWebPage(prodWorkCenter.getWebPage());
			prodWorkCenterUIModel.setRefCityUUID(prodWorkCenter
					.getRefCityUUID());
			prodWorkCenterUIModel.setCityName(prodWorkCenter.getCityName());
			prodWorkCenterUIModel.setCountryName(prodWorkCenter
					.getCountryName());
			prodWorkCenterUIModel.setStateName(prodWorkCenter.getStateName());
			prodWorkCenterUIModel.setTownZone(prodWorkCenter.getTownZone());
			prodWorkCenterUIModel.setSubArea(prodWorkCenter.getSubArea());
			prodWorkCenterUIModel.setStreetName(prodWorkCenter.getStreetName());
			prodWorkCenterUIModel.setHouseNumber(prodWorkCenter
					.getHouseNumber());
			prodWorkCenterUIModel.setPostcode(prodWorkCenter.getPostcode());
			prodWorkCenterUIModel.setParentOrganizationUUID(prodWorkCenter.getParentOrganizationUUID());
			prodWorkCenterUIModel.setOrganizationFunction(prodWorkCenter.getOrganizationFunction());
			if(logonInfo != null){
				Map<Integer, String> orgFunctionMap =
						organizationManager.initOrgFunctionMap(logonInfo.getLanguageCode(), prodWorkCenter.getClient(),
								false);
				if(orgFunctionMap != null){
					prodWorkCenterUIModel.setOrganizationFunctionValue(orgFunctionMap.get(prodWorkCenter.getOrganizationFunction()));
				}
			}
		}
	}
	
	public void convParentOrganizationToUI(Organization parentOrganization,
			ProdWorkCenterUIModel prodWorkCenterUIModel)
			throws ServiceEntityConfigureException {
		if (parentOrganization != null) {
			prodWorkCenterUIModel.setParentOrganizationId(parentOrganization
					.getId());
			prodWorkCenterUIModel.setParentOrganizationName(parentOrganization
					.getName());
		}
	}

	public void convUIToProdWorkCenter(
			ProdWorkCenterUIModel prodWorkCenterUIModel,
			ProdWorkCenter rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(prodWorkCenterUIModel, rawEntity);
		if (!ServiceEntityStringHelper.checkNullString(prodWorkCenterUIModel
				.getParentOrganizationUUID())) {
			rawEntity.setParentOrganizationUUID(prodWorkCenterUIModel.getParentOrganizationUUID());
		}
		rawEntity.setId(prodWorkCenterUIModel.getId());
		rawEntity.setName(prodWorkCenterUIModel.getName());
		rawEntity.setNote(prodWorkCenterUIModel.getNote());
		rawEntity.setFullName(prodWorkCenterUIModel.getFullName());
		rawEntity.setKeyWorkCenterFlag(prodWorkCenterUIModel
				.getKeyWorkCenterFlag());
		rawEntity.setKeyWorkCenter(prodWorkCenterUIModel.getKeyWorkCenter());
		rawEntity.setRefCostCenterUUID(prodWorkCenterUIModel
				.getRefCostCenterUUID());
		rawEntity.setUsageNote(prodWorkCenterUIModel.getUsageNote());
		rawEntity.setRefGroupUUID(prodWorkCenterUIModel.getRefGroupUUID());
		rawEntity.setCapacityCalculateType(prodWorkCenterUIModel
				.getCapacityCalculateType());
		rawEntity.setTelephone(prodWorkCenterUIModel.getTelephone());
		rawEntity.setAddress(prodWorkCenterUIModel.getAddress());
		rawEntity.setFax(prodWorkCenterUIModel.getFax());
		rawEntity.setRefCityUUID(prodWorkCenterUIModel.getRefCityUUID());
		rawEntity.setCityName(prodWorkCenterUIModel.getCityName());
		rawEntity.setCountryName(prodWorkCenterUIModel.getCountryName());
		rawEntity.setStateName(prodWorkCenterUIModel.getStateName());
		rawEntity.setTownZone(prodWorkCenterUIModel.getTownZone());
		rawEntity.setSubArea(prodWorkCenterUIModel.getSubArea());
		rawEntity.setStreetName(prodWorkCenterUIModel.getStreetName());
		rawEntity.setHouseNumber(prodWorkCenterUIModel.getHouseNumber());
		rawEntity.setOrganizationFunction(prodWorkCenterUIModel.getOrganizationFunction());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convProdWorkCenterResItemToUI(
			ProdWorkCenterResItem prodWorkCenterResItem,
			ProdWorkCenterResItemUIModel prodWorkCenterResItemUIModel)
			throws ServiceEntityInstallationException {
		if (prodWorkCenterResItem != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(prodWorkCenterResItem.getUuid())) {
				prodWorkCenterResItemUIModel.setUuid(prodWorkCenterResItem
						.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(prodWorkCenterResItem.getParentNodeUUID())) {
				prodWorkCenterResItemUIModel
						.setParentNodeUUID(prodWorkCenterResItem
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(prodWorkCenterResItem.getRootNodeUUID())) {
				prodWorkCenterResItemUIModel
						.setRootNodeUUID(prodWorkCenterResItem
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(prodWorkCenterResItem.getClient())) {
				prodWorkCenterResItemUIModel.setClient(prodWorkCenterResItem
						.getClient());
			}
			prodWorkCenterResItemUIModel
					.setKeyResourceFlag(prodWorkCenterResItem
							.getKeyResourceFlag());
			this.initKeyResourceMap();
			prodWorkCenterResItemUIModel
					.setKeyResourceValue(this.keyResourceMap
							.get(prodWorkCenterResItem.getKeyResourceFlag()));
			prodWorkCenterResItemUIModel.setRefUUID(prodWorkCenterResItem
					.getRefUUID());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convProductionResourceUnionToItemUI(
			ProductionResourceUnion productionResourceUnion,
			ProdWorkCenterResItemUIModel prodWorkCenterResItemUIModel)
			throws ServiceEntityInstallationException {
		if (productionResourceUnion != null) {

			prodWorkCenterResItemUIModel.setResourceId(productionResourceUnion
					.getId());
			prodWorkCenterResItemUIModel
					.setResourceName(productionResourceUnion.getName());
			prodWorkCenterResItemUIModel
					.setResourceType(productionResourceUnion.getResourceType());
			Map<Integer, String> resourceTypeMap = productionResourceUnionManager
					.initResourceTypeMap();
			prodWorkCenterResItemUIModel.setResourceTypeValue(resourceTypeMap
					.get(productionResourceUnion.getResourceType()));
		}
	}

	public void convParentWorkCenterToItemUI(ProdWorkCenter prodWorkCenter,
			ProdWorkCenterResItemUIModel prodWorkCenterResItemUIModel) {
		if (prodWorkCenter != null) {
			prodWorkCenterResItemUIModel.setRefWorkCenterId(prodWorkCenter
					.getId());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:prodWorkCenterResItem
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToProdWorkCenterResItem(
			ProdWorkCenterResItemUIModel prodWorkCenterResItemUIModel,
			ProdWorkCenterResItem rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(prodWorkCenterResItemUIModel.getUuid())) {
			rawEntity.setUuid(prodWorkCenterResItemUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(prodWorkCenterResItemUIModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(prodWorkCenterResItemUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(prodWorkCenterResItemUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(prodWorkCenterResItemUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(prodWorkCenterResItemUIModel.getClient())) {
			rawEntity.setClient(prodWorkCenterResItemUIModel.getClient());
		}
		rawEntity.setKeyResourceFlag(prodWorkCenterResItemUIModel
				.getKeyResourceFlag());
		rawEntity.setRefUUID(prodWorkCenterResItemUIModel.getRefUUID());
	}

	public Map<Integer, String> initCapacityCalculateTypeMap()
			throws ServiceEntityInstallationException {
		if (this.capacityCalculateTypeMap == null) {
			this.capacityCalculateTypeMap = serviceDropdownListHelper
					.getUIDropDownMap(ProdWorkCenterUIModel.class,
							"capacityCalculateType");
		}
		return this.capacityCalculateTypeMap;
	}

	public Map<Integer, String> initKeyWorkCenterMap()
			throws ServiceEntityInstallationException {
		if (this.keyWorkCenterMap == null) {
			this.keyWorkCenterMap = standardKeyFlagProxy.getKeyFlagMap();
		}
		return this.keyWorkCenterMap;
	}

	public Map<Integer, String> initKeyResourceMap()
			throws ServiceEntityInstallationException {
		if (this.keyResourceMap == null) {
			this.keyResourceMap = standardKeyFlagProxy.getKeyFlagMap();
		}
		return this.keyResourceMap;
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		ProdWorkCenter prodWorkCenter = (ProdWorkCenter) super
				.newRootEntityNode(client);
		String prodWorkCenterID = prodWorkCenterIdHelper.genDefaultId(client);
		prodWorkCenter.setId(prodWorkCenterID);
		return prodWorkCenter;
	}

	public void convRefCostCenterToUI(Organization organization,
			ProdWorkCenterUIModel prodWorkCenterUIModel) {
		if (organization != null) {
			prodWorkCenterUIModel.setRefCostCenterId(organization.getId());
			prodWorkCenterUIModel.setRefCostCenterName(organization.getName());
			prodWorkCenterUIModel.setRefCostCenterUUID(organization.getUuid());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convProdWorkCenterCalendarItemToUI(
			ProdWorkCenterCalendarItem prodWorkCenterCalendarItem,
			ProdWorkCenterCalendarItemUIModel prodWorkCenterCalendarItemUIModel) {
		if (prodWorkCenterCalendarItem != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(prodWorkCenterCalendarItem.getUuid())) {
				prodWorkCenterCalendarItemUIModel
						.setUuid(prodWorkCenterCalendarItem.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(prodWorkCenterCalendarItem
							.getParentNodeUUID())) {
				prodWorkCenterCalendarItemUIModel
						.setParentNodeUUID(prodWorkCenterCalendarItem
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(prodWorkCenterCalendarItem
							.getRootNodeUUID())) {
				prodWorkCenterCalendarItemUIModel
						.setRootNodeUUID(prodWorkCenterCalendarItem
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(prodWorkCenterCalendarItem.getClient())) {
				prodWorkCenterCalendarItemUIModel
						.setClient(prodWorkCenterCalendarItem.getClient());
			}
			if (prodWorkCenterCalendarItem.getEndDate() != null) {
				prodWorkCenterCalendarItemUIModel
						.setEndDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(prodWorkCenterCalendarItem.getEndDate()));
				prodWorkCenterCalendarItemUIModel
						.setRefUUID(prodWorkCenterCalendarItem.getRefUUID());
				if (prodWorkCenterCalendarItem.getStartDate() != null) {
					prodWorkCenterCalendarItemUIModel
							.setStartDate(DefaultDateFormatConstant.DATE_FORMAT
									.format(prodWorkCenterCalendarItem
											.getStartDate()));
				}
				prodWorkCenterCalendarItemUIModel
						.setRefNodeName(prodWorkCenterCalendarItem
								.getRefNodeName());
			}
		}

	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:prodWorkCenterCalendarItem
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToProdWorkCenterCalendarItem(
			ProdWorkCenterCalendarItemUIModel prodWorkCenterCalendarItemUIModel,
			ProdWorkCenterCalendarItem rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(prodWorkCenterCalendarItemUIModel.getUuid())) {
			rawEntity.setUuid(prodWorkCenterCalendarItemUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(prodWorkCenterCalendarItemUIModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(prodWorkCenterCalendarItemUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(prodWorkCenterCalendarItemUIModel
						.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(prodWorkCenterCalendarItemUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(prodWorkCenterCalendarItemUIModel.getClient())) {
			rawEntity.setClient(prodWorkCenterCalendarItemUIModel.getClient());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(prodWorkCenterCalendarItemUIModel.getEndDate())) {
			try {
				rawEntity.setEndDate(DefaultDateFormatConstant.DATE_FORMAT
						.parse(prodWorkCenterCalendarItemUIModel.getEndDate()));
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setRefUUID(prodWorkCenterCalendarItemUIModel.getRefUUID());
		if (!ServiceEntityStringHelper
				.checkNullString(prodWorkCenterCalendarItemUIModel
						.getStartDate())) {
			try {
				rawEntity
						.setStartDate(DefaultDateFormatConstant.DATE_FORMAT
								.parse(prodWorkCenterCalendarItemUIModel
										.getStartDate()));
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setRefNodeName(prodWorkCenterCalendarItemUIModel
				.getRefNodeName());
	}

	public List<ServiceEntityNode> searchInternal(
			ProdWorkCenterSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[prodWorkCenter]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(ProdWorkCenter.SENAME);
		searchNodeConfig0.setNodeName(ProdWorkCenter.NODENAME);
		searchNodeConfig0.setNodeInstID(ProdWorkCenter.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[prodWorkCenterResItem]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(ProdWorkCenterResItem.SENAME);
		searchNodeConfig1.setNodeName(ProdWorkCenterResItem.NODENAME);
		searchNodeConfig1.setNodeInstID(ProdWorkCenterResItem.NODENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1.setBaseNodeInstID(ProdWorkCenter.SENAME);
		searchNodeConfig1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig1.setBaseNodeInstID(ProdWorkCenter.SENAME);
		searchNodeConfigList.add(searchNodeConfig1);
		// Search node:[prodWorkCenterCalendarItem]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(ProdWorkCenterCalendarItem.SENAME);
		searchNodeConfig2.setNodeName(ProdWorkCenterCalendarItem.NODENAME);
		searchNodeConfig2.setNodeInstID(ProdWorkCenterCalendarItem.NODENAME);
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig2.setBaseNodeInstID(ProdWorkCenter.SENAME);
		searchNodeConfigList.add(searchNodeConfig2);
		// Search node:[organization]
		BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
		searchNodeConfig3.setSeName(Organization.SENAME);
		searchNodeConfig3.setNodeName(Organization.NODENAME);
		searchNodeConfig3.setNodeInstID(Organization.SENAME);
		searchNodeConfig3.setStartNodeFlag(false);
		searchNodeConfig3
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig3.setMapBaseFieldName("refCostCenterUUID");
		searchNodeConfig3.setMapSourceFieldName("uuid");
		searchNodeConfig3.setBaseNodeInstID(ProdWorkCenter.SENAME);
		searchNodeConfigList.add(searchNodeConfig3);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

}
