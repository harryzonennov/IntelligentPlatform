package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocParaConsumerValueModeProxy;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardValueComparatorProxy;
import com.company.IntelligentPlatform.common.service.ServiceChartTimeSlot;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityDateHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigurePara;

@Service
public abstract class ServiceDocConfigParaProxy {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected ServiceDocParaConsumerValueModeProxy serviceDocParaConsumerValueModeProxy;

	@Autowired
	protected SimpleDataProviderFactory simpleDataProviderFactory;
	

	public abstract List<ServiceDocConfigParaUnion> getDocInputPara()
			throws ServiceDocConfigureException;

	public abstract List<ServiceDocConfigParaUnion> getDocOutputPara()
			throws ServiceDocConfigureException;

	/**
	 * Return this document/service entity type label, to be shown on service
	 * doc configure UI
	 * 
	 * @return
	 */
	public abstract String getDocTypeLabel();
	
	/**
	 * Hook Method to init search model, should be implemented in each sub-class,
	 * will be invoked by service doc configure framework.
	 * 
	 * @return searchModel instance with neccessary initialization
	 * @throws ServiceDocConfigureException
	 */
	public abstract SEUIComModel initInputSearchModel()
			throws ServiceDocConfigureException;
	
	
	/**
	 * Hook Method to implement the logic to set output consumer model value
	 * @param consumerModel
	 * @param inputSearchModel
	 * @throws ServiceDocConfigureException
	 */
	public abstract void setOutputConsumerModel(Object consumerModel,
			SEUIComModel inputSearchModel) throws ServiceDocConfigureException;

	public String getDefaultDocFieldLabel(String fieldName,
			Map<String, String> fieldLabelMap) {
		if (fieldLabelMap == null) {
			return fieldName;
		}
		return fieldLabelMap.get(fieldName);
	}

	protected Map<String, String> getFieldLabelMap() throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = ServiceDocConfigParaProxy.class.getResource("").getPath();
		String resFileName = "ServiceDocConfigParaLabel";
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	public ServiceDocConfigParaUnion filterOutParaUnion(String fieldName,
			List<ServiceDocConfigParaUnion> rawParaUnionList) {
		if (rawParaUnionList == null || rawParaUnionList.size() == 0) {
			return null;
		}
		if (ServiceEntityStringHelper.checkNullString(fieldName)) {
			return null;
		}
		for (ServiceDocConfigParaUnion serviceDocConfigParaUnion : rawParaUnionList) {
			if (fieldName.equals(serviceDocConfigParaUnion.getFieldValue())) {
				return serviceDocConfigParaUnion;
			}
		}
		return null;

	}

	/**
	 * Helper method to generate the default document input parameter union list
	 * 
	 * @return
	 * @throws IOException
	 * @throws ServiceEntityInstallationException
	 */
	public List<ServiceDocConfigParaUnion> getDefaultDocInputPara()
			throws IOException, ServiceEntityInstallationException {
		Map<String, String> fieldLabelMap = getFieldLabelMap();
		List<ServiceDocConfigParaUnion> paraUnionList = new ArrayList<ServiceDocConfigParaUnion>();
		// Add Union of res org uuid
		ServiceDocConfigParaUnion paraResOrgIDUnion = new ServiceDocConfigParaUnion();
		paraResOrgIDUnion
				.setFieldName(IServiceDocConfigConstants.FIELD_RESORG_UUID);
		String fieldLabel = getDefaultDocFieldLabel(
				IServiceDocConfigConstants.FIELD_RESORG_UUID, fieldLabelMap);
		paraResOrgIDUnion
				.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);
		paraResOrgIDUnion.setFieldTypeClass(String.class);
		paraResOrgIDUnion.setLabel(fieldLabel);
		paraResOrgIDUnion.setFieldLabel(fieldLabel);
		paraUnionList.add(paraResOrgIDUnion);

