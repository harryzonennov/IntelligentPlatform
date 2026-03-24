package com.company.IntelligentPlatform.logistics.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.repository.QualityInspectOrderRepository;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.QualityInspectOrderConfigureProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductException;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SimpleSEMessageResponse;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import java.time.ZoneId;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class QualityInspectOrderManager extends ServiceEntityManager {

	public static final String METHOD_ConvWarehouseToUI = "convWarehouseToUI";

	public static final String METHOD_ConvWarehouseAreaToUI = "convWarehouseAreaToUI";

	public static final String METHOD_ConvWarehouseAreaToItemUI = "convWarehouseAreaToItemUI";

	public static final String METHOD_ConvRefWasteWarehouseToItemUI = "convRefWasteWarehouseToItemUI";

	public static final String METHOD_ConvRefWasteWarehouseAreaToItemUI = "convRefWasteWarehouseAreaToItemUI";

	public static final String METHOD_ConvRefRegisteredProductToUI = "convRefRegisteredProductToUI";

	public static final String METHOD_ConvQualityInspectOrderToUI = "convQualityInspectOrderToUI";

	public static final String METHOD_ConvUIToQualityInspectOrder = "convUIToQualityInspectOrder";

	public static final String METHOD_ConvReservedDocumentToUI = "convReservedDocumentToUI";

	private Map<String, Map<Integer, String>> checkStatusMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> categoryMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> inspectTypeMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	@Autowired
	protected QualityInspectOrderRepository qualityInspectOrderDAO;

	@Autowired
	protected QualityInspectOrderConfigureProxy qualityInspectOrderConfigureProxy;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected StandardPriorityProxy standardPriorityProxy;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected RegisteredProductManager registeredProductManager;

	@Autowired
	protected QualityInspectOrderIdHelper qualityInspectOrderIdHelper;

	@Autowired
	protected QualityInspectOrderServiceUIModelExtension qualityInspectOrderServiceUIModelExtension;

	@Autowired
	protected QualityInspectMatItemServiceUIModelExtension qualityInspectMatItemServiceUIModelExtension;

	@Autowired
	protected QualityInspectOrderSearchProxy qualityInspectOrderSearchProxy;

	@Autowired
	protected SplitMatItemProxy splitMatItemProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected QualityInspectOrderActionExecutionProxy qualityInspectOrderActionExecutionProxy;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SerialIdDocumentProxy serialIdDocumentProxy;

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		QualityInspectOrder qualityInspectOrder = (QualityInspectOrder) super
				.newRootEntityNode(client);
		String qualityInspectOrderId = qualityInspectOrderIdHelper
				.genDefaultId(client);
		qualityInspectOrder.setId(qualityInspectOrderId);
		return qualityInspectOrder;
	}


	/**
	 * Calculate differential amount by all sample amount and failed amount
	 * 
	 * @param qualityInspectMatItem
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 * @throws QualityInspectException
	 */
	public StorageCoreUnit calculateSuccessAmount(
			QualityInspectMatItem qualityInspectMatItem)
			throws MaterialException, ServiceEntityConfigureException,
			QualityInspectException {
		StorageCoreUnit storageCoreUnit = new StorageCoreUnit();
		storageCoreUnit.setAmount(qualityInspectMatItem.getSampleAmount());
		storageCoreUnit.setRefUnitUUID(qualityInspectMatItem.getRefUnitUUID());
		storageCoreUnit.setRefMaterialSKUUUID(qualityInspectMatItem
				.getRefMaterialSKUUUID());
		StorageCoreUnit failRequest = new StorageCoreUnit();
		failRequest.setAmount(qualityInspectMatItem.getFailAmount());
		failRequest.setRefUnitUUID(qualityInspectMatItem.getFailRefUnitUUID());
		failRequest.setRefMaterialSKUUUID(qualityInspectMatItem
				.getRefMaterialSKUUUID());
		StorageCoreUnit resultUnit = materialStockKeepUnitManager
				.mergeStorageUnitCore(storageCoreUnit, failRequest,
						StorageCoreUnit.OPERATOR_MINUS,
						qualityInspectMatItem.getClient());
		if (resultUnit.getAmount() < 0) {
			String labelAmount = materialStockKeepUnitManager.getAmountLabel(
					failRequest.getRefMaterialSKUUUID(),
					failRequest.getRefUnitUUID(), failRequest.getAmount(),
					qualityInspectMatItem.getClient());
			throw new QualityInspectException(
					QualityInspectException.PARA_FAIL_OVERAMOUNT, labelAmount);
		}
		return resultUnit;
	}


	public void postUpdateServiceUIModel(QualityInspectOrderServiceUIModel qualityInspectOrderServiceUIModel,
										 QualityInspectOrderServiceModel qualityInspectOrderServiceModel) throws ServiceEntityConfigureException {
		List<QualityInspectMatItemUIModel> qualityInspectMatItemUIModelList =
				ServiceCollectionsHelper.parseToUINodeList(qualityInspectOrderServiceUIModel.getQualityInspectMatItemUIModelList(),
						QualityInspectMatItemServiceUIModel::getQualityInspectMatItemUIModel);
		List<ServiceEntityNode> qualityInspectMatItemList = ServiceCollectionsHelper.parseToSENodeList(qualityInspectOrderServiceModel.getQualityInspectMatItemList(),
				QualityInspectMatItemServiceModel::getQualityInspectMatItem);
		serialIdDocumentProxy.updateSerialIdMetaToDocumentWrapper(qualityInspectMatItemUIModelList, qualityInspectMatItemList);
	}

	/**
	 * Entrance method to post update quality inspect order service model
	 *
	 * @param qualityInspectOrderServiceModel
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void postUpdateQualityInspectOrder(QualityInspectOrderServiceModel qualityInspectOrderServiceModel){
		/*
		 * [Step1] Calculate the Default Quality Inspect Order Status
		 */
		QualityInspectOrder qualityInspectOrder = qualityInspectOrderServiceModel.getQualityInspectOrder();
      if(qualityInspectOrder.getCheckStatus() == QualityInspectOrder.CHECKSTATUS_INITIAL){
			List<QualityInspectMatItemServiceModel> qualityInspectMatItemServiceModelList = qualityInspectOrderServiceModel.getQualityInspectMatItemList();
			if (ServiceCollectionsHelper.checkNullList(qualityInspectMatItemServiceModelList)) {
				return;
			}
			List<ServiceEntityNode> qualityInspectMatItemList = new ArrayList<>();
			qualityInspectMatItemServiceModelList.forEach(serviceModel->{
				ServiceCollectionsHelper.mergeToList(qualityInspectMatItemList, serviceModel.getQualityInspectMatItem());
			});
			int defCheckStatus = calDefInspectOrderStatus(qualityInspectMatItemList);
			qualityInspectOrder.setCheckStatus(defCheckStatus);
		}
	}

	/**
	 * Logic to Calculate default inspect order check result by traverse all the sub inspect mat item list
	 * @param qualityInspectMatItemList
	 * @return
	 */
	public int calDefInspectOrderStatus(List<ServiceEntityNode> qualityInspectMatItemList){
		if(ServiceCollectionsHelper.checkNullList(qualityInspectMatItemList)){
			return QualityInspectOrder.CHECKSTATUS_INITIAL;
		}
		List<ServiceEntityNode> passedMatItemList = new ArrayList<>();
		List<ServiceEntityNode> nonPassedMatItemList = new ArrayList<>();
		List<ServiceEntityNode> partPassedMatItemList = new ArrayList<>();
		List<ServiceEntityNode> initialMatItemList = new ArrayList<>();
		for (ServiceEntityNode seNode : qualityInspectMatItemList) {
			QualityInspectMatItem qualityInspectMatItem = (QualityInspectMatItem) seNode;
			if(qualityInspectMatItem.getItemCheckStatus() == QualityInspectOrder.CHECKSTATUS_FULLPASS){
				ServiceCollectionsHelper.mergeToList(passedMatItemList, qualityInspectMatItem);
			}
			if(qualityInspectMatItem.getItemCheckStatus() == QualityInspectOrder.CHECKSTATUS_NOTPASS){
				ServiceCollectionsHelper.mergeToList(nonPassedMatItemList, qualityInspectMatItem);
			}
			if(qualityInspectMatItem.getItemCheckStatus() == QualityInspectOrder.CHECKSTATUS_PARTPASS){
				ServiceCollectionsHelper.mergeToList(partPassedMatItemList, qualityInspectMatItem);
			}
			if(qualityInspectMatItem.getItemCheckStatus() == QualityInspectOrder.CHECKSTATUS_INITIAL){
				ServiceCollectionsHelper.mergeToList(initialMatItemList, qualityInspectMatItem);
			}
		}
		/*
		 * [Step2] Single Case
		 */
		if(passedMatItemList.size() == qualityInspectMatItemList.size()){
			return QualityInspectOrder.CHECKSTATUS_FULLPASS;
		}
		if(nonPassedMatItemList.size() == qualityInspectMatItemList.size()){
			return QualityInspectOrder.CHECKSTATUS_NOTPASS;
		}
		if(partPassedMatItemList.size() == qualityInspectMatItemList.size()){
			return QualityInspectOrder.CHECKSTATUS_PARTPASS;
		}
		if(initialMatItemList.size() == qualityInspectMatItemList.size()){
			return QualityInspectOrder.CHECKSTATUS_INITIAL;
		}
		/*
		 * [Step3] Mixed Case
		 */
		if(passedMatItemList.size() > 0 && passedMatItemList.size() < qualityInspectMatItemList.size()){
			if(nonPassedMatItemList.size() > 0 ){
				return QualityInspectOrder.CHECKSTATUS_NOTPASS;
			}
			if(partPassedMatItemList.size() > 0){
				return QualityInspectOrder.CHECKSTATUS_PARTPASS;
			}
			if(initialMatItemList.size() > 0){
				return QualityInspectOrder.CHECKSTATUS_INITIAL;
			}
		}

		if(nonPassedMatItemList.size() > 0 && nonPassedMatItemList.size() < qualityInspectMatItemList.size()){

			return QualityInspectOrder.CHECKSTATUS_NOTPASS;
		}
		return QualityInspectOrder.CHECKSTATUS_INITIAL;
	}


	public void startInspectProcess(
			QualityInspectOrderServiceModel qualityInspectOrderServiceModel)
			throws ServiceModuleProxyException, ServiceEntityConfigureException, QualityInspectException {
		QualityInspectOrder qualityInspectOrder = qualityInspectOrderServiceModel
				.getQualityInspectOrder();
		boolean checkStart = checkForStart(qualityInspectOrderServiceModel);
		if(!checkStart){
			// can't set start
			throw new QualityInspectException(QualityInspectException.TYPE_CANT_START);
		}
		qualityInspectOrder.setStatus(QualityInspectOrder.STATUS_INPROCESS);
		qualityInspectOrder.setCheckDate(java.time.LocalDate.now());
	}

	public boolean checkForStart(QualityInspectOrderServiceModel qualityInspectOrderServiceModel) throws ServiceEntityConfigureException, QualityInspectException {
		QualityInspectOrder qualityInspectOrder = qualityInspectOrderServiceModel.getQualityInspectOrder();
		int checkSplitMan = splitMatItemProxy.getSplitRegProdMandatory(qualityInspectOrder.getClient());
		if(checkSplitMan != StandardSwitchProxy.SWITCH_ON){
			return true;
		}
		List<QualityInspectMatItemServiceModel> qualityInspectMatItemList = qualityInspectOrderServiceModel
				.getQualityInspectMatItemList();
		if(ServiceCollectionsHelper.checkNullList(qualityInspectMatItemList)){
			// this should not happen
			return true;
		}
		List<ServiceEntityNode> qualityDocMatItemList =
				qualityInspectMatItemList.stream().map(QualityInspectMatItemServiceModel::getQualityInspectMatItem).collect(Collectors.toList());
		Map<String, List<SerialIdDocumentProxy.DocMatItemMaterialUnion>> docItemUnionListMap =
				serialIdDocumentProxy.buildDocItemUnionListMap(qualityDocMatItemList, true);
		if(docItemUnionListMap != null && !docItemUnionListMap.isEmpty()){
			throw new QualityInspectException(QualityInspectException.PARA_EMPTY_SERIALID);
		}
		return true;
	}

	protected void setCompleteQualityInspectMatItem(
			QualityInspectMatItem qualityInspectMatItem, SerialLogonInfo serialLogonInfo)
			throws ServiceEntityConfigureException {
		Map<Integer, Integer> inspectStatusToRegProductStatusMap = inspectStatusToRegProductStatusMap();
		MaterialStockKeepUnit materialStockKeepUnit = getReferenceMaterialSKU(
				qualityInspectMatItem.getRefMaterialSKUUUID(),
				qualityInspectMatItem.getClient());
		if (materialStockKeepUnit != null
				&& RegisteredProductManager.checkRegisteredProduct(materialStockKeepUnit)) {
			// Active Register product
			try {
				RegisteredProduct registeredProduct = (RegisteredProduct) materialStockKeepUnit;
				int targetRegProductStatus = inspectStatusToRegProductStatusMap
						.get(qualityInspectMatItem.getItemCheckStatus());
				if (targetRegProductStatus == RegisteredProduct.TRACESTATUS_ACTIVE) {
					// TODO use standard action proxy here.
					registeredProductManager
							.activeRegisterProduct(
									registeredProduct,
									IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER,
									qualityInspectMatItem.getUuid(), null,
									serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID(),
									serialLogonInfo.getLanguageCode());
				}
				if (targetRegProductStatus == RegisteredProduct.TRACESTATUS_ARCHIVE) {
					// TODO use standard action proxy here.
//					registeredProductManager
//							.archiveRegisterProduct(
//									registeredProduct,
//									IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER,
//									qualityInspectMatItem.getUuid(), null,
//									serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
				}
			} catch (RegisteredProductException
					| ServiceEntityInstallationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected MaterialStockKeepUnit getReferenceMaterialSKU(String uuid,
			String client) throws ServiceEntityConfigureException {
		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
						RegisteredProduct.NODENAME, client, null);
		return materialStockKeepUnit;
	}

	/**
	 * Logic to get proper identifier for each Inspect Material item
	 * 
	 * @param qualityInspectMatItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public String getInspectMatItemIndentifier(
			QualityInspectMatItem qualityInspectMatItem)
			throws ServiceEntityConfigureException {
		return docFlowProxy.getDefDocItemIdentifier(qualityInspectMatItem);
	}

	/**
	 * Quick Create Service Model for qualityInspectOrder
	 * 
	 * @param qualityInspectOrder
	 * @param qualityInspectMatItemList
	 * @return
	 */
	public ServiceModule quickCreateServiceModel(
			QualityInspectOrder qualityInspectOrder,
			List<ServiceEntityNode> qualityInspectMatItemList) {
		QualityInspectOrderServiceModel qualityInspectOrderServiceModel = new QualityInspectOrderServiceModel();
		qualityInspectOrderServiceModel
				.setQualityInspectOrder(qualityInspectOrder);
		List<QualityInspectMatItemServiceModel> qualityInspectMatItemServiceModelList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(qualityInspectMatItemList)) {
			qualityInspectMatItemList
					.forEach(rawSENode -> {
						QualityInspectMatItemServiceModel qualityInspectMatItemServiceModel = new QualityInspectMatItemServiceModel();
						qualityInspectMatItemServiceModel
								.setQualityInspectMatItem((QualityInspectMatItem) rawSENode);
						qualityInspectMatItemServiceModelList
								.add(qualityInspectMatItemServiceModel);
					});
		}
		qualityInspectOrderServiceModel
				.setQualityInspectMatItemList(qualityInspectMatItemServiceModelList);
		return qualityInspectOrderServiceModel;
	}


	public List<SimpleSEMessageResponse> preCheckSetComplete(QualityInspectOrderServiceModel qualityInspectOrderServiceModel)
			throws ServiceModuleProxyException, ServiceEntityConfigureException, QualityInspectException,
			DocActionException {
		Map<Integer, Integer> inspectStatusToRegProductStatusMap = inspectStatusToRegProductStatusMap();
		Map<Integer, Integer> targetRegProductStatusMessageLevelMap = targetRegProductStatusMessageLevelMap();
		return qualityInspectOrderActionExecutionProxy.preCheckErrorMessage(qualityInspectOrderServiceModel,
				(DocActionExecutionProxy.IGetMessageExecutor<QualityInspectMatItem, QualityInspectException>) qualityInspectMatItem -> {
					int targetRegProductStatus = inspectStatusToRegProductStatusMap
							.get(qualityInspectMatItem.getItemCheckStatus());
					int messageLevel = targetRegProductStatusMessageLevelMap
							.get(targetRegProductStatus);
					SimpleSEMessageResponse simpleSEMessageResponse = new SimpleSEMessageResponse();
					if (messageLevel == SimpleSEMessageResponse.MESSAGELEVEL_ERROR) {
						simpleSEMessageResponse
								.setMessageLevel(SimpleSEMessageResponse.MESSAGELEVEL_ERROR);
						simpleSEMessageResponse
								.setRefException(new QualityInspectException(
										QualityInspectException.PARA_CHECKITEM_INIT));
						simpleSEMessageResponse
								.setErrorCode(QualityInspectException.PARA_CHECKITEM_INIT);
						String indentifier = getInspectMatItemIndentifier(qualityInspectMatItem);
						simpleSEMessageResponse
								.setErrorParas(new String[] { indentifier });
					}
					if (messageLevel == SimpleSEMessageResponse.MESSAGELEVEL_WARN) {
						simpleSEMessageResponse
								.setMessageLevel(SimpleSEMessageResponse.MESSAGELEVEL_WARN);
						String indentifier = getInspectMatItemIndentifier(qualityInspectMatItem);
						simpleSEMessageResponse
								.setErrorParas(new String[] { indentifier });
					}
					return ServiceCollectionsHelper.asList(simpleSEMessageResponse);
				});
	}

//	/**
//	 * Core Logic to pre-check the validate if could set to complete
//	 *
//	 * @param qualityInspectOrderServiceModel
//	 * @throws ServiceModuleProxyException
//	 * @throws ServiceEntityConfigureException
//	 * @throws QualityInspectException
//	 */
//	public List<SimpleSEMessageResponse> preCheckSetComplete(
//			QualityInspectOrderServiceModel qualityInspectOrderServiceModel)
//			throws ServiceModuleProxyException, ServiceEntityConfigureException {
//		Map<Integer, Integer> inspectStatusToRegProductStatusMap = inspectStatusToRegProductStatusMap();
//		Map<Integer, Integer> targetRegProductStatusMessageLevelMap = targetRegProductStatusMessageLevelMap();
//		List<QualityInspectMatItemServiceModel> qualityInspectMatItemList = qualityInspectOrderServiceModel
//				.getQualityInspectMatItemList();
//		List<SimpleSEMessageResponse> resultList = new ArrayList<>();
//		if (!ServiceCollectionsHelper.checkNullList(qualityInspectMatItemList)) {
//			for (QualityInspectMatItemServiceModel qualityInspectMatItemServiceModel : qualityInspectMatItemList) {
//				QualityInspectMatItem qualityInspectMatItem = qualityInspectMatItemServiceModel
//						.getQualityInspectMatItem();
//				int targetRegProductStatus = inspectStatusToRegProductStatusMap
//						.get(qualityInspectMatItem.getItemCheckStatus());
//				int messageLevel = targetRegProductStatusMessageLevelMap
//						.get(targetRegProductStatus);
//				ServiceMessageResponseExtension simpleSEMessageResponse = new ServiceMessageResponseExtension();
//				if (messageLevel == SimpleSEMessageResponse.MESSAGELEVEL_ERROR) {
//					simpleSEMessageResponse
//							.setMessageLevel(SimpleSEMessageResponse.MESSAGELEVEL_ERROR);
//					simpleSEMessageResponse
//							.setRefException(new QualityInspectException(
//									QualityInspectException.PARA_CHECKITEM_INIT));
//					simpleSEMessageResponse
//							.setErrorCode(QualityInspectException.PARA_CHECKITEM_INIT);
//					String indentifier = getInspectMatItemIndentifier(qualityInspectMatItem);
//					simpleSEMessageResponse
//							.setErrorParas(new String[] { indentifier });
//					resultList.add(simpleSEMessageResponse);
//				}
//				if (messageLevel == SimpleSEMessageResponse.MESSAGELEVEL_WARN) {
//					simpleSEMessageResponse
//							.setMessageLevel(SimpleSEMessageResponse.MESSAGELEVEL_WARN);
//					String indentifier = getInspectMatItemIndentifier(qualityInspectMatItem);
//					simpleSEMessageResponse
//							.setErrorParas(new String[] { indentifier });
//					resultList.add(simpleSEMessageResponse);
//				}
//			}
//		}
//		return resultList;
//	}

	/**
	 * TODO to migrate this logic in configure table in the future? Core Logic &
	 * Mapping about Inspect status to target registered product status. [Pay
	 * attention] If target registered product status is ["TRACESTATUS_INIT"],
	 * Error message will raise
	 */
	public static Map<Integer, Integer> inspectStatusToRegProductStatusMap() {
		Map<Integer, Integer> resultMap = new HashMap<>();
		resultMap.put(QualityInspectOrder.CHECKSTATUS_INITIAL,
				RegisteredProduct.TRACESTATUS_INIT);
		resultMap.put(QualityInspectOrder.CHECKSTATUS_FULLPASS,
				RegisteredProduct.TRACESTATUS_ACTIVE);
		resultMap.put(QualityInspectOrder.CHECKSTATUS_PARTPASS,
				RegisteredProduct.TRACESTATUS_ACTIVE);
		resultMap.put(QualityInspectOrder.CHECKSTATUS_NOTPASS,
				RegisteredProduct.TRACESTATUS_ARCHIVE);
		return resultMap;
	}

	public static Map<Integer, Integer> targetRegProductStatusMessageLevelMap() {
		Map<Integer, Integer> resultMap = new HashMap<>();
		resultMap.put(RegisteredProduct.TRACESTATUS_INIT,
				SimpleSEMessageResponse.MESSAGELEVEL_ERROR);
		resultMap.put(RegisteredProduct.TRACESTATUS_ARCHIVE,
				SimpleSEMessageResponse.MESSAGELEVEL_WARN);
		// other status:
		resultMap.put(RegisteredProduct.TRACESTATUS_ACTIVE,
				SimpleSEMessageResponse.MESSAGELEVEL_INFO);
		resultMap.put(RegisteredProduct.TRACESTATUS_INSERVICE,
				SimpleSEMessageResponse.MESSAGELEVEL_INFO);
		return resultMap;
	}


	public void convQualityInspectOrderToUI(
			QualityInspectOrder qualityInspectOrder,
			QualityInspectOrderUIModel qualityInspectOrderUIModel)
			throws ServiceEntityInstallationException {
		convQualityInspectOrderToUI(qualityInspectOrder,
				qualityInspectOrderUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convQualityInspectOrderToUI(
			QualityInspectOrder qualityInspectOrder,
			QualityInspectOrderUIModel qualityInspectOrderUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException {
		if (qualityInspectOrder != null) {
			docFlowProxy.convDocumentToUI(qualityInspectOrder,
					qualityInspectOrderUIModel, logonInfo);
			qualityInspectOrderUIModel.setReservedDocUUID(qualityInspectOrder
					.getReservedDocUUID());
			if (logonInfo != null) {
				Map<Integer, String> checkStatusMap = this
						.initCheckStatusMap(logonInfo.getLanguageCode());
				qualityInspectOrderUIModel.setCheckStatusValue(checkStatusMap
						.get(qualityInspectOrder.getCheckStatus()));
			}
			qualityInspectOrderUIModel.setCheckStatus(qualityInspectOrder
					.getCheckStatus());
			qualityInspectOrderUIModel.setGrossPrice(qualityInspectOrder
					.getGrossPrice());
			qualityInspectOrderUIModel
					.setProductionBatchNumber(qualityInspectOrder
							.getProductionBatchNumber());
			qualityInspectOrderUIModel
					.setPurchaseBatchNumber(qualityInspectOrder
							.getPurchaseBatchNumber());
			qualityInspectOrderUIModel.setCheckResult(qualityInspectOrder
					.getCheckResult());
			qualityInspectOrderUIModel.setReservedDocType(qualityInspectOrder
					.getReservedDocType());
			if(logonInfo != null){
				Map<Integer, String> documentTypeMap = this.initDocumentTypeMap(logonInfo.getLanguageCode());
				qualityInspectOrderUIModel
						.setReservedDocTypeValue(documentTypeMap
								.get(qualityInspectOrder.getReservedDocType()));
			}
			if (qualityInspectOrder.getCheckDate() != null) {
				qualityInspectOrderUIModel
						.setCheckDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(qualityInspectOrder.getCheckDate()));
			}
			if (qualityInspectOrder.getCreatedTime() != null) {
				qualityInspectOrderUIModel
						.setCreatedDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(qualityInspectOrder.getCreatedTime()));
			}
			qualityInspectOrderUIModel.setName(qualityInspectOrder.getName());
			if (logonInfo != null) {
				Map<Integer, String> checkStatusMap = this
						.initCheckStatusMap(logonInfo.getLanguageCode());
				qualityInspectOrderUIModel.setCheckStatusValue(checkStatusMap
						.get(qualityInspectOrder.getCheckStatus()));
			}
			qualityInspectOrderUIModel.setCategory(qualityInspectOrder
					.getCategory());
			if (logonInfo != null) {
				Map<Integer, String> categoryMap = this
						.initCategoryMap(logonInfo.getLanguageCode());
				qualityInspectOrderUIModel.setCategoryValue(categoryMap
						.get(qualityInspectOrder.getCategory()));
			}
			qualityInspectOrderUIModel.setId(qualityInspectOrder.getId());
			if (logonInfo != null) {
				Map<Integer, String> inspectTypeMap = this
						.initInspectTypeMap(logonInfo.getLanguageCode());
				qualityInspectOrderUIModel.setInspectTypeValue(inspectTypeMap
						.get(qualityInspectOrder.getInspectType()));
			}
			qualityInspectOrderUIModel.setInspectType(qualityInspectOrder
					.getInspectType());
			qualityInspectOrderUIModel.setNote(qualityInspectOrder.getNote());
			if (logonInfo != null) {
				Map<Integer, String> statusMap = this.initStatusMap(logonInfo
						.getLanguageCode());
				qualityInspectOrderUIModel.setStatusValue(statusMap
						.get(qualityInspectOrder.getStatus()));
			}
			qualityInspectOrderUIModel.setStatus(qualityInspectOrder
					.getStatus());

			qualityInspectOrderUIModel.setRefWarehouseUUID(qualityInspectOrder
					.getRefWarehouseUUID());
			qualityInspectOrderUIModel
					.setRefWarehouseAreaUUID(qualityInspectOrder
							.getRefWarehouseAreaUUID());
			if (logonInfo != null) {
				Map<Integer, String> priorityCodeMap = initPriorityCodeMap(logonInfo
						.getLanguageCode());
				qualityInspectOrderUIModel.setPriorityCodeValue(priorityCodeMap
						.get(qualityInspectOrder.getPriorityCode()));
			}
			qualityInspectOrderUIModel.setPriorityCode(qualityInspectOrder
					.getPriorityCode());

		}

	}

	public void convReservedDocumentToUI(ServiceEntityNode documentContent,
			QualityInspectOrderUIModel qualityInspectOrderUIModel) {
		if (documentContent != null) {
			qualityInspectOrderUIModel
					.setReservedDocId(documentContent.getId());
			qualityInspectOrderUIModel.setReservedDocName(documentContent
					.getName());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:qualityInspectOrder
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToQualityInspectOrder(
			QualityInspectOrderUIModel qualityInspectOrderUIModel,
			QualityInspectOrder rawEntity) {
		docFlowProxy.convUIToDocument(qualityInspectOrderUIModel, rawEntity);
		rawEntity.setReservedDocUUID(qualityInspectOrderUIModel
				.getReservedDocUUID());
		rawEntity.setCheckStatus(qualityInspectOrderUIModel.getCheckStatus());
		rawEntity.setCheckResult(qualityInspectOrderUIModel.getCheckResult());
		rawEntity.setUuid(qualityInspectOrderUIModel.getUuid());
		rawEntity.setReservedDocType(qualityInspectOrderUIModel
				.getReservedDocType());
		rawEntity.setProductionBatchNumber(qualityInspectOrderUIModel
				.getProductionBatchNumber());
		rawEntity.setPurchaseBatchNumber(qualityInspectOrderUIModel
				.getPurchaseBatchNumber());
		rawEntity.setClient(qualityInspectOrderUIModel.getClient());
		if (!ServiceEntityStringHelper
				.checkNullString(qualityInspectOrderUIModel.getCheckDate())) {
			try {
				rawEntity.setCheckDate(DefaultDateFormatConstant.DATE_FORMAT.parse(qualityInspectOrderUIModel.getCheckDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setGrossPrice(qualityInspectOrderUIModel.getGrossPrice());
		rawEntity.setCategory(qualityInspectOrderUIModel.getCategory());
		rawEntity.setInspectType(qualityInspectOrderUIModel.getInspectType());
		rawEntity.setRefWarehouseAreaUUID(qualityInspectOrderUIModel
				.getRefWarehouseAreaUUID());
		rawEntity.setRefWarehouseUUID(qualityInspectOrderUIModel
				.getRefWarehouseUUID());
		rawEntity.setNote(qualityInspectOrderUIModel.getNote());
		rawEntity.setPriorityCode(qualityInspectOrderUIModel.getPriorityCode());
	}

	/**
	 * Core Logic to calculate if current item is split able or not
	 * 
	 * @param qualityInspectMatItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public int calculateSplitFlag(QualityInspectMatItem qualityInspectMatItem)
			throws MaterialException, ServiceEntityConfigureException {
		String refUnitUUID = qualityInspectMatItem.getRefUnitUUID();
		StorageCoreUnit requestSplitUnit = new StorageCoreUnit(
				qualityInspectMatItem.getRefMaterialSKUUUID(), refUnitUUID,
				qualityInspectMatItem.getSampleAmount());
		return materialStockKeepUnitManager.calculateSplitFlag(
				requestSplitUnit, qualityInspectMatItem.getClient());
	}

	public Map<Integer, String> initCheckStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.checkStatusMapLan, QualityInspectOrderUIModel.class,
				"checkStatus");
	}

	public Map<Integer, String> initDocumentTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return serviceDocumentComProxy
				.getDocumentTypeMap(false, languageCode);
	}


	public Map<Integer, String> initCategoryMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.categoryMapLan, QualityInspectOrderUIModel.class,
				"category");
	}

	public Map<Integer, String> initInspectTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.inspectTypeMapLan, QualityInspectOrderUIModel.class,
				"inspectType");
	}

	public Map<Integer, String> initStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, QualityInspectOrderUIModel.class, IDocumentNodeFieldConstant.STATUS);
	}

	public Map<Integer, String> initPriorityCodeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return standardPriorityProxy.getPriorityMap(languageCode);
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(qualityInspectOrderDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(qualityInspectOrderConfigureProxy);
	}



	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convWarehouseToUI(Warehouse warehouse,
			QualityInspectOrderUIModel qualityInspectOrderUIModel) {
		if (warehouse != null) {
			qualityInspectOrderUIModel.setRefWarehouseId(warehouse.getId());
			qualityInspectOrderUIModel.setRefWarehouseName(warehouse.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convWarehouseAreaToUI(WarehouseArea warehouseArea,
			QualityInspectOrderUIModel qualityInspectOrderUIModel) {
		if (warehouseArea != null) {
			qualityInspectOrderUIModel.setRefWarehouseAreaId(warehouseArea
					.getId());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convWarehouseAreaToItemUI(WarehouseArea warehouseArea,
			QualityInspectMatItemUIModel qualityInspectMatItemUIModel) {
		if (warehouseArea != null) {
			qualityInspectMatItemUIModel.setRefWarehouseAreaId(warehouseArea
					.getId());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convRefWasteWarehouseToItemUI(Warehouse warehouse,
			QualityInspectMatItemUIModel qualityInspectMatItemUIModel) {
		if (warehouse != null) {
			qualityInspectMatItemUIModel.setRefWasteWarehouseId(warehouse
					.getId());
			qualityInspectMatItemUIModel.setRefWasteWarehouseName(warehouse
					.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convRefWasteWarehouseAreaToItemUI(WarehouseArea warehouseArea,
			QualityInspectMatItemUIModel qualityInspectMatItemUIModel) {
		if (warehouseArea != null) {
			qualityInspectMatItemUIModel.setRefWasteWareAreaId(warehouseArea
					.getId());
		}
	}


	public ServiceDocumentExtendUIModel convInspectOrderToDocExtUIModel(
			QualityInspectOrderUIModel qualityInspectOrderUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		serviceDocumentExtendUIModel.setRefUIModel(qualityInspectOrderUIModel);
		serviceDocumentExtendUIModel.setUuid(qualityInspectOrderUIModel
				.getUuid());
		serviceDocumentExtendUIModel
				.setParentNodeUUID(qualityInspectOrderUIModel
						.getParentNodeUUID());
		serviceDocumentExtendUIModel.setRootNodeUUID(qualityInspectOrderUIModel
				.getRootNodeUUID());
		serviceDocumentExtendUIModel.setId(qualityInspectOrderUIModel.getId());
		serviceDocumentExtendUIModel.setName(qualityInspectOrderUIModel
				.getName());
		serviceDocumentExtendUIModel.setStatus(qualityInspectOrderUIModel
				.getStatus());
		serviceDocumentExtendUIModel.setStatusValue(qualityInspectOrderUIModel
				.getStatusValue());
		serviceDocumentExtendUIModel
				.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER);
		if(logonInfo != null){
			serviceDocumentExtendUIModel
					.setDocumentTypeValue(serviceDocumentComProxy
							.getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER,
									logonInfo.getLanguageCode()));
		}
		// Logic to set reference data
		String referenceDate = qualityInspectOrderUIModel.getCheckDate();
		if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
			referenceDate = qualityInspectOrderUIModel.getCreatedDate();
		}
		serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		return serviceDocumentExtendUIModel;
	}

	public ServiceDocumentExtendUIModel convInspectMatItemToDocExtUIModel(
			QualityInspectMatItemUIModel qualityInspectMatItemUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		serviceDocumentExtendUIModel
				.setRefUIModel(qualityInspectMatItemUIModel);
		docFlowProxy.convDocMatItemUIToDocExtUIModel(qualityInspectMatItemUIModel,
				serviceDocumentExtendUIModel, logonInfo,
				IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER);		
		serviceDocumentExtendUIModel.setId(qualityInspectMatItemUIModel
				.getParentDocId());
		serviceDocumentExtendUIModel.setName(qualityInspectMatItemUIModel
				.getParentDocName());
		serviceDocumentExtendUIModel.setStatus(qualityInspectMatItemUIModel
				.getParentDocStatus());
		serviceDocumentExtendUIModel
				.setStatusValue(qualityInspectMatItemUIModel
						.getParentDocStatusValue());
		// Logic to set reference data
		String referenceDate = qualityInspectMatItemUIModel.getCheckDate();
		if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
			referenceDate = qualityInspectMatItemUIModel.getCreatedDate();
		}
		serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		return serviceDocumentExtendUIModel;
	}

	@Override
	public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(
			ServiceEntityNode seNode, LogonInfo logonInfo) {
		if (seNode == null) {
			return null;
		}
		if (ServiceEntityNode.NODENAME_ROOT.equals(seNode.getNodeName())) {
			QualityInspectOrder qualityInspectOrder = (QualityInspectOrder) seNode;
			try {
				QualityInspectOrderUIModel qualityInspectOrderUIModel = (QualityInspectOrderUIModel) genUIModelFromUIModelExtension(
						QualityInspectOrderUIModel.class,
						qualityInspectOrderServiceUIModelExtension
								.genUIModelExtensionUnion().get(0),
						qualityInspectOrder, logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel =
						convInspectOrderToDocExtUIModel(qualityInspectOrderUIModel, logonInfo);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException
					| ServiceEntityConfigureException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, QualityInspectOrder.SENAME));
			} catch (ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, QualityInspectOrder.SENAME));
			}
		}
		if (QualityInspectMatItem.NODENAME.equals(seNode.getNodeName())) {
			QualityInspectMatItem qualityInspectMatItem = (QualityInspectMatItem) seNode;
			try {
				QualityInspectMatItemUIModel qualityInspectMatItemUIModel = (QualityInspectMatItemUIModel) genUIModelFromUIModelExtension(
						QualityInspectMatItemUIModel.class,
						qualityInspectMatItemServiceUIModelExtension
								.genUIModelExtensionUnion().get(0),
						qualityInspectMatItem, logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convInspectMatItemToDocExtUIModel(qualityInspectMatItemUIModel, logonInfo);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException
					| ServiceEntityConfigureException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, QualityInspectMatItem.NODENAME));
			} catch (ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, QualityInspectMatItem.NODENAME));
			}
		}
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.QualityInspectOrder;
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return qualityInspectOrderSearchProxy;
	}

}
