package com.company.IntelligentPlatform.common.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Component
public class ServiceMD5Base64EncodeProxy {
	
	public String getEncodeValue(String rawValue) throws ServiceEncodeException {
		if (rawValue == null
				|| rawValue.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			return null;
		} else {
			String value = null;
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException ex) {
				throw new ServiceEncodeException(
						ServiceEncodeException.PARA_SYSTEM_WRONG,
						ex.getMessage());
			}
			try {
				value = Base64.getEncoder().encode(md5.digest(rawValue
						.getBytes("utf-8"))).toString();
			} catch (Exception ex) {
				throw new ServiceEncodeException(
						ServiceEncodeException.PARA_SYSTEM_WRONG,
						ex.getMessage());
			}
			return value;
		}
	}

}
