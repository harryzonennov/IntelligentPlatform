package com.company.IntelligentPlatform.logistics.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.dto.InboundDeliverySearchModel;
import com.company.IntelligentPlatform.logistics.dto.InboundDeliveryUIModel;
import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.model.InboundItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.IServiceDocConfigConstants;
import com.company.IntelligentPlatform.common.service.ISimpleDataProviderConstants;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigOutputParaModel;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigParaProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigParaUnion;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceDocMaterialTemplateParaProxy;
import com.company.IntelligentPlatform.common.service.ServiceSimpleDataProviderException;
import com.company.IntelligentPlatform.common.model.IDocumentNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class InboundDeliveryConfigParaProxy extends ServiceDocMaterialTemplateParaProxy {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

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
			List<ServiceDocConfigParaUnion> paraUnionList = new ArrayList<>();
			// Add Union of res org uuid
			ServiceDocConfigParaUnion paraResOrgIDUnion = new ServiceDocConfigParaUnion();
			paraResOrgIDUnion
					.setFieldName(IServiceDocConfigConstants.FIELD_RESPONSIBLE_EMPLOYEEUUID);
			String fieldLabel = getDefaultDocFieldLabel(
					IServiceDocConfigConstants.FIELD_RESPONSIBLE_EMPLOYEEUUID,
					fieldLabelMap);
			paraResOrgIDUnion
					.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);
			paraResOrgIDUnion.setFieldTypeClass(String.class);
			paraResOrgIDUnion.setLabel(fieldLabel);
			paraResOrgIDUnion.setFieldLabel(fieldLabel);
			paraUnionList.add(paraResOrgIDUnion);
			
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
			simpleDataProviderFactory.pushToDataProviderMap(ISimpleDataProviderConstants.ID_CURTIMEDATA, dataProviderMap);			
			paraCreatedTimeUnion.setDataProviderMap(dataProviderMap);
			paraUnionList.add(paraCreatedTimeUnion);

			// Add Union of status
			ServiceDocConfigParaUnion statusUnion = new ServiceDocConfigParaUnion();
			statusUnion.setFieldName(IServiceDocConfigConstants.FIELD_STATUS);
			fieldLabel = getDefaultDocFieldLabel(statusUnion.getFieldName(),
					fieldLabelMap);
			statusUnion.setLabel(fieldLabel);
			statusUnion.setFieldLabel(fieldLabel);
			statusUnion.setFieldTypeClass(int.class);
			statusUnion.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);			
			statusUnion.setDropdownMap(getInboundDeliveryStatusMap());
			paraUnionList.add(statusUnion);
			
			ServiceDocConfigParaUnion refWarehouseUUIDUnion = new ServiceDocConfigParaUnion();
			refWarehouseUUIDUnion.setFieldName("refWarehouseUUID");
			fieldLabel = getDefaultDocFieldLabel(
					refWarehouseUUIDUnion.getFieldName(), fieldLabelMap);
			refWarehouseUUIDUnion.setLabel(fieldLabel);
			refWarehouseUUIDUnion.setFieldLabel(fieldLabel);
			refWarehouseUUIDUnion
					.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);
			refWarehouseUUIDUnion.setFieldTypeClass(String.class);
			paraUnionList.add(refWarehouseUUIDUnion);

			ServiceDocConfigParaUnion processDateUnion = new ServiceDocConfigParaUnion();
			processDateUnion.setFieldName("recordStoreDate");
			fieldLabel = getDefaultDocFieldLabel(
					processDateUnion.getFieldName(), fieldLabelMap);
			processDateUnion.setLabel(fieldLabel);
			processDateUnion.setFieldLabel(fieldLabel);
			processDateUnion
					.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);
			processDateUnion.setFieldTypeClass(Date.class);
			Map<String, String> dataProviderMap2 = new HashMap<String, String>();
			dataProviderMap2.put(ISimpleDataProviderConstants.ID_CURTIMEDATA, "当前时间数据源");
			simpleDataProviderFactory.pushToDataProviderMap(ISimpleDataProviderConstants.ID_CURTIMEDATA, dataProviderMap2);
			
			processDateUnion.setDataProviderMap(dataProviderMap2);
			paraUnionList.add(processDateUnion);
			List<ServiceDocConfigParaUnion> rawParaUnionList = this.getDefaultDocInputPara();
			List<ServiceDocConfigParaUnion> resultList = mergeDefaultDocParaList(rawParaUnionList, paraUnionList);
			return resultList;

		} catch (IOException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}

	@Override
	public List<ServiceEntityNode> getDocMaterialSKUItemList(
			List<ServiceEntityNode> docList, String materialSKUUUID)
			throws ServiceDocConfigureException {
		if (ServiceCollectionsHelper.checkNullList(docList)) {
			return null;
		}
		try {
			ServiceEntityNode fstSeNode = docList.get(0);
			List<ServiceEntityNode> resultItemList = new ArrayList<>();
			for (ServiceEntityNode seNode : docList) {
				List<ServiceEntityNode> inboundItemList = inboundDeliveryManager
						.getEntityNodeListByKey(seNode.getUuid(),
								IServiceEntityNodeFieldConstant.PARENTNODEUUID,
								InboundItem.NODENAME,
								fstSeNode.getClient(), null);
				if (inboundItemList != null && inboundItemList.size() > 0) {
					if (ServiceEntityStringHelper
							.checkNullString(materialSKUUUID)) {
						// In case materialSKU is null, don't need to filter
						return inboundItemList;
					} else {
						for (ServiceEntityNode tmpSENode : inboundItemList) {
							InboundItem inboundItem = (InboundItem) tmpSENode;
							if (materialSKUUUID.equals(inboundItem
									.getRefMaterialSKUUUID())) {
								resultItemList.add(inboundItem);
							}
						}
					}
				}
			}
			return resultItemList;
		} catch (ServiceEntityConfigureException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
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
		List<ServiceEntityNode> inboundItemItemList = this
				.getDocMaterialSKUItemList(docList, materialSKUUUID);
		if (ServiceCollectionsHelper.checkNullList(inboundItemItemList)) {
			return nullCoreUnit;
		}
		try {
			List<ServiceDocConfigParaUnion> serviceDocConfigParaList = this
					.getDefaultDocInputPara();
			ServiceEntityNode fstSeNode = inboundItemItemList.get(0);
			List<StorageCoreUnit> storageCoreUnitList = new ArrayList<StorageCoreUnit>();
			for (ServiceEntityNode seNode : inboundItemItemList) {
				InboundItem inboundItem = (InboundItem) seNode;
				StorageCoreUnit storageCoreUnit = new StorageCoreUnit();
				storageCoreUnit.setRefMaterialSKUUUID(inboundItem
						.getRefMaterialSKUUUID());
				storageCoreUnit.setAmount(inboundItem.getAmount());
				storageCoreUnit.setRefUnitUUID(inboundItem
						.getRefUnitUUID());
				storageCoreUnitList.add(storageCoreUnit);
			}
			// Merge and get the number
			// Merge and get the number
			StorageCoreUnit storageCoreUnit = materialStockKeepUnitManager
					.mergeStorageUnitCore(storageCoreUnitList,
							fstSeNode.getClient());
			storageCoreUnit = postAveMaterialAmount(storageCoreUnit,
					consumerModel, inputParaUnionList, serviceDocConfigParaList, null);
			return storageCoreUnit;
		} catch (MaterialException ex) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					ex.getErrorMessage());
		} catch (ServiceEntityConfigureException ex) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					ex.getMessage());
		} catch (IOException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getMessage());
		} catch (ServiceSimpleDataProviderException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getErrorMessage());
		}
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
		return "入库单";
	}
	
	public Map<Integer, String> getInboundDeliveryStatusMap() throws ServiceEntityInstallationException{
		Map<Integer, String> statusMap = serviceDropdownListHelper
				.getUIDropDownMap(InboundDeliveryUIModel.class, IDocumentNodeFieldConstant.STATUS);
		return statusMap;
	}

	@Override
	public List<ServiceEntityNode> searchDocList(SEUIComModel searchModel,
			String client) throws ServiceDocConfigureException {
		return null;

	}

	@Override
	public SEUIComModel initInputSearchModel() throws ServiceDocConfigureException {
		InboundDeliverySearchModel inboundDeliverySearchModel = new InboundDeliverySearchModel();
		return inboundDeliverySearchModel;
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
