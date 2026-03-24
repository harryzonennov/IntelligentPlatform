package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceChartDataSeries;
import com.company.IntelligentPlatform.common.service.ServiceChartHelper;
import com.company.IntelligentPlatform.common.service.ServiceChartItemUnion;
import com.company.IntelligentPlatform.common.service.ServiceChartTimeSlot;
import com.company.IntelligentPlatform.common.service.ServiceComChartModel;
import com.company.IntelligentPlatform.common.service.ServiceXTimeSlotChartModel;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigParaProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigParaUnion;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureException;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;

/**
 * Service Para proxy class for high chart
 * 
 * @author Zhang,Hang
 *
 */
@Service
public class ServiceHighChartXTimeSlotParaProxy extends
		ServiceDocConfigParaProxy {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;


	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Override
	public List<ServiceDocConfigParaUnion> getDocInputPara()
			throws ServiceDocConfigureException {
		List<ServiceDocConfigParaUnion> paraUnionList = new ArrayList<ServiceDocConfigParaUnion>();
		// Add Union of object uuid
		ServiceDocConfigParaUnion objectKeyUnion = new ServiceDocConfigParaUnion();
		objectKeyUnion.setFieldName(ServiceXTimeSlotChartModel.FIELD_OBJECTKEY);
		String fieldLabel = ServiceXTimeSlotChartModel.FIELD_OBJECTKEY;
		objectKeyUnion.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);
		objectKeyUnion.setFieldTypeClass(String.class);
		objectKeyUnion.setLabel(fieldLabel);
		objectKeyUnion.setFieldLabel(fieldLabel);
		objectKeyUnion.setMandatoryFlag(true);
		paraUnionList.add(objectKeyUnion);

		// Add Union of object name
		ServiceDocConfigParaUnion objectNameUnion = new ServiceDocConfigParaUnion();
		objectNameUnion
				.setFieldName(ServiceXTimeSlotChartModel.FIELD_OBJECTNAME);
		fieldLabel = ServiceXTimeSlotChartModel.FIELD_OBJECTNAME;
		objectNameUnion
				.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);
		objectNameUnion.setFieldTypeClass(String.class);
		objectNameUnion.setLabel(fieldLabel);
		objectNameUnion.setFieldLabel(fieldLabel);
		paraUnionList.add(objectNameUnion);

		// Add Union of startTime
		ServiceDocConfigParaUnion startTimeUnion = new ServiceDocConfigParaUnion();
		startTimeUnion.setFieldName(ServiceXTimeSlotChartModel.FIELD_STARTTIME);
		fieldLabel = ServiceXTimeSlotChartModel.FIELD_STARTTIME;
		startTimeUnion.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);
		startTimeUnion.setFieldTypeClass(Date.class);
		startTimeUnion.setLabel(fieldLabel);
		startTimeUnion.setFieldLabel(fieldLabel);
		startTimeUnion.setMandatoryFlag(true);
		paraUnionList.add(startTimeUnion);

		// Add Union of endTime
		ServiceDocConfigParaUnion endTimeUnion = new ServiceDocConfigParaUnion();
		endTimeUnion.setFieldName(ServiceXTimeSlotChartModel.FIELD_ENDTIME);
		fieldLabel = ServiceXTimeSlotChartModel.FIELD_ENDTIME;
		endTimeUnion.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);
		endTimeUnion.setFieldTypeClass(Date.class);
		endTimeUnion.setLabel(fieldLabel);
		endTimeUnion.setFieldLabel(fieldLabel);
		endTimeUnion.setMandatoryFlag(true);
		paraUnionList.add(endTimeUnion);

		// Add Union of time unit
		ServiceDocConfigParaUnion timeUnitUnion = new ServiceDocConfigParaUnion();
		timeUnitUnion.setFieldName(ServiceXTimeSlotChartModel.FIELD_TIMEUNIT);
		fieldLabel = ServiceXTimeSlotChartModel.FIELD_TIMEUNIT;
		timeUnitUnion.setParaDirection(ServiceDocConfigParaUnion.DIRECT_INPUT);
		timeUnitUnion.setFieldTypeClass(int.class);
		timeUnitUnion.setLabel(fieldLabel);
		timeUnitUnion.setFieldLabel(fieldLabel);
		timeUnitUnion.setMandatoryFlag(true);
		paraUnionList.add(timeUnitUnion);

		return paraUnionList;
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
		return "ServiceHighChart报表模板";
	}

	/**
	 * Hook Method to init search model, should be implemented in each
	 * sub-class, will be invoked by service doc configure framework.
	 * 
	 * @return searchModel instance with neccessary initialization
	 * @throws ServiceDocConfigureException
	 */
	@Override
	public SEUIComModel initInputSearchModel()
			throws ServiceDocConfigureException {
		ServiceXTimeSlotChartModel serviceXTimeSlotChartModel = new ServiceXTimeSlotChartModel();
		return serviceXTimeSlotChartModel;
	}

	@Override
	public void setOutputConsumerModel(Object consumerModel,
			SEUIComModel inputSearchModel) throws ServiceDocConfigureException{
		ServiceComChartModel serviceComChartModel = (ServiceComChartModel) consumerModel;
		ServiceXTimeSlotChartModel serviceXTimeSlotChartModel = (ServiceXTimeSlotChartModel) inputSearchModel;
		generateChartModelCore(
				serviceComChartModel,
				serviceXTimeSlotChartModel.getTimeUnit(),
				serviceXTimeSlotChartModel.getRefSeUIModelList(), serviceXTimeSlotChartModel.getStartTime(),
				serviceXTimeSlotChartModel.getEndTime(), serviceXTimeSlotChartModel.getAxisTitle(), serviceXTimeSlotChartModel.getObjectNameField(), serviceXTimeSlotChartModel.getObjectKeyField(),
				serviceXTimeSlotChartModel.getObjectAmountField(), serviceXTimeSlotChartModel.getTimeField());
	}

	protected void generateChartModelCore(ServiceComChartModel serviceComChartModel, int unitTime,
			List<SEUIComModel> rawSEUIModelList, Date startDate, Date endDate,
			String axisTitle, String objectNameField, String objectKeyField,
			String objectAmountField, String timeField){
		serviceComChartModel.setyAxisTitle(axisTitle);
		List<String> categories = new ArrayList<String>();
		List<ServiceChartDataSeries> dataSeries = new ArrayList<ServiceChartDataSeries>();
		List<ServiceChartTimeSlot> tsList = ServiceChartHelper
				.genChartTimeSlots(unitTime, startDate, endDate);
		String chartTitle = null;
		// build empty data series list
		for (SEUIComModel seUIModel : rawSEUIModelList) {
			try {
				Object objectKey = ServiceEntityFieldsHelper
						.getReflectiveObjectValue(seUIModel, objectKeyField);
				Object objectName = ServiceEntityFieldsHelper
						.getReflectiveObjectValue(seUIModel, objectNameField);
				ServiceChartDataSeries serviceChartDataSeries = getDataSeriesByObjectUUID(
						dataSeries, objectKey.toString());
				if (serviceChartDataSeries == null) {
					serviceChartDataSeries = new ServiceChartDataSeries();
					serviceChartDataSeries.setObjectName(objectName.toString());
					serviceChartDataSeries.setObjectUUID(objectKey.toString());
					dataSeries.add(serviceChartDataSeries);
				}
			} catch (IllegalArgumentException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			} catch (NoSuchFieldException e) {
				continue;
			}
			
		}
		List<ServiceChartItemUnion> resultChartList = new ArrayList<ServiceChartItemUnion>();
		for (int i = 0; i < tsList.size(); i++) {
			// build the time slot label
			ServiceChartTimeSlot tsSlot = tsList.get(i);
			categories.add(DefaultDateFormatConstant.DATE_FORMAT.format(tsSlot
					.getStartDate()));
			Map<String, ServiceChartItemUnion> tmpMap = fillDataIntoTimeSlot(
					startDate, endDate, timeField, objectKeyField,
					objectAmountField, rawSEUIModelList);
			if (tmpMap == null) {
				continue;
			}
			// Set the timeslot index
			Set<String> keySet = tmpMap.keySet();
			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String key = it.next();
				ServiceChartItemUnion serviceChartItemUnion = tmpMap.get(key);
				serviceChartItemUnion.setTimeSlotIndex(i);
				serviceChartItemUnion.setStartDate(tsSlot.getStartDate());
				serviceChartItemUnion.setEndDate(tsSlot.getEndDate());
				resultChartList.add(serviceChartItemUnion);

			}
		}
		// Post process each store item chart union, reset the amount for 0
		// value
		for (ServiceChartDataSeries series : dataSeries) {
			// TODO to analyze and implement this logic again
			// warehouseStoreItemLogChartHelper.mergeEmptyValueMainEntry(
			// series.getObjectUUID(), resultChartList, tsList.size());
		}
		for (ServiceChartItemUnion serviceChartItemUnion : resultChartList) {
			mergeIntoDataSeries(dataSeries,
					serviceChartItemUnion.getObjectKey(), serviceChartItemUnion);
		}
		// set title as compound name of Account title
		serviceComChartModel.setTitle(chartTitle);
		serviceComChartModel.setCategories(categories);
		serviceComChartModel.setDataSeries(dataSeries);		
	}

	protected ServiceChartDataSeries getDataSeriesByObjectUUID(
			List<ServiceChartDataSeries> dataSeries, String objectUUID) {
		if (dataSeries == null || dataSeries.size() == 0) {
			return null;
		}
		for (ServiceChartDataSeries series : dataSeries) {
			if (objectUUID.equals(series.getObjectUUID())) {
				return series;
			}
		}
		return null;
	}

	public void mergeIntoDataSeries(List<ServiceChartDataSeries> dataSeries,
			String objectKeyValue, ServiceChartItemUnion serviceChartItemUnion) {
		for (ServiceChartDataSeries serviceChartDataSeries : dataSeries) {
			if (objectKeyValue.equals(serviceChartDataSeries.getObjectUUID())) {
				serviceChartDataSeries.getValueList().add(
						serviceChartItemUnion.getAverageAmount());
			}
		}
	}

	protected Date getRawObjectDateValue(SEUIComModel seUIModel,
			String timeField) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException {
		Object timeObject = ServiceEntityFieldsHelper.getReflectiveObjectValue(
				seUIModel, timeField);
		if (timeObject != null) {
			return (Date) timeObject;
		}
		return null;
	}

	protected double getRawObjectDoubleValue(SEUIComModel seUIModel,
			String fieldName) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException {
		Object valueObject = ServiceEntityFieldsHelper
				.getReflectiveObjectValue(seUIModel, fieldName);
		if (valueObject != null) {
			Double doubleObject = new Double(valueObject.toString());
			return doubleObject.doubleValue();
		}
		return 0;
	}

	/**
	 * [Internal method] to add new empty item chart union In case the UIModel
	 * can not be found by object key
	 * 
	 * @param result
	 * @param objectKeyValue
	 */
	protected void setEmptyItemChartUnion(
			Map<String, ServiceChartItemUnion> result, String objectKeyValue) {
		ServiceChartItemUnion serviceChartItemUnion = new ServiceChartItemUnion();
		serviceChartItemUnion.setArriveIndex(1);
		serviceChartItemUnion.setAllAmount(0);
		serviceChartItemUnion.setObjectKey(objectKeyValue);
		result.put(objectKeyValue, serviceChartItemUnion);
	}

	/**
	 * Core logic to fill different data into timeslot, while accumulate
	 * allAmount in different timeslot
	 * 
	 * @param startDate
	 * @param endDate
	 * @param rawStoreItemLogList
	 * @return
	 */
	public Map<String, ServiceChartItemUnion> fillDataIntoTimeSlot(
			Date startDate, Date endDate, String timeField,
			String objectKeyField, String objectAmountField,
			List<SEUIComModel> rawSEUIModelList) {
		Map<String, ServiceChartItemUnion> result = new HashMap<String, ServiceChartItemUnion>();
		if (rawSEUIModelList == null || rawSEUIModelList.size() == 0) {
			return null;
		}
		for (SEUIComModel seUIModel : rawSEUIModelList) {
			Date objectTime = null;
			try {
				objectTime = getRawObjectDateValue(seUIModel, timeField);
			} catch (IllegalArgumentException ex) {
				continue;
			} catch (IllegalAccessException ex) {
				continue;
			} catch (NoSuchFieldException ex) {
				continue;
			}
			if (objectTime == null) {
				continue;
			}
			long toStartDiff = startDate.getTime() - objectTime.getTime();
			long toEndDiff = objectTime.getTime() - endDate.getTime();
			Object objectKeyValue;
			try {
				objectKeyValue = ServiceEntityFieldsHelper
						.getReflectiveObjectValue(seUIModel, objectKeyField);
			} catch (IllegalArgumentException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			} catch (NoSuchFieldException e) {
				continue;
			}
			if (objectKeyValue == null) {
				continue;
			}
			double amount;
			try {
				amount = getRawObjectDoubleValue(seUIModel, objectAmountField);
			} catch (IllegalArgumentException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			} catch (NoSuchFieldException e) {
				continue;
			}
			if (toStartDiff > 0 || toEndDiff > 0) {
				// Setting the empty warehouse store item log
				if (result.get(objectKeyValue) == null) {
					setEmptyItemChartUnion(result, objectKeyValue.toString());
				}
				continue;
			}
			ServiceChartItemUnion serviceChartItemUnion = result
					.get(objectKeyValue);
			if (serviceChartItemUnion == null) {
				serviceChartItemUnion = new ServiceChartItemUnion();
				serviceChartItemUnion.setArriveIndex(1);
				serviceChartItemUnion.setAllAmount(amount);
				serviceChartItemUnion.setObjectKey(objectKeyValue.toString());
				List<SEUIComModel> refSeUIModelList = serviceChartItemUnion
						.getRefSeUIModelList();
				refSeUIModelList.add(seUIModel);
				serviceChartItemUnion.setRefSeUIModelList(refSeUIModelList);
				result.put(objectKeyValue.toString(), serviceChartItemUnion);
			} else {
				double allAmount = serviceChartItemUnion.getAllAmount()
						+ amount;
				serviceChartItemUnion.setAllAmount(allAmount);
				if (amount != 0) {
					// Set arrive index plus one, only when none zero amount
					serviceChartItemUnion.setArriveIndex(serviceChartItemUnion
							.getArriveIndex() + 1);
				}
				List<SEUIComModel> refSeUIModelList = serviceChartItemUnion
						.getRefSeUIModelList();
				refSeUIModelList.add(seUIModel);
				serviceChartItemUnion.setRefSeUIModelList(refSeUIModelList);
				result.put(serviceChartItemUnion.getObjectKey(),
						serviceChartItemUnion);
			}
		}
		Set<String> keySet = result.keySet();
		Iterator<String> it = keySet.iterator();
		// Calculate the average amount
		while (it.hasNext()) {
			String key = it.next();
			ServiceChartItemUnion serviceChartItemUnion = result.get(key);
			// refresh average amount
			double averageAmount = serviceChartItemUnion.getAllAmount()
					/ serviceChartItemUnion.getArriveIndex();
			serviceChartItemUnion.setAverageAmount(averageAmount);
		}
		return result;
	}

}
