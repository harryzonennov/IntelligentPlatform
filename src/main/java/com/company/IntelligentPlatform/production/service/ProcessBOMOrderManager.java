package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.production.dto.ProcessBOMOrderUIModel;
// TODO-DAO: import net.thorstein.production.DAO.ProcessBOMOrderDAO;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.ProcessBOMItem;
import com.company.IntelligentPlatform.production.model.ProcessBOMOrder;
import com.company.IntelligentPlatform.production.model.ProcessBOMOrderConfigureProxy;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;

/**
 * Logic Manager CLASS FOR Service Entity [ProcessBOMOrder]
 *
 * @author
 * @date Sun Apr 03 21:09:24 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class ProcessBOMOrderManager extends ServiceEntityManager {

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected ProcessBOMOrderDAO processBOMOrderDAO;

	@Autowired
	protected ProcessBOMOrderConfigureProxy processBOMOrderConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected ProcessBOMOrderIdHelper processBOMOrderIdHelper;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;

	public ProcessBOMOrderManager() {
		super.seConfigureProxy = new ProcessBOMOrderConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new ProcessBOMOrderDAO();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(processBOMOrderDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(processBOMOrderConfigureProxy);
	}

	/**
	 * Confirm to create ProcessBOMOrder model as well as sub Item from
	 * necessary data
	 * 
	 * @param refMaterialSKUUUID
	 * @param refBOMUUID
	 * @param refProcessRouteUUID
	 * @param logonUser
	 * @param organizationUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ProcessBOMOrderException
	 */
	public ProcessBOMOrder confirmToCreateModel(String refMaterialSKUUUID,
			String refBOMUUID, String refProcessRouteUUID,
			String logonUserUUID, String organizationUUID, String client)
			throws ServiceEntityConfigureException, ProcessBOMOrderException {
		ProcessBOMOrder processBOMOrder = (ProcessBOMOrder) newRootEntityNode(client);
		/**
		 * [Step1] check the data for prepartion
		 */
		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(refMaterialSKUUUID,
						IServiceEntityNodeFieldConstant.UUID,
						MaterialStockKeepUnit.NODENAME, client, null);
		if (materialStockKeepUnit == null) {
			throw new ProcessBOMOrderException(
					ProcessBOMOrderException.TYPE_CREATE_NO_MATERIALSKU);
		}
		BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
				.getEntityNodeByKey(refBOMUUID,
						IServiceEntityNodeFieldConstant.UUID,
						BillOfMaterialOrder.NODENAME, client, null);
		if (billOfMaterialOrder == null) {
			throw new ProcessBOMOrderException(
					ProcessBOMOrderException.TYPE_CREATE_NO_BILLOFMATERIAL);
		}
		ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) processRouteOrderManager
				.getEntityNodeByKey(refProcessRouteUUID,
						IServiceEntityNodeFieldConstant.UUID,
						ProcessRouteOrder.NODENAME, client, null);
		if (processRouteOrder == null) {
			throw new ProcessBOMOrderException(
					ProcessBOMOrderException.TYPE_CREATE_NO_PROCESSROUTE);
		}
		processBOMOrder.setRefBOMUUID(refBOMUUID);
		processBOMOrder.setRefMaterialSKUUUID(refMaterialSKUUUID);
		processBOMOrder.setRefProcessRouteUUID(refProcessRouteUUID);
		/**
		 * [Step2] generate the sub ProcessBOMItem from
		 */
		List<ServiceEntityNode> processRouteProcessItemList = processRouteOrderManager
				.getEntityNodeListByKey(refProcessRouteUUID,
						IServiceEntityNodeFieldConstant.PARENTNODEUUID,
						ProcessRouteProcessItem.NODENAME, client, null);
		List<ServiceEntityNode> subProcessBOMItemList = new ArrayList<ServiceEntityNode>();
		if (processRouteProcessItemList != null) {
			for (ServiceEntityNode seNode : processRouteProcessItemList) {
				ProcessRouteProcessItem processItem = (ProcessRouteProcessItem) seNode;
				ProcessBOMItem processBOMItem = newProcessBOMItem(
						processBOMOrder, processItem);
				subProcessBOMItemList.add(processBOMItem);
			}
		}
		/**
		 * [Step3] update into persistence
		 */
		insertSENode(processBOMOrder, logonUserUUID, organizationUUID);
		if (!ServiceCollectionsHelper.checkNullList(subProcessBOMItemList)) {
			insertSENodeList(subProcessBOMItemList, logonUserUUID,
					organizationUUID);
		}

		return processBOMOrder;

	}

	/**
	 * Return new instance of ProcessBOMItem by ProcessRouteProcessItem instance
	 * and processBOMOrder
	 * 
	 * @param processBOMOrder
	 * @param processItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ProcessBOMItem newProcessBOMItem(ProcessBOMOrder processBOMOrder,
			ProcessRouteProcessItem processItem)
			throws ServiceEntityConfigureException {
		ProcessBOMItem processBOMItem = (ProcessBOMItem) newEntityNode(
				processBOMOrder, ProcessBOMItem.NODENAME);
		processBOMItem.setRefProssRouteProcessItemUUID(processItem.getUuid());
		return processBOMItem;
	}

	public void convProcessBOMOrderToUI(ProcessBOMOrder processBOMOrder,
			ProcessBOMOrderUIModel processBOMOrderUIModel) {
		if (processBOMOrder != null) {
			processBOMOrderUIModel.setUuid(processBOMOrder.getUuid());
			processBOMOrderUIModel.setId(processBOMOrder.getId());
			processBOMOrderUIModel.setName(processBOMOrder.getName());
			processBOMOrderUIModel.setNote(processBOMOrder.getNote());
			processBOMOrderUIModel.setRefMaterialSKUUUID(processBOMOrder
					.getRefMaterialSKUUUID());
			processBOMOrderUIModel.setAmount(processBOMOrder.getAmount());
			processBOMOrderUIModel.setRefUnitUUID(processBOMOrder
					.getRefUnitUUID());
			processBOMOrderUIModel.setRefBOMUUID(processBOMOrder
					.getRefBOMUUID());
			processBOMOrderUIModel.setRefProcessRouteUUID(processBOMOrder
					.getRefProcessRouteUUID());
		}
	}

	public void convUIToProcessBOMOrder(ProcessBOMOrder rawEntity,
			ProcessBOMOrderUIModel processBOMOrderUIModel) {
		rawEntity.setUuid(processBOMOrderUIModel.getUuid());
		rawEntity.setId(processBOMOrderUIModel.getId());
		rawEntity.setName(processBOMOrderUIModel.getName());
		rawEntity.setNote(processBOMOrderUIModel.getNote());
		rawEntity.setRefMaterialSKUUUID(processBOMOrderUIModel
				.getRefMaterialSKUUUID());
		rawEntity.setAmount(processBOMOrderUIModel.getAmount());
		rawEntity.setRefUnitUUID(processBOMOrderUIModel.getRefUnitUUID());
		rawEntity.setRefBOMUUID(processBOMOrderUIModel.getRefBOMUUID());
		rawEntity.setRefProcessRouteUUID(processBOMOrderUIModel
				.getRefProcessRouteUUID());
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		ProcessBOMOrder processBOMOrder = (ProcessBOMOrder) super
				.newRootEntityNode(client);
		String processBOMOrderID = processBOMOrderIdHelper.genDefaultId(client);
		processBOMOrder.setId(processBOMOrderID);
		return processBOMOrder;
	}

	public void convUIToBillOfMaterialOrder(BillOfMaterialOrder rawEntity,
			ProcessBOMOrderUIModel processBOMOrderUIModel) {
	}

	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			ProcessBOMOrderUIModel processBOMOrderUIModel) {
		if (materialStockKeepUnit != null) {
			processBOMOrderUIModel.setRefMaterialSKUID(materialStockKeepUnit
					.getId());
			processBOMOrderUIModel.setRefMaterialSKUName(materialStockKeepUnit
					.getName());
		}
	}

	public void convUIToMaterialStockKeepUnit(MaterialStockKeepUnit rawEntity,
			ProcessBOMOrderUIModel processBOMOrderUIModel) {
		rawEntity.setId(processBOMOrderUIModel.getRefMaterialSKUID());
		rawEntity.setName(processBOMOrderUIModel.getRefMaterialSKUName());
	}

	public void convProcessRouteOrderToUI(ProcessRouteOrder processRouteOrder,
			ProcessBOMOrderUIModel processBOMOrderUIModel) {
		if (processRouteOrder != null) {
			processBOMOrderUIModel.setRefProcessRouteID(processRouteOrder
					.getId());
			processBOMOrderUIModel.setRouteType(processRouteOrder
					.getRouteType());
			processBOMOrderUIModel
					.setRouteStatus(processRouteOrder.getStatus());
		}
	}

	public void convBillOfMaterialToUI(BillOfMaterialOrder billOfMaterialOrder,
			ProcessBOMOrderUIModel processBOMOrderUIModel) {
		if (billOfMaterialOrder != null) {
			processBOMOrderUIModel
					.setRefBOMOrderID(billOfMaterialOrder.getId());
		}
	}

	public void convUIToProcessRouteOrder(ProcessRouteOrder rawEntity,
			ProcessBOMOrderUIModel processBOMOrderUIModel) {
		rawEntity.setId(processBOMOrderUIModel.getRefProcessRouteID());
		rawEntity.setRouteType(processBOMOrderUIModel.getRouteType());
		rawEntity.setStatus(processBOMOrderUIModel.getRouteStatus());
	}

}
