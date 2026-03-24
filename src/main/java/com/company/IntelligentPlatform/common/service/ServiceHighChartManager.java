package com.company.IntelligentPlatform.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Core Service Manager to manage the high chart template
 *
 * @author Zhang, Hang
 */
@Service
public class ServiceHighChartManager {

    public void mergeEmptyValueMainEntry(String objectKey,
                                         List<ServiceChartItemUnion> rawChartUnionList, int tsSize) {
        ServiceChartItemUnion firstNonEmptyChartUnion = getFirstNonEmptyTimeSlotIndex(
                objectKey, rawChartUnionList);
        // Merge the head part
        if (firstNonEmptyChartUnion == null) {
            return;
        }
        mergeEmptyValueForHeadPart(objectKey, firstNonEmptyChartUnion,
                rawChartUnionList);
        mergeEmptyValueOtherPartRecursive(
                firstNonEmptyChartUnion.getTimeSlotIndex(), tsSize, objectKey,
                rawChartUnionList);
    }

    protected void mergeEmptyValueOtherPartRecursive(int startIndex,
                                                     int tsSize, String objectKey,
                                                     List<ServiceChartItemUnion> rawChartUnionList) {
        ServiceChartItemUnion nextChartUnion = getNextNonEmptyTimeSlotIndex(
                startIndex, tsSize, objectKey, rawChartUnionList);
        if (nextChartUnion == null) {
            // In case there is no non-empty chart union till end of the list
            mergeEmptyValueForOtherPart(objectKey, startIndex, tsSize, rawChartUnionList);
            return;
        } else {
            mergeEmptyValueForOtherPart(objectKey, startIndex, nextChartUnion.getTimeSlotIndex(), rawChartUnionList);
        }
        if (nextChartUnion.getTimeSlotIndex() < tsSize - 1) {
            mergeEmptyValueOtherPartRecursive(
                    nextChartUnion.getTimeSlotIndex(), tsSize, objectKey,
                    rawChartUnionList);
        }
    }

    protected void mergeEmptyValueForHeadPart(String objectKey,
                                              ServiceChartItemUnion firstNonEmptyChartUnion,
                                              List<ServiceChartItemUnion> rawChartUnionList) {
//		// In case there is non empty union
//		WarehouseStoreItemLog targetStoreItemLog = getCorTargetLogItem(
//				firstNonEmptyChartUnion, 1);
//		if (targetStoreItemLog == null) {
//			return;
//		}
//		for (int i = 0; i < firstNonEmptyChartUnion.getTimeSlotIndex(); i++) {
//			ServiceChartItemUnion chartUnion = getUnionByTimeSlotIndex(
//					objectKey, i, rawChartUnionList);
//			chartUnion.setAverageAmount(targetStoreItemLog.getAmount());
//			chartUnion.setAllAmount(targetStoreItemLog.getAmount());
//			chartUnion.setArriveIndex(1);
//		}

    }

    protected void mergeEmptyValueForOtherPart(String objectKey, int startIndex,
                                               int endIndex, List<ServiceChartItemUnion> rawChartUnionList) {
//		ServiceChartItemUnion firstNonEmptyChartUnion = getUnionByTimeSlotIndex(
//				objectKey, startIndex, rawChartUnionList);
//		WarehouseStoreItemLog targetStoreItemLog = getCorTargetLogItem(
//				firstNonEmptyChartUnion, 2);
//		for (int i = startIndex + 1; i < endIndex; i++) {
//			ServiceChartItemUnion chartUnion = getUnionByTimeSlotIndex(
//					objectKey, i, rawChartUnionList);
//			if(chartUnion == null){
//				continue;
//			}
//			if(chartUnion.getAverageAmount() != 0){
//				continue;
//			}
//			chartUnion.setAverageAmount(targetStoreItemLog.getUpdatedAmount());
//			chartUnion.setAllAmount(targetStoreItemLog.getUpdatedAmount());
//			chartUnion.setArriveIndex(1);
//		}
    }

