package com.company.IntelligentPlatform.logistics.dto;

import java.util.List;
import java.time.LocalDateTime;

import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemLog;
import org.springframework.stereotype.Service;

@Service
public class WarehouseStoreItemLogChartHelper {
	
	public void mergeEmptyValueMainEntry(String skuUUID,
			List<WarehouseStoreItemChartUnion> rawChartUnionList, int tsSize) {
		WarehouseStoreItemChartUnion firstNonEmptyChartUnion = getFirstNonEmptyTimeSlotIndex(
				skuUUID, rawChartUnionList);
		// Merge the head part
		if (firstNonEmptyChartUnion == null) {
			// In case all of this SKU amount is empty
			return;
		}
		mergeEmptyValueForHeadPart(skuUUID, firstNonEmptyChartUnion,
				rawChartUnionList);
		mergeEmptyValueOtherPartRecursive(
				firstNonEmptyChartUnion.getTimeSlotIndex(), tsSize, skuUUID,
				rawChartUnionList);
	}

	protected void mergeEmptyValueOtherPartRecursive(int startIndex,
			int tsSize, String skuUUID,
			List<WarehouseStoreItemChartUnion> rawChartUnionList) {
		WarehouseStoreItemChartUnion nextChartUnion = getNextNonEmptyTimeSlotIndex(
				startIndex, tsSize, skuUUID, rawChartUnionList);
		if (nextChartUnion == null) {
			// In case there is no non-empty chart union till end of the list
			mergeEmptyValueForOtherPart(skuUUID, startIndex, tsSize, rawChartUnionList);
			return;
		}else{
			mergeEmptyValueForOtherPart(skuUUID, startIndex, nextChartUnion.getTimeSlotIndex(), rawChartUnionList);
		}
		if (nextChartUnion.getTimeSlotIndex() < tsSize - 1) {
			mergeEmptyValueOtherPartRecursive(
					nextChartUnion.getTimeSlotIndex(), tsSize, skuUUID,
					rawChartUnionList);
		}
	}

	protected void mergeEmptyValueForHeadPart(String skuUUID,
			WarehouseStoreItemChartUnion firstNonEmptyChartUnion,
			List<WarehouseStoreItemChartUnion> rawChartUnionList) {
		// In case there is non empty union
		WarehouseStoreItemLog targetStoreItemLog = getCorTargetLogItem(
				firstNonEmptyChartUnion, 1);
		if (targetStoreItemLog == null) {
			return;
		}
		for (int i = 0; i < firstNonEmptyChartUnion.getTimeSlotIndex(); i++) {
			WarehouseStoreItemChartUnion chartUnion = getUnionByTimeSlotIndex(
					skuUUID, i, rawChartUnionList);
			chartUnion.setAverageAmount(targetStoreItemLog.getAmount());
			chartUnion.setAllAmount(targetStoreItemLog.getAmount());
			chartUnion.setArriveIndex(1);
		}
	}

	protected void mergeEmptyValueForOtherPart(String skuUUID, int startIndex,
			int endIndex, List<WarehouseStoreItemChartUnion> rawChartUnionList) {
		WarehouseStoreItemChartUnion firstNonEmptyChartUnion = getUnionByTimeSlotIndex(
				skuUUID, startIndex, rawChartUnionList);
		WarehouseStoreItemLog targetStoreItemLog = getCorTargetLogItem(
				firstNonEmptyChartUnion, 2);
		for (int i = startIndex + 1; i < endIndex; i++) {
			WarehouseStoreItemChartUnion chartUnion = getUnionByTimeSlotIndex(
					skuUUID, i, rawChartUnionList);
			if(chartUnion == null){
				continue;
			}
			if(chartUnion.getAverageAmount() != 0){
				continue;
			}
			chartUnion.setAverageAmount(targetStoreItemLog.getUpdatedAmount());
			chartUnion.setAllAmount(targetStoreItemLog.getUpdatedAmount());
			chartUnion.setArriveIndex(1);
		}
	}