		// Add Union of res employee uuid
		ServiceDocConfigParaUnion paraResEmployeeUUIDUnion = new ServiceDocConfigParaUnion();
		paraResEmployeeUUIDUnion
				.setFieldName(IServiceDocConfigConstants.FIELD_RESPONSIBLE_EMPLOYEEUUID);
		fieldLabel = getDefaultDocFieldLabel(
				IServiceDocConfigConstants.FIELD_RESPONSIBLE_EMPLOYEEUUID,
				fieldLabelMap);
		paraResEmployeeUUIDUnion
				.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);
		paraResEmployeeUUIDUnion.setFieldTypeClass(String.class);
		paraResEmployeeUUIDUnion.setLabel(fieldLabel);
		paraResEmployeeUUIDUnion.setFieldLabel(fieldLabel);
		paraUnionList.add(paraResEmployeeUUIDUnion);

		// Add Union of Speical input data:output amount caculate mode
		ServiceDocConfigParaUnion outputAmountModeUnion = new ServiceDocConfigParaUnion();
		outputAmountModeUnion
				.setFieldName(IServiceDocConfigConstants.FIELD_OUTPUTAMOUNT_MODE);
		fieldLabel = getDefaultDocFieldLabel(
				IServiceDocConfigConstants.FIELD_OUTPUTAMOUNT_MODE,
				fieldLabelMap);
		outputAmountModeUnion
				.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);
		outputAmountModeUnion.setFieldTypeClass(String.class);
		outputAmountModeUnion.setLabel(fieldLabel);
		outputAmountModeUnion.setFieldLabel(fieldLabel);
		outputAmountModeUnion.setDropdownMap(getOutputAmountModeMap());
		Map<Integer, String> tmpConsumerValueModeMap = new HashMap<Integer, String>();
		String labelSetValue = serviceDocParaConsumerValueModeProxy
				.getValueModeLabel(ServiceDocConfigurePara.INPUT_VALUEMODE_SETVALUE);
		tmpConsumerValueModeMap
				.put(ServiceDocConfigurePara.INPUT_VALUEMODE_SETVALUE,
						labelSetValue);
		outputAmountModeUnion.setConsumerValueModeMap(tmpConsumerValueModeMap);
		paraUnionList.add(outputAmountModeUnion);

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
		paraUnionList.add(statusUnion);
		return paraUnionList;
	}

	public Map<Integer, String> getOutputAmountModeMap() throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = ServiceDocConfigParaProxy.class.getResource("").getPath();
		String resFileName = "ServiceDocConfigParaUnion_outAmountMode";
		Map<Integer, String> outAmountMap = serviceDropdownListHelper
				.getDropDownMapInteger(path, resFileName, locale);
		return outAmountMap;
	}

	/**
	 * Helper method to merge 2 para list by checking duplicate by field name and
	 * post data will overwritten the raw list
	 * 
	 * @param preList
	 *            :raw para list from super class
	 * @param postList
	 *            : post implemented para list from detailed implementation
	 *            class
	 * @return
	 */
	protected List<ServiceDocConfigParaUnion> mergeDefaultDocParaList(
			List<ServiceDocConfigParaUnion> preList,
			List<ServiceDocConfigParaUnion> postList) {
		if (preList == null || preList.size() == 0) {
			return postList;
		}
		if (postList == null || postList.size() == 0) {
			return preList;
		}
		List<ServiceDocConfigParaUnion> resultList = new ArrayList<ServiceDocConfigParaUnion>();
		resultList.addAll(postList);
		for (ServiceDocConfigParaUnion serviceDocConfigParaUnion : preList) {
			boolean hitFlag = false;
			for (ServiceDocConfigParaUnion tmpConfigParaUnion : postList) {
				if (serviceDocConfigParaUnion.getFieldName().equals(
						tmpConfigParaUnion.getFieldName())) {
					hitFlag = true;
					break;
				}
			}
			if (!hitFlag) {
				resultList.add(serviceDocConfigParaUnion);
			}
		}
		return resultList;
	}

	public int getOutAmountMode(List<ServiceEntityNode> inputParaUnionList)
			throws ServiceDocConfigureException {
		if (ServiceCollectionsHelper.checkNullList(inputParaUnionList)) {
			return 0;
		}
		ServiceDocConfigurePara serviceOutAmountModePara = null;
		for (ServiceEntityNode seNode : inputParaUnionList) {
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) seNode;
			if (serviceDocConfigurePara.getParaDirection() == ServiceDocConfigParaUnion.DIRECT_OUTPUT) {
				continue;
			}
			if (serviceDocConfigurePara.getResourceFieldName().equals(
					IServiceDocConfigConstants.FIELD_OUTPUTAMOUNT_MODE)) {
				serviceOutAmountModePara = serviceDocConfigurePara;
				break;
			}
		}
		if (serviceOutAmountModePara == null) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.TYPE_SYSTEM_WRONG);
		}
		int outAmountMode = Integer.parseInt(serviceOutAmountModePara
				.getFixValue());
		return outAmountMode;
	}

	public List<ServiceEntityNode> getInputDateTypeList(
			List<ServiceEntityNode> inputParaUnionList,
			List<ServiceDocConfigParaUnion> serviceDocConfigParaList) {
		if (!ServiceCollectionsHelper.checkNullList(inputParaUnionList)) {
			return null;
		}
		List<ServiceDocConfigParaUnion> resultList = new ArrayList<ServiceDocConfigParaUnion>();
		for (ServiceEntityNode seNode : inputParaUnionList) {
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) seNode;
			if (serviceDocConfigurePara.getParaDirection() == ServiceDocConfigParaUnion.DIRECT_OUTPUT) {
				continue;
			}
			ServiceDocConfigParaUnion serviceDocConfigParaUnion = filteroutParaUnion(
					serviceDocConfigParaList,
					serviceDocConfigurePara.getResourceFieldName());
			if (serviceDocConfigParaUnion == null) {
				continue;
			}
			if (Date.class.getSimpleName().equals(
					serviceDocConfigParaUnion.getFieldTypeClass()
							.getSimpleName())) {
				resultList.add(serviceDocConfigParaUnion);
			}
		}
		return null;
	}

	/**
	 * Filter out the ServiceDocConfigParaUnion instance online by field name
	 * 
	 * @param rawInputUnionParaList
	 * @param fieldName
	 * @return
	 */
	public ServiceDocConfigParaUnion filteroutParaUnion(
			List<ServiceDocConfigParaUnion> rawInputUnionParaList,
			String fieldName) {
		if (rawInputUnionParaList != null && rawInputUnionParaList.size() > 0) {
			for (ServiceDocConfigParaUnion serviceDocConfigParaUnion : rawInputUnionParaList) {
				if (fieldName.equals(serviceDocConfigParaUnion.getFieldName())) {
					return serviceDocConfigParaUnion;
				}
			}
		}
		return null;
	}

	public StorageCoreUnit postAveMaterialAmount(
			StorageCoreUnit rawMaterialAmount, SEUIComModel consumerModel,
			List<ServiceEntityNode> inputParaUnionList,
			List<ServiceDocConfigParaUnion> serviceDocConfigParaList, LogonInfo logonInfo)
			throws ServiceDocConfigureException,
			ServiceSimpleDataProviderException {
		List<ServiceChartTimeSlot> resultAveTimeSlotList = getComparableDateInputPara(
				consumerModel, inputParaUnionList, serviceDocConfigParaList, logonInfo);
		if (ServiceCollectionsHelper.checkNullList(resultAveTimeSlotList)) {
			// should raise excepiton

		}
		ServiceChartTimeSlot serviceChartTimeSlot = resultAveTimeSlotList
				.get(0);
		int outAmountModePara = getOutAmountMode(inputParaUnionList);
		double diffPara = 1;
		switch (outAmountModePara) {
		case ServiceDocConfigParaUnion.OUTAMOUNT_MODE_AVE_DAY:
			diffPara = ServiceEntityDateHelper.getDiffDays(
					serviceChartTimeSlot.getStartDate(),
					serviceChartTimeSlot.getEndDate());
			break;
		case ServiceDocConfigParaUnion.OUTAMOUNT_MODE_AVE_WEEK:
			diffPara = ServiceEntityDateHelper.getDiffDays(
					serviceChartTimeSlot.getStartDate(),
					serviceChartTimeSlot.getEndDate());
			break;
		case ServiceDocConfigParaUnion.OUTAMOUNT_MODE_AVE_MONTH:
			diffPara = ServiceEntityDateHelper.getDiffDays(
					serviceChartTimeSlot.getStartDate(),
					serviceChartTimeSlot.getEndDate());
			break;
		default:
			diffPara = 1;
		}
		rawMaterialAmount.setAmount(rawMaterialAmount.getAmount() / diffPara);
		return rawMaterialAmount;
	}

	public List<ServiceChartTimeSlot> getComparableDateInputPara(
			SEUIComModel consumerModel,
			List<ServiceEntityNode> inputParaUnionList,
			List<ServiceDocConfigParaUnion> serviceDocConfigParaList, LogonInfo logonInfo)
			throws ServiceSimpleDataProviderException,
			ServiceDocConfigureException {
		List<ServiceEntityNode> rawInputDataParaList = getInputDateTypeList(
				inputParaUnionList, serviceDocConfigParaList);
		if (!ServiceCollectionsHelper.checkNullList(rawInputDataParaList)) {
			return null;
		}
		List<ServiceChartTimeSlot> resultTimeslotList = new ArrayList<ServiceChartTimeSlot>();
		for (ServiceEntityNode seNode : inputParaUnionList) {
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) seNode;
			if (serviceDocConfigurePara.getFixValueOperator() == StandardValueComparatorProxy.OPERATOR_GREATER
					|| serviceDocConfigurePara.getFixValueOperator() == StandardValueComparatorProxy.OPERATOR_GREATER_EQ) {
				Date startDate = getFixValueDateUnion(serviceDocConfigurePara,
						consumerModel, logonInfo,false);
				Date endDate = new Date();
				ServiceChartTimeSlot serviceChartTimeSlot = new ServiceChartTimeSlot();
				serviceChartTimeSlot.setStartDate(startDate);
				serviceChartTimeSlot.setEndDate(endDate);
				resultTimeslotList.add(serviceChartTimeSlot);
			}
			if (serviceDocConfigurePara.getFixValueOperator() == StandardValueComparatorProxy.OPERATOR_BETWEEN) {
				Date startDate = getFixValueDateUnion(serviceDocConfigurePara,
						consumerModel, logonInfo,false);
				Date endDate = getFixValueDateUnion(serviceDocConfigurePara,
						consumerModel, logonInfo, true);
				ServiceChartTimeSlot serviceChartTimeSlot = new ServiceChartTimeSlot();
				serviceChartTimeSlot.setStartDate(startDate);
				serviceChartTimeSlot.setEndDate(endDate);
				resultTimeslotList.add(serviceChartTimeSlot);
			}
			if (serviceDocConfigurePara.getFixValueOperator() == StandardValueComparatorProxy.OPERATOR_LESS
					|| serviceDocConfigurePara.getFixValueOperator() == StandardValueComparatorProxy.OPERATOR_LESS_EQ) {
				Date endDate = getFixValueDateUnion(serviceDocConfigurePara,
						consumerModel, logonInfo,true);
				Date startDate = ServiceEntityDateHelper.getEarliestDate();
				ServiceChartTimeSlot serviceChartTimeSlot = new ServiceChartTimeSlot();
				serviceChartTimeSlot.setStartDate(startDate);
				serviceChartTimeSlot.setEndDate(endDate);
				resultTimeslotList.add(serviceChartTimeSlot);
			}
		}
		return resultTimeslotList;
	}

	/**
	 * Core logic to get fix value date type value union
	 * 
	 * @param serviceDocConfigurePara
	 * @param consumerModel
	 * @return
	 * @throws ServiceSimpleDataProviderException
	 * @throws ServiceDocConfigureException
	 */
	public Date getFixValueDateUnion(
			ServiceDocConfigurePara serviceDocConfigurePara,
			SEUIComModel consumerModel, LogonInfo logonInfo, boolean highFlag)
			throws ServiceSimpleDataProviderException,
			ServiceDocConfigureException {
		if (serviceDocConfigurePara.getConsumerValueMode() == ServiceDocConfigurePara.INPUT_VALUEMODE_COMDATASOURCE) {
			// In case com data source
			ServiceSimpleDataProviderTemplate dataProviderTemplate = simpleDataProviderFactory
					.getSimpleDataProvider(serviceDocConfigurePara
							.getDataSourceProviderID());
			if (dataProviderTemplate == null) {
				// should raise exception
			}
			if (highFlag) {
				SimpleDataOffsetUnion simpleDataOffsetUnion = new SimpleDataOffsetUnion();
				simpleDataOffsetUnion
						.setOffsetDirection(serviceDocConfigurePara
								.getDataOffsetDirectionHigh());
				simpleDataOffsetUnion.setOffsetUnit(serviceDocConfigurePara
						.getDataOffsetUnitHigh());
				simpleDataOffsetUnion.setOffsetValue(serviceDocConfigurePara
						.getDataOffsetValueHigh());
				return (Date) dataProviderTemplate
						.getResultData(simpleDataOffsetUnion, logonInfo);
			} else {
				SimpleDataOffsetUnion simpleDataOffsetUnion = new SimpleDataOffsetUnion();
				simpleDataOffsetUnion
						.setOffsetDirection(serviceDocConfigurePara
								.getDataOffsetDirection());
				simpleDataOffsetUnion.setOffsetUnit(serviceDocConfigurePara
						.getDataOffsetUnit());
				simpleDataOffsetUnion.setOffsetValue(serviceDocConfigurePara
						.getDataOffsetValue());
				return (Date) dataProviderTemplate
						.getResultData(simpleDataOffsetUnion, logonInfo);
			}
		}
		if (serviceDocConfigurePara.getConsumerValueMode() == ServiceDocConfigurePara.INPUT_VALUEMODE_PASSVALUE) {
			// In case pass value from consumer mode
			// !!Pay Attention!! No process for high value and low value
			List<Field> fieldList = ServiceEntityFieldsHelper
					.getFieldsList(consumerModel.getClass());
			try {
				Object inputValue = ServiceEntityFieldsHelper
						.getReflectiveObjectValue(consumerModel, fieldList,
								serviceDocConfigurePara.getConsumerFieldName());
				return (Date) inputValue;
			} catch (IllegalArgumentException e) {
				throw new ServiceDocConfigureException(
						ServiceDocConfigureException.PARA_SYSTEM_WRONG,
						e.getMessage());
			} catch (IllegalAccessException e) {
				throw new ServiceDocConfigureException(
						ServiceDocConfigureException.PARA_SYSTEM_WRONG,
						e.getMessage());
			} catch (NoSuchFieldException e) {
				throw new ServiceDocConfigureException(
						ServiceDocConfigureException.PARA_SYSTEM_WRONG,
						e.getMessage());
			}
		}
		if (serviceDocConfigurePara.getConsumerValueMode() == ServiceDocConfigurePara.INPUT_VALUEMODE_SETVALUE) {
			if (highFlag) {
				return serviceDocConfigurePara.getFixValueDateHigh();
			} else {
				return serviceDocConfigurePara.getFixValueDate();
			}
		}
		throw new ServiceDocConfigureException(
				ServiceDocConfigureException.TYPE_SYSTEM_WRONG);
	}

	/**
	 * Helper method to generate the default document input parameter union list
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<ServiceDocConfigParaUnion> getDefaultDocOutputPara()
			throws IOException {
		Map<String, String> fieldLabelMap = getFieldLabelMap();
		List<ServiceDocConfigParaUnion> paraUnionList = new ArrayList<ServiceDocConfigParaUnion>();
		// Add Union of out doc list
		ServiceDocConfigParaUnion serviceDocListUnion = new ServiceDocConfigParaUnion();
		serviceDocListUnion
				.setFieldName(IServiceDocConfigConstants.PARA_SERDOC_LIST);
		String fieldLabel = getDefaultDocFieldLabel(
				IServiceDocConfigConstants.PARA_SERDOC_LIST, fieldLabelMap);
		serviceDocListUnion
				.setParaDirection(ServiceDocConfigParaUnion.DIRECT_OUTPUT);
		serviceDocListUnion.setFieldTypeClass(List.class);
		serviceDocListUnion.setLabel(fieldLabel);
		serviceDocListUnion.setFieldLabel(fieldLabel);
		paraUnionList.add(serviceDocListUnion);

		// Add Union of material SKU List
		ServiceDocConfigParaUnion materialSKUListUnion = new ServiceDocConfigParaUnion();
		materialSKUListUnion
				.setFieldName(IServiceDocConfigConstants.PARA_MATERIALSKU_LIST);

		fieldLabel = getDefaultDocFieldLabel(
				IServiceDocConfigConstants.PARA_MATERIALSKU_LIST, fieldLabelMap);
		materialSKUListUnion.setLabel(fieldLabel);
		materialSKUListUnion.setFieldLabel(fieldLabel);
		materialSKUListUnion
				.setParaDirection(ServiceDocConfigParaUnion.DIRECT_OUTPUT);
		materialSKUListUnion.setFieldTypeClass(List.class);
		paraUnionList.add(materialSKUListUnion);

		// Add Union of material SKU amount
		ServiceDocConfigParaUnion materialSKUAmountUnion = new ServiceDocConfigParaUnion();
		materialSKUAmountUnion
				.setFieldName(IServiceDocConfigConstants.PARA_MATERIALSKU_AMOUNT);

		fieldLabel = getDefaultDocFieldLabel(
				IServiceDocConfigConstants.PARA_MATERIALSKU_AMOUNT,
				fieldLabelMap);
		materialSKUAmountUnion.setLabel(fieldLabel);
		materialSKUAmountUnion.setFieldLabel(fieldLabel);
		materialSKUAmountUnion.setFieldTypeClass(double.class);
		materialSKUAmountUnion
				.setParaDirection(ServiceDocConfigParaUnion.DIRECT_OUTPUT);
		paraUnionList.add(materialSKUAmountUnion);

		// Add Union of material SKU Unit
		ServiceDocConfigParaUnion materialSKUUnitUnion = new ServiceDocConfigParaUnion();
		materialSKUUnitUnion
				.setFieldName(IServiceDocConfigConstants.PARA_MATERIALSKU_UNIT);

		fieldLabel = getDefaultDocFieldLabel(
				IServiceDocConfigConstants.PARA_MATERIALSKU_UNIT, fieldLabelMap);
		materialSKUUnitUnion.setLabel(fieldLabel);
		materialSKUUnitUnion.setFieldTypeClass(String.class);
		materialSKUUnitUnion
				.setParaDirection(ServiceDocConfigParaUnion.DIRECT_OUTPUT);
		paraUnionList.add(materialSKUUnitUnion);

		return paraUnionList;
	}

}
