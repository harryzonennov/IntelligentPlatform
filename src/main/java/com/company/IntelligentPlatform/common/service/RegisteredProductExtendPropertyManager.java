package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.RegisteredProductExtendPropertyUIModel;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.List;
import java.util.Map;

@Service
public class RegisteredProductExtendPropertyManager {

	public static final String METHOD_ConvStandardMaterialUnitToPropertyUI = "convStandardMaterialUnitToPropertyUI";

	public static final String METHOD_ConvRefMaterialSKUToPropertyUI = "convRefMaterialSKUToPropertyUI";

	public static final String METHOD_ConvRegisteredProductToPropertyUI = "convRegisteredProductToPropertyUI";

	public static final String METHOD_ConvRegisteredProductExtendPropertyToUI = "convRegisteredProductExtendPropertyToUI";

	public static final String METHOD_ConvUIToRegisteredProductExtendProperty = "convUIToRegisteredProductExtendProperty";

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected RegisteredProductManager registeredProductManager;

	@Autowired
	protected StandardMaterialUnitManager standardMaterialUnitManager;

	@Autowired
	protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), RegisteredProduct.NODENAME,
						request.getUuid(), RegisteredProductExtendProperty.NODENAME, registeredProductManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<RegisteredProduct>) registeredProduct -> {
					// How to get the base page header model list
					List<PageHeaderModel> pageHeaderModelList =
							docPageHeaderModelProxy.getDocPageHeaderModelList(registeredProduct, "serialId");
					return pageHeaderModelList;
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<RegisteredProductExtendProperty>) (registeredProductExtendProperty, pageHeaderModel) -> {
					// How to render current page header
					pageHeaderModel.setHeaderName(registeredProductExtendProperty.getName());
					return pageHeaderModel;
				});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	public Map<Integer, String> initQualityInspectMap(String lanCode)
			throws ServiceEntityInstallationException {
		return materialConfigureTemplateManager.initQualityInspectMap(lanCode);
	}

	public Map<Integer, String> initMeasureFlagMap(String lanCode)
			throws ServiceEntityInstallationException {
		return materialConfigureTemplateManager.initMeasureFlagMap(lanCode);
	}

	public void convStandardMaterialUnitToPropertyUI(
			StandardMaterialUnit standardMaterialUnit,
			RegisteredProductExtendPropertyUIModel registeredProductExtendPropertyUIModel) {
		if (standardMaterialUnit != null) {
			registeredProductExtendPropertyUIModel
					.setRefUnitValue(standardMaterialUnit.getName());
		}
	}

	public void convRegisteredProductToPropertyUI(
			RegisteredProduct registeredProduct,
			RegisteredProductExtendPropertyUIModel registeredProductExtendPropertyUIModel)
			throws ServiceEntityInstallationException {
		if (registeredProduct != null) {
			registeredProductExtendPropertyUIModel
					.setRefSerialId(registeredProduct.getSerialId());
		}
	}

	public void convRefMaterialSKUToPropertyUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			RegisteredProductExtendPropertyUIModel registeredProductExtendPropertyUIModel)
			throws ServiceEntityInstallationException {
		if (materialStockKeepUnit != null) {
			registeredProductExtendPropertyUIModel
					.setRefMaterialSKUId(materialStockKeepUnit.getId());
			registeredProductExtendPropertyUIModel
					.setRefMaterialSKUName(materialStockKeepUnit.getName());
			registeredProductExtendPropertyUIModel
					.setPackageStandard(materialStockKeepUnit
							.getPackageStandard());
		}
	}

	public void convRegisteredProductExtendPropertyToUI(
			RegisteredProductExtendProperty registeredProductExtendProperty,
			RegisteredProductExtendPropertyUIModel registeredProductExtendPropertyUIModel)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		convRegisteredProductExtendPropertyToUI(
				registeredProductExtendProperty,
				registeredProductExtendPropertyUIModel, null);
	}

	public void convRegisteredProductExtendPropertyToUI(
			RegisteredProductExtendProperty registeredProductExtendProperty,
			RegisteredProductExtendPropertyUIModel registeredProductExtendPropertyUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		if (registeredProductExtendProperty != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(registeredProductExtendProperty, registeredProductExtendPropertyUIModel);
			registeredProductExtendPropertyUIModel
					.setRefValueSettingUUID(registeredProductExtendProperty
							.getRefValueSettingUUID());
			registeredProductExtendPropertyUIModel
					.setDoubleValue(registeredProductExtendProperty
							.getDoubleValue());
			registeredProductExtendPropertyUIModel
					.setStringValue(registeredProductExtendProperty
							.getStringValue());
			registeredProductExtendPropertyUIModel
					.setIntValue(registeredProductExtendProperty.getIntValue());
			registeredProductExtendPropertyUIModel
					.setQualityInspectFlag(registeredProductExtendProperty
							.getQualityInspectFlag());
			registeredProductExtendPropertyUIModel
					.setRefUnitUUID(registeredProductExtendProperty
							.getRefUnitUUID());
			StandardMaterialUnit standardMaterialUnit = (StandardMaterialUnit) standardMaterialUnitManager
					.getEntityNodeByKey(
							registeredProductExtendProperty.getRefUnitUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							StandardMaterialUnit.NODENAME,
							registeredProductExtendProperty.getClient(), null);
			if (ServiceEntityStringHelper
					.checkNullString(registeredProductExtendPropertyUIModel
							.getStringValue())) {
				// In case 'string value' is not used.
				registeredProductExtendPropertyUIModel
						.setStringValue(registeredProductExtendProperty
								.getDoubleValue() + "");
			}
			if (standardMaterialUnit != null) {
				registeredProductExtendPropertyUIModel
						.setRefUnitValue(standardMaterialUnit.getName());
				if (ServiceEntityStringHelper
						.checkNullString(registeredProductExtendPropertyUIModel
								.getStringValue())) {
					// In case 'string value' is not used.
					registeredProductExtendPropertyUIModel
							.setStringValue(registeredProductExtendProperty
									.getDoubleValue()
									+ standardMaterialUnit.getName());
				}
			}
			registeredProductExtendPropertyUIModel
					.setMeasureFlag(registeredProductExtendProperty
							.getMeasureFlag());
			if (logonInfo != null) {
				Map<Integer, String> qualityInspectMap = this
						.initQualityInspectMap(logonInfo.getLanguageCode());
				registeredProductExtendPropertyUIModel
						.setQualityInspectFlagValue(qualityInspectMap
								.get(registeredProductExtendProperty
										.getQualityInspectFlag()));
				Map<Integer, String> measureFlag = this
						.initMeasureFlagMap(logonInfo.getLanguageCode());
				registeredProductExtendPropertyUIModel
						.setMeasureFlagValue(measureFlag
								.get(registeredProductExtendProperty
										.getMeasureFlag()));
			}
		}
	}

	public void convUIToRegisteredProductExtendProperty(
			RegisteredProductExtendPropertyUIModel registeredProductExtendPropertyUIModel,
			RegisteredProductExtendProperty rawEntity) {
		if (rawEntity != null) {
			DocFlowProxy.convUIToServiceEntityNode(registeredProductExtendPropertyUIModel, rawEntity);
			rawEntity
					.setRefValueSettingUUID(registeredProductExtendPropertyUIModel
							.getRefValueSettingUUID());
			rawEntity.setDoubleValue(registeredProductExtendPropertyUIModel
					.getDoubleValue());
			rawEntity.setIntValue(registeredProductExtendPropertyUIModel
					.getIntValue());
			rawEntity.setMeasureFlag(registeredProductExtendPropertyUIModel
					.getMeasureFlag());
			rawEntity.setRefUnitUUID(registeredProductExtendPropertyUIModel
					.getRefUnitUUID());
			rawEntity.setIntValue(registeredProductExtendPropertyUIModel
					.getIntValue());
			rawEntity
					.setQualityInspectFlag(registeredProductExtendPropertyUIModel
							.getQualityInspectFlag());
			rawEntity.setStringValue(registeredProductExtendPropertyUIModel
					.getStringValue());
		}
	}

}
