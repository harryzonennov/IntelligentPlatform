package com.company.IntelligentPlatform.sales.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.sales.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.sales.dto.SalesContractMaterialItemUIModel;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreCheckProxy;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;


@Service
public class SalesContractMaterialItemManager {

	public static final String METHOD_ConvSalesContractMaterialItemToUI = "convSalesContractMaterialItemToUI";

	public static final String METHOD_ConvUIToSalesContractMaterialItem = "convUIToSalesContractMaterialItem";

	public static final String METHOD_ConvParentDocToItemUI = "convParentDocToItemUI";

	@Autowired
	protected SalesContractManager salesContractManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected WarehouseStoreCheckProxy warehouseStoreCheckProxy;

	protected Logger logger = LoggerFactory.getLogger(SalesContractMaterialItemManager.class);

	public void convSalesContractMaterialItemToUI(
			SalesContractMaterialItem salesContractMaterialItem,
			SalesContractMaterialItemUIModel salesContractMaterialItemUIModel)
			throws ServiceEntityConfigureException {
		convSalesContractMaterialItemToUI(salesContractMaterialItem, salesContractMaterialItemUIModel, null);
	}

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), SalesContract.NODENAME,
						request.getUuid(), SalesContractMaterialItem.NODENAME, salesContractManager);
		docPageHeaderInputPara.setGenBaseModelList((DocPageHeaderModelProxy.GenBaseModelList<SalesContract>) salesContract -> {
            // How to get the base page header model list
			return docPageHeaderModelProxy.getDocPageHeaderModelList(salesContract, null);
        });
		docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<SalesContractMaterialItem>) (salesContractMaterialItem, pageHeaderModel) ->
				docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(salesContractMaterialItem, pageHeaderModel));
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityConfigureException
	 */
	public void convSalesContractMaterialItemToUI(
			SalesContractMaterialItem salesContractMaterialItem,
			SalesContractMaterialItemUIModel salesContractMaterialItemUIModel, LogonInfo logonInfo)
			throws ServiceEntityConfigureException {
		if (salesContractMaterialItem != null) {
			docFlowProxy.convDocMatItemToUI(salesContractMaterialItem, salesContractMaterialItemUIModel, logonInfo);
			salesContractMaterialItemUIModel
					.setRefUnitUUID(salesContractMaterialItem.getRefUnitUUID());
			salesContractMaterialItemUIModel
					.setItemStatus(salesContractMaterialItem.getItemStatus());
			if(logonInfo != null){
				try {
					Map<Integer, String> statusMap = salesContractManager.initStatus(logonInfo.getLanguageCode());
					salesContractMaterialItemUIModel
							.setItemStatusValue(statusMap
									.get(salesContractMaterialItem.getItemStatus()));
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "convSalesContractMaterialItemToUI"));
				}
			}
			if (salesContractMaterialItem.getRequireShippingTime() != null) {
				salesContractMaterialItemUIModel
						.setRequireShippingTime(DefaultDateFormatConstant.DATE_FORMAT
								.format(salesContractMaterialItem
										.getRequireShippingTime()));
			}
			if (salesContractMaterialItem.getDeliveryDoneTime() != null) {
				salesContractMaterialItemUIModel
						.setDeliveryDoneTime(DefaultDateFormatConstant.DATE_FORMAT
								.format(salesContractMaterialItem.getDeliveryDoneTime()));
			}
			if (salesContractMaterialItem.getProcessDoneTime() != null) {
				salesContractMaterialItemUIModel
						.setProcessDoneTime(DefaultDateFormatConstant.DATE_FORMAT
								.format(salesContractMaterialItem.getProcessDoneTime()));
			}
			salesContractMaterialItemUIModel.setDeliveryDoneBy(salesContractMaterialItem.getDeliveryDoneBy());
			salesContractMaterialItemUIModel.setProcessDoneBy(salesContractMaterialItem
					.getProcessDoneBy());
			salesContractMaterialItemUIModel
					.setRefUnitName(salesContractMaterialItem.getRefUnitName());
			salesContractMaterialItemUIModel
					.setShippingPoint(salesContractMaterialItem
							.getShippingPoint());
			salesContractMaterialItemUIModel
					.setRefTechStandard(salesContractMaterialItem
							.getRefTechStandard());
			salesContractMaterialItemUIModel.setStoreCheckStatus(salesContractMaterialItem.getStoreCheckStatus());
			if(logonInfo != null){
				Map<Integer, String> storeCheckStatusMap = null;
				try {
					storeCheckStatusMap = warehouseStoreCheckProxy.getCheckStatusMap(logonInfo.getLanguageCode());
					salesContractMaterialItemUIModel.setStoreCheckStatusValue(storeCheckStatusMap.get(salesContractMaterialItem.getStoreCheckStatus()));
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "storeCheckStatus"));
				}
			}
			salesContractMaterialItemUIModel
					.setUnitPrice(salesContractMaterialItem.getUnitPrice());
			salesContractMaterialItemUIModel
					.setCurrencyCode(salesContractMaterialItem
							.getCurrencyCode());
			salesContractMaterialItemUIModel
					.setItemPrice(salesContractMaterialItem.getItemPrice());
			salesContractMaterialItemUIModel
					.setNextDocType(salesContractMaterialItem.getNextDocType());
			salesContractMaterialItemUIModel
					.setNextDocMatItemUUID(salesContractMaterialItem
							.getNextDocMatItemUUID());
			salesContractMaterialItemUIModel
					.setPrevDocType(salesContractMaterialItem.getPrevDocType());
			salesContractMaterialItemUIModel
					.setPrevDocMatItemUUID(salesContractMaterialItem
							.getPrevDocMatItemUUID());
			salesContractMaterialItemUIModel
					.setRefFinAccountUUID(salesContractMaterialItem
							.getRefFinAccountUUID());
			salesContractMaterialItemUIModel
					.setRefOutboundItemUUID(salesContractMaterialItem
							.getRefOutboundItemUUID());

		}
	}

	public void convParentDocToItemUI(SalesContract salesContract,
									  SalesContractMaterialItemUIModel salesContractMaterialItemUIModel){
		convParentDocToItemUI(salesContract, salesContractMaterialItemUIModel, null);
	}

	public void convParentDocToItemUI(SalesContract salesContract,
									  SalesContractMaterialItemUIModel salesContractMaterialItemUIModel, LogonInfo logonInfo){
		docFlowProxy.convParentDocToItemUI(salesContract, salesContractMaterialItemUIModel, logonInfo);
		if(salesContract.getPlanExecutionDate() != null){
			salesContractMaterialItemUIModel
					.setPlanExecutionDate(DefaultDateFormatConstant.DATE_FORMAT
							.format(salesContract.getPlanExecutionDate()));
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:salesContractMaterialItem
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToSalesContractMaterialItem(
			SalesContractMaterialItemUIModel salesContractMaterialItemUIModel,
			SalesContractMaterialItem rawEntity) {
		docFlowProxy.convUIToDocMatItem(salesContractMaterialItemUIModel, rawEntity);
		if (!ServiceEntityStringHelper
				.checkNullString(salesContractMaterialItemUIModel.getId())) {
			rawEntity.setId(salesContractMaterialItemUIModel.getId());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(salesContractMaterialItemUIModel
						.getRequireShippingTime())) {
			try {
				rawEntity
						.setRequireShippingTime(DefaultDateFormatConstant.DATE_FORMAT
								.parse(salesContractMaterialItemUIModel
										.getRequireShippingTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setShippingPoint(salesContractMaterialItemUIModel
				.getShippingPoint());
		rawEntity.setNote(salesContractMaterialItemUIModel.getNote());
		rawEntity.setShippingPoint(salesContractMaterialItemUIModel
				.getShippingPoint());
		rawEntity.setRefTechStandard(salesContractMaterialItemUIModel
				.getRefTechStandard());
		if(salesContractMaterialItemUIModel.getItemStatus() > 0){
			rawEntity.setItemStatus(salesContractMaterialItemUIModel
					.getItemStatus());
		}
		rawEntity.setCurrencyCode(salesContractMaterialItemUIModel
				.getCurrencyCode());
		if (!ServiceEntityStringHelper
				.checkNullString(salesContractMaterialItemUIModel
						.getRefFinAccountUUID())) {
			rawEntity.setRefFinAccountUUID(salesContractMaterialItemUIModel
					.getRefFinAccountUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(salesContractMaterialItemUIModel
						.getRefOutboundItemUUID())) {
			rawEntity.setRefOutboundItemUUID(salesContractMaterialItemUIModel
					.getRefOutboundItemUUID());
		}
	}

}