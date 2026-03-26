package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.ProcessBOMItemUIModel;
import com.company.IntelligentPlatform.production.model.ProcessBOMItem;
import com.company.IntelligentPlatform.production.model.ProcessBOMOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class ProcessBOMItemManager {

	@Autowired
	protected ProcessBOMOrderManager processBOMOrderManager;

	/**
	 * New instance of ProcessBOMItem
	 * 
	 * @param processBOMOrder
	 * @param refProssRouteProcessItemUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ProcessBOMItem newProcessBOMItem(ProcessBOMOrder processBOMOrder,
			String refProssRouteProcessItemUUID)
			throws ServiceEntityConfigureException {
		ProcessBOMItem processBOMItem = (ProcessBOMItem) processBOMOrderManager
				.newEntityNode(processBOMOrder, ProcessBOMItem.NODENAME);
		processBOMItem
				.setRefProssRouteProcessItemUUID(refProssRouteProcessItemUUID);
		return processBOMItem;
	}

	public void convProcessBOMItemToUI(ProcessBOMItem processBOMItem,
			ProcessBOMItemUIModel processBOMItemUIModel) {
		if (processBOMItem != null) {
			processBOMItemUIModel.setUuid(processBOMItem.getUuid());
			processBOMItemUIModel.setParentNodeUUID(processBOMItem
					.getParentNodeUUID());
			processBOMItemUIModel.setId(processBOMItem.getId());
			processBOMItemUIModel.setNote(processBOMItem.getNote());
			processBOMItemUIModel
					.setRefProssRouteProcessItemUUID(processBOMItem
							.getRefProssRouteProcessItemUUID());
		}
	}

	public void convUIToProcessBOMItem(ProcessBOMItem rawEntity,
			ProcessBOMItemUIModel processBOMItemUIModel) {
		rawEntity.setUuid(processBOMItemUIModel.getUuid());
		rawEntity.setParentNodeUUID(processBOMItemUIModel.getParentNodeUUID());
		rawEntity.setId(processBOMItemUIModel.getId());
		rawEntity.setNote(processBOMItemUIModel.getNote());
		rawEntity.setRefProssRouteProcessItemUUID(processBOMItemUIModel
				.getRefProssRouteProcessItemUUID());
	}

	public void convProcessRouteProcessItemToUI(
			ProcessRouteProcessItem processRouteProcessItem,
			ProcessBOMItemUIModel processBOMItemUIModel) {
		if (processRouteProcessItem != null) {
			processBOMItemUIModel.setKeyRouteFlag(processRouteProcessItem
					.getKeyProcessFlag());
			processBOMItemUIModel.setProcessIndex(processRouteProcessItem
					.getProcessIndex());
			processBOMItemUIModel
					.setProductionBatchSize(processRouteProcessItem
							.getProductionBatchSize());
			processBOMItemUIModel.setMoveBatchSize(processRouteProcessItem
					.getMoveBatchSize());
			processBOMItemUIModel.setExecutionTime(processRouteProcessItem
					.getVarExecutionTime());
			processBOMItemUIModel.setPrepareTime(processRouteProcessItem
					.getPrepareTime());
			processBOMItemUIModel.setQueueTime(processRouteProcessItem
					.getQueueTime());
			processBOMItemUIModel.setMoveTime(processRouteProcessItem
					.getVarMoveTime());
		}
	}

	public void convProdProcessToUI(ProdProcess prodProcess,
			ProcessBOMItemUIModel processBOMItemUIModel) {
		if (prodProcess != null) {
			processBOMItemUIModel.setProdProcessId(prodProcess.getId());
			processBOMItemUIModel.setProdProcessName(prodProcess.getName());
		}
	}

	public void convProdWorkCenterToUI(ProdWorkCenter prodWorkCenter,
			ProcessBOMItemUIModel processBOMItemUIModel) {
		if (prodWorkCenter != null) {
			processBOMItemUIModel
					.setRefWorkCenterName(prodWorkCenter.getName());
			processBOMItemUIModel.setRefWorkCenterID(prodWorkCenter.getId());
		}
	}

	public void convUIToProdWorkCenter(ProdWorkCenter rawEntity,
			ProcessBOMItemUIModel processBOMItemUIModel) {
		rawEntity.setName(processBOMItemUIModel.getRefWorkCenterName());
		rawEntity.setId(processBOMItemUIModel.getRefWorkCenterID());
	}

}
