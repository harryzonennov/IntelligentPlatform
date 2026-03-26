package com.company.IntelligentPlatform.common.model;

import java.util.Comparator;

public class ServiceRegisterEntityModelTypeCompare implements Comparator<ServiceEntityRegisterEntity>{

	@Override
	public int compare(ServiceEntityRegisterEntity register1, ServiceEntityRegisterEntity register2) {
		String modelType1 = register1.getSeModuleType();
		String modelType2 = register2.getSeModuleType();
		if(modelType1 != null && modelType2 != null){
			return modelType1.compareTo(modelType2);
		}else{
			return -1;
		}
	}

}
