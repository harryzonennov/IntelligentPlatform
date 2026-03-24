package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceMessageHelper;


@Service
public class ServiceModelMessageHelper extends ServiceMessageHelper {

	public static final String messageResource = "ServiceModelMessage";

	public static final String nameResource = "ServiceModelName";

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	Map<String, Map<String, String>> messageResourceMapLan = new HashMap<>();

	Map<String, Map<String, String>> nameResourceMapLan = new HashMap<>();
	
	public String getMessage(String modelName, String languageCode) throws IOException, ServiceEntityInstallationException {
		Map<String, String> messageMap = this.initMessageResourceMap(languageCode);
		if(messageMap != null){
			return messageMap.get(modelName);
		}
		return null;
	}

	public Map<String, String> initMessageResourceMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefaultLanguageStrMap(languageCode, this.messageResourceMapLan, lanCode->{
			try {
				String path = this.getClass().getResource("").getPath();
				Map<String, String> tempMessageMap = ServiceDropdownListHelper
						.getStrStaticDropDownMap(path + messageResource, lanCode);
				return tempMessageMap;
			} catch (IOException e) {
				return null;
			}
		});
	}

	public Map<String, String> initNameResourceMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefaultLanguageStrMap(languageCode, this.nameResourceMapLan, lanCode->{
			try {
				String path = this.getClass().getResource("").getPath();
				Map<String, String> tempMessageMap = ServiceDropdownListHelper
						.getStrStaticDropDownMap(path + nameResource, lanCode);
				return tempMessageMap;
			} catch (IOException e) {
				return null;
			}
		});
	}

	public String getName(String modelName, String languageCode) throws IOException, ServiceEntityInstallationException {
		Map<String, String> nameMap = this.initNameResourceMap(languageCode);
		if(nameMap != null){
			return nameMap.get(modelName);
		}
		return null;
	}
	
}