	/**
	 * [Internal method] get the correct target log item from log item
	 * 
	 * @param targetChartUnion
	 * @param direction
	 *            :1-head part, 2-other part
	 */
	protected WarehouseStoreItemLog getCorTargetLogItem(
			WarehouseStoreItemChartUnion targetChartUnion, int direction) {
		if (targetChartUnion == null) {
			return null;
		}
		if (targetChartUnion.getWarehouseStoreItemLogList() == null
				|| targetChartUnion.getWarehouseStoreItemLogList().size() == 0) {
			return null;
		}
		if (targetChartUnion.getWarehouseStoreItemLogList().size() == 1) {
			return targetChartUnion.getWarehouseStoreItemLogList().get(0);
		}
		LocalDateTime targetCreateDate = targetChartUnion.getWarehouseStoreItemLogList()
				.get(0).getCreatedTime();
		WarehouseStoreItemLog targetStoreItemLog = targetChartUnion
				.getWarehouseStoreItemLogList().get(0);
		for (WarehouseStoreItemLog storeItemLog : targetChartUnion
				.getWarehouseStoreItemLogList()) {
			if (direction == 1) {
				// In case head part
				if (storeItemLog.getCreatedTime().isBefore(targetCreateDate)) {
					targetStoreItemLog = storeItemLog;
				}
			} else {
				// In case other part
				if (storeItemLog.getCreatedTime().isAfter(targetCreateDate)) {
					targetStoreItemLog = storeItemLog;
				}
			}
		}
		return targetStoreItemLog;
	}

	/**
	 * [Internal method] filter out the Chart union by time-slot index
	 * 
	 * @param skuUUID
	 * @param index
	 * @param rawChartUnionList
	 * @return
	 */
	protected WarehouseStoreItemChartUnion getUnionByTimeSlotIndex(
			String skuUUID, int index,
			List<WarehouseStoreItemChartUnion> rawChartUnionList) {
		for (WarehouseStoreItemChartUnion chartUnion : rawChartUnionList) {
			if (!skuUUID.equals(chartUnion.getSkuUUID())) {
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
	 * @param skuUUID
	 * @param rawChartUnionList
	 * @return
	 */
	protected WarehouseStoreItemChartUnion getFirstNonEmptyTimeSlotIndex(
			String skuUUID, List<WarehouseStoreItemChartUnion> rawChartUnionList) {
		WarehouseStoreItemChartUnion warehouseStoreItemChartUnion = null;
		int minTimeSlotIndex = 0;
		if (rawChartUnionList == null || rawChartUnionList.size() == 0) {
			return null;
		}
		for (WarehouseStoreItemChartUnion chartUnion : rawChartUnionList) {
			if (!skuUUID.equals(chartUnion.getSkuUUID())) {
				continue;
			}
			if (chartUnion.getAverageAmount() == 0) {
				continue;
			}
			minTimeSlotIndex = chartUnion.getTimeSlotIndex();
			warehouseStoreItemChartUnion = chartUnion;
		}
		for (WarehouseStoreItemChartUnion chartUnion : rawChartUnionList) {
			if (!skuUUID.equals(chartUnion.getSkuUUID())) {
				continue;
			}
			if (chartUnion.getWarehouseStoreItemLogList() == null
					|| chartUnion.getWarehouseStoreItemLogList().size() == 0) {
				continue;
			}
			if (chartUnion.getTimeSlotIndex() <= minTimeSlotIndex) {
				minTimeSlotIndex = chartUnion.getTimeSlotIndex();
				warehouseStoreItemChartUnion = chartUnion;
			}
		}
		return warehouseStoreItemChartUnion;
	}

	protected WarehouseStoreItemChartUnion getNextNonEmptyTimeSlotIndex(
			int oldIndex, int timeSlotSize, String skuUUID,
			List<WarehouseStoreItemChartUnion> rawChartUnionList) {
		if (rawChartUnionList == null || rawChartUnionList.size() == 0) {
			return null;
		}
		for (int i = oldIndex + 1; i < timeSlotSize; i++) {
			WarehouseStoreItemChartUnion chartUnion = getUnionByTimeSlotIndex(
					skuUUID, i, rawChartUnionList);
			if (!skuUUID.equals(chartUnion.getSkuUUID())) {
				continue;
			}
			if (chartUnion.getWarehouseStoreItemLogList() == null
					|| chartUnion.getWarehouseStoreItemLogList().size() == 0) {
				continue;
			}
			return chartUnion;
		}
		return null;
	}

}
