package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceFieldUnion;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.SpringContextBeanService;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;

@Deprecated
@Service
public class ServiceSearchModelComProxy {

	public static final String purchaseContractSearchModel = "purchaseContractSearchModel";

	public static final String purchaseRequestSearchModel = "purchaseRequestSearchModel";

	public static final String purchaseReturnOrderSearchModel = "purchaseReturnOrderSearchModel";

	public static final String salesContractSearchModel = "salesContractSearchModel";

	public static final String inboundDeliverySearchModel = "inboundDeliverySearchModel";

	public static final String outboundDeliverySearchModel = "outboundDeliverySearchModel";

	public static final String inquirySearchModel = "inquirySearchModel";

	public static final String inboundItemSearchModel = "inboundItemSearchModel";

	public static final String outboundItemSearchModel = "outboundItemSearchModel";

	public static final String inventoryCheckOrderSearchModel = "inventoryCheckOrderSearchModel";

	public static final String inventoryCheckItemSearchModel = "inventoryCheckItemSearchModel";

	public static final String purchaseContractMaterialItemSearchModel = "purchaseContractMaterialItemSearchModel";

	public static final String inquiryMaterialItemSearchModel = "inquiryMaterialItemSearchModel";

	public static final String qualityInspectMatItemSearchModel = "qualityInspectMatItemSearchModel";

	public static final String qualityInspectOrderSearchModel = "qualityInspectOrderSearchModel";

	public static final String billOfMaterialItemSearchModel = "billOfMaterialItemSearchModel";

	public static final String billOfMaterialOrderSearchModel = "billOfMaterialOrderSearchModel";

	public static final String billOfMaterialTemplateItemSearchModel = "billOfMaterialTemplateItemSearchModel";

	public static final String billOfMaterialTemplateSearchModel = "billOfMaterialTemplateSearchModel";

	public static final String corporateCustomerSearchModel = "corporateCustomerSearchModel";

	public static final String corporateSupplierSearchModel = "corporateSupplierSearchModel";

	public static final String employeeSearchModel = "employeeSearchModel";

	public static final String materialStockKeepUnitSearchModel = "materialStockKeepUnitSearchModel";

	public static final String registeredProductSearchModel = "registeredProductSearchModel";

	public static final String warehouseStoreItemSearchModel = "warehouseStoreItemSearchModel";

	public static final String warehouseStoreSettingSearchModel = "warehouseStoreSettingSearchModel";

	public static final String warehouseStoreItemLogSearchModel = "warehouseStoreItemLogSearchModel";

	public static final String prodPickingOrderSearchModel = "prodPickingOrderSearchModel";

	public static final String prodPickingRefMaterialItemSearchModel = "prodPickingRefMaterialItemSearchModel";

	public static final String productionOrderSearchModel = "productionOrderSearchModel";

	public static final String productionPlanSearchModel = "productionPlanSearchModel";

	public static final String productionOrderItemSearchModel = "productionOrderItemSearchModel";

	public static final String COM_SEARCH_INTERNAL = "searchInternal";

	public static final String COM_SEARCH_INTERNALITEM = "searchItemInternal";

	@Autowired
	protected SpringContextBeanService springContextBeanService;

	protected List<ServiceSearchModelConfigure> searchConfigureList;

	public List<ServiceSearchModelConfigure> getSearchConfigureList() {
		if (ServiceCollectionsHelper.checkNullList(this.searchConfigureList)) {
			this.searchConfigureList = initSearchConfigureCore();
		}
		return this.searchConfigureList;
	}

