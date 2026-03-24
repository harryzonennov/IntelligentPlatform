package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateItem;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplate;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.CorporateCustomerException;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.dto.ServiceExcelReportResponseModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;
import com.company.IntelligentPlatform.common.service.ServiceReflectiveHelper;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IDocumentNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import jakarta.annotation.PostConstruct;
import java.util.*;

@Service
public class BillOfMaterialTemplateReportProxy extends ServiceExcelReportProxy {

	public static final String CONFIG_NAME = "BillOfMaterialTemplate";

	@Autowired
	protected BillOfMaterialTemplateServiceUIModelExtension billOfMaterialTemplateServiceUIModelExtension;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BillOfMaterialTemplateItemServiceUIModelExtension billOfMaterialTemplateItemServiceUIModelExtension;

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;

	@Autowired
	protected BillOfMaterialTemplateManager billOfMaterialTemplateManager;

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
		List<ServiceEntityNode> rawBOMItemList = billOfMaterialTemplateManager
				.getEntityNodeListByKeyList(keyList,
						BillOfMaterialTemplateItem.NODENAME, null);
		for (ServiceEntityNode rawSENode : rawBOMOrderList) {
			BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) rawSENode;
			BillOfMaterialExcelModel billOfMaterialExcelModel = new BillOfMaterialExcelModel();
			BillOfMaterialTemplateUIModel billOfMaterialTemplateUIModel = (BillOfMaterialTemplateUIModel) billOfMaterialTemplateManager
					.genUIModelFromUIModelExtension(
							BillOfMaterialTemplateUIModel.class,
							billOfMaterialTemplateServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							billOfMaterialTemplate, logonInfo, null);
			ServiceReflectiveHelper.convComModelReflect(
					billOfMaterialTemplateUIModel, billOfMaterialExcelModel, null);
			billOfMaterialExcelModelList.add(billOfMaterialExcelModel);
			List<SEUIComModel> subBOMExcelModelList = genSubBomItemExcelModel(
					billOfMaterialTemplate.getUuid(), rawBOMItemList, logonInfo);
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
							BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) rawSENode;
							return billOfMaterialTemplateItem.getRefParentItemUUID();
						}, rawBOMItemList, false);
		if (ServiceCollectionsHelper.checkNullList(subBOMItemList)) {
			return null;
		}
		for (ServiceEntityNode seNode : subBOMItemList) {
			BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) seNode;
			BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel = (BillOfMaterialTemplateItemUIModel) billOfMaterialTemplateManager
					.genUIModelFromUIModelExtension(
							BillOfMaterialTemplateItemUIModel.class,
							billOfMaterialTemplateItemServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							billOfMaterialTemplateItem, logonInfo, null);
			BillOfMaterialExcelModel billOfMaterialExcelModel = new BillOfMaterialExcelModel();
			ServiceReflectiveHelper.convComModelReflect(
					billOfMaterialTemplateItemUIModel, billOfMaterialExcelModel, null);
			billOfMaterialExcelModelList.add(billOfMaterialExcelModel);
			List<SEUIComModel> subBOMExcelModelList = genSubBomItemExcelModel(
					billOfMaterialTemplateItem.getUuid(), rawBOMItemList, logonInfo);
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
		if (BillOfMaterialTemplate.NODENAME.equals(nodeName)) {
			BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) billOfMaterialTemplateManager
					.newRootEntityNode(client);
			convertExcelModelToBOMOrderWrapper(billOfMaterialExcelModel,
					billOfMaterialTemplate);
			// Insert into persistence firstly.
			billOfMaterialTemplateManager.insertSENode(billOfMaterialTemplate,
					logonUserUUID, organizationUUID);
			resultList.add(billOfMaterialTemplate);
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
		if (BillOfMaterialTemplateItem.NODENAME.equals(nodeName)) {
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
			BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) this
					.createItemFromParent(parentBOMItem);
			convertExcelModelToBOMItemWrapper(billOfMaterialExcelModel,
					billOfMaterialTemplateItem);
			// Insert into persistence firstly.
			billOfMaterialTemplateManager.insertSENode(billOfMaterialTemplateItem,
					logonUserUUID, organizationUUID);
			resultList.add(billOfMaterialTemplateItem);
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
		if (BillOfMaterialTemplate.NODENAME.equals(parentBOMItem.getNodeName())) {
			BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) billOfMaterialTemplateManager
					.newEntityNode(parentBOMItem, BillOfMaterialTemplateItem.NODENAME);
			billOfMaterialTemplateItem.setRefParentItemUUID(parentBOMItem.getUuid());
			return billOfMaterialTemplateItem;
		}
		if (BillOfMaterialTemplateItem.NODENAME.equals(parentBOMItem.getNodeName())) {
			BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) billOfMaterialTemplateManager
					.getEntityNodeByKey(parentBOMItem.getRootNodeUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							BillOfMaterialTemplate.NODENAME, null);
			if (billOfMaterialTemplate == null) {
				// raise exception
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_EXCEL_NOPARENT,
						parentBOMItem.getId());
			}
			BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) billOfMaterialTemplateManager
					.newEntityNode(billOfMaterialTemplate,
							BillOfMaterialTemplateItem.NODENAME);
			billOfMaterialTemplateItem.setRefParentItemUUID(parentBOMItem.getUuid());
			BillOfMaterialTemplateItem parentBillOfMaterialTemplateItem = (BillOfMaterialTemplateItem) parentBOMItem;
			billOfMaterialTemplateItem
					.setLayer(parentBillOfMaterialTemplateItem.getLayer() + 1);
			return billOfMaterialTemplateItem;
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
		if (BillOfMaterialTemplate.NODENAME.equals(nodeName)) {
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getUuid())) {
				BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) billOfMaterialTemplateManager
						.getEntityNodeByKey(billOfMaterialExcelModel.getUuid(),
								IServiceEntityNodeFieldConstant.UUID, nodeName,
								client, null);
				if (billOfMaterialTemplate != null) {
					return billOfMaterialTemplate;
				}
			}
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getId())) {
				BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) billOfMaterialTemplateManager
						.getEntityNodeByKey(billOfMaterialExcelModel.getId(),
								IServiceEntityNodeFieldConstant.ID, nodeName,
								client, null, true);
				if (billOfMaterialTemplate != null) {
					return billOfMaterialTemplate;
				}
			}
			return null;
		}

		if (BillOfMaterialTemplateItem.NODENAME.equals(nodeName)) {
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getUuid())) {
				BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) billOfMaterialTemplateManager
						.getEntityNodeByKey(billOfMaterialExcelModel.getUuid(),
								IServiceEntityNodeFieldConstant.UUID, nodeName,
								client, null);
				if (billOfMaterialTemplateItem != null) {
					return billOfMaterialTemplateItem;
				}
			}
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getId())) {
				BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) billOfMaterialTemplateManager
						.getEntityNodeByKey(billOfMaterialExcelModel.getId(),
								IServiceEntityNodeFieldConstant.ID, nodeName,
								client, null);
				if (billOfMaterialTemplateItem != null) {
					return billOfMaterialTemplateItem;
				}
			}
			return null;
		}
		return null;
	}

	/**
	 * Wrapper to get existed BOM entity from persistence
	 *
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	private ServiceEntityNode getExistBOMEntityByIdWrapper(String id,
			String client) throws ServiceEntityConfigureException {
		BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) billOfMaterialTemplateManager
				.getEntityNodeByKey(id, IServiceEntityNodeFieldConstant.ID,
						BillOfMaterialTemplate.NODENAME, null, true);
		if (billOfMaterialTemplate != null) {
			return billOfMaterialTemplate;
		}
		BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) billOfMaterialTemplateManager
				.getEntityNodeByKey(id, IServiceEntityNodeFieldConstant.ID,
						BillOfMaterialTemplateItem.NODENAME, null, true);
		if (billOfMaterialTemplateItem != null) {
			return billOfMaterialTemplateItem;
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
			return BillOfMaterialTemplate.NODENAME;
		} else {
			return BillOfMaterialTemplateItem.NODENAME;
		}
	}

	public void convertExcelModelToBOMOrderWrapper(
			BillOfMaterialExcelModel billOfMaterialExcelModel,
			BillOfMaterialTemplate billOfMaterialTemplate)
			throws ServiceEntityConfigureException, BillOfMaterialException {
		if (billOfMaterialExcelModel != null && billOfMaterialTemplate != null) {
			/*
			 * [Step1] default conversion logic to BOM Order
			 */
			convertExcelModelToBOMOrder(billOfMaterialExcelModel,
					billOfMaterialTemplate);
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
							billOfMaterialTemplate.getClient(), null, true);
			if (materialStockKeepUnit == null) {
				// should raise exception and record
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_EXCEL_WRG_MATID,
						refMaterialSKUId);
			} else {
				billOfMaterialTemplate.setRefMaterialSKUUUID(materialStockKeepUnit
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
				billOfMaterialTemplate.setRefUnitUUID(refUnitUUID);
			}
			// Check ref work center
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getRefWocId())) {
				ProdWorkCenter prodWorkCenter = (ProdWorkCenter) prodWorkCenterManager
						.getEntityNodeByKey(
								billOfMaterialExcelModel.getRefWocId(),
								IServiceEntityNodeFieldConstant.ID,
								MaterialStockKeepUnit.NODENAME,
								billOfMaterialTemplate.getClient(), null, true);
				if (prodWorkCenter != null) {
					billOfMaterialTemplate.setRefWocUUID(prodWorkCenter.getUuid());
				}
			}

		}
	}

	public void convertExcelModelToBOMOrder(
			BillOfMaterialExcelModel billOfMaterialExcelModel,
			BillOfMaterialTemplate billOfMaterialTemplate)
			throws ServiceEntityConfigureException {
		if (billOfMaterialExcelModel != null && billOfMaterialTemplate != null) {
			List<String> skipFieldList = ServiceCollectionsHelper.asList(
					IServiceEntityNodeFieldConstant.UUID,
					IServiceEntityNodeFieldConstant.PARENTNODEUUID,
					IServiceEntityNodeFieldConstant.CLIENT,
					IServiceEntityNodeFieldConstant.ROOTNODEUUID,
					IDocumentNodeFieldConstant.STATUS);
			ServiceReflectiveHelper.convComModelReflect(
					billOfMaterialExcelModel, billOfMaterialTemplate,
					skipFieldList);
		}
	}

	public void convertBOMOrderToExcelModel(
			BillOfMaterialTemplate billOfMaterialTemplate,
			BillOfMaterialExcelModel billOfMaterialExcelModel) {
		ServiceReflectiveHelper.convComModelReflect(billOfMaterialTemplate,
				billOfMaterialExcelModel, null);
	}

	public void convertBOMOrderUIModelToExcelModel(
			BillOfMaterialTemplateUIModel billOfMaterialTemplateUIModel,
			BillOfMaterialExcelModel billOfMaterialExcelModel) {
		ServiceReflectiveHelper.convComModelReflect(billOfMaterialTemplateUIModel,
				billOfMaterialExcelModel, null);
	}

	public void convertExcelModelToBOMItemWrapper(
			BillOfMaterialExcelModel billOfMaterialExcelModel,
			BillOfMaterialTemplateItem billOfMaterialTemplateItem)
			throws BillOfMaterialException, ServiceEntityConfigureException {
		if (billOfMaterialExcelModel != null && billOfMaterialTemplateItem != null) {
			convertExcelModelToBOMItem(billOfMaterialExcelModel,
					billOfMaterialTemplateItem);
			/*
			 * [Step2] Basic checks and record error
			 */
			// [2.1] Check and calculate theo loss rate
			billOfMaterialTemplateItem.setTheoLossRate(BillOfMaterialTemplateItemManager
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
							billOfMaterialTemplateItem.getClient(), null, true);
			if (materialStockKeepUnit == null) {
				// should raise exception and record
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_EXCEL_WRG_MATID,
						refMaterialSKUId);
			} else {
				billOfMaterialTemplateItem.setRefMaterialSKUUUID(materialStockKeepUnit
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
				billOfMaterialTemplateItem.setRefUnitUUID(refUnitUUID);
			}
			// [2.4] Check ref work center
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getRefWocId())) {
				ProdWorkCenter prodWorkCenter = (ProdWorkCenter) prodWorkCenterManager
						.getEntityNodeByKey(
								billOfMaterialExcelModel.getRefWocId(),
								IServiceEntityNodeFieldConstant.ID,
								MaterialStockKeepUnit.NODENAME,
								billOfMaterialTemplateItem.getClient(), null, true);
				if (prodWorkCenter != null) {
					billOfMaterialTemplateItem.setRefWocUUID(prodWorkCenter.getUuid());
				}
			}
			// [2.5] Check Sub BOM
			if (!ServiceEntityStringHelper
					.checkNullString(billOfMaterialExcelModel.getRefSubBOMId())) {
				BillOfMaterialTemplate subBOMOrder = (BillOfMaterialTemplate) prodWorkCenterManager
						.getEntityNodeByKey(
								billOfMaterialExcelModel.getRefSubBOMId(),
								IServiceEntityNodeFieldConstant.ID,
								BillOfMaterialTemplate.NODENAME,
								billOfMaterialTemplateItem.getClient(), null, true);
				if (subBOMOrder != null) {
					billOfMaterialTemplateItem.setRefSubBOMUUID(subBOMOrder.getUuid());
				}
			}
		}
	}

	public void convertExcelModelToBOMItem(
			BillOfMaterialExcelModel billOfMaterialExcelModel,
			BillOfMaterialTemplateItem billOfMaterialTemplateItem) {
		if (billOfMaterialExcelModel != null && billOfMaterialTemplateItem != null) {
			List<String> skipFieldList = ServiceCollectionsHelper.asList(
					IServiceEntityNodeFieldConstant.UUID,
					IServiceEntityNodeFieldConstant.PARENTNODEUUID,
					IServiceEntityNodeFieldConstant.CLIENT,
					IServiceEntityNodeFieldConstant.ROOTNODEUUID,
					IDocumentNodeFieldConstant.STATUS);
			ServiceReflectiveHelper
					.convComModelReflect(billOfMaterialExcelModel,
							billOfMaterialTemplateItem, skipFieldList);
		}
	}

	public void convertBOMItemToExcelModel(
			BillOfMaterialTemplateItem billOfMaterialTemplateItem,
			BillOfMaterialExcelModel billOfMaterialExcelModel) {
		if (billOfMaterialExcelModel != null && billOfMaterialTemplateItem != null) {
			ServiceReflectiveHelper.convComModelReflect(billOfMaterialTemplateItem,
					billOfMaterialExcelModel, null);
		}
	}

}
