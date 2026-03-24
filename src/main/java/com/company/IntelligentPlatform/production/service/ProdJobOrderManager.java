package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.production.dto.ProdJobMaterialItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProdJobOrderUIModel;
import com.company.IntelligentPlatform.production.repository.ProdJobOrderRepository;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialSKUUnitReference;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdJobMaterialItem;
import com.company.IntelligentPlatform.production.model.ProdJobOrder;
import com.company.IntelligentPlatform.production.model.ProdJobOrderConfigureProxy;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.production.model.ProductionOrder;

/**
 * Logic Manager CLASS FOR Service Entity [ProdJobOrder]
 *
 * @author
 * @date Mon Apr 11 10:53:41 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class ProdJobOrderManager extends ServiceEntityManager {

	@Autowired
	protected ProdJobOrderRepository prodJobOrderDAO;

	@Autowired
	protected ProdJobOrderConfigureProxy prodJobOrderConfigureProxy;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;

	@Autowired
	protected ProdProcessManager prodProcessManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;
	
	@Autowired
	protected ProdJobOrderIdHelper prodJobOrderIdHelper;

	public ProdJobOrderManager() {
		super.seConfigureProxy = new ProdJobOrderConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new ProdJobOrderDAO();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(prodJobOrderDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(prodJobOrderConfigureProxy);
	}
	
	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		ProdJobOrder prodJobOrder = (ProdJobOrder) super.newRootEntityNode(client);
		String prodJobOrderID = prodJobOrderIdHelper.genDefaultId(client);
		prodJobOrder.setId(prodJobOrderID);
		return prodJobOrder;
	}

	/**
	 * Core method to generate the prod job order array (including the order
	 * head as well as material item)
	 * 
	 * @param productionOrder
	 * @param firstBOMItemList
	 * @param ratio
	 * @param processRouteOrder
	 * @param processRouteProcessItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public List<ServiceEntityNode> generateProdJobOrderArray(
			ProductionOrder productionOrder,
			List<ServiceEntityNode> firstBOMItemList, double ratio,
			ProcessRouteOrder processRouteOrder,
			List<ServiceEntityNode> processRouteProcessItemList)
			throws ServiceEntityConfigureException, MaterialException {
		List<ServiceEntityNode> prodJobOrderArray = new ArrayList<ServiceEntityNode>();
		if (!ServiceCollectionsHelper
				.checkNullList(processRouteProcessItemList)) {
			for (ServiceEntityNode seNode : processRouteProcessItemList) {
				ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) seNode;
				ProdJobOrder prodJobOrder = (ProdJobOrder) newRootEntityNode(productionOrder
						.getClient());
				initSetProdJobOrder(prodJobOrder, productionOrder,
						processRouteOrder, processRouteProcessItem);
				prodJobOrderArray.add(prodJobOrder);
				List<ServiceEntityNode> subProdJobItemList = generateProdJobOrderMaterialItemList(
						processRouteProcessItem, prodJobOrder, ratio,
						firstBOMItemList);
				if (!ServiceCollectionsHelper.checkNullList(subProdJobItemList)) {
					prodJobOrderArray.addAll(subProdJobItemList);
				}
			}
		}
		return prodJobOrderArray;
	}

	/**
	 * Filter out the prodorder list from raw prod order/material item array
	 * online
	 * 
	 * @param rawProdJobOrderArray
	 * @return
	 */
	public List<ServiceEntityNode> filterOutProdOrderListOnline(
			List<ServiceEntityNode> rawProdJobOrderArray) {
		List<ServiceEntityNode> result = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawProdJobOrderArray) {
			if (ProdJobOrder.SENAME.equals(seNode.getServiceEntityName())
					&& ProdJobOrder.NODENAME.equals(seNode.getNodeName())) {
				result.add(seNode);
			}
		}
		return result;
	}

	/**
	 * Filter out the prodorder UIModel list from raw prod order/material item
	 * array online
	 * 
	 * @param rawProdJobOrderArray
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ProdJobOrderUIModel> filterOutProdOrderUIModelListOnline(
			List<ServiceEntityNode> rawProdJobOrderArray)
			throws ServiceEntityConfigureException {
		List<ProdJobOrderUIModel> result = new ArrayList<ProdJobOrderUIModel>();
		for (ServiceEntityNode seNode : rawProdJobOrderArray) {
			if (ProdJobOrder.SENAME.equals(seNode.getServiceEntityName())
					&& ProdJobOrder.NODENAME.equals(seNode.getNodeName())) {
				ProdJobOrder prodJobOrder = (ProdJobOrder) seNode;
				ProdJobOrderUIModel prodJobOrderUIModel = new ProdJobOrderUIModel();
				convProdJobOrderToComUI(prodJobOrder, prodJobOrderUIModel);
				result.add(prodJobOrderUIModel);
			}
		}
		return result;
	}

	/**
	 * Filter out the prodorder material item list from raw prod order/material
	 * item online array and baseUUID
	 * 
	 * @param rawProdJobOrderArray
	 * @param baseUUID
	 *            :prod job order head uuid
	 * @return
	 */
	public List<ServiceEntityNode> filterOutJobMaterialItemListOnline(
			List<ServiceEntityNode> rawProdJobOrderArray, String baseUUID) {
		if (ServiceEntityStringHelper.checkNullString(baseUUID)) {
			return null;
		}
		List<ServiceEntityNode> result = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawProdJobOrderArray) {
			if (baseUUID.equals(seNode.getParentNodeUUID())
					&& ProdJobMaterialItem.NODENAME
							.equals(seNode.getNodeName())) {
				result.add(seNode);
			}
		}
		return result;
	}

	/**
	 * Filter out the prodorder material item UIModel list from raw prod
	 * order/material item online array and baseUUID
	 * 
	 * @param rawProdJobOrderArray
	 * @param baseUUID
	 *            :prod job order head uuid
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ProdJobMaterialItemUIModel> filterOutJobMaterialItemUIModelListOnline(
			List<ServiceEntityNode> rawProdJobOrderArray, String baseUUID)
			throws ServiceEntityConfigureException {
		if (ServiceEntityStringHelper.checkNullString(baseUUID)) {
			return null;
		}
		List<ProdJobMaterialItemUIModel> result = new ArrayList<ProdJobMaterialItemUIModel>();
		for (ServiceEntityNode seNode : rawProdJobOrderArray) {
			if (baseUUID.equals(seNode.getParentNodeUUID())
					&& ProdJobMaterialItem.NODENAME
							.equals(seNode.getNodeName())) {
				ProdJobMaterialItem prodJobMaterialItem = (ProdJobMaterialItem) seNode;
				ProdJobMaterialItemUIModel prodJobMaterialItemUIModel = new ProdJobMaterialItemUIModel();
				convProdJobMaterialItemToComUI(prodJobMaterialItem,
						prodJobMaterialItemUIModel);
				result.add(prodJobMaterialItemUIModel);
			}
		}
		return result;
	}

	/**
	 * [Internal method] init set the production order by each process item
	 * 
	 * @param prodJobOrder
	 * @param productionOrder
	 * @param processRouteOrder
	 * @param processRouteProcessItem
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public void initSetProdJobOrder(ProdJobOrder prodJobOrder,
			ProductionOrder productionOrder,
			ProcessRouteOrder processRouteOrder,
			ProcessRouteProcessItem processRouteProcessItem)
			throws MaterialException, ServiceEntityConfigureException {
		prodJobOrder.setRefProdRouteProcessItemUUID(processRouteProcessItem
				.getUuid());
		prodJobOrder.setRefWorkCenterUUID(processRouteProcessItem
				.getRefWorkCenterUUID());
		prodJobOrder.setCategory(ProdJobOrder.CATEGORY_PROD);
		prodJobOrder.setRefProductionOrderUUID(productionOrder.getUuid());
		// Caculate the lead time for each process
		double selfLeadTime = processRouteOrderManager
				.caculateSKUProdTimeByProcessUnion(
						productionOrder.getRefMaterialSKUUUID(),
						productionOrder.getAmount(),
						productionOrder.getRefUnitUUID(),
						processRouteProcessItem);
		prodJobOrder.setPlanNeedTime(selfLeadTime);
	}

	public List<ServiceEntityNode> generateProdJobOrderMaterialItemList(
			ProcessRouteProcessItem processRouteProcessItem,
			ProdJobOrder prodJobOrder, double ratio,
			List<ServiceEntityNode> rawBOMItemList)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> prodJobMatItemList = new ArrayList<ServiceEntityNode>();
		if (!ServiceCollectionsHelper.checkNullList(rawBOMItemList)) {
			for (ServiceEntityNode seNode : rawBOMItemList) {
				BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) seNode;
				if (processRouteProcessItem.getUuid().equals(
						billOfMaterialItem.getRefRouteProcessItemUUID())) {
					ProdJobMaterialItem prodJobMaterialItem = (ProdJobMaterialItem) newEntityNode(
							prodJobOrder, ProdJobMaterialItem.NODENAME);
					initSetProdJobMaterialItem(prodJobMaterialItem,
							billOfMaterialItem, ratio);
					prodJobMatItemList.add(prodJobMaterialItem);
				}
			}
		}
		return prodJobMatItemList;
	}

	public void initSetProdJobMaterialItem(
			ProdJobMaterialItem prodJobMaterialItem,
			BillOfMaterialItem billOfMaterialItem, double ratio) {
		prodJobMaterialItem.setAmount(billOfMaterialItem.getAmount() * ratio);
		prodJobMaterialItem.setRefMaterialSKUUUID(billOfMaterialItem
				.getRefMaterialSKUUUID());
		prodJobMaterialItem.setRefUnitUUID(billOfMaterialItem.getRefUnitUUID());
	}

	/**
	 * Conv prod job material item to compound UI model
	 * 
	 * @param prodJobMaterialItem
	 * @param prodJobMaterialItemUIModel
	 * @throws ServiceEntityConfigureException
	 */
	public void convProdJobMaterialItemToComUI(
			ProdJobMaterialItem prodJobMaterialItem,
			ProdJobMaterialItemUIModel prodJobMaterialItemUIModel)
			throws ServiceEntityConfigureException {		
		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(
						prodJobMaterialItem.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
						MaterialStockKeepUnit.NODENAME, null);		
		List<ServiceEntityNode> materialSKUUnitList = materialStockKeepUnitManager
				.getEntityNodeListByKey(materialStockKeepUnit.getUuid(),
						IServiceEntityNodeFieldConstant.ROOTNODEUUID,
						MaterialSKUUnitReference.NODENAME,
						materialStockKeepUnit.getClient(), null);
		convProdJobMaterialItemToUI(prodJobMaterialItem,
				prodJobMaterialItemUIModel, materialStockKeepUnit, materialSKUUnitList);
		convMaterialStockKeepUnitToItemUI(materialStockKeepUnit,
				prodJobMaterialItemUIModel);
	}

	public void convProdJobMaterialItemToUI(
			ProdJobMaterialItem prodJobMaterialItem,
			ProdJobMaterialItemUIModel prodJobMaterialItemUIModel,
			MaterialStockKeepUnit materialStockKeepUnit,
			List<ServiceEntityNode> materialSKUUnitList) throws ServiceEntityConfigureException {
		if (prodJobMaterialItem != null) {
			prodJobMaterialItemUIModel.setUuid(prodJobMaterialItem.getUuid());
			prodJobMaterialItemUIModel
					.setRefMaterialSKUUUID(prodJobMaterialItem
							.getRefMaterialSKUUUID());
			prodJobMaterialItemUIModel.setNote(prodJobMaterialItem.getNote());
			prodJobMaterialItemUIModel.setAmount(prodJobMaterialItem
					.getAmount());
			Map<String, String> materialUnitMap = materialStockKeepUnitManager
					.getAllUnitMapFromSKU(materialStockKeepUnit,
							materialSKUUnitList);
			prodJobMaterialItemUIModel.setRefUnitUUID(prodJobMaterialItem.getRefUnitUUID());
			String unitLabel = materialUnitMap.get(prodJobMaterialItem.getRefUnitUUID());
			if(!ServiceEntityStringHelper.checkNullString(unitLabel) && prodJobMaterialItem.getAmount() > 0){
				prodJobMaterialItemUIModel.setAmountLabel(prodJobMaterialItem.getAmount() + unitLabel);
			}else{
				prodJobMaterialItemUIModel.setAmountLabel(prodJobMaterialItem.getAmount() + " ");
			}
			

		}
	}

	public void convMaterialStockKeepUnitToItemUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			ProdJobMaterialItemUIModel prodJobMaterialItemUIModel) {
		if (materialStockKeepUnit != null) {
			prodJobMaterialItemUIModel
					.setRefMaterialSKUID(materialStockKeepUnit.getId());
			prodJobMaterialItemUIModel
					.setRefMaterialSKUName(materialStockKeepUnit.getName());
		}
	}

	public void convUIToProdJobMaterialItem(ProdJobMaterialItem rawEntity,
			ProdJobMaterialItemUIModel prodJobMaterialItemUIModel) {
		rawEntity.setUuid(prodJobMaterialItemUIModel.getUuid());
		rawEntity.setParentNodeUUID(prodJobMaterialItemUIModel
				.getParentNodeUUID());
		rawEntity.setNote(prodJobMaterialItemUIModel.getNote());
		rawEntity.setRefMaterialSKUUUID(prodJobMaterialItemUIModel
				.getRefMaterialSKUUUID());
	}

	/**
	 * Convert producton job order to compound UI model
	 * 
	 * @param prodJobOrder
	 * @param prodJobOrderUIModel
	 * @throws ServiceEntityConfigureException
	 */
	public void convProdJobOrderToComUI(ProdJobOrder prodJobOrder,
			ProdJobOrderUIModel prodJobOrderUIModel)
			throws ServiceEntityConfigureException {
		ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
				.getEntityNodeByKey(prodJobOrder.getRefProductionOrderUUID(),
						"uuid", ProductionOrder.NODENAME, null);
		convProdJobOrderToUI(prodJobOrder, prodJobOrderUIModel);
		ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) processRouteOrderManager
				.getEntityNodeByKey(
						prodJobOrder.getRefProdRouteProcessItemUUID(), "uuid",
						ProcessRouteProcessItem.NODENAME, null);
		if (processRouteProcessItem != null) {
			convProcessRouteProcessItemToUI(processRouteProcessItem, prodJobOrderUIModel);
			ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) processRouteOrderManager
					.getEntityNodeByKey(
							processRouteProcessItem.getParentNodeUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteOrder.NODENAME, null);
			convProcessRouteOrderToUI(processRouteOrder, prodJobOrderUIModel);
			ProdProcess prodProcess = (ProdProcess) prodProcessManager
					.getEntityNodeByKey(processRouteProcessItem.getRefUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProdProcess.NODENAME, null);
			convProdProcessToUI(prodProcess, prodJobOrderUIModel);
			ProdWorkCenter prodWorkCenter = (ProdWorkCenter) prodWorkCenterManager
					.getEntityNodeByKey(prodProcess.getRefWorkCenterUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProdWorkCenter.NODENAME,
							processRouteProcessItem.getClient(), null);
			convProdWorkCenterToUI(prodWorkCenter, prodJobOrderUIModel);
		}
		if (productionOrder != null) {
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
					.getEntityNodeByKey(
							productionOrder.getRefMaterialSKUUUID(), "uuid",
							MaterialStockKeepUnit.NODENAME, null);
			convMaterialStockKeepUnitToUI(materialStockKeepUnit,
					prodJobOrderUIModel);
			List<ServiceEntityNode> materialSKUUnitList = materialStockKeepUnitManager
					.getEntityNodeListByKey(materialStockKeepUnit.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							MaterialSKUUnitReference.NODENAME,
							materialStockKeepUnit.getClient(), null);
			convProductionOrderToUI(productionOrder, materialStockKeepUnit,
					materialSKUUnitList, prodJobOrderUIModel);
		}
	}

	public void convProdJobOrderToUI(ProdJobOrder prodJobOrder,
			ProdJobOrderUIModel prodJobOrderUIModel) {
		if (prodJobOrder != null) {
			prodJobOrderUIModel.setUuid(prodJobOrder.getUuid());
			prodJobOrderUIModel.setId(prodJobOrder.getId());
			prodJobOrderUIModel.setNote(prodJobOrder.getNote());
			prodJobOrderUIModel.setRefProductionOrderUUID(prodJobOrder
					.getRefProductionOrderUUID());
			prodJobOrderUIModel.setRefProdRouteProcessItemUUID(prodJobOrder
					.getRefProdRouteProcessItemUUID());
			prodJobOrderUIModel.setRefWorkCenterUUID(prodJobOrder
					.getRefWorkCenterUUID());
			prodJobOrderUIModel.setStartDate(prodJobOrder.getStartDate() != null ? DefaultDateFormatConstant.DATE_FORMAT.format(java.util.Date.from(prodJobOrder.getStartDate().atZone(java.time.ZoneId.systemDefault()).toInstant())) : null);
			prodJobOrderUIModel.setPlanStartDate(prodJobOrder.getPlanStartDate() != null ? DefaultDateFormatConstant.DATE_FORMAT.format(java.util.Date.from(prodJobOrder.getPlanStartDate().atZone(java.time.ZoneId.systemDefault()).toInstant())) : null);
			prodJobOrderUIModel.setEndDate(prodJobOrder.getEndDate() != null ? DefaultDateFormatConstant.DATE_FORMAT.format(java.util.Date.from(prodJobOrder.getEndDate().atZone(java.time.ZoneId.systemDefault()).toInstant())) : null);
			prodJobOrderUIModel.setPlanEndDate(prodJobOrder.getPlanEndDate() != null ? DefaultDateFormatConstant.DATE_FORMAT.format(java.util.Date.from(prodJobOrder.getPlanEndDate().atZone(java.time.ZoneId.systemDefault()).toInstant())) : null);
			prodJobOrderUIModel.setPlanNeedTime(prodJobOrder.getPlanNeedTime());
			prodJobOrderUIModel.setStatus(prodJobOrder.getStatus());
		}
	}

	public void convUIToProdJobOrder(ProdJobOrder rawEntity,
			ProdJobOrderUIModel prodJobOrderUIModel) {
		rawEntity.setUuid(prodJobOrderUIModel.getUuid());
		rawEntity.setId(prodJobOrderUIModel.getId());
		rawEntity.setNote(prodJobOrderUIModel.getNote());
		rawEntity.setRefProductionOrderUUID(prodJobOrderUIModel
				.getRefProductionOrderUUID());
		rawEntity.setRefProdRouteProcessItemUUID(prodJobOrderUIModel
				.getRefProdRouteProcessItemUUID());
		rawEntity.setRefWorkCenterUUID(prodJobOrderUIModel
				.getRefWorkCenterUUID());
		try { rawEntity.setStartDate(prodJobOrderUIModel.getStartDate() != null && !prodJobOrderUIModel.getStartDate().isEmpty() ? DefaultDateFormatConstant.DATE_FORMAT.parse(prodJobOrderUIModel.getStartDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null); } catch (java.text.ParseException e) { /* skip */ }
		try { rawEntity.setPlanStartDate(prodJobOrderUIModel.getPlanStartDate() != null && !prodJobOrderUIModel.getPlanStartDate().isEmpty() ? DefaultDateFormatConstant.DATE_FORMAT.parse(prodJobOrderUIModel.getPlanStartDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null); } catch (java.text.ParseException e) { /* skip */ }
		try { rawEntity.setEndDate(prodJobOrderUIModel.getEndDate() != null && !prodJobOrderUIModel.getEndDate().isEmpty() ? DefaultDateFormatConstant.DATE_FORMAT.parse(prodJobOrderUIModel.getEndDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null); } catch (java.text.ParseException e) { /* skip */ }
		try { rawEntity.setPlanEndDate(prodJobOrderUIModel.getPlanEndDate() != null && !prodJobOrderUIModel.getPlanEndDate().isEmpty() ? DefaultDateFormatConstant.DATE_FORMAT.parse(prodJobOrderUIModel.getPlanEndDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null); } catch (java.text.ParseException e) { /* skip */ }
		rawEntity.setPlanNeedTime(prodJobOrderUIModel.getPlanNeedTime());
	}

	public void convProductionOrderToUI(ProductionOrder productionOrder,
			MaterialStockKeepUnit materialStockKeepUnit,
			List<ServiceEntityNode> materialSKUUnitList,
			ProdJobOrderUIModel prodJobOrderUIModel)
			throws ServiceEntityConfigureException {
		if (productionOrder != null) {
			prodJobOrderUIModel
					.setRefProductionOrderID(productionOrder.getId());
			prodJobOrderUIModel.setRefMainMaterialSKUUUID(productionOrder
					.getRefMaterialSKUUUID());
			prodJobOrderUIModel.setMainAmount(productionOrder.getAmount());
			Map<String, String> materialUnitMap = materialStockKeepUnitManager
					.getAllUnitMapFromSKU(materialStockKeepUnit,
							materialSKUUnitList);
			if (materialUnitMap != null && productionOrder.getAmount() > 0) {
				String unitLabel = materialUnitMap.get(productionOrder
						.getRefUnitUUID());
				prodJobOrderUIModel.setMainAmountLabel(productionOrder
						.getAmount() + unitLabel);
			}
			prodJobOrderUIModel.setMainRefUnitUUID(productionOrder
					.getRefUnitUUID());
		}
	}

	public void convProcessRouteOrderToUI(ProcessRouteOrder processRouteOrder,
			ProdJobOrderUIModel prodJobOrderUIModel) {
		if (processRouteOrder != null) {
			prodJobOrderUIModel.setRefProcessRouteOrderID(processRouteOrder
					.getId());
			prodJobOrderUIModel.setRefProcessRouteOrderName(processRouteOrder
					.getName());
		}
	}
	
	public void convProcessRouteProcessItemToUI(ProcessRouteProcessItem processRouteProcessItem,
			ProdJobOrderUIModel prodJobOrderUIModel) {
		if (processRouteProcessItem != null) {
			prodJobOrderUIModel.setProcessIndex(processRouteProcessItem.getProcessIndex());			
		}
	}

	public void convProdProcessToUI(ProdProcess prodProcess,
			ProdJobOrderUIModel prodJobOrderUIModel) {
		if (prodProcess != null) {
			prodJobOrderUIModel.setProcessID(prodProcess.getId());
			prodJobOrderUIModel.setProcessName(prodProcess.getName());			
		}
	}

	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			ProdJobOrderUIModel prodJobOrderUIModel) {
		if (materialStockKeepUnit != null) {
			prodJobOrderUIModel.setRefMainMaterialID(materialStockKeepUnit
					.getId());
			prodJobOrderUIModel.setRefMainMaterialName(materialStockKeepUnit
					.getName());

		}
	}

	public void convProdWorkCenterToUI(ProdWorkCenter prodWorkCenter,
			ProdJobOrderUIModel prodJobOrderUIModel) {
		if (prodWorkCenter != null) {
			prodJobOrderUIModel.setRefWorkCenterID(prodWorkCenter.getId());
			prodJobOrderUIModel.setRefWorkCenterName(prodWorkCenter.getName());
		}
	}

}
