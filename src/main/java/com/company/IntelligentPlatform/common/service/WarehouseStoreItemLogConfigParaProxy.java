package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.IServiceDocConfigConstants;
import com.company.IntelligentPlatform.common.service.ISimpleDataProviderConstants;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigOutputParaModel;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigParaProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigParaUnion;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceDocMaterialTemplateParaProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;

@Service
public class WarehouseStoreItemLogConfigParaProxy extends ServiceDocMaterialTemplateParaProxy{

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	protected Map<String, String> getDeliveryFieldLabelMap() throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = ServiceDocConfigParaProxy.class.getResource("").getPath();
		String resFileName = "DeliveryConfigParaLabel";
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	@Override
	public List<ServiceDocConfigParaUnion> getDocInputPara()
			throws ServiceDocConfigureException {
		try {
			Map<String, String> fieldLabelMap = getDeliveryFieldLabelMap();
			List<ServiceDocConfigParaUnion> paraUnionList = new ArrayList<ServiceDocConfigParaUnion>();
			// Add Union of warehouseUUID uuid
			ServiceDocConfigParaUnion warehouseUUIDUnion = new ServiceDocConfigParaUnion();
			warehouseUUIDUnion
					.setFieldName(IServiceDocConfigConstants.FIELD_ROOTNODEUUID);
			String fieldLabel = "warehouseUUID";
			warehouseUUIDUnion
					.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);
			warehouseUUIDUnion.setFieldTypeClass(String.class);
			warehouseUUIDUnion.setLabel(fieldLabel);
			warehouseUUIDUnion.setFieldLabel(fieldLabel);
			paraUnionList.add(warehouseUUIDUnion);

			// Add Union of created time low
			ServiceDocConfigParaUnion paraCreatedTimeUnion = new ServiceDocConfigParaUnion();
			paraCreatedTimeUnion
					.setFieldName(IServiceDocConfigConstants.FIELD_CREATEDTIME);

			fieldLabel = getDefaultDocFieldLabel(
					IServiceDocConfigConstants.FIELD_CREATEDTIME, fieldLabelMap);
			paraCreatedTimeUnion.setLabel(fieldLabel);
			paraCreatedTimeUnion.setFieldLabel(fieldLabel);
			paraCreatedTimeUnion.setFieldTypeClass(Date.class);
			paraCreatedTimeUnion
					.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);
			Map<String, String> dataProviderMap = new HashMap<>();
			simpleDataProviderFactory.pushToDataProviderMap(
					ISimpleDataProviderConstants.ID_CURTIMEDATA,
					dataProviderMap);
			paraUnionList.add(paraCreatedTimeUnion);

			// Add Union of material SKU UUID
			ServiceDocConfigParaUnion materialSKUUUIDUnion = new ServiceDocConfigParaUnion();
			materialSKUUUIDUnion
					.setFieldName("refMaterialSKUUUID");

			fieldLabel = "refMaterialSKUUUID";
			materialSKUUUIDUnion.setLabel(fieldLabel);
			materialSKUUUIDUnion.setFieldLabel(fieldLabel);
			materialSKUUUIDUnion.setFieldTypeClass(String.class);
			materialSKUUUIDUnion
					.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);	
			paraUnionList.add(materialSKUUUIDUnion);
			return paraUnionList;
		} catch (IOException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}

	/**
	 * Filter service entity node list by input the document list and filter
	 * material SKU
	 * 
	 * @param docList
	 *            :raw service node doc list
	 * @param materialSKUUUID
	 *            :in case need to filter by material SKUUUID
	 * @return
	 * @throws ServiceDocConfigureException
	 */
	@Override
	public List<ServiceEntityNode> getDocMaterialSKUItemList(
			List<ServiceEntityNode> docList, String materialSKUUUID)
			throws ServiceDocConfigureException {
		return null;
	}

	@Override
	public StorageCoreUnit getMaterialSKUAmount(SEUIComModel consumerModel,
			List<ServiceEntityNode> docList,
			List<ServiceEntityNode> inputParaUnionList, String materialSKUUUID)
			throws ServiceDocConfigureException {
		StorageCoreUnit nullCoreUnit = new StorageCoreUnit();
		nullCoreUnit.setAmount(0);
		nullCoreUnit.setRefMaterialSKUUUID(materialSKUUUID);
		if (ServiceCollectionsHelper.checkNullList(docList)) {
			return nullCoreUnit;
		}
		List<ServiceEntityNode> warehouseStoreItemLogList = this
				.getDocMaterialSKUItemList(docList, materialSKUUUID);
		if (ServiceCollectionsHelper.checkNullList(warehouseStoreItemLogList)) {
			return nullCoreUnit;
		}
		return null;

	}

	@Override
	public List<ServiceDocConfigParaUnion> getDocOutputPara()
			throws ServiceDocConfigureException {
		try {
			List<ServiceDocConfigParaUnion> paraUnionList = this
					.getDefaultDocOutputPara();
			return paraUnionList;
		} catch (IOException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}
	

	@Override
	public String getDocTypeLabel() {
		return "库存日志";
	}

	@Override
	public List<ServiceEntityNode> searchDocList(SEUIComModel searchModel,
			String client) throws ServiceDocConfigureException {
		return null;
	}

	@Override
	public SEUIComModel initInputSearchModel() throws ServiceDocConfigureException {
		return null;
	}

	@Override
	public ServiceDocConfigOutputParaModel generateOutputParaModel(
			SEUIComModel consumerModel, List<ServiceEntityNode> docList,
			List<ServiceEntityNode> inputParaUnionList, String materialSKUUUID)
			throws ServiceDocConfigureException {
		ServiceDocConfigOutputParaModel outputParaModel = new ServiceDocConfigOutputParaModel();
		List<ServiceEntityNode> materialSKUItemList = getDocMaterialSKUItemList(
				docList, materialSKUUUID);
		outputParaModel.setServiceDocList(docList);
		outputParaModel.setDocMaterialSKUItemList(materialSKUItemList);
		StorageCoreUnit materialStoreUnit = getMaterialSKUAmount(consumerModel,
				docList, inputParaUnionList, materialSKUUUID);
		outputParaModel.setMaterialSKUAmount(materialStoreUnit.getAmount());
		outputParaModel.setMaterialSKUUnit(materialStoreUnit.getRefUnitUUID());
		return outputParaModel;
	}

	@Override
	public void setOutputConsumerModel(Object consumerModel,
			SEUIComModel inputSearchModel) throws ServiceDocConfigureException {
		// TODO Auto-generated method stub
		
	}

}
