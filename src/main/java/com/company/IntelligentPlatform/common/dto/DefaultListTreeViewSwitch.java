package com.company.IntelligentPlatform.common.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.ISystemConfigureSwitch;

@Service
public class DefaultListTreeViewSwitch implements ISystemConfigureSwitch{
	
    public static final int VIEWTYPE_LIST = 2;
	
	public static final int VIEWTYPE_TREE = 3;

	/**
	 * [Pay attention] key value should not use value 1, starting from 2
	 * @return
	 */
	@Override
	public Map<Integer, String> getSwitchMap() {
		Map<Integer, String> switchMap = new HashMap<Integer, String>();
		switchMap.put(VIEWTYPE_LIST, "List View");
		switchMap.put(VIEWTYPE_TREE, "Tree View");		
		return switchMap;
	}

}
