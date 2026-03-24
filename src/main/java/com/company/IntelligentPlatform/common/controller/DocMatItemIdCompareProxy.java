package com.company.IntelligentPlatform.common.controller;

import java.lang.reflect.Field;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxy;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class DocMatItemIdCompareProxy {

	public static int compareItemId(String id1, String id2) {
		if (ServiceEntityStringHelper.checkNullString(id1)
				&& ServiceEntityStringHelper.checkNullString(id2)) {
			return 0;
		}
		if (ServiceEntityStringHelper.checkNullString(id1)) {
			return -1;
		}
		if (ServiceEntityStringHelper.checkNullString(id2)) {
			return 1;
		}
		if (id1.equals(id2)) {
			return 0;
		}
		return id1.compareTo(id2);
	}

	/**
	 * Default Generate Service UI Model Comparator
	 * @param serviceUIModuleType
	 * @param serviceUIModelExtensionUnion
	 * @return
	 */
	public <T> Comparator<T> genDefServiceUIModelComparator(
			T serviceUIModuleType,
			ServiceUIModelExtensionUnion serviceUIModelExtensionUnion) {
		return new Comparator<T>() {
			public int compare(T model1, T model2) {
				try {
					String itemId1 = getDefItemId((ServiceUIModule) model1, serviceUIModelExtensionUnion);
					String itemId2 = getDefItemId((ServiceUIModule) model1, serviceUIModelExtensionUnion);
					return compareItemId(itemId1, itemId2);
				} catch (IllegalArgumentException | IllegalAccessException
						| ServiceUIModuleProxyException e) {
					return 0;
				}
			}
		};
	}

	public String getDefItemId(ServiceUIModule serviceUIModule,
			ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
			throws IllegalArgumentException, IllegalAccessException,
			ServiceUIModuleProxyException {
		Field coreUIModuleField = ServiceModuleProxy
				.getUIModuleFieldByNodeInstId(serviceUIModule.getClass(),
						serviceUIModelExtensionUnion.getNodeInstId());
		if (coreUIModuleField == null) {
			// raise exception when no core module field found
			throw new ServiceUIModuleProxyException(
					ServiceUIModuleProxyException.PARA_NOCOREMODULE,
					serviceUIModule.getClass().getSimpleName());
		}
		coreUIModuleField.setAccessible(true);
		// Get core UI model value
		DocMatItemUIModel uiModelValue = (DocMatItemUIModel) coreUIModuleField
				.get(serviceUIModule);
		return uiModelValue.getId();
	}
}
