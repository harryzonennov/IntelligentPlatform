package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.production.dto.BillOfMaterialExcelModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemUIModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialOrderUIModel;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CorporateCustomerException;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.dto.ServiceExcelReportResponseModel;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;
import com.company.IntelligentPlatform.common.service.ServiceReflectiveHelper;
import com.company.IntelligentPlatform.common.model.IDocumentNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class BillOfMaterialOrderReportProxy extends ServiceExcelReportProxy {

	public static final String CONFIG_NAME = "BillOfMaterialOrder";

	@Autowired
	protected BillOfMaterialOrderServiceUIModelExtension billOfMaterialOrderServiceUIModelExtension;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BillOfMaterialItemServiceUIModelExtension billOfMaterialItemServiceUIModelExtension;

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	public void initConfig() {
		this.configureName = CONFIG_NAME;
		this.excelModelClass = BillOfMaterialExcelModel.class;
	}

	@Override
	public void insertExcelBatchData(
			ServiceExcelReportResponseModel serviceExcelReportResponseModel,
			String modelName) throws ServiceExcelConfigException,
			AuthorizationException, LogonInfoException {
		super.insertExcelBatchData(serviceExcelReportResponseModel, modelName);
	}

	/**
	 * Main entry to update excel upload content into persistence
	 *
	 * @param serviceExcelReportResponseModel
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws MaterialException
	 * @throws CorporateCustomerException
	 */
	public void updateExcelBatch(
			ServiceExcelReportResponseModel serviceExcelReportResponseModel,
			boolean copyIdFlag, boolean overwiteFlag, String logonUserUUID,
			String organizationUUID, String client, String languageCode)
			throws ServiceEntityConfigureException, BillOfMaterialException,
			ServiceEntityInstallationException {
		Map<Integer, SEUIComModel> dataMap = serviceExcelReportResponseModel
				.getDataMap();
		if (dataMap == null || dataMap.isEmpty()) {
			return;
		}
		/**
		 * [Step1] retrieve raw data list
		 */
		Set<Integer> keySet = dataMap.keySet();
		Iterator<Integer> it = keySet.iterator();
		List<BillOfMaterialExcelModel> billOfMaterialExcelModelList = new ArrayList<BillOfMaterialExcelModel>();
		while (it.hasNext()) {
			int key = it.next();
			BillOfMaterialExcelModel billOfMaterialExcelModel = (BillOfMaterialExcelModel) dataMap
					.get(key);
			ServiceEntityNode billOfMaterialEntity = getExistBOMEntityWrapper(
					billOfMaterialExcelModel, client);
			if (billOfMaterialEntity != null) {
				updateExistedBOMEntity(billOfMaterialEntity, overwiteFlag);
			} else {
				billOfMaterialExcelModelList.add(billOfMaterialExcelModel);
			}
		}
		@SuppressWarnings("unchecked")
		List<BillOfMaterialExcelModel> bomOrderExcelModelList = (List<BillOfMaterialExcelModel>) ServiceCollectionsHelper
				.filterListOnline(
						0,
						item -> {
							BillOfMaterialExcelModel billOfMaterialExcelModel = (BillOfMaterialExcelModel) item;
							return billOfMaterialExcelModel.getLayer();
						}, billOfMaterialExcelModelList, false);
		if (!ServiceCollectionsHelper.checkNullList(bomOrderExcelModelList)) {
			for (BillOfMaterialExcelModel billOfMaterialExcelModel : bomOrderExcelModelList) {
				createFromExcelModelUnion(billOfMaterialExcelModel,
						billOfMaterialExcelModelList, copyIdFlag,
						logonUserUUID, organizationUUID, client,
						languageCode);
			}
		}
	}

	/**
	 * Main Entrance to generate BOM Excel model list for Excel download
	 * 
	 * @param rawBOMOrderList
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 */
	public List<SEUIComModel> generateExcelModel(
			List<ServiceEntityNode> rawBOMOrderList, LogonInfo logonInfo)
			throws ServiceEntityConfigureException, ServiceModuleProxyException {
		List<SEUIComModel> billOfMaterialExcelModelList = new ArrayList<>();
		if (ServiceCollectionsHelper.checkNullList(rawBOMOrderList)) {
			return null;
		}
		/*
		 * Get all BOM Item List
		 */
		List<ServiceBasicKeyStructure> keyList = new ArrayList<ServiceBasicKeyStructure>();
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
		key1.setKeyName(IServiceEntityNodeFieldConstant.ROOTNODEUUID);
		List<String> multipleValueList = new ArrayList<>();
		rawBOMOrderList.forEach(rawSENode -> {
			multipleValueList.add(rawSENode.getUuid());
		});
		key1.setMultipleValueList(multipleValueList);
		keyList.add(key1);
		List<ServiceEntityNode> rawBOMItemList = billOfMaterialOrderManager
				.getEntityNodeListByKeyList(keyList,
						BillOfMaterialItem.NODENAME, null);
		for (ServiceEntityNode rawSENode : rawBOMOrderList) {
			BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) rawSENode;
			BillOfMaterialExcelModel billOfMaterialExcelModel = new BillOfMaterialExcelModel();
			BillOfMaterialOrderUIModel billOfMaterialOrderUIModel = (BillOfMaterialOrderUIModel) billOfMaterialOrderManager
					.genUIModelFromUIModelExtension(
							BillOfMaterialOrderUIModel.class,
							billOfMaterialOrderServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							billOfMaterialOrder, logonInfo, null);
			ServiceReflectiveHelper.convComModelReflect(
					billOfMaterialOrderUIModel, billOfMaterialExcelModel, null);
			billOfMaterialExcelModelList.add(billOfMaterialExcelModel);
			List<SEUIComModel> subBOMExcelModelList = genSubBomItemExcelModel(
					billOfMaterialOrder.getUuid(), rawBOMItemList, logonInfo);
			if (!ServiceCollectionsHelper.checkNullList(subBOMExcelModelList)) {
				billOfMaterialExcelModelList.addAll(subBOMExcelModelList);
			}
		}
		return billOfMaterialExcelModelList;
	}

	private List<SEUIComModel> genSubBomItemExcelModel(String parentItemUUID,
			List<ServiceEntityNode> rawBOMItemList, LogonInfo logonInfo)
			throws ServiceModuleProxyException, ServiceEntityConfigureException {
		List<SEUIComModel> billOfMaterialExcelModelList = new ArrayList<>();
		if (ServiceCollectionsHelper.checkNullList(rawBOMItemList)) {
			return null;
		}
		List<ServiceEntityNode> subBOMItemList = ServiceCollectionsHelper
				.filterSENodeListOnline(
						parentItemUUID,
						rawSENode -> {
							BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) rawSENode;
							return billOfMaterialItem.getRefParentItemUUID();
						}, rawBOMItemList, false);
		if (ServiceCollectionsHelper.checkNullList(subBOMItemList)) {
			return null;
		}
		for (ServiceEntityNode seNode : subBOMItemList) {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) seNode;
			BillOfMaterialItemUIModel billOfMaterialItemUIModel = (BillOfMaterialItemUIModel) billOfMaterialOrderManager
					.genUIModelFromUIModelExtension(
							BillOfMaterialItemUIModel.class,
							billOfMaterialItemServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							billOfMaterialItem, logonInfo, null);
			BillOfMaterialExcelModel billOfMaterialExcelModel = new BillOfMaterialExcelModel();
			ServiceReflectiveHelper.convComModelReflect(
					billOfMaterialItemUIModel, billOfMaterialExcelModel, null);
			billOfMaterialExcelModelList.add(billOfMaterialExcelModel);
			List<SEUIComModel> subBOMExcelModelList = genSubBomItemExcelModel(
					billOfMaterialItem.getUuid(), rawBOMItemList, logonInfo);
			if (!ServiceCollectionsHelper.checkNullList(subBOMExcelModelList)) {
				billOfMaterialExcelModelList.addAll(subBOMExcelModelList);
			}
		}
		return billOfMaterialExcelModelList;
	}

	@SuppressWarnings("unchecked")
	private List<ServiceEntityNode> createFromExcelModelUnion(
			BillOfMaterialExcelModel billOfMaterialExcelModel,
			List<BillOfMaterialExcelModel> rawBOMExcelModelList,
			boolean copyIdFlag, String logonUserUUID, String organizationUUID,
			String client, String languageCode)
			throws ServiceEntityConfigureException,
			ServiceEntityInstallationException, BillOfMaterialException {
		String nodeName = getNodeNameByLayer(billOfMaterialExcelModel
				.getLayer());
		// Pre-check id from excel model
		if (ServiceEntityStringHelper.checkNullString(billOfMaterialExcelModel
				.getId())) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		if (BillOfMaterialOrder.NODENAME.equals(nodeName)) {
			BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
					.newRootEntityNode(client);
			convertExcelModelToBOMOrderWrapper(billOfMaterialExcelModel,
					billOfMaterialOrder);
			// Insert into persistence firstly.
			billOfMaterialOrderManager.insertSENode(billOfMaterialOrder,
					logonUserUUID, organizationUUID);
			resultList.add(billOfMaterialOrder);
			// Navigate to children
			List<BillOfMaterialExcelModel> childBOMExcelModelList = (List<BillOfMaterialExcelModel>) ServiceCollectionsHelper
					.filterListOnline(
							billOfMaterialExcelModel.getId(),
							item -> {
								BillOfMaterialExcelModel tempBOMMaterialExcelModel = (BillOfMaterialExcelModel) item;
								return tempBOMMaterialExcelModel
										.getParentItemId();
							}, rawBOMExcelModelList, false);
			if (!ServiceCollectionsHelper.checkNullList(childBOMExcelModelList)) {
				for (BillOfMaterialExcelModel tempChildMaterialExcelModel : childBOMExcelModelList) {
					List<ServiceEntityNode> tempResultList = createFromExcelModelUnion(
							tempChildMaterialExcelModel, rawBOMExcelModelList,
							copyIdFlag, logonUserUUID, organizationUUID,
							client, languageCode);
					if (!ServiceCollectionsHelper.checkNullList(tempResultList)) {
						resultList.addAll(tempResultList);
					}
				}
			}
			return resultList;
		}
		if (BillOfMaterialItem.NODENAME.equals(nodeName)) {
			if (ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getParentItemId())) {
				// record issue parent item not exist

			}
			ServiceEntityNode parentBOMItem = getExistBOMEntityByIdWrapper(
					billOfMaterialExcelModel.getParentItemId(), client);
			if (parentBOMItem == null) {
				// Raise exception record issue parent item not exist
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_EXCEL_NOPARENT,
						billOfMaterialExcelModel.getId());

			}
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) this
					.createItemFromParent(parentBOMItem);
			convertExcelModelToBOMItemWrapper(billOfMaterialExcelModel,
					billOfMaterialItem);
			// Insert into persistence firstly.
			billOfMaterialOrderManager.insertSENode(billOfMaterialItem,
					logonUserUUID, organizationUUID);
			resultList.add(billOfMaterialItem);
			// Navigate to children
			List<BillOfMaterialExcelModel> childBOMExcelModelList = (List<BillOfMaterialExcelModel>) ServiceCollectionsHelper
					.filterListOnline(
							billOfMaterialExcelModel.getId(),
							item -> {
								BillOfMaterialExcelModel tempBOMMaterialExcelModel = (BillOfMaterialExcelModel) item;
								return tempBOMMaterialExcelModel
										.getParentItemId();
							}, rawBOMExcelModelList, false);
			if (!ServiceCollectionsHelper.checkNullList(childBOMExcelModelList)) {
				for (BillOfMaterialExcelModel tempChildMaterialExcelModel : childBOMExcelModelList) {
					List<ServiceEntityNode> tempResultList = createFromExcelModelUnion(
							tempChildMaterialExcelModel, rawBOMExcelModelList,
							copyIdFlag, logonUserUUID, organizationUUID,
							client, languageCode);
					if (!ServiceCollectionsHelper.checkNullList(tempResultList)) {
						resultList.addAll(tempResultList);
					}
				}
			}
			return resultList;
		}
		return null;
	}

	/**
	 * [Internal method] Create BOM Item from Parent wrapper logic
	 * 
	 * @param parentBOMItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws BillOfMaterialException
	 */
	private ServiceEntityNode createItemFromParent(
			ServiceEntityNode parentBOMItem)
			throws ServiceEntityConfigureException, BillOfMaterialException {
		if (BillOfMaterialOrder.NODENAME.equals(parentBOMItem.getNodeName())) {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) billOfMaterialOrderManager
					.newEntityNode(parentBOMItem, BillOfMaterialItem.NODENAME);
			billOfMaterialItem.setRefParentItemUUID(parentBOMItem.getUuid());
			return billOfMaterialItem;
		}
		if (BillOfMaterialItem.NODENAME.equals(parentBOMItem.getNodeName())) {
			BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
					.getEntityNodeByKey(parentBOMItem.getRootNodeUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							BillOfMaterialOrder.NODENAME, null);
			if (billOfMaterialOrder == null) {
				// raise exception
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_EXCEL_NOPARENT,
						parentBOMItem.getId());
			}
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) billOfMaterialOrderManager
					.newEntityNode(billOfMaterialOrder,
							BillOfMaterialItem.NODENAME);
			billOfMaterialItem.setRefParentItemUUID(parentBOMItem.getUuid());
			BillOfMaterialItem parentBillOfMaterialItem = (BillOfMaterialItem) parentBOMItem;
			billOfMaterialItem
					.setLayer(parentBillOfMaterialItem.getLayer() + 1);
			return billOfMaterialItem;
		}
		// TODO raise exception
		return null;
	}

	private void updateExistedBOMEntity(ServiceEntityNode billOfMaterialEntity,
			boolean overwiteFlag) {
		if (!overwiteFlag) {
			return;
		}
	}

	/**
	 * Wrapper to get existed BOM entity from persistence
	 * 
	 * @param billOfMaterialExcelModel
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ServiceEntityNode getExistBOMEntityWrapper(
			BillOfMaterialExcelModel billOfMaterialExcelModel, String client)
			throws ServiceEntityConfigureException {
		String nodeName = getNodeNameByLayer(billOfMaterialExcelModel
				.getLayer());
		if (BillOfMaterialOrder.NODENAME.equals(nodeName)) {
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getUuid())) {
				BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
						.getEntityNodeByKey(billOfMaterialExcelModel.getUuid(),
								IServiceEntityNodeFieldConstant.UUID, nodeName,
								client, null);
				if (billOfMaterialOrder != null) {
					return billOfMaterialOrder;
				}
			}
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getId())) {
				BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
						.getEntityNodeByKey(billOfMaterialExcelModel.getId(),
								IServiceEntityNodeFieldConstant.ID, nodeName,
								client, null, true);
				if (billOfMaterialOrder != null) {
					return billOfMaterialOrder;
				}
			}
			return null;
		}

		if (BillOfMaterialItem.NODENAME.equals(nodeName)) {
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getUuid())) {
				BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) billOfMaterialOrderManager
						.getEntityNodeByKey(billOfMaterialExcelModel.getUuid(),
								IServiceEntityNodeFieldConstant.UUID, nodeName,
								client, null);
				if (billOfMaterialItem != null) {
					return billOfMaterialItem;
				}
			}
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getId())) {
				BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) billOfMaterialOrderManager
						.getEntityNodeByKey(billOfMaterialExcelModel.getId(),
								IServiceEntityNodeFieldConstant.ID, nodeName,
								client, null);
				if (billOfMaterialItem != null) {
					return billOfMaterialItem;
				}
			}
			return null;
		}
		return null;
	}

	/**
	 * Wrapper to get existed BOM entity from persistence
	 * 
	 * @param billOfMaterialExcelModel
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	private ServiceEntityNode getExistBOMEntityByIdWrapper(String id,
			String client) throws ServiceEntityConfigureException {
		BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
				.getEntityNodeByKey(id, IServiceEntityNodeFieldConstant.ID,
						BillOfMaterialOrder.NODENAME, null, true);
		if (billOfMaterialOrder != null) {
			return billOfMaterialOrder;
		}
		BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) billOfMaterialOrderManager
				.getEntityNodeByKey(id, IServiceEntityNodeFieldConstant.ID,
						BillOfMaterialItem.NODENAME, null, true);
		if (billOfMaterialItem != null) {
			return billOfMaterialItem;
		}
		return null;
	}

	/**
	 * Core Logic to get BOM Entity Node Name by layer: if layer == 0, then
	 * return order, if layer > 0, then return item
	 * 
	 * @param layer
	 */
	public static String getNodeNameByLayer(int layer) {
		if (layer == 0 || layer < 0) {
			return BillOfMaterialOrder.NODENAME;
		} else {
			return BillOfMaterialItem.NODENAME;
		}
	}

	public void convertExcelModelToBOMOrderWrapper(
			BillOfMaterialExcelModel billOfMaterialExcelModel,
			BillOfMaterialOrder billOfMaterialOrder)
			throws ServiceEntityConfigureException, BillOfMaterialException {
		if (billOfMaterialExcelModel != null && billOfMaterialOrder != null) {
			/*
			 * [Step1] default conversion logic to BOM Order
			 */
			convertExcelModelToBOMOrder(billOfMaterialExcelModel,
					billOfMaterialOrder);
			/*
			 * [Step2] Basic checks and record error
			 */
			// [2.1] Check RefMaterial
			String refMaterialSKUId = billOfMaterialExcelModel
					.getRefMaterialSKUId();
			if (ServiceEntityStringHelper.checkNullString(refMaterialSKUId)) {
				// should raise exception and record
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_EXCEL_WRG_MATID,
						billOfMaterialExcelModel.getId());

			}
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
					.getEntityNodeByKey(refMaterialSKUId,
							IServiceEntityNodeFieldConstant.ID,
							MaterialStockKeepUnit.NODENAME,
							billOfMaterialOrder.getClient(), null, true);
			if (materialStockKeepUnit == null) {
				// should raise exception and record
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_EXCEL_WRG_MATID,
						refMaterialSKUId);
			} else {
				billOfMaterialOrder.setRefMaterialSKUUUID(materialStockKeepUnit
						.getUuid());
			}
			// [2.2] Check RefUnitName
			String refUnitName = billOfMaterialExcelModel.getRefUnitName();
			if (ServiceEntityStringHelper.checkNullString(refUnitName)) {
				// should raise exception and record
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_EXCEL_WRG_UNITNAME,
						billOfMaterialExcelModel.getId());

			}
			String refUnitUUID = materialStockKeepUnitManager
					.getRefUnitUUIDByName(materialStockKeepUnit, refUnitName);
			if (ServiceEntityStringHelper.checkNullString(refUnitUUID)) {
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_EXCEL_WRG_UNITNAME,
						refUnitName);
			} else {
				billOfMaterialOrder.setRefUnitUUID(refUnitUUID);
			}
			// Check ref work center
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getRefWocId())) {
				ProdWorkCenter prodWorkCenter = (ProdWorkCenter) prodWorkCenterManager
						.getEntityNodeByKey(
								billOfMaterialExcelModel.getRefWocId(),
								IServiceEntityNodeFieldConstant.ID,
								MaterialStockKeepUnit.NODENAME,
								billOfMaterialOrder.getClient(), null, true);
				if (prodWorkCenter != null) {
					billOfMaterialOrder.setRefWocUUID(prodWorkCenter.getUuid());
				}
			}

		}
	}

	public void convertExcelModelToBOMOrder(
			BillOfMaterialExcelModel billOfMaterialExcelModel,
			BillOfMaterialOrder billOfMaterialOrder)
			throws ServiceEntityConfigureException {
		if (billOfMaterialExcelModel != null && billOfMaterialOrder != null) {
			List<String> skipFieldList = ServiceCollectionsHelper.asList(
					IServiceEntityNodeFieldConstant.UUID,
					IServiceEntityNodeFieldConstant.PARENTNODEUUID,
					IServiceEntityNodeFieldConstant.CLIENT,
					IServiceEntityNodeFieldConstant.ROOTNODEUUID,
					IDocumentNodeFieldConstant.STATUS);
			ServiceReflectiveHelper.convComModelReflect(
					billOfMaterialExcelModel, billOfMaterialOrder,
					skipFieldList);
		}
	}

	public void convertBOMOrderToExcelModel(
			BillOfMaterialOrder billOfMaterialOrder,
			BillOfMaterialExcelModel billOfMaterialExcelModel) {
		ServiceReflectiveHelper.convComModelReflect(billOfMaterialOrder,
				billOfMaterialExcelModel, null);
	}

	public void convertBOMOrderUIModelToExcelModel(
			BillOfMaterialOrderUIModel billOfMaterialOrderUIModel,
			BillOfMaterialExcelModel billOfMaterialExcelModel) {
		ServiceReflectiveHelper.convComModelReflect(billOfMaterialOrderUIModel,
				billOfMaterialExcelModel, null);
	}

	public void convertExcelModelToBOMItemWrapper(
			BillOfMaterialExcelModel billOfMaterialExcelModel,
			BillOfMaterialItem billOfMaterialItem)
			throws BillOfMaterialException, ServiceEntityConfigureException {
		if (billOfMaterialExcelModel != null && billOfMaterialItem != null) {
			convertExcelModelToBOMItem(billOfMaterialExcelModel,
					billOfMaterialItem);
			/*
			 * [Step2] Basic checks and record error
			 */
			// [2.1] Check and calculate theo loss rate
			billOfMaterialItem.setTheoLossRate(BillOfMaterialItemManager
					.calculateValidLossRate(billOfMaterialExcelModel
							.getTheoLossRate()));
			// [2.2] Check RefMaterial
			String refMaterialSKUId = billOfMaterialExcelModel
					.getRefMaterialSKUId();
			if (ServiceEntityStringHelper.checkNullString(refMaterialSKUId)) {
				// should raise exception and record
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_EXCEL_WRG_MATID,
						billOfMaterialExcelModel.getId());

			}
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
					.getEntityNodeByKey(refMaterialSKUId,
							IServiceEntityNodeFieldConstant.ID,
							MaterialStockKeepUnit.NODENAME,
							billOfMaterialItem.getClient(), null, true);
			if (materialStockKeepUnit == null) {
				// should raise exception and record
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_EXCEL_WRG_MATID,
						refMaterialSKUId);
			} else {
				billOfMaterialItem.setRefMaterialSKUUUID(materialStockKeepUnit
						.getUuid());
			}
			// [2.3] Check RefUnitName
			String refUnitName = billOfMaterialExcelModel.getRefUnitName();
			if (ServiceEntityStringHelper.checkNullString(refUnitName)) {
				// should raise exception and record
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_EXCEL_WRG_UNITNAME,
						billOfMaterialExcelModel.getId());

			}
			String refUnitUUID = materialStockKeepUnitManager
					.getRefUnitUUIDByName(materialStockKeepUnit, refUnitName);
			if (ServiceEntityStringHelper.checkNullString(refUnitUUID)) {
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_EXCEL_WRG_UNITNAME,
						materialStockKeepUnit.getId());
			} else {
				billOfMaterialItem.setRefUnitUUID(refUnitUUID);
			}
			// [2.4] Check ref work center
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getRefWocId())) {
				ProdWorkCenter prodWorkCenter = (ProdWorkCenter) prodWorkCenterManager
						.getEntityNodeByKey(
								billOfMaterialExcelModel.getRefWocId(),
								IServiceEntityNodeFieldConstant.ID,
								MaterialStockKeepUnit.NODENAME,
								billOfMaterialItem.getClient(), null, true);
				if (prodWorkCenter != null) {
					billOfMaterialItem.setRefWocUUID(prodWorkCenter.getUuid());
				}
			}
			// [2.5] Check Sub BOM
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getRefSubBOMId())) {
				BillOfMaterialOrder subBOMOrder = (BillOfMaterialOrder) prodWorkCenterManager
						.getEntityNodeByKey(
								billOfMaterialExcelModel.getRefSubBOMId(),
								IServiceEntityNodeFieldConstant.ID,
								BillOfMaterialOrder.NODENAME,
								billOfMaterialItem.getClient(), null, true);
				if (subBOMOrder != null) {
					billOfMaterialItem.setRefSubBOMUUID(subBOMOrder.getUuid());
				}
			}
		}
	}

	public void convertExcelModelToBOMItem(
			BillOfMaterialExcelModel billOfMaterialExcelModel,
			BillOfMaterialItem billOfMaterialItem) {
		if (billOfMaterialExcelModel != null && billOfMaterialItem != null) {
			List<String> skipFieldList = ServiceCollectionsHelper.asList(
					IServiceEntityNodeFieldConstant.UUID,
					IServiceEntityNodeFieldConstant.PARENTNODEUUID,
					IServiceEntityNodeFieldConstant.CLIENT,
					IServiceEntityNodeFieldConstant.ROOTNODEUUID,
					IDocumentNodeFieldConstant.STATUS);
			ServiceReflectiveHelper
					.convComModelReflect(billOfMaterialExcelModel,
							billOfMaterialItem, skipFieldList);
		}
	}

	public void convertBOMItemToExcelModel(
			BillOfMaterialItem billOfMaterialItem,
			BillOfMaterialExcelModel billOfMaterialExcelModel) {
		if (billOfMaterialExcelModel != null && billOfMaterialItem != null) {
			ServiceReflectiveHelper.convComModelReflect(billOfMaterialItem,
					billOfMaterialExcelModel, null);
		}
	}

}