    /**
     * [Internal method] get the correct target log item from log item
     *
     * @param targetChartUnion
     * @param direction        :1-head part, 2-other part
     */
    protected SEUIComModel getCorTargetLogItem(
            ServiceChartItemUnion targetChartUnion, int direction) {
        if (targetChartUnion == null) {
            return null;
        }
        if (targetChartUnion.getRefSeUIModelList() == null
                || targetChartUnion.getRefSeUIModelList().size() == 0) {
            return null;
        }
        if (targetChartUnion.getRefSeUIModelList().size() == 1) {
            return targetChartUnion.getRefSeUIModelList().get(0);
        }
//		Date targetCreateDate = targetChartUnion.getRefSeUIModelList()
//				.get(0).getCreatedTime();
//		WarehouseStoreItemLog targetStoreItemLog = targetChartUnion
//				.getRefSeUIModelList().get(0);
//		for (WarehouseStoreItemLog storeItemLog : targetChartUnion
//				.getRefSeUIModelList()) {
//			if (direction == 1) {
//				// In case head part
//				if (storeItemLog.getCreatedTime().getTime()
//						- targetCreateDate.getTime() < 0) {
//					targetStoreItemLog = storeItemLog;
//				}
//			} else {
//				// In case other part
//				if (storeItemLog.getCreatedTime().getTime()
//						- targetCreateDate.getTime() > 0) {
//					targetStoreItemLog = storeItemLog;
//				}
//			}
//		}
        return null;
    }

    /**
     * [Internal method] filter out the Chart union by time-slot index
     *
     * @param objectKey
     * @param index
     * @param rawChartUnionList
     * @return
     */
    protected ServiceChartItemUnion getUnionByTimeSlotIndex(
            String objectKey, int index,
            List<ServiceChartItemUnion> rawChartUnionList) {
        for (ServiceChartItemUnion chartUnion : rawChartUnionList) {
            if (!objectKey.equals(chartUnion.getObjectKey())) {
                continue;
            }
            if (chartUnion.getTimeSlotIndex() == index) {
                return chartUnion;
            }
        }
        return null;
    }

    /**
     * [Internal method] get the first non empty chart union with minimum SKU
     * UUID
     *
     * @param objectKey
     * @param rawChartUnionList
     * @return
     */
    protected ServiceChartItemUnion getFirstNonEmptyTimeSlotIndex(
            String objectKey, List<ServiceChartItemUnion> rawChartUnionList) {
        ServiceChartItemUnion serviceChartItemUnion = null;
        int minTimeSlotIndex = 0;
        if (rawChartUnionList == null || rawChartUnionList.size() == 0) {
            return null;
        }
        for (ServiceChartItemUnion chartUnion : rawChartUnionList) {
            if (!objectKey.equals(chartUnion.getObjectKey())) {
                continue;
            }
            if (chartUnion.getAverageAmount() == 0) {
                continue;
            }
            minTimeSlotIndex = chartUnion.getTimeSlotIndex();
            serviceChartItemUnion = chartUnion;
        }
        for (ServiceChartItemUnion chartUnion : rawChartUnionList) {
            if (!objectKey.equals(chartUnion.getObjectKey())) {
                continue;
            }
            if (chartUnion.getRefSeUIModelList() == null
                    || chartUnion.getRefSeUIModelList().size() == 0) {
                continue;
            }
            if (chartUnion.getTimeSlotIndex() <= minTimeSlotIndex) {
                minTimeSlotIndex = chartUnion.getTimeSlotIndex();
                serviceChartItemUnion = chartUnion;
            }
        }
        return serviceChartItemUnion;
    }

    protected ServiceChartItemUnion getNextNonEmptyTimeSlotIndex(
            int oldIndex, int timeSlotSize, String objectKey,
            List<ServiceChartItemUnion> rawChartUnionList) {
        if (rawChartUnionList == null || rawChartUnionList.size() == 0) {
            return null;
        }
        for (int i = oldIndex + 1; i < timeSlotSize; i++) {
            ServiceChartItemUnion chartUnion = getUnionByTimeSlotIndex(
                    objectKey, i, rawChartUnionList);
            if (!objectKey.equals(chartUnion.getObjectKey())) {
                continue;
            }
            if (chartUnion.getRefSeUIModelList() == null
                    || chartUnion.getRefSeUIModelList().size() == 0) {
                continue;
            }
            return chartUnion;
        }
        return null;
    }


}
