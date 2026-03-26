package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.production.dto.ProdProcessSearchModel;
import com.company.IntelligentPlatform.production.dto.ProdProcessUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.production.repository.ProdProcessRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProdProcessConfigureProxy;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;

/**
 * Logic Manager CLASS FOR Service Entity [ProdProcess]
 *
 * @author
 * @date Thu Mar 31 11:46:04 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class ProdProcessManager extends ServiceEntityManager {

	public static final String METHOD_ConvProdProcessToUI = "convProdProcessToUI";

	public static final String METHOD_ConvUIToProdProcess = "convUIToProdProcess";

	public static final String METHOD_ConvProdWorkCenterToUI = "convProdWorkCenterToUI";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected ProdProcessRepository prodProcessDAO;
	@Autowired
	protected ProdProcessConfigureProxy prodProcessConfigureProxy;

	@Autowired
	protected ProdProcessIdHelper prodProcessIdHelper;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;
	
	@Autowired
	protected BsearchService bsearchService;

	protected Map<Integer, String> statusMap;

	protected Map<Integer, String> keyProcessMap;

	public Map<Integer, String> initStatusMap()
			throws ServiceEntityInstallationException {
		if (this.statusMap == null) {
			this.statusMap = serviceDropdownListHelper.getUIDropDownMap(
					ProdProcessUIModel.class, "status");
		}
		return this.statusMap;
	}

	public Map<Integer, String> initKeyProcessMap()
			throws ServiceEntityInstallationException {
		if (this.keyProcessMap == null) {
			this.keyProcessMap = standardKeyFlagProxy.getKeyFlagMap();
		}
		return this.keyProcessMap;
	}

	public ProdProcessManager() {
		super.seConfigureProxy = new ProdProcessConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, prodProcessDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(prodProcessConfigureProxy);
	}

	public void convProdProcessToUI(ProdProcess prodProcess,
			ProdProcessUIModel prodProcessUIModel)
			throws ServiceEntityInstallationException {
		if (prodProcess != null) {
			if (!ServiceEntityStringHelper.checkNullString(prodProcess
					.getUuid())) {
				prodProcessUIModel.setUuid(prodProcess.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(prodProcess
					.getParentNodeUUID())) {
				prodProcessUIModel.setParentNodeUUID(prodProcess
						.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(prodProcess
					.getRootNodeUUID())) {
				prodProcessUIModel.setRootNodeUUID(prodProcess
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(prodProcess
					.getClient())) {
				prodProcessUIModel.setClient(prodProcess.getClient());
			}
			prodProcessUIModel.setId(prodProcess.getId());
			prodProcessUIModel.setName(prodProcess.getName());
			prodProcessUIModel.setNote(prodProcess.getNote());
			prodProcessUIModel.setKeyProcessFlag(prodProcess
					.getKeyProcessFlag());
			this.initStatusMap();
			prodProcessUIModel.setStatusValue(this.statusMap.get(prodProcess
					.getStatus()));
			this.initKeyProcessMap();
			prodProcessUIModel.setKeyProcessValue(this.keyProcessMap
					.get(prodProcess.getKeyProcessFlag()));
			prodProcessUIModel.setStatus(prodProcess.getStatus());
			prodProcessUIModel.setRefWorkCenterUUID(prodProcess
					.getRefWorkCenterUUID());
			prodProcessUIModel.setProductionBatchSize(prodProcess
					.getProductionBatchSize());
			prodProcessUIModel.setMoveBatchSize(prodProcess.getMoveBatchSize());
			prodProcessUIModel.setVarExecutionTime(prodProcess
					.getVarExecutionTime());
			prodProcessUIModel.setFixedExecutionTime(prodProcess
					.getFixedExecutionTime());
			prodProcessUIModel.setPrepareTime(prodProcess.getPrepareTime());
			prodProcessUIModel.setQueueTime(prodProcess.getQueueTime());
			prodProcessUIModel.setFixedMoveTime(prodProcess.getFixedMoveTime());
			prodProcessUIModel.setVarMoveTime(prodProcess.getVarMoveTime());
		}
	}

	public void convUIToProdProcess(ProdProcessUIModel prodProcessUIModel,
			ProdProcess rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(prodProcessUIModel
				.getUuid())) {
			rawEntity.setUuid(prodProcessUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodProcessUIModel
				.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(prodProcessUIModel.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodProcessUIModel
				.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(prodProcessUIModel.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodProcessUIModel
				.getClient())) {
			rawEntity.setClient(prodProcessUIModel.getClient());
		}
		rawEntity.setId(prodProcessUIModel.getId());
		rawEntity.setName(prodProcessUIModel.getName());
		rawEntity.setNote(prodProcessUIModel.getNote());
		rawEntity.setKeyProcessFlag(prodProcessUIModel.getKeyProcessFlag());
		rawEntity.setStatus(prodProcessUIModel.getStatus());
		rawEntity.setRefWorkCenterUUID(prodProcessUIModel
				.getRefWorkCenterUUID());
		rawEntity.setProductionBatchSize(prodProcessUIModel
				.getProductionBatchSize());
		rawEntity.setMoveBatchSize(prodProcessUIModel.getMoveBatchSize());
		rawEntity.setVarExecutionTime(prodProcessUIModel.getVarExecutionTime());
		rawEntity.setFixedExecutionTime(prodProcessUIModel
				.getFixedExecutionTime());
		rawEntity.setPrepareTime(prodProcessUIModel.getPrepareTime());
		rawEntity.setQueueTime(prodProcessUIModel.getQueueTime());
		rawEntity.setFixedMoveTime(prodProcessUIModel.getFixedMoveTime());
		rawEntity.setVarMoveTime(prodProcessUIModel.getVarMoveTime());
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		ProdProcess prodProcess = (ProdProcess) super.newRootEntityNode(client);
		String prodProcessID = prodProcessIdHelper.genDefaultId(client);
		prodProcess.setId(prodProcessID);
		return prodProcess;
	}

	public void convProdWorkCenterToUI(ProdWorkCenter prodWorkCenter,
			ProdProcessUIModel prodProcessUIModel) {
		if (prodWorkCenter != null) {
			prodProcessUIModel.setRefWorkCenterId(prodWorkCenter.getId());
			prodProcessUIModel.setRefWorkCenterName(prodWorkCenter.getName());
		}
	}
	
	public List<ServiceEntityNode> searchInternal(
			ProdProcessSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[prodProcess]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(ProdProcess.SENAME);
		searchNodeConfig0.setNodeName(ProdProcess.NODENAME);
		searchNodeConfig0.setNodeInstID(ProdProcess.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[organization]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(ProdWorkCenter.SENAME);
		searchNodeConfig1.setNodeName(ProdWorkCenter.NODENAME);
		searchNodeConfig1.setNodeInstID(ProdWorkCenter.SENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig1.setMapBaseFieldName("refWorkCenterUUID");
		searchNodeConfig1.setMapSourceFieldName("uuid");
		searchNodeConfig1.setBaseNodeInstID(ProdProcess.SENAME);
		searchNodeConfigList.add(searchNodeConfig1);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

}
