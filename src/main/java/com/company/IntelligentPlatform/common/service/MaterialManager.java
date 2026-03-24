package com.company.IntelligentPlatform.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.*;
// TODO-DAO: import ...MaterialDAO;
import com.company.IntelligentPlatform.common.service.CorporateCustomerException;
import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialConfigureProxy;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.model.MaterialUnitReference;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceReflectiveHelper;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.SerialNumberSettingManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.service.ServiceCalendarHelper;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialNumberSetting;
import com.company.IntelligentPlatform.common.model.ServiceModuleConvertPara;
import com.company.IntelligentPlatform.common.model.ISystemCodeValueCollectConstants;
import java.time.ZoneId;
import java.time.LocalDateTime;

/**
 * Logic Manager CLASS FOR Service Entity [Material]
 *
 * @author
 * @date Tue Sep 01 21:39:19 CST 2015
 *       <p>
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class MaterialManager extends ServiceEntityManager {

	public static final String METHOD_ConvMaterialToUI = "convMaterialToUI";

	public static final String METHOD_ConvSupplierToUI = "convSupplierToUI";

	public static final String METHOD_ConvUIToMaterial = "convUIToMaterial";

	public static final String METHOD_ConvMaterialTypeToUI = "convMaterialTypeToUI";

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected MaterialDAO materialDAO;

	@Autowired
	protected MaterialConfigureProxy materialConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected MaterialIdHelper materialIdHelper;

	@Autowired
	protected MaterialTypeManager materialTypeManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected ServiceCalendarHelper serviceCalendarHelper;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected MatDecisionValueSettingManager matDecisionValueSettingManager;

	@Autowired
	protected StandardMaterialUnitManager standardMaterialUnitManager;

	@Autowired
	protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

	@Autowired
	protected SerialNumberSettingManager serialNumberSettingManager;

	@Autowired
	protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

	@Autowired
	protected MaterialSearchProxy materialSearchProxy;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Map<String, Map<Integer, String>> materialCategoryMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> supplyTypeMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> operationModeMapLan = new HashMap<>();


	protected Map<Integer, String> outPackageMaterialTypeMap;

	protected Map<String, Material> materialMap = new HashMap<>();

	protected List<ServiceEntityNode> materialTypeList;

	protected List<ServiceEntityNode> standardMaterialUnitList;

	protected List<ServiceEntityNode> rawMaterialPackageType;

	public Map<Integer, String> initMaterialCategoryMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.materialCategoryMapLan, MaterialUIModel.class,
				"materialCategory");		
	}

	public Map<Integer, String> initQualityInspectMap(String languageCode)
			throws ServiceEntityInstallationException {
		return materialConfigureTemplateManager
				.initQualityInspectMap(languageCode);		
	}

	public Map<Integer, String> initSupplyTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.supplyTypeMapLan, MaterialUIModel.class,
				"supplyType");		
	}

	public Map<Integer, String> initStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, MaterialUIModel.class,
				"status");
	}

	public Map<Integer, String> initSwitchMap(String languageCode)
			throws ServiceEntityInstallationException {		
		return standardSwitchProxy.getSimpleSwitchMap(languageCode);
	}

	public Map<Integer, String> initPackageMaterialType(String client,
			boolean refreshFlag) throws ServiceEntityInstallationException {
		List<ServiceEntityNode> systemCodeValueUnionList = this
				.initRawPackageTypeMap(client, refreshFlag);
		return systemCodeValueCollectionManager
				.convertIntCodeValueUnionMap(systemCodeValueUnionList);
	}

	public List<ServiceEntityNode> initRawPackageTypeMap(String client,
			boolean refreshFlag) throws ServiceEntityInstallationException {
		if (refreshFlag
				|| ServiceCollectionsHelper
						.checkNullList(this.rawMaterialPackageType)) {
			try {
				this.rawMaterialPackageType = systemCodeValueCollectionManager
						.loadRawCodeValueUnionList(
								ISystemCodeValueCollectConstants.ID_MAT_PACKAGETYPE,
								client);
			} catch (ServiceModuleProxyException
					| ServiceEntityConfigureException e) {
				// just ignore
			}
		}
		return this.rawMaterialPackageType;
	}

	public Map<Integer, String> initOutPackageMaterialTypeMap()
			throws ServiceEntityInstallationException {
		if (this.outPackageMaterialTypeMap == null) {
			this.outPackageMaterialTypeMap = serviceDropdownListHelper
					.getUIDropDownMap(MaterialUnitUIModel.class,
							"outPackageMaterialType");
		}
		return this.outPackageMaterialTypeMap;
	}

	@Override
	public void updateBuffer(ServiceEntityNode serviceEntityNode) {
		if (serviceEntityNode != null
				&& Material.SENAME.equals(serviceEntityNode
						.getServiceEntityName())) {
			Material material = (Material) serviceEntityNode;
			this.materialMap.put(material.getUuid(),
					material);
		}
	}
	
	@Override
	public void updateBuffer(List<ServiceEntityNode> seNodeList) {
		this.materialMap = new HashMap<>();
		if(!ServiceCollectionsHelper.checkNullList(seNodeList)){
			for(ServiceEntityNode seNode:seNodeList){
				updateBuffer(seNode);
			}
		}
	}

	public void activeMaterial(Material material, SerialLogonInfo serialLogonInfo)
            throws MaterialException, ServiceEntityConfigureException, DocActionException {
		List<ServiceEntityNode> rawMaterialUnitReferenceList = getEntityNodeListByKey(material.getUuid(),
						IServiceEntityNodeFieldConstant.PARENTNODEUUID,
						MaterialUnitReference.NODENAME,
						material.getClient(), null);
		boolean copyMaterialId = true;
		materialStockKeepUnitManager.updateSKUBatchFromRefMaterial(
				material, copyMaterialId, rawMaterialUnitReferenceList,
				serialLogonInfo, true);
		updateBuffer(material);
		updateSENode(material, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
	}

	/**
	 * Get Material instance online, from cache, or finally from DB persistence
	 * 
	 * @param uuid
	 * @param client
	 * @param rawMaterialList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Material getMaterialWrapper(String uuid, String client,
			List<ServiceEntityNode> rawMaterialList)
			throws ServiceEntityConfigureException {
		if (!ServiceCollectionsHelper.checkNullList(rawMaterialList)) {
			for (ServiceEntityNode seNode : rawMaterialList) {
				if (uuid.equals(seNode.getUuid())) {
					return (Material) seNode;
				}
			}
		}
		// In case not find, trying to find from DB
		if (this.materialMap.containsKey(uuid)) {
			return this.materialMap.get(uuid);
		}
		// In case not find, then find from persistence
		Material material = (Material) getEntityNodeByKey(uuid,
				IServiceEntityNodeFieldConstant.UUID, Material.NODENAME,
				client, null);
		this.materialMap.put(uuid, material);
		return material;
	}


	public void convMaterialToUI(Material material,
			MaterialUIModel materialUIModel) {
		convMaterialToUI(material, materialUIModel, null);
	}

	public void convMaterialToUI(Material material,
								 MaterialUIModel materialUIModel, LogonInfo logonInfo) {
		convMaterialToUI(material, materialUIModel, null, logonInfo);
	}

	public void convMaterialToUI(Material material,
			MaterialUIModel materialUIModel, List<ServiceEntityNode> standardMaterialUnitList, LogonInfo logonInfo) {
		if (material != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(material, materialUIModel);
			materialUIModel.setRefMaterialType(material.getRefMaterialType());
			materialUIModel.setStatus(material.getStatus());
			materialUIModel.setMaterialCategory(material.getMaterialCategory());
			if(logonInfo != null){
				try {
					Map<Integer, String> materialCategoryMap = this.initMaterialCategoryMap(logonInfo.getLanguageCode());
					materialUIModel.setMaterialCategoryValue(materialCategoryMap
							.get(material.getMaterialCategory()));
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
				}
				try {
					Map<Integer, String> switchFlagMap = this.initSwitchMap(logonInfo.getLanguageCode());
					materialUIModel.setSwitchFlagValue(switchFlagMap.get(material
							.getSwitchFlag()));
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
				}
			}
			materialUIModel.setMainProductionPlace(material
					.getMainProductionPlace());			
			materialUIModel.setQualityInspectFlag(material.getSwitchFlag());
			if (logonInfo != null) {
				try {
					Map<Integer, String> qualityInspectMap = this
							.initQualityInspectMap(logonInfo.getLanguageCode());
					materialUIModel
							.setQualityInspectFlagValue(qualityInspectMap
									.get(material.getQualityInspectFlag()));
					Map<Integer, String> statusMap = this
							.initStatusMap(logonInfo.getLanguageCode());
					materialUIModel
							.setStatusValue(statusMap
									.get(material.getStatus()));
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, "qualityInspect"));
				}
			}
			if(!ServiceCollectionsHelper.checkNullList(standardMaterialUnitList)){
				try {
					setStandardUnitValueToUIModel(standardMaterialUnitList, material, materialUIModel);
				} catch (NoSuchFieldException | IllegalAccessException e) {
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, ""));
				}
				StandardMaterialUnit standardMaterialUnit = (StandardMaterialUnit) ServiceCollectionsHelper
						.filterSENodeByName(materialUIModel.getMainMaterialUnitName(), standardMaterialUnitList);
				if (standardMaterialUnit != null) {
					materialUIModel.setMainMaterialUnit(standardMaterialUnit.getUuid());
					materialUIModel.setMainMaterialUnitName(standardMaterialUnit.getName());
				}
			}
			materialUIModel.setSwitchFlag(material.getSwitchFlag());
			materialUIModel.setBarcode(material.getBarcode());
			materialUIModel.setSwitchFlagShow(material.getSwitchFlag());
			materialUIModel.setMainMaterialUnit(material.getMainMaterialUnit());
			materialUIModel.setNote(material.getNote());
			materialUIModel.setLength(material.getLength());
			materialUIModel.setWidth(material.getWidth());
			materialUIModel.setHeight(material.getHeight());
			materialUIModel.setNetWeight(ServiceEntityDoubleHelper
					.trancateDoubleScale4(material.getNetWeight()));
			materialUIModel.setGrossWeight(ServiceEntityDoubleHelper
					.trancateDoubleScale4(material.getGrossWeight()));
			materialUIModel.setVolume(ServiceEntityDoubleHelper
					.trancateDoubleScale4(material.getVolume()));
			materialUIModel.setInboundDeliveryPrice(material
					.getInboundDeliveryPrice());
			materialUIModel.setOutboundDeliveryPrice(material
					.getOutboundDeliveryPrice());
			materialUIModel.setRefMainSupplierUUID(material
					.getRefMainSupplierUUID());
			materialUIModel.setOperationMode(material.getOperationMode());
			try {
				Map<Integer, String> packageMaterialTypeMap = this.initPackageMaterialType(
						material.getClient(), false);
				materialUIModel
						.setPackageMaterialTypeValue(packageMaterialTypeMap
								.get(material.getPackageMaterialType()));
			} catch (ServiceEntityInstallationException e) {
				// do nothing
			}
			if (logonInfo != null) {
				try {
					Map<Integer, String> operationModeMap = this.initOperationModeMap(logonInfo.getLanguageCode());
					materialUIModel.setOperationModeValue(operationModeMap
							.get(material.getOperationMode()));
				} catch (ServiceEntityInstallationException e) {
					// do nothing
				}
			}
			materialUIModel.setPackageStandard(material.getPackageStandard());
			materialUIModel.setRetailPrice(material.getRetailPrice());
			materialUIModel.setMemberSalePrice(material.getMemberSalePrice());
			materialUIModel.setPurchasePrice(material.getPurchasePrice());
			materialUIModel.setWholeSalePrice(material.getWholeSalePrice());
			materialUIModel.setPackageMaterialType(material
					.getPackageMaterialType());
			materialUIModel.setMinStoreNumber(material.getMinStoreNumber());
			materialUIModel.setLastUpdateTime(material.getLastUpdateTime() != null ? java.util.Date.from(material.getLastUpdateTime().atZone(java.time.ZoneId.systemDefault()).toInstant()) : null);
			materialUIModel.setCargoType(material.getCargoType());
			materialUIModel.setFixLeadTime(material.getFixLeadTime());
			materialUIModel.setVariableLeadTime(material.getVariableLeadTime());
			materialUIModel.setAmountForVarLeadTime(material
					.getAmountForVarLeadTime());
			materialUIModel.setUnitCost(material.getUnitCost());
			materialUIModel.setPurchasePriceDisplay(material
					.getPurchasePriceDisplay());
			materialUIModel.setRetailPriceDisplay(material
					.getRetailPriceDisplay());
			materialUIModel.setUnitCostDisplay(material
					.getUnitCostDisplay());
			materialUIModel.setRefLengthUnit(material.getRefLengthUnit());
			materialUIModel.setRefVolumeUnit(material.getRefVolumeUnit());
			materialUIModel.setRefWeightUnit(material.getRefWeightUnit());
			materialUIModel.setSupplyType(material.getSupplyType());
			if(logonInfo!= null){
				try {
					Map<Integer, String> supplyTypeMap = this.initSupplyTypeMap(logonInfo.getLanguageCode());
					materialUIModel.setSupplyTypeValue(supplyTypeMap.get(material
							.getSupplyType()));
				} catch (ServiceEntityInstallationException e) {
					// skip
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
							e, "convMaterialToUI"));
				}
			}
		}
	}

	public void setStandardUnitValueToUIModel(List<ServiceEntityNode> standardMaterialUnitList,
											ServiceEntityNode material,
									   SEUIComModel materialUIModel)
			throws NoSuchFieldException, IllegalAccessException {
		if(!ServiceCollectionsHelper.checkNullList(standardMaterialUnitList)){
			String refLengthUnit = (String) ServiceReflectiveHelper.getFieldValue(material, "refLengthUnit");
			ServiceEntityNode refLengthUnitNode = ServiceCollectionsHelper
					.filterSENodeOnline(
							refLengthUnit,
							standardMaterialUnitList);
			if(refLengthUnitNode != null){
				ServiceReflectiveHelper.reflectSetValue("refLengthUnit", materialUIModel, refLengthUnitNode.getUuid());
				ServiceReflectiveHelper.reflectSetValue("refLengthUnitValue", materialUIModel,
						refLengthUnitNode.getName());
			}
			String refWeightUnit = (String) ServiceReflectiveHelper.getFieldValue(material, "refWeightUnit");
			ServiceEntityNode refWeightUnitNode = ServiceCollectionsHelper
					.filterSENodeOnline(
							refWeightUnit,
							standardMaterialUnitList);
			if(refWeightUnitNode != null){
				ServiceReflectiveHelper.reflectSetValue("refWeightUnit", materialUIModel,
						refWeightUnitNode.getUuid());
				ServiceReflectiveHelper.reflectSetValue("refWeightUnitValue", materialUIModel,
						refWeightUnitNode.getName());
			}
			String refVolumeUnit = (String) ServiceReflectiveHelper.getFieldValue(material, "refVolumeUnit");
			ServiceEntityNode refVolumeUnitNode = ServiceCollectionsHelper
					.filterSENodeOnline(
							refVolumeUnit,
							standardMaterialUnitList);
			if(refVolumeUnitNode != null){
				ServiceReflectiveHelper.reflectSetValue("refVolumeUnit", materialUIModel,
						refVolumeUnitNode.getUuid());
				ServiceReflectiveHelper.reflectSetValue("refVolumeUnitValue", materialUIModel,
						refVolumeUnitNode.getName());
			}
		}
	}

	public void convUIToMaterial(MaterialUIModel materialUIModel,
			Material rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(materialUIModel, rawEntity);
		if (!ServiceEntityStringHelper.checkNullString(materialUIModel
				.getRefMaterialType())) {
			rawEntity.setRefMaterialType(materialUIModel.getRefMaterialType());
		}
		if (materialUIModel.getMaterialCategory() > 0) {
			rawEntity
					.setMaterialCategory(materialUIModel.getMaterialCategory());
		}
		if (materialUIModel.getQualityInspectFlag() > 0) {
			rawEntity.setQualityInspectFlag(materialUIModel
					.getQualityInspectFlag());
		}
		rawEntity.setMainProductionPlace(materialUIModel
				.getMainProductionPlace());
		rawEntity.setBarcode(materialUIModel.getBarcode());
		rawEntity.setMainMaterialUnit(materialUIModel.getMainMaterialUnit());
		rawEntity.setLength(materialUIModel.getLength());
		rawEntity.setWidth(materialUIModel.getWidth());
		rawEntity.setHeight(materialUIModel.getHeight());
		rawEntity.setNetWeight(ServiceEntityDoubleHelper
				.trancateDoubleScale4(materialUIModel.getNetWeight()));
		rawEntity.setGrossWeight(ServiceEntityDoubleHelper
				.trancateDoubleScale4(materialUIModel.getGrossWeight()));
		rawEntity.setVolume(ServiceEntityDoubleHelper
				.trancateDoubleScale4(materialUIModel.getVolume()));
		rawEntity.setInboundDeliveryPrice(materialUIModel
				.getInboundDeliveryPrice());
		rawEntity.setOutboundDeliveryPrice(materialUIModel
				.getOutboundDeliveryPrice());
		rawEntity.setRefMainSupplierUUID(materialUIModel
				.getRefMainSupplierUUID());
		if (materialUIModel.getOperationMode() > 0) {
			rawEntity.setOperationMode(materialUIModel.getOperationMode());
		}
		rawEntity.setPackageStandard(materialUIModel.getPackageStandard());
		rawEntity.setRetailPrice(materialUIModel.getRetailPrice());
		rawEntity.setRetailPriceDisplay(materialUIModel.getRetailPriceDisplay());
		rawEntity.setMemberSalePrice(materialUIModel.getMemberSalePrice());
		rawEntity.setPurchasePrice(materialUIModel.getPurchasePrice());
		rawEntity.setPurchasePriceDisplay(materialUIModel.getPurchasePriceDisplay());
		rawEntity.setWholeSalePrice(materialUIModel.getWholeSalePrice());
		rawEntity.setPackageMaterialType(materialUIModel
				.getPackageMaterialType());
		rawEntity.setMinStoreNumber(materialUIModel.getMinStoreNumber());
		rawEntity.setCargoType(materialUIModel.getCargoType());
		rawEntity.setFixLeadTime(materialUIModel.getFixLeadTime());
		rawEntity.setVariableLeadTime(materialUIModel.getVariableLeadTime());
		rawEntity.setAmountForVarLeadTime(materialUIModel
				.getAmountForVarLeadTime());
		rawEntity.setUnitCost(materialUIModel.getUnitCost());
		rawEntity.setUnitCostDisplay(materialUIModel.getUnitCostDisplay());
		rawEntity.setRefLengthUnit(materialUIModel.getRefLengthUnit());
		rawEntity.setRefVolumeUnit(materialUIModel.getRefVolumeUnit());
		rawEntity.setRefWeightUnit(materialUIModel.getRefWeightUnit());
		if (materialUIModel.getSupplyType() != 0) {
			rawEntity.setSupplyType(materialUIModel.getSupplyType());
		}
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(materialDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(materialConfigureProxy);
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		Material material = (Material) super.newRootEntityNode(client);
		String materialId = materialIdHelper.genDefaultId(client);
		material.setId(materialId);
		return material;
	}

	public void convCorporateCustomerToUI(CorporateCustomer corporateCustomer,
			MaterialUIModel materialUIModel) {
		if (corporateCustomer != null) {
			materialUIModel.setMainSupplierName(corporateCustomer.getName());
		}
	}

	public void convUIToCorporateCustomer(CorporateCustomer rawEntity,
			MaterialUIModel materialUIModel) {
		rawEntity.setName(materialUIModel.getMainSupplierName());
	}

	public void convMaterialTypeToUI(MaterialType materialType,
			MaterialUIModel materialUIModel)
			throws ServiceEntityConfigureException {
		if (materialType != null) {
			materialUIModel.setMaterialTypeId(materialType.getId());
			materialUIModel.setMaterialTypeName(materialType.getName());
			String systemMatTypeID = getSystemMaterialTypeID(materialType);
			materialUIModel.setSystemMatTypeID(systemMatTypeID);
		}
	}

	public String getSystemMaterialTypeID(MaterialType materialType)
			throws ServiceEntityConfigureException {
		if (materialType.getSystemStandardCategory() == StandardSystemCategoryProxy.CATE_SYSTEM_STANDARD) {
			return materialType.getId();
		}
		MaterialType parentMaterialType = (MaterialType) materialTypeManager
				.getEntityNodeByKey(materialType.getParentTypeUUID(),
						IServiceEntityNodeFieldConstant.UUID,
						MaterialType.NODENAME, null);
		if (parentMaterialType == null) {
			return null;
		} else {
			return getSystemMaterialTypeID(parentMaterialType);
		}
	}

	/**
	 * Conversion to material from UI Model from Excel
	 *
	 * @param materialUIModel
	 * @param rawEntity
	 * @throws ServiceEntityConfigureException
	 */
	public void convExcelModelToMaterial(MaterialUIModel materialUIModel,
			Material rawEntity, MaterialMetaPackage materialMetaPackage)
			throws ServiceEntityConfigureException {
		// Normal Conversion logic from UI to Raw Model
		this.convUIToMaterial(materialUIModel, rawEntity);
		// Conversion from meta-data.
		this.initStandardMaterialUnitList(rawEntity.getClient());
		if (!ServiceCollectionsHelper.checkNullList(materialMetaPackage
				.getStandardMaterialUnitList())) {
			ServiceEntityNode rawStandardMaterialUnit = ServiceCollectionsHelper
					.filterSENodeByName(
							materialUIModel.getMainMaterialUnitName(),
							materialMetaPackage.getStandardMaterialUnitList());
			if (rawStandardMaterialUnit != null) {
				rawEntity
						.setMainMaterialUnit(rawStandardMaterialUnit.getUuid());
			}
		}
		int supplyType = ServiceCollectionsHelper.getKeyByValue(
				materialMetaPackage.getSupplyTypeMap(),
				materialUIModel.getSupplyTypeValue());
		if (supplyType != 0) {
			rawEntity.setSupplyType(supplyType);
		}
	}


	public List<ServiceEntityNode> getMaterialTypeList(String client)
			throws ServiceEntityConfigureException {
		if (this.materialTypeList == null) {
			List<ServiceEntityNode> materialTypeList = materialTypeManager
					.getEntityNodeListByKey(null, null, MaterialType.NODENAME,
							client, null);
			this.materialTypeList = materialTypeList;
		}
		return this.materialTypeList;
	}

	public List<ServiceEntityNode> initStandardMaterialUnitList(String client)
			throws ServiceEntityConfigureException {
		if (this.standardMaterialUnitList == null) {
			List<ServiceEntityNode> standardMaterialUnitList = standardMaterialUnitManager
					.getEntityNodeListByKey(null, null,
							StandardMaterialUnit.NODENAME, client, null);
			this.standardMaterialUnitList = standardMaterialUnitList;
		}
		return this.standardMaterialUnitList;
	}

	public Map<Integer, String> initOperationModeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.operationModeMapLan, MaterialUIModel.class,
				"operationMode");		
	}

	public List<ServiceEntityNode> getStandardMaterialUnitList(SerialLogonInfo serialLogonInfo)
			throws ServiceEntityConfigureException {
		return standardMaterialUnitManager
				.getEntityNodeListByKey(null, null,
						StandardMaterialUnit.NODENAME, serialLogonInfo.getClient(), null);
	}

	public List<ServiceModuleConvertPara> genStandardUnitConvertParaList(SerialLogonInfo serialLogonInfo,
																	  String targetNodeInstId)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> standardMaterialUnitList = getStandardMaterialUnitList(serialLogonInfo);
		ServiceModuleConvertPara serviceModuleConvertPara = new ServiceModuleConvertPara();
		serviceModuleConvertPara.setTargetNodeInstId(targetNodeInstId);
		serviceModuleConvertPara.setNodeInstId(targetNodeInstId);
		serviceModuleConvertPara.setServiceEntityList(standardMaterialUnitList);
		return ServiceCollectionsHelper.asList(serviceModuleConvertPara);
	}

	public void updateMaterialExcelModel(
			MaterialUIModel materialUIModel,
			boolean copyMaterialId, List<ServiceEntityNode> allStandardUnitList,
			final Function<Material, Material> updateMaterialCallBack,
			final Function<MaterialStockKeepUnit, MaterialStockKeepUnit> updateMaterialSKUCallBack,
			boolean overwriteFlag,
			SerialLogonInfo serialLogonInfo)
			throws ServiceEntityConfigureException, ServiceEntityInstallationException, MaterialException,
			ServiceModuleProxyException {
		/*
		 * [Step1] Check dealer ID duplicate
		 */
		String materialId = materialUIModel.getId();
		if (ServiceEntityStringHelper.checkNullString(materialId)) {
			return;
		}
		Material material = (Material) this.getEntityNodeByKey(materialId,
				IServiceEntityNodeFieldConstant.ID, Material.NODENAME, serialLogonInfo.getClient(),
				null);
		if (material != null) {
			// try to insert material SKU firstly from this material
			try {
				materialStockKeepUnitManager.updateInitialSKUBackendFromMaterial(
						material, copyMaterialId, null, updateMaterialSKUCallBack,
						serialLogonInfo, false);
			} catch (DocActionException | ServiceModuleProxyException e) {
				throw new MaterialException(MaterialException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
			return;
		}
		/*
		 * [Step3] Check existence of Material category and type
		 */
		/*
		 * [Step 3.1] Set Material Category
		 */
		int materialCategory = 0;
		String materialCategoryValue = materialUIModel
				.getMaterialCategoryValue();
		if (ServiceEntityStringHelper.checkNullString(materialCategoryValue)) {
			// Or set the default category
			materialCategory = Material.MATECATE_FINISH_PRODUCTS;
			// continue;
		} else {
			Map<Integer, String> materialCategoryMap = this.initMaterialCategoryMap(serialLogonInfo.getLanguageCode());
			materialCategory = ServiceCollectionsHelper.getKeyByValue(
					materialCategoryMap, materialCategoryValue);
			if (materialCategory == 0) {

			}
		}
		/*
		 * [Step 3.2] Set Material Type
		 */
		materialUIModel.setMaterialCategory(materialCategory);

		String materialTypeId = materialUIModel.getMaterialTypeId();
		String materialTypeUUID = ServiceEntityStringHelper.EMPTYSTRING;
		if (ServiceEntityStringHelper.checkNullString(materialTypeId)) {
			materialTypeUUID = getDefaultMaterialTypeUUID(serialLogonInfo.getClient());
			// continue;
		} else {
			// Get material type			
			MaterialType materialType = (MaterialType) ServiceCollectionsHelper.filterOnline(materialTypeId, rawSENode->{
				MaterialType tempType = (MaterialType) rawSENode;
				return tempType.getId();
			}, getMaterialTypeList(serialLogonInfo.getClient()));
			if (materialType == null) {
				// Log error and continue;
				logger.error("empty material Type:" + materialTypeId);
			} else {
				materialTypeUUID = materialType.getUuid();
			}
		}
		materialUIModel.setRefMaterialType(materialTypeUUID);
		
		/*
		 * [Step 3.3] Set Quality inspect Flag
		 */
		Map<Integer, String> qualityInspectFlagMap = this
				.initQualityInspectMap(serialLogonInfo.getLanguageCode());
		if (qualityInspectFlagMap != null) {
			String qualityInspectFlagValue = materialUIModel
					.getQualityInspectFlagValue();
			Integer qualityInspectFlag = ServiceCollectionsHelper
					.getKeyByValue(qualityInspectFlagMap,
							qualityInspectFlagValue);
			if (qualityInspectFlag != null) {
				materialUIModel.setQualityInspectFlag(qualityInspectFlag);
			}
		}
		/*
		 * [Step 3.4] Set Supply Type
		 */
		Map<Integer, String> supplyTypeMap = initSupplyTypeMap(serialLogonInfo.getLanguageCode());
		if (supplyTypeMap != null) {
			Integer supplyType = ServiceCollectionsHelper
					.getKeyByValue(supplyTypeMap,
							materialUIModel
							.getSupplyTypeValue());
			if (supplyType != null) {
				materialUIModel.setSupplyType(supplyType);
			}
		}
		/*
		 * [Step 3.5] Set Some Units
		 */
		if (!ServiceCollectionsHelper.checkNullList(allStandardUnitList)) {
			ServiceEntityNode refLengthUnitNode = ServiceCollectionsHelper
					.filterSENodeByName(
							materialUIModel.getRefLengthUnitValue(),
							allStandardUnitList);
			if(refLengthUnitNode != null){
				materialUIModel.setRefLengthUnit(refLengthUnitNode.getUuid());
			}
			ServiceEntityNode refWeightUnitNode = ServiceCollectionsHelper
					.filterSENodeByName(
							materialUIModel.getRefWeightUnitValue(),
							allStandardUnitList);
			if(refWeightUnitNode != null){
				materialUIModel.setRefWeightUnit(refWeightUnitNode.getUuid());
			}
			ServiceEntityNode refVolumeUnitNode = ServiceCollectionsHelper
					.filterSENodeByName(
							materialUIModel.getRefVolumeUnitValue(),
							allStandardUnitList);
			if(refVolumeUnitNode != null){
				materialUIModel.setRefVolumeUnit(refVolumeUnitNode.getUuid());
			}
			StandardMaterialUnit standardMaterialUnit = (StandardMaterialUnit) ServiceCollectionsHelper
					.filterSENodeByName(materialUIModel.getMainMaterialUnitName(), allStandardUnitList);
			if (standardMaterialUnit != null) {
				materialUIModel.setMainMaterialUnit(standardMaterialUnit.getUuid());
				materialUIModel.setMainMaterialUnitName(standardMaterialUnit.getName());
			}
		}
		/*
		 * [3.5] Set operation mode
		 */
		//TODO read this from configuration
		materialUIModel.setOperationMode(Material.OPERATIONMODE_COMPOUND);

		/*
		 * [Step4] Execute insert data batch
		 */
		Map<Integer, String> switchFlagMap = this
				.initSwitchMap(serialLogonInfo.getLanguageCode());
		Map<Integer, String> materialCategoryMap = this.initMaterialCategoryMap(serialLogonInfo.getLanguageCode());
		Map<Integer, String> operationModeMap = this.initOperationModeMap(serialLogonInfo.getLanguageCode());
		MaterialMetaPackage materialMetaPackage = new MaterialMetaPackage(
				materialCategoryMap, supplyTypeMap, switchFlagMap,
				operationModeMap, this.standardMaterialUnitList);
		insertExcelModelUnion(materialUIModel, copyMaterialId,
				materialMetaPackage, 
				updateMaterialSKUCallBack, serialLogonInfo);
	}



	/**
	 * In case can not find material type, then assign a default one firstly.
	 *
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	protected String getDefaultMaterialTypeUUID(String client)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> rawTypeList = materialTypeManager
				.getEntityNodeListByKey(null, null, MaterialType.NODENAME,
						client, null);
		if (rawTypeList != null) {
			return rawTypeList.get(0).getUuid();
		}
		return null;
	}

	public void admDeleteComMaterialUnion(Material material)
			throws ServiceEntityConfigureException {
		/**
		 * Find material SE model and
		 */
		List<ServiceEntityNode> materialUnitRefList = getEntityNodeListByKey(
				material.getUuid(),
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				Material.NODENAME, material.getClient(), null);
		// Find all relative SKU model list
		List<ServiceEntityNode> materialSKUList = materialStockKeepUnitManager
				.getEntityNodeListByKey(material.getUuid(), "refMaterialUUID",
						MaterialStockKeepUnit.NODENAME, material.getClient(),
						null);
		if (materialSKUList != null && materialSKUList.size() > 0) {
			for (ServiceEntityNode seNode : materialSKUList) {
				MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) seNode;
				materialStockKeepUnitManager
						.admDeleteComMaterialSKUUnion(materialStockKeepUnit);
			}
		}
		if (materialUnitRefList != null && materialUnitRefList.size() > 0) {
			deleteSENode(materialUnitRefList, material.getResEmployeeUUID(),
					null);
		}
		deleteSENode(material, material.getResEmployeeUUID(), null);
	}

	public Material newMaterialFromType(String baseTypeUUID, String client)
			throws ServiceEntityConfigureException, MaterialException {
		MaterialType materialType = (MaterialType) materialTypeManager
				.getEntityNodeByKey(baseTypeUUID,
						IServiceEntityNodeFieldConstant.UUID,
						MaterialType.NODENAME, client, null);
		if (materialType == null) {
			throw new MaterialException(MaterialException.PARA_NO_MATTYPE,
					baseTypeUUID);
		}
		Material material = (Material) newRootEntityNode(client);
		material.setRefMaterialType(baseTypeUUID);
		MatDecisionValueSetting matDecisionSerialFormat = matDecisionValueSettingManager
				.getDecisionValue(material,
						MatDecisionValueSettingManager.VAUSAGE_SUBID_FORMAT);
		if (matDecisionSerialFormat != null) {
			SerialNumberSetting serialNumberSetting = (SerialNumberSetting) serialNumberSettingManager
					.getEntityNodeByKey(matDecisionSerialFormat.getRawValue(),
							IServiceEntityNodeFieldConstant.UUID,
							SerialNumberSetting.NODENAME, client, null, true);
			if (serialNumberSetting != null) {
				String targetId = this.materialIdHelper
						.genDefaultId(serialNumberSetting);
				material.setId(targetId);
			}
		}
		return material;
	}

	public Material processNewMaterialFromType(Material material)
			throws ServiceEntityConfigureException, MaterialException {
		String refTypeUUID = material.getRefMaterialType();
		MaterialType materialType = (MaterialType) materialTypeManager
				.getEntityNodeByKey(refTypeUUID,
						IServiceEntityNodeFieldConstant.UUID,
						MaterialType.NODENAME, material.getClient(), null);
		if (materialType == null) {
			throw new MaterialException(MaterialException.PARA_NO_MATTYPE,
					refTypeUUID);
		}
		MatDecisionValueSetting matDecisionSerialFormat = matDecisionValueSettingManager
				.getDecisionValue(material,
						MatDecisionValueSettingManager.VAUSAGE_SUBID_FORMAT);
		if (matDecisionSerialFormat != null) {
			SerialNumberSetting serialNumberSetting = (SerialNumberSetting) serialNumberSettingManager
					.getEntityNodeByKey(matDecisionSerialFormat.getRawValue(),
							IServiceEntityNodeFieldConstant.UUID,
							SerialNumberSetting.NODENAME, material.getClient(), null, true);
			if (serialNumberSetting != null) {
				String targetId = this.materialIdHelper
						.genDefaultId(serialNumberSetting);
				material.setId(targetId);
			}
		}
		return material;
	}


	/**
	 * Core method for insert corporate distributor excel model
	 *
	 * @throws CorporateCustomerException
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void insertExcelModelUnion(
			MaterialUIModel materialUIModel,
			Boolean copyMaterialId,
			MaterialMetaPackage materialMetaPackage,
			final Function<MaterialStockKeepUnit, MaterialStockKeepUnit> updateMaterialSKUCallback,
			SerialLogonInfo serialLogonInfo)
			throws ServiceEntityConfigureException, MaterialException, ServiceModuleProxyException {
		/*
		 * [Step1] Necessary conversion and insert into DB
		 */
		Material material = (Material) newRootEntityNode(serialLogonInfo.getClient());
		convExcelModelToMaterial(materialUIModel, material, materialMetaPackage);
		insertSENode(material, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());

		/*
		 * [Step2] Active material, and Set the switch flag
		 */
		int switchFlag = ServiceCollectionsHelper.getKeyByValue(
				materialMetaPackage.getSwitchFlagMap(),
				materialUIModel.getSwitchFlagValue());
		try{
			if (switchFlag == StandardSwitchProxy.SWITCH_ON) {
				material.setSwitchFlag(switchFlag);
				materialStockKeepUnitManager.updateInitialSKUBackendFromMaterial(
						material, copyMaterialId, null, null, serialLogonInfo, true);
			} else {
				materialStockKeepUnitManager.updateInitialSKUBackendFromMaterial(
						material, copyMaterialId, null, updateMaterialSKUCallback,
						serialLogonInfo, true);
			}
		}catch(DocActionException e){
			throw new MaterialException(MaterialException.PARA_SYSTEM_ERROR, e.getErrorMessage());
		}
		if (switchFlag == StandardSwitchProxy.SWITCH_OFF) {
			material.setSwitchFlag(switchFlag);
		}
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return materialSearchProxy;
	}

}
