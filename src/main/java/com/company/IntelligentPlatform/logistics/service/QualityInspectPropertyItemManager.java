package com.company.IntelligentPlatform.logistics.service;

import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.dto.QualityInspectPropertyItemUIModel;
import com.company.IntelligentPlatform.logistics.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.model.RegisteredProductExtendProperty;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class QualityInspectPropertyItemManager{

	public static final String METHOD_ConvRefRegisteredProductToPropertyItemUI = "convRefRegisteredProductToPropertyItemUI";

	public static final String METHOD_ConvRegisteredProductPropertyItemToUI = "convRegisteredProductPropertyItemToUI";

	public static final String METHOD_ConvQualityInspectPropertyItemToUI = "convQualityInspectPropertyItemToUI";

	public static final String METHOD_ConvQualityInspectOrderToPropertyUI = "convQualityInspectOrderToPropertyUI";

	public static final String METHOD_ConvUIToQualityInspectPropertyItem = "convUIToQualityInspectPropertyItem";

	@Autowired
	protected QualityInspectOrderManager qualityInspectOrderManager;
	
	@Autowired
	protected  QualityInspectMatItemManager qualityInspectMatItemManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), QualityInspectMatItem.NODENAME,
						request.getUuid(), QualityInspectPropertyItem.NODENAME, qualityInspectOrderManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<QualityInspectMatItem>) qualityInspectMatItem -> {
					// How to get the base page header model list
					return qualityInspectMatItemManager.getPageHeaderModelList(new ServiceJSONRequest(qualityInspectMatItem.getUuid(), qualityInspectMatItem.getParentNodeUUID()), client);
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<QualityInspectPropertyItem>) (qualityInspectPropertyItem, pageHeaderModel) -> {
					pageHeaderModel.setHeaderName(qualityInspectPropertyItem.getFieldName());
					return pageHeaderModel;
				});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convRefRegisteredProductToPropertyItemUI(
			RegisteredProduct registeredProduct,
			QualityInspectPropertyItemUIModel qualityInspectPropertyItemUIModel) {
		if (registeredProduct != null) {
			qualityInspectPropertyItemUIModel
					.setRefMaterialSKUId(registeredProduct.getId());
			qualityInspectPropertyItemUIModel
					.setPackageStandard(registeredProduct.getPackageStandard());
			qualityInspectPropertyItemUIModel
					.setRefMaterialSKUName(registeredProduct.getName());
			qualityInspectPropertyItemUIModel.setRefSerialId(registeredProduct
					.getSerialId());
			qualityInspectPropertyItemUIModel
					.setProductionBatchNumber(registeredProduct
							.getProductionBatchNumber());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convQualityInspectOrderToPropertyUI(
			QualityInspectOrder qualityInspectOrder,
			QualityInspectPropertyItemUIModel qualityInspectPropertyItemUIModel)
			throws ServiceEntityInstallationException {
		if (qualityInspectOrder != null) {
			qualityInspectPropertyItemUIModel.setCategory(qualityInspectOrder
					.getCategory());
		}
	}

	public void convQualityInspectPropertyItemToUI(
			QualityInspectPropertyItem qualityInspectPropertyItem,
			QualityInspectPropertyItemUIModel qualityInspectPropertyItemUIModel)
			throws ServiceEntityInstallationException {
		convQualityInspectPropertyItemToUI(qualityInspectPropertyItem,
				qualityInspectPropertyItemUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convQualityInspectPropertyItemToUI(
			QualityInspectPropertyItem qualityInspectPropertyItem,
			QualityInspectPropertyItemUIModel qualityInspectPropertyItemUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException {
		if (qualityInspectPropertyItem != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(qualityInspectPropertyItem, qualityInspectPropertyItemUIModel);
			qualityInspectPropertyItemUIModel
					.setCriHighValueDouble(qualityInspectPropertyItem
							.getCriHighValueDouble());
			qualityInspectPropertyItemUIModel
					.setCriCenterValueDouble(qualityInspectPropertyItem
							.getCriCenterValueDouble());
			qualityInspectPropertyItemUIModel
					.setCriLowValueDouble(qualityInspectPropertyItem
							.getCriLowValueDouble());
			qualityInspectPropertyItemUIModel
					.setFieldName(qualityInspectPropertyItem.getFieldName());
			qualityInspectPropertyItemUIModel
					.setCriOffSetValue(qualityInspectPropertyItem
							.getCriOffSetValue());
			qualityInspectPropertyItemUIModel
					.setCriLowValue(qualityInspectPropertyItem.getCriLowValue());
			qualityInspectPropertyItemUIModel
					.setCriCenterValue(qualityInspectPropertyItem
							.getCriCenterValue());
			qualityInspectPropertyItemUIModel
					.setCriOffSetValueDouble(qualityInspectPropertyItem
							.getCriOffSetValueDouble());
			qualityInspectPropertyItemUIModel
					.setActualValueDouble(qualityInspectPropertyItem
							.getActualValueDouble());
			qualityInspectPropertyItemUIModel
					.setCriHighValue(qualityInspectPropertyItem
							.getCriHighValue());
			qualityInspectPropertyItemUIModel
					.setRefPropertyUUID(qualityInspectPropertyItem
							.getRefPropertyUUID());
			qualityInspectPropertyItemUIModel
					.setRefUnitUUID(qualityInspectPropertyItem.getRefUnitUUID());
			if (logonInfo != null) {
				Map<Integer, String> checkStatusMap = qualityInspectOrderManager
						.initCheckStatusMap(logonInfo.getLanguageCode());
				qualityInspectPropertyItemUIModel
						.setPropCheckStatusValue(checkStatusMap
								.get(qualityInspectPropertyItem
										.getPropCheckStatus()));
			}
			qualityInspectPropertyItemUIModel
					.setPropCheckStatus(qualityInspectPropertyItem
							.getPropCheckStatus());
			qualityInspectPropertyItemUIModel
					.setActualValue(qualityInspectPropertyItem.getActualValue());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:qualityInspectPropertyItem
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToQualityInspectPropertyItem(
			QualityInspectPropertyItemUIModel qualityInspectPropertyItemUIModel,
			QualityInspectPropertyItem rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(qualityInspectPropertyItemUIModel, rawEntity);
		rawEntity.setCriHighValueDouble(qualityInspectPropertyItemUIModel
				.getCriHighValueDouble());
		rawEntity.setCriCenterValueDouble(qualityInspectPropertyItemUIModel
				.getCriCenterValueDouble());
		rawEntity.setCriLowValueDouble(qualityInspectPropertyItemUIModel
				.getCriLowValueDouble());
		rawEntity.setUuid(qualityInspectPropertyItemUIModel.getUuid());
		rawEntity
				.setFieldName(qualityInspectPropertyItemUIModel.getFieldName());
		rawEntity.setCriOffSetValue(qualityInspectPropertyItemUIModel
				.getCriOffSetValue());
		rawEntity.setName(qualityInspectPropertyItemUIModel.getName());
		rawEntity.setCriLowValue(qualityInspectPropertyItemUIModel
				.getCriLowValue());
		rawEntity.setId(qualityInspectPropertyItemUIModel.getId());
		rawEntity.setCriCenterValue(qualityInspectPropertyItemUIModel
				.getCriCenterValue());
		rawEntity.setCriOffSetValueDouble(qualityInspectPropertyItemUIModel
				.getCriOffSetValueDouble());
		rawEntity.setNote(qualityInspectPropertyItemUIModel.getNote());
		rawEntity.setActualValueDouble(qualityInspectPropertyItemUIModel
				.getActualValueDouble());
		rawEntity.setCriHighValue(qualityInspectPropertyItemUIModel
				.getCriHighValue());
		rawEntity.setPropCheckStatus(qualityInspectPropertyItemUIModel
				.getPropCheckStatus());
		rawEntity.setActualValue(qualityInspectPropertyItemUIModel
				.getActualValue());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convRegisteredProductPropertyItemToUI(
			RegisteredProductExtendProperty registeredProductExtendProperty,
			QualityInspectPropertyItemUIModel qualityInspectPropertyItemUIModel) {
		if (registeredProductExtendProperty != null) {
			qualityInspectPropertyItemUIModel
					.setActualValueDouble(registeredProductExtendProperty
							.getDoubleValue());
			qualityInspectPropertyItemUIModel
					.setRefUnitUUID(registeredProductExtendProperty
							.getRefUnitUUID());
			qualityInspectPropertyItemUIModel
					.setActualValue(registeredProductExtendProperty
							.getStringValue());
		}
	}

}
