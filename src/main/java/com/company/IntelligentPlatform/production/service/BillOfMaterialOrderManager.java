package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.production.dto.BillOfMaterialOrderServiceUIModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.production.dto.BillOfMaterialOrderUIModel;
import com.company.IntelligentPlatform.production.repository.BillOfMaterialOrderRepository;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;
import com.company.IntelligentPlatform.common.model.IServiceEntityCommonFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Logic Manager CLASS FOR Service Entity [BillOfMaterialOrder]
 * 
 * @author
 * @date Wed Dec 23 18:17:03 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
public class BillOfMaterialOrderManager extends ServiceEntityManager {
    @PersistenceContext
    private EntityManager entityManager;


	@Autowired
	protected BillOfMaterialOrderRepository billOfMaterialOrderDAO;

	@Autowired
	protected BillOfMaterialOrderConfigureProxy billOfMaterialOrderConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected BillOfMaterialOrderIdHelper billOfMaterialOrderIdHelper;

	@Autowired
	protected BillOfMaterialItemIdHelper billOfMaterialItemIdHelper;

	@Autowired
	protected BillOfMaterialItemManager billOfMaterialItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected BillOfMaterialOrderServiceUIModelExtension billOfMaterialOrderServiceUIModelExtension;

	@Autowired
	protected BillOfMaterialOrderSearchProxy billOfMaterialOrderSearchProxy;

	@Autowired
	protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

	public static final String METHOD_ConvBillOfMaterialOrderToUI = "convBillOfMaterialOrderToUI";

	public static final String METHOD_ConvUIToBillOfMaterialOrder = "convUIToBillOfMaterialOrder";

	public static final String METHOD_ConvMaterialStockKeepUnitToUI = "convMaterialStockKeepUnitToUI";

	public static final String METHOD_ConvProcessRouteOrderToUI = "convProcessRouteOrderToUI";

	public static final String METHOD_ConvProdWorkCenterToUI = "convProdWorkCenterToUI";

	public static final String METHOD_ConvBillOfMaterialTemplateToUI = "convBillOfMaterialTemplateToUI";

	protected Map<String, BillOfMaterialOrder> billOfMaterialOrderMap = new HashMap<>();

	protected Map<Integer, String> itemViewTypeMap;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	protected Map<String, Map<Integer, String>> leadTimeCalModeMap = new HashMap<>();

