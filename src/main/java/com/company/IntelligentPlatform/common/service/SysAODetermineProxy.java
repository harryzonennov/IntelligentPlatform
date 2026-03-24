package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Factory proxy class for SYSTEM authorization determine
 * @author Zhang,Hang
 *
 */
@Service
public class SysAODetermineProxy {
	
	/**
	 * Class attribute as determine area, 
	 */
	@Autowired
	protected CrossOrgData crossOrgData;
	
	@Autowired
	protected ExternalOrgData externalOrgData;
	
	@Autowired
	protected SelfOrgData selfOrgData;
	
	@Autowired
	protected LowerOrgData lowerOrgData;
	
	@Autowired
	protected SelfUserData selfUserData;
	
	public SysAODeterminer getSystemAODeterminer(String determineName){
		if(determineName == null){
			return null;
		}
		if(determineName.equals(CrossOrgData.class.getSimpleName())){
			return crossOrgData;
		}
		if(determineName.equals(ExternalOrgData.class.getSimpleName())){
			return externalOrgData;
		}
		if(determineName.equals(LowerOrgData.class.getSimpleName())){
			return lowerOrgData;
		}
		if(determineName.equals(SelfOrgData.class.getSimpleName())){
			return selfOrgData;
		}
		if(determineName.equals(SelfUserData.class.getSimpleName())){
			return selfUserData;
		}
		return null;
	}

}
