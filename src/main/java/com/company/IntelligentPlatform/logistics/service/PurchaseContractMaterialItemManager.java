package com.company.IntelligentPlatform.logistics.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.dto.PurchaseContractMaterialItemUIModel;
import com.company.IntelligentPlatform.logistics.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.SplitMatItemProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import java.time.ZoneId;
import java.time.LocalDateTime;

@Service
@Transactional
public class PurchaseContractMaterialItemManager {

	public static final String METHOD_ConvPurchaseContractMaterialItemToUI = "convPurchaseContractMaterialItemToUI";

	public static final String METHOD_ConvUIToPurchaseContractMaterialItem = "convUIToPurchaseContractMaterialItem";

	public static final String METHOD_ConvParentDocToItemUI = "convParentDocToItemUI";

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;
	
	@Autowired
	protected PurchaseContractManager purchaseContractManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected SplitMatItemProxy splitMatItemProxy;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), PurchaseContract.NODENAME,
						request.getUuid(), PurchaseContractMaterialItem.NODENAME, purchaseContractManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<PurchaseContract>) purchaseContract -> {
					// How to get the base page header model list
                    return docPageHeaderModelProxy.getDocPageHeaderModelList(purchaseContract, null);
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<PurchaseContractMaterialItem>) (purchaseContractMaterialItem, pageHeaderModel) ->
						docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(purchaseContractMaterialItem, pageHeaderModel));
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}


	/**
	 * [Internal method] Convert from UI model to se
	 * model:purchaseContractMaterialItem
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToPurchaseContractMaterialItem(PurchaseContractMaterialItemUIModel purchaseContractMaterialItemUIModel,
													 PurchaseContractMaterialItem rawEntity) {
		docFlowProxy.convUIToDocMatItem(purchaseContractMaterialItemUIModel, rawEntity);
		if (!ServiceEntityStringHelper.checkNullString(purchaseContractMaterialItemUIModel.getId())) {
			rawEntity.setId(purchaseContractMaterialItemUIModel.getId());
		}
		if (!ServiceEntityStringHelper.checkNullString(purchaseContractMaterialItemUIModel.getRequireShippingTime())) {
			try {
				rawEntity.setRequireShippingTime(
						DefaultDateFormatConstant.DATE_FORMAT.parse(purchaseContractMaterialItemUIModel.getRequireShippingTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setShippingPoint(purchaseContractMaterialItemUIModel.getShippingPoint());
		rawEntity.setShippingPoint(purchaseContractMaterialItemUIModel.getShippingPoint());
		if(purchaseContractMaterialItemUIModel.getItemStatus() > 0){
			rawEntity.setItemStatus(purchaseContractMaterialItemUIModel.getItemStatus());
		}
		rawEntity.setRefUnitName(purchaseContractMaterialItemUIModel.getRefUnitName());
		rawEntity.setCurrencyCode(purchaseContractMaterialItemUIModel.getCurrencyCode());
	}

	public void convParentDocToItemUI(PurchaseContract purchaseContract,
									  PurchaseContractMaterialItemUIModel purchaseContractMaterialItemUIModel, LogonInfo logonInfo){
		docFlowProxy.convParentDocToItemUI(purchaseContract, purchaseContractMaterialItemUIModel, logonInfo);
		if(purchaseContract.getSignDate() != null){
			purchaseContractMaterialItemUIModel
					.setSignDate(DefaultDateFormatConstant.DATE_FORMAT
							.format(purchaseContract.getSignDate()));
		}
		if(purchaseContract.getRequireExecutionDate() != null){
			purchaseContractMaterialItemUIModel
					.setRequireExecutionDate(DefaultDateFormatConstant.DATE_FORMAT
							.format(purchaseContract.getRequireExecutionDate()));
		}
	}

	public void convPurchaseContractMaterialItemToUI(PurchaseContractMaterialItem purchaseContractMaterialItem,
													 PurchaseContractMaterialItemUIModel purchaseContractMaterialItemUIModel)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		convPurchaseContractMaterialItemToUI(purchaseContractMaterialItem, purchaseContractMaterialItemUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceEntityConfigureException
	 */
	public void convPurchaseContractMaterialItemToUI(PurchaseContractMaterialItem purchaseContractMaterialItem,
													 PurchaseContractMaterialItemUIModel purchaseContractMaterialItemUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		if (purchaseContractMaterialItem != null) {
			docFlowProxy.convDocMatItemToUI(purchaseContractMaterialItem, purchaseContractMaterialItemUIModel, logonInfo);
			purchaseContractMaterialItemUIModel.setItemStatus(purchaseContractMaterialItem.getItemStatus());
			if (logonInfo != null) {
				Map<Integer, String> itemStatusMap = purchaseContractManager.initItemStatus(logonInfo.getLanguageCode());
				purchaseContractMaterialItemUIModel
						.setItemStatusValue(itemStatusMap.get(purchaseContractMaterialItem.getItemStatus()));
			}
			if (purchaseContractMaterialItem.getRequireShippingTime() != null) {
				purchaseContractMaterialItemUIModel.setRequireShippingTime(
						DefaultDateFormatConstant.DATE_FORMAT.format(purchaseContractMaterialItem.getRequireShippingTime()));
			}
			purchaseContractMaterialItemUIModel.setId(purchaseContractMaterialItem.getId());
			purchaseContractMaterialItemUIModel.setRefUnitName(purchaseContractMaterialItem.getRefUnitName());
			purchaseContractMaterialItemUIModel.setShippingPoint(purchaseContractMaterialItem.getShippingPoint());
			purchaseContractMaterialItemUIModel.setNote(purchaseContractMaterialItem.getNote());
			if (purchaseContractMaterialItem.getCreatedTime() != null) {
				purchaseContractMaterialItemUIModel
						.setCreatedDate(DefaultDateFormatConstant.DATE_FORMAT.format(purchaseContractMaterialItem.getCreatedTime()));
			}
			try {
				purchaseContractMaterialItemUIModel
						.setSplitEnableFlag(splitMatItemProxy.calculateSplitFlag(purchaseContractMaterialItem));
			} catch (MaterialException e) {
				// just skip
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
			}
			purchaseContractMaterialItemUIModel.setCurrencyCode(purchaseContractMaterialItem.getCurrencyCode());

		}
	}

}