	public Map<Integer, String> initStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, BillOfMaterialOrderUIModel.class, "status");
	}

	public Map<Integer, String> initItemViewTypeMap()
			throws ServiceEntityInstallationException {
		if (this.itemViewTypeMap == null) {
			this.itemViewTypeMap = serviceDropdownListHelper.getUIDropDownMap(
					BillOfMaterialOrderUIModel.class, "itemViewType");
		}
		return this.itemViewTypeMap;
	}

	public Map<Integer, String> initLeadTimeCalModeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.leadTimeCalModeMap, BillOfMaterialOrderUIModel.class, "leadTimeCalMode");
	}

	public BillOfMaterialOrderManager() {
		super.seConfigureProxy = new BillOfMaterialOrderConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, billOfMaterialOrderDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(billOfMaterialOrderConfigureProxy);
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) super
				.newRootEntityNode(client);
		String billOfMaterialID = billOfMaterialOrderIdHelper
				.genDefaultId(client);
		billOfMaterialOrder.setId(billOfMaterialID);
		return billOfMaterialOrder;
	}

	/**
	 * Logic of generate billOfMaterial Default ID
	 * 
	 * @param billOfMaterialOrder
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public String newBomItemId(BillOfMaterialOrder billOfMaterialOrder)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> rawBomItemList = getEntityNodeListByKey(
				billOfMaterialOrder.getUuid(),
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				BillOfMaterialItem.NODENAME, null);
		String billOfMaterialItemID = billOfMaterialItemIdHelper
				.genDefaultIdOnline(rawBomItemList,
						billOfMaterialOrder.getClient());
		billOfMaterialItemID = billOfMaterialOrder.getId() + "-"
				+ billOfMaterialItemID;
		return billOfMaterialItemID;
	}

	/**
	 * Logic to get relative BOM Order from production order If production order
	 * binding to some BOM item, then return parent BOM Order
	 * 
	 * @param refBOMUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws BillOfMaterialException
	 */
	public BillOfMaterialOrder getRefBillOfMaterialOrderWrapper(
			String refBOMUUID, String client)
			throws ServiceEntityConfigureException, BillOfMaterialException {
		BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) getEntityNodeByKey(
				refBOMUUID, IServiceEntityNodeFieldConstant.UUID,
				BillOfMaterialOrder.NODENAME, client, null);
		if (billOfMaterialOrder == null) {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) getEntityNodeByKey(
					refBOMUUID, IServiceEntityNodeFieldConstant.UUID,
					BillOfMaterialItem.NODENAME, client, null);
			if (billOfMaterialItem != null) {
				billOfMaterialOrder = (BillOfMaterialOrder) getEntityNodeByKey(
						billOfMaterialItem.getRootNodeUUID(),
						IServiceEntityNodeFieldConstant.UUID,
						BillOfMaterialOrder.NODENAME, client, null);
			} else {
				throw new BillOfMaterialException(
						BillOfMaterialException.PARA_NO_BOMOrder, refBOMUUID);
			}
		}
		return billOfMaterialOrder;
	}

	/**
	 * "temporary code" Get default bill of material order by SKU
	 * 
	 * @param skuUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public BillOfMaterialOrder getDefaultBOMBySKU(String skuUUID,
			String client) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = getBOMOrderListBySKU(skuUUID,
				client);
		if (ServiceCollectionsHelper.checkNullList(resultList)) {
			return null;
		}
		if(resultList.size() == 1){
			return (BillOfMaterialOrder) resultList.get(0);
		}
		return filterMostRecentBOMOrder(resultList);
	}

	public List<ServiceEntityNode> getBOMOrderListBySKU(String skuUUID,
			String client) throws ServiceEntityConfigureException {
		List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(skuUUID, IServiceEntityCommonFieldConstant.REFMATERIALSKUUUID);
		ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(BillOfMaterialOrder.STATUS_INUSE, "status");
		ServiceBasicKeyStructure key3 = new ServiceBasicKeyStructure(BillOfMaterialOrder.SENAME,
				IServiceEntityNodeFieldConstant.SERVICEENTITYNAME);
		return getEntityNodeListByKeyList(ServiceCollectionsHelper.asList(key1, key2, key3),
				 BillOfMaterialOrder.NODENAME, client,
				null);
	}

	/**
	 * Core Logic to archive BOM Order: set status to 'Retired'.
	 * 
	 * @param billOfMaterialOrder
	 * @param logonUserUUID
	 * @param organizationUUID
	 */
	public void archiveBOMOrder(BillOfMaterialOrder billOfMaterialOrder,
			String logonUserUUID, String organizationUUID) {
		BillOfMaterialOrder billOfMaterialOrderBack = (BillOfMaterialOrder) billOfMaterialOrder
				.clone();
		billOfMaterialOrder.setStatus(BillOfMaterialOrder.STATUS_RETIRED);
		updateSENode(billOfMaterialOrder, billOfMaterialOrderBack,
				logonUserUUID, organizationUUID);
	}

	/**
	 * Core Logic to approve billOfMaterialOrder and update to DB
	 *
	 * @param billOfMaterialOrderServiceModel
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 */
	public void activeService(
			BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel,
			String logonUserUUID, String organizationUUID)
			throws ServiceModuleProxyException, ServiceEntityConfigureException {
		this.executeActionCore(billOfMaterialOrderServiceModel,
				ServiceCollectionsHelper.asList(BillOfMaterialOrder.STATUS_SUBMITTED),
				BillOfMaterialOrder.STATUS_INUSE,
				BillOfMaterialOrderActionNode.DOC_ACTION_REJECT_APPROVE, null, logonUserUUID, organizationUUID);
	}

	/**
	 * Core Logic to approve billOfMaterialOrder and update to DB
	 *
	 * @param billOfMaterialOrderServiceModel
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 */
	public void rejectApproveService(
			BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel,
			String logonUserUUID, String organizationUUID)
			throws ServiceModuleProxyException, ServiceEntityConfigureException {
		this.executeActionCore(billOfMaterialOrderServiceModel,
				ServiceCollectionsHelper.asList(BillOfMaterialOrder.STATUS_SUBMITTED),
				BillOfMaterialOrder.STATUS_REJECT_APPROVAL,
				BillOfMaterialOrderActionNode.DOC_ACTION_REJECT_APPROVE, null, logonUserUUID, organizationUUID);
	}

	/**
	 * Core Logic to approve billOfMaterialOrder and update to DB
	 *
	 * @param billOfMaterialOrderServiceModel
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 */
	public void submitService(
			BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel,
			String logonUserUUID, String organizationUUID)
			throws ServiceModuleProxyException, ServiceEntityConfigureException {
		this.executeActionCore(billOfMaterialOrderServiceModel,
				ServiceCollectionsHelper.asList(BillOfMaterialOrder.STATUS_INITIAL,
						BillOfMaterialOrder.STATUS_REJECT_APPROVAL)
				, BillOfMaterialOrder.STATUS_SUBMITTED,
				BillOfMaterialOrderActionNode.DOC_ACTION_SUBMIT, null, logonUserUUID, organizationUUID);
	}

	/**
	 * Core Logic to approve billOfMaterialOrder and update to DB
	 *
	 * @param billOfMaterialOrderServiceModel
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 */
	public void revokeSubmitService(
			BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel,
			String logonUserUUID, String organizationUUID)
			throws ServiceModuleProxyException, ServiceEntityConfigureException {
		this.executeActionCore(billOfMaterialOrderServiceModel,
				ServiceCollectionsHelper.asList(BillOfMaterialOrder.STATUS_SUBMITTED), BillOfMaterialOrder.STATUS_INITIAL,
				BillOfMaterialOrderActionNode.DOC_ACTION_REVOKE_SUBMIT, null, logonUserUUID, organizationUUID);
	}

	/**
	 * Core Logic to approve billOfMaterialOrder and update to DB
	 *
	 * @param billOfMaterialOrderServiceModel
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 */
	public void executeActionCore(
			BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel, List<Integer> curStatusList, int targetStatus,
			int actionCode,
			Function<BillOfMaterialItemServiceModel, BillOfMaterialItemServiceModel> updateItemCallback,
			String logonUserUUID, String organizationUUID)
			throws ServiceModuleProxyException, ServiceEntityConfigureException {
		BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderServiceModel
				.getBillOfMaterialOrder();
		if (!DocActionNodeProxy.checkCurStatus(curStatusList, billOfMaterialOrder.getStatus())) {
			return;
		}
		billOfMaterialOrder.setStatus(targetStatus);
		docActionNodeProxy.updateDocActionWrapper(actionCode,
				BillOfMaterialOrderActionNode.NODENAME, null, IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER,
				this,
				billOfMaterialOrderServiceModel,
				billOfMaterialOrder,
				logonUserUUID,
				organizationUUID);
		// update action node
		if (!ServiceCollectionsHelper
				.checkNullList(billOfMaterialOrderServiceModel.getBillOfMaterialItemList())) {
			for (BillOfMaterialItemServiceModel billOfMaterialOrderMaterialItemServiceModel :
					billOfMaterialOrderServiceModel
							.getBillOfMaterialItemList()) {
				if (updateItemCallback != null) {
					updateItemCallback.apply(billOfMaterialOrderMaterialItemServiceModel);
					continue;
				}
			}
		}
		updateServiceModuleWithDelete(
				BillOfMaterialOrderServiceModel.class,
				billOfMaterialOrderServiceModel, logonUserUUID, organizationUUID, BillOfMaterialOrder.SENAME,
				billOfMaterialOrderServiceUIModelExtension);
	}

	/**
	 * Core Logic to Initialize BOM Order: set status to 'Initial'.
	 * 
	 * @param billOfMaterialOrder
	 * @param logonUserUUID
	 * @param organizationUUID
	 */
	public void reInitBOMOrder(BillOfMaterialOrder billOfMaterialOrder,
			String logonUserUUID, String organizationUUID) {
		BillOfMaterialOrder billOfMaterialOrderBack = (BillOfMaterialOrder) billOfMaterialOrder
				.clone();
		billOfMaterialOrder.setStatus(BillOfMaterialOrder.STATUS_INITIAL);
		updateSENode(billOfMaterialOrder, billOfMaterialOrderBack,
				logonUserUUID, organizationUUID);
	}

	/**
	 * Get all relative sub BOM Order from one specified template
	 * @param templateUUID
	 * @param client
	 * @return
	 */
	public List<ServiceEntityNode> getAllSubBOMOrderList(String templateUUID, String client) throws ServiceEntityConfigureException {
		return this.getEntityNodeListByKey(templateUUID, BillOfMaterialOrder.FIELD_RefTemplateUUID,
				BillOfMaterialOrder.NODENAME, client, null);
	}

	public void batchArchiveOldOrders(String templateUUID, String logonUserUUID, String organizationUUID,
									  String client) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> subBOMOrderList = getAllSubBOMOrderList(templateUUID, client);
		if(ServiceCollectionsHelper.checkNullList(subBOMOrderList)){
			return;
		}
		for(ServiceEntityNode serviceEntityNode: subBOMOrderList){
			BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) serviceEntityNode;
			if(billOfMaterialOrder.getStatus() == BillOfMaterialOrder.STATUS_INUSE){
				archiveBOMOrder(billOfMaterialOrder, logonUserUUID, organizationUUID);
			}
		}
	}

	/**
	 * From all sub BOM Order from one template, choose the one with highest version number and patch number
	 * @param templateUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public BillOfMaterialOrder getRecentBOMOrder(String templateUUID, String client) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> subBOMOrderList = getAllSubBOMOrderList(templateUUID, client);
		return filterMostRecentBOMOrder(subBOMOrderList);
	}

	public BillOfMaterialOrder filterMostRecentBOMOrder(List<ServiceEntityNode> subBOMOrderList){
		if(ServiceCollectionsHelper.checkNullList(subBOMOrderList)){
			return null;
		}
		int versionNumber = 0, patchNumber = 0;
		BillOfMaterialOrder result = null;
		for(ServiceEntityNode serviceEntityNode: subBOMOrderList){
			BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) serviceEntityNode;
			if(billOfMaterialOrder.getVersionNumber() >= versionNumber || (billOfMaterialOrder.getVersionNumber() ==
					versionNumber && billOfMaterialOrder.getPatchNumber() >= patchNumber)){
				versionNumber = billOfMaterialOrder.getVersionNumber();
				patchNumber = billOfMaterialOrder.getPatchNumber();
				result = billOfMaterialOrder;
			}
		}
		return result;
	}

	public BillOfMaterialOrder switchToRecentActiveBOMOrder(String curOrderUUID, String client, boolean fastSkip) throws ServiceEntityConfigureException, BillOfMaterialException {
		BillOfMaterialOrder curBillOfMaterialOrder = (BillOfMaterialOrder) this.getEntityNodeByKey(curOrderUUID,
				IServiceEntityNodeFieldConstant.UUID,
				BillOfMaterialOrder.NODENAME, client, null);
		if(curBillOfMaterialOrder == null){
			throw new BillOfMaterialException(BillOfMaterialException.PARA_NO_BOMOrder, curOrderUUID);
		}
		if(curBillOfMaterialOrder.getStatus() == BillOfMaterialOrder.STATUS_INUSE && fastSkip){
			return curBillOfMaterialOrder;
		}
		List<ServiceEntityNode> subBOMOrderList = getAllSubBOMOrderList(curBillOfMaterialOrder.getRefTemplateUUID(), client);
		if(ServiceCollectionsHelper.checkNullList(subBOMOrderList)){
			return _checkReturnCurBOMOrder(curBillOfMaterialOrder);
		}
		if(subBOMOrderList.size() == 1){
			return _checkReturnCurBOMOrder(curBillOfMaterialOrder);
		}
		if(subBOMOrderList.size() > 1){
			return filterMostRecentBOMOrder(subBOMOrderList);
		}
		return _checkReturnCurBOMOrder(curBillOfMaterialOrder);
	}

	private BillOfMaterialOrder _checkReturnCurBOMOrder(BillOfMaterialOrder curBillOfMaterialOrder) throws BillOfMaterialException {
		if(curBillOfMaterialOrder.getStatus() == BillOfMaterialOrder.STATUS_INUSE){
			return curBillOfMaterialOrder;
		}
		throw new BillOfMaterialException(BillOfMaterialException.PARA_NO_BOMOrder, curBillOfMaterialOrder.getUuid());
	}

	public List<BOMItemWithMaterialSKUUnion> getBOMItemMaterialSKUList(String bomOrderUUID, String client) throws ServiceEntityConfigureException, BillOfMaterialException {
		BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) this.getEntityNodeByKey(bomOrderUUID,
				IServiceEntityNodeFieldConstant.UUID,
				BillOfMaterialOrder.NODENAME, client, null);
		if(billOfMaterialOrder == null){
			throw new BillOfMaterialException(BillOfMaterialException.PARA_NO_BOMOrder, bomOrderUUID);
		}
		List<ServiceEntityNode> bomItemList = getEntityNodeListByKey(
				billOfMaterialOrder.getUuid(),
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				BillOfMaterialItem.NODENAME, billOfMaterialOrder.getClient(),
				null);
		if(ServiceCollectionsHelper.checkNullList(bomItemList)){
			return null;
		}
		List<String> refMaterialSKUUUIDList = bomItemList.stream().map(serviceEntityNode -> {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) serviceEntityNode;
			return billOfMaterialItem.getRefMaterialSKUUUID();
		}).collect(Collectors.toList());
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
		key1.setMultipleValueList(refMaterialSKUUUIDList);
		key1.setKeyName(IServiceEntityNodeFieldConstant.UUID);

		ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(MaterialStockKeepUnit.SENAME,
				IServiceEntityNodeFieldConstant.SERVICEENTITYNAME);
		List<ServiceEntityNode> materialSKUList =
				materialStockKeepUnitManager.getEntityNodeListByKeyList(ServiceCollectionsHelper.asList(key1, key2),
				MaterialStockKeepUnit.NODENAME, client, null);
		List<BOMItemWithMaterialSKUUnion> resultList = new ArrayList<>();
		bomItemList.stream().forEach(serviceEntityNode -> {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) serviceEntityNode;
			MaterialStockKeepUnit materialStockKeepUnit =
					(MaterialStockKeepUnit) ServiceCollectionsHelper.filterSENodeOnline(billOfMaterialItem.getRefMaterialSKUUUID(),
							materialSKUList);
			if(materialStockKeepUnit != null){
				resultList.add(new BOMItemWithMaterialSKUUnion(billOfMaterialItem, materialStockKeepUnit));
			}
		});
		return resultList;
	}

	public static class BOMItemWithMaterialSKUUnion{

		private BillOfMaterialItem billOfMaterialItem;

		private MaterialStockKeepUnit materialStockKeepUnit;

		public BOMItemWithMaterialSKUUnion() {
		}

		public BOMItemWithMaterialSKUUnion(BillOfMaterialItem billOfMaterialItem,
										   MaterialStockKeepUnit materialStockKeepUnit) {
			this.billOfMaterialItem = billOfMaterialItem;
			this.materialStockKeepUnit = materialStockKeepUnit;
		}

		public BillOfMaterialItem getBillOfMaterialItem() {
			return billOfMaterialItem;
		}

		public void setBillOfMaterialItem(BillOfMaterialItem billOfMaterialItem) {
			this.billOfMaterialItem = billOfMaterialItem;
		}

		public MaterialStockKeepUnit getMaterialStockKeepUnit() {
			return materialStockKeepUnit;
		}

		public void setMaterialStockKeepUnit(MaterialStockKeepUnit materialStockKeepUnit) {
			this.materialStockKeepUnit = materialStockKeepUnit;
		}
	}

	/**
	 * Generate the productive BOM model by calculating all the embedded BOM
	 * model and generate the compound BOM for direct production
	 * 
	 * @param billOfMaterialOrder
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws BillOfMaterialException
	 * @throws MaterialException
	 */
	@Deprecated
	public List<ServiceEntityNode> genProductiveBOMModelCore(
			BillOfMaterialOrder billOfMaterialOrder)
			throws ServiceEntityConfigureException, BillOfMaterialException,
			MaterialException {
		/**
		 * [Step1] get the root BOM model and BOM Item list
		 */

		if (billOfMaterialOrder == null) {
			throw new BillOfMaterialException(
					BillOfMaterialException.PARA_NO_BOMOrder);
		}
		List<ServiceEntityNode> bomItemList = getEntityNodeListByKey(
				billOfMaterialOrder.getUuid(),
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				BillOfMaterialItem.NODENAME, billOfMaterialOrder.getClient(),
				null);
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		if (bomItemList != null && bomItemList.size() > 0) {
			for (ServiceEntityNode seNode : bomItemList) {
				ProductiveBOMItem productiveBOMItem = generateInitProductiveBOMItem((BillOfMaterialItem) seNode);
				resultList.add(productiveBOMItem);
			}
		}
		/**
		 * [Step2] Navigate the first BOM Layer
		 */
		for (ServiceEntityNode seNode : bomItemList) {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) seNode;
			if (billOfMaterialItem.getRefSubBOMUUID() != null) {
				BillOfMaterialOrder subBOMOrder = (BillOfMaterialOrder) getEntityNodeByKey(
						billOfMaterialItem.getRefSubBOMUUID(),
						IServiceEntityNodeFieldConstant.UUID,
						BillOfMaterialOrder.NODENAME,
						billOfMaterialOrder.getClient(), null);
				if (subBOMOrder != null) {
					StorageCoreUnit storageCoreUnit1 = new StorageCoreUnit();
					storageCoreUnit1.setAmount(billOfMaterialItem.getAmount());
					storageCoreUnit1.setRefMaterialSKUUUID(billOfMaterialItem
							.getRefMaterialSKUUUID());
					storageCoreUnit1.setRefUnitUUID(billOfMaterialItem
							.getRefUnitUUID());
					StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit();
					storageCoreUnit2.setAmount(subBOMOrder.getAmount());
					storageCoreUnit2.setRefMaterialSKUUUID(subBOMOrder
							.getRefMaterialSKUUUID());
					storageCoreUnit2.setRefUnitUUID(subBOMOrder
							.getRefUnitUUID());
					double ratio = materialStockKeepUnitManager
							.getStorageUnitRatio(storageCoreUnit1,
									storageCoreUnit2, seNode.getClient());
					List<ServiceEntityNode> subBOMList = getSubBOMOrderListForProduction(
							billOfMaterialItem, subBOMOrder, ratio);
					if (subBOMList != null && subBOMList.size() > 0) {
						resultList.addAll(subBOMList);
					}
				}
			}
		}
		return resultList;
	}

	@Override
	public ServiceEntityNode getEntityNodeByKey(Object keyValue,
			String keyName, String nodeName, String client,
			List<ServiceEntityNode> rawSEList)
			throws ServiceEntityConfigureException {
		if (IServiceEntityNodeFieldConstant.UUID.equals(keyName)
				&& BillOfMaterialOrder.NODENAME.equals(nodeName)) {
			if (this.billOfMaterialOrderMap.containsKey(keyValue)) {
				return this.billOfMaterialOrderMap.get(keyValue);
			}
			// In case not find, then find from persistence
			BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) super
					.getEntityNodeByKey(keyValue, keyName, nodeName, client,
							rawSEList);
			if (billOfMaterialOrder != null) {
				this.billOfMaterialOrderMap.put(keyName, billOfMaterialOrder);
			}
			return billOfMaterialOrder;
		} else {
			return super.getEntityNodeByKey(keyValue, keyName, nodeName,
					client, rawSEList);
		}
	}

	@Override
	public void updateBuffer(ServiceEntityNode serviceEntityNode) {
		if (serviceEntityNode != null
				&& LogonUser.SENAME.equals(serviceEntityNode
						.getServiceEntityName())) {
			BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) serviceEntityNode;
			this.billOfMaterialOrderMap.put(billOfMaterialOrder.getUuid(),
					billOfMaterialOrder);
		}
	}

	/**
	 * Set ratio to whole productive bom list
	 * 
	 * @param productiveBOMList
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws BillOfMaterialException
	 * @throws MaterialException
	 */
	public void setRatioToProductiveBOMModel(double ratio,
			List<ServiceEntityNode> productiveBOMList)
			throws ServiceEntityConfigureException, BillOfMaterialException,
			MaterialException {
		for (ServiceEntityNode seNode : productiveBOMList) {
			if (BillOfMaterialItem.NODENAME.equals(seNode.getNodeName())) {
				ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
				// don't set ratio to amount
				// productiveBOMItem.setAmount(productiveBOMItem.getAmount()*ratio);
				if (productiveBOMItem.getAmountWithLossRate() == 0) {
					double amountWithLossRate = productiveBOMItem.getAmount()
							/ (1 - productiveBOMItem.getTheoLossRate() / 100)
							* ratio;
					productiveBOMItem.setAmountWithLossRate(amountWithLossRate);
				}
				productiveBOMItem.setAmountWithLossRate(productiveBOMItem
						.getAmountWithLossRate() * ratio);
			}
			if (BillOfMaterialOrder.NODENAME.equals(seNode.getNodeName())) {
				ProductiveBOMOrder productiveBOMOrder = (ProductiveBOMOrder) seNode;
				// don't set ratio to amount
				// productiveBOMOrder.setAmount(productiveBOMOrder.getAmount()*ratio);
				if (productiveBOMOrder.getAmountWithLossRate() == 0) {
					double amountWithLossRate = productiveBOMOrder.getAmount()
							* ratio;
					productiveBOMOrder
							.setAmountWithLossRate(amountWithLossRate);
				}
				productiveBOMOrder.setAmountWithLossRate(productiveBOMOrder
						.getAmountWithLossRate() * ratio);
			}
		}
	}

	/**
	 * model and generate the compound BOM for direct production
	 * 
	 * @param billOfMaterialOrder
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws BillOfMaterialException
	 * @throws MaterialException
	 */
	public List<ServiceEntityNode> genProductiveBOMModel(
			BillOfMaterialOrder billOfMaterialOrder)
			throws ServiceEntityConfigureException, BillOfMaterialException,
			MaterialException {
		/*
		 * [Step1] get the root BOM model and BOM Item list
		 */

		if (billOfMaterialOrder == null) {
			throw new BillOfMaterialException(
					BillOfMaterialException.PARA_NO_BOMOrder);
		}
		List<ServiceEntityNode> allBomItemList = getEntityNodeListByKey(
				billOfMaterialOrder.getUuid(),
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				BillOfMaterialItem.NODENAME, billOfMaterialOrder.getClient(),
				null);
		List<ServiceEntityNode> firstLayerBomItemList = filterSubBOMItemList(
				billOfMaterialOrder.getUuid(), allBomItemList);
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(firstLayerBomItemList)) {
			for (ServiceEntityNode seNode : firstLayerBomItemList) {
				ProductiveBOMItem productiveBOMItem = generateInitProductiveBOMItem((BillOfMaterialItem) seNode);
				resultList.add(productiveBOMItem);
			}
		}
		/*
		 * [Step2] Navigate the first BOM Layer
		 */
		for (ServiceEntityNode seNode : firstLayerBomItemList) {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) seNode;
			List<ServiceEntityNode> subBOMList = generateSubProductiveBomModel(
					billOfMaterialOrder, billOfMaterialItem, allBomItemList);
			if (!ServiceCollectionsHelper.checkNullList(subBOMList)) {
				resultList.addAll(subBOMList);
			}
		}
		return resultList;
	}

	/**
	 * Recursively to generate sub productive BOM model
	 * 
	 * @param billOfMaterialItem
	 * @param allBomItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	private List<ServiceEntityNode> generateSubProductiveBomModel(
			BillOfMaterialOrder billOfMaterialOrder,
			BillOfMaterialItem billOfMaterialItem,
			List<ServiceEntityNode> allBomItemList) throws MaterialException,
			ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		List<ServiceEntityNode> directChildBomItemList = filterSubBOMItemList(
				billOfMaterialItem.getUuid(), allBomItemList);
		/*
		 * [Step1] In case this node has sub nodes
		 */
		if (!ServiceCollectionsHelper.checkNullList(directChildBomItemList)) {
			for (ServiceEntityNode seNode : directChildBomItemList) {
				BillOfMaterialItem childBomItem = (BillOfMaterialItem) seNode;
				ProductiveBOMItem productiveBOMItem = generateInitProductiveBOMItem(childBomItem);
				// Set Route Order UUID
				productiveBOMItem.setRefRouteOrderUUID(billOfMaterialOrder
						.getRefRouteOrderUUID());
				productiveBOMItem.setLeadTimeCalMode(billOfMaterialOrder
						.getLeadTimeCalMode());
				resultList.add(productiveBOMItem);

				// Recursive call this method to get sub productive BOM item
				List<ServiceEntityNode> subProductiveBomList = generateSubProductiveBomModel(
						billOfMaterialOrder, childBomItem, allBomItemList);
				if (!ServiceCollectionsHelper
						.checkNullList(subProductiveBomList)) {
					resultList.addAll(subProductiveBomList);
				}
			}
		}
		/*
		 * [Step2] In case this bom item has reference BOM order
		 */
		if (billOfMaterialItem.getRefSubBOMUUID() != null) {
			BillOfMaterialOrder subBOMOrder = (BillOfMaterialOrder) getEntityNodeByKey(
					billOfMaterialItem.getRefSubBOMUUID(),
					IServiceEntityNodeFieldConstant.UUID,
					BillOfMaterialOrder.NODENAME,
					billOfMaterialOrder.getClient(), null);
			if (subBOMOrder != null) {
				StorageCoreUnit storageCoreUnit1 = new StorageCoreUnit();
				storageCoreUnit1.setAmount(billOfMaterialItem.getAmount());
				storageCoreUnit1.setRefMaterialSKUUUID(billOfMaterialItem
						.getRefMaterialSKUUUID());
				storageCoreUnit1.setRefUnitUUID(billOfMaterialItem
						.getRefUnitUUID());
				StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit();
				storageCoreUnit2.setAmount(subBOMOrder.getAmount());
				storageCoreUnit2.setRefMaterialSKUUUID(subBOMOrder
						.getRefMaterialSKUUUID());
				storageCoreUnit2.setRefUnitUUID(subBOMOrder.getRefUnitUUID());
				double ratio = materialStockKeepUnitManager
						.getStorageUnitRatio(storageCoreUnit1,
								storageCoreUnit2,
								billOfMaterialOrder.getClient());
				List<ServiceEntityNode> subBOMList = getSubBOMOrderListForProduction(
						billOfMaterialItem, subBOMOrder, ratio);
				if (subBOMList != null && subBOMList.size() > 0) {
					resultList.addAll(subBOMList);
				}
			}
		}
		return resultList;
	}

	public ProductiveBOMOrder genInitProductiveBomOrder(
			BillOfMaterialOrder billOfMaterialOrder) {
		ProductiveBOMOrder productiveBOMOrder = new ProductiveBOMOrder();
		productiveBOMOrder.setUuid(billOfMaterialOrder.getUuid());
		productiveBOMOrder.setId(billOfMaterialOrder.getId());
		productiveBOMOrder.setName(billOfMaterialOrder.getName());
		productiveBOMOrder.setNote(billOfMaterialOrder.getNote());
		productiveBOMOrder.setRefMaterialSKUUUID(billOfMaterialOrder
				.getRefMaterialSKUUUID());
		productiveBOMOrder.setRefUnitUUID(billOfMaterialOrder.getRefUnitUUID());
		productiveBOMOrder.setAmount(billOfMaterialOrder.getAmount());
		productiveBOMOrder.setRootNodeUUID(billOfMaterialOrder
				.getRootNodeUUID());
		productiveBOMOrder.setParentNodeUUID(billOfMaterialOrder
				.getParentNodeUUID());
		productiveBOMOrder.setItemCategory(billOfMaterialOrder
				.getItemCategory());
		productiveBOMOrder.setClient(billOfMaterialOrder.getClient());
		productiveBOMOrder.setRefRouteOrderUUID(billOfMaterialOrder
				.getRefRouteOrderUUID());
		productiveBOMOrder.setLeadTimeCalMode(billOfMaterialOrder
				.getLeadTimeCalMode());
		return productiveBOMOrder;
	}

	/**
	 * Generate the initial productive BOM Item from standard BOM Item
	 * 
	 * @param billOfMaterialItem
	 * @return
	 */
	public ProductiveBOMItem generateInitProductiveBOMItem(
			BillOfMaterialItem billOfMaterialItem) {
		ProductiveBOMItem productiveBOMItem = new ProductiveBOMItem();
		productiveBOMItem.setUuid(billOfMaterialItem.getUuid());
		productiveBOMItem.setId(billOfMaterialItem.getId());
		productiveBOMItem.setName(billOfMaterialItem.getName());
		productiveBOMItem.setNote(billOfMaterialItem.getNote());
		productiveBOMItem.setRefMaterialSKUUUID(billOfMaterialItem
				.getRefMaterialSKUUUID());
		productiveBOMItem.setRefParentItemUUID(billOfMaterialItem
				.getRefParentItemUUID());
		productiveBOMItem.setRefUnitUUID(billOfMaterialItem.getRefUnitUUID());
		productiveBOMItem.setAmount(billOfMaterialItem.getAmount());
		productiveBOMItem.setRefSubBOMUUID(billOfMaterialItem
				.getRefSubBOMUUID());
		productiveBOMItem.setLayer(billOfMaterialItem.getLayer());
		productiveBOMItem.setRootNodeUUID(billOfMaterialItem.getRootNodeUUID());
		productiveBOMItem.setParentNodeUUID(billOfMaterialItem
				.getParentNodeUUID());
		productiveBOMItem.setItemCategory(billOfMaterialItem.getItemCategory());
		productiveBOMItem.setTheoLossRate(billOfMaterialItem.getTheoLossRate());
		productiveBOMItem.setLeadTimeOffset(billOfMaterialItem
				.getLeadTimeOffset());
		productiveBOMItem.setClient(billOfMaterialItem.getClient());
		return productiveBOMItem;
	}

	/**
	 * Logic to binding the sub BOM model to parent BillOfMaterial Item
	 * 
	 * @param parentBOMItem
	 * @param billOfMaterialOrder
	 * @param ratio
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public List<ServiceEntityNode> getSubBOMOrderListForProduction(
			BillOfMaterialItem parentBOMItem,
			BillOfMaterialOrder billOfMaterialOrder, double ratio)
			throws ServiceEntityConfigureException, MaterialException {
		/*
		 * [Step1] Get all BOM Item list
		 */
		List<ServiceEntityNode> bomItemList = getEntityNodeListByKey(
				billOfMaterialOrder.getUuid(),
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				BillOfMaterialItem.NODENAME, billOfMaterialOrder.getClient(),
				null);
		if (bomItemList == null || bomItemList.size() == 0) {
			return null;
		}
		/*
		 * [Step2] Reset parentNode UUID for first layer, point to parentBOMItem
		 */
		List<ServiceEntityNode> firstBomLayerList = filterBOMItemListByLayer(1,
				bomItemList);
		if (firstBomLayerList == null || firstBomLayerList.size() == 0) {
			return null;
		}
		/*
		 * [Step4] Reset the layer and ratio for all the list, and point to
		 * parentBOMItem's root node
		 */
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : bomItemList) {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) seNode;
			ProductiveBOMItem productiveBOMItem = generateInitProductiveBOMItem(billOfMaterialItem);
			productiveBOMItem.setAmount(billOfMaterialItem.getAmount() * ratio);
			productiveBOMItem.setRootNodeUUID(parentBOMItem.getRootNodeUUID());
			productiveBOMItem.setLayer(billOfMaterialItem.getLayer()
					+ parentBOMItem.getLayer());
			if (billOfMaterialOrder.getUuid().equals(
					billOfMaterialItem.getParentNodeUUID())) {
				// In case this item is in direct 1st layer, pointing to
				// parentBOMItem
				productiveBOMItem.setParentNodeUUID(parentBOMItem.getUuid());
				productiveBOMItem.setRefParentItemUUID(parentBOMItem.getUuid());
			}
			// Set Route Order UUID
			productiveBOMItem.setRefRouteOrderUUID(billOfMaterialOrder
					.getRefRouteOrderUUID());
			productiveBOMItem.setLeadTimeCalMode(billOfMaterialOrder
					.getLeadTimeCalMode());
			resultList.add(productiveBOMItem);
			if (billOfMaterialItem.getRefSubBOMUUID() != null) {
				BillOfMaterialOrder subBOMOrder = (BillOfMaterialOrder) getEntityNodeByKey(
						billOfMaterialItem.getRefSubBOMUUID(),
						IServiceEntityNodeFieldConstant.UUID,
						BillOfMaterialOrder.NODENAME,
						billOfMaterialItem.getClient(), null);
				if (subBOMOrder != null) {
					StorageCoreUnit storageCoreUnit1 = new StorageCoreUnit();
					storageCoreUnit1.setAmount(productiveBOMItem.getAmount());
					storageCoreUnit1.setRefMaterialSKUUUID(productiveBOMItem
							.getRefMaterialSKUUUID());
					storageCoreUnit1.setRefUnitUUID(productiveBOMItem
							.getRefUnitUUID());

					StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit();
					storageCoreUnit2.setAmount(subBOMOrder.getAmount());
					storageCoreUnit2.setRefMaterialSKUUUID(subBOMOrder
							.getRefMaterialSKUUUID());
					storageCoreUnit2.setRefUnitUUID(subBOMOrder
							.getRefUnitUUID());
					double subRatio = materialStockKeepUnitManager
							.getStorageUnitRatio(storageCoreUnit1,
									storageCoreUnit2,
									billOfMaterialItem.getClient());
					List<ServiceEntityNode> subBOMList = getSubBOMOrderListForProduction(
							billOfMaterialItem, subBOMOrder, subRatio);
					if (subBOMList != null && subBOMList.size() > 0) {
						resultList.addAll(subBOMList);
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * Get the Buttom BOM item layer
	 * 
	 * @param rawBomItemList
	 * @return
	 */
	public int getFooterBOMItemLayer(List<ServiceEntityNode> rawBomItemList) {
		if (rawBomItemList == null || rawBomItemList.size() == 0) {
			return 0;
		}
		int buttomLayer = 1;
		for (ServiceEntityNode seNode : rawBomItemList) {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) seNode;
			if (billOfMaterialItem.getLayer() > buttomLayer) {
				buttomLayer = billOfMaterialItem.getLayer();
			}
		}
		return buttomLayer;
	}

	/**
	 * Filter the online BOM list by next layer
	 * 
	 * @param parentItemUUID
	 * @param rawBomItemList
	 * @return
	 */
	public List<ServiceEntityNode> filterSubBOMItemList(String parentItemUUID,
			List<ServiceEntityNode> rawBomItemList) {
		if (rawBomItemList == null || rawBomItemList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (ServiceEntityNode seNode : rawBomItemList) {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) seNode;
			if (billOfMaterialItem.getRefParentItemUUID()
					.equals(parentItemUUID)) {
				resultList.add(billOfMaterialItem);
			}
		}
		return resultList;
	}

	/**
	 * Filter ALL the sub BOM item list until the button
	 * 
	 * @param parentItemUUID
	 * @param rawBomItemList
	 * @return
	 */
	@Deprecated // TODO to be replaced by DocumentSpecifier filter method
	public List<ServiceEntityNode> filterAllSubBOMItemList(
			String parentItemUUID, List<ServiceEntityNode> rawBomItemList) {
		List<ServiceEntityNode> directSubBomItemList = filterSubBOMItemList(
				parentItemUUID, rawBomItemList);
		if (directSubBomItemList == null || directSubBomItemList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		resultList.addAll(directSubBomItemList);
		for (ServiceEntityNode seNode : directSubBomItemList) {
			List<ServiceEntityNode> subBomItemList = filterAllSubBOMItemList(
					seNode.getUuid(), rawBomItemList);
			if (subBomItemList != null && subBomItemList.size() > 0) {
				resultList.addAll(subBomItemList);
			}
		}
		return resultList;
	}

	/**
	 * Filter the online BOM list by layer
	 * 
	 * @param layer
	 * @param rawBomItemList
	 * @return
	 */
	public List<ServiceEntityNode> filterBOMItemListByLayer(int layer,
			List<ServiceEntityNode> rawBomItemList) {
		if (rawBomItemList == null || rawBomItemList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawBomItemList) {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) seNode;
			if (billOfMaterialItem.getLayer() == layer) {
				resultList.add(billOfMaterialItem);
			}
		}
		return resultList;
	}

	/**
	 * [Internal method] Get the list of buttom BOM item list, these item is raw
	 * material which doesn't has sub BOM sub material or has other BOM model
	 * related
	 * 
	 * @param rawBomItemList
	 *            : Raw, all of the BOM Item list, [pay attention]: these BOM
	 *            list should already finished BOM list merge job
	 * @return
	 */
	public List<ServiceEntityNode> getFooterBomItemList(
			List<ServiceEntityNode> rawBomItemList) {
		if (rawBomItemList == null || rawBomItemList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawBomItemList) {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) seNode;
			List<ServiceEntityNode> directSubBomItemList = filterSubBOMItemList(
					billOfMaterialItem.getUuid(), rawBomItemList);
			if (directSubBomItemList == null
					|| directSubBomItemList.size() == 0) {
				resultList.add(billOfMaterialItem);
			}
		}
		return resultList;
	}

	/**
	 * Filter the online BOM Item by UUID
	 * 
	 * @param uuid
	 * @param rawBomItemList
	 * @return
	 */
	public BillOfMaterialItem filterBOMItemByUUID(String uuid,
			List<ServiceEntityNode> rawBomItemList) {
		if (ServiceCollectionsHelper.checkNullList(rawBomItemList)) {
			return null;
		}
		for (ServiceEntityNode seNode : rawBomItemList) {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) seNode;
			if (uuid.equals(billOfMaterialItem.getUuid())) {
				return billOfMaterialItem;
			}
		}
		return null;
	}

	/**
	 * Filter out all the upstream BOM item list in one path until the first
	 * layer
	 * 
	 * @param billOfMaterialItem
	 * @param rawBomItemList
	 * @return
	 */
	public List<ServiceEntityNode> filterUpstreamBOMItemList(
			BillOfMaterialItem billOfMaterialItem,
			List<ServiceEntityNode> rawBomItemList) {
		if (rawBomItemList == null || rawBomItemList.size() == 0) {
			return null;
		}
		if (billOfMaterialItem.getLayer() <= 1) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		BillOfMaterialItem parentItem = filterBOMItemByUUID(
				billOfMaterialItem.getRefParentItemUUID(), rawBomItemList);
		if (parentItem != null) {
			resultList.add(parentItem);
			List<ServiceEntityNode> parentItemList = filterUpstreamBOMItemList(
					parentItem, rawBomItemList);
			if (parentItemList != null && parentItemList.size() > 0) {
				resultList.addAll(parentItemList);
			}
		}
		return resultList;
	}

	public List<ServiceEntityNode> getFirstLayerSubItemList(String baseUUID,
			String client) throws ServiceEntityConfigureException {
		List<ServiceBasicKeyStructure> keyList = new ArrayList<ServiceBasicKeyStructure>();
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
		key1.setKeyName(IServiceEntityNodeFieldConstant.ROOTNODEUUID);
		key1.setKeyValue(baseUUID);
		keyList.add(key1);
		ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure();
		key2.setKeyName("layer");
		key2.setKeyValue(1);
		keyList.add(key2);
		return getEntityNodeListByKeyList(
				keyList, BillOfMaterialItem.NODENAME, client, null);
	}

	public List<ServiceEntityNode> filterFirstLayerSubItemListOnline(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityConfigureException {
		if (rawList == null || rawList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawList) {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) seNode;
			if (billOfMaterialItem != null
					&& billOfMaterialItem.getLayer() == 1) {
				resultList.add(billOfMaterialItem);
			}
		}
		return resultList;
	}

	protected void throwMaterialNestedUnion(String skuUUID, String client)
			throws ServiceEntityConfigureException, BillOfMaterialException {
		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(skuUUID,
						IServiceEntityNodeFieldConstant.UUID,
						MaterialStockKeepUnit.NODENAME, client, null);
		if (materialStockKeepUnit != null) {
			throw new BillOfMaterialException(
					BillOfMaterialException.PARA_NESTEDITEM_UPLAYER,
					materialStockKeepUnit.getId() + "-"
							+ materialStockKeepUnit.getName());
		} else {
			throw new BillOfMaterialException(
					BillOfMaterialException.PARA_NO_MATERIAL, skuUUID);
		}
	}

	/**
	 * Logic to check BOM Material Nested problem from current Item To root
	 * 
	 * @param billOfMaterialItem
	 * @param rawBOMItemList
	 * @throws ServiceEntityConfigureException
	 * @throws BillOfMaterialException
	 */
	public void checkItemMaterialNestedUpstream(
			BillOfMaterialItem billOfMaterialItem,
			List<ServiceEntityNode> rawBOMItemList)
			throws ServiceEntityConfigureException, BillOfMaterialException {
		if (billOfMaterialItem.getLayer() == 0) {
			return;
		}
		BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) getEntityNodeByKey(
				billOfMaterialItem.getRootNodeUUID(),
				IServiceEntityNodeFieldConstant.UUID,
				BillOfMaterialOrder.NODENAME, billOfMaterialItem.getClient(),
				null);
		// Compare with root material and item material
		if (billOfMaterialItem.getRefMaterialSKUUUID() == null) {
			throw new BillOfMaterialException(
					BillOfMaterialException.PARA_NO_MATERIAL,
					billOfMaterialItem.getId());
		}
		if (billOfMaterialOrder.getRefMaterialSKUUUID() == null) {
			throw new BillOfMaterialException(
					BillOfMaterialException.PARA_NO_MATERIAL,
					billOfMaterialOrder.getId());
		}
		if (billOfMaterialOrder.getRefMaterialSKUUUID().equals(
				billOfMaterialOrder.getRefMaterialSKUUUID())) {
			throwMaterialNestedUnion(
					billOfMaterialOrder.getRefMaterialSKUUUID(),
					billOfMaterialItem.getClient());
		}
		if (billOfMaterialItem.getLayer() > 1) {
			List<ServiceEntityNode> upStreamList = filterUpstreamBOMItemList(
					billOfMaterialItem, rawBOMItemList);
			if (upStreamList != null && upStreamList.size() > 0) {
				for (ServiceEntityNode seNode : upStreamList) {
					BillOfMaterialItem upItem = (BillOfMaterialItem) seNode;
					if (upItem.getRefMaterialSKUUUID() == null) {
						throw new BillOfMaterialException(
								BillOfMaterialException.PARA_NO_MATERIAL,
								upItem.getId());
					}
					if (billOfMaterialOrder.getRefMaterialSKUUUID().equals(
							upItem.getRefMaterialSKUUUID())) {
						throwMaterialNestedUnion(
								upItem.getRefMaterialSKUUUID(),
								upItem.getClient());
					}
				}
			}
		}
	}

	/**
	 * Logic to check BOM Material Nested problem from current Item To root by
	 * checking all the sub material from this SUB BOM model
	 * 
	 * @param billOfMaterialItem
	 * @param rawBOMItemList
	 * @throws ServiceEntityConfigureException
	 * @throws BillOfMaterialException
	 */
	public void checkItemSubBomNestedUpstream(
			BillOfMaterialItem billOfMaterialItem,
			List<ServiceEntityNode> rawBOMItemList)
			throws ServiceEntityConfigureException, BillOfMaterialException {
		if (billOfMaterialItem.getLayer() == 0) {
			return;
		}
		BillOfMaterialOrder subBOMOrder = (BillOfMaterialOrder) getEntityNodeByKey(
				billOfMaterialItem.getRefSubBOMUUID(),
				IServiceEntityNodeFieldConstant.UUID,
				BillOfMaterialOrder.NODENAME, billOfMaterialItem.getClient(),
				null);
		if (subBOMOrder == null) {
			throw new BillOfMaterialException(
					BillOfMaterialException.TYPE_SYSTEM_WRONG);
		}
		List<ServiceEntityNode> allSubBOMItemList = getEntityNodeListByKey(
				subBOMOrder.getUuid(),
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				BillOfMaterialItem.NODENAME, billOfMaterialItem.getClient(),
				null);
		if (allSubBOMItemList == null || allSubBOMItemList.size() == 0) {
			return;
		}
		BillOfMaterialItem dummyBillOfMaterialItem = (BillOfMaterialItem) billOfMaterialItem
				.clone();
		for (ServiceEntityNode seNode : allSubBOMItemList) {
			BillOfMaterialItem subBillOfMaterialItem = (BillOfMaterialItem) seNode;
			dummyBillOfMaterialItem.setRefMaterialSKUUUID(subBillOfMaterialItem
					.getRefMaterialSKUUUID());
			checkItemMaterialNestedUpstream(dummyBillOfMaterialItem,
					rawBOMItemList);
		}
	}

	public void convBillOfMaterialOrderToUI(
			BillOfMaterialOrder billOfMaterialOrder,
			BillOfMaterialOrderUIModel billOfMaterialOrderUIModel)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		convBillOfMaterialOrderToUI(billOfMaterialOrder, billOfMaterialOrderUIModel, null);
	}

	public static String formatVersion(int versionNumber, int patchNumber){
		return versionNumber + "." + patchNumber;
	}

	public void convBillOfMaterialOrderToUI(
			BillOfMaterialOrder billOfMaterialOrder,
			BillOfMaterialOrderUIModel billOfMaterialOrderUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		if (billOfMaterialOrder != null) {
			docFlowProxy.convDocumentToUI(billOfMaterialOrder, billOfMaterialOrderUIModel, logonInfo);
			billOfMaterialOrderUIModel.setNote(billOfMaterialOrder.getNote());
			billOfMaterialOrderUIModel.setItemCategory(billOfMaterialOrder
					.getItemCategory());
			billOfMaterialOrderUIModel.setLeadTimeCalMode(billOfMaterialOrder
					.getLeadTimeCalMode());
			if(logonInfo != null){
				Map<Integer, String> statusMap = this.initStatusMap(logonInfo.getLanguageCode());
				billOfMaterialOrderUIModel.setStatusValue(statusMap
						.get(billOfMaterialOrder.getStatus()));
			}
			billOfMaterialOrderUIModel.setRefTemplateUUID(billOfMaterialOrder.getRefTemplateUUID());
			billOfMaterialOrderUIModel.setVersionNumber(billOfMaterialOrder.getVersionNumber());
			billOfMaterialOrderUIModel.setPatchNumber(billOfMaterialOrder.getPatchNumber());
			billOfMaterialOrderUIModel.setGenVersion(formatVersion(billOfMaterialOrder.getVersionNumber(),
					billOfMaterialOrder.getPatchNumber()));
			billOfMaterialOrderUIModel.setStatus(billOfMaterialOrder
					.getStatus());
			billOfMaterialOrderUIModel
					.setRefMaterialSKUUUID(billOfMaterialOrder
							.getRefMaterialSKUUUID());
			billOfMaterialOrderUIModel.setAmount(ServiceEntityDoubleHelper
					.trancateDoubleScale4(billOfMaterialOrder.getAmount()));
			billOfMaterialOrderUIModel.setRefUnitUUID(billOfMaterialOrder
					.getRefUnitUUID());
			billOfMaterialOrderUIModel.setRefWocUUID(billOfMaterialOrder
					.getRefWocUUID());
			try {
				String refUnitName = materialStockKeepUnitManager
						.getRefUnitName(
								billOfMaterialOrder.getRefMaterialSKUUUID(),
								billOfMaterialOrder.getRefUnitUUID(),
								billOfMaterialOrder.getClient());
				billOfMaterialOrderUIModel.setRefUnitName(refUnitName);
			} catch (MaterialException e) {
				// just skip
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "refUnitName"));
			} catch (ServiceEntityConfigureException e) {
				// just skip
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "refUnitName"));
			}
			try {
				String amountLabel = materialStockKeepUnitManager
						.getAmountLabel(
								billOfMaterialOrder.getRefMaterialSKUUUID(),
								billOfMaterialOrder.getRefUnitUUID(),
								billOfMaterialOrder.getAmount(),
								billOfMaterialOrder.getClient());
				billOfMaterialOrderUIModel.setAmountLabel(amountLabel);
			} catch (MaterialException e) {
				// just skip
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "amountLabel"));
			}
		}
	}

	public void convUIToBillOfMaterialOrder(
			BillOfMaterialOrderUIModel billOfMaterialOrderUIModel,
			BillOfMaterialOrder rawEntity) {
		docFlowProxy.convUIToDocument(billOfMaterialOrderUIModel, rawEntity);
		rawEntity.setNote(billOfMaterialOrderUIModel.getNote());
		rawEntity.setRefMaterialSKUUUID(billOfMaterialOrderUIModel
				.getRefMaterialSKUUUID());
		rawEntity.setId(billOfMaterialOrderUIModel.getId());
		rawEntity.setNote(billOfMaterialOrderUIModel.getNote());
		rawEntity.setAmount(billOfMaterialOrderUIModel.getAmount());
		rawEntity.setRefUnitUUID(billOfMaterialOrderUIModel.getRefUnitUUID());
		rawEntity.setLeadTimeCalMode(billOfMaterialOrderUIModel
				.getLeadTimeCalMode());
		rawEntity.setRefRouteOrderUUID(billOfMaterialOrderUIModel
				.getRefRouteOrderUUID());
		rawEntity.setRefWocUUID(billOfMaterialOrderUIModel.getRefWocUUID());
	}
	
	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			BillOfMaterialOrderUIModel billOfMaterialOrderUIModel) {
		convMaterialStockKeepUnitToUI(materialStockKeepUnit, billOfMaterialOrderUIModel, null);
	}

	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			BillOfMaterialOrderUIModel billOfMaterialOrderUIModel, LogonInfo logonInfo) {
		if (materialStockKeepUnit != null) {
			billOfMaterialOrderUIModel
					.setRefMaterialSKUId(materialStockKeepUnit.getId());
			billOfMaterialOrderUIModel
					.setRefMaterialSKUName(materialStockKeepUnit.getName());
			billOfMaterialOrderUIModel.setSupplyType(materialStockKeepUnit
					.getSupplyType());
			billOfMaterialOrderUIModel
					.setRefPackageStandard(materialStockKeepUnit
							.getPackageStandard());
			if(logonInfo != null){
				try {
					Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager
							.initSupplyTypeMap(logonInfo.getLanguageCode());
					billOfMaterialOrderUIModel.setSupplyTypeValue(supplyTypeMap
							.get(materialStockKeepUnit.getSupplyType()));
				} catch (ServiceEntityInstallationException e) {
					// log error and continue
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
				}
			}
			
		}
	}

	public void convProcessRouteOrderToUI(ProcessRouteOrder processRouteOrder,
			BillOfMaterialOrderUIModel billOfMaterialOrderUIModel) {
		if (processRouteOrder != null) {
			billOfMaterialOrderUIModel.setRefRouteOrderUUID(processRouteOrder
					.getUuid());
			billOfMaterialOrderUIModel.setRefRouteOrderId(processRouteOrder
					.getId());
			billOfMaterialOrderUIModel.setRefRouteOrderName(processRouteOrder
					.getId());
		}
	}

	public void convProdWorkCenterToUI(ProdWorkCenter prodWorkCenter,
			BillOfMaterialOrderUIModel billOfMaterialOrderUIModel) {
		if (prodWorkCenter != null) {
			billOfMaterialOrderUIModel.setRefWocName(prodWorkCenter.getName());
			billOfMaterialOrderUIModel.setRefWocId(prodWorkCenter.getId());
		}
	}

	public void convBillOfMaterialTemplateToUI(BillOfMaterialTemplate billOfMaterialTemplate,
											   BillOfMaterialOrderUIModel billOfMaterialOrderUIModel){
		billOfMaterialOrderUIModel.setRefTemplateId(billOfMaterialTemplate.getId());
		billOfMaterialOrderUIModel.setRefTemplateName(billOfMaterialTemplate.getName());
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return billOfMaterialOrderSearchProxy;
	}

	public boolean checkBlockExecutionByDocflow(int actionCode, String uuid, ServiceJSONRequest serviceJSONRequest,
												SerialLogonInfo serialLogonInfo){
		if(actionCode == BillOfMaterialOrderActionNode.DOC_ACTION_APPROVE){
			return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
					IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER, uuid, serviceJSONRequest, serialLogonInfo,
					actionCode);
		}
		if(actionCode == BillOfMaterialOrderActionNode.DOC_ACTION_REJECT_APPROVE){
			return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
					IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER, uuid,serviceJSONRequest, serialLogonInfo,
					actionCode);
		}
		if(actionCode == BillOfMaterialOrderActionNode.DOC_ACTION_REVOKE_SUBMIT){
			serviceFlowRuntimeEngine.clearInvolveProcessIns(
					IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER,uuid);
			return true;
		}
		return true;
	}

	public void submitFlow(BillOfMaterialOrderServiceUIModel billOfMaterialOrderServiceUIModel,
						   SerialLogonInfo serialLogonInfo) throws ServiceFlowRuntimeException {
		String uuid = billOfMaterialOrderServiceUIModel.getBillOfMaterialOrderUIModel().getUuid();
		ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara =
				new ServiceFlowRuntimeEngine.ServiceFlowInputPara(billOfMaterialOrderServiceUIModel,
						IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER, uuid,
						BillOfMaterialOrderActionNode.DOC_ACTION_APPROVE, serialLogonInfo);
		serviceFlowRuntimeEngine.submitFlow(serviceFlowInputPara);
	}

	@Override
	public void exeFlowActionEnd(int documentType, String uuid, int actionCode, ServiceJSONRequest serviceJSONRequest
			,  SerialLogonInfo serialLogonInfo){
		try {
			BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
					BillOfMaterialOrder.NODENAME, serialLogonInfo.getClient(), null);
			BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel = (BillOfMaterialOrderServiceModel) loadServiceModule(BillOfMaterialOrderServiceModel.class,
					billOfMaterialOrder, billOfMaterialOrderServiceUIModelExtension);
			billOfMaterialOrderServiceModel.setServiceJSONRequest(serviceJSONRequest);
			if(actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE){
				this.activeService(billOfMaterialOrderServiceModel, serialLogonInfo.getRefUserUUID(),
						serialLogonInfo.getHomeOrganizationUUID());
			}
			if(actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE){
				this.rejectApproveService(billOfMaterialOrderServiceModel, serialLogonInfo.getRefUserUUID(),
						serialLogonInfo.getHomeOrganizationUUID());
			}
		} catch (ServiceEntityConfigureException | ServiceModuleProxyException e) {
			logger.error("Failed during bill of material order processing", e);
		}
	}
}
