package com.company.IntelligentPlatform.production.service;

import java.util.Map;

import com.company.IntelligentPlatform.production.dto.ProcessRouteProcessItemUIModel;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class ProcessRouteProcessItemManager {

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;
	
	@Autowired
	protected ProdProcessManager prodProcessManager;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;

	public void convProcessRouteProcessItemToUI(
			ProcessRouteProcessItem processRouteProcessItem,
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel)
			throws ServiceEntityInstallationException {
		if (processRouteProcessItem != null) {
			processRouteProcessItemUIModel.setUuid(processRouteProcessItem
					.getUuid());
			processRouteProcessItemUIModel.setId(processRouteProcessItem
					.getId());
			processRouteProcessItemUIModel.setName(processRouteProcessItem
					.getName());
			processRouteProcessItemUIModel.setNote(processRouteProcessItem
					.getNote());
			processRouteProcessItemUIModel
					.setKeyProcessFlag(processRouteProcessItem
							.getKeyProcessFlag());
			Map<Integer, String> keyFlagMap = standardKeyFlagProxy
					.getKeyFlagMap();
			processRouteProcessItemUIModel.setKeyProcessValue(keyFlagMap
					.get(processRouteProcessItem.getKeyProcessFlag()));
			processRouteProcessItemUIModel
					.setParentNodeUUID(processRouteProcessItem
							.getParentNodeUUID());
			processRouteProcessItemUIModel
					.setProcessIndex(processRouteProcessItem.getProcessIndex());
			processRouteProcessItemUIModel.setStatus(processRouteProcessItem
					.getStatus());
			processRouteProcessItemUIModel
					.setProductionBatchSize(processRouteProcessItem
							.getProductionBatchSize());
			processRouteProcessItemUIModel
					.setMoveBatchSize(processRouteProcessItem
							.getMoveBatchSize());
			processRouteProcessItemUIModel
					.setVarExecutionTime(processRouteProcessItem
							.getVarExecutionTime());
			processRouteProcessItemUIModel
			.setFixedExecutionTime(processRouteProcessItem
					.getFixedExecutionTime());
			processRouteProcessItemUIModel
					.setPrepareTime(processRouteProcessItem.getPrepareTime());
			processRouteProcessItemUIModel.setQueueTime(processRouteProcessItem
					.getQueueTime());
			processRouteProcessItemUIModel.setVarMoveTime(processRouteProcessItem
					.getVarMoveTime());
			processRouteProcessItemUIModel.setFixedMoveTime(processRouteProcessItem
					.getFixedMoveTime());
		}
	}

	public void convUIToProcessRouteProcessItem(
			ProcessRouteProcessItem rawEntity,
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel) {
		rawEntity.setUuid(processRouteProcessItemUIModel.getUuid());
		rawEntity.setId(processRouteProcessItemUIModel.getId());
		rawEntity.setName(processRouteProcessItemUIModel.getName());
		rawEntity.setNote(processRouteProcessItemUIModel.getNote());
		rawEntity.setProcessIndex(processRouteProcessItemUIModel
				.getProcessIndex());
		rawEntity.setKeyProcessFlag(processRouteProcessItemUIModel
				.getKeyProcessFlag());
		rawEntity.setProductionBatchSize(processRouteProcessItemUIModel
				.getProductionBatchSize());
		rawEntity.setMoveBatchSize(processRouteProcessItemUIModel
				.getMoveBatchSize());
		rawEntity.setVarExecutionTime(processRouteProcessItemUIModel
				.getVarExecutionTime());
		rawEntity.setFixedExecutionTime(processRouteProcessItemUIModel
				.getFixedExecutionTime());
		rawEntity.setPrepareTime(processRouteProcessItemUIModel
				.getPrepareTime());
		rawEntity.setQueueTime(processRouteProcessItemUIModel.getQueueTime());
		rawEntity.setFixedMoveTime(processRouteProcessItemUIModel
				.getFixedMoveTime());
		rawEntity.setVarMoveTime(processRouteProcessItemUIModel
				.getVarMoveTime());	
	}

	public void convProdProcessToUI(ProdProcess prodProcess,
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel)
			throws ServiceEntityInstallationException {
		if (prodProcess != null) {			
			processRouteProcessItemUIModel
					.setProdProcessId(prodProcess.getId());
			processRouteProcessItemUIModel.setProdProcessName(prodProcess
					.getName());
			processRouteProcessItemUIModel.setId(prodProcess.getId());
			processRouteProcessItemUIModel.setName(prodProcess.getName());						
		}
	}

	public void convProdWorkCenterToUI(ProdWorkCenter prodWorkCenter,
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel)
			throws ServiceEntityInstallationException {
		if (prodWorkCenter != null) {
			processRouteProcessItemUIModel.setRefWorkCenterName(prodWorkCenter
					.getName());
			processRouteProcessItemUIModel.setRefWorkCenterUUID(prodWorkCenter
					.getUuid());
			processRouteProcessItemUIModel.setRefWorkCenterId(prodWorkCenter
					.getId());
		}
	}

	/**
	 * Conv compound prod process item to UI model
	 * 
	 * @param processRouteProcessItem
	 * @param processRouteProcessItemUIModel
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceEntityConfigureException
	 */
	public void convComProcessItemToUI(
			ProcessRouteProcessItem processRouteProcessItem,
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		if (processRouteProcessItemUIModel == null
				|| processRouteProcessItem == null) {
			return;
		}
		convProcessRouteProcessItemToUI(processRouteProcessItem,
				processRouteProcessItemUIModel);
		if (processRouteProcessItem != null) {
			ProdProcess prodProcess = (ProdProcess) prodProcessManager
					.getEntityNodeByKey(processRouteProcessItem.getRefUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProdProcess.NODENAME, null);
			convProdProcessToUI(prodProcess, processRouteProcessItemUIModel);
			// Set default work center from prod process
			ProdWorkCenter prodWorkCenter = (ProdWorkCenter) prodWorkCenterManager
					.getEntityNodeByKey(prodProcess.getRefWorkCenterUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProdWorkCenter.NODENAME, prodProcess.getClient(),
							null);
			convProdWorkCenterToUI(prodWorkCenter,
					processRouteProcessItemUIModel);
		}
	}

}