	private List<ServiceSearchModelConfigure> initSearchConfigureCore() {
		List<ServiceSearchModelConfigure> resultList = new ArrayList<ServiceSearchModelConfigure>();
		resultList.add(new ServiceSearchModelConfigure(
				purchaseContractSearchModel,
				ServiceEntityManagerFactoryInContext.purchaseContractManager,
				COM_SEARCH_INTERNAL));
		resultList.add(new ServiceSearchModelConfigure(
				purchaseRequestSearchModel,
				ServiceEntityManagerFactoryInContext.purchaseRequestManager,
				COM_SEARCH_INTERNAL));
		resultList.add(new ServiceSearchModelConfigure(
				purchaseReturnOrderSearchModel,
				ServiceEntityManagerFactoryInContext.purchaseReturnOrderManager,
				COM_SEARCH_INTERNAL));
		resultList.add(new ServiceSearchModelConfigure(
				salesContractSearchModel,
				ServiceEntityManagerFactoryInContext.salesContractManager,
				COM_SEARCH_INTERNAL));
		resultList.add(new ServiceSearchModelConfigure(
				inboundDeliverySearchModel,
				ServiceEntityManagerFactoryInContext.inboundDeliveryManager,
				COM_SEARCH_INTERNAL));
		resultList.add(new ServiceSearchModelConfigure(
				outboundDeliverySearchModel,
				ServiceEntityManagerFactoryInContext.outboundDeliveryManager,
				COM_SEARCH_INTERNAL));
		resultList.add(new ServiceSearchModelConfigure(inboundItemSearchModel,
				ServiceEntityManagerFactoryInContext.inboundDeliveryManager,
				COM_SEARCH_INTERNALITEM));
		resultList.add(new ServiceSearchModelConfigure(outboundItemSearchModel,
				ServiceEntityManagerFactoryInContext.outboundDeliveryManager,
				COM_SEARCH_INTERNALITEM));
		resultList
				.add(new ServiceSearchModelConfigure(
						inventoryCheckOrderSearchModel,
						ServiceEntityManagerFactoryInContext.inventoryCheckOrderManager,
						COM_SEARCH_INTERNAL));
		resultList
				.add(new ServiceSearchModelConfigure(
						inventoryCheckItemSearchModel,
						ServiceEntityManagerFactoryInContext.inventoryCheckOrderManager,
						COM_SEARCH_INTERNALITEM));
		resultList.add(new ServiceSearchModelConfigure(
				purchaseContractMaterialItemSearchModel,
				ServiceEntityManagerFactoryInContext.purchaseContractManager,
				COM_SEARCH_INTERNALITEM));
		resultList.add(new ServiceSearchModelConfigure(
				inquiryMaterialItemSearchModel,
				ServiceEntityManagerFactoryInContext.inquiryManager,
				COM_SEARCH_INTERNALITEM));
		resultList
				.add(new ServiceSearchModelConfigure(
						qualityInspectOrderSearchModel,
						ServiceEntityManagerFactoryInContext.qualityInspectOrderManager,
						COM_SEARCH_INTERNAL));
		resultList
				.add(new ServiceSearchModelConfigure(
						qualityInspectMatItemSearchModel,
						ServiceEntityManagerFactoryInContext.qualityInspectOrderManager,
						COM_SEARCH_INTERNALITEM));
		resultList
				.add(new ServiceSearchModelConfigure(
						billOfMaterialItemSearchModel,
						ServiceEntityManagerFactoryInContext.billOfMaterialOrderManager,
						COM_SEARCH_INTERNALITEM));
		resultList
				.add(new ServiceSearchModelConfigure(
						billOfMaterialOrderSearchModel,
						ServiceEntityManagerFactoryInContext.billOfMaterialOrderManager,
						COM_SEARCH_INTERNAL));
		resultList
				.add(new ServiceSearchModelConfigure(
						billOfMaterialTemplateItemSearchModel,
						ServiceEntityManagerFactoryInContext.billOfMaterialTemplateManager,
						COM_SEARCH_INTERNALITEM));
		resultList
				.add(new ServiceSearchModelConfigure(
						billOfMaterialTemplateSearchModel,
						ServiceEntityManagerFactoryInContext.billOfMaterialTemplateManager,
						COM_SEARCH_INTERNAL));
		resultList.add(new ServiceSearchModelConfigure(
				corporateCustomerSearchModel,
				ServiceEntityManagerFactoryInContext.corporateCustomerManager,
				COM_SEARCH_INTERNAL));
		resultList.add(new ServiceSearchModelConfigure(
				corporateSupplierSearchModel,
				ServiceEntityManagerFactoryInContext.corporateSupplierManager,
				COM_SEARCH_INTERNAL));
		resultList
				.add(new ServiceSearchModelConfigure(
						materialStockKeepUnitSearchModel,
						ServiceEntityManagerFactoryInContext.materialStockKeepUnitManager,
						COM_SEARCH_INTERNAL));
		resultList.add(new ServiceSearchModelConfigure(
				registeredProductSearchModel,
				ServiceEntityManagerFactoryInContext.registeredProductManager,
				COM_SEARCH_INTERNAL));
		return resultList;
	}

	public List<ServiceFieldUnion> getFieldUnionList(String searchModelName)
			throws ServiceEntityInstallationException {
		Object rawSearchModel = springContextBeanService
				.getBean(searchModelName);
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(rawSearchModel.getClass());
		List<ServiceFieldUnion> resultList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(fieldList)) {
			for (Field field : fieldList) {
				resultList.add(new ServiceFieldUnion(field.getName(), field
						.getName(), field.getType().getSimpleName()));
			}
		}
		return resultList;
	}

}
