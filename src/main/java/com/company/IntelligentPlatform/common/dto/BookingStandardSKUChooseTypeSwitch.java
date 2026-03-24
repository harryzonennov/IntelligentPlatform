package com.company.IntelligentPlatform.common.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.ISystemConfigureSwitch;

@Service
public class BookingStandardSKUChooseTypeSwitch implements ISystemConfigureSwitch{
	
	public static final int FROM_WAREHOUSEITEM = 2;
	
	public static final int FROM_WAREHOUSE_TO_SKU = 3;
	
	public static final int FROM_SKU_TO_WAREHOUSE = 4;
	
	public static final int SKU_STANDALONE = 5;
	
	public static final String ELEMENT_ID = "BookingStandardItemChoose";

	/**
	 * [Pay attention] key value should not use value 1, starting from 2
	 * @return
	 */
	@Override
	public Map<Integer, String> getSwitchMap() {
		Map<Integer, String> switchMap = new HashMap<Integer, String>();
		switchMap.put(FROM_WAREHOUSEITEM, "From WarehouseItem");
		switchMap.put(FROM_WAREHOUSE_TO_SKU, "From Warehouse To SKU");
		switchMap.put(FROM_SKU_TO_WAREHOUSE, "From SKU To Warehouse");
		switchMap.put(SKU_STANDALONE, "Material SKU Standalone");
		return switchMap;
	}

}
