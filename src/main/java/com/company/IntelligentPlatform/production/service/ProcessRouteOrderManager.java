package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.production.dto.ProcessRouteOrderSearchModel;
import com.company.IntelligentPlatform.production.dto.ProcessRouteOrderUIModel;
import com.company.IntelligentPlatform.production.dto.ProcessRouteProcessItemUIModel;
import com.company.IntelligentPlatform.production.repository.ProcessRouteOrderRepository;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.model.IReferenceNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrderConfigureProxy;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [ProcessRouteOrder]
 *
 * @author
 * @date Thu Mar 31 11:53:48 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class ProcessRouteOrderManager extends ServiceEntityManager {

	public static final String METHOD_ConvProcessRouteProcessItemToUI = "convProcessRouteProcessItemToUI";

	public static final String METHOD_ConvUIToProcessRouteProcessItem = "convUIToProcessRouteProcessItem";

	public static final String METHOD_ConvParentDocToProcessItemUI = "convParentDocToProcessItemUI";

	public static final String METHOD_ConvProdWorkCenterToUI = "convProdWorkCenterToUI";

	public static final String METHOD_ConvProdProcessToUI = "convProdProcessToUI";

	public static final String METHOD_ConvProcessRouteOrderToUI = "convProcessRouteOrderToUI";

	public static final String METHOD_ConvUIToProcessRouteOrder = "convUIToProcessRouteOrder";

	public static final String METHOD_ConvMaterialStockKeepUnitToUI = "convMaterialStockKeepUnitToUI";

	@Autowired
	protected ProcessRouteOrderRepository processRouteOrderDAO;

	@Autowired
	protected ProcessRouteOrderConfigureProxy processRouteOrderConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected ProcessRouteOrderIdHelper processRouteOrderIdHelper;

	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	private Map<Integer, String> statusMap;

	private Map<Integer, String> routeTypeMap;

	private Map<Integer, String> keyFlagMap;

	public ProcessRouteOrderManager() {
		super.seConfigureProxy = new ProcessRouteOrderConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new ProcessRouteOrderDAO();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(processRouteOrderDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(processRouteOrderConfigureProxy);
	}

	public Map<Integer, String> initStatusMap()
			throws ServiceEntityInstallationException {
		if (this.statusMap == null) {
			this.statusMap = serviceDropdownListHelper.getUIDropDownMap(
					ProcessRouteOrderUIModel.class, "status");
		}
		return this.statusMap;
	}

	public Map<Integer, String> initRouteTypeMap()
			throws ServiceEntityInstallationException {
		if (this.routeTypeMap == null) {
			this.routeTypeMap = serviceDropdownListHelper.getUIDropDownMap(
					ProcessRouteOrderUIModel.class, "routeType");
		}
		return this.routeTypeMap;
	}

	public Map<Integer, String> initKeyFlagMap()
			throws ServiceEntityInstallationException {
		if (this.keyFlagMap == null) {
			this.keyFlagMap = standardKeyFlagProxy.getKeyFlagMap();
		}
		return this.keyFlagMap;
	}

	/**
	 * "temporary code" Get default bill of material order by SKU
	 * 
	 * @param skuUUID
	 * @param logonUser
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ProcessRouteOrder getDefaultProcessRouteBySKU(String skuUUID,
			String client) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = getProcessRouteListBySKU(skuUUID,
				client);
		if (resultList == null || resultList.size() == 0) {
			return null;
		}
		return (ProcessRouteOrder) resultList.get(0);
	}

	public List<ServiceEntityNode> getProcessRouteListBySKU(String skuUUID,
			String client) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = getEntityNodeListByKey(skuUUID,
				"refMaterialSKUUUID", ProcessRouteOrder.NODENAME, client, null);
		return resultList;
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) super
				.newRootEntityNode(client);
		String processRouteOrderID = processRouteOrderIdHelper
				.genDefaultId(client);
		processRouteOrder.setId(processRouteOrderID);
		return processRouteOrder;
	}

	/**
	 * Core Logic to caculate a material SKU self production time by calculating
	 * each process item
	 * 
	 * @param materialStockKeepUnit
	 * @param amount
	 * @param refUnitUUID
	 * @param processRouteProcessItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public double caculateSKUProdTimeByProcess(String skuUUID, double amount,
			String refUnitUUID,
			List<ServiceEntityNode> processRouteProcessItemList)
			throws MaterialException, ServiceEntityConfigureException {
		if (ServiceCollectionsHelper.checkNullList(processRouteProcessItemList)) {
			return 0;
		}
		double comLeadTime = 0;
		for (ServiceEntityNode seNode : processRouteProcessItemList) {
			ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) seNode;
			comLeadTime = comLeadTime
					+ caculateSKUProdTimeByProcessUnion(skuUUID, amount,
							refUnitUUID, processRouteProcessItem);
		}
		return comLeadTime;
	}

	/**
	 * Core Logic to caculate material production time by each production
	 * process
	 * 
	 * @param skuUUID
	 * @param amount
	 * @param refUnitUUID
	 * @param processRouteProcessItem
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public double caculateSKUProdTimeByProcessUnion(String skuUUID,
			double amount, String refUnitUUID,
			ProcessRouteProcessItem processRouteProcessItem)
			throws MaterialException, ServiceEntityConfigureException {
		double processLeadTime = 0;
		StorageCoreUnit prodOrderStorageCoreUnit = new StorageCoreUnit();
		prodOrderStorageCoreUnit.setAmount(amount);
		prodOrderStorageCoreUnit.setRefMaterialSKUUUID(skuUUID);
		prodOrderStorageCoreUnit.setRefUnitUUID(refUnitUUID);
		StorageCoreUnit tmpProcessItemStorageUnit = new StorageCoreUnit();
		tmpProcessItemStorageUnit.setAmount(processRouteProcessItem
				.getProductionBatchSize());
		tmpProcessItemStorageUnit.setRefMaterialSKUUUID(skuUUID);
		String mainUnitUUID = materialStockKeepUnitManager
				.getMainUnitUUID(skuUUID);
		tmpProcessItemStorageUnit.setRefUnitUUID(mainUnitUUID);
		double processRatio = materialStockKeepUnitManager.getStorageUnitRatio(
				prodOrderStorageCoreUnit, tmpProcessItemStorageUnit,
				processRouteProcessItem.getClient());
		/**
		 * [Step2] The execution time for this process
		 */
		double planExcutionTime = processRouteProcessItem.getVarExecutionTime()
				* processRatio
				+ processRouteProcessItem.getFixedExecutionTime();
		processLeadTime = processLeadTime + planExcutionTime;
		/**
		 * [Step3] Calculate the move time and queue time
		 */
		// Caculate the move time if the value is set
		if (processRouteProcessItem.getVarMoveTime() > 0) {
			tmpProcessItemStorageUnit = new StorageCoreUnit();
			tmpProcessItemStorageUnit.setAmount(processRouteProcessItem
					.getMoveBatchSize());
			tmpProcessItemStorageUnit.setRefMaterialSKUUUID(skuUUID);
			tmpProcessItemStorageUnit
					.setRefUnitUUID(materialStockKeepUnitManager
							.getMainUnitUUID(skuUUID));
			double moveRatio = materialStockKeepUnitManager
					.getStorageUnitRatio(prodOrderStorageCoreUnit,
							tmpProcessItemStorageUnit,
							processRouteProcessItem.getClient());
			double planMoveTime = processRouteProcessItem.getVarMoveTime()
					* moveRatio;
			processLeadTime = processLeadTime + planMoveTime;
		}
		if (processRouteProcessItem.getQueueTime() > 0) {
			processLeadTime = processLeadTime
					+ processRouteProcessItem.getQueueTime();
		}
		return processLeadTime;
	}

	public ProcessRouteProcessItem newProcessItem(
			ProcessRouteOrder processRouteOrder, ProdProcess prodProcess)
			throws ServiceEntityConfigureException {
		ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) newEntityNode(
				processRouteOrder, ProcessRouteProcessItem.NODENAME);
		buildReferenceNode(prodProcess, processRouteProcessItem,
				ServiceEntityFieldsHelper.getCommonPackage(ProdProcess.class));
		int maxIndex = getExistedMaxProcessIndex(processRouteOrder.getUuid(),
				prodProcess.getClient());
		if (maxIndex > ProcessRouteProcessItem.START_INDEX) {
			processRouteProcessItem.setProcessIndex(maxIndex
					+ ProcessRouteProcessItem.STEP_INDEX);
		} else {
			processRouteProcessItem.setProcessIndex(maxIndex);
		}
		return processRouteProcessItem;
	}

	public int getExistedMaxProcessIndex(String baseUUID, String client)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> subProcessItemList = getEntityNodeListByKey(
				baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				ProcessRouteProcessItem.NODENAME, client, null);
		if (ServiceCollectionsHelper.checkNullList(subProcessItemList)) {
			return ProcessRouteProcessItem.START_INDEX;
		}
		int maxIndex = ProcessRouteProcessItem.START_INDEX;
		for (ServiceEntityNode seNode : subProcessItemList) {
			ProcessRouteProcessItem processItem = (ProcessRouteProcessItem) seNode;
			if (processItem.getProcessIndex() > maxIndex) {
				maxIndex = processItem.getProcessIndex();
			}
		}
		return maxIndex;
	}

	/**
	 * If possible, get the existed process item
	 * 
	 * @param baseUUID
	 * @param resourceUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ProcessRouteProcessItem getExistedProcessItem(String baseUUID,
			String resourceUUID, String client)
			throws ServiceEntityConfigureException {
		List<ServiceBasicKeyStructure> keyList = new ArrayList<ServiceBasicKeyStructure>();
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
		key1.setKeyName(IServiceEntityNodeFieldConstant.ROOTNODEUUID);
		key1.setKeyValue(baseUUID);
		keyList.add(key1);
		ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure();
		key2.setKeyName(IReferenceNodeFieldConstant.REFUUID);
		key2.setKeyValue(resourceUUID);
		keyList.add(key2);
		List<ServiceEntityNode> resultList = getEntityNodeListByKeyList(
				keyList, ProcessRouteProcessItem.NODENAME, null);
		if (ServiceCollectionsHelper.checkNullList(resultList)) {
			return null;
		} else {
			return (ProcessRouteProcessItem) resultList.get(0);
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convProcessRouteProcessItemToUI(
			ProcessRouteProcessItem processRouteProcessItem,
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel) {
		if (processRouteProcessItem != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(processRouteProcessItem.getUuid())) {
				processRouteProcessItemUIModel.setUuid(processRouteProcessItem
						.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(processRouteProcessItem
							.getParentNodeUUID())) {
				processRouteProcessItemUIModel
						.setParentNodeUUID(processRouteProcessItem
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(processRouteProcessItem.getRootNodeUUID())) {
				processRouteProcessItemUIModel
						.setRootNodeUUID(processRouteProcessItem
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(processRouteProcessItem.getClient())) {
				processRouteProcessItemUIModel
						.setClient(processRouteProcessItem.getClient());
			}
			processRouteProcessItemUIModel
					.setMoveBatchSize(processRouteProcessItem
							.getMoveBatchSize());
			processRouteProcessItemUIModel
					.setRefWorkCenterUUID(processRouteProcessItem
							.getRefWorkCenterUUID());
			processRouteProcessItemUIModel
					.setKeyProcessFlag(processRouteProcessItem
							.getKeyProcessFlag());
			processRouteProcessItemUIModel
					.setProcessIndex(processRouteProcessItem.getProcessIndex());
			processRouteProcessItemUIModel.setQueueTime(processRouteProcessItem
					.getQueueTime());
			processRouteProcessItemUIModel.setStatus(processRouteProcessItem
					.getStatus());
			processRouteProcessItemUIModel
					.setPrepareTime(processRouteProcessItem.getPrepareTime());
			processRouteProcessItemUIModel
					.setVarExecutionTime(processRouteProcessItem
							.getVarExecutionTime());
			processRouteProcessItemUIModel
					.setProductionBatchSize(processRouteProcessItem
							.getProductionBatchSize());
			processRouteProcessItemUIModel
					.setFixedMoveTime(processRouteProcessItem
							.getFixedMoveTime());
			processRouteProcessItemUIModel
					.setVarMoveTime(processRouteProcessItem.getVarMoveTime());
			processRouteProcessItemUIModel.setRefUUID(processRouteProcessItem
					.getRefUUID());
		}
	}

	/**
	 * [Internal method] Convert from UI model to SE
	 * model:processRouteProcessItem
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToProcessRouteProcessItem(
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel,
			ProcessRouteProcessItem rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(processRouteProcessItemUIModel.getUuid())) {
			rawEntity.setUuid(processRouteProcessItemUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(processRouteProcessItemUIModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(processRouteProcessItemUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(processRouteProcessItemUIModel
						.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(processRouteProcessItemUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(processRouteProcessItemUIModel.getClient())) {
			rawEntity.setClient(processRouteProcessItemUIModel.getClient());
		}
		rawEntity.setMoveBatchSize(processRouteProcessItemUIModel
				.getMoveBatchSize());
		rawEntity.setRefWorkCenterUUID(processRouteProcessItemUIModel
				.getRefWorkCenterUUID());
		rawEntity.setStatus(processRouteProcessItemUIModel.getStatus());
		rawEntity.setKeyProcessFlag(processRouteProcessItemUIModel
				.getKeyProcessFlag());
		rawEntity.setProcessIndex(processRouteProcessItemUIModel
				.getProcessIndex());
		rawEntity.setQueueTime(processRouteProcessItemUIModel.getQueueTime());
		rawEntity.setPrepareTime(processRouteProcessItemUIModel
				.getPrepareTime());
		rawEntity.setVarExecutionTime(processRouteProcessItemUIModel
				.getVarExecutionTime());
		rawEntity.setProductionBatchSize(processRouteProcessItemUIModel
				.getProductionBatchSize());
		rawEntity.setFixedMoveTime(processRouteProcessItemUIModel
				.getFixedMoveTime());
		rawEntity.setVarMoveTime(processRouteProcessItemUIModel
				.getVarMoveTime());
		rawEntity.setRefUUID(processRouteProcessItemUIModel.getRefUUID());
	}

	public void convProcessRouteOrderToUI(ProcessRouteOrder processRouteOrder,
			ProcessRouteOrderUIModel processRouteOrderUIModel)
			throws ServiceEntityInstallationException {
		if (processRouteOrder != null) {
			if (!ServiceEntityStringHelper.checkNullString(processRouteOrder
					.getUuid())) {
				processRouteOrderUIModel.setUuid(processRouteOrder.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(processRouteOrder
					.getParentNodeUUID())) {
				processRouteOrderUIModel.setParentNodeUUID(processRouteOrder
						.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(processRouteOrder
					.getRootNodeUUID())) {
				processRouteOrderUIModel.setRootNodeUUID(processRouteOrder
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(processRouteOrder
					.getClient())) {
				processRouteOrderUIModel.setClient(processRouteOrder
						.getClient());
			}
			processRouteOrderUIModel.setId(processRouteOrder.getId());
			processRouteOrderUIModel.setName(processRouteOrder.getName());
			processRouteOrderUIModel.setNote(processRouteOrder.getNote());
			processRouteOrderUIModel.setKeyRouteFlag(processRouteOrder
					.getKeyRouteFlag());
			initRouteTypeMap();
			processRouteOrderUIModel.setRouteTypeValue(this.routeTypeMap
					.get(processRouteOrder.getRouteType()));
			processRouteOrderUIModel.setRouteType(processRouteOrder
					.getRouteType());
			initStatusMap();
			processRouteOrderUIModel.setStatusValue(this.statusMap
					.get(processRouteOrder.getStatus()));
			processRouteOrderUIModel.setStatus(processRouteOrder.getStatus());
			initKeyFlagMap();
			processRouteOrderUIModel.setKeyRouteValue(this.keyFlagMap
					.get(processRouteOrder.getKeyRouteFlag()));
			processRouteOrderUIModel
					.setRefParentProcessRouteUUID(processRouteOrder
							.getRefParentProcessRouteUUID());
			processRouteOrderUIModel
					.setRefTemplateProcessRouteUUID(processRouteOrder
							.getRefTemplateProcessRouteUUID());
			processRouteOrderUIModel.setRefMaterialSKUUUID(processRouteOrder
					.getRefMaterialSKUUUID());
			processRouteOrderUIModel.setRefUnitUUID(processRouteOrder
					.getRefUnitUUID());
			processRouteOrderUIModel.setRefUnitName(processRouteOrder
					.getRefUnitUUID());
		}
	}

	public void convMaterialSKUToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			ProcessRouteOrderUIModel processRouteOrderUIModel)
			throws ServiceEntityInstallationException {
		if (materialStockKeepUnit != null) {
			processRouteOrderUIModel.setRefMaterialSKUId(materialStockKeepUnit
					.getId());
			processRouteOrderUIModel
					.setRefMaterialSKUName(materialStockKeepUnit.getName());
		}
	}

	public void convUIToProcessRouteOrder(
			ProcessRouteOrderUIModel processRouteOrderUIModel,
			ProcessRouteOrder rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(processRouteOrderUIModel
				.getUuid())) {
			rawEntity.setUuid(processRouteOrderUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(processRouteOrderUIModel
				.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(processRouteOrderUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(processRouteOrderUIModel
				.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(processRouteOrderUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(processRouteOrderUIModel
				.getClient())) {
			rawEntity.setClient(processRouteOrderUIModel.getClient());
		}
		rawEntity.setId(processRouteOrderUIModel.getId());
		rawEntity.setName(processRouteOrderUIModel.getName());
		rawEntity.setNote(processRouteOrderUIModel.getNote());
		rawEntity.setKeyRouteFlag(processRouteOrderUIModel.getKeyRouteFlag());
		rawEntity.setRouteType(processRouteOrderUIModel.getRouteType());
		rawEntity.setRefParentProcessRouteUUID(processRouteOrderUIModel
				.getRefParentProcessRouteUUID());
		rawEntity.setRefTemplateProcessRouteUUID(processRouteOrderUIModel
				.getRefTemplateProcessRouteUUID());
		rawEntity.setRefMaterialSKUUUID(processRouteOrderUIModel
				.getRefMaterialSKUUUID());
		rawEntity.setRefUnitUUID(processRouteOrderUIModel.getRefUnitUUID());
		rawEntity.setRefUnitUUID(processRouteOrderUIModel.getRefUnitName());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convProdWorkCenterToUI(ProdWorkCenter prodWorkCenter,
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel) {
		if (prodWorkCenter != null) {
			processRouteProcessItemUIModel.setRefWorkCenterId(prodWorkCenter
					.getId());
			processRouteProcessItemUIModel.setRefWorkCenterName(prodWorkCenter
					.getName());

		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			ProcessRouteOrderUIModel processRouteOrderUIModel) {
		if (materialStockKeepUnit != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(materialStockKeepUnit.getClient())) {
				processRouteOrderUIModel.setClient(materialStockKeepUnit
						.getClient());
			}
			processRouteOrderUIModel
					.setRefMaterialSKUName(materialStockKeepUnit.getName());
			processRouteOrderUIModel.setRefMaterialSKUId(materialStockKeepUnit
					.getId());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convProdProcessToUI(ProdProcess prodProcess,
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel) {
		if (prodProcess != null) {
			if (!ServiceEntityStringHelper.checkNullString(prodProcess
					.getClient())) {
				processRouteProcessItemUIModel.setClient(prodProcess
						.getClient());
			}
			processRouteProcessItemUIModel.setProdProcessName(prodProcess
					.getName());
			processRouteProcessItemUIModel.setKeyProcessFlag(prodProcess
					.getKeyProcessFlag());
			processRouteProcessItemUIModel
					.setProdProcessId(prodProcess.getId());
		}
	}

	public void convParentDocToProcessItemUI(
			ProcessRouteOrder processRouteOrder,
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel) {
		if (processRouteOrder != null) {			
			processRouteProcessItemUIModel.setDocumentId(processRouteOrder.getId());
		}
	}

	public List<ServiceEntityNode> searchInternal(
			ProcessRouteOrderSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[processRouteProcessItem]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(ProcessRouteProcessItem.SENAME);
		searchNodeConfig0.setNodeName(ProcessRouteProcessItem.NODENAME);
		searchNodeConfig0.setNodeInstID(ProcessRouteProcessItem.NODENAME);
		searchNodeConfig0.setStartNodeFlag(false);
		searchNodeConfig0
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig0.setBaseNodeInstID(ProcessRouteOrder.SENAME);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[processRouteOrder]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(ProcessRouteOrder.SENAME);
		searchNodeConfig2.setNodeName(ProcessRouteOrder.NODENAME);
		searchNodeConfig2.setNodeInstID(ProcessRouteOrder.SENAME);
		searchNodeConfig2.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig2);
		// Search node:[prodWorkCenter]
		BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
		searchNodeConfig4.setSeName(ProdWorkCenter.SENAME);
		searchNodeConfig4.setNodeName(ProdWorkCenter.NODENAME);
		searchNodeConfig4.setNodeInstID(ProdWorkCenter.SENAME);
		searchNodeConfig4.setStartNodeFlag(false);
		searchNodeConfig4
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig4.setMapBaseFieldName("refWorkCenterUUID");
		searchNodeConfig4.setMapSourceFieldName("uuid");
		searchNodeConfig4.setBaseNodeInstID(ProcessRouteProcessItem.NODENAME);
		searchNodeConfigList.add(searchNodeConfig4);
		// Search node:[materialStockKeepUnit]
		BSearchNodeComConfigure searchNodeConfig6 = new BSearchNodeComConfigure();
		searchNodeConfig6.setSeName(MaterialStockKeepUnit.SENAME);
		searchNodeConfig6.setNodeName(MaterialStockKeepUnit.NODENAME);
		searchNodeConfig6.setNodeInstID(MaterialStockKeepUnit.SENAME);
		searchNodeConfig6.setStartNodeFlag(false);
		searchNodeConfig6
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig6.setMapBaseFieldName("refMaterialSKUUUID");
		searchNodeConfig6.setMapSourceFieldName("uuid");
		searchNodeConfig6.setBaseNodeInstID(ProcessRouteOrder.SENAME);
		searchNodeConfigList.add(searchNodeConfig6);
		// Search node:[prodProcess]
		BSearchNodeComConfigure searchNodeConfig8 = new BSearchNodeComConfigure();
		searchNodeConfig8.setSeName(ProdProcess.SENAME);
		searchNodeConfig8.setNodeName(ProdProcess.NODENAME);
		searchNodeConfig8.setNodeInstID(ProdProcess.SENAME);
		searchNodeConfig8.setStartNodeFlag(false);
		searchNodeConfig8
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE);
		searchNodeConfig8.setBaseNodeInstID(ProcessRouteProcessItem.NODENAME);
		searchNodeConfigList.add(searchNodeConfig8);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

}
