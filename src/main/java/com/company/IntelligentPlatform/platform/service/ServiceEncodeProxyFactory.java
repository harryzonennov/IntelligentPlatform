package com.company.IntelligentPlatform.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

@Service
public class ServiceEncodeProxyFactory {
	
	public static final String ENCODE_MD5 = "MD5";
	
	@Autowired
	protected ServiceMD5EncodeProxy serviceMD5EncodeProxy;
	
	public IServiceEncodeProxy getEncodeProxy(String key) throws ServiceEncodeException{
		if(key == null || key.equals(ServiceEntityStringHelper.EMPTYSTRING)){
			throw new ServiceEncodeException(ServiceEncodeException.TYPE_SYSTEM_WRONG);
		}
		if(key.equals(ENCODE_MD5)){
			return serviceMD5EncodeProxy;
		}
		throw new ServiceEncodeException(ServiceEncodeException.TYPE_SYSTEM_WRONG);
	}

}
