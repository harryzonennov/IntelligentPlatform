package com.company.IntelligentPlatform.logistics.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.dto.QualityInspectMatItemUIModel;
import com.company.IntelligentPlatform.logistics.model.QualityInspectMatItem;
import com.company.IntelligentPlatform.logistics.model.QualityInspectOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceCommonDataFormatter;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import java.time.ZoneId;
import java.time.LocalDate;


@Service
@Transactional
public class QualityInspectMatItemManager{

	public static final String METHOD_ConvQualityInspectMatItemToUI = "convQualityInspectMatItemToUI";

	public static final String METHOD_ConvUIToQualityInspectMatItem = "convUIToQualityInspectMatItem";

	public static final String METHOD_ConvParentDocToItemUI = "convParentDocToItemUI";

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected QualityInspectOrderManager qualityInspectOrderManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	protected Logger logger = LoggerFactory.getLogger(QualityInspectMatItemManager.class);

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), QualityInspectOrder.NODENAME,
						request.getUuid(), QualityInspectMatItem.NODENAME, qualityInspectOrderManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<QualityInspectOrder>) qualityInspectOrder -> {
					// How to get the base page header model list
                    return docPageHeaderModelProxy.getDocPageHeaderModelList(qualityInspectOrder, null);
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<QualityInspectMatItem>) (qualityInspectMatItem, pageHeaderModel) ->
						docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(qualityInspectMatItem, pageHeaderModel));
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceEntityConfigureException
	 * @throws QualityInspectException
	 * @throws MaterialException
	 */
	public void convQualityInspectMatItemToUI(
			QualityInspectMatItem qualityInspectMatItem,
			QualityInspectMatItemUIModel qualityInspectMatItemUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, MaterialException,
			QualityInspectException {
		if (qualityInspectMatItem != null) {
			docFlowProxy.convDocMatItemToUI(qualityInspectMatItem,
					qualityInspectMatItemUIModel, logonInfo);
			qualityInspectMatItemUIModel.setNote(qualityInspectMatItem
					.getNote());
			qualityInspectMatItemUIModel.setCheckTimes(qualityInspectMatItem
					.getCheckTimes());
			qualityInspectMatItemUIModel
					.setSampleUnitUUID(qualityInspectMatItem
							.getSampleUnitUUID());
			qualityInspectMatItemUIModel
					.setProductionBatchNumber(qualityInspectMatItem
							.getProductionBatchNumber());
			qualityInspectMatItemUIModel
					.setRefWarehouseAreaUUID(qualityInspectMatItem
							.getRefWarehouseAreaUUID());
			if (qualityInspectMatItem.getCheckDate() != null) {
				qualityInspectMatItemUIModel
						.setCheckDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(qualityInspectMatItem.getCheckDate()));
			}
			qualityInspectMatItemUIModel.setSampleAmount(qualityInspectMatItem
					.getSampleAmount());
			qualityInspectMatItemUIModel
					.setFailRefUnitUUID(qualityInspectMatItem
							.getFailRefUnitUUID());
			qualityInspectMatItemUIModel.setFailAmount(qualityInspectMatItem
					.getFailAmount());
			// In case sample unit is empty
			if (ServiceEntityStringHelper.checkNullString(qualityInspectMatItem
					.getSampleUnitUUID())) {
				qualityInspectMatItem.setSampleUnitUUID(qualityInspectMatItem
						.getRefUnitUUID());
			}
			try {
				qualityInspectMatItemUIModel
						.setSampleAmountLabel(materialStockKeepUnitManager.getAmountLabel(
								qualityInspectMatItem.getRefMaterialSKUUUID(),
								qualityInspectMatItem.getSampleUnitUUID(),
								qualityInspectMatItem.getSampleAmount(),
								qualityInspectMatItem.getClient()));
			} catch (MaterialException e1) {
				// log the issue and continue
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e1, "sampleAmountLabel"));
			}
			try {
				qualityInspectMatItemUIModel
						.setFailAmountLabel(materialStockKeepUnitManager
								.getAmountLabel(qualityInspectMatItem
												.getRefMaterialSKUUUID(),
										qualityInspectMatItem
												.getFailRefUnitUUID(),
										qualityInspectMatItem.getFailAmount(),
										qualityInspectMatItem.getClient()));
			} catch (MaterialException e1) {
				// log the issue and continue
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e1, "failAmountLabel"));
			}
			StorageCoreUnit successCoreUnit = qualityInspectOrderManager.calculateSuccessAmount(qualityInspectMatItem);
			qualityInspectMatItemUIModel.setSuccessAmount(successCoreUnit
					.getAmount());
			qualityInspectMatItemUIModel.setSuccessRefUnitUUID(successCoreUnit
					.getRefUnitUUID());
			qualityInspectMatItemUIModel
					.setRefWasteWarehouseUUID(qualityInspectMatItem
							.getRefWasteWarehouseUUID());
			qualityInspectMatItemUIModel
					.setRefWasteWareAreaUUID(qualityInspectMatItem
							.getRefWasteWareAreaUUID());
			try {
				qualityInspectMatItemUIModel
						.setSuccessAmountLabel(materialStockKeepUnitManager
								.getAmountLabel(qualityInspectMatItem
												.getRefMaterialSKUUUID(),
										qualityInspectMatItemUIModel
												.getSuccessRefUnitUUID(),
										qualityInspectMatItemUIModel
												.getSuccessAmount(),
										qualityInspectMatItem.getClient()));
			} catch (MaterialException e1) {
				// log the issue and continue
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e1, "successAmountLabel"));
			}
			StorageCoreUnit sampleUnit = new StorageCoreUnit(
					qualityInspectMatItem.getRefMaterialSKUUUID(),
					qualityInspectMatItem.getSampleUnitUUID(),
					qualityInspectMatItem.getSampleAmount());
			StorageCoreUnit allAmountUnit = new StorageCoreUnit(
					qualityInspectMatItem.getRefMaterialSKUUUID(),
					qualityInspectMatItem.getRefUnitUUID(),
					qualityInspectMatItem.getAmount());
			try {
				double sampleRate = materialStockKeepUnitManager
						.getStorageUnitRatio(sampleUnit, allAmountUnit,
								qualityInspectMatItem.getClient());
				qualityInspectMatItemUIModel.setSampleRate(sampleRate);
				String sampleRateValue = ServiceCommonDataFormatter
						.formatPercentageValue(sampleRate);
				qualityInspectMatItemUIModel
						.setSampleRateValue(sampleRateValue);
			} catch (MaterialException e) {
				// log the issue and continue
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, "sampleRate"));
			}
			qualityInspectMatItemUIModel
					.setItemCheckResult(qualityInspectMatItem
							.getItemCheckResult());
			if (logonInfo != null) {
				Map<Integer, String> checkStatusMap = qualityInspectOrderManager
						.initCheckStatusMap(logonInfo.getLanguageCode());
				qualityInspectMatItemUIModel
						.setItemCheckStatusValue(checkStatusMap
								.get(qualityInspectMatItem.getItemCheckStatus()));
				Map<Integer, String> inspectTypeMap = qualityInspectOrderManager
						.initInspectTypeMap(logonInfo.getLanguageCode());
				qualityInspectMatItemUIModel
						.setItemInspectTypeValue(inspectTypeMap
								.get(qualityInspectMatItem.getItemInspectType()));
			}
			qualityInspectMatItemUIModel
					.setItemCheckStatus(qualityInspectMatItem
							.getItemCheckStatus());
			qualityInspectMatItemUIModel
					.setItemInspectType(qualityInspectMatItem
							.getItemInspectType());
			// Calculate the split flag
			qualityInspectMatItemUIModel.setSplitEnableFlag(qualityInspectOrderManager
					.calculateSplitFlag(qualityInspectMatItem));

		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:qualityInspectMatItem
	 *
	 */
	public void convUIToQualityInspectMatItem(
			QualityInspectMatItemUIModel qualityInspectMatItemUIModel,
			QualityInspectMatItem rawEntity) {
		docFlowProxy
				.convUIToDocMatItem(qualityInspectMatItemUIModel, rawEntity);
		rawEntity.setNote(qualityInspectMatItemUIModel.getNote());
		rawEntity.setCheckTimes(qualityInspectMatItemUIModel.getCheckTimes());
		rawEntity.setSampleUnitUUID(qualityInspectMatItemUIModel
				.getSampleUnitUUID());
		if (!ServiceEntityStringHelper
				.checkNullString(qualityInspectMatItemUIModel.getCheckDate())) {
			try {
				rawEntity.setCheckDate(DefaultDateFormatConstant.DATE_FORMAT.parse(qualityInspectMatItemUIModel.getCheckDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setSampleAmount(qualityInspectMatItemUIModel
				.getSampleAmount());
		rawEntity.setSampleUnitUUID(qualityInspectMatItemUIModel
				.getSampleUnitUUID());
		rawEntity.setFailAmount(qualityInspectMatItemUIModel.getFailAmount());
		rawEntity.setFailRefUnitUUID(qualityInspectMatItemUIModel
				.getFailRefUnitUUID());
		// Check and validate success amount
		rawEntity.setItemCheckResult(qualityInspectMatItemUIModel
				.getItemCheckResult());
		rawEntity.setSampleRate(qualityInspectMatItemUIModel.getSampleRate());
		rawEntity.setItemCheckStatus(qualityInspectMatItemUIModel
				.getItemCheckStatus());
		rawEntity.setRefWarehouseAreaUUID(qualityInspectMatItemUIModel
				.getRefWarehouseAreaUUID());
		rawEntity.setRefWasteWarehouseUUID(qualityInspectMatItemUIModel
				.getRefWasteWarehouseUUID());
		rawEntity.setRefWasteWareAreaUUID(qualityInspectMatItemUIModel
				.getRefWasteWareAreaUUID());
		rawEntity.setItemInspectType(qualityInspectMatItemUIModel
				.getItemInspectType());
	}


	public void convParentDocToItemUI(
			QualityInspectOrder qualityInspectOrder,
			QualityInspectMatItemUIModel qualityInspectMatItemUIModel)
			throws ServiceEntityInstallationException {
		convParentDocToItemUI(qualityInspectOrder,
				qualityInspectMatItemUIModel, null);
	}

	public void convParentDocToItemUI(
			QualityInspectOrder qualityInspectOrder,
			QualityInspectMatItemUIModel qualityInspectMatItemUIModel,
			LogonInfo logonInfo) {
		if (qualityInspectOrder != null) {
			docFlowProxy.convParentDocToItemUI(qualityInspectOrder, qualityInspectMatItemUIModel, logonInfo);
			qualityInspectMatItemUIModel.setCategory(qualityInspectOrder
					.getCategory());
			qualityInspectMatItemUIModel
					.setRefWarehouseUUID(qualityInspectOrder
							.getRefWarehouseUUID());
		}
	}


}
