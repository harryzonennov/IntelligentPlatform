package com.company.IntelligentPlatform.logistics.dto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;

/**
 * Constant check of warehouse store 
 * @author Zhang,Hang
 *
 */
@Service
public class WarehouseStoreCheckProxy {

	public static final int STATUS_UNKNOWN = 0;

	public static final int STATUS_SUFFICIENT = 1;

	public static final int STATUS_INSUFFICIENT = 2;

	public static final int STATUS_EMPTY = 3;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	protected Map<Integer, String> checkStatusMap;

	protected Map<String, Map<Integer, String>> checkStatusMapLan = new HashMap<>();

	public static final String PROPERTIES_RESOURCE = "WarehouseStoreCheckStatus";

	public Map<Integer, String> getCheckStatusMap()
			throws ServiceEntityInstallationException {
		if (this.checkStatusMap == null) {
			try {
				String path = this.getClass().getResource("").getPath();
				this.checkStatusMap = serviceDropdownListHelper.getDropDownMap(path
						+ PROPERTIES_RESOURCE);
			} catch (IOException e) {
				throw new ServiceEntityInstallationException(ServiceEntityInstallationException.PARA_SYSTEM_WRONG,
						e.getMessage());
			}
		}
		return this.checkStatusMap;
	}

	public String getCheckStatusValue(int key)
			throws ServiceEntityInstallationException {
		Map<Integer, String> switchMap = getCheckStatusMap();
		return switchMap.get(key);
	}

	/**
	 * Get Action Code Map as well as extended action code map
	 * @param languageCode
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public Map<Integer, String> getCheckStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapResource(languageCode,
				this.checkStatusMapLan, this.getClass().getResource("").getPath() + PROPERTIES_RESOURCE);

	}

}
